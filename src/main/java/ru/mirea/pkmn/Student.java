package ru.mirea.pkmn;

import java.io.Serializable;

public class Student implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String surName;
    private String familyName;
    private String group;

    public Student(String familyName, String firstName, String surName, String group)
    {
        this.familyName = familyName;
        this.firstName = firstName;
        this.surName = surName;
        this.group = group;
    }

    @Override
    public String toString()
    {
        return familyName + " / " + firstName + " / " + surName + " / " + group;
    }
}