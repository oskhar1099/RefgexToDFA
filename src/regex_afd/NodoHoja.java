/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex_afd;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author oskha
 */
public class NodoHoja extends Nodo {

    private int num;
    private Set<Integer> siguientePos;

    public NodoHoja(String symbol) {
        super(symbol);
        siguientePos = new HashSet<>();
    }

    
    public int getNum() {
        return num;
    }

    
    public void setNum(int num) {
        this.num = num;
    }
    
    public void addTosiguientePos(int number){
        siguientePos.add(number);
    }

    
    public Set<Integer> getSiguientePos() {
        return siguientePos;
    }

   
    public void setsiguientePos(Set<Integer> siguientePos) {
        this.siguientePos = siguientePos;
    }
}
