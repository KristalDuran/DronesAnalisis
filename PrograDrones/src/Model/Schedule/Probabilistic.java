/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.Path;
import Model.IConstants;
import Model.Schedule.Schedule;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kris
 */
public class Probabilistic implements Schedule,IConstants {

    Random getRand = new Random();
    ArrayList<Path> totalPaths;
    int timeExpected;
    
    public Probabilistic(ArrayList<Path> totalPaths, int timeExpected){
        this.totalPaths = totalPaths;
        this.timeExpected = timeExpected;
    }
    
    
    public void AirTrafficController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void CalcProbabilistic(ArrayList<Integer> pathsIndex){
        Path actual;
        int rand;
        int result[][] = new int[pathsIndex.size()][totalPaths.size()];
        while(pathsIndex.size() > 0){

            if(pathsIndex.size() == 1){
                actual = totalPaths.get(pathsIndex.get(0) -1);
                //90 milisegundos * offset + tiempo en llegar + 2worst time to get to te top
    //            System.out.println("offset= " + actual.getOffset() + " tiempo= " +);
                if((((actual.getOffset() + 1) * 90) + ((actual.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP)) > ((timeExpected * 3600)*1000)){
                    System.out.println("no alcanzó el tiempo de simulación");
                    return;
                }
                //System.out.println("se añadio el path:" + actual.getPath().toString() + " en tiempo:" + (actual.getOffset() + 1) *90);
                System.out.println("termina");
                result[actual.getOffset()][pathsIndex.get(0) -1] = pathsIndex.get(0);
                actual.setOffset(actual.getOffset() + 1);
                return;
            }
            else{
                rand = getRand.nextInt(pathsIndex.size());
                actual = totalPaths.get(pathsIndex.get(rand)-1);
                //System.out.println("rand =:" + rand);
                //System.out.println("index del path = " + (pathsIndex.get(rand)-1) + " ------------------------------------");
                //System.out.println("i = " + actual.getOffset());
                //System.out.println("j = " + (pathsIndex.get(rand)-1));
                //90 milisegundos * offset + tiempo en llegar + 2worst time to get to te top
                if((((actual.getOffset() + 1) * 90) + ((actual.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP)) > ((timeExpected * 3600)*1000)){
                    System.out.println("no alcanzo el tiempo de simulación");
                    return;
                }
                //System.out.println("se añadio el path:" + actual.getPath().toString() + " en tiempo:" + (actual.getOffset() + 1) *90);
                result[actual.getOffset()][pathsIndex.get(rand)-1] = pathsIndex.get(rand);
                actual.setOffset(actual.getOffset() + 1);
                pathsIndex.remove(rand);
                //CalcProbabilistic(pathsIndex);
            }    
        }
    }
}
