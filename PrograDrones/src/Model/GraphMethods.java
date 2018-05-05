/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * This class manipulate the graph
 * @author Jose and Kristal
 */
public class GraphMethods implements IConstants {
    
    //it is an accountant
    private int idTrip = 0;
    
    //the user defines these variables
    private int numberOfTracksByStation;
    private int numberOfStations;
    
    //these arrays are required to make a graph and have all the nodes, and finally to print the graph
    Graph graph = new Graph();
    ArrayList<Integer> linesToDraw = new ArrayList();
    Node[] nodes;
    
    
    public GraphMethods() {
    }
//Settes and gettes-------------------------------------------------------------------------------------------------------------
    
    public void setNodes(){
        nodes = new Node[numberOfStations];
    }
    
    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }
    
    public int getNumberOfTracksByStation() {
        return numberOfTracksByStation;
    }

    public void setNumberOfTracksByStation(String numberOfTracksByStation) throws Exceptions{
        if (excetions.isNumeric(numberOfTracksByStation)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.numberOfTracksByStation = Integer.parseInt(numberOfTracksByStation);
    }
    
    public int getNumberOfStations() {
        
        return numberOfStations;
    }

    public void setNumberOfStations(String numberOfStations) throws Exceptions {
        if (excetions.isNumeric(numberOfStations)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.numberOfStations = Integer.parseInt(numberOfStations);
    }

    public ArrayList<Integer> getLinesToDraw() {
        return linesToDraw;
    }

    public void setLinesToDraw(ArrayList<Integer> linesToDraw) {
        this.linesToDraw = linesToDraw;
    }
    
    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }   
    
    public Node[] getNodes() {
        return nodes;
    }

 
// Methods to make the stations and destinations--------------------------------------------------------------------------------
    
    /**
     * This method make the nodes that are the stations and it adds this stations to an array
     * @param name
     * @param x
     * @param y 
     */
    public void MakeStation(Integer name, int x, int y){
        
        Node create = new Node(name, x, y);
        nodes[(name.intValue()-1)] = create;
    }
    
    /**
     * This method define the nodes adjacents first the random station and them the closest 
     * @param graph 
     */
    public void MakeGraph(GraphMethods graph){        

        linesToDraw.clear();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int subNode = 0; subNode < graph.getNodes().length; subNode++) {
            int cantPista = graph.getNumberOfTracksByStation() - graph.getNodes()[subNode].getAdjacentNodes().size();               
            
            while ( cantPista > 0) {
                int destineNode = (int) (Math.random()*graph.getNumberOfStations());
                if (graph.getNodes()[subNode].getName().compareTo(graph.getNodes()[destineNode].getName()) != 0 ) {
                    if (arrayList.size()+1 == graph.getNumberOfStations()) { 
                        defineTheClosets(graph, subNode);
                        cantPista--;
                    }else{   
                        if (  (graph.getNodes()[destineNode].getAdjacentNodes().size() < 1) ){
                            addArrayTemp(destineNode, arrayList);
                            addArrayTemp(subNode, arrayList);
                            graph.getNodes()[subNode].addDestination(graph.getNodes()[destineNode], defineDistance(graph.getNodes()[subNode], graph.getNodes()[destineNode]));                                
                            graph.getNodes()[destineNode].addDestination(graph.getNodes()[subNode], defineDistance(graph.getNodes()[destineNode], graph.getNodes()[subNode]));
                            linesToDraw.add(graph.getNodes()[subNode].getX());
                            linesToDraw.add(graph.getNodes()[subNode].getY());
                            linesToDraw.add(graph.getNodes()[destineNode].getX());
                            linesToDraw.add(graph.getNodes()[destineNode].getY());
                            cantPista--;                            
                        }    
                    }  
                }
            }            
        }
        
        addGraph();
        
    }
    
    /**
     * This method add node to a temporal array
     * @param node
     * @param arrayList 
     */
    public void addArrayTemp(int node, ArrayList<Integer> arrayList){
         if (!arrayList.contains(node)) {
            arrayList.add(node);
        }
    }
    
    /**
     * This method define the distance from one node to another
     * @param origen
     * @param destin
     * @return int
     */
    public int defineDistance(Node origen, Node destin){
        return (int)Math.sqrt((int)Math.pow(destin.getX() - origen.getX(), 2) + (int)Math.pow(destin.getY() - origen.getY(), 2));
    }
    
    /**
     * This method add all node to the graph 
     */
    public void addGraph(){
        for (int i = 0; i < nodes.length; i++) {
            graph.addNode(nodes[i]);
        }
    }
    
    /**
     * This method define which is the nearest node
     * @param graph
     * @param currentNode
     * @return Node
     */
    public Node defineBestNode(GraphMethods graph, int currentNode){
        
        Node currentNod = nodes[currentNode];
        Node bestNode = null;
        int i;
        
        if (currentNode == 0) {
            bestNode = nodes[nodes.length-1];
        }else{
            bestNode = nodes[0];
        }       
                
        for (i = 0; i < nodes.length; i++) {
            if (i != currentNode) {
              if (defineDistance(currentNod, nodes[i]) < defineDistance(currentNod, bestNode)) {                    
                    bestNode = nodes[i];
                }
            }
        }
        return bestNode;
        
    }
    /**
     * This method define what the adjacent node will be
     * @param graph
     * @param currentNode 
     */
    public void defineTheClosets(GraphMethods graph, int currentNode){
        Node destineNode = defineBestNode(graph,currentNode);
        int distancia = defineDistance(graph.getNodes()[currentNode], destineNode);
        graph.getNodes()[currentNode].addDestination(destineNode, distancia);
        destineNode.addDestination(graph.getNodes()[currentNode], distancia);
        linesToDraw.add(graph.getNodes()[currentNode].getX());
        linesToDraw.add(graph.getNodes()[currentNode].getY());
        linesToDraw.add(destineNode.getX());
        linesToDraw.add(destineNode.getY());
    }

//Methods to calculate the shortest paths-------------------------------------------------------------------------------------------------
    
    public Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry < Node, Integer> adjacencyPair:currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }
    
    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
    
    private static void CalculateMinimumDistance(Node evaluationNode,
        Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    } 
    
    public Graph copy(){
        Graph nuevo = new Graph();
    
        for(Node copy: this.graph.getNodes()){
            nuevo.addNode(new Node(copy.getName(),copy.getX(),copy.getY()));
        }
        
        for(Node tmp:this.graph.getNodes()){
            for (Map.Entry < Node, Integer> adjacencyPair:tmp.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                for(Node j: nuevo.getNodes()){
                    if(j.getName() == tmp.getName()){
                        j.addDestination(nuevo.getNodeByName(adjacentNode.getName()), edgeWeight);
                        break;
                    }
                }
            }
        }
        return nuevo;
    }
    
    public Node getNode(int toFind){
        for(Node toGet:this.nodes){
            if(toGet.getName() == toFind){
                return toGet;
            }
        }
        return null;
    }
    
    public Graph calculateDijkstraWith(int nodeName){
    
        Graph copia = copy();
        
        Node toCalc = copia.getNodeByName(nodeName);
        
        copia = calculateShortestPathFromSource(copia,toCalc);
        
        return copia;
        
    }
        
    
    public ArrayList<Path> getPath(int nodeName,Graph graph, ArrayList<Path> totalPaths){
        
        ArrayList<Path> paths = new ArrayList();
        Path toGet;
        
        for(Node tmp : graph.getNodes()){
            toGet = new Path();
            toGet.setTotalWeight(tmp.getDistance());
            
            for(Node nodo:tmp.getShortestPath()){
                toGet.getPath().add(nodo.getName());
            }
            toGet.getPath().add(tmp.getName());
            if(toGet.getPath().size() > 1){
                paths.add(toGet);
                totalPaths.add(toGet);
            }
        }
        return paths;
    }
   
    public ArrayList<Path> setShortestPath(){

        Graph dijkstraResult = new Graph();
        ArrayList<Path> totalPaths = new ArrayList();
        
        for(Node toCalcDijkstra:graph.getNodes()){
            dijkstraResult = calculateDijkstraWith(toCalcDijkstra.getName());
            toCalcDijkstra.setPath(getPath(toCalcDijkstra.getName(),dijkstraResult,totalPaths));
        }
//        System.out.println(" ");
//        for(Node i:graph.getNodes()){
//            System.out.println("Caminos de: " + i.getName());
//            for(Path j:i.getPath()){
//                
//                for(int a:j.getPath()){
//                    System.out.print(a + ",");
//                }
//                System.out.println("");
//            }
//        }
//        System.out.println(" ");
        
        return totalPaths;
    }
}

 