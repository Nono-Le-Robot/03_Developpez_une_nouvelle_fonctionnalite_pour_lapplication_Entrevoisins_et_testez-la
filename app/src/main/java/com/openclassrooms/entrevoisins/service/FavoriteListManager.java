package com.openclassrooms.entrevoisins.service;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListManager {
    private static FavoriteListManager instance;
    private List<String> favoriteList = new ArrayList<>();

    private FavoriteListManager() {
        // Constructeur privé pour empêcher l'instanciation directe
    }

    public static synchronized FavoriteListManager getInstance() {
        if (instance == null) {
            instance = new FavoriteListManager();
        }
        return instance;
    }

    public void addFavorite(String userId) {
        favoriteList.add(userId);
    }

    public List<String> getFavoriteList() {
        return favoriteList;
    }

    public void removeFavorite(String userId) {
        favoriteList.remove(userId);
    }

    // Autres méthodes de gestion des favoris (comme supprimer, vérifier si un utilisateur est favori, etc.)
}
