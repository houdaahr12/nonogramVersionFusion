package com.nonogram.controller;

import com.nonogram.model.Puzzle;
import com.nonogram.model.enums.Niveau;
import com.nonogram.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {

    @Autowired
    private GameService gameService;

    // GET all puzzles
    @GetMapping
    public ResponseEntity<List<Puzzle>> getAllPuzzles() {
        return ResponseEntity.ok(gameService.getAllPuzzles());
    }

    // GET puzzle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Puzzle> getPuzzleById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getPuzzleById(id));
    }

    // GET puzzles by level
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Puzzle>> getByNiveau(@PathVariable Niveau niveau) {
        return ResponseEntity.ok(gameService.getPuzzlesByNiveau(niveau));
    }

    // GET one random puzzle by level
    @GetMapping("/random/{niveau}")
    public ResponseEntity<Puzzle> getRandomByNiveau(@PathVariable Niveau niveau) {
        return ResponseEntity.ok(gameService.getOnePuzzleByNiveau(niveau));
    }
}