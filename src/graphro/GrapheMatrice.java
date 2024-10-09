/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphro;

import java.io.*;
import java.util.*;

/**
 * Graphes implantés dans des "matrices"
 *
 * @author FMorain (morain@lix.polytechnique.fr)
 * @version 2007.01.12
 */
public class GrapheMatrice extends Graphe {

    private Vector<Vector<Arc>> M;
    private Numerotation numerotation;

    public int taille() {
        return M.size();
    }

    public GrapheMatrice(int n) {
        numerotation = new Numerotation(n);
        M = new Vector<Vector<Arc>>(n);
        M.setSize(n);
    }

    public void ajouterSommet(Sommet s) {
        if (numerotation.ajouterElement(s)) {
            int n = taille();
            Vector<Arc> vs = new Vector<Arc>(n);
            vs.setSize(n);
            M.set(numerotation.numero(s), vs);
        }
    }

    public boolean existeArc(Sommet s, Sommet t) {
        int si = numerotation.numero(s);
        int ti = numerotation.numero(t);
        return M.get(si).get(ti) != null;
    }

    private boolean existeArc(int i, int j) {
        return M.get(i).get(j) != null;
    }

    public void ajouterArc(Sommet s, Sommet t, int val) {
        ajouterSommet(s);
        ajouterSommet(t);
        int si = numerotation.numero(s);
        int ti = numerotation.numero(t);
        M.get(si).set(ti, new Arc(s, t, val));
    }

    public int valeurArc(Sommet s, Sommet t) {
        int si = numerotation.numero(s);
        int ti = numerotation.numero(t);
        return M.get(si).get(ti).valeur();
    }

    private int valeurArc(int i, int j) {
        return M.get(i).get(j).valeur();
    }

    public void enleverArc(Sommet s, Sommet t) {
        int si = numerotation.numero(s);
        int ti = numerotation.numero(t);
        M.get(si).remove(ti);
    }

    public void modifierValeur(Sommet s, Sommet t, int val) {
        int si = numerotation.numero(s);
        int ti = numerotation.numero(t);
        M.get(si).get(ti).modifierValeur(val);
    }

    public LinkedList<Arc> voisins(Sommet s) {
        LinkedList<Arc> l = new LinkedList<Arc>();
        int si = numerotation.numero(s);
        for (int j = 0; j < taille(); j++) {
            if (existeArc(si, j)) {
                l.addLast(M.get(si).get(j));
            }
        }
        return l;
    }
    
    /* GDM*/
    public LinkedList<Arc> voisinsPred(Sommet s) {
        LinkedList<Arc> l = new LinkedList<Arc>();
        int si = numerotation.numero(s);
        for (int j = 0; j < taille(); j++) {
            if (existeArc(j, si)) {
                l.addLast(M.get(j).get(si));
            }
        }
        return l;
    }

    public int degre(Sommet s){
        return  voisins(s).size();
        
    }
    
    /* GDM*/
    public LinkedList<Sommet> successeurs(Sommet s){
        
        LinkedList<Sommet> l = new LinkedList<Sommet>();
        LinkedList<Arc> la = new LinkedList<Arc>();
        la = voisins(s);
        
        for (int i = 0; i < la.size() ; i++){
            l.add(la.get(i).destination());         
        }
        return l;
    }
    
    /* GDM*/
    public LinkedList<Sommet> predecesseurs(Sommet s){
        
        LinkedList<Sommet> l = new LinkedList<Sommet>();
        LinkedList<Arc> la = new LinkedList<Arc>();
        la = voisinsPred(s);
        
        for (int i = 0; i < la.size() ; i++){
            l.add(la.get(i).origine());         
        }
        return l;
    }

    public Collection<Sommet> sommets() {
        return numerotation.elements();
    }

    public GrapheMatrice copie() {
        int n = taille();
        GrapheMatrice G = new GrapheMatrice(n);
        for (int i = 0; i < n; i++) {
            G.ajouterSommet(numerotation.elementAt(i));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M.get(i).get(j) != null) {
                    G.ajouterArc(numerotation.elementAt(i),
                            numerotation.elementAt(j),
                            valeurArc(i, j));
                }
            }
        }
        return G;
    }

    public static GrapheMatrice deMatrice(int[][] M) {
        int n = M.length;
        GrapheMatrice G = new GrapheMatrice(n);
        for (int i = 0; i < n; i++) {
            G.ajouterSommet(new Sommet(i + "", 0));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < M[i].length; j++) {
                if (M[i][j] == 1) {
                    G.ajouterArc(G.numerotation.elementAt(i),
                            G.numerotation.elementAt(j),
                            1);
                }
            }
        }
        return G;
    }
    
 public int flotMaxFordF(Graphe g){
     throw new UnsupportedOperationException("Not supported yet.");
 }

    public String toString() {  
        return M.toString();
    }
}
