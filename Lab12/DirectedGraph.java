import java.util.*;
/**
 * The DirectedGraph containing directed nodes and edges
 *
 * @author Evan Vu
 */

class GraphException extends RuntimeException{
        public GraphException( String name )
        {
        super( name );
        }
    }
    
public class DirectedGraph<E>
{
    
    class DirectedGraphNode<E>{
        E key;
        ArrayList<DirectedGraphEdge> edges=new ArrayList<DirectedGraphEdge>();
        int scratch=0;
        int dist=0;
        DirectedGraphNode prev;
        
        DirectedGraphNode(E key){ this.key = key;}
        
        DirectedGraphEdge nearestNeighbor(){
            DirectedGraphEdge first = edges.get(0);
            if (first==null) return null;
            for (DirectedGraphEdge edge:edges){
                if (edge.compareTo(first)<0) first=edge;
            }
            return first;
        }
        
         public void reset( ){ 
             dist = 1000000; prev = null; scratch = 0; 
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
    
    class Path implements Comparable<Path>{
        DirectedGraphNode dest;
        int dist;
        
        Path(DirectedGraphNode dest, int dist){
            this.dest=dest;
            this.dist=dist;
        }
        
        public int compareTo(Path comp){
            return this.dist - comp.dist;
        }
    }
    
    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
    public void printPath( E destKey )
    {
        DirectedGraphNode w = this.getByKey( destKey );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == 1000000 )
            System.out.println( destKey + " is unreachable" );
        else
        {
            System.out.print( "(Cost is: " + w.dist + ") " );
            printPath( w );
            System.out.println( );
        }
    }
    
    /** 
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     */
    private void printPath( DirectedGraphNode dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );
            System.out.print( " to " );
        }
        System.out.print( dest.key );
    }
    
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll( )
    {
        for( DirectedGraphNode v : list )
            v.reset( );
    }
    
    
    List<DirectedGraphNode> list=new ArrayList<DirectedGraphNode>();
    public DirectedGraph(){}
    
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
        if(!node1.edges.isEmpty()){
            node1.edges.add(added);
            return true;
        }
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
        PriorityQueue<Path> pq = new PriorityQueue<Path>();
        DirectedGraphNode start = this.getByKey(key);
        if( start == null )
            throw new NoSuchElementException( "Start DirectedGraphNode not found" );

        clearAll();
        pq.add( new Path( start, 0 ) ); start.dist = 0;
        
        int nodesSeen = 0;
        while( !pq.isEmpty( ) && nodesSeen < list.size( ) )
        {
            Path vrec = pq.remove( );
            DirectedGraphNode v = vrec.dest;
            if( v.scratch != 0 )  // already processed v
                continue;
            
            v.scratch = 1;
            nodesSeen++;
            ArrayList<DirectedGraphEdge> vedges = v.edges;

            for( DirectedGraphEdge e : vedges )
            {
                DirectedGraphNode w = e.end;
                int cvw = e.weight;
                
                if( cvw < 0 )
                    throw new GraphException( "Graph has negative edges" );
                    
                if( w.dist > v.dist + cvw )
                {
                    w.dist = v.dist +cvw;
                    w.prev = v;
                    pq.add( new Path( w, w.dist ) );
                }
            }
        }
    }
}
