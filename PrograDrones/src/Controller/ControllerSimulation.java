/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BackTracking;
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
        if (e.getSource() == simulacion.jBExit) {
            simulacion.setVisible(false);
        }
        if (e.getSource() == simulacion.jBStart) {
            if (simulacion.jRadioButton1.isSelected()) {
                //correr con divide
            }else{
                if (simulacion.jRadioButton2.isSelected()) {
//                    BackTracking back = new BackTracking(graphMethods.getViajesExactos(), graphMethods.getTotalPaths(), 
//                            graphMethods.getWorstTimeCase(), graphMethods.calculateNumOfDronesBySet());
//                    back.ControllerBackTracking();
                }
            }
        }
        
    }
    
}
