/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.Event;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author USUARIO
 */
public class painter implements Runnable{
    
    
    public Thread t;
    public ArrayList<Integer> coords;
    Graphics g;
    
    int pivot;
    int xA;
    int yA;
    int xB;
    int yB;
    
    int size = 8;
    int sleep = 10;
    Random random = new Random();
    
    public painter(ArrayList<Integer> coords,Graphics g){
        this.coords = coords;
        this.g = g;
        this.g.setColor(Color.BLACK);
        t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        try{
            for(int i = 0; i < coords.size() - 3; i += 4){
                xA = coords.get(i);
                yA = coords.get(i + 1);
                xB = coords.get(i + 2);
                yB = coords.get(i + 3);
                
                if(Math.abs(xA - xB) >= Math.abs(yA - yB)){
                    pivot = Math.abs(xA - xB) / Math.abs(yA - yB);
                    //pivot = Math.round(Math.abs(coords.get(i) - coords.get(i + 2)) / Math.abs(coords.get(i + 1) - coords.get(i + 3)));
                }
                if(Math.abs(xA - xB) <= Math.abs(yA - yB)){
                    pivot = Math.abs(yA - yB) / Math.abs(xA - xB);
                    //pivot = Math.round(Math.abs(coords.get(i + 1) - coords.get(i + 3)) / Math.abs(coords.get(i) - coords.get(i + 2)));
                }
                if(xA <= xB && yA <= yB){
                    if(Math.abs(xA - xB) >= Math.abs(yA - yB)){
                        // upper = 1 significa que el pivot se sacó de la diferencia de x > y
                        drawNorEste(xA, yA, xB, yB , pivot, 1);
                    }
                    else{
                        drawNorEste(xA, yA, xB, yB , pivot, 0);
                    }
                }
                else if(xA <= xB && yA >= yB){
                    if(Math.abs(xA - xB) >= Math.abs(yA - yB)){
                        // upper = 1 significa que el pivot se sacó de la diferencia de x > y
                        drawSurEste(xA, yA, xB, yB , pivot, 1);
                    }
                    else{
                        drawSurEste(xA, yA, xB, yB , pivot, 0);
                    }
                }else if(xA >= xB && yA >= yB){
                    if(Math.abs(xA - xB) >= Math.abs(yA - yB)){
                        // upper = 1 significa que el pivot se sacó de la diferencia de x > y
                        drawNorOeste(xA, yA, xB, yB , pivot, 1);
                    }
                    else{
                        drawNorOeste(xA, yA, xB, yB , pivot, 0);
                    }
                }else{
                    if(Math.abs(xA - xB) >= Math.abs(yA - yB)){
                        // upper = 1 significa que el pivot se sacó de la diferencia de x > y
                        drawSurOeste(xA, yA, xB, yB , pivot, 1);
                    }
                    else{
                        drawSurOeste(xA, yA, xB, yB , pivot, 0);
                    }  
                }  
            }
        }catch(Exception ex){
        }
        
        
    }
    
    
    
    public void drawNorEste(int x1, int y1, int x2, int y2, int pivot, int upper){
        
        int rep = 0;
        try{
        while(x1 <= x2 || y1 <= y2){
            Thread.sleep(sleep);
            this.g.fillOval(x1, y1, size, size);
            if(upper == 1){
                if(x1 <= x2){
                   x1++;
                }
                if(rep == pivot){
                    if(y1 <= y2){
                        y1++;
                    }
                    rep = 0;
                }
                rep++;
            }
            else{
                if(y1 <= y2){
                    y1++;
                }
                if(rep == pivot){
                    if(x1 <= x2){
                        x1++;
                    }
                    rep = 0;
                }
                rep++;      
            }
        }
        }catch(Exception ex){}   
    }
    
    public void drawSurEste(int x1, int y1, int x2, int y2, int pivot, int upper){
        
        int rep = 0;
        try{
        while(x1 <= x2 || y1 >= y2){
            Thread.sleep(sleep);
            this.g.fillOval(x1, y1, size, size);
            
            if(upper == 1){
                if(x1 <= x2){
                   x1++;
                }
                if(rep == pivot){
                    if(y1 >= y2){
                        y1--;
                    }
                    rep = 0;
                }
                rep++;
            }
            else{
                if(y1 >= y2){
                    y1--;
                }
                if(rep == pivot){
                    if(x1 <= x2){
                        x1++;
                    }
                    rep = 0;
                }
                rep++;      
            }
        }
        }catch(Exception ex){}   
    }
    
    
    public void drawSurOeste(int x1, int y1, int x2, int y2, int pivot, int upper){
        
        int rep = 0;
        try{
        while(x1 >= x2 || y1 <= y2){
            Thread.sleep(sleep);
            this.g.fillOval(x1, y1, size, size);
            
            if(upper == 1){
                if(x1 >= x2){
                   x1--;
                }
                if(rep == pivot){
                    if(y1 <= y2){
                        y1++;
                    }
                    rep = 0;
                }
                rep++;
            }
            else{
                if(y1 <= y2){
                    y1++;
                }
                if(rep == pivot){
                    if(x1 >= x2){
                        x1--;
                    }
                    rep = 0;
                }
                rep++;      
            }
        }
        }catch(Exception ex){}   
    }
    
    public void drawNorOeste(int x1, int y1, int x2, int y2, int pivot, int upper){
        
        int rep = 0;
        try{
        while(x1 >= x2 || y1 >= y2){
            Thread.sleep(sleep);
            this.g.fillOval(x1, y1, size, size);
            
            if(upper == 1){
                if(x1 >= x2){
                   x1--;
                }
                if(rep == pivot){
                    if(y1 >= y2){
                        y1--;
                    }
                    rep = 0;
                }
                rep++;
            }
            else{
                if(y1 >= y2){
                    y1--;
                }
                if(rep == pivot){
                    if(x1 >= x2){
                        x1--;
                    }
                    rep = 0;
                }
                rep++;      
            }
        }
        }catch(Exception ex){}   
    }
    
    
}
