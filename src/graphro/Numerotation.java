/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphro;

import java.util.*;

/**
 * Numérotation des graphes
 *
 * @author FMorain (morain@lix.polytechnique.fr)
 * @author PChassignet (chassign@lix.polytechnique.fr)
 * @version 2006.11.22
 */

public class Numerotation {

    private int compteur;
    private Hashtable<Sommet, Integer> HSI;
    private Vector<Sommet> VS;

    public Numerotation(int n) {
        compteur = -1;
        HSI = new Hashtable<Sommet, Integer>();
        VS = new Vector<Sommet>(n);
        VS.setSize(n);
    }

    public int taille() {
        return VS.size();
    }

    public boolean ajouterElement(Sommet s) {
        if (!HSI.containsKey(s)) {
            compteur++;
            HSI.put(s, compteur);
            VS.set(compteur, s);
            return true;
        }
        return false;
    }

    public int numero(Sommet s) {
        return HSI.get(s);
    }

    public Sommet elementAt(int i) {
        return VS.elementAt(i);
    }

    public Collection<Sommet> elements() {
        return VS;
    }
}
