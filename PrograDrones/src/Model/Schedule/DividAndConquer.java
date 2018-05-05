/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.Exceptions;
import Model.IConstants;
import static Model.IConstants.WORSE_TIME_TO_GET_TO_THE_TOP;
import Model.Path;
import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class DividAndConquer implements Schedule, IConstants{
    private ArrayList<ArrayList<Path>> tripsSlots = new ArrayList<ArrayList<Path>>();
    private int timeTotal = 0;
    
    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int timeExpected )throws Exceptions{


        divide(totalPaths, 0, totalPaths.size()-1, 0);
        if (totalPaths.size()%2 != 0) {
            addTime(totalPaths.get(totalPaths.size()/2));
            tripsSlots.get(totalPaths.get(totalPaths.size()/2).getOffset()).add(totalPaths.get(totalPaths.size()/2));

        }
        
        if (timeTotal > ((timeExpected * 3600)*1000)) {
            
            throw new Exceptions(excetions.msg(2));
        }
        
        return tripsSlots;
    }

    public ArrayList<ArrayList<Path>> divide(ArrayList<Path> trips, int low, int high, int cont){
        if (low < high){
            
            int middle = (low + high) /2;

            divide(trips, low, middle,cont );

            divide(trips, middle+1, high, cont );

            unir(trips, low, middle, high, cont);
        }
        
        return null;
    }
    
    public void unir(ArrayList<Path> arr, int low, int middle, int high, int cont){
        
        
        Path[] helper = new Path[arr.size()];
	for (int i = low; i <= high; i++) {
            helper[i] = arr.get(i);
            
            if (helper[i].getOffset() > 0 ) {
                restTime(helper[i]);
                tripsSlots.get(arr.get(i).getOffset()-1).remove(arr.get(i));
                arr.get(i).setOffset(arr.get(i).getOffset()-1);
            }            
	}
       
        if (tripsSlots.size() <= high) {
            for (int n = 0; n <= high; n++) {
                tripsSlots.add(new ArrayList<>());
            } 
        }
        
        int helperLeft = low;
	int helperRight = middle + 1;
	int current = low;
	
	while (helperLeft <= middle && helperRight <= high) {

//            if (!tripsSlots.get(helper[helperLeft].getOffset()).contains(helper[helperLeft])) {
                tripsSlots.get(helper[helperLeft].getOffset()).add(helper[helperLeft]);
                helper[helperLeft].setOffset(helper[helperLeft].getOffset()+1);
                addTime(helper[helperLeft]);
                helperLeft++;

//            }
            
//            if (!tripsSlots.get(helper[helperRight].getOffset()).contains(helper[helperRight])) {
                tripsSlots.get(helper[helperRight].getOffset()).add(helper[helperRight]);
                helper[helperRight].setOffset(helper[helperRight].getOffset()+1);
                addTime(helper[helperRight]);
                helperRight++;

//            }
            current ++;
		
	}
	
 
        
    }
    
    public void addTime(Path path){
        if (path.getOffset() == 1) {
            timeTotal += (((path.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP));
        }else
            timeTotal += 90;   //----------------------------------------lo que dura subiendose
    }
    
    public void restTime(Path path){
        if (path.getOffset() == 1) {
            timeTotal -= (((path.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP));
        }else
            timeTotal -= 90;   //----------------------------------------lo que dura subiendose
    }

    
}
