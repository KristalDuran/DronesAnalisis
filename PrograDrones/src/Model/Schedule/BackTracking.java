/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.Exceptions;
import Model.IConstants;
import java.util.ArrayList;
import Model.Path;
/**
 *
 * @author Kris
 */
public class BackTracking implements Schedule, IConstants{
    
    private ArrayList<Path> totalPaths;
    private ArrayList<ArrayList<Path>> lineOfTime;
    private int time;
    private int timeTotal = 0;
    
    public BackTracking() {
        
    }
    
    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int time) throws Exceptions{
        this.totalPaths = totalPaths;
        this.time = time;
        lineOfTime = new ArrayList<ArrayList<Path>>(totalPaths.size());
        boolean cantDoTrips = backtracking(0, totalPaths, 0 , 0);
        if (cantDoTrips) {
            //sendTrips();
            return lineOfTime;
        }
        throw new Exceptions(excetions.msg(2));
    }
    
     /**
     * This method make a controller of the trips, it is recursive 
     * @param indexMatriz
     * @param listOfTrips
     * @param indexList
     * @return boolean
     */
    public boolean backtracking( int indexMatriz, ArrayList<Path> listOfTrips, int indexList, int cont){        
        
        if (cont == listOfTrips.size()) {
            return true;
        }else{
            while (indexList <= listOfTrips.size()-1) { 
                if (insertTrip(indexMatriz, listOfTrips.get(indexList))) {
                    cont++;
                    if (!validarTime( listOfTrips.get(indexList), time)) {
                        return false;
                    }
                    if (indexList >= listOfTrips.size()-1) {
                        backtracking(indexMatriz+1, listOfTrips, 0, cont);
                        break;
                    }
                }
                indexList++;
            }
            if (cont < listOfTrips.size()-1 && indexList > listOfTrips.size()-1) {
                backtracking(indexMatriz+1, listOfTrips, 0, cont);        
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
//            for (int i = 0; i <= index - lineOfTime.size(); i++) {
                lineOfTime.add(new ArrayList<>());
//            }
        }
        
        lineOfTime.get(index).add(path);  
        path.setOffset(path.getOffset()+1);
        return true;        
    }
    
    /**
     * This method 
     * @param path
     * @param timeExpected
     * @return 
     */ 
    public boolean validarTime( Path path, int timeExpected){
        
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
