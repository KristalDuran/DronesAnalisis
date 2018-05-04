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
import javax.swing.JPanel;

/**
 *
 * @author USUARIO
 */
public class ThreadManager implements Runnable{

    Thread t;
    JPanel g;
    ArrayList<ArrayList<Integer>> toDraw;
    ArrayList<Integer> linesToDraw;
    Node nodos[];

    public ThreadManager(JPanel plano, ArrayList<ArrayList<Integer>> toDraw, ArrayList<Integer> linesToDraw, Node[] nodos) {
        this.g = plano;
        this.toDraw = toDraw;
        this.linesToDraw = linesToDraw;
        this.nodos = nodos;
        new GraphPainter(nodos, linesToDraw, plano.getGraphics());
        this.t = new Thread(this);
        t.start();
    }
    
    public void run() {
        try {
            for(int i = 0; i < toDraw.size(); i++){
                new Painter(toDraw.get(i),g.getGraphics());
                Thread.sleep(33);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
