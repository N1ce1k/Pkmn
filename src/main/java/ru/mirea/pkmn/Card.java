package ru.mirea.pkmn;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Card implements Serializable
{
    private static final long serialVersionUID = 1L;
    private PokemonStage pokemonStage;
    private String name;
    private int hp;
    private EnergyType pokemonType;
    private Card evolvesFrom;
    private List<AttackSkill> skills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark;
    private Student pokemonOwner;

    public Card(PokemonStage pokemonStage, String name, int hp, EnergyType pokemonType, Card evolvesFrom,
                List<AttackSkill> skills, EnergyType weaknessType, EnergyType resistanceType,
                String retreatCost, String gameSet, char regulationMark, Student pokemonOwner)
    {
        this.pokemonStage = pokemonStage;
        this.name = name;
        this.hp = hp;
        this.pokemonType = pokemonType;
        this.evolvesFrom = evolvesFrom;
        this.skills = skills;
        this.weaknessType = weaknessType;
        this.resistanceType = resistanceType;
        this.retreatCost = retreatCost;
        this.gameSet = gameSet;
        this.regulationMark = regulationMark;
        this.pokemonOwner = pokemonOwner;
    }

    @Override
    public String toString()
    {
        return "Stage: " + pokemonStage +
                "\nName: " + name +
                "\nHP: " + hp +
                "\nType: " + pokemonType +
                "\nEvolves From: " + (evolvesFrom != null ? evolvesFrom: "-") +
                "\nSkills: " + (skills.isEmpty() ? "-" : skills.stream().map(AttackSkill::toString).collect(Collectors.joining(", "))) +
                "\nWeakness: " + (weaknessType != null ? weaknessType : "-") +
                "\nResistance: " + (resistanceType != null ? resistanceType : "-") +
                "\nRetreat Cost: " + retreatCost +
                "\nSet: " + gameSet +
                "\nRegulation Mark: " + regulationMark +
                "\nOwner: " + (pokemonOwner != null ? pokemonOwner.toString() : "Unknown");
    }

    public String getName()
    {
        return name;
    }
}
