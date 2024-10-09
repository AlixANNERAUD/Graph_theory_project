/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphro;

/*    Classe de Sommets
@author FMorain (morain@lix.polytechnique.fr)
@author PChassignet (chassign@lix.polytechnique.fr)
@version 2006.11.27
 */
public class Sommet {

    String nom;
    private int marque;

    public Sommet(String nn, int mm) {
        nom = nn;
        marque = mm;
    }

    public Sommet(Sommet s, int mm) {
        nom = s.nom;
        marque = mm;
    }

    public int valeurMarque() {
        return marque;
    }

    public void modifierMarque(int m) {
        marque = m;
    }

    public boolean equals(Object o) {
        return nom.equals(((Sommet) o).nom);
    }

    public int compareTo(Object o) {
        Sommet s = (Sommet) o;
        return nom.compareTo(s.nom);
    }

    public String toString() {
        return "" + nom;
    }

    public int hashCode() {
        return nom.hashCode();
    }
}
