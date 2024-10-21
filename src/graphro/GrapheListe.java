package graphro;

import java.io.*;
import java.util.*;

public class GrapheListe {

    private Map<Sommet, List<Arc>> adjacence;

    public GrapheListe() {
        adjacence = new HashMap<>();
    }

    public GrapheListe(String nomFichier) throws IOException {
        adjacence = new HashMap<>();
        lireGrapheDepuisFichier(nomFichier);
    }

    public void ajouterSommet(Sommet s) {
        if (!adjacence.containsKey(s)) {
            adjacence.put(s, new ArrayList<>());
        }
    }

    public void ajouterArc(Sommet source, Sommet destination, int cout) {
        ajouterSommet(source);
        ajouterSommet(destination);
        adjacence.get(source).add(new Arc(destination, cout));
    }

    // Assurez-vous que cette méthode est déclarée 'public'
    public List<Arc> getAdjacents(Sommet s) {
        return adjacence.getOrDefault(s, new ArrayList<>());
    }

    // Assurez-vous que cette méthode est déclarée 'public'
    public Collection<Sommet> sommets() {
        return adjacence.keySet();
    }

    public ResultatChemin plusCourtChemin(Sommet source, Sommet destination) {
        Map<Sommet, Integer> distances = new HashMap<>();
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();
        PriorityQueue<SommetDistance> queue = new PriorityQueue<>();
        Set<Sommet> visites = new HashSet<>();

        for (Sommet sommet : sommets()) {
            distances.put(sommet, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(new SommetDistance(source, 0));

        while (!queue.isEmpty()) {
            SommetDistance sdCourant = queue.poll();
            Sommet courant = sdCourant.sommet;

            if (visites.contains(courant))
                continue;
            visites.add(courant);

            if (courant.equals(destination)) {
                // Reconstruire le chemin
                List<Sommet> chemin = new ArrayList<>();
                Sommet actuel = destination;
                while (actuel != null) {
                    chemin.add(actuel);
                    actuel = predecesseurs.get(actuel);
                }
                Collections.reverse(chemin);
                return new ResultatChemin(distances.get(destination), chemin);
            }

            for (Arc arc : getAdjacents(courant)) {
                Sommet voisin = arc.getDestination();
                int nouveauCout = distances.get(courant) + arc.getCout();
                if (nouveauCout < distances.get(voisin)) {
                    distances.put(voisin, nouveauCout);
                    predecesseurs.put(voisin, courant);
                    queue.add(new SommetDistance(voisin, nouveauCout));
                }
            }
        }

        return new ResultatChemin(Integer.MAX_VALUE, null);
    }

    public void lireGrapheDepuisFichier(String nomFichier) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomFichier));
        String ligne;
        Map<String, Sommet> mapSommets = new HashMap<>();

        while ((ligne = br.readLine()) != null) {
            ligne = ligne.trim();
            if (ligne.isEmpty() || ligne.startsWith("#")) {
                continue; // Ignorer les lignes vides ou les commentaires
            }

            String[] tokens = ligne.split("\\s+");
            if (tokens[0].equals("V")) {
                // Définition d'un sommet
                String nomSommet = tokens[1];
                boolean estIntersection = Boolean.parseBoolean(tokens[2]);
                Sommet sommet = new Sommet(nomSommet, 0, estIntersection);
                this.ajouterSommet(sommet);
                mapSommets.put(nomSommet, sommet);
            } else if (tokens[0].equals("E")) {
                // Définition d'une arête
                String sourceNom = tokens[1];
                String destinationNom = tokens[2];
                int cout = Integer.parseInt(tokens[3]);

                Sommet source = mapSommets.get(sourceNom);
                Sommet destination = mapSommets.get(destinationNom);

                if (source == null || destination == null) {
                    throw new IllegalArgumentException("Sommet non défini pour l'arête : " + ligne);
                }

                this.ajouterArc(source, destination, cout);
            } else {
                throw new IllegalArgumentException("Ligne invalide dans le fichier : " + ligne);
            }
        }

        br.close();
    }

    private class SommetDistance implements Comparable<SommetDistance> {
        Sommet sommet;
        int distance;

        public SommetDistance(Sommet sommet, int distance) {
            this.sommet = sommet;
            this.distance = distance;
        }

        @Override
        public int compareTo(SommetDistance other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
