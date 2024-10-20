package graphro;

public class Arc {

    private Sommet destination;
    private int cout;

    public Arc(Sommet destination, int cout) {
        this.destination = destination;
        this.cout = cout;
    }

    public Sommet getDestination() {
        return destination;
    }

    public int getCout() {
        return cout;
    }
}
