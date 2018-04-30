/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GraphMethods;
import Model.TripMethods;
import Model.Path;
import Model.painter;
import View.Event;
import View.Menu;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class Main {
    
    public static void main(String[] args){
        
        Menu menu = new Menu();
        
        menu.fijarControlador(new ControllerMenu(menu, new GraphMethods(), new TripMethods()));
        
        //menu.fijarModelo(graphMethods);  

//    painter a;
//    Event ventana = new Event();
//    ventana.setVisible(true);
//    ArrayList<Integer> b = new ArrayList();
//    b.add(0);
//    b.add(0);
//    
//    b.add(100);
//    b.add(300);
//    
//    b.add(100);
//    b.add(300);
//    
//    b.add(50);
//    b.add(50);
//    
//    b.add(50);
//    b.add(50);
//    
//    b.add(300);
//    b.add(200);
//    
//    b.add(300);
//    b.add(200);
//    
//    b.add(50);
//    b.add(300);
//    new painter(2,b,ventana.planoCartesiano.getGraphics());
    
  
    //for(int i = 0; i < 1; i++){
        
    //}
    
//    ArrayList<Integer> c = new ArrayList();
//    c.add(100);
//    c.add(100);
//    
//    c.add(200);
//    c.add(200);
//    
//    
//        new painter(2,c,ventana.planoCartesiano.getGraphics());
    }
}
