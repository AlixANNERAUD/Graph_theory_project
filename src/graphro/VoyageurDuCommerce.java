package graphro;

import java.io.*;
import java.util.*;

public class VoyageurDuCommerce {

    private GrapheListe graphe;

    public VoyageurDuCommerce(GrapheListe graphe) {
        this.graphe = graphe;
    }

    public Vector<Sommet> algoOptimisation(Sommet depart) {
        Vector<Sommet> tour = initialiserTour(depart);

        boolean aucun_changement = true;

        do {
            aucun_changement = true;
            int tailleTour = tour.size();
            Vector<Sommet> meilleurTour = new Vector<>(tour);

            // Pour toutes les paires d’arêtes (i, j) du tour
            for (int i = 1; i < tailleTour - 1; i++) {
                for (int j = i + 1; j < tailleTour; j++) {

                    if (arcExisteEntre(tour, i, j)) {
                        // Génére un nouveau tour en échangeant deux arêtes
                        Vector<Sommet> nouveauTour = new Vector<>(tour);
                        inverserArcs(nouveauTour, i, j);

                        // 6. Comparer la longueur des tours
                        if (calculerCoutTour(nouveauTour) < calculerCoutTour(meilleurTour)) {
                            meilleurTour = nouveauTour;
                            aucun_changement = false;
                        }
                    }
                }
            }

            tour = meilleurTour;

        } while (!aucun_changement);

        return tour;
    }

    // Initialiser un tour en partant d'un sommet
    private Vector<Sommet> initialiserTour(Sommet depart) {
        Vector<Sommet> tour = new Vector<>();
        tour.add(depart);
        for (Sommet s : this.graphe.sommets()) {
            if (!s.equals(depart)) {
                tour.add(s);
            }
        }
        tour.add(depart); 
        return tour;
    }

    // Fonction pour inverser une section du tour
    private void inverserArcs(Vector<Sommet> tour, int i, int j) {
        while (i < j) {
            Sommet temp = tour.get(i);
            tour.set(i, tour.get(j));
            tour.set(j, temp);
            i++;
            j--;
        }
    }

    // Calculer le coût total d'un tour
    private int calculerCoutTour(Vector<Sommet> tour) {
        int coutTotal = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            Sommet s1 = tour.get(i);
            Sommet s2 = tour.get(i + 1);
            // Vérifier si l'arc entre s1 et s2 existe
            if (graphe.existeArc(s1, s2)) {
                coutTotal += this.graphe.valeurArc(s1, s2);
            } else {
                // Si une arête n'existe pas, on considère le tour comme invalide
                return Integer.MAX_VALUE;
            }
        }
        return coutTotal;
    }

    // Vérifier si les arêtes à inverser existent
    private boolean arcExisteEntre(Vector<Sommet> tour, int i, int j) {
        // Vérifier que les indices sont dans les limites du tour
        if (i <= 0 || j >= tour.size() - 1) {
            return false; // Si les indices ne sont pas valides, retourner false
        }

        Sommet s1 = tour.get(i - 1);
        Sommet s2 = tour.get(i);
        Sommet s3 = tour.get(j);
        Sommet s4 = tour.get(j + 1);

        // Vérifier que les arêtes (s1, s2) et (s3, s4) existent avant d'inverser
        return graphe.existeArc(s1, s2) && graphe.existeArc(s3, s4);
    }
}
