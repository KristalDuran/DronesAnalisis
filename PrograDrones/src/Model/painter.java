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
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author USUARIO
 */
public class Painter implements Runnable{
    
    
    private Thread t;
    private ArrayList<Integer> coords;
    private Graphics g;
    
    private int pivot;
    private int xA;
    private int yA;
    private int xB;
    private int yB;
    
    private int dotSize = 10;
    
    private Random random = new Random();
    private int sleep = 8;
    private int dronesBySet;
    private int totalDrones;
    private JLabel numberOfDronesByNow;
    
    public Painter(ArrayList<Integer> coords,Graphics g,int hourInRealSeconds,JLabel numberOfDronesByNow,int dronesBySet,int totalDrones){
        this.coords = coords;
        this.g = g;
        Color rand = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255), 255);
        this.g.setColor(rand);
        this.sleep = sleep * hourInRealSeconds;
        this.numberOfDronesByNow = numberOfDronesByNow;
        this.dronesBySet = dronesBySet;
        this.totalDrones = totalDrones; 
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
                }
                if(Math.abs(xA - xB) <= Math.abs(yA - yB)){
                    pivot = Math.abs(yA - yB) / Math.abs(xA - xB);
                }
                if(xA <= xB && yA <= yB){
                    if(Math.abs(xA - xB) >= Math.abs(yA - yB)){
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
            int currentNumber = Integer.parseInt(numberOfDronesByNow.getText()) + dronesBySet;
            if(currentNumber > totalDrones){
                currentNumber = currentNumber - (currentNumber - totalDrones);
                numberOfDronesByNow.setText(Integer.toString(currentNumber));
            }
            else{
                numberOfDronesByNow.setText(Integer.toString(currentNumber));
            }
            
        }catch(Exception ex){
        }
        
        
    }
    
    
    
    public void drawNorEste(int x1, int y1, int x2, int y2, int pivot, int upper){
        
        int rep = 0;
        try{
        while(x1 <= x2 || y1 <= y2){
            Thread.sleep(sleep);
            this.g.fillOval(x1, y1, dotSize, dotSize);
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
            this.g.fillOval(x1, y1, dotSize, dotSize);
            
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
            this.g.fillOval(x1, y1, dotSize, dotSize);
            
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
            this.g.fillOval(x1, y1, dotSize, dotSize);
            
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
    
    
    public void soutDistancia(){
        int distance = 0;
        for(int i = 0; i < coords.size() - 3; i += 4){
            distance = (int) (distance + Math.sqrt(Math.pow(coords.get(i) - coords.get(i + 2),2) + Math.pow(coords.get(i + 1) - coords.get(i + 3),2)));
        }
        System.out.println("distancia: " + distance);
    }
    
    
}
