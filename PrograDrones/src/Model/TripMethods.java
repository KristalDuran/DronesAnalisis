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
public class TripMethods extends TripVariables implements IConstants{
//    
    
    public TripMethods() {
        super();
    }

//>>>>>>> 5da2cc44d5300833c068208ef1f5f79571965965
    public void setNumberOfStations(String numberOfStations)throws Exceptions{
        if (excetions.isNumeric(numberOfStations)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.numberOfStations = Integer.parseInt(numberOfStations);
    }
    
    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(String cant) throws Exceptions{
        if (excetions.isNumeric(cant)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.numberOfTrips = Integer.parseInt(cant);
    }
    
     public int getPistaHeight() {
        return trackHeight;
    }

    public void setPistaHeight(String trackHeight) throws Exceptions{
        if (excetions.isNumeric(trackHeight)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.trackHeight = Integer.parseInt(trackHeight);
        
    }
    
    public int getPistaWidth() {
        return trackWidth;
    }

    public void setPistaWidth(String trackWidth) throws Exceptions{
        if (excetions.isNumeric(trackWidth)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.trackWidth = Integer.parseInt(trackWidth);

    }
    
    public ArrayList<Path> getTotalPaths() {
        return totalPaths;
    }
    
    public int getTimeReal() {
        return timeReal;
    }

    public void setTimeReal(String timeReal) throws Exception{
        if (excetions.isNumeric(timeReal)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.timeReal = Integer.parseInt(timeReal);
    }
    
    public int getTimeProx() {
        return timeProx;
    }
    
    public void setTimeProx(String timeProx) throws Exceptions{
        if (excetions.isNumeric(timeProx)) {
            throw new Exceptions(excetions.msg(0));
        }
        this.timeProx = Integer.parseInt(timeProx);
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
    
    public void setCantDronesByIndividualTrip(int cantStations) throws Exceptions{
        cantDronesByIndividualTrip = calculateNumOfDronesBySet()/(2*Math.pow(cantStations,2)) - (2 * cantStations);
        if(cantDronesByIndividualTrip < 1){
            throw new Exceptions(excetions.msg(1));
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
    
    public ArrayList<Path> calculateTrip(Node[] nodes) throws Exceptions{
        
        setCantDronesByIndividualTrip(numberOfStations);
        
        int cantRestanteViajes = numberOfTrips;
        
        while(cantRestanteViajes > 0){
            int indiceDelViaje = (int)(Math.random()*(totalPaths.size()-1));
            Path pathPorRealizar = totalPaths.get(indiceDelViaje);
            //cantRestanteViajes -= cantDronesByIndividualTrip;----------------------------------marca de cómo estába antes
            cantRestanteViajes--;
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
        printTrips();
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
