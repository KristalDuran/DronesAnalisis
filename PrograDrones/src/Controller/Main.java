/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GraphMethods;
import Model.TripMethods;
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
        
        
    }
}
