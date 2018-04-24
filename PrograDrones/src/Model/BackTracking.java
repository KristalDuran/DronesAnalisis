/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kris
 */
public class BackTracking {
    
    Map<Path, Integer> trips;
    double WORSE_TIME_TO_GET_TO_THE_TOP;
    boolean exit = false;
    Map<Path[], Integer> HashShippingTime = new HashMap<>();
    Path[] paths;
    ArrayList<Path> totalPaths;
    Path[][] lineOfTime;
    int droneSet;
    /**
     * 
     * @param trips
     * @param totalPaths
     * @param WORSE_TIME_TO_GET_TO_THE_TOP 
     */
    public BackTracking(Map<Path, Integer> trips, ArrayList<Path> totalPaths, double WORSE_TIME_TO_GET_TO_THE_TOP, int droneSet) {
        this.trips = trips;
        this.totalPaths = totalPaths;
        paths = new Path[trips.size()];
        lineOfTime = new Path[trips.size()][10];
        this.WORSE_TIME_TO_GET_TO_THE_TOP = WORSE_TIME_TO_GET_TO_THE_TOP;
        this.droneSet = droneSet;
    }
    
    
    /**
     * This method make a controller of the trips, it is recursive 
     * @param indexMatriz
     * @param listOfTrips
     * @param indexList
     * @return boolean
     */
    public boolean backtracking( int indexMatriz, Path[] listOfTrips, int indexList){
        
        Path auxPath = null;
        
        if (indexList >= listOfTrips.length) 
            exit = false;
            
        while(indexList < listOfTrips.length || exit == false){  
            
            indexMatriz++;       
            if (insertTrip( indexMatriz, listOfTrips[indexList])) {
                if (indexList < listOfTrips.length-1) {
                    backtracking(-1, listOfTrips, indexList+1);
                }else
                    exit = true;    
                    break;
            }
        }
        return exit;
    }
    
    
    /**
     * This methos insertThe trips in the correct block, it validates and define the time
     * @param index
     * @param path
     * @return boolean
     */
    public boolean insertTrip(int index, Path path){
        int time = 0;
        int pastTime = 0;
        int posLineOfTime;
        for (posLineOfTime = 0; posLineOfTime < lineOfTime[index].length; posLineOfTime++) {
            if (lineOfTime[index][posLineOfTime] != null) {
                if (!validateElement(lineOfTime[index][posLineOfTime], path)) {
                    return false;
                }
                time = trips.get(lineOfTime[index][posLineOfTime]);
                if (pastTime < time) {
                    pastTime = time;               
                }else{
                    time = pastTime;
                }
            }
        }
        addTrip(index, posLineOfTime, path);        
        if (pastTime> time) {
            time = pastTime;
        }
        if (HashShippingTime.containsKey(lineOfTime[index])) {
            HashShippingTime.remove(lineOfTime[index]);            
        }
        HashShippingTime.put(lineOfTime[index], (time+((int)WORSE_TIME_TO_GET_TO_THE_TOP*2)));
        
        return true;        
    }
    
    /**
     * This method adds a new trip in the respective block, 
     * it makes an auxiliary list to increase the size and match the original
     * @param index
     * @param i
     * @param path 
     */
    public void addTrip( int index, int i, Path path){
        Path[] newList = new Path[(lineOfTime[index].length)+1];
        if (lineOfTime[index][9] != null) {
            System.out.println("Entro");
            for (int j = 0; j < lineOfTime[index].length; j++) {
                newList[j] = lineOfTime[index][j];
            }        
        }
        
        newList[(lineOfTime[index].length)] = path;
        lineOfTime[index] = newList;        
    }
    
    
    public void ControllerBackTracking(){
        
        int cont = 0;
        for (Path i :  totalPaths) {            
            if (trips.containsKey(i)) {
                paths[cont] = i;
                cont++;
            }
            
        }
        
        backtracking( -1, paths, 0);    
        
        sendTrips();
    }
    
    /**
     * 
this method receives two trips and compares the stations of each trip
     * @param path1
     * @param path2
     * @return boolean
     */
    public boolean validateElement(Path path1, Path path2){
        if (path1.getPath().size() == path2.getPath().size()) {
            int finalPath2 = path2.getPath().size()-1;
            for (int i = 0; i < path1.getPath().size(); i++) {
              if (path1.getPath().get(i) != path2.getPath().get(finalPath2)) {
                    return false;
                }
                if (i >= finalPath2) {                    
                    break;
                }
                finalPath2--;
            }
        
        }else{
            for (int i = 0; i < path1.getPath().size(); i++) {
                if (path2.getPath().contains(path1.getPath().get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * This method print the line of time 
     */
    public void sendTrips(){
        int time = 0;
        int shippingCounter = 0;
        int auxNumersOfTrips = 0 ;
        
        for (Path[] shipping: lineOfTime) {
            if (HashShippingTime.containsKey(shipping)) {
                System.out.println("\"-------------------------------------------------------------------\nSlot: " +shippingCounter+"\n                                 Tiempo inicial: "+time+"");

                for (Path path: shipping) {

                    if (path != null) {
                        System.out.println();
                        for (int station: path.getPath()) {
                            System.out.print(station + " ");
                            auxNumersOfTrips++;
                        }
                        System.out.println("Tiempo " + trips.get(path));
                    }

                }
                
                time += HashShippingTime.get(shipping);
                System.out.println("                                 Tiempo final: "+
                        time );
                //+ "\nViajes " +auxNumersOfTrips*droneSet+ "/"
            }
            
            shippingCounter++;
        }
        
    }
}
