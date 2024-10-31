package ru.mirea.pkmn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id; // Поле id
    private PokemonStage pokemonStage;
    private String name;
    private int hp;
    private EnergyType pokemonType;
    private Card evolvesFrom; // Эволюционная карта
    private List<AttackSkill> skills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark; // Регуляционный знак
    private Student pokemonOwner; // Владелец покемона
    private int number;
    private List<String> skillsDescription;

    public Card() {
        this.id = UUID.randomUUID(); // Генерируем уникальный идентификатор
        this.skillsDescription = new ArrayList<>(); // Инициализируем список
    }

    public Card(PokemonStage pokemonStage, String name, int hp, EnergyType pokemonType, Card evolvesFrom,
                List<AttackSkill> skills, EnergyType weaknessType, EnergyType resistanceType,
                String retreatCost, String gameSet, char regulationMark, Student pokemonOwner, int number) {
        this.id = UUID.randomUUID(); // Генерируем уникальный идентификатор
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
        this.number = number;
        this.skillsDescription = new ArrayList<>();
    }

    // Геттеры и сеттеры
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getSkillsDescription() {
        return skillsDescription;
    }

    public void setSkillsDescription(List<String> skillsDescription) {
        this.skillsDescription = skillsDescription;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setGameSet(String gameSet) {
        this.gameSet = gameSet;
    }

    public String getGameSet() {
        return gameSet;
    }

    public void setPokemonStage(PokemonStage pokemonStage) {
        this.pokemonStage = pokemonStage;
    }

    public PokemonStage getPokemonStage() {
        return pokemonStage;
    }

    public void setRetreatCost(String retreatCost) {
        this.retreatCost = retreatCost;
    }

    public String getRetreatCost() {
        return retreatCost;
    }

    public void setWeaknessType(EnergyType weaknessType) {
        this.weaknessType = weaknessType;
    }

    public EnergyType getWeaknessType() {
        return weaknessType;
    }

    public void setResistanceType(EnergyType resistanceType) {
        this.resistanceType = resistanceType;
    }

    public EnergyType getResistanceType() {
        return resistanceType;
    }

    public void setPokemonType(EnergyType pokemonType) {
        this.pokemonType = pokemonType;
    }

    public EnergyType getPokemonType() {
        return pokemonType;
    }

    public void setSkills(List<AttackSkill> skills) {
        this.skills = skills;
    }

    public List<AttackSkill> getSkills() {
        return skills;
    }

    public void setRegulationMark(char regulationMark) {
        this.regulationMark = regulationMark;
    }

    public char getRegulationMark() {
        return regulationMark;
    }

    public void setPokemonOwner(Student pokemonOwner) {
        this.pokemonOwner = pokemonOwner;
    }

    public Student getPokemonOwner() {
        return pokemonOwner;
    }

    public void setEvolvesFrom(Card evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public Card getEvolvesFrom() {
        return evolvesFrom;
    }

    @Override
    public String toString() {
        return "Stage: " + pokemonStage +
                "\nName: " + name +
                "\nHP: " + hp +
                "\nType: " + pokemonType +
                "\nEvolves From: " + (evolvesFrom != null ? evolvesFrom : "-") +
                "\nSkills: " + (skills != null && !skills.isEmpty() ? skills.stream().map(AttackSkill::toString).collect(Collectors.joining(", ")) : "-") +
                "\nWeakness: " + (weaknessType != null ? weaknessType : "-") +
                "\nResistance: " + (resistanceType != null ? resistanceType : "-") +
                "\nRetreat Cost: " + retreatCost +
                "\nSet: " + gameSet +
                "\nRegulation Mark: " + regulationMark +
                "\nOwner: " + (pokemonOwner != null ? pokemonOwner.toString() : "Unknown") +
                "\nNumber: " + number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
