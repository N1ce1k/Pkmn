package ru.mirea.pkmn.SichinskiyLE;

import ru.mirea.pkmn.*;
import ru.mirea.pkmn.web.http.PkmnHttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardImport {

    public static Card importCard(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String stageLine = reader.readLine();
            if (stageLine == null || stageLine.trim().isEmpty()) {
                throw new NullPointerException("Stage line is missing or empty");
            }
            PokemonStage stage = PokemonStage.valueOf(stageLine.trim().toUpperCase());
            System.out.println("Stage: " + stage);

            String name = reader.readLine();
            if (name == null || name.trim().isEmpty()) {
                throw new NullPointerException("Name line is missing or empty");
            }
            System.out.println("Name: " + name);

            String hpLine = reader.readLine();
            if (hpLine == null || hpLine.trim().isEmpty()) {
                throw new NullPointerException("HP line is missing or empty");
            }
            int hp = Integer.parseInt(hpLine.trim());
            System.out.println("HP: " + hp);

            String typeLine = reader.readLine();
            if (typeLine == null || typeLine.trim().isEmpty()) {
                throw new NullPointerException("Energy type line is missing or empty");
            }
            EnergyType type = EnergyType.valueOf(typeLine.trim().toUpperCase());
            System.out.println("Energy Type: " + type);

            String evolvesFromPath = reader.readLine();
            Card evolvesFrom = null;
            if (evolvesFromPath != null && !evolvesFromPath.trim().equals("-")) {
                evolvesFrom = importCard(evolvesFromPath.trim());
                System.out.println("Evolves from card found at: " + evolvesFromPath);
            }

            String skillsLine = reader.readLine();
            List<AttackSkill> skills = new ArrayList<>();
            if (skillsLine != null && !skillsLine.trim().isEmpty()) {
                String[] skillsData = skillsLine.split(",");
                for (String skillData : skillsData) {
                    String[] parts = skillData.trim().split(" / ");
                    if (parts.length == 3) {
                        String cost = parts[0].trim();
                        String nameSkill = parts[1].trim();
                        int damage = Integer.parseInt(parts[2].trim());
                        skills.add(new AttackSkill(nameSkill, cost, damage));
                    }
                }
                System.out.println("Skills: " + skills);
            }

            String weaknessLine = reader.readLine();
            EnergyType weakness = (weaknessLine != null && !weaknessLine.trim().equals("-")) ?
                    EnergyType.valueOf(weaknessLine.trim().toUpperCase()) : null;
            System.out.println("Weakness: " + weakness);

            String resistanceLine = reader.readLine();
            EnergyType resistance = (resistanceLine != null && !resistanceLine.trim().equals("-")) ?
                    EnergyType.valueOf(resistanceLine.trim().toUpperCase()) : null;
            System.out.println("Resistance: " + resistance);

            String retreatCost = reader.readLine();
            System.out.println("Retreat Cost: " + retreatCost);

            String setName = reader.readLine();
            System.out.println("Set Name: " + setName);

            String regulationMarkLine = reader.readLine();
            if (regulationMarkLine == null || regulationMarkLine.trim().isEmpty()) {
                throw new NullPointerException("Regulation mark line is missing or empty");
            }
            char regulationMark = regulationMarkLine.trim().charAt(0);
            System.out.println("Regulation Mark: " + regulationMark);

            String ownerLine = reader.readLine();
            Student pokemonOwner = null;
            if (ownerLine != null && !ownerLine.trim().equals("-")) {
                String[] ownerData = ownerLine.split(" / ");
                if (ownerData.length == 4) {
                    pokemonOwner = new Student(ownerData[0], ownerData[1], ownerData[2], ownerData[3]);
                    System.out.println("Owner: " + pokemonOwner);
                } else {
                    System.out.println("Owner line is incorrectly formatted: " + ownerLine);
                }
            }

            String numberLine = reader.readLine();
            if (numberLine == null || numberLine.trim().isEmpty()) {
                throw new NullPointerException("Number line is missing or empty");
            }
            int number = Integer.parseInt(numberLine.trim());
            System.out.println("Card Number: " + number);

            return new Card(stage, name, hp, type, evolvesFrom, skills, weakness, resistance, retreatCost, setName, regulationMark, pokemonOwner, number);
        } catch (IOException e) {
            System.out.println("Error during card import: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid enum value: " + e.getMessage());
            return null;
        }
    }

    public Card importFromCrd(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Card) in.readObject();
        } catch (IOException e) {
            System.out.println("Error during card import: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Card class not found: " + e.getMessage());
            return null;
        }
    }

    public static void updateAttackSkill(Card card, PkmnHttpClient httpClient) throws IOException {
        if (card.getEvolvesFrom() != null) {
            updateAttackSkill(card.getEvolvesFrom(), httpClient);
        }

        List<JsonNode> tmp = httpClient.getPokemonCard(card.getName(), card.getNumber()).findValues("text");
        if (tmp.size() > card.getSkills().size()) {
            for (int i = 0; i < card.getSkills().size(); i++) {
                card.getSkills().get(i).setDescription(tmp.get(i).asText());
            }
        } else {
            System.out.println("Not enough skills to update: expected " + tmp.size() + ", but got " + card.getSkills().size());
        }
    }

    public static ArrayList<AttackSkill> parseAttackSkillsFromJson(String json) throws JsonProcessingException {
        ArrayList<AttackSkill> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode tmp = (ArrayNode) objectMapper.readTree(json);
        for (int i = 0; i < tmp.size(); i++) {
            JsonNode ji = tmp.get(i);
            AttackSkill as = new AttackSkill();
            as.setDescription(ji.findValue("text").asText());
            as.setCost(ji.findValue("cost").asText());
            as.setDamage(ji.get("damage").asInt());
            as.setName(ji.findValue("name").asText());
            result.add(as);
        }
        return result;
    }
}
