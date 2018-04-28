/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GraphMethods;
<<<<<<< HEAD
import Model.TripMethods;
=======
import Model.Path;
>>>>>>> 066d8d74284dd4983ee201df0a0916ccd12a0153
import View.Menu;
import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class Main {
    public static void main(String[] args){
        
        Menu menu = new Menu();
        
        menu.fijarControlador(new ControllerMenu(menu, new GraphMethods(), new TripMethods()));
        
        
        ArrayList<ArrayList<Path>> asd = new ArrayList();
        asd.set(0,new ArrayList());
        asd.get(0).add(new Path());
        for(int i = 0; i < asd.size(); i++){
            for(Path j:asd.get(i)){
                System.out.println(j.getTotalWeight());
            }
        }
        
        //System.out.println(asd.get(0).get(0).getTotalWeight());
        
        
        
        
        
        
        
        
    }
}
