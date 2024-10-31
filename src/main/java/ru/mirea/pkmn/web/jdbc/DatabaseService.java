package ru.mirea.pkmn.web.jdbc;

import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.Student;

import java.util.UUID;

public interface DatabaseService {
    Card getCardFromDatabase(UUID cardId);

    Student getStudentFromDatabase(UUID studentId);

    void saveCardToDatabase(Card card);

    UUID createPokemonOwner(Student owner);
}
