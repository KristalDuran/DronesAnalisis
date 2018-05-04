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
public interface IConstants {
    public final static int SPEED = 120; //velocidad
    public final static int MILLISECOND = 1000;
    public final static double  WORSE_TIME_TO_GET_TO_THE_TOP = (double)(3.997/(double)SPEED)*3600000;
    public final static Exceptions excetions = new Exceptions(0);
}
