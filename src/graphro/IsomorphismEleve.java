package graphro;
import java.util.*;
public class IsomorphismEleve{

    public static class PairOfNodes {
	    public Sommet u;
	    public Sommet v;
	}
    
    /* Méthode utilisée pour l'étape 5 */
    public static Boolean isIsomorphicDegre(Graphe g1, Graphe g2){
        List<Integer> degre_V1 = new ArrayList<Integer>();
        List<Integer> degre_V2 = new ArrayList<Integer>();
        
        for(Sommet v1 :g1.sommets()){
            degre_V1.add(g1.degre(v1));
        }
        for(Sommet v2 :g2.sommets()){
            degre_V2.add(g2.degre(v2));
        }
        if (degre_V1.size() != degre_V2.size()){
            return false;
        }
        Collections.sort(degre_V1);
        Collections.sort(degre_V2);
        for (int i=0; i<degre_V1.size();i++ ){
            if(degre_V1.get(i) != degre_V2.get(i)){
                return false;
            }
        }   
        return true;
    }
    
    /* Méthode utilisée pour l'étape 2 */
    public static Boolean isIsomorphic(Graphe g1, Graphe g2){
        Collection<Sommet> V1 = g1.sommets();
        Collection<Sommet> V2 = g2.sommets();
        Vector<PairOfNodes> partialMatching = new Vector<>();
        LinkedList<Sommet> unusedNodesV1 = new LinkedList<>(V1);
        LinkedList<Sommet> unusedNodesV2 = new LinkedList<>(V2);
        return isIsomorphicR(g1,g2,partialMatching,unusedNodesV1,unusedNodesV2);
    }

    

    private static Boolean isIsomorphicR(Graphe g1, Graphe g2,
                                         Vector<PairOfNodes> partialMatching,
                                         LinkedList<Sommet> unusedNodesV1,
                                         LinkedList<Sommet> unusedNodesV2){
              
        if (unusedNodesV1.isEmpty() && unusedNodesV2.isEmpty()){
            if(testMatching(g1,g2,partialMatching)){
                return true;
            }
            else
                return false;
        }

        Sommet nodeToMatch = unusedNodesV1.pollFirst();
            
        for(Sommet matchedNode :unusedNodesV2){
            PairOfNodes matcheds = new PairOfNodes();
			matcheds.u = nodeToMatch;
            matcheds.v = matchedNode;
            Vector<PairOfNodes> rPartialMatching;
            rPartialMatching = (Vector<PairOfNodes>)partialMatching.clone();
                
			rPartialMatching.add(matcheds);
			LinkedList<Sommet> rUnusedNodesV2;
			rUnusedNodesV2 = (LinkedList<Sommet>) unusedNodesV2.clone();
			rUnusedNodesV2.remove(matchedNode);

			LinkedList<Sommet> rUnusedNodesV1;
			rUnusedNodesV1 = (LinkedList<Sommet>) unusedNodesV1.clone();
			rUnusedNodesV1.remove(nodeToMatch);

			Boolean res = isIsomorphicR(g1,g2,rPartialMatching,rUnusedNodesV1,rUnusedNodesV2);
			if (res == true){
				return true;
			}
            
            
        }
        
        return false; //On a pas pu matcher nodeToMatch
    }
    private static Boolean testMatching(Graphe g1, Graphe g2,
                                        Vector<PairOfNodes> Matching){
        HashMap<Sommet,Sommet> phi = new HashMap<Sommet,Sommet>();
        HashMap<Sommet,Sommet> phiInv = new HashMap<Sommet,Sommet>();
        for (PairOfNodes p : Matching){
            phi.put(p.u,p.v);
            phiInv.put(p.v,p.u);
        }
        
        // test des edges de G1
        for (Sommet v1:g1.sommets()){
            for (Sommet v2:g1.sommets()){
                if(g1.existeArc(v1,v2)){
                    //Check si existe dans g2
                    if (!g2.existeArc(phi.get(v1), phi.get(v2))){
                        return false;
                    }
                }
            }
        }
        // test des edges de G2
        for (Sommet v1:g2.sommets()){
            for (Sommet v2:g2.sommets()){
                if(g2.existeArc(v1,v2)){
                    //Check si existe dans g1
                    if (!g1.existeArc(phiInv.get(v1), phiInv.get(v2))){
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
