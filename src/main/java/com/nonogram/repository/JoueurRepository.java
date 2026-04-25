package com.nonogram.repository;

import com.nonogram.model.Joueur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JoueurRepository {

    @PersistenceContext
    private EntityManager em;

    // Sauvegarder un nouveau joueur
    public void save(Joueur joueur) {
        em.persist(joueur);
    }

    public Joueur findByPseudo(String pseudo) {
        try {
            return em.createQuery(
                "SELECT j FROM Joueur j WHERE j.pseudo = :pseudo", Joueur.class)
                .setParameter("pseudo", pseudo)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean existsByPseudo(String pseudo) {
        return findByPseudo(pseudo) != null;
    }
}