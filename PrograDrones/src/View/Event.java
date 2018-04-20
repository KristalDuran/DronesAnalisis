/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.GraphMethods;
import Model.Node;
import Model.Restriction;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

/**
 *
 * @author Kris
 */
public class Event extends javax.swing.JFrame {

    
    
    private int cant = 0;
    GraphMethods graphMethods;
    /**
     * Creates new form Event
     */
    public Event(GraphMethods pGraph) {
        graphMethods = pGraph;
        initComponents();
    }

    
    
    public void setCant(int cantidad){
        cant  = cantidad;
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
        Graphics g = planoCartesiano.getGraphics();
        g.drawLine(x, y, x2, y2);
    }
    
    public void fijarController(EventListener controller){
        jBCreate.addActionListener((ActionListener) controller);
        jBBack.addActionListener((ActionListener) controller);
        jBStar.addActionListener((ActionListener) controller);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jBCreate = new javax.swing.JButton();
        jBBack = new javax.swing.JButton();
        jBStar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        planoCartesiano = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Un calculo previo muestra que.....");

        jBCreate.setText("Create Graph");
        jBCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCreateActionPerformed(evt);
            }
        });

        jBBack.setText("Back");

        jBStar.setText("Star");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Message");

        planoCartesiano.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout planoCartesianoLayout = new javax.swing.GroupLayout(planoCartesiano);
        planoCartesiano.setLayout(planoCartesianoLayout);
        planoCartesianoLayout.setHorizontalGroup(
            planoCartesianoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        planoCartesianoLayout.setVerticalGroup(
            planoCartesianoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 693, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(planoCartesiano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jBCreate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBBack)
                                .addGap(36, 36, 36)
                                .addComponent(jBStar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel1)
                                .addGap(272, 272, 272)
                                .addComponent(jLabel2)))
                        .addGap(0, 963, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(planoCartesiano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBCreate)
                    .addComponent(jBBack)
                    .addComponent(jBStar))
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCreateActionPerformed
 // TODO add your handling code here:
        Dimension dimension = planoCartesiano.getSize();
        
        int width = dimension.width-40;
        int height = dimension.height-40;
        
        Graphics grafico = planoCartesiano.getGraphics();
        
        ArrayList<Restriction> restrictionsX = new ArrayList();
        ArrayList<Restriction> restrictionsY = new ArrayList();
        
        Random getRandom = new Random();
        
        int x = getRandom.nextInt(width);
        int y = getRandom.nextInt(height);
        
        for(int nodesToDraw = 0; nodesToDraw < cant; nodesToDraw++){
            
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
        System.out.println("end pinta nodos");
        graphMethods.MakeGraph(graphMethods);//crea los arcos
        drawArray(graphMethods.getLinesToDraw());//dibuja las lineas de los archos
        //graphMethods.setCantPistas();//calcula la cantidad de pistas y la cantidad de drones por pista
        graphMethods.setShortestPath();
    }//GEN-LAST:event_jBCreateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jBBack;
    public javax.swing.JButton jBCreate;
    public javax.swing.JButton jBStar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JPanel planoCartesiano;
    // End of variables declaration//GEN-END:variables
}
