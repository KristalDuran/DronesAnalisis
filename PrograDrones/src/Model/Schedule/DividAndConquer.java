/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.Exceptions;
import Model.Path;
import Model.Schedule.Schedule;
import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class DividAndConquer implements Schedule{

    @Override
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int time) throws Exceptions{
        ArrayList<ArrayList<Path>> tripsSlots = new ArrayList<ArrayList<Path>>();

//                sizeof(arr)/sizeof(arr[0])
        divide(totalPaths, 0, totalPaths.size()-1, tripsSlots);
        return tripsSlots;
    }

    public ArrayList<ArrayList<Path>> divide(ArrayList<Path> trips, int lk, int r, ArrayList<ArrayList<Path>> tripsSlots){
        if (lk < r){
            // Same as (l+r)/2, but avoids overflow for
            // large l and h
            System.out.println("r " + r);
            int m = lk+(r-lk)/2;

            // Sort first and second halves
            divide(trips, lk, m, tripsSlots);
            divide(trips, m+1, r, tripsSlots);

            unir(trips, lk, m, r, tripsSlots);
        }
        
        return null;
    }
    
    public void unir(ArrayList<Path> arr, int lk, int m, int r, ArrayList<ArrayList<Path>> tripsSlots){
        // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]

        int i, j, k;
        System.out.println("m " + m + "\nlk "+lk);
        int n1 = m - lk + 1;
        System.out.println("n1 " + n1);
        int n2 =  r - m;
        System.out.println("n2 " + n2);

        /* create temp arrays */
        ArrayList<Path> L = new ArrayList<>(n1);
        ArrayList<Path> R = new ArrayList<>(n2);

        /* Copy data to temp arrays L[] and R[] */
        for (i = 0; i < n1; i++)
            L.add(i, arr.get(lk + i));

        for (j = 0; j < n2; j++)
            R.add(j, arr.get(m + 1+ j));

        System.out.println("todos " + (n2+n1));
        if (tripsSlots.size() < n2+n1) {
            for (int n = 0; n <= n2+n1; n++) {
                tripsSlots.add(new ArrayList<>());
            }
                
        }
        /* Merge the temp arrays back into arr[l..r]*/
        i = 0; // Initial index of first subarray
        j = 0; // Initial index of second subarray
        k = lk; // Initial index of merged subarray
        while (i < n1 && j < n2)
        {
            // aca debo definir el path y decir si lo agrego a la lista en la posicion correcta y los sigo ordenando
            if (!tripsSlots.get(arr.get(i).getOffset()).contains(arr.get(i))) {
                tripsSlots.get(arr.get(i).getOffset()).add(arr.get(i));
                arr.get(i).setOffset(arr.get(i).getOffset()+1);
                System.out.println("i Agrego el path: " );
                for (int n = 0; n < arr.get(i).getPath().size(); n++) {
                    System.out.print(arr.get(i).getPath().get(n) + " ");
                }
                System.out.println("en la pocision " + arr.get(i).getOffset());
            }
            
            if (!tripsSlots.get(arr.get(j).getOffset()).contains(arr.get(j))) {
                tripsSlots.get(arr.get(j).getOffset()).add(arr.get(j));
                arr.get(j).setOffset(arr.get(j).getOffset()+1);
                System.out.println("j Agrego el path: " );
                for (int n = 0; n < arr.get(j).getPath().size(); n++) {
                    System.out.print(arr.get(j).getPath().get(n) + " ");
                }
                System.out.println("en la pocision " + arr.get(j).getOffset());
            }
    //        if (L.get(i) <= R.get(j))
    //        {
    //            arr[k] = L[i];
    //            i++;
    //        }
    //        else
    //        {
    //            arr[k] = R[j];
    //            j++;
    //        }
    //        k++;
        }

        /* Copy the remaining elements of L[], if there
           are any */
    //    while (i < n1)
    //    {
    //        arr[k] = L[i];
    //        i++;
    //        k++;
    //    }
    // 
    //    /* Copy the remaining elements of R[], if there
    //       are any */
    //    while (j < n2)
    //    {
    //        arr[k] = R[j];
    //        j++;
    //        k++;
    //    }
}
 

    
    
}
