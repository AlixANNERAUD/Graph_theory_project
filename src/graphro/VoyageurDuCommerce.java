package graphro;

import java.util.HashMap;

public class VoyageurDuCommerce {
    
    private GrapheMatrice graphe;
    private int etiquettes[];

    public VoyageurDuCommerce(GrapheMatrice graphe, int etiquettes[]) {
        this.graphe = graphe;
        if (this.etiquettes.length != graphe.taille()) {
            throw new IllegalArgumentException("Le nombre d'étiquettes ne correspond pas au nombre de sommets du graphe");
        }
        this.etiquettes = etiquettes;
    }

    

}
