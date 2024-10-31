package ru.mirea.pkmn.web.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mirea.pkmn.*;
import ru.mirea.pkmn.SichinskiyLE.CardImport;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class DatabaseServiceImpl implements DatabaseService {

    private final Connection connection;
    private final Properties databaseProperties;

    public DatabaseServiceImpl() throws SQLException, IOException {
        databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));
        connection = DriverManager.getConnection(
                databaseProperties.getProperty("database.url"),
                databaseProperties.getProperty("database.user"),
                databaseProperties.getProperty("database.password")
        );
        System.out.println("Connection is " + (connection.isValid(0) ? "up" : "down"));
    }

    @Override
    public Card getCardFromDatabase(UUID cardId) {
        Card result = new Card();
        try {
            String query = String.format("SELECT * FROM card WHERE (id = '%s');", cardId);
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            if (rs.next()) {
                result.setId(cardId);
                getCardBase(result, rs);
            } else {
                throw new RuntimeException("Empty result from database");
            }
        } catch (SQLException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void getCardBase(Card result, ResultSet rs) throws SQLException, JsonProcessingException {
        UUID evolvesFrom = (UUID) rs.getObject("evolves_from");
        result.setEvolvesFrom(evolvesFrom == null ? null : getCardFromDatabase(evolvesFrom));
        result.setName(rs.getString("name"));
        result.setNumber(rs.getInt("card_number"));
        result.setHp(rs.getInt("hp"));
        result.setPokemonOwner(getStudentFromDatabase((UUID) rs.getObject("pokemon_owner")));
        result.setRegulationMark(rs.getString("regulation_mark").charAt(0));
        result.setWeaknessType(EnergyType.valueOf(rs.getString("weakness_type")));
        result.setGameSet(rs.getString("game_set"));
        String resistance = rs.getString("resistance_type");
        result.setResistanceType(resistance == null ? null : EnergyType.valueOf(resistance));
        result.setPokemonStage(PokemonStage.valueOf(rs.getString("stage").toUpperCase()));
        result.setRetreatCost(rs.getString("retreat_cost"));
        result.setSkills(CardImport.parseAttackSkillsFromJson(rs.getString("attack_skills")));
    }

    @Override
    public Student getStudentFromDatabase(UUID studentId) {
        Student result = new Student();
        try {
            String query = String.format("SELECT * FROM student WHERE (id = '%s');", studentId);
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);

            if (rs.next()) {
                result.setId(studentId);
                result.setFirstName(rs.getString("firstName"));
                result.setFamilyName(rs.getString("familyName"));
                result.setSurName(rs.getString("patronicName"));
                result.setGroup(rs.getString("group"));
            } else {
                throw new RuntimeException("Empty result from database");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void saveCardToDatabase(Card card) {
        StringBuilder queryBase = new StringBuilder("INSERT INTO card(");
        StringBuilder query = new StringBuilder("VALUES(");
        if (card.getEvolvesFrom() != null) {
            queryBase.append("evolves_from, ");
            query.append("'").append(card.getEvolvesFrom().getId()).append("', ");
        }
        if (card.getPokemonOwner() != null) {
            queryBase.append("pokemon_owner, ");
            query.append("'").append(card.getPokemonOwner().getId()).append("', ");
        }
        queryBase.append("id, name, hp, game_set, stage, retreat_cost, weakness_type, resistance_type, attack_skills, pokemon_type, regulation_mark, card_number) ");
        query.append("'").append(UUID.randomUUID()).append("', '");
        query.append(card.getName()).append("', ");
        query.append(card.getHp()).append(", '");
        query.append(card.getGameSet()).append("', '");
        query.append(card.getPokemonStage()).append("', '");
        query.append(card.getRetreatCost()).append("', '");
        query.append(card.getWeaknessType()).append("', '");
        query.append(card.getResistanceType()).append("', '");
        query.append("[");

        for (AttackSkill as : card.getSkills()) {
            query.append(as.toString().replace('\'', '`')).append(", ");
        }
        if (!card.getSkills().isEmpty()) {
            query.delete(query.length() - 2, query.length());
        }
        query.append("]").append("', '");
        query.append(card.getPokemonType()).append("', '");
        query.append(card.getRegulationMark()).append("', ");
        query.append(card.getNumber()).append(");");

        System.out.println(queryBase.toString() + query.toString());

        try {
            connection.createStatement().executeUpdate(queryBase.toString() + query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID createPokemonOwner(Student owner) {
        UUID ownerId = UUID.randomUUID();
        try {
            String query = String.format("INSERT INTO student (\"firstName\", \"familyName\", \"patronicName\", \"group\", \"id\") " +
                            "VALUES ('%s', '%s', '%s', '%s', '%s');",
                    owner.getFirstName(), owner.getFamilyName(), owner.getSurName(), owner.getGroup(), ownerId);
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ownerId;
    }
}
