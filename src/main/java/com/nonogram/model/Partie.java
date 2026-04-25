package com.nonogram.model;

import com.nonogram.model.enums.EtatPartie;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partie")
public class Partie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "joueur_id", nullable = false)
    private Joueur joueur;

    @ManyToOne
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @Enumerated(EnumType.STRING)
    private EtatPartie etat = EtatPartie.EN_COURS;

    @Column(name = "grillecourante", columnDefinition = "TEXT")
    private String grilleCourante;

    @Column(name = "scoreactuel")
    private int scoreActuel;

    @Column(name = "scorefinal")
    private int scoreFinal;

    @Column(name = "nberreurs")
    private int nbErreurs;

    @Column(name = "tempsecoule")
    private int tempsEcoule;

    @Column(name = "estgagnee")
    private boolean estGagnee;

    @Column(name = "datedebut")
    private LocalDateTime dateDebut;

    @Column(name = "datesauvegarde")
    private LocalDateTime dateSauvegarde;

    @Column(name = "datefin")
    private LocalDateTime dateFin;
    // ── Constructors ──────────────────────────────────────────────

    public Partie() {}

    public Partie(Joueur joueur, Puzzle puzzle, String grilleCourante) {
        this.joueur         = joueur;
        this.puzzle         = puzzle;
        this.grilleCourante = grilleCourante;
        this.etat           = EtatPartie.EN_COURS;
        this.dateDebut      = LocalDateTime.now();
        this.scoreActuel    = 0;
        this.scoreFinal     = 0;
        this.nbErreurs      = 0;
        this.tempsEcoule    = 0;
        this.estGagnee      = false;
    }

    // ── Getters & Setters ─────────────────────────────────────────

    public Long getId()                              { return id; }
    public void setId(Long id)                       { this.id = id; }

    public Joueur getJoueur()                        { return joueur; }
    public void setJoueur(Joueur joueur)             { this.joueur = joueur; }

    public Puzzle getPuzzle()                        { return puzzle; }
    public void setPuzzle(Puzzle puzzle)             { this.puzzle = puzzle; }

    public EtatPartie getEtat()                      { return etat; }
    public void setEtat(EtatPartie etat)             { this.etat = etat; }

    public String getGrilleCourante()                { return grilleCourante; }
    public void setGrilleCourante(String g)          { this.grilleCourante = g; }

    public int getScoreActuel()                      { return scoreActuel; }
    public void setScoreActuel(int scoreActuel)      { this.scoreActuel = scoreActuel; }

    public int getScoreFinal()                       { return scoreFinal; }
    public void setScoreFinal(int scoreFinal)        { this.scoreFinal = scoreFinal; }

    public int getNbErreurs()                        { return nbErreurs; }
    public void setNbErreurs(int nbErreurs)          { this.nbErreurs = nbErreurs; }

    public int getTempsEcoule()                      { return tempsEcoule; }
    public void setTempsEcoule(int tempsEcoule)      { this.tempsEcoule = tempsEcoule; }

    public boolean isEstGagnee()                     { return estGagnee; }
    public void setEstGagnee(boolean estGagnee)      { this.estGagnee = estGagnee; }

    public LocalDateTime getDateDebut()              { return dateDebut; }
    public void setDateDebut(LocalDateTime d)        { this.dateDebut = d; }

    public LocalDateTime getDateSauvegarde()         { return dateSauvegarde; }
    public void setDateSauvegarde(LocalDateTime d)   { this.dateSauvegarde = d; }

    public LocalDateTime getDateFin()                { return dateFin; }
    public void setDateFin(LocalDateTime dateFin)    { this.dateFin = dateFin; }
}