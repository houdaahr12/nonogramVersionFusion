package com.nonogram.controller;

import com.nonogram.model.Joueur;
import com.nonogram.service.JoueurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JoueurService joueurService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @RequestBody Map<String, String> body,
            HttpSession session) {

        String pseudo   = body.get("pseudo");
        String password = body.get("password");
        String result   = joueurService.register(pseudo, password);

        if (result.equals("OK")) {
            // ← sauvegarde dans la session serveur
            session.setAttribute("pseudo", pseudo);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Compte créé !",
                "pseudo", pseudo
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", result
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Map<String, String> body,
            HttpSession session) {

        String pseudo   = body.get("pseudo");
        String password = body.get("password");
        Joueur joueur   = joueurService.login(pseudo, password);

        if (joueur != null) {
            // ← sauvegarde dans la session serveur
            session.setAttribute("pseudo", joueur.getPseudo());
            session.setAttribute("joueurId", joueur.getId());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Connecté !",
                "pseudo", joueur.getPseudo(),
                "score", joueur.getMeilleurScore()
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "Pseudo ou mot de passe incorrect"
            ));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate(); 
        return ResponseEntity.ok(Map.of("status", "success"));
    }
}