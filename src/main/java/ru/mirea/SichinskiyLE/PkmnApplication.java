package ru.mirea.SichinskiyLE;

public class PkmnApplication
{
    public static void main(String[] args)
    {
        CardImport cardImport = new CardImport();
        CardExport cardExport = new CardExport();

        String cardFilePath = "C:\\Users\\lsich\\IdeaProjects\\Pkmn\\src\\main\\resources\\my_card.txt";
        Card originalCard = cardImport.importCard(cardFilePath);
        if (originalCard == null)
        {
            System.out.println("Card import failed. Please check the file path and format.");
            return;
        }
        System.out.println("Оригинальная карта: ");
        System.out.println(originalCard);

        String exportFileName = "C:\\Users\\lsich\\IdeaProjects\\Pkmn\\src\\main\\resources\\exported_card";
        cardExport.exportCard(originalCard, exportFileName);

        Card importedCard = cardImport.importFromCrd(exportFileName + ".crd");
        if (importedCard == null)
        {
            System.out.println("Failed to import card from .crd file.");
            return;
        }
        System.out.println("Карта после десериализации:");
        System.out.println(importedCard);

        if (originalCard.toString().equals(importedCard.toString()))
        {
            System.out.println("Оригинальная и восстановленная карты совпадают!");
        }
        else
        {
            System.out.println("Ошибка! Оригинальная и восстановленная карты НЕ совпадают.");
        }
    }
}


