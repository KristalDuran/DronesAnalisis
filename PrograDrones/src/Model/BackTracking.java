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
 * @author Kris
 */
public class BackTracking {
    
    Map<Path, Integer> trips;

    public BackTracking(Map<Path, Integer> trips, ArrayList<Path> totalPaths) {
        this.trips = trips;
        this.totalPaths = totalPaths;
        paths = new Path[trips.size()];
        lineaTiempo = new Path[trips.size()][1];
    }
    
    
    boolean exit = false;
    public boolean backtracking( int index, Path[] todos, int indexTodos){
        
        Path auxPath = null;
        
        if (indexTodos >= todos.length) 
            exit = false;
            
        while(indexTodos < todos.length || exit == false){  
            
            index++;       
            if (sePuedeInsertar( index, todos[indexTodos])) {
                if (indexTodos < todos.length-1) {
//                    System.out.println("Llamado recursivo " + (indexTodos+1) + "   "+todos.length);
                    
                    backtracking(-1, todos, indexTodos+1);
                }else
                    exit = true;    
                    break;
            }
        }
        return exit;
    }
    
    public boolean sePuedeInsertar(int index, Path path){
//        System.out.println("Valida si se puede agregar "+index);
        int i;
        for (i = 0; i < lineaTiempo[index].length; i++) {
//            System.out.println("for se puede ");
            if (lineaTiempo[index][i] != null) {
                if (!validarElementos(lineaTiempo[index][i], path)) {
//                    System.out.println("No se puede");
                    return false;
                }
            }
        }
        agregar( index, i, path);
        return true;        
    }
    
    
    public void agregar( int index, int i, Path path){
//        System.out.println("agregar");
        Path[] newA = new Path[(lineaTiempo[index].length)+1];
        for (int j = 0; j < lineaTiempo[index].length; j++) {
            newA[j] = lineaTiempo[index][j];
        }
        newA[(lineaTiempo[index].length)] = path;
        lineaTiempo[index] = newA;
    }
    
    Path[] paths;
    ArrayList<Path> totalPaths;
    Path[][] lineaTiempo;
    public void all(){
        
        int cont = 0;
        for (Path i :  totalPaths) {            
            if (trips.containsKey(i)) {
                paths[cont] = i;
                cont++;
            }
            
        }
        System.out.println("Inicia Backtraking\n");
        backtracking( -1, paths, 0);
        System.err.println("\n \n \nFinaliza");
        
        
        enviar();
    }
    
    public boolean validarElementos(Path path1, Path path2){
        if (path1.getPath().size() == path2.getPath().size()) {
            int finalPath2 = path2.getPath().size()-1;
            for (int i = 0; i < path1.getPath().size(); i++) {
//                System.out.println("Igual length \nPath1 "+path1.getPath().get(i)+" Path2 "+path2.getPath().get(finalPath2));
                if (path1.getPath().get(i) != path2.getPath().get(finalPath2)) {
                    return false;
                }
                if (i >= finalPath2) {
//                    System.out.println("los busca diferentes");
                    
                    break;
                }
                finalPath2--;
            }
        
        }else{
//            System.out.println("Difereten length");
            for (int i = 0; i < path1.getPath().size(); i++) {
                if (path2.getPath().contains(path1.getPath().get(i))) {
//                    System.out.println("No se puede agregar");
                    return false;
                }
            }
        }
//        System.out.println("Lo puede agregar");
        return true;
    }
    
    public void enviar(){
        int cont = 0;
        for (Path[] campos: lineaTiempo) {
            
                System.out.println("Se enviaran los siguientes viajes al mismo tiempo, slot: " +cont);
                for (Path path: campos) {
                    
                    if (path != null) {
                        
                        for (int punto: path.getPath()) {
                            System.out.print(punto + " ");
                        }
                        System.out.print("Tiempo " +trips.get(path));
                    }
                    
                    System.out.println("\n");
                }
                cont++;
//            if (campos[0] != null) {
//                break;
//            }
        }
        
    }
}
