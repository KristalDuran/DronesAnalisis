/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GraphMethods;
import View.Simulacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Kris
 */
public class ControllerSimulation implements ActionListener{

    Simulacion simulacion;
    GraphMethods graphMethods;

    public ControllerSimulation(Simulacion simulacion, GraphMethods graphMethods) {
        this.simulacion = simulacion;
        this.graphMethods = graphMethods;
        this.simulacion.setVisible(true);
    }
      
        
    @Override
    public void actionPerformed(ActionEvent e) {
        simulacion.setVisible(false);
    }
    
}
