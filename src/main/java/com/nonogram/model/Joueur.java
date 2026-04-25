package com.nonogram.model;

import jakarta.persistence.*;

@Entity
@Table(name = "joueurs")
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String pseudo;

    @Column(nullable = false)
    private String password;

 
    @Column(name = "meilleurscore")
    private int meilleurScore;

    @Column(name = "totalparties")
    private int totalParties;

    public Joueur() {}

    public Joueur(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
        this.meilleurScore = 0;
        this.totalParties = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getMeilleurScore() { return meilleurScore; }
    public void setMeilleurScore(int score) { this.meilleurScore = score; }

    public int getTotalParties() { return totalParties; }
    public void setTotalParties(int totalParties) { this.totalParties = totalParties; }
}