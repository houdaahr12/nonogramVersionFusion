package com.nonogram.service;

import com.nonogram.model.Puzzle;
import com.nonogram.model.enums.Niveau;
import com.nonogram.repository.PuzzleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final PuzzleRepository puzzleRepository;
    private final Random random = new Random();

    public GameService(PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    // 🔹 Get puzzle by ID
    public Puzzle getPuzzleById(Long id) {
        return puzzleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Puzzle not found with id: " + id));
    }

    // 🔹 Get all active puzzles
    public List<Puzzle> getAllPuzzles() {
        return puzzleRepository.findAllActif();
    }

    // 🔹 Get puzzles by difficulty
    public List<Puzzle> getPuzzlesByNiveau(Niveau niveau) {
        return puzzleRepository.findByNiveau(niveau);
    }

    // 🔹 Get ONE puzzle (random selection → better UX)
    public Puzzle getOnePuzzleByNiveau(Niveau niveau) {
        List<Puzzle> puzzles = puzzleRepository.findByNiveau(niveau);

        if (puzzles.isEmpty()) {
            throw new RuntimeException("No puzzle found for niveau: " + niveau);
        }

        return puzzles.get(random.nextInt(puzzles.size()));
    }

 // 🔹 Check if next level exists
    public boolean hasNextLevel(Niveau niveau) {
        Niveau next = getNextNiveau(niveau);
        if (next == null) return false;
        return !puzzleRepository.findByNiveau(next).isEmpty();
    }

 // 🔹 Get first puzzle of next difficulty level
    public Puzzle getFirstPuzzleOfNextNiveau(Niveau current) {
        Niveau next = getNextNiveau(current);
        if (next == null) return null;
        List<Puzzle> puzzles = puzzleRepository.findByNiveau(next);
        return puzzles.isEmpty() ? null : puzzles.get(0);
    }
    // 🔹 Optional: get next difficulty level
    public Niveau getNextNiveau(Niveau current) {
        switch (current) {
            case FACILE:
                return Niveau.MOYEN;
            case MOYEN:
                return Niveau.DIFFICILE;
            default:
                return null;
        }
    }
    
 // 🔹 Get starting score based on level
    public int getScoreDepart(Niveau niveau) {
        switch (niveau) {
            case FACILE:    return 1000;
            case MOYEN:     return 3000;
            case DIFFICILE: return 5000;
            default:        return 1000;
        }
    }

}