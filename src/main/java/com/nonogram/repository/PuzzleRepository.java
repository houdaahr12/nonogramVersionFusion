package com.nonogram.repository;

import com.nonogram.model.Puzzle;
import com.nonogram.model.enums.Niveau;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PuzzleRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Puzzle puzzle) {
        em.persist(puzzle);
    }

    public void update(Puzzle puzzle) {
        em.merge(puzzle);
    }

    public Optional<Puzzle> findById(Long id) {
        return Optional.ofNullable(em.find(Puzzle.class, id));
    }

    public List<Puzzle> findAllActif() {
        return em.createQuery(
                "SELECT p FROM Puzzle p WHERE p.actif = true", Puzzle.class)
                .getResultList();
    }

    public List<Puzzle> findByNiveau(Niveau niveau) {
        return em.createQuery(
                "SELECT p FROM Puzzle p WHERE p.niveau = :niveau AND p.actif = true", Puzzle.class)
                .setParameter("niveau", niveau)
                .getResultList();
    }

    public boolean existsByTitle(String title) {
        try {
            em.createQuery(
                    "SELECT p FROM Puzzle p WHERE p.title = :title", Puzzle.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}