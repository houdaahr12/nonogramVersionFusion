package com.nonogram.model;

import com.nonogram.model.enums.Niveau;
import jakarta.persistence.*;

@Entity
@Table(name = "puzzle")
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "gridsize")
    private int gridSize;

    @Column(name = "max_erreurs")
    private int maxErreurs = 0;
    

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    @Column(name = "solutionjson", columnDefinition = "TEXT")
    private String solutionJson;

    @Column(name = "rowcluesjson", columnDefinition = "TEXT")
    private String rowCluesJson;

    @Column(name = "colcluesjson", columnDefinition = "TEXT")
    private String colCluesJson;

    @Column(name = "imageurl", columnDefinition = "TEXT")
    private String imageUrl;

    

    private boolean actif = true;

    public Puzzle() {}

    public Puzzle(int id, String title, int gridSize, Niveau niveau,
                  String solutionJson, String rowCluesJson, String colCluesJson) {
        this.id = id;
        this.title = title;
        this.gridSize = gridSize;
        this.niveau = niveau;
        this.solutionJson = solutionJson;
        this.rowCluesJson = rowCluesJson;
        this.colCluesJson = colCluesJson;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getGridSize() { return gridSize; }
    public void setGridSize(int gridSize) { this.gridSize = gridSize; }

    public int getMaxErreurs() { return maxErreurs; }
    public void setMaxErreurs(int maxErreurs) { this.maxErreurs = maxErreurs; }

    public Niveau getNiveau() { return niveau; }
    public void setNiveau(Niveau niveau) { this.niveau = niveau; }

    public String getSolutionJson() { return solutionJson; }
    public void setSolutionJson(String solutionJson) { this.solutionJson = solutionJson; }

    public String getRowCluesJson() { return rowCluesJson; }
    public void setRowCluesJson(String rowCluesJson) { this.rowCluesJson = rowCluesJson; }

    public String getColCluesJson() { return colCluesJson; }
    public void setColCluesJson(String colCluesJson) { this.colCluesJson = colCluesJson; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}