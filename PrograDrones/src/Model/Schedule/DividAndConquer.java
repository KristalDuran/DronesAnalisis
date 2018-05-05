
package Model.Schedule;

import Model.OwnException;
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
    private int timeExpected;
    
    
    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int timeExpected )throws OwnException{
        this.timeExpected = timeExpected;
        divide(totalPaths, 0, totalPaths.size()-1);
        
        if (totalPaths.size() > 1 && totalPaths.size()%2 != 0) {
            validateTime(totalPaths.get(totalPaths.size()/2));
            tripsSlots.get(totalPaths.get(totalPaths.size()/2).getOffset()).add(totalPaths.get(totalPaths.size()/2));
        }
        
        if (totalPaths.size() <= 1) {
            tripsSlots.add(totalPaths );
        }
        
        return tripsSlots;
    }

    /**
     * This method divides the travel list recursively 
     * The O big is: O(n log n)
     * T(n) = sumatoria de n/2 hasta cero
     * @param trips
     * @param low
     * @param high
     * @return ArrayList<ArrayList<>>
     */
    public ArrayList<ArrayList<Path>> divide(ArrayList<Path> trips, int low, int high) throws OwnException{
        if (low < high){ //times: n
            
            int middle = (low + high) /2; //times: n-1

            divide(trips, low, middle ); // times: n/2

            divide(trips, middle+1, high); // times: n/2

            merge(trips, low, middle, high); // times: n/2
        }
        
        return null;
    }
    
    /**
     * This method merges the array and that inserts the trips in the correct place of departure
     * El O grande es de: O(n)

     * @param arr
     * @param low
     * @param middle
     * @param high
     */
    public void merge(ArrayList<Path> arr, int low, int middle, int high) throws OwnException{
        
        Path[] helper = new Path[arr.size()]; //times: 1
        
	for (int indexArray = low; indexArray <= high; indexArray++) { //times: high - low
            helper[indexArray] = arr.get(indexArray); //times: high - low -1
            
            if (helper[indexArray].getOffset() > 0 ) { //times: high - low -1
                tripsSlots.get(arr.get(indexArray).getOffset()-1).remove(arr.get(indexArray));
                arr.get(indexArray).setOffset(arr.get(indexArray).getOffset()-1);
            }            
	}
       
        if (tripsSlots.size() <= high) { //times: 1
            for (int legthTrips = 0; legthTrips <= high; legthTrips++) { //times: high
                tripsSlots.add(new ArrayList<>());
            } 
        }
        
        int helperLeft = low;
	int helperRight = middle + 1;
	int current = low;
	
	while (helperLeft <= middle && helperRight <= high) { //times: 

            tripsSlots.get(helper[helperLeft].getOffset()).add(helper[helperLeft]);
            
            if (!validateTime(helper[helperRight])) {
                throw new OwnException(excetions.msg(2));
            }
            
            helper[helperLeft].setOffset(helper[helperLeft].getOffset()+1);
            
            helperLeft++;

            tripsSlots.get(helper[helperRight].getOffset()).add(helper[helperRight]);

            if (!validateTime(helper[helperRight])) {
                throw new OwnException(excetions.msg(2));
            }
            helper[helperRight].setOffset(helper[helperRight].getOffset()+1);
            helperRight++;

            current ++;
		
	}
	
    }
    
    /**
     * This method adds the time of agreement offSet
     * @param path 
     */
    public boolean validateTime(Path path){
        
        if ((((path.getOffset()) * 90) + ((path.getTotalWeight()/120)*3600000) + (2*WORSE_TIME_TO_GET_TO_THE_TOP)) 
                > timeExpected*3600000) {
            return false;
        }
        return true;
    }

    
}
