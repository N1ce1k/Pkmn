package ru.mirea.pkmn.SichinskiyLE;

import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.web.http.PkmnHttpClient;
import ru.mirea.pkmn.web.jdbc.DatabaseServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class PkmnApplication {
    public static void main(String[] args) {
        try {
            PkmnHttpClient httpClient = new PkmnHttpClient();

            DatabaseServiceImpl db = new DatabaseServiceImpl();

            Card myCard = CardImport.importCard("src/main/resources/my_card.txt");

            CardImport.updateAttackSkill(myCard, httpClient);

            db.saveCardToDatabase(myCard);
            System.out.println("Карта успешно сохранена в базе данных.");
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ошибка SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


