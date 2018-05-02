/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USUARIO
 */
public class TripVariables {
    public int numberOfStations;
    public int numberOfTrips;
    public int trackHeight;
    public int trackWidth;
    public ArrayList<Path> totalPaths;
    public ArrayList<Object> tiemposRestriction = new ArrayList<>(); //se van a guarda de tres en tres: path, el punto restringido y el tiempo en que va a estar ahi
    
    public Map<Path, Integer> viajesExactos = new HashMap<>();
    
    public ArrayList<Path> trips = new ArrayList<>();
    
    public int timeReal;
    public int timeProx;
    public int cantDronesXPista;
  
    public double cantDronesByIndividualTrip;
}
