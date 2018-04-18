/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jil
 */
public class Graph {
    
    
    private Set<Node> nodes = new HashSet<>();
    
    public Graph(){
    
    }
    
    public Graph(Graph copia){
        this.nodes = copia.nodes;
    }
    
    
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
    
    public void setShortestPathToNode(Node toChange){
        for(Node toSet:nodes){
            if(toSet.getName() == toChange.getName()){
                toSet.setShortestPath(toChange.getShortestPath());
            }
        }
    }
    public Node getNodeByName(Integer name){
        
        for(Node NodeToFind : nodes){
            if(NodeToFind.getName() == name){
                return NodeToFind;
            }
            
        }
        return null;
    }
    
    
    
}

