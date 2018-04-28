/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.IConstants.MILLISECOND;
import static Model.IConstants.SPEED;
import static Model.IConstants.WORSE_TIME_TO_GET_TO_THE_TOP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Kris
 */
public class TripMethods {
    private int numberOfTrips;
    private int trackHeight;
    private int trackWidth;
    private ArrayList<Path> totalPaths = new ArrayList();
    private ArrayList<Object> tiemposRestriction = new ArrayList<>(); //se van a guarda de tres en tres: path, el punto restringido y el tiempo en que va a estar ahi
    
    private Map<Path, Integer> viajesExactos = new HashMap<>();
    
    private int timeReal;
    private int timeProx;
    private int cantDronesXPista;
    Node[] nodes;
    
    
    
    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int cant) {
        this.numberOfTrips = cant;
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
    
    public ArrayList<Path> getTotalPaths() {
        return totalPaths;
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
    
    public int getCantDronesXPista() {
        return cantDronesXPista;
    }

    public void setCantDronesXPista(int cantDronesXPista) {
        this.cantDronesXPista = cantDronesXPista;
    }
    
    public ArrayList<Path> getPathsToDo(){
        
        Random getRandom = new Random();
        int pathIndex;
        ArrayList<Path> result = new ArrayList();
        
        while(numberOfTrips > 0){
            pathIndex = getRandom.nextInt(totalPaths.size());
            numberOfTrips--;
            result.add(totalPaths.get(pathIndex));  
        }
        return result;
    }
    
    public int calculateNumOfDronesBySet(){
        int cant = 1000/trackHeight;
        int cuantosViajesEnUnaDireccion = cant/2;
        int cuantosDronesCabenEnPista = trackHeight*trackWidth / 6;
        int cuantosDronesPorSet = cuantosDronesCabenEnPista*cuantosViajesEnUnaDireccion;
        return cuantosDronesPorSet;
    }
    
    public void calculateTrip(){
        int cantRestanteViajes = numberOfTrips;
        
        while(cantRestanteViajes > 0){
            int indiceDelViaje = (int)(Math.random()*(totalPaths.size()-1));
//            System.out.println("Indice del viaje " + indiceDelViaje);
            Path pathPorRealizar = totalPaths.get(indiceDelViaje);

            cantRestanteViajes -= calculateNumOfDronesBySet();   
            //validar que si no son divisibles puede que el ultimo calculo
            //quede un numero negativo o que no sea un set completo.
                                    
//            System.out.println("length"+pathPorRealizar.path.size());
            int timeTotal = 0;    
            for (int stacion = 0; stacion < pathPorRealizar.getPath().size()-1; stacion++) {
                int stationActual = pathPorRealizar.getPath().get(stacion);
                int stationSiguietne= pathPorRealizar.getPath().get(stacion+1);

//                System.out.println("Viaje "+stationActual+" "+stationSiguietne);
//                System.out.println("Node origen " + arrayNode.get((stationActual)-1).getName());
//                System.out.println("Node destino "+arrayNode.get((stationSiguietne)-1).getName());
//                System.out.println("Distanca " +(double)(arrayNode.get((stationActual)-1).adjacentNodes.get(arrayNode.get((stationSiguietne)-1)))/1000);
//                System.out.println("Tiempo "+ calculateXDistanceTime((double)(arrayNode.get((stationActual)-1).adjacentNodes.get(arrayNode.get((stationSiguietne)-1)))/1000));
                int time = calculateXDistanceTime((double)(nodes[((stationActual)-1)].adjacentNodes.get(nodes[((stationSiguietne)-1)]))/1000);
//                System.out.println("");
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
    
//    public double calculateTotalTakeoffTime(){
//        int cantDeSalidasYLLegadas = numberOfTrips/calculateNumOfDronesBySet();
//        System.out.println("cantDeSalidasYLLegadas "+cantDeSalidasYLLegadas+"  "+(int)WORSE_TIME_TO_GET_TO_THE_TOP);
//        double timeTotalSalirLLegar = (cantDeSalidasYLLegadas*(int)WORSE_TIME_TO_GET_TO_THE_TOP)*2;
//        System.out.println("timeTotalSalirLLegar "+timeTotalSalirLLegar);
//        return timeTotalSalirLLegar;
//    }
    
    public void calculateSiSePuedeRealizarTodosViejes(){
        
//        timeProx = timeProx * MILLISECOND;
//        System.out.println("Tiempo inicial milisegundos: " + timeProx);
////        timeProx -= calculateTotalTakeoffTime();
//        System.out.println("Tiempo Total restante en segundos: " + (timeProx/1000));
//        calculateTrip();
//        System.out.println("Tiempo Total restante en segundos: " + (timeProx/1000));
        
        
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
            }
        }
        return paths;
    }

    public Map<Path, Integer> getViajesExactos() {
        return viajesExactos;
    }
    
    
    public int calculateXDistanceTime(double distance){
        //en milisegundos
        return(int) ((double)((double)distance/(double)SPEED)*3600000);
    }
    
//    public double calculateSlots(){
//        return (timeProx*MILLISECOND)/WORSE_TIME_TO_GET_TO_THE_TOP;
//    }
    
}
