/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Kris
 */
public class GraphMethods {
    
    int idTrip = 0;
    int cantTrips;
    int pistaHeight;
    int pistaWidth;
    int sizePista;
    int cantPistas;
    int cantStation;
    int timeReal;
    int timeProx;
    int cantDronesXPista;
    float worstTimeCase = 1000/30;//al no saber las posiciones de las pistas se debe calcular como si tuvieran que subir
                                  //hasta la pista màs alta
    
    Graph graph = new Graph();
    ArrayList<Integer> linesToDraw = new ArrayList();
    Node[] nodes;
    
    public GraphMethods() {
    }

    public void setNodes(){
        nodes = new Node[cantStation];
    }
    
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
    
    public void MakeStation(Integer name, int x, int y){
        
        Node create = new Node(name, x, y);
        nodes[(name.intValue()-1)] = create;
    }
    
    //definir que vertice se conecta con otro y el tama;o del arco es decir la distancia
    public void MakeGraph(GraphMethods graph){        
        //elegir aleatoriamente el que no tenga arco, si todos tienen, elegir los mas cercanos 
        graph.mergesort(graph.getNodes(), 0, graph.getNodes().length);
        linesToDraw.clear();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int subNode = 0; subNode < graph.getNodes().length; subNode++) {
            int cantPista = graph.getSizePista() - graph.getNodes()[subNode].getAdjacentNodes().size();               
            
            while ( cantPista > 0) {
                int destineNode = (int) (Math.random()*graph.getCantStation());
                if (graph.getNodes()[subNode].getName().compareTo(graph.getNodes()[destineNode].getName()) != 0 ) {
                    if (arrayList.size()+1 == graph.getCantStation()) { 
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
        
        addGraph(graph);
        
    }
    
    public void addArrayTemp(int node, ArrayList<Integer> arrayList){
         if (!arrayList.contains(node)) {
            arrayList.add(node);
        }
    }
    
    public int defineDistance(Node origen, Node destin){
        return (int)Math.sqrt((int)Math.pow(destin.getX() - origen.getX(), 2) + (int)Math.pow(destin.getY() - origen.getY(), 2));
    }
    
    public void addGraph(GraphMethods graph){
        
        for (int i = 0; i < graph.getNodes().length; i++) {
            graph.getGraph().addNode(graph.getNodes()[i]);
        }
        
        for(Node tmp : graph.getNodes()){
            //System.out.println("Nodo:" + tmp.getName());
            for(Node j : tmp.getShortestPath()){
                //System.out.println("from:" + tmp.getName() + " to " + j.getName() + " distance = " + j.getDistance());
            }
        }
        
        //graph = calculateShortestPathFromSource(graph, nodes[0]);
        
        //System.out.println("from 1:");
        for(Node tmp : graph.getNodes()){
            //System.out.println("to:" + tmp.getName() + " distance: " + tmp.getDistance());
        }
    }
    
    public int defineBestNode(GraphMethods graph, int currentNode){
        int nodeNext = (currentNode+1);
        int nodePast = (currentNode-1);
        int distanceNext = Integer.MAX_VALUE;
        int distancePast = Integer.MAX_VALUE;
        
        if (nodeNext > graph.getNodes().length-1) {
            nodeNext = currentNode;
        }
        
        if (nodePast < 0) {
            nodePast = currentNode;
        }
        
        if (currentNode != nodeNext) {
            while (true) {            
                if (graph.getNodes()[nodeNext].getAdjacentNodes().containsKey(graph.getNodes()[(currentNode)])) {
                    if (nodeNext != graph.getNodes().length-1)                       
                        nodeNext++;
                    else{
                        distanceNext = Integer.MAX_VALUE;
                        break;
                    }
                }else{
                    distanceNext = defineDistance(graph.getNodes()[currentNode], graph.getNodes()[nodeNext]);
                    break;
                }
            }
        }
        
        if(currentNode != nodePast){
            while (true) {            
                if (graph.getNodes()[nodePast].getAdjacentNodes().containsKey(graph.getNodes()[(currentNode)])) {
                    if (nodePast != 0) {
                        nodePast--;
                    }else{
                        distancePast =Integer.MAX_VALUE;
                        break;
                    }
                }else{
                    distancePast = defineDistance(graph.getNodes()[currentNode], graph.getNodes()[nodePast]);
                    break;
                }
            }
        }
        
        if (distanceNext < distancePast) {
            System.out.println("mayor " + distancePast + "menor" + distanceNext);
            return nodeNext;
        }else{
            System.out.println("mayor " + distanceNext + "menor" + distancePast);
            return nodePast; 
        }
    }
    
    public void defineTheClosets(GraphMethods graph, int currentNode){
        int destineNode = defineBestNode(graph,currentNode);
        int distancia = defineDistance(graph.getNodes()[currentNode], graph.getNodes()[destineNode]);
        graph.getNodes()[currentNode].addDestination(graph.getNodes()[destineNode], distancia);
        graph.getNodes()[destineNode].addDestination(graph.getNodes()[currentNode], distancia);
        linesToDraw.add(graph.getNodes()[currentNode].getX());
        linesToDraw.add(graph.getNodes()[currentNode].getY());
        linesToDraw.add(graph.getNodes()[destineNode].getX());
        linesToDraw.add(graph.getNodes()[destineNode].getY());
    }
        
    public static void mergesort(Node[ ] matrix, int init, int n)
        {
        int n1;
        int n2;
        if (n > 1)
        {
            n1 = n / 2;
            n2 = n - n1;
            mergesort(matrix, init, n1);
            mergesort(matrix, init + n1, n2);
            merge(matrix, init, n1, n2);
        }
    }
    
    private static void merge(Node[] matrix, int init, int n1, int n2) {
        
        Node[ ] buffer = new Node[n1+n2];
        int temp = 0;
        int temp1 = 0;
        int temp2 = 0;
        int i;
        while ((temp1 < n1) && (temp2 < n2))
        {
            int a = matrix[init + temp1].getX() + matrix[init + temp1].getY();
            int b = matrix[init + n1 + temp2].getX() + matrix[init + n1 + temp2].getY();
            if (a < b)
                buffer[temp++] = matrix[init + (temp1++)];
            else
                buffer[temp++] = matrix[init + n1 + (temp2++)];
        }
        while (temp1 < n1)
            buffer[temp++] = matrix[init + (temp1++)];
        while (temp2 < n2)
            buffer[temp++] = matrix[init + n1 + (temp2++)];
        for (i = 0; i < n1+n2; i++)
            matrix[init + i] = buffer[i];
    }
    
   
    
    public void controllerAereoProbabilistic(){
        
    }
    
    public void controllerAereoDividAndConquer(){
        
    }
    
    public void controllerAereoBacktracking(){
        
    }
    
    public boolean ExistWay(int origen, int destino){
        return false;
    }
    //camino mas corto 
    public void LookBestWay(int origen, int destino){
        //Guarda en hashtable
    }   

    public int getIdTrip() {
        return idTrip;
    }

    public int getCant() {
        return cantTrips;
    }

    public int getSizePista() {
        return sizePista;
    }

    public int getCantStation() {
        return cantStation;
    }

    public int getTimeReal() {
        return timeReal;
    }

    public int getTimeProx() {
        return timeProx;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setCant(int cant) {
        this.cantTrips = cant;
    }

    public void setSizePista(int sizePista) {
        this.sizePista = sizePista;
    }

    public void setCantStation(int cantStation) {
        this.cantStation = cantStation;
    }

    public void setTimeReal(int timeReal) {
        this.timeReal = timeReal;
    }

    public void setTimeProx(int timeProx) {
        this.timeProx = timeProx;
    }

    public ArrayList<Integer> getLinesToDraw() {
        return linesToDraw;
    }

    public void setLinesToDraw(ArrayList<Integer> linesToDraw) {
        this.linesToDraw = linesToDraw;
    }

    public int getPistaHeight() {
        return pistaHeight;
    }

    public void setPistaHeight(int pistaHeight) {
        this.pistaHeight = pistaHeight;
    }

    public int getPistaWidth() {
        return pistaWidth;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    
    
    public void setPistaWidth(int pistaWidth) {
        this.pistaWidth = pistaWidth;
    }
    
    public void setCantPistas(){
        //500 entre el alto de la pista designado para decir cuàntas pistas de ida caben en la mitad
        //se toma 500 metros porque por cada pista de ida tiene que haber una de vuelta
        this.cantPistas = 500 / this.pistaHeight;
        setCantDronesXPista();
    }
    
    public void setCantDronesXPista(){
        //area de la pista entre area de un drone = 6
        this.cantDronesXPista = (this.pistaHeight*this.pistaWidth)/6;
        
    }

    public int getCantTrips() {
        return cantTrips;
    }

    public void setCantTrips(int cantTrips) {
        this.cantTrips = cantTrips;
    }

    public int getCantPistas() {
        return cantPistas;
    }

    public void setCantPistas(int cantPistas) {
        this.cantPistas = cantPistas;
    }

    public int getCantDronesXPista() {
        return cantDronesXPista;
    }

    public void setCantDronesXPista(int cantDronesXPista) {
        this.cantDronesXPista = cantDronesXPista;
    }

    public float getWorstTimeCase() {
        return worstTimeCase;
    }

    public void setWorstTimeCase(float worstTimeCase) {
        this.worstTimeCase = worstTimeCase;
    }

//    public Node getNode() {
//        return node;
//    }
//
//    public void setNode(Node node) {
//        this.node = node;
//    }

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
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
    
    
    public void setShortestPath(){

        Graph resultadoDijkstra = new Graph();
        ArrayList<Path> totalPaths = new ArrayList();
        
        for(Node toCalcDijkstra:graph.getNodes()){
            resultadoDijkstra = calculateDijkstraWith(toCalcDijkstra.getName());
            toCalcDijkstra.setPath(getPath(toCalcDijkstra.getName(),resultadoDijkstra));
        }
        
        for(Node i:graph.getNodes()){
            System.out.println("Caminos de: " + i.getName());
            for(Path j:i.getPath()){
                
                for(int a:j.getPath()){
                    System.out.print(a + ",");
                }
                System.out.println("");
            }
        }
    }
    
    public Graph calculateDijkstraWith(int nodeName){
    
        Graph copia = copy();
        
        Node toCalc = copia.getNodeByName(nodeName);
        
        copia = calculateShortestPathFromSource(copia,toCalc);
        
        return copia;
        
    }
    
    public ArrayList<Path> getPath(int nodeName,Graph graph){
        
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
            }
            
        }
    
        return paths;
    }
    
}
