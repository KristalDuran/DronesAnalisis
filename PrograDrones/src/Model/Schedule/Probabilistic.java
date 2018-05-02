/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.Path;
import Model.IConstants;
import Model.Schedule.Schedule;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kris
 */
public class Probabilistic implements Schedule,IConstants {

    Random getRand = new Random();
    
    public Probabilistic(){
        
    }
    
    

    
    
    public ArrayList<ArrayList<Path>> CalcProbabilistic(ArrayList<Path> totalPaths, int time){

        Path actual;
        int rand;
        ArrayList<ArrayList<Path>> result = new ArrayList<ArrayList<Path>>(totalPaths.size());
        while(totalPaths.size() > 0){

            if(totalPaths.size() == 1){
                actual = totalPaths.get(0);
                //90 milisegundos * offset + tiempo en llegar + 2worst time to get to te top
                if((((actual.getOffset() + 1) * 90) + ((actual.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP)) > ((time * 3600)*1000)){
                    System.out.println("no alcanzó el tiempo de simulación");
                    return null;
                }
                if(result.size() <= actual.getOffset()){
                    for(int i = 0; i <= (actual.getOffset()-result.size()); i++){
                        result.add(new ArrayList<Path>());
                    }
                }
                System.out.println("termina");
                result.get(actual.getOffset()).add(actual);
                actual.setOffset(actual.getOffset() + 1);
                break;
            }
            else{
                rand = getRand.nextInt(totalPaths.size());
                actual = totalPaths.get(rand);
                //90 milisegundos * offset + tiempo en llegar + 2worst time to get to te top
                if((((actual.getOffset() + 1) * 90) + ((actual.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP)) > ((time * 3600)*1000)){
                    System.out.println("no alcanzo el tiempo de simulación");
                    return null;
                }
                if(result.size() <= actual.getOffset()){
                    for(int i = 0; i <= (actual.getOffset()-result.size()); i++){
                        result.add(new ArrayList<Path>());
                    }
                }
                result.get(actual.getOffset()).add(actual);
                actual.setOffset(actual.getOffset() + 1);
                totalPaths.remove(rand);
            }    
        }
        
        soutResult(result);
        return result;
        
    }
    
    
    public void soutResult(ArrayList<ArrayList<Path>> result){
        Path a;
        
        for(int i = 0; i < result.size();i++){
            System.out.println("Tiempo: " + i + " salen");
            for(int j = 0; j < result.get(i).size();j++){
                a = result.get(i).get(j);
                System.out.println(a.getPath().toString());
            }
        }
        
        
        
    }

    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int time) {
        return CalcProbabilistic(totalPaths,time);
    }
}
