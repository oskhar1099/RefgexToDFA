/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex_afd;

import java.util.Set;

/**
 *
 * @author oskha
 */
public class AFDTrans {
    
    private final Estado q0;
    private Estado curr;
    private char c;
    private final Set<String> entrada;
    
    public AFDTrans(Estado q0, Set<String> entrada){
        this.q0 = q0;
        this.curr = this.q0;
        this.entrada = entrada;
    }
    
    public boolean setCharacter(char c){
        if (!entrada.contains(c+"")){
            return false;
        }
        this.c = c;
        return true;
    }
    
    public boolean traverse(){
        curr = curr.getNextStateBySymbol(""+c);
        return curr.getIsAcceptable();
    }
}
