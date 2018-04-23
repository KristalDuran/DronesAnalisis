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

/**
 *
 * @author Kris
 */
public class GraphMethods {
    
    int idTrip = 0;
    int numberOfTrips;
    int trackHeight;
    int trackWidth;
    int numberOfTracksByStation;
    int numberOfTrack;
    int numberOfStations;
    int timeReal;
    int timeProx;
    int cantDronesXPista;
    float worstTimeCase = 1000/30;//al no saber las posiciones de las pistas se debe calcular como si tuvieran que subir
                                  //hasta la pista màs alta
    Graph graph = new Graph();
    ArrayList<Integer> linesToDraw = new ArrayList();
    Node[] nodes;
    
    ArrayList<Node> arrayNode = new ArrayList<>();
    
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
    
    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int cant) {
        this.numberOfTrips = cant;
    }
    
    public int getNumberOfTracksByStation() {
        return numberOfTracksByStation;
    }

    public void setNumberOfTracksByStation(int numberOfTracksByStation) {
        this.numberOfTracksByStation = numberOfTracksByStation;
    }
    
    public int getNumberOfStations() {
        return numberOfStations;
    }

    public void setNumberOfStations(int numberOfStations) {
        this.numberOfStations = numberOfStations;
    }
    
    public int getTimeReal() {
        return timeReal;
    }

    public void setTimeReal(int timeReal) {
        this.timeReal = timeReal;
    }
    
    public int getTimeProx() {
        return timeProx;
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
        return trackHeight;
    }

    public void setPistaHeight(int pistaHeight) {
        this.trackHeight = pistaHeight;
    }

    public int getPistaWidth() {
        return trackWidth;
    }

    public void setPistaWidth(int pistaWidth) {
        this.trackWidth = pistaWidth;
    }
    
    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }    
    
//    public void setCantPistas(){
//        //500 entre el alto de la pista designado para decir cuàntas pistas de ida caben en la mitad
//        //se toma 500 metros porque por cada pista de ida tiene que haber una de vuelta
//        this.numberOfTrack = 500 / this.trackHeight;
//        setCantDronesXPista();
//    }
//    
//    public void setCantDronesXPista(){
//        //area de la pista entre area de un drone = 6
//        this.cantDronesXPista = (this.trackHeight*this.trackWidth)/6;
//        
//    }

    public int getNumberOfTrack() {
        return numberOfTrack;
    }

    public void setNumberOfTrack(int numberOfTrack) {
        this.numberOfTrack = numberOfTrack;
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

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }
 
// Methods to make the stations and destinations--------------------------------------------------------------------------------
    
    public void MakeStation(Integer name, int x, int y){
        
        Node create = new Node(name, x, y);
        nodes[(name.intValue()-1)] = create;
        arrayNode.add(create);
    }
    
    //definir que vertice se conecta con otro y el tama;o del arco es decir la distancia
    public void MakeGraph(GraphMethods graph){        
        //elegir aleatoriamente el que no tenga arco, si todos tienen, elegir los mas cercanos 
        graph.mergesort(graph.getNodes(), 0, graph.getNodes().length);
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
    
    ArrayList<Path> totalPaths = new ArrayList();
    
    public void setShortestPath(){

        Graph dijkstraResult = new Graph();
        
        
        for(Node toCalcDijkstra:graph.getNodes()){
            dijkstraResult = calculateDijkstraWith(toCalcDijkstra.getName());
            toCalcDijkstra.setPath(getPath(toCalcDijkstra.getName(),dijkstraResult));
        }
        System.out.println(" ");
        for(Node i:graph.getNodes()){
            System.out.println("Caminos de: " + i.getName());
            for(Path j:i.getPath()){
                
                for(int a:j.getPath()){
                    System.out.print(a + ",");
                }
                System.out.println("");
            }
        }
        System.out.println(" ");
    }
    
    public Graph calculateDijkstraWith(int nodeName){
    
        Graph copia = copy();
        
        Node toCalc = copia.getNodeByName(nodeName);
        
        copia = calculateShortestPathFromSource(copia,toCalc);
        
        return copia;
        
    }
    
    private ArrayList<Path> OneJumpPaths = new ArrayList();
    
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
                totalPaths.add(toGet);
                if(toGet.getPath().size() == 2){
                    OneJumpPaths.add(toGet);
                }
            }
        }
        return paths;
    }
    
    public ArrayList<Integer> getPathsToDo(){
        
        Random getRandom = new Random();
        int pathIndex;
        ArrayList<Integer> result = new ArrayList();
        
        while(numberOfTrips > 0){
            //System.out.println("1 viaje");
            pathIndex = getRandom.nextInt(totalPaths.size());
            numberOfTrips--;
            result.add(pathIndex + 1);  
        }
        return result;
    }
    
    
   // este hash esta acomodado de la forma [cantidad de saltos][pathIndex en totalPaths]
    public int[][] pathsToHash(ArrayList<Integer> pathsIndex){
        int hash[][] = new int[totalPaths.size()][totalPaths.size()];
        for(int toHash : pathsIndex){
            for(int hashing = 0; hashing < totalPaths.size(); hashing++){
                if(hash[totalPaths.get(toHash -1).getPath().size()][hashing] == 0){
                    hash[totalPaths.get(toHash -1).getPath().size()][hashing] = toHash;
                    break;
                }
            }
        }
        
        for(int i = 0; i < totalPaths.size(); i++){
            for(int j = 0; j < totalPaths.size(); j++){
                System.out.print(hash[i][j] + ",");
            }
            System.out.println("");
        }
        
        System.out.println("------------------------------------");
        System.out.println("");
        return hash;
    }
    
    private int hash[][];
    
    private Restriction hashRestrictionsByStation[][];//esta declaracion puede que le falten espacios
    
    public void setHashRestrictionsByStation(){
        hashRestrictionsByStation = new Restriction[30][totalPaths.size()];
    }
    
    public void calculateDivideAndConquer_PreCalc(){
        int i = 1;
        setHashRestrictionsByStation();
        hash = pathsToHash(getPathsToDo());
        while(i < totalPaths.size()- 1 && hash[i][0] == 0){
            i++;
        }
        i--;
        
        int j  = 0;
        
        while(j < totalPaths.size()- 1 &&hash[i][j] == 0){
            j++;
        }
        j--;
        //System.out.println("i = " + i);
        //System.out.println("j = " + j);
        calculateDivideAndConquer(0, i, j, hash);
    }
    
    public int getWeightInTimeOf(int stationA,int stationB){
        
        for(Path toGet:OneJumpPaths){
            if(toGet.getPath().get(0) == stationA && toGet.getPath().get(1) == stationB){
                return ((toGet.getTotalWeight()/120)*3600000);
            }   
        }
       return 0;
    }
    
    public void calculateDivideAndConquer(int offset, int i , int j, int hash[][]){
        
        Path toEval;
        Restriction toCreate;
        int offsetAux = offset;
        boolean flag  = false;
        Restriction restrictions[];
        Restriction restrictionsToJumps[];
        int timeToBeThere = 0;
        int timeToBeThereSumatory = 0;
        int timeProxAux = timeProx*1000;
        i = totalPaths.size()- 1;
        j = totalPaths.size()- 1;
        int restrictionAdded;
        for(i = totalPaths.size()- 1; i >= 0; i --){
            for(j = totalPaths.size()- 1; j >= 0; j --){
                if(hash[i][j] == 0){
                    continue;
                }
                toEval = totalPaths.get(hash[i][j] - 1);
                System.out.print(hash[i][j]);
                System.out.print(",");
                hash[i][j] = 0;
                while(!flag){
                    flag = true;
                    //el pivot va a evaluar las restricciones
                    restrictions = hashRestrictionsByStation[toEval.getPath().get(0)];
                    for(Restriction pivot: restrictions){
                        if(((toEval.getTotalWeight()/120)*3600000) + offsetAux > timeProx*1000){
                            System.out.println("no se pueden acomodar los viajes en el tiempo dado");
                            flag = true;
                            break;
                            
                        }
//                        if(pivot.from > offsetAux + ((toEval.getTotalWeight()/120)*3600000)){
//                            flag = true;
//                            break;
//                        }
                        
                        //mientras el pivot tiene interseccion con el offsetAux y el peor caso de tiempo
                        //y ademas la estacion tiene restriccion en ese tiempo
                        //se corre el offsetAux y se vuelve a evaluar
                        while(pivot.isIntersection(offsetAux,(int)(offsetAux + WORSE_TIME_TO_GET_TO_THE_TOP)) 
                                                                                    && pivot.hasRestriction(toEval.getPath().get(0))){
                            offsetAux = offsetAux + pivot.to;
                            flag = false;
                        }

                        for(int jumpToEval = 0; jumpToEval < toEval.getPath().size() -1; jumpToEval++){
                            restrictionsToJumps = hashRestrictionsByStation[toEval.getPath().get(jumpToEval + 1)];
                            timeToBeThere = getWeightInTimeOf(toEval.getPath().get(jumpToEval),toEval.getPath().get(jumpToEval+1));
                            timeToBeThereSumatory = timeToBeThereSumatory + timeToBeThere;
                            for(Restriction pivotForJumps:restrictionsToJumps){
                                if(pivotForJumps.isRestriction((int)(offsetAux + WORSE_TIME_TO_GET_TO_THE_TOP + timeToBeThere))){
                                    offsetAux = offsetAux + 1;
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        
                        for(Restriction pivotToEnd:hashRestrictionsByStation[toEval.getPath().get(toEval.getPath().size()-1)]){
                            while(pivotToEnd.isIntersection(offsetAux + timeToBeThereSumatory + getWeightInTimeOf(toEval.getPath().get(toEval.getPath().size()-2),toEval.getPath().get(toEval.getPath().size()-1)),(int)(offsetAux + timeToBeThereSumatory +WORSE_TIME_TO_GET_TO_THE_TOP)) 
                                                                                    && pivotToEnd.hasRestriction(toEval.getPath().get(toEval.getPath().size()-1))){
                                offsetAux = offsetAux + pivotToEnd.to;
                                flag = false;
                                break;
                            }
                        }
                        

                    }
                    timeToBeThereSumatory = 0;
                    if(flag){
                        //System.out.println("a");
                        //crear la restriccion del despegue
//                        toCreate = new Restriction(offsetAux,(int)(offsetAux + WORSE_TIME_TO_GET_TO_THE_TOP));
//                        toCreate.setCantRestrictions(numberOfStations);
//                        toCreate.addRestrictedStation(toEval.getPath().get(0));
                        
                        for(int toAdd = 0; toAdd < hashRestrictionsByStation[toEval.getPath().get(0)].length; toAdd++){
                            
                            if(hashRestrictionsByStation[toEval.getPath().get(0)][toAdd] == null){
                                hashRestrictionsByStation[toEval.getPath().get(0)][toAdd] = new Restriction(offsetAux,(int)(offsetAux + WORSE_TIME_TO_GET_TO_THE_TOP));
                                hashRestrictionsByStation[toEval.getPath().get(0)][toAdd].setCantRestrictions(numberOfStations);
                                hashRestrictionsByStation[toEval.getPath().get(0)][toAdd].addRestrictedStation(toEval.getPath().get(0));
                                restrictionAdded = toAdd;
                                break;
                            }    
                        }
                        
                        //crear las restricciones de los saltos sobre las estaciones
                        for(int getIntersections = 0; getIntersections < toEval.getPath().size()-1; getIntersections++){
                            timeToBeThere = getWeightInTimeOf(toEval.getPath().get(getIntersections),toEval.getPath().get(getIntersections +1));
                            timeToBeThereSumatory = timeToBeThereSumatory + timeToBeThere;
//                            toCreate = new Restriction(offsetAux + timeToBeThereSumatory,offsetAux + timeToBeThereSumatory);
//                            toCreate.setCantRestrictions(numberOfStations);
//                            toCreate.addRestrictedStation(toEval.getPath().get(getIntersections +1));
                            
                            for(int toAdd = 0; toAdd < hashRestrictionsByStation[toEval.getPath().get(getIntersections +1)].length; toAdd++){
                            
                                if(hashRestrictionsByStation[toEval.getPath().get(0)][toAdd] == null){
                                    hashRestrictionsByStation[toEval.getPath().get(0)][toAdd] = new Restriction(offsetAux + timeToBeThereSumatory,offsetAux + timeToBeThereSumatory);
                                    hashRestrictionsByStation[toEval.getPath().get(0)][toAdd].setCantRestrictions(numberOfStations);
                                    hashRestrictionsByStation[toEval.getPath().get(0)][toAdd].addRestrictedStation(toEval.getPath().get(getIntersections +1));
                                    break;
                                }
                            }
                        }
                        timeToBeThere = getWeightInTimeOf(toEval.getPath().get(toEval.getPath().size()-2),toEval.getPath().get(toEval.getPath().size()-1));
//                        toCreate = new Restriction(offsetAux + timeToBeThereSumatory,(int)(offsetAux + WORSE_TIME_TO_GET_TO_THE_TOP + timeToBeThereSumatory + timeToBeThere));
//                        toCreate.setCantRestrictions(numberOfStations);
//                        toCreate.addRestrictedStation(toEval.getPath().get(0));
                        
                        for(int toAdd = 0; toAdd < hashRestrictionsByStation[toEval.getPath().get(0)].length; toAdd++){
                            
                            if(hashRestrictionsByStation[toEval.getPath().get(0)][toAdd] == null){
                                hashRestrictionsByStation[toEval.getPath().get(0)][toAdd] = new Restriction(offsetAux + timeToBeThereSumatory,(int)(offsetAux + WORSE_TIME_TO_GET_TO_THE_TOP + timeToBeThereSumatory + timeToBeThere));
                                hashRestrictionsByStation[toEval.getPath().get(0)][toAdd].setCantRestrictions(numberOfStations);
                                hashRestrictionsByStation[toEval.getPath().get(0)][toAdd].addRestrictedStation(toEval.getPath().get(0));
                                
                            }    
                        }
                        timeProxAux = timeProxAux - ((toEval.getTotalWeight()/120)*3600000);
                        offsetAux = offset;
                    }
                    timeToBeThere = 0;
                    timeToBeThereSumatory = 0;
                }
            }
            System.out.println("");
            //i--;
            //j = totalPaths.size()- 1;
        }
        
        //hashRestrictionsByStation[1][1] = new Restriction(0,0);
        System.out.println("tiempo sobrante = " + timeProxAux);
        
        Restriction tmp;
        for(int a = 0; a < hashRestrictionsByStation.length; a++ ){
            for(int b = 0; b < hashRestrictionsByStation[0].length; b++){
                if(hashRestrictionsByStation[a][b] != null){
                    System.out.print("Estación: ");
                    for(int c = 0; c < hashRestrictionsByStation[a][b].getStationsRestricted().length ; c++){
                        System.out.print(hashRestrictionsByStation[a][b].getStationsRestricted()[c] + ",");
                    }
                    System.out.println("");
                    System.out.print("from: " + hashRestrictionsByStation[a][b].from);
                    System.out.println(" To: " + hashRestrictionsByStation[a][b].to);
                }
            }
        }
        
        System.out.println("hast[0] length:" + hashRestrictionsByStation[1].length);
        
    }
    
    
    
    
    
    
//Methods to define the times--------------------------------------------------------------------------------------------------
    private static int SPEED = 120; //velocidad
    private static int MILLISECOND = 1000;
    private static double  WORSE_TIME_TO_GET_TO_THE_TOP = (double)(3.997/(double)SPEED)*3600000;
    
    public int calculateXDistanceTime(double distance){
        //en milisegundos
        return(int) ((double)((double)distance/(double)SPEED)*3600000);
    }

    public int calculateNumOfDronesBySet(){
        int cant = 1000/trackHeight;
        int cuantosViajesEnUnaDireccion = cant/2;
        int cuantosDronesCabenEnPista = trackHeight*trackWidth / 6;
        int cuantosDronesPorSet = cuantosDronesCabenEnPista*cuantosViajesEnUnaDireccion;
        return cuantosDronesPorSet;
    }
        
    ArrayList<Object> tiemposRestriction = new ArrayList<>(); //se van a guarda de tres en tres: path, el punto restringido y el tiempo en que va a estar ahi
    
    Map<Path, Integer> viajesExactos = new HashMap<>();
    
    int tiempoGlobal=0;
    
    public void calculateTrip(){
        int cantRestanteViajes = numberOfTrips;
        
        while(cantRestanteViajes > 0){
            int indiceDelViaje = (int)(Math.random()*(totalPaths.size()-1));
            System.out.println("Indice del viaje " + indiceDelViaje);
            Path pathPorRealizar = totalPaths.get(indiceDelViaje);

            cantRestanteViajes -= calculateNumOfDronesBySet();   
            //validar que si no son divisibles puede que el ultimo calculo
            //quede un numero negativo o que no sea un set completo.
                                    
            System.out.println("length"+pathPorRealizar.path.size());
            int timeTotal = 0;    
            for (int stacion = 0; stacion < pathPorRealizar.getPath().size()-1; stacion++) {
                int stationActual = pathPorRealizar.getPath().get(stacion);
                int stationSiguietne= pathPorRealizar.getPath().get(stacion+1);

                System.out.println("Viaje "+stationActual+" "+stationSiguietne);
                System.out.println("Node origen " + arrayNode.get((stationActual)-1).getName());
                System.out.println("Node destino "+arrayNode.get((stationSiguietne)-1).getName());
                System.out.println("Distanca " +(double)(arrayNode.get((stationActual)-1).adjacentNodes.get(arrayNode.get((stationSiguietne)-1)))/1000);
                System.out.println("Tiempo "+ calculateXDistanceTime((double)(arrayNode.get((stationActual)-1).adjacentNodes.get(arrayNode.get((stationSiguietne)-1)))/1000));
                int time = calculateXDistanceTime((double)(arrayNode.get((stationActual)-1).adjacentNodes.get(arrayNode.get((stationSiguietne)-1)))/1000);
                System.out.println("");
                //time es lo que dura de a b pero falta considerarlo en una variable global 
                timeTotal += time;
                tiemposRestriction.add(pathPorRealizar.getPath());
                tiemposRestriction.add(stationSiguietne);
                tiemposRestriction.add(time);
                timeProx -= time; //se resta lo que dure en llegar del tiempo total que pude durar el viaje                    
            }
            viajesExactos.put(pathPorRealizar, timeTotal);          
        }        
    }
    
    public double calculateSlots(){
        return (timeProx*MILLISECOND)/WORSE_TIME_TO_GET_TO_THE_TOP;
    }
    
    public double calculateTotalTakeoffTime(){
        int cantDeSalidasYLLegadas = numberOfTrips/calculateNumOfDronesBySet();
        System.out.println("cantDeSalidasYLLegadas "+cantDeSalidasYLLegadas+"  "+(int)WORSE_TIME_TO_GET_TO_THE_TOP);
        double timeTotalSalirLLegar = (cantDeSalidasYLLegadas*(int)WORSE_TIME_TO_GET_TO_THE_TOP)*2;
        System.out.println("timeTotalSalirLLegar "+timeTotalSalirLLegar);
        return timeTotalSalirLLegar;
    }
    
    public void calculateSiSePuedeRealizarTodosViejes(){
        
        timeProx = timeProx * MILLISECOND;
        System.out.println("Tiempo inicial milisegundos: " + timeProx);
        timeProx -= calculateTotalTakeoffTime();
        System.out.println("Tiempo Total restante en segundos: " + (timeProx/1000));
        calculateTrip();
        System.out.println("Tiempo Total restante en segundos: " + (timeProx/1000));
        
        
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("               Hash Table ");
        for (Path i :  totalPaths) {
            
            if (viajesExactos.containsKey(i)) {
                for (Integer integer : i.getPath()) {
                    System.out.print(""+integer + " ");
                }
                System.out.println("Tiempo: " + viajesExactos.get(i));
            }

        }
        
    }
}

