package com.nonogram.service;

import com.nonogram.model.Joueur;
import com.nonogram.model.Partie;
import com.nonogram.model.Puzzle;
import com.nonogram.model.enums.EtatPartie;
import com.nonogram.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PartieService {

    @Autowired
    private PartieRepository partieRepository;

    @Autowired
    private GameService gameService;

    // Create new game
    @Transactional
    public Partie creerPartie(Joueur joueur, Puzzle puzzle) {
        int scoreDepart = gameService.getScoreDepart(puzzle.getNiveau());
        String grilleVide = creerGrilleVide(puzzle.getGridSize());
        Partie partie = new Partie(joueur, puzzle, grilleVide);
        partie.setScoreActuel(scoreDepart);
        partieRepository.save(partie);
        return partie;
    }

    // Update grid state
    @Transactional
    public void updateGrille(Long partieId, String grilleCourante,
                              int scoreActuel, int nbErreurs, int tempsEcoule) {
        Partie partie = partieRepository.findById(partieId)
                .orElseThrow(() -> new RuntimeException("Partie not found"));

        partie.setGrilleCourante(grilleCourante);
        partie.setScoreActuel(scoreActuel);
        partie.setNbErreurs(nbErreurs);
        partie.setTempsEcoule(tempsEcoule);
        // ← DO NOT change etat here! Keep whatever state it has
        partieRepository.save(partie);
    }

    // Save game
    @Transactional
    public void sauvegarder(Long partieId, String grilleCourante,
                             int scoreActuel, int nbErreurs, int tempsEcoule) {
        Partie partie = partieRepository.findById(partieId)
                .orElseThrow(() -> new RuntimeException("Partie not found"));
        partie.setGrilleCourante(grilleCourante);
        partie.setScoreActuel(scoreActuel);
        partie.setNbErreurs(nbErreurs);
        partie.setTempsEcoule(tempsEcoule);
        partie.setEtat(EtatPartie.SAUVEGARDEE);
        partie.setDateSauvegarde(LocalDateTime.now());
        partieRepository.save(partie);
    }

    // Finish game
    @Transactional
    public void terminer(Long partieId, int scoreFinal) {
        Partie partie = partieRepository.findById(partieId)
                .orElseThrow(() -> new RuntimeException("Partie not found"));
        partie.setEtat(EtatPartie.TERMINEE);
        partie.setEstGagnee(true);
        partie.setScoreFinal(scoreFinal);
        partie.setDateFin(LocalDateTime.now());
        partieRepository.save(partie);
    }

    // Game over
    @Transactional
    public void gameOver(Long partieId) {
        Partie partie = partieRepository.findById(partieId)
                .orElseThrow(() -> new RuntimeException("Partie not found"));
        partie.setEtat(EtatPartie.GAME_OVER);
        partie.setEstGagnee(false);
        partie.setDateFin(LocalDateTime.now());
        partieRepository.save(partie);
    }

    // Quit
    @Transactional
    public void quitter(Long partieId) {
        Partie partie = partieRepository.findById(partieId)
                .orElseThrow(() -> new RuntimeException("Partie not found"));
        partie.setEtat(EtatPartie.ABANDONNEE);
        partie.setDateFin(LocalDateTime.now());
        partieRepository.save(partie);
    }

    // Create empty grid JSON
    private String creerGrilleVide(int size) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append("[");
            for (int j = 0; j < size; j++) {
                sb.append("0");
                if (j < size - 1) sb.append(",");
            }
            sb.append("]");
            if (i < size - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
    public Optional<Partie> findSavedByJoueurAndPuzzle(Joueur joueur, long i) {
        return partieRepository.findSavedByJoueurAndPuzzle(joueur, i);
    }
    
    public Optional<Partie> findById(Long partieId) {
        return partieRepository.findById(partieId);
    }

    @Transactional
    public void reprendrePartie(Long partieId) {
        Partie partie = partieRepository.findById(partieId)
                .orElseThrow(() -> new RuntimeException("Partie not found"));
        partie.setEtat(EtatPartie.EN_COURS);
        partieRepository.save(partie);
    }
    
    public long countByJoueur(Joueur joueur) {
        return partieRepository.countByJoueur(joueur);
    }
}