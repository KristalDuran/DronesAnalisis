/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GraphMethods;
import Model.Path;
import View.Menu;
import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public class Main {
    public static void main(String[] args){
        
        Menu menu = new Menu();
        
        GraphMethods graphMethods = new GraphMethods();
       
        menu.fijarControlador(new ControllerMenu(menu, graphMethods));
        
        menu.fijarModelo(graphMethods);
        
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
