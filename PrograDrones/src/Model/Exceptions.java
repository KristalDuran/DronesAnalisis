/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Kris
 */
public class Exceptions extends Exception{
    
    private String[] arrayExceptions = {"Se ha ingresado un dato que no es numérico",
                                        "El tamaño de las pistas es demasiado pequeño con respecto a la cantidad de estaciones",
                                        "No es posible enviar la cantidad de drones solicitados en el tiempo asignado"};
    private int numberException;
    
    public Exceptions(int numExcpetion) {
        String message = msg(numExcpetion);
        
    }
    
    public Exceptions(String msg){
        super(msg);
    }
    
    /**
     * Take the message of the expecion
     * @param num
     * @return 
     */
    public String msg(int num){
        return arrayExceptions[num];
    }
    
    public boolean isNumeric(String value){
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
