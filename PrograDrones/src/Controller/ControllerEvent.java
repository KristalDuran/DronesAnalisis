/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.OwnException;
import Model.Graph;
import Model.GraphMethods;
import Model.Node;
import Model.Path;
import Model.Restriction;
import Model.Schedule.BackTracking;
import Model.Schedule.DividAndConquer;
import Model.Schedule.Probabilistic;
import Model.TripMethods;
import Model.ThreadManager;
import View.Event;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Kris
 */
public class ControllerEvent implements ActionListener{

    private Event event;
    private GraphMethods graphMethods;
    private TripMethods tripMethods;
    private int time;
    

    
    public ControllerEvent(Event event, GraphMethods graphMethods, TripMethods tripMethods) {
        this.event = event;
        this.graphMethods = graphMethods;
        this.tripMethods = tripMethods;
        this.time = tripMethods.timeProx;
        event.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == event.jBBack) {
                event.setVisible(false);

            } else if (e.getSource() == event.jBStar) {
                createGraph();
                tripMethods.setTotalPaths(graphMethods.setShortestPath());
                ArrayList<Path> arr = tripMethods.calculateTrip(graphMethods.getNodes());
                
                if (event.jRadioDivide.isSelected()) {
                    DividAndConquer divide = new DividAndConquer();
                    
                    long INICIO_MS = System.currentTimeMillis();
                    ArrayList<ArrayList<Path>> result = divide.AirTrafficController(arr, time);
                    long DURACION_MS = System.currentTimeMillis() - INICIO_MS;
                    
                    setValuesInView(result, DURACION_MS);
                    
                }else{
                    if (event.jRadioProbabilistic.isSelected()) {
                        Probabilistic probabilistic = new Probabilistic(); //arreglar
                        
                        long INICIO_MS = System.currentTimeMillis();
                        ArrayList<ArrayList<Path>> result = probabilistic.AirTrafficController(arr, time);
                        long DURACION_MS = System.currentTimeMillis() - INICIO_MS;
                        
                        setValuesInView(result, DURACION_MS);

                    }else if (event.jRadioBackTracking.isSelected()) {
                        BackTracking back = new BackTracking();
                        
                        long INICIO_MS = System.currentTimeMillis();
                        ArrayList<ArrayList<Path>> result = back.AirTrafficController(arr, time);
                        long DURACION_MS = System.currentTimeMillis() - INICIO_MS;
                        setValuesInView(result, DURACION_MS);
                        
                    }
                }

            }
        } catch (OwnException exception) {
            event.setVisible(false);
            graphMethods.setGraph(new Graph());
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
        
        
    }
    
    public void setValuesInView(ArrayList<ArrayList<Path>> result, long DURACION_MS){
        
        new ThreadManager(event.tripsByTheMoment,event.numberOfDronesByTheMoment,event.planoCartesiano,prepareResultToMakeTheGraphics(result),graphMethods.getLinesToDraw(),graphMethods.getNodes(),(int)tripMethods.getCantDronesByIndividualTrip(),tripMethods.getNumberOfTrips(), tripMethods.getTimeReal());
        event.leftoverTime.setText(Integer.toString(tripMethods.getTimeRemaining(result, time)));
        event.fitTrips.setText(Double.toString(DURACION_MS));
        event.record.setText("Drones por set: " + (int)tripMethods.getCantDronesByIndividualTrip() + "\n" + tripMethods.soutResult(result));
    
    }
    
    
    public ArrayList<ArrayList<Integer>> prepareResultToMakeTheGraphics(ArrayList<ArrayList<Path>> result){
        ArrayList<ArrayList<Integer>> toDraw = new ArrayList();
        Path temp;
        Node node;
        int indexInsert = 0;
        for(int getIOfPath = 0; getIOfPath < result.size();getIOfPath++){
            for(int getJOfPath = 0; getJOfPath < result.get(getIOfPath).size();getJOfPath++){
                //System.out.println("j vale:" + j);
                temp = result.get(getIOfPath).get(getJOfPath);
                //System.out.println("viaje:" + temp.getPath().toString());
                if(toDraw.size() <= indexInsert){
                    for(int resize = 0; resize <= (indexInsert - toDraw.size());resize++){
                        toDraw.add(new ArrayList<Integer>());
                    }
                }
                
                node = graphMethods.getGraph().getNodeByName(temp.getPath().get(0));
                toDraw.get(indexInsert).add(node.getX());
                toDraw.get(indexInsert).add(node.getY());

                for(int getCoordsOfJumps = 1; getCoordsOfJumps < temp.getPath().size() - 1;getCoordsOfJumps++){
                    node = graphMethods.getGraph().getNodeByName(temp.getPath().get(getCoordsOfJumps));
                    toDraw.get(indexInsert).add(node.getX());
                    toDraw.get(indexInsert).add(node.getY());
                    toDraw.get(indexInsert).add(node.getX());
                    toDraw.get(indexInsert).add(node.getY());
                }

                node = graphMethods.getGraph().getNodeByName(temp.getPath().get(temp.getPath().size() - 1));
                toDraw.get(indexInsert).add(node.getX());
                toDraw.get(indexInsert).add(node.getY());
                indexInsert++;
            }
        }
        
        return toDraw;
    
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
            grafico.drawOval(x,y,20,20);
            String numberOfNode = "" + (nodesToDraw+1);
            grafico.drawChars(numberOfNode.toCharArray(),0, numberOfNode.toCharArray().length, x+4, y+15);
        }
        graphMethods.MakeGraph(graphMethods);
        drawArray(graphMethods.getLinesToDraw());
    
    }
    
    
    
   
}
