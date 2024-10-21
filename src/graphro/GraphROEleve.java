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
            GrapheListe graphe = new GrapheListe("./data/voyageur.txt");

            // Récupération du sommet de départ
            Sommet depart = null;
            for (Sommet s : graphe.sommets()) {
                if (s.nom.equals("Depot")) {
                    depart = s;
                    break;
                }
            }
            if (depart == null) {
                throw new IllegalArgumentException("Le sommet de départ 'Depot' n'a pas été trouvé dans le graphe.");
            }

            VoyageurDuCommerce vdc = new VoyageurDuCommerce(graphe);

            List<Sommet> tourOptimal = vdc.algoOptimisation(depart);

            System.out.println("Tour optimal : ");
            for (Sommet s : tourOptimal) {
                System.out.print(s.nom + " -> ");
            }
            System.out.println("FIN");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
