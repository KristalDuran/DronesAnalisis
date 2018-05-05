
package Model.Schedule;

import Model.ownException;
import Model.IConstants;
import java.util.ArrayList;
import Model.Path;
/**
 * This clase allows to determine if the amount of trips is possible 
 * in the estimated time, additionally orders the trips according to the 
 * time of departure
 * @author Kristal and Jose
 */
public class BackTracking implements Schedule, IConstants{
    
    private ArrayList<Path> totalPaths;
    private ArrayList<ArrayList<Path>> lineOfTime;
    private int time;
    private int timeTotal = 0;
    
    public BackTracking() {
        
    }
    
    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int time) throws ownException{
        this.totalPaths = totalPaths;
        this.time = time;
        lineOfTime = new ArrayList<ArrayList<Path>>(totalPaths.size());
        boolean cantDoTrips = backtracking(0, totalPaths, 0 , 0);
        if (cantDoTrips) {
            //sendTrips();
            return lineOfTime;
        }
        throw new ownException(excetions.msg(2));
    }
    
     /**
     * This method make a controller of the trips, it is recursive, 
     * it chooses the trips that can be added in the current slot 
     * @param indexListOfAllTrips
     * @param listOfTrips
     * @param indexList
     * @return boolean
     */
    public boolean backtracking( int indexListOfAllTrips, ArrayList<Path> listOfTrips, int indexList, int counterTripsadded){        
        
        if (counterTripsadded == listOfTrips.size()) {
            return true;
        }else{
            while (indexList <= listOfTrips.size()-1) { 
                if (insertTrip(indexListOfAllTrips, listOfTrips.get(indexList))) {
                    counterTripsadded++;
                    if (!validateTime(listOfTrips.get(indexList), time)) {
                        return false;
                    }
                    if (indexList >= listOfTrips.size()-1) {
                        backtracking(indexListOfAllTrips+1, listOfTrips, 0, counterTripsadded);
                        break;
                    }
                }
                indexList++;
            }
            if (counterTripsadded < listOfTrips.size()-1 && indexList > listOfTrips.size()-1) {
                backtracking(indexListOfAllTrips+1, listOfTrips, 0, counterTripsadded);        
            }else{ return true; }
        }
        return true;
    }
    
    
    /**
     * This methos insertThe trips in the correct block
     * @param indexListOfAllTrips
     * @param path
     * @return boolean
     */
    public boolean insertTrip(int indexListOfAllTrips, Path path){
        
        if (path.getOffset() != indexListOfAllTrips) {
            return false;
        }
        
        if (lineOfTime.size()-1 < indexListOfAllTrips) {
//            for (int i = 0; i <= index - lineOfTime.size(); i++) {
                lineOfTime.add(new ArrayList<>());
//            }
        }
        
        lineOfTime.get(indexListOfAllTrips).add(path);  
        path.setOffset(path.getOffset()+1);
        return true;        
    }
    
    /**
     * This method validates and define the time
     * @param path
     * @param timeExpected
     * @return boolean
     */ 
    public boolean validateTime( Path path, int timeExpected){
        
        if (path.getOffset() == 1) {
            timeTotal += (((path.getOffset()) * 90) + ((path.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP));
        }else{
            timeTotal += 90; // ese tiempo que dura subiendose el mismo creo que esta mal
        }
        
        if (timeTotal > ((timeExpected * 3600)*1000)) {
            return false;
        }
        return true;
    }
    
    /**
     * This method print the line of time 
     */
    public void sendTrips(){
        int shippingCounter = 0;
        int auxNumersOfTrips = 0 ;
        int cont = 0;
        for (ArrayList<Path> shipping: lineOfTime) {
            cont++;
            System.out.println("               inicio slot " + cont);
            for (Path path: shipping) {
                if (path != null) {
                    System.out.println();
                    for (int station: path.getPath()) {
                        System.out.print(station + " ");
                        auxNumersOfTrips++;
                    }
                    System.out.println("");
                }

            } 
            System.out.println("               fin slot " + cont);
            
            shippingCounter++;
        }
    }

    
}
