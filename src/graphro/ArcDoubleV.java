/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphro;

/**
 *
 * @author gdelmondo
 */
public class ArcDoubleV {
    private Sommet o, d;
    private Couple c;

    public ArcDoubleV(Sommet o0, Sommet d0, int flux, int cap) {
        this.o = o0;
        this.d = d0;
        c.setFlux(flux);
        c.setCapacite(cap);
    }

    public ArcDoubleV(ArcDoubleV a) {
        this.o = a.o;
        this.d = a.d;
        c.setCapacite(a.capacite());
        c.setFlux(a.flux());
    }

    public Sommet destination() {
        return d;
    }

    public Sommet origine() {
        return o;
    }

    public int flux() {
        return c.getFlux();
    }

    public int capacite() {
        return c.getCapacite();
    }
    
    public void modifierValeurFlux(int vv) {
        c.setFlux(vv);
    }
    
    public void modifierValeurCapacite(int vv) {
        c.setCapacite(vv);
    }
    

    public boolean equals(Object aa) {
        ArcDoubleV a = (ArcDoubleV) aa;
        return o.equals(a.o) && d.equals(a.d) && (c.getFlux() == a.flux()) && (c.getCapacite() == a.capacite());
    }

    public String toString() {
        return "(" + this.o + ", " + this.d + ")";
    }

    public int hashCode() {
        String str = "" + this;
        return str.hashCode();
    }
}
