/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.GraphMethods;
import Model.TripMethods;
import Model.TripVariables;
import View.Event;
import View.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Kris
 */
public class ControllerMenu implements ActionListener {
    private Menu Menu;
    private GraphMethods graphMethods;
    private TripMethods tripMethods;
    private TripVariables tripVariables;

    public ControllerMenu(Menu Menu, GraphMethods graphMethods) {
        this.Menu = Menu;
        this.graphMethods = graphMethods;
        Menu.setVisible(true);
        
    }    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        tripMethods = new TripMethods();
        
        graphMethods.setNumberOfTracksByStation(Integer.parseInt(Menu.SizePista.getText()));
        graphMethods.setNumberOfStations(Integer.parseInt(Menu.CantStation.getText()));
        
        tripMethods.numberOfStations = Integer.parseInt(Menu.CantStation.getText());
        tripMethods.setPistaHeight(Integer.parseInt(Menu.txtHeight.getText()));
        tripMethods.setPistaWidth(Integer.parseInt(Menu.txtWidth.getText()));
        tripMethods.setNumberOfTrips(Integer.parseInt(Menu.CantViaje.getText()));
        
        tripMethods.setTimeReal(Integer.parseInt(Menu.TimeReal.getText()));
        tripMethods.setTimeProx(Integer.parseInt(Menu.TimeProx.getText()));
        
        Event event = new Event();
        graphMethods.setNodes();
        event.fijarController(new ControllerEvent(event, graphMethods, tripMethods));
    }
    
}
