/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kris
 */
public class BackTracking {
    
    Map<Path, Integer> trips = new HashMap<>();
    
    
    public void backtracking(Path[] paths, int index, Path input){
        Path[] auxPath = new Path[2];
        
        if (isASolition()) 
            processSolition();
        else{
            index++;
            auxPath = contruct(input, auxPath);
            for (Path path : auxPath) {
                paths[index] = path;
                backtracking(paths, index, input);
                if (finished()) {
                    return;
                }
            }
        }
        
    }
    
    public boolean isASolition(){
        return false;
    }
    
    public void processSolition(){
        
    }
    
    public Path[] contruct(Path input, Path[] aux){
        for (int i = 0; i < paths.length; i++) {
            if (input.equals(paths[i])) {
                if (i+2 < trips.size()) {
                    aux[0] = this.paths[i+1];
                    aux[1] = this.paths[i+2];
                }
            }
        }
        return aux;
    }
    
    public boolean finished(){
        return true;
    }
    
    Path[] paths = new Path[trips.size()];
    Path[] totalPaths;
    
    public void all(){
        int cont = 0;
        for (Path i :  totalPaths) {            
            if (trips.containsKey(i)) {
                paths[cont] = i;
            }
            cont++;
        }
    }
}
