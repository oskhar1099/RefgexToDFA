/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex_afd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author oskha
 */
public class Estado {
    
    private int ID;
    private Set<Integer> name;
    private HashMap<String, Estado> move;
    
    private boolean IsAcceptable;
    private boolean IsMarked;
    private String estado;
    public Estado(){
        move = new HashMap<>();
        name = new HashSet<>();
        IsAcceptable = false;
        IsMarked = false;
    }
    
    public void mueve(String symbol, Estado s){
        move.put(symbol, s);
    }
    
    public void addToName(int number){
        name.add(number);
    }
    public void addAllToName(Set<Integer> number){
        name.addAll(number);
    }
    
    public void setIsMarked(boolean bool){
        IsMarked = bool;
    }
    
    public boolean getIsMarked(){
        return IsMarked;
    }
    
    public Set<Integer> getName(){
        return name;
    }

    public void setAccept() {
        IsAcceptable = true;
    }
    
    public boolean getIsAcceptable(){
        return  IsAcceptable;
    }
    
    public Estado getNextStateBySymbol(String str){
        return this.move.get(str);
    }
    
    public HashMap<String, Estado> getAllMoves(){
        return move;
    }
    public void setEstado(String estado){
        this.estado=estado;
    }

    public String getEstado() {
        return estado;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
