/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphro;

import java.util.Vector;

/**
 *
 * 
 */
public class GraphROEleve {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GrapheListe graphe = GrapheListe.deFichier("./data/voyageur.txt");

        VoyageurDuCommerce vdc = new VoyageurDuCommerce(graphe);

        Vector<Sommet> cicle = vdc.algoOptimisation(graphe.sommets().iterator().next());
        System.out.println("Ciclo: " + cicle);
    }
}
