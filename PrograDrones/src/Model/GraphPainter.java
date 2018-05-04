/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author USUARIO
 */
public class GraphPainter implements Runnable{

    public Thread t;
    
    Node nodos[];
    ArrayList<Integer> linesToDraw;
    Graphics g;
    
    public GraphPainter(Node nodos[], ArrayList<Integer> linesToDraw, Graphics g){
        this.nodos= nodos;
        this.linesToDraw = linesToDraw;
        this.g = g;
        t = new Thread(this);
        t.start();
    }
    
    public void run() {
        while(true){
            try {
                Thread.sleep(120);
                g.clearRect(0, 0, 2000, 2000);
                drawNodes(g);
                drawLines(g);
            } catch (InterruptedException ex) {
                Logger.getLogger(GraphPainter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void drawNodes(Graphics g){
    
        for(Node node:this.nodos){
            g.drawOval(node.getX(), node.getY(), 20, 20);
            g.drawString(node.getName().toString(), node.getX() + 4, node.getY() + 15);
            g.drawString("[" + node.getX() +","+ node.getY() + "]", node.getX(), node.getY());
        }
        
        
    }
    
    
    public void drawLines(Graphics g){
        for(int drawPoints = 0; drawPoints <= linesToDraw.size()-3;drawPoints += 4){
            g.drawLine(linesToDraw.get(drawPoints),linesToDraw.get(drawPoints+1),linesToDraw.get(drawPoints+2),linesToDraw.get(drawPoints+3));
        }
    
    }
    
}
