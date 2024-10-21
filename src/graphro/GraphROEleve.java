/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphro;

import java.io.IOException;
import java.util.List;

/**
 *
 * 
 */
public class GraphROEleve {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Lecture du graphe depuis le fichier
            GrapheListe graphe = new GrapheListe("data/voyageur.txt");

            // Récupération du sommet de départ
            Sommet depart = null;
            for (Sommet s : graphe.sommets()) {
                if (s.nom.equals("Depot")) {
                    depart = s;
                    break;
                }
            }
            if (depart == null) {
                throw new IllegalArgumentException("Le sommet de départ 'A' n'a pas été trouvé dans le graphe.");
            }

            // Création de l'instance du voyageur de commerce
            VoyageurDuCommerce vdc = new VoyageurDuCommerce(graphe);

            // Calcul du tour optimal
            List<Sommet> cheminOptimal = vdc.algoOptimisation(depart);

            // Affichage du chemin optimal
            System.out.println("Chemin optimal : ");
            for (Sommet s : cheminOptimal) {
                System.out.print(s.nom + " -> ");
            }
            System.out.println("FIN");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
