/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.GraphMethods;
import Model.Path;
import Model.Restriction;
import Model.Schedule.BackTracking;
import Model.Schedule.DividAndConquer;
import Model.Schedule.Probabilistic;
import Model.TripMethods;
import View.Event;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kris
 */
public class ControllerEvent implements ActionListener{

    Event event;
    GraphMethods graphMethods;
    TripMethods tripMethods;
    int time;
    
    public ControllerEvent(Event event, GraphMethods graphMethods, TripMethods tripMethods, int time) {
        this.event = event;
        this.graphMethods = graphMethods;
        this.tripMethods = tripMethods;
        this.time = time;
        event.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == event.jBBack) {
            event.setVisible(false);
            
        } else if (e.getSource() == event.jBStar) {
            createGraph();
            tripMethods.setTotalPaths(graphMethods.setShortestPath());
            ArrayList<Path> arr = tripMethods.calculateTrip(graphMethods.getNodes());
            if (event.jRadioDivide.isSelected()) {
                DividAndConquer divide = new DividAndConquer();
                divide.AirTrafficController(arr, time);
                
            }else{
                if (event.jRadioProbabilistic.isSelected()) {
                    Probabilistic probabilistic = new Probabilistic(arr, time); //arreglar
                    probabilistic.AirTrafficController(arr, time);
                    
                }else if (event.jRadioBackTracking.isSelected()) {
                    BackTracking back = new BackTracking();
                    back.AirTrafficController(arr, time);
                }
            }
            
        }
        
    }
    
    private boolean isAllowToPlace(ArrayList<Restriction> restrictions, int toEvaluate){
        
        for(Restriction restriction: restrictions){
            if(restriction.isRestriction(toEvaluate)){
                return false;
            }
        }
        return true;
    }
    
    public void drawArray(ArrayList<Integer> linesToDraw){
        for(int drawPoints = 0; drawPoints <= linesToDraw.size()-3;drawPoints += 4){
            drawLine(linesToDraw.get(drawPoints),linesToDraw.get(drawPoints+1),linesToDraw.get(drawPoints+2),linesToDraw.get(drawPoints+3));
        }
    }
    
    public void drawLine(int x, int y, int x2, int y2){
        Graphics g = event.planoCartesiano.getGraphics();
        g.drawLine(x, y, x2, y2);
    }
    
    
    
    
    
    public void createGraph(){
        Dimension dimension = event.planoCartesiano.getSize();
        
        int width = dimension.width-40;
        int height = dimension.height-40;
        
        Graphics grafico = event.planoCartesiano.getGraphics();
        
        grafico.clearRect(0, 0, width + 40, height + 40);
        
        ArrayList<Restriction> restrictionsX = new ArrayList();
        ArrayList<Restriction> restrictionsY = new ArrayList();
        
        Random getRandom = new Random();
        
        int x = getRandom.nextInt(width);
        int y = getRandom.nextInt(height);
        
        for(int nodesToDraw = 0; nodesToDraw < graphMethods.getNumberOfStations(); nodesToDraw++){
            
            while(!isAllowToPlace(restrictionsX,x) && !isAllowToPlace(restrictionsY,y)){
                x = getRandom.nextInt(width)+20;
                y = getRandom.nextInt(height)+20;
            }
            
            restrictionsX.add(new Restriction(x-30,x+30));
            restrictionsY.add(new Restriction(y-30,y+30));
            
            graphMethods.MakeStation(nodesToDraw+1, x, y);
            //nodesToDraw+1 para no iniciar en 0
            grafico.drawOval(x,y,20,20);
            String numberOfNode = "" + (nodesToDraw+1);
            grafico.drawChars(numberOfNode.toCharArray(),0, numberOfNode.toCharArray().length, x+4, y+15);
            //guardar en un array los puntos x+10,y+10///////////////////////////////////////////////
        }
        graphMethods.MakeGraph(graphMethods);//crea los arcos
        drawArray(graphMethods.getLinesToDraw());//dibuja las lineas de los archos
        //graphMethods.setCantPistas();
//        graphMethods.setShortestPath();
    
    }
    
    
    
   
}
