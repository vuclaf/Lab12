import java.util.*;
import java.io.*;
/**
 * GraphExperiment class to receive input and run experiments
 *
 * @author EvanVu
 */
public class GraphExperiment<E>
{
    DirectedGraph<E> graph = new DirectedGraph<E>();
    
    public GraphExperiment(){
    }
    
    public static void main(String[] args) throws IOException{
        GraphExperiment<String> exp = new GraphExperiment<String>();
        Scanner scr = new Scanner(new File("input.txt"));
        if(scr.hasNext()){
            String nodes=scr.nextLine();
            String[] nodeList = nodes.split(" ");
            for(String node:nodeList){
                if(node==null) throw new NullPointerException();
                exp.graph.addNode(node);
            }
        }
        while(scr.hasNext()){
            String input=scr.nextLine();
            String[] inputList = input.split(" ");
            if(inputList.length==0) throw new NullPointerException();
            exp.graph.addEdge(inputList[0],inputList[1],Integer.parseInt(inputList[2]));
        }
        Scanner sct = new Scanner(System.in);
        System.out.println("Which node do you want to start with?");
        String start=scr.nextLine();
        exp.graph.dijkstra(start);
        System.out.println(start + " " + "0");
        exp.graph.printPathAll();
    }
}
