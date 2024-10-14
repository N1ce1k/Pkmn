package ru.mirea.pkmn.SichinskiyLE;

import ru.mirea.pkmn.Card;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CardExport
{
    public void exportCard(Card card, String fileName)
    {
        try (FileOutputStream fileOut = new FileOutputStream(fileName + ".crd");
             ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {
            out.writeObject(card);
            System.out.println("Card saved to " + fileName + ".crd");
        }
        catch (IOException e)
        {
            System.out.println("Error during card export: " + e.getMessage());
        }
    }
}
