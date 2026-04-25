package com.nonogram.repository;

import com.nonogram.model.Joueur;
import com.nonogram.model.Partie;
import com.nonogram.model.enums.EtatPartie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PartieRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Partie partie) {
        if (partie.getId() == null) {
            em.persist(partie); 
        } else {
            em.merge(partie);    
        }
    }

    public Partie update(Partie partie) {
        return em.merge(partie);  // returns the managed instance — always use the return value
    }

    public Optional<Partie> findById(Long id) {
        return Optional.ofNullable(em.find(Partie.class, id));
    }

    /** All games for a player, newest first */
    public List<Partie> findByJoueur(Joueur joueur) {
        return em.createQuery(
                "SELECT p FROM Partie p WHERE p.joueur = :joueur ORDER BY p.dateDebut DESC",
                Partie.class)
                .setParameter("joueur", joueur)
                .getResultList();
    }

    /** Resume the most recent saved game for a player */
    public Optional<Partie> findSavedByJoueur(Joueur joueur) {
        try {
            Partie p = em.createQuery(
                    "SELECT p FROM Partie p WHERE p.joueur = :joueur AND p.etat = :etat " +
                    "ORDER BY p.dateSauvegarde DESC",
                    Partie.class)
                    .setParameter("joueur", joueur)
                    .setParameter("etat", EtatPartie.SAUVEGARDEE)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.of(p);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /** Active (EN_COURS) game for a player on a specific puzzle */
    public Optional<Partie> findActiveByJoueurAndPuzzle(Joueur joueur, Long puzzleId) {
        try {
            Partie p = em.createQuery(
                    "SELECT p FROM Partie p WHERE p.joueur = :joueur " +
                    "AND p.puzzle.id = :puzzleId AND p.etat = :etat",
                    Partie.class)
                    .setParameter("joueur", joueur)
                    .setParameter("puzzleId", puzzleId)
                    .setParameter("etat", EtatPartie.EN_COURS)
                    .getSingleResult();
            return Optional.of(p);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /** History: only finished games (won or lost) */
    public List<Partie> findTermineesbyJoueur(Joueur joueur) {
        return em.createQuery(
                "SELECT p FROM Partie p WHERE p.joueur = :joueur " +
                "AND p.etat IN (:t, :go) ORDER BY p.dateFin DESC",
                Partie.class)
                .setParameter("joueur", joueur)
                .setParameter("t",  EtatPartie.TERMINEE)
                .setParameter("go", EtatPartie.GAME_OVER)
                .getResultList();
    }
    
    /** Saved game for a player on a specific puzzle */
    public Optional<Partie> findSavedByJoueurAndPuzzle(Joueur joueur, Long puzzleId) {
        try {
            Partie p = em.createQuery(
                    "SELECT p FROM Partie p WHERE p.joueur = :joueur " +
                    "AND p.puzzle.id = :puzzleId AND p.etat = :etat " +
                    "ORDER BY p.dateSauvegarde DESC",
                    Partie.class)
                    .setParameter("joueur", joueur)
                    .setParameter("puzzleId", puzzleId)
                    .setParameter("etat", EtatPartie.SAUVEGARDEE)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.of(p);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    public long countByJoueur(Joueur joueur) {
        return em.createQuery(
                "SELECT COUNT(p) FROM Partie p WHERE p.joueur = :joueur", Long.class)
                .setParameter("joueur", joueur)
                .getSingleResult();
    }
}