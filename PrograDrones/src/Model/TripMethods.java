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

public class TripMethods extends TripVariables implements IConstants{
//    
    
    public TripMethods() {
        super();
    }

    public void setNumberOfStations(String numberOfStations)throws ownException{
        if (excetions.isNumeric(numberOfStations)) {
            throw new ownException(excetions.msg(0));
        }
        this.numberOfStations = Integer.parseInt(numberOfStations);
    }
    
    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(String cant) throws ownException{
        if (excetions.isNumeric(cant)) {
            throw new ownException(excetions.msg(0));
        }
        this.numberOfTrips = Integer.parseInt(cant);
    }
    
     public int getPistaHeight() {
        return trackHeight;
    }

    public void setPistaHeight(String trackHeight) throws ownException{
        if (excetions.isNumeric(trackHeight)) {
            throw new ownException(excetions.msg(0));
        }
        this.trackHeight = Integer.parseInt(trackHeight);
        
    }
    
    public int getPistaWidth() {
        return trackWidth;
    }

    public void setPistaWidth(String trackWidth) throws ownException{
        if (excetions.isNumeric(trackWidth)) {
            throw new ownException(excetions.msg(0));
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
            throw new ownException(excetions.msg(0));
        }
        this.timeReal = Integer.parseInt(timeReal);
    }
    
    public int getTimeProx() {
        return timeProx;
    }
    
    public void setTimeProx(String timeProx) throws ownException{
        if (excetions.isNumeric(timeProx)) {
            throw new ownException(excetions.msg(0));
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
    
    public void setCantDronesByIndividualTrip(int cantStations) throws ownException{
        cantDronesByIndividualTrip = (int)(calculateNumOfDronesBySet()/(2*Math.pow(cantStations,2)) - (2 * cantStations));
        System.out.println("cantidad de drones por viaje son:" + cantDronesByIndividualTrip);
        if(cantDronesByIndividualTrip < 1){
            throw new ownException(excetions.msg(1));
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
    
    public ArrayList<Path> calculateTrip(Node[] nodes) throws ownException{
        
        setCantDronesByIndividualTrip(numberOfStations);
        
        int cantRestanteViajes = numberOfTrips;
        Random rand = new Random();
        
        while(cantRestanteViajes > 0){
            int indiceDelViaje = rand.nextInt(totalPaths.size());
            Path pathPorRealizar = totalPaths.get(indiceDelViaje);
            cantRestanteViajes -= cantDronesByIndividualTrip;
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
        
    public int getTimeRemaining(ArrayList<ArrayList<Path>> result, int totalTime){
        totalTime = (totalTime*3600)*1000;
        ArrayList<Path> lastSlot = result.get(result.size()-1);
        Path larger = new Path();
        larger.setTotalWeight(0);
        
        for(int getLargerTrip = 0; getLargerTrip < lastSlot.size();getLargerTrip++){
            if(lastSlot.get(getLargerTrip).getTotalWeight() > larger.getTotalWeight()){
                larger = lastSlot.get(getLargerTrip);
            }
        }
        int timeToEndLastTrip = (int)((90 * (larger.getOffset() + 1)) + ((larger.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP));
        return totalTime - timeToEndLastTrip;
    }
    
}
