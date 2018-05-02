/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.IConstants.SPEED;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kris
 */
//<<<<<<< HEAD
//public class TripMethods {
//    private int numberOfTrips;
//    private int trackHeight;
//    private int trackWidth;
//    private ArrayList<Path> totalPaths;
//    
//    private ArrayList<Path> trips = new ArrayList<>();
//    
//    private int timeReal;
//    private int timeProx;
//    private int cantDronesXPista;
//  
//    private double cantDronesByIndividualTrip;
//=======
public class TripMethods extends TripVariables{
//    
    
    public TripMethods() {
        super();
    }

//>>>>>>> 5da2cc44d5300833c068208ef1f5f79571965965
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

    public void setTotalPaths(ArrayList<Path> totalPaths) {
        this.totalPaths = totalPaths;
    }
    
    public double getCantDronesByIndividualTrip() {
        return cantDronesByIndividualTrip;
    }
    
    public void setCantDronesByIndividualTrip(int cantStations) {
        cantDronesByIndividualTrip = calculateNumOfDronesBySet()/(2*Math.pow(cantStations,2)) - (2 * cantStations);
        if(cantDronesByIndividualTrip < 1){
            System.out.println("el tamaño de las pistas es demasiado pequeño con respecto a la cantidad de estaciones");
        }
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
    
    public ArrayList<Path> calculateTrip(Node[] nodes){
        setCantDronesByIndividualTrip(numberOfStations);
        int cantRestanteViajes = numberOfTrips;
        
        while(cantRestanteViajes > 0){
            int indiceDelViaje = (int)(Math.random()*(totalPaths.size()-1));
            Path pathPorRealizar = totalPaths.get(indiceDelViaje);
            cantRestanteViajes -= cantDronesByIndividualTrip;
//            cantRestanteViajes -= calculateNumOfDronesBySet(); 
            //donde se define el total de distancia 
//            int timeTotal = 0;    
//            for (int stacion = 0; stacion < pathPorRealizar.getPath().size()-1; stacion++) {
//                int stationActual = pathPorRealizar.getPath().get(stacion);
//                int stationSiguietne= pathPorRealizar.getPath().get(stacion+1);
//                                    
//            }
            trips.add(pathPorRealizar);
        }
        //printTrips();
        return trips;
    }

    public void printTrips(){
       
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("               Hash Table ");
        for (Path i :  trips) {
            for (Integer integer : i.getPath()) {
                System.out.print(""+integer + " ");
            }
            System.out.println("");
        }
        
    }    
    
    public int calculateXDistanceTime(double distance){
        //en milisegundos
        return(int) ((double)((double)distance/(double)SPEED)*3600000);
    }
        
}
