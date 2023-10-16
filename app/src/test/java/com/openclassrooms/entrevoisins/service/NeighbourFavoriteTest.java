package com.openclassrooms.entrevoisins.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourFavoriteTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    //verifier si l'ajout au favoris fonctionne
    @Test
    public void addFavorite() {
        //on choisi un voisin
        Neighbour selectedNeighbour = service.getNeighbour("0");
        // on ajoute le voisin en favoris
        selectedNeighbour.addFavorite();
        // on vérifie si is favorite est a true
        boolean isFavorite = selectedNeighbour.getFavorite();
        // comparer ici
        assertTrue(isFavorite);
    }

    //verifier si la suppression des favoris fonctionne
    @Test
    public void removeFavorite() {
        //on choisi un voisin
        Neighbour selectedNeighbour = service.getNeighbour("0");
        // on ajoute le voisin en favoris
        selectedNeighbour.removeFavorite();
        // on vérifie si is favorite est a true
        assertFalse(selectedNeighbour.getFavorite());
    }
    @Test
    public void favoriteListWork(){
        //on choisi un voisin
        Neighbour selectedNeighbour = service.getNeighbour("0");
        // on ajoute un voisin en favoris
        selectedNeighbour.addFavorite();
        // on récupére la liste des favoris
        List<Neighbour> favoriteNeighbours = service.getAllFavorite();
        // on verifie que la liste n'est pas vide
        assertFalse(favoriteNeighbours.isEmpty());
        // on verifie que le voisin ajouté est le bon
        assertThat(favoriteNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(selectedNeighbour));
        // on le retire le voisin des favoris
        selectedNeighbour.removeFavorite();
        //on met a jours la liste des favoris
        favoriteNeighbours = service.getAllFavorite();
        //on verifie que la liste est vide
        assertTrue(favoriteNeighbours.isEmpty());
    }
}
