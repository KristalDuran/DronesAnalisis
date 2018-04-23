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
    
    /**
     * 
     * @param trips
     * @param totalPaths
     * @param WORSE_TIME_TO_GET_TO_THE_TOP 
     */
    public BackTracking(Map<Path, Integer> trips, ArrayList<Path> totalPaths, double WORSE_TIME_TO_GET_TO_THE_TOP) {
        this.trips = trips;
        this.totalPaths = totalPaths;
        paths = new Path[trips.size()];
        lineOfTime = new Path[trips.size()][1];
        this.WORSE_TIME_TO_GET_TO_THE_TOP = WORSE_TIME_TO_GET_TO_THE_TOP;
    }
    
    
    /**
     * This method make a controller of the trips, it is recursive 
     * @param indexMatriz
     * @param listOfTrips
     * @param indexList
     * @return 
     */
    public boolean backtracking( int indexMatriz, Path[] listOfTrips, int indexList){
        
        Path auxPath = null;
        
        if (indexList >= listOfTrips.length) 
            exit = false;
            
        while(indexList < listOfTrips.length || exit == false){  
            
            indexMatriz++;       
            if (insertTrip( indexMatriz, listOfTrips[indexList])) {
                if (indexList < listOfTrips.length-1) {
//                    System.out.println("Llamado recursivo " + (indexTodos+1) + "   "+todos.length);
                    
                    backtracking(-1, listOfTrips, indexList+1);
                }else
                    exit = true;    
                    break;
            }
        }
        return exit;
    }
    
    
    public boolean insertTrip(int index, Path path){
//        System.out.println("Valida si se puede agregar "+index);
        int time = 0;
        int pastTime = 0;
        int posLineOfTime;
        for (posLineOfTime = 0; posLineOfTime < lineOfTime[index].length; posLineOfTime++) {
//            System.out.println("for se puede ");
            if (lineOfTime[index][posLineOfTime] != null) {
                if (!validateElement(lineOfTime[index][posLineOfTime], path)) {
//                    System.out.println("No se puede");
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
    
    
    public void addTrip( int index, int i, Path path){
//        System.out.println("agregar");
        Path[] newList = new Path[(lineOfTime[index].length)+1];
        for (int j = 0; j < lineOfTime[index].length; j++) {
            newList[j] = lineOfTime[index][j];
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
    
    public boolean validateElement(Path path1, Path path2){
        if (path1.getPath().size() == path2.getPath().size()) {
            int finalPath2 = path2.getPath().size()-1;
            for (int i = 0; i < path1.getPath().size(); i++) {
//                System.out.println("Igual length \nPath1 "+path1.getPath().get(i)+" Path2 "+path2.getPath().get(finalPath2));
                if (path1.getPath().get(i) != path2.getPath().get(finalPath2)) {
                    return false;
                }
                if (i >= finalPath2) {
//                    System.out.println("los busca diferentes");
                    
                    break;
                }
                finalPath2--;
            }
        
        }else{
//            System.out.println("Difereten length");
            for (int i = 0; i < path1.getPath().size(); i++) {
                if (path2.getPath().contains(path1.getPath().get(i))) {
//                    System.out.println("No se puede agregar");
                    return false;
                }
            }
        }
        
//        System.out.println("Lo puede agregar");
        return true;
    }
    
    public void sendTrips(){
        int time = 0;
        int shippingCounter = 0;
        
        for (Path[] shipping: lineOfTime) {
            if (HashShippingTime.containsKey(shipping)) {
                System.out.println("\"-------------------------------------------------------------------\nSlot: " +shippingCounter+"\n                                 Tiempo inicial: "+time+"");

                for (Path path: shipping) {

                    if (path != null) {
                        System.out.println();
                        for (int station: path.getPath()) {
                            System.out.print(station + " ");
                        }
                        System.out.println("Tiempo " + trips.get(path));
                    }

                }
                
                time += HashShippingTime.get(shipping);
                System.out.println("                                 Tiempo final: "+time);
            }
            
            shippingCounter++;
        }
        
    }
}
