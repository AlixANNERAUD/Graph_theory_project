package graphro;

import java.util.*;

public class VoyageurDuCommerce {

    private GrapheListe graphe;
    private Map<String, Integer> cachePlusCourtsChemins;

    public VoyageurDuCommerce(GrapheListe graphe) {
        this.graphe = graphe;
        this.cachePlusCourtsChemins = new HashMap<>();
    }

    public List<Sommet> algoOptimisation(Sommet depart) {
        List<Sommet> pointsLivraison = obtenirPointsLivraison(depart);
        List<Sommet> tour = initialiserTour(pointsLivraison, depart);

        boolean amelioration;
        do {
            amelioration = false;
            int tailleTour = tour.size();

            for (int i = 1; i < tailleTour - 2; i++) {
                for (int j = i + 1; j < tailleTour - 1; j++) {
                    List<Sommet> nouveauTour = deuxOptEchange(tour, i, j);
                    if (calculerCoutTour(nouveauTour) < calculerCoutTour(tour)) {
                        tour = nouveauTour;
                        amelioration = true;
                    }
                }
            }
        } while (amelioration);

        return tour;
    }

    private List<Sommet> obtenirPointsLivraison(Sommet depart) {
        List<Sommet> pointsLivraison = new ArrayList<>();
        for (Sommet s : graphe.sommets()) {
            if (!s.equals(depart) && !s.estIntersection()) {
                pointsLivraison.add(s);
            }
        }
        return pointsLivraison;
    }

    private List<Sommet> initialiserTour(List<Sommet> pointsLivraison, Sommet depart) {
        List<Sommet> tour = new ArrayList<>();
        tour.add(depart);
        tour.addAll(pointsLivraison);
        tour.add(depart);
        return tour;
    }

    private List<Sommet> deuxOptEchange(List<Sommet> tour, int i, int j) {
        List<Sommet> nouveauTour = new ArrayList<>(tour.subList(0, i));
        List<Sommet> sousListe = new ArrayList<>(tour.subList(i, j + 1));
        Collections.reverse(sousListe);
        nouveauTour.addAll(sousListe);
        nouveauTour.addAll(tour.subList(j + 1, tour.size()));
        return nouveauTour;
    }

    private int calculerCoutTour(List<Sommet> tour) {
        int coutTotal = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            Sommet s1 = tour.get(i);
            Sommet s2 = tour.get(i + 1);
            int cout = obtenirCoutPlusCourtChemin(s1, s2);
            if (cout == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE; // Pas de chemin entre s1 et s2
            }
            coutTotal += cout;
        }
        return coutTotal;
    }

    private int obtenirCoutPlusCourtChemin(Sommet s1, Sommet s2) {
        String cle_1 = s1.nom + "-" + s2.nom;
        String cle_2 = s2.nom + "-" + s1.nom;
        if (cachePlusCourtsChemins.containsKey(cle_1)) {
            return cachePlusCourtsChemins.get(cle_1);
        } else if (cachePlusCourtsChemins.containsKey(cle_2)) {
            return cachePlusCourtsChemins.get(cle_2);
        } else {
            int cout = graphe.plusCourtChemin(s1, s2);
            cachePlusCourtsChemins.put(cle_1, cout);
            return cout;
        }
    }
}
