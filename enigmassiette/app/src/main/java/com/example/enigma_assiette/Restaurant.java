package com.example.enigma_assiette;

public class Restaurant {
    private String nom;
    private String dateHeureRepas;
    private float noteDecoration;
    private float noteNourriture;
    private float noteService;
    private String critique;

    public Restaurant(String nom, String dateHeureRepas, float noteDecoration, float noteNourriture, float noteService, String critique) {
        this.nom = nom;
        this.dateHeureRepas = dateHeureRepas;
        this.noteDecoration = noteDecoration;
        this.noteNourriture = noteNourriture;
        this.noteService = noteService;
        this.critique = critique;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}