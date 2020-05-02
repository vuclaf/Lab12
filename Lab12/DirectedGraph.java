import java.util.*;
/**
 * The DirectedGraph containing directed nodes and edges
 *
 * @author Evan Vu
 */
public class DirectedGraph<E>
{
    class DirectedGraphNode<E>{
        E key;
        ArrayList<DirectedGraphEdge> edges;
        
        DirectedGraphNode(E key){ this.key = key;}
        
        DirectedGraphEdge nearestNeighbor(){
            DirectedGraphEdge first = edges.get(0);
            if (first==null) return null;
            for (DirectedGraphEdge edge:edges){
                if (edge.compareTo(first)<0) first=edge;
            }
            return first;
        }
        
        @Override
        public boolean equals(Object o){
            DirectedGraphNode comp=(DirectedGraphNode)o;
            if (this.key==comp.key) return true;
            return false;
        }
    }
    
    class DirectedGraphEdge implements Comparable<DirectedGraphEdge>{
        DirectedGraphNode start;
        DirectedGraphNode end;
        int weight;
        
        DirectedGraphEdge(DirectedGraphNode start, DirectedGraphNode end, int weight){
            this.start=start;
            this.end=end;
            this.weight=weight;
        }
        
        public int compareTo(DirectedGraphEdge edge){
            return this.weight - edge.weight;
        }
    }
    
    List<DirectedGraphNode> list=new ArrayList<DirectedGraphNode>();
    
    public DirectedGraph(){

    }
    
    public DirectedGraphNode getByKey(E key){
        Iterator itr = list.iterator();
        while(itr.hasNext()){
            DirectedGraphNode node = (DirectedGraphNode)itr.next();
            if(node.key==key) return node;
        }
        return null;
    }
    
    public boolean addNode(E key){
        if(this.getByKey(key)==null){
            list.add(new DirectedGraphNode(key));
            return true;
        }
        return false;
    }
    
    public boolean addEdge(E k1, E k2, int w){
        DirectedGraphNode node1 = this.getByKey(k1);
        DirectedGraphNode node2 = this.getByKey(k2);
        if (node2==null || node1==null) return false;
        DirectedGraphEdge added = new DirectedGraphEdge(this.getByKey(k1),this.getByKey(k2),w);
        if(!node1.edges.contains(added)){
            node1.edges.add(added);
            return true;
        }
        return false;
    }
    
    public ArrayList getNeighbors(E key){
        DirectedGraphNode node = this.getByKey(key);
        ArrayList<DirectedGraphEdge> edges=node.edges;
        ArrayList<DirectedGraphNode> neighbors = new ArrayList<DirectedGraphNode>();
        for (DirectedGraphEdge edge:edges){
            neighbors.add(edge.end);
        }
        return neighbors;
    }
    
    public void dijkstra(E key){
        
    }
}
