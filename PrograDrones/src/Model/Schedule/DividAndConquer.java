/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.Exceptions;
import Model.Path;
import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class DividAndConquer implements Schedule{
ArrayList<ArrayList<Path>> tripsSlots = new ArrayList<ArrayList<Path>>();
    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int time) throws Exceptions{

        divide(totalPaths, 0, totalPaths.size()-1);
        for (int i = 0; i < tripsSlots.size(); i++) {
            for (int j = 0; j < tripsSlots.get(i).size(); j++) {
                System.out.println("Viaje en tiempo: " + i);
                for (int k = 0; k < tripsSlots.get(i).get(j).getPath().size(); k++) {
                    System.out.println(tripsSlots.get(i).get(j).getPath().get(k) + " ");
                }
            }
        }
        return tripsSlots;
    }

    public ArrayList<ArrayList<Path>> divide(ArrayList<Path> trips, int low, int high){
        if (low < high){
            
            int middle = (low + high) /2;
            
            if(Math.abs(low - high) == 1){
                unir(trips,low,middle,high);
                return null;
            }
            
            divide(trips, low, middle);

            divide(trips, middle+1, high);

            //unir(trips, low, middle, high);
        }
        
        return null;
    }
    
    public void unir(ArrayList<Path> arr, int low, int middle, int high){

        Path[] helper = new Path[arr.size()];
	for (int i = low; i <= high; i++) {
		helper[i] = arr.get(i);
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
            if (!tripsSlots.get(arr.get(helperLeft).getOffset()).contains(arr.get(helperLeft))) {
                tripsSlots.get(arr.get(helperLeft).getOffset()).add(arr.get(helperLeft));
                arr.get(helperLeft).setOffset(arr.get(helperLeft).getOffset()+1);
//                    arr.remove(arr.get(helperLeft));
                System.out.println("i Agrego el path: " );
                for (int n = 0; n < arr.get(helperLeft).getPath().size(); n++) {
                    System.out.print(arr.get(helperLeft).getPath().get(n) + " ");
                }
                System.out.println("en la pocision " + arr.get(helperLeft).getOffset()+"\n");
                helperLeft++;
            }
            
            if (!tripsSlots.get(arr.get(helperRight).getOffset()).contains(arr.get(helperRight))) {
                tripsSlots.get(arr.get(helperRight).getOffset()).add(arr.get(helperRight));
                arr.get(helperRight).setOffset(arr.get(helperRight).getOffset()+1);
//                    arr.remove(arr.get(helperRight));
                System.out.println("j Agrego el path: " );
                for (int n = 0; n < arr.get(helperRight).getPath().size(); n++) {
                    System.out.print(arr.get(helperRight).getPath().get(n) + " ");
                }
                System.out.println("en la pocision " + arr.get(helperRight).getOffset()+"\n");
                helperRight++;
            }
            current ++;
//            }


            
            //helper[helperLeft].equals(helper[helperRight].getOffset())
//            if(helper[helperLeft].getOffset() <= helper[helperRight].getOffset()){
//                arr.remove(helper[helperLeft]);
//                    arr.add(current, helper[helperLeft]);
//                    
////			array[current] = helper[helperLeft];
//                    helperLeft++;
//
//            }else{
//                arr.remove(helper[helperRight]);
//                    arr.add(current, helper[helperRight]);
////			array[current] = helper[helperRight];
//                    helperRight++;
//            }
//            current ++;		
	}
	
//	int remaining = middle - helperLeft;
//	for (int i = 0; i <= remaining; i++) {
//            arr.add(current+1, helper[helperLeft+ i]);
////		array[current+i] = helper[helperLeft+ i];
//	}
//    
 
    }
 

    
}
