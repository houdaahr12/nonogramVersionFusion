package com.nonogram.service;

import com.nonogram.model.Joueur;
import com.nonogram.repository.JoueurRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoueurService {

    @Autowired
    private JoueurRepository joueurRepository;

    // 🔐 Ajoute cet encodeur BCrypt
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(String pseudo, String password) {
        if (pseudo == null || pseudo.trim().isEmpty())
            return "Pseudo invalide";
        if (password == null || password.length() < 3)
            return "Mot de passe trop court";
        if (joueurRepository.existsByPseudo(pseudo))
            return "Pseudo déjà utilisé";

        // 🔐 MODIFICATION ICI : Hasher le mot de passe AVANT de sauvegarder
        String hashedPassword = passwordEncoder.encode(password);
        
        // Utiliser le constructeur avec le mot de passe hashé
        Joueur nouveauJoueur = new Joueur(pseudo, hashedPassword);
        joueurRepository.save(nouveauJoueur);
        
        return "OK";
    }

    public Joueur login(String pseudo, String password) {
        Joueur joueur = joueurRepository.findByPseudo(pseudo);
        if (joueur == null) return null;
        
        // 🔐 MODIFICATION ICI : Comparer avec BCrypt, pas avec equals()
        if (!passwordEncoder.matches(password, joueur.getPassword())) return null;
        
        return joueur;
    }
    
    public Joueur findByPseudo(String pseudo) {
        return joueurRepository.findByPseudo(pseudo);
    }

    @Transactional
    public void save(Joueur joueur) {
        joueurRepository.save(joueur);   // ← add this
    }
    
    
}