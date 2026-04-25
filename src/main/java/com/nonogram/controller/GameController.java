package com.nonogram.controller;
import com.nonogram.model.Joueur;
import java.util.Map;
import com.nonogram.service.JoueurService;
import com.nonogram.service.PartieService;


import com.nonogram.model.Partie;
import com.nonogram.service.JoueurService;
import com.nonogram.service.PartieService;
import com.nonogram.model.Puzzle;
import com.nonogram.model.enums.Niveau;
import com.nonogram.service.GameService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private GameService gameService; 
    @Autowired
    private JoueurService joueurService;  

    @Autowired
    private PartieService partieService; 

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("pseudo") == null)
            return "redirect:/login";

        String pseudo = (String) session.getAttribute("pseudo");
        Joueur joueur = joueurService.findByPseudo(pseudo);
        List<Puzzle> puzzles = gameService.getAllPuzzles();

        Map<Integer, Long> savedPartieIds = new HashMap<>();
        for (Puzzle p : puzzles) {
            partieService.findSavedByJoueurAndPuzzle(joueur, (long) p.getId())
                    .ifPresent(partie -> savedPartieIds.put(p.getId(), partie.getId()));
        }

        // ↓ THIS is the fix
        long totalParties = partieService.countByJoueur(joueur);

        model.addAttribute("puzzles", puzzles);
        model.addAttribute("pseudo", pseudo);
        model.addAttribute("meilleurScore", joueur.getMeilleurScore());
        model.addAttribute("totalParties", totalParties); // ← NEW, live count from DB
        model.addAttribute("savedPartieIds", savedPartieIds);
        return "home";
    }

    @GetMapping("/resume/{partieId}")
    public String resume(@PathVariable("partieId") Long partieId, Model model, HttpSession session) {
        if (session.getAttribute("pseudo") == null)
            return "redirect:/login";

        Partie partie = partieService.findById(partieId)
                .orElse(null);
        if (partie == null) return "redirect:/home";

        // Resume: set back to EN_COURS
        partieService.reprendrePartie(partieId);

        model.addAttribute("puzzle", partie.getPuzzle());
        model.addAttribute("partieId", partie.getId());
        model.addAttribute("scoreDepart", partie.getScoreActuel());

        // FIX: pass the saved grid so the frontend can restore cell states
        model.addAttribute("grilleCourante", partie.getGrilleCourante());

        // FIX: pass saved error and time counters so the game resumes correctly
        model.addAttribute("nbErreurs", partie.getNbErreurs());
        model.addAttribute("tempsEcoule", partie.getTempsEcoule());

        // Player stats for the header
        String pseudo = (String) session.getAttribute("pseudo");
        Joueur joueur = joueurService.findByPseudo(pseudo);
        model.addAttribute("meilleurScore", joueur.getMeilleurScore());
        model.addAttribute("totalParties", partieService.countByJoueur(joueur)); 
        boolean hasNext = gameService.hasNextLevel(partie.getPuzzle().getNiveau());
        model.addAttribute("hasNextLevel", hasNext);
        if (hasNext) {
            Puzzle nextPuzzle = gameService.getFirstPuzzleOfNextNiveau(partie.getPuzzle().getNiveau());
            model.addAttribute("nextLevelId", nextPuzzle != null ? nextPuzzle.getId() : null);
        } else {
            model.addAttribute("nextLevelId", null);
        }

        return "game";
    }

    
    @GetMapping("/play/{id}")
    public String play(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("pseudo") == null)
            return "redirect:/login";

        Puzzle puzzle = gameService.getPuzzleById(id);
        if (puzzle == null) return "redirect:/home";

        // Get logged in player
        String pseudo = (String) session.getAttribute("pseudo");
        Joueur joueur = joueurService.findByPseudo(pseudo);

        // Create new Partie in DB
        Partie partie = partieService.creerPartie(joueur, puzzle);

        model.addAttribute("puzzle", puzzle);
        model.addAttribute("partieId", partie.getId());
        model.addAttribute("scoreDepart", partie.getScoreActuel());
        model.addAttribute("meilleurScore", joueur.getMeilleurScore());
        model.addAttribute("totalParties", joueur.getTotalParties());

        // For a fresh game: no saved grid, no errors, no elapsed time
        model.addAttribute("grilleCourante", null);
        model.addAttribute("nbErreurs", 0);
        model.addAttribute("tempsEcoule", 0);

        // Next level logic — only show button if not the last level (EXPERT)
        boolean hasNext = gameService.hasNextLevel(puzzle.getNiveau());
        model.addAttribute("hasNextLevel", hasNext);
        if (hasNext) {
            Puzzle nextPuzzle = gameService.getFirstPuzzleOfNextNiveau(puzzle.getNiveau());
            model.addAttribute("nextLevelId", nextPuzzle != null ? nextPuzzle.getId() : null);
        } else {
            model.addAttribute("nextLevelId", null);
        }

        return "game";
    }
    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}