/*
 * 
 */
package Model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class has the data of the Graph
 * @author Jose and Kristal
 */
public class Graph {
   
    private Set<Node> nodes = new HashSet<>();
    
    /**
     * This is the constructor
     */
    public Graph(){
    
    }
    
    /**
     * This constructos set the nodes of the graph
     * @param copia 
     */
    public Graph(Graph copia){
        this.nodes = copia.nodes;
    }
    
    /**
     * This method add each node to a list
     * @param nodeA 
     */
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    /**
     * This method get the nodes
     * @return Set<>
     */
    public Set<Node> getNodes() {
        return nodes;
    }

    /**
     * This method set the list of nodes
     * @param nodes 
     */
    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
    
    /**
     *  
     * @param toChange 
     */
    public void setShortestPathToNode(Node toChange){
        for(Node toSet:nodes){
            if(toSet.getName().equals(toChange.getName())){
                toSet.setShortestPath(toChange.getShortestPath());
            }
        }
    }
    
    /**
     * This method return de node that has the nome of the param
     * @param name
     * @return Node
     */
    public Node getNodeByName(Integer name){
        
        for(Node NodeToFind : nodes){
            if(NodeToFind.getName().equals(name)){
                return NodeToFind;
            }
            
        }
        return null;
    }
    
    
    
}

