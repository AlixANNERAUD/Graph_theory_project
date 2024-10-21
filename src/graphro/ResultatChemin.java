package graphro;

import java.util.List;

public class ResultatChemin {
    private int cout;
    private List<Sommet> chemin;

    public ResultatChemin(int cout, List<Sommet> chemin) {
        this.cout = cout;
        this.chemin = chemin;
    }

    public int getCout() {
        return cout;
    }

    public List<Sommet> getChemin() {
        return chemin;
    }
}
