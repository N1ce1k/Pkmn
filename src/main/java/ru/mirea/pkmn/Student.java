package ru.mirea.pkmn;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id; // Поле id
    private String firstName;
    private String surName;
    private String familyName;
    private String group;

    public Student() {
        this.id = UUID.randomUUID();
    }

    public Student(String familyName, String firstName, String surName, String group) {
        this.id = UUID.randomUUID();
        this.familyName = familyName;
        this.firstName = firstName;
        this.surName = surName;
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return familyName + " / " + firstName + " / " + surName + " / " + group;
    }
}
