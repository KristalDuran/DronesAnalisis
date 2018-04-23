/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class Restriction {
    int from;
    int to;
    int stationsRestricted[] = null;
    //Restriction generatedFromThisOne[];
    
    public Restriction(int from, int to){
        this.from = from;
        this.to = to;
    }
    
    public boolean isRestriction(int toEvaluate){
        if(from <= toEvaluate && toEvaluate <= to){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void addRestrictedStation(int stationName){
       for(int toAdd = 0; toAdd < stationsRestricted.length; toAdd ++){
           if(stationsRestricted[toAdd] == 0){
               stationsRestricted[toAdd] = stationName;
               break;
           }
       }
    }
    
    public void setCantRestrictions(int cant){
        stationsRestricted = new int[cant];
        
        for(int clean = 0; clean < cant; clean++){
            stationsRestricted[clean] = 0;
        }
    }
    
    public boolean hasRestriction(int stationName){
        
        for(int i = 0; i < stationsRestricted.length; i++){
            if(stationsRestricted[i] == stationName){
                return true;
            }
        }
        return false;
    }
    
    public boolean isIntersection(int from, int to){
        for(int i = from; i < to; i++){
            if(isRestriction(i)){
                return true;
            }
        }
        return false;
    }

    public int[] getStationsRestricted() {
        return stationsRestricted;
    }
    
}
