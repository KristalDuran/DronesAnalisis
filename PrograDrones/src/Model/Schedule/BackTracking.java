/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.IConstants;
import java.util.ArrayList;
import Model.Path;
/**
 *
 * @author Kris
 */
public class BackTracking implements Schedule, IConstants{
    
    ArrayList<Path> totalPaths;
    ArrayList<ArrayList<Path>> lineOfTime;
    int time;
    
    public BackTracking() {
        
    }
    
    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int time) {
        this.totalPaths = totalPaths;
        this.time = time;
        lineOfTime = new ArrayList<ArrayList<Path>>(totalPaths.size());
        if (backtracking(0, totalPaths, 0, new ArrayList<Integer>() , 0)) {
            sendTrips();
            return lineOfTime;
        }
        System.out.println("No alcanzo el tiempo");
        return null;
    }
    
     /**
     * This method make a controller of the trips, it is recursive 
     * @param indexMatriz
     * @param listOfTrips
     * @param indexList
     * @return boolean
     */
    public boolean backtracking( int indexMatriz, ArrayList<Path> listOfTrips, int indexList, ArrayList<Integer> ma, int cont){        
        
        if (cont == listOfTrips.size()) {
            return true;
        }else{
            while (indexList <= listOfTrips.size()-1) { 
                if (insertTrip(indexMatriz, listOfTrips.get(indexList))) {
                    cont++;
                    if (!validarTime(ma, listOfTrips.get(indexList), time)) {
                        return false;
                    }
                    if (indexList >= listOfTrips.size()-1) {
                        backtracking(indexMatriz+1, listOfTrips, 0, ma, cont);
                        break;
                    }
                }
                indexList++;
            }
            if (cont < listOfTrips.size()-1 && indexList > listOfTrips.size()-1) {
                backtracking(indexMatriz+1, listOfTrips, 0, ma, cont);        
            }else{ return true; }
        }
        return true;
    }
    
    
    /**
     * This methos insertThe trips in the correct block, it validates and define the time
     * @param index
     * @param path
     * @return boolean
     */
    public boolean insertTrip(int index, Path path){
        
        if (path.getOffset() != index) {
            return false;
        }
        
        if (lineOfTime.size()-1 < index) {
            lineOfTime.add(new ArrayList<>());
        }
        
        lineOfTime.get(index).add(path);  
        path.setOffset(path.getOffset()+1);
        return true;        
    }
    
    //creo que esta mal, probar bien 
    public boolean validarTime(ArrayList<Integer> ma, Path path, int timeExpected){
        if (ma.size()-1 < path.getOffset()) {
            ma.add(0);
        }
        ma.set(path.getOffset()-1, (int)(ma.get(path.getOffset()-1)+ (((path.getOffset()) * 90) + ((path.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP))));
        if (ma.get(path.getOffset()-1) > ((timeExpected * 3600)*1000)) {
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
