/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphro;

import java.io.*;
import java.util.*;

/**
 * Graphes implantés dans des "listes"
 *
 * @author FMorain (morain@lix.polytechnique.fr)
 * @version 2007.01.30 [propagation modifs de Arc]
 */
public class GrapheListe extends Graphe {

    private Vector<LinkedList<Arc>> L;
    private Numerotation numerotation;

    public int taille() {
        return L.size();
    }

    public GrapheListe(int n) {
        numerotation = new Numerotation(n);
        L = new Vector<LinkedList<Arc>>(n);
        L.setSize(n);
    }

    public void ajouterSommet(Sommet s) {
        if (numerotation.ajouterElement(s)) {
            L.set(numerotation.numero(s), new LinkedList<Arc>());
        }
    }

    public boolean existeArc(Sommet s, Sommet t) {
        for (Arc a : L.get(numerotation.numero(s))) {
            if ((a.destination()).equals(t)) {
                return true;
            }
        }
        return false;
    }

    private boolean existeArc(int i, int j) {
        Sommet t = numerotation.elementAt(j);
        for (Arc a : L.get(i)) {
            if (a.destination().equals(t)) {
                return true;
            }
        }
        return false;
    }

    public void ajouterArc(Sommet s, Sommet t, int val) {
        ajouterSommet(s);

        ajouterSommet(t);
        int si = numerotation.numero(s);
        L.get(si).addLast(new Arc(s, t, val));
    }

    public void ajouterArc(int i, int j, int val) {
        L.get(i).addLast(new Arc(numerotation.elementAt(i),
                numerotation.elementAt(j),
                val));
    }

    public int valeurArc(Sommet s, Sommet t) {
        for (Arc a : L.get(numerotation.numero(s))) {
            if (a.destination().equals(t)) {
                return a.valeur();
            }
        }
        return -1; // convention
    }

    public int valeurArc(int i, int j) {
        Sommet t = numerotation.elementAt(j);
        for (Arc a : L.get(i)) {
            if (a.destination().equals(t)) {
                return a.valeur();
            }
        }
        return -1; // convention
    }

    public void enleverArc(Sommet s, Sommet t) {
        int si = numerotation.numero(s);
        Arc a = null;
        for (Arc aa : L.get(numerotation.numero(s))) {
            if (aa.destination().equals(t)) {
                a = aa;
                break;
            }
        }
        if (a != null) {
            L.get(numerotation.numero(s)).remove(a);
        }
    }

    public void modifierValeur(Sommet s, Sommet t, int val) {
        for (Arc a : L.get(numerotation.numero(s))) {
            if (a.destination().equals(t)) {
                a.modifierValeur(val);
                return;
            }
        }
    }

    
    public LinkedList<Arc> voisins(Sommet s) {
        return L.get(numerotation.numero(s));
    }

    public int degre(Sommet s) {
        return voisins(s).size();
    }

    
    public Collection<Sommet> sommets() {
        return numerotation.elements();
    }

    public GrapheListe copie() {
        int n = taille();
        GrapheListe G = new GrapheListe(n);
        for (int i = 0; i < n; i++) {
            G.ajouterSommet(numerotation.elementAt(i));
        }
        for (int i = 0; i < n; i++) {
// recopie dans le même ordre
            LinkedList<Arc> Li = G.L.get(i);
            for (Arc a : L.get(i)) {
                Li.addLast(a);
            }
        }
        return G;
    }
// retourne vrai si le caractère c est dans str

    private static boolean option(String str, char c) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    public static GrapheListe deFichier(String nomfic) {
        try {
            Scanner scan
                    = new Scanner(
                            new BufferedReader(new FileReader(nomfic)));
            System.out.println(scan.next());
            int n = scan.nextInt();
            GrapheListe G = new GrapheListe(n);
            String str = scan.next();
            boolean estValue = option(str, 'v');
            boolean estSym = option(str, 's');
            boolean avecCouples = option(str, 'c');

            System.out.println("nb noeuds = " + n);
            for (int i = 0; i < n; i++) {
                Sommet s = new Sommet(scan.next(), 0);
                G.ajouterSommet(s);
            }
            if (avecCouples) {
              //  System.out.println("Avec couples");
// on lit des lignes "i j dij" ou "i j"
                while (scan.hasNext()) {
                    Sommet s = new Sommet(scan.next(), 0);
                    Sommet t = new Sommet(scan.next(), 0);
                    int si = G.numerotation.numero(s);
                    int ti = G.numerotation.numero(t);
                    if (estValue) {
                        G.ajouterArc(si, ti, (int) scan.nextInt());
                    } else {
                        G.ajouterArc(si, ti, 1);
                    }
                }
            } else {
// format "s ns t0 t1 ... t{ns-1}"
// ou "s ns t0 v0 t1 v1 ... t{ns-1} v{ns-1}"
               // System.out.println("Avec listes, estvalue=" + estValue);
                for (int r = 0; r < n; r++) {
                    Sommet s = new Sommet(scan.next(), 0);
                    int si = G.numerotation.numero(s);
                    int nj = (int) scan.nextInt();
                    for (int k = 0; k < nj; k++) {
                        Sommet t = new Sommet(scan.next(), 0);
                        int ti = G.numerotation.numero(t);
                        if (estValue) {
                            G.ajouterArc(si, ti,
                                    (int) scan.nextInt());
                        } else {
                            G.ajouterArc(si, ti, 1);
                        }
                    }
                }
            }

            //System.out.println("G=" + G);
            if (estSym) // on doit symétriser G
            {
                for (Sommet s : G.sommets()) {
                    for (Sommet t : G.sommets()) {
                        if (G.existeArc(s, t)
                                && !G.existeArc(s, t)) {
                            G.ajouterArc(s, t,
                                    G.valeurArc(s, t));
                        }
                    }
                }
            }
            return G;
        } catch (Exception e) {
        }
        return null;
    }
    
    public int flotMaxFordF(Graphe g){
        
      throw new UnsupportedOperationException("Not supported yet.");  
        
    }
    
    
public String toString() {
        return L.toString();
    }
}
