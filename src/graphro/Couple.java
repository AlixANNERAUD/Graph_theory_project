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
public class Couple {
    private int flux  ;
    private int capacite  ;
    
    
    Couple(int f, int cap){
        this.flux = f;
        this.capacite = cap;
    }
    
    int getFlux(){
        return flux;
    }
    
    int getCapacite(){
        return capacite;   
    }
    
    void setFlux(int newF){
        flux = newF;
    }
    
    void setCapacite(int newC){
        capacite = newC;
    }
    
}
