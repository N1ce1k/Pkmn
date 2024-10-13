package ru.mirea.SichinskiyLE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardImport
{
    public Card importCard(String filePath)
    {
        File file = new File(filePath);
        if (!file.exists())
        {
            System.out.println("File not found: " + filePath);
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            PokemonStage stage = PokemonStage.valueOf(reader.readLine().toUpperCase());
            String name = reader.readLine();
            int hp = Integer.parseInt(reader.readLine());
            EnergyType type = EnergyType.valueOf(reader.readLine().toUpperCase());

            String evolvesFromPath = reader.readLine();
            Card evolvesFrom = null;
            if (evolvesFromPath != null && !evolvesFromPath.equals("-"))
            {
                evolvesFrom = importCard(evolvesFromPath);
            }

            String skillsLine = reader.readLine();
            List<AttackSkill> skills = new ArrayList<>();
            if (skillsLine != null)
            {
                String[] skillsData = skillsLine.split(",");
                for (String skillData : skillsData)
                {
                    String[] parts = skillData.trim().split(" / ");
                    if (parts.length == 3)
                    {
                        String cost = parts[0];
                        String nameSkill = parts[1];
                        int damage = Integer.parseInt(parts[2]);
                        skills.add(new AttackSkill(nameSkill, cost, damage));
                    }
                }
            }

            String weaknessLine = reader.readLine();
            EnergyType weakness = (weaknessLine != null && !weaknessLine.equals("-")) ? EnergyType.valueOf(weaknessLine.toUpperCase()) : null;

            String resistanceLine = reader.readLine();
            EnergyType resistance = (resistanceLine != null && !resistanceLine.equals("-")) ? EnergyType.valueOf(resistanceLine.toUpperCase()) : null;

            String retreatCost = reader.readLine();
            String setName = reader.readLine();
            char regulationMark = reader.readLine().charAt(0);

            String ownerLine = reader.readLine();
            Student owner = null;
            if (ownerLine != null) {
                String[] ownerData = ownerLine.split(" / ");
                if (ownerData.length == 4) {
                    owner = new Student(ownerData[0], ownerData[1], ownerData[2], ownerData[3]);
                }
            }
            return new Card(stage, name, hp, type, evolvesFrom, skills, weakness, resistance, retreatCost, setName, regulationMark, owner);
        }
        catch (IOException e)
        {
            System.out.println("Error during card import: " + e.getMessage());
            return null;
        }
    }

    public Card importFromCrd(String filePath)
    {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn))
        {
            return (Card) in.readObject();
        } catch (IOException e)
        {
            System.out.println("Error during card import: " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Card class not found: " + e.getMessage());
            return null;
        }
    }
}