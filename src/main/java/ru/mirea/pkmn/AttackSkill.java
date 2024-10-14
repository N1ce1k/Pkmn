package ru.mirea.pkmn;

import java.io.Serializable;

public class AttackSkill implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String cost;
    private int damage;

    public AttackSkill(String name, String cost, int damage)
    {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
    }

    @Override
    public String toString()
    {
        return cost + " / " + name + " / " + damage;
    }
}