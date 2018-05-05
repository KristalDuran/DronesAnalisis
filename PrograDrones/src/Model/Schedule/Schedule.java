/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Schedule;

import Model.OwnException;
import Model.Path;
import java.util.ArrayList;

/**
 *
 * @author Kris
 */
public interface Schedule{
    
    public ArrayList<ArrayList<Path>> AirTrafficController(ArrayList<Path> totalPaths, int time) throws OwnException;
}
