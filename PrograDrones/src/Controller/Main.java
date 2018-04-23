/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GraphMethods;
import Model.Restriction;
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


//        Restriction hashRestrictionsByStation[][] = new Restriction[30][30];
//        Restriction as[] = hashRestrictionsByStation[0];
//        for(Restriction b:as){
//            System.out.println(b);
//        }
//        System.out.println("----------------------------");
//        for(int a = 0; a < hashRestrictionsByStation.length; a++ ){
//            for(int j = 0; j < hashRestrictionsByStation[0].length; j++){
//                System.out.println(hashRestrictionsByStation[a][j]);
//            }
//        }
    }
}
