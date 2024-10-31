package ru.mirea.pkmn;

import java.io.Serial;
import java.io.Serializable;

public class AttackSkill implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String cost;
    private int damage;
    private String description;

    // Конструктор с параметрами
    public AttackSkill(String name, String cost, int damage) {
        this.name = name;
        this.cost = cost;
        setDamage(damage);  // Используем сеттер для проверки значений
        this.description = ""; // Устанавливаем по умолчанию на пустую строку
    }

    // Пустой конструктор для сериализации
    public AttackSkill() {
        this.name = "";
        this.cost = "";
        this.damage = 0;
        this.description = ""; // Устанавливаем по умолчанию на пустую строку
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        this.damage = damage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AttackSkill{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                ", damage=" + damage +
                ", description='" + description + '\'' +
                '}';
    }
}