package ru.mirea.SichinskiyLE;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Card implements Serializable
{
    private static final long serialVersionUID = 1L;
    private PokemonStage stage;
    private String name;
    private int hp;
    private EnergyType type;
    private Card evolvesFrom;
    private List<AttackSkill> skills;
    private EnergyType weakness;
    private EnergyType resistance;
    private String retreatCost;
    private String setName;
    private char regulationMark;
    private Student owner;

    public Card(PokemonStage stage, String name, int hp, EnergyType type, Card evolvesFrom,
                List<AttackSkill> skills, EnergyType weakness, EnergyType resistance,
                String retreatCost, String setName, char regulationMark, Student owner)
    {
        this.stage = stage;
        this.name = name;
        this.hp = hp;
        this.type = type;
        this.evolvesFrom = evolvesFrom;
        this.skills = skills;
        this.weakness = weakness;
        this.resistance = resistance;
        this.retreatCost = retreatCost;
        this.setName = setName;
        this.regulationMark = regulationMark;
        this.owner = owner;
    }

    @Override
    public String toString()
    {
        return "Stage: " + stage +
                "\nName: " + name +
                "\nHP: " + hp +
                "\nType: " + type +
                "\nEvolves From: " + (evolvesFrom != null ? evolvesFrom.getName() : "-") +
                "\nSkills: " + (skills.isEmpty() ? "-" : skills.stream().map(AttackSkill::toString).collect(Collectors.joining(", "))) +
                "\nWeakness: " + (weakness != null ? weakness : "-") +
                "\nResistance: " + (resistance != null ? resistance : "-") +
                "\nRetreat Cost: " + retreatCost +
                "\nSet: " + setName +
                "\nRegulation Mark: " + regulationMark +
                "\nOwner: " + (owner != null ? owner.toString() : "Unknown");
    }

    public String getName()
    {
        return name;
    }
}
