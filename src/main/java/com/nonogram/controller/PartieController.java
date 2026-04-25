package com.nonogram.controller;

import com.nonogram.model.Joueur;
import com.nonogram.model.Partie;
import com.nonogram.model.Puzzle;
import com.nonogram.service.GameService;
import com.nonogram.service.JoueurService;
import com.nonogram.service.PartieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/partie")
public class PartieController {

    @Autowired
    private PartieService partieService;

    @Autowired
    private GameService gameService;

    @Autowired
    private JoueurService joueurService;

    // ── Start new game ────────────────────────────────────────
    @PostMapping("/nouvelle")
    public ResponseEntity<Map<String, Object>> nouvelle(
            @RequestBody Map<String, Long> body,
            HttpSession session) {

        // Check logged in
        String pseudo = (String) session.getAttribute("pseudo");
        if (pseudo == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Not logged in"));
        }

        Long puzzleId = body.get("puzzleId");
        Joueur joueur = joueurService.findByPseudo(pseudo);
        Puzzle puzzle = gameService.getPuzzleById(puzzleId);

        Partie partie = partieService.creerPartie(joueur, puzzle);

        return ResponseEntity.ok(Map.of(
            "partieId",     partie.getId(),
            "scoreActuel",  partie.getScoreActuel(),
            "grille",       partie.getGrilleCourante()
        ));
    }

    // ── Update grid (called on every cell click) ──────────────
    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> update(
            @RequestBody Map<String, Object> body,
            HttpSession session) {

        if (session.getAttribute("pseudo") == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        Long   partieId       = Long.valueOf(body.get("partieId").toString());
        String grilleCourante = body.get("grilleCourante").toString();
        int    scoreActuel    = Integer.parseInt(body.get("scoreActuel").toString());
        int    nbErreurs      = Integer.parseInt(body.get("nbErreurs").toString());    // ✅
        int    tempsEcoule    = Integer.parseInt(body.get("tempsEcoule").toString());  // ✅

        partieService.updateGrille(partieId, grilleCourante,
                                   scoreActuel, nbErreurs, tempsEcoule);

        return ResponseEntity.ok(Map.of("status", "updated"));
    }

    // ── Save game ─────────────────────────────────────────────
    @PostMapping("/sauvegarder")
    public ResponseEntity<Map<String, String>> sauvegarder(
            @RequestBody Map<String, Object> body,
            HttpSession session) {

        if (session.getAttribute("pseudo") == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        Long   partieId      = Long.valueOf(body.get("partieId").toString());
        String grilleCourante = body.get("grilleCourante").toString();
        int scoreActuel = Integer.parseInt(body.get("scoreActuel").toString());
        int    nbErreurs     = (int) body.get("nbErreurs");
        int    tempsEcoule   = (int) body.get("tempsEcoule");

        partieService.sauvegarder(partieId, grilleCourante,
                                  scoreActuel, nbErreurs, tempsEcoule);

        return ResponseEntity.ok(Map.of("status", "saved"));
    }

    // ── Quit game ─────────────────────────────────────────────
    @PostMapping("/quitter")
    public ResponseEntity<Map<String, String>> quitter(
            @RequestBody Map<String, Long> body,
            HttpSession session) {

        if (session.getAttribute("pseudo") == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        partieService.quitter(body.get("partieId"));
        return ResponseEntity.ok(Map.of("status", "quit"));
    }
    
 // ── Win: player solved the puzzle ────────────────────────
    @PostMapping("/terminer")
    public ResponseEntity<Map<String, String>> terminer(
            @RequestBody Map<String, Object> body,
            HttpSession session) {

        if (session.getAttribute("pseudo") == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        Long partieId  = Long.valueOf(body.get("partieId").toString());
        int  scoreFinal = Integer.parseInt(body.get("scoreFinal").toString());

        partieService.terminer(partieId, scoreFinal);
        return ResponseEntity.ok(Map.of("status", "gagne"));
    }

    // ── Game over: too many errors ────────────────────────────
    @PostMapping("/gameover")
    public ResponseEntity<Map<String, String>> gameOver(
            @RequestBody Map<String, Object> body,
            HttpSession session) {

        if (session.getAttribute("pseudo") == null)
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in"));

        Long partieId = Long.valueOf(body.get("partieId").toString());

        partieService.gameOver(partieId);
        return ResponseEntity.ok(Map.of("status", "gameover"));
    }
}