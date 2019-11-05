import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * @author Oussama, Lilya
 */
public class BFSAlgorithm {
    int [][] distance_matrice;
    int [][] npcc;
    Graphe graphe;
        
        
    public BFSAlgorithm(Graphe graphe) {
        this.graphe = graphe;
    }
    
    public void BFS (int u) {    	
    	String[] color = new String[graphe.getSommets().size()];
    	Deque<Integer> file = new LinkedList<>();
    	for(int i = 0; i < graphe.getSommets().size(); i++)  {
            color[i] =  "blanc";
    	}
    	file.add(u);
    	color[u] = "gris";
    	int s, dist;
    	while(!file.isEmpty()) {
            s = file.removeFirst();
            //System.out.println("sommet courant : " + s);
            npcc[u][s] = 0;
            npcc[s][u] = 0;
            for(Sommet tmp : graphe.getSommetByID(s).getAdjacence()) {
                String col = color[tmp.getId()];
                if(col.equals("blanc")) {
                    //si première visite ajouter à la file et changer la couleur
                    if(color[tmp.getId()].equals("blanc")) {
                        color[tmp.getId()] =  "gris";
                        distance_matrice[u][tmp.getId()] = distance_matrice[u][s] + 1;
                        distance_matrice[tmp.getId()][u] = distance_matrice[u][s] + 1;
                        file.add(tmp.getId());
                    }
                }
                if(tmp.getId() != s && tmp.getId() != u && u!=s) {
                    dist = distance_matrice[u][tmp.getId()] + distance_matrice[tmp.getId()][s];
                    //System.out.println("**********Dist : "+dist);
                    //si dist = 1 alors npcc = 1
                    if(distance_matrice[u][s] == 1) {
                        npcc[u][s] = 1;
                        npcc[s][u] = 1;
                    }
                    if(distance_matrice[u][tmp.getId()] == 1) {
                        npcc[u][tmp.getId()] = 1;
                        npcc[tmp.getId()][u] = 1;
                    }
                    if(distance_matrice[u][s] == dist) {
                        npcc[u][s] += npcc[u][tmp.getId()];
                        npcc[s][u] += npcc[u][tmp.getId()];
                    }
                }
            }
            color[s] = "noir";
    	}
    }
    
    double bet_svt(int s, int v, int t) {
        double svt = 0;
        if(npcc[s][t] == 0) {
            return svt;
        }
        if(distance_matrice[s][t] == distance_matrice[s][v]+distance_matrice[v][t]) {
            double npcc_svt = npcc[s][v]*npcc[v][t];
            svt = npcc_svt / npcc[s][t];
        }
        return svt;
    }
    
    void addBet2(int[] tab){
        int size = tab.length;
    	
    	double n = (size-1)*(size-2);
    	double bet = 0;
    	for(int v = 0; v < size; v++) {
            if(graphe.getSommetByID(tab[v]) != null){
                double sum=0;
                for (int s = 0; s < size; s++) {
                    for(int t = 0;t < size; t++) {
                        if(s!=tab[v] && s!=t && tab[v]!=t) {
                            sum += bet_svt(s,tab[v],t);
                        }   
                    }
                }
                bet = sum / n;
                System.out.println(tab[v]+" : "+ bet);
            }else{
                System.out.println(tab[v]+" : Numero de sommet invalide");
            }
        }
    }
    
    void addBet() {
    	int size = graphe.getSommets().size();
    	
    	double n = (size-1)*(size-2);
    	double bet = 0;
    	for(int v = 0; v < size; v++) {
            double sum=0;
            for (int s = 0; s < size; s++) {
                for(int t = 0;t < size; t++) {
                    if(s!=v && s!=t && v!=t) {
                        sum += bet_svt(s,v,t);
                    }   
                }
            }
            bet = sum / n;
            System.out.println(v+" : "+ bet);
    	}
    }
    
    void addDistance(){
        System.out.println("Liste Betwenness : ");
        distance_matrice = new int[graphe.getSommets().size()][graphe.getSommets().size()];
        npcc = new int[graphe.getSommets().size()][graphe.getSommets().size()];
	for(int i = 0; i < npcc.length; i++) {
            //System.out.println("BFS sur : "+i);
            BFS(i);
	 }
	/*System.out.println("*********Matrice des distances*************");
        for(int i = 0; i<distance_matrice.length; i++) {
       // System.out.println("col "+i+":");
                for(int j= 0; j<distance_matrice[i].length; j++) {
            //System.out.print(distance_matrice[i][j]+" ");
                }
        System.out.println();
        }
        System.out.println("*********Matrice des npcc*************");
        for(int i = 0; i<npcc.length; i++) {
       // System.out.println("col "+i+":");
                for(int j= 0; j<npcc[i].length; j++) {
            //System.out.print(npcc[i][j]+" ");
                }
        //System.out.println();
        }*/
    }
}
