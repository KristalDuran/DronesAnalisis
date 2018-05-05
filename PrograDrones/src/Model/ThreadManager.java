/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author USUARIO
 */
public class ThreadManager implements Runnable{

    private Thread t;
    private JPanel g;
    private ArrayList<ArrayList<Integer>> toDraw;
    private ArrayList<Integer> linesToDraw;
    private Node nodos[];
    private JLabel numberOfDronesByNow;
    private JLabel tripsByTheMoment;
    private int dronesBySet;
    private int totalDrones;
    private int sleep = 33;
    private int hourInRealSeconds;
    private int tripSent = 0;
    

    public ThreadManager(JLabel tripsByTheMoment,JLabel numberOfDronesByNow,JPanel plano, ArrayList<ArrayList<Integer>> toDraw, ArrayList<Integer> linesToDraw, Node[] nodos,int dronesBySet,int totalDrones,int hourInRealSeconds) {
        this.g = plano;
        this.toDraw = toDraw;
        this.linesToDraw = linesToDraw;
        this.nodos = nodos;
        this.numberOfDronesByNow = numberOfDronesByNow;
        this.dronesBySet = dronesBySet;
        this.totalDrones = totalDrones;
        this.numberOfDronesByNow.setText("0");
        this.sleep = sleep * hourInRealSeconds;
        this.hourInRealSeconds = hourInRealSeconds;
        this.tripsByTheMoment = tripsByTheMoment;
        new GraphPainter(nodos, linesToDraw, plano.getGraphics());
        this.t = new Thread(this);
        t.start();
    }
    
    public void run() {
        try {
            for(int i = 0; i < toDraw.size(); i++){
                new Painter(toDraw.get(i),g.getGraphics(),hourInRealSeconds,numberOfDronesByNow,dronesBySet,totalDrones);
                tripSent++;
                tripsByTheMoment.setText(Integer.toString(tripSent));
                Thread.sleep(sleep);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
