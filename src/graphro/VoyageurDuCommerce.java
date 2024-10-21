package graphro;

import java.util.*;

public class VoyageurDuCommerce {

    private GrapheListe graphe;
    private Map<String, ResultatChemin> cachePlusCourtsChemins;
    private GrapheListe grapheCompletAdresses;

    public VoyageurDuCommerce(GrapheListe graphe) {
        this.graphe = graphe;
        this.cachePlusCourtsChemins = new HashMap<>();
    }

    public List<Sommet> algoOptimisation(Sommet depart) {
        List<Sommet> adresses = obtenirPointsLivraison(depart);

        // Créer le graphe complet des adresses
        creerGrapheCompletDesAdresses(adresses);

        // Initialiser le tour sur le graphe complet des adresses
        List<Sommet> tour = initialiserTour(adresses, depart);

        // Appliquer l'algorithme 2-opt sur le graphe complet des adresses
        boolean amelioration;
        int meilleurCout = calculerCoutTour(tour);
        do {
            amelioration = false;
            int tailleTour = tour.size();

            for (int i = 1; i < tailleTour - 2; i++) {
                for (int j = i + 1; j < tailleTour - 1; j++) {
                    List<Sommet> nouveauTour = deuxOptEchange(tour, i, j);
                    int nouveauCout = calculerCoutTour(nouveauTour);

                    if (nouveauCout < meilleurCout) {
                        tour = nouveauTour;
                        meilleurCout = nouveauCout;
                        amelioration = true;
                    }
                }
            }
        } while (amelioration);

        // Reconstituer le chemin complet dans le graphe original
        List<Sommet> cheminComplet = new ArrayList<>();

        for (int i = 0; i < tour.size() - 1; i++) {
            Sommet s1 = tour.get(i);
            Sommet s2 = tour.get(i + 1);
            ResultatChemin resultat = obtenirResultatChemin(s1, s2);
            List<Sommet> chemin = resultat.getChemin();

            if (chemin == null) {
                throw new IllegalStateException("Aucun chemin trouvé entre " + s1.nom + " et " + s2.nom);
            }

            // Ajouter le chemin sans répéter les sommets
            for (Sommet s : chemin) {
                if (!cheminComplet.isEmpty() && s.equals(cheminComplet.get(cheminComplet.size() - 1))) {
                    continue;
                }
                cheminComplet.add(s);
            }
        }

        return cheminComplet;
    }

    private void creerGrapheCompletDesAdresses(List<Sommet> adresses) {
        grapheCompletAdresses = new GrapheListe();
        cachePlusCourtsChemins = new HashMap<>();
        for (Sommet adresse1 : adresses) {
            for (Sommet adresse2 : adresses) {
                if (!adresse1.equals(adresse2)) {
                    String cle = adresse1.nom + "-" + adresse2.nom;
                    ResultatChemin resultat;
                    if (cachePlusCourtsChemins.containsKey(cle)) {
                        resultat = cachePlusCourtsChemins.get(cle);
                    } else {
                        resultat = graphe.plusCourtChemin(adresse1, adresse2);
                        cachePlusCourtsChemins.put(cle, resultat);
                    }
                    grapheCompletAdresses.ajouterArc(adresse1, adresse2, resultat.getCout());
                }
            }
        }
    }

    private List<Sommet> obtenirPointsLivraison(Sommet depart) {
        List<Sommet> pointsLivraison = new ArrayList<>();
        for (Sommet s : graphe.sommets()) {
            if (!s.estIntersection()) {
                pointsLivraison.add(s);
            }
        }
        // S'assurer que le point de départ est le premier de la liste
        if (!pointsLivraison.contains(depart)) {
            pointsLivraison.add(0, depart);
        } else {
            pointsLivraison.remove(depart);
            pointsLivraison.add(0, depart);
        }
        return pointsLivraison;
    }

    private List<Sommet> initialiserTour(List<Sommet> pointsLivraison, Sommet depart) {
        List<Sommet> tour = new ArrayList<>(pointsLivraison);
        tour.add(depart); // Retour au point de départ
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
            int cout = grapheCompletAdresses.getCoutArc(s1, s2);
            if (cout == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE; // Pas de chemin entre s1 et s2
            }
            coutTotal += cout;
        }
        return coutTotal;
    }

    private ResultatChemin obtenirResultatChemin(Sommet s1, Sommet s2) {
        String cle1 = s1.nom + "-" + s2.nom;
        String cle2 = s2.nom + "-" + s1.nom;
        if (cachePlusCourtsChemins.containsKey(cle1)) {
            return cachePlusCourtsChemins.get(cle1);
        } else if (cachePlusCourtsChemins.containsKey(cle2)) {
            return cachePlusCourtsChemins.get(cle2);
        } else { 
            ResultatChemin resultat = graphe.plusCourtChemin(s1, s2);
            cachePlusCourtsChemins.put(cle1, resultat);
            return resultat;
        }
    }
}
