
package Model.Schedule;

import Model.Exceptions;
import Model.IConstants;
import static Model.IConstants.WORSE_TIME_TO_GET_TO_THE_TOP;
import Model.Path;
import java.util.ArrayList;

/**
 * This clase allows to determine if the amount of trips is possible 
 * in the estimated time, additionally orders the trips according to the 
 * time of departure
 * @author Kristal and Jose
 */
public class DividAndConquer implements Schedule, IConstants{
    
    
    private ArrayList<ArrayList<Path>> tripsSlots = new ArrayList<ArrayList<Path>>();
    private int timeTotal = 0;
    
    
    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int timeExpected )throws Exceptions{
        
        divide(totalPaths, 0, totalPaths.size()-1, 0);
        
        if (totalPaths.size() > 1 && totalPaths.size()%2 != 0) {
            addTime(totalPaths.get(totalPaths.size()/2));
            tripsSlots.get(totalPaths.get(totalPaths.size()/2).getOffset()).add(totalPaths.get(totalPaths.size()/2));
        }
        
        if (timeTotal > ((timeExpected * 3600)*1000)) {   
            throw new Exceptions(excetions.msg(2));
        }
        
        return tripsSlots;
    }

    /**
     * This method divides the travel list recursively 
     * @param trips
     * @param low
     * @param high
     * @param cont
     * @return ArrayList<ArrayList<>>
     */
    public ArrayList<ArrayList<Path>> divide(ArrayList<Path> trips, int low, int high, int cont){
        if (low < high){
            
            int middle = (low + high) /2;

            divide(trips, low, middle,cont );

            divide(trips, middle+1, high, cont );

            merge(trips, low, middle, high, cont);
        }
        
        return null;
    }
    
    /**
     * This method merges the array and that inserts the trips in the correct place of departure
     * @param arr
     * @param low
     * @param middle
     * @param high
     * @param cont 
     */
    public void merge(ArrayList<Path> arr, int low, int middle, int high, int cont){
        
        
        Path[] helper = new Path[arr.size()];
	for (int indexArray = low; indexArray <= high; indexArray++) {
            helper[indexArray] = arr.get(indexArray);
            
            if (helper[indexArray].getOffset() > 0 ) {
                restTime(helper[indexArray]);
                tripsSlots.get(arr.get(indexArray).getOffset()-1).remove(arr.get(indexArray));
                arr.get(indexArray).setOffset(arr.get(indexArray).getOffset()-1);
            }            
	}
       
        if (tripsSlots.size() <= high) {
            for (int legthTrips = 0; legthTrips <= high; legthTrips++) {
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
    
    /**
     * This method adds the time of agreement offSet
     * @param path 
     */
    public void addTime(Path path){
        if (path.getOffset() == 1) {
            timeTotal += (((path.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP));
        }else
            timeTotal += 90;   //----------------------------------------lo que dura subiendose
    }
    
    /**
     * This method subtracts the time of agreement offSet
     * @param path 
     */
    public void restTime(Path path){
        if (path.getOffset() == 1) {
            timeTotal -= (((path.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP));
        }else
            timeTotal -= 90;   //----------------------------------------lo que dura subiendose
    }

    
}
