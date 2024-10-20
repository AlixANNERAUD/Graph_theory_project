package graphro;

public class Sommet {

    String nom;
    private int marque;
    private boolean estIntersection;

    public Sommet(String nn, int mm, boolean estIntersection) {
        nom = nn;
        marque = mm;
        this.estIntersection = estIntersection;
    }

    public Sommet(Sommet s, int mm) {
        nom = s.nom;
        marque = mm;
        this.estIntersection = s.estIntersection;
    }

    public int valeurMarque() {
        return marque;
    }

    public void modifierMarque(int m) {
        marque = m;
    }

    public boolean estIntersection() {
        return estIntersection;
    }

    public void setEstIntersection(boolean estIntersection) {
        this.estIntersection = estIntersection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Sommet sommet = (Sommet) o;

        return nom.equals(sommet.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }

    @Override
    public String toString() {
        return nom;
    }
}
