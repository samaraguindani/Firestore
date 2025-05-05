package com.example.fire;

public class User {
    private String id;
    private String name;

    public User() {
        // Construtor vazio necessário para o Firestore
    }

    public User(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
