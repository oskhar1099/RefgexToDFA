package regex_afd;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author oskha
 */

public class Nodo {

    String symbol;
    private Nodo parent;
    Nodo left;
    Nodo right;
    int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    private Set<Integer> firstPos;
    private Set<Integer> lastPos;
    private boolean nullable;

    public Nodo(String symbol) {
        this.symbol = symbol;
        parent = null;
        left = null;
        right = null;

        firstPos = new HashSet<>();
        lastPos = new HashSet<>();
        nullable = false;
    }

    
    public String getSymbol() {
        return symbol;
    }

    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    
    public Nodo getParent() {
        return parent;
    }

    
    public void setParent(Nodo parent) {
        this.parent = parent;
    }

    
    public Nodo getLeft() {
        return left;
    }

    
    public void setLeft(Nodo left) {
        this.left = left;
    }

    
    public Nodo getRight() {
        return right;
    }

    
    public void setRight(Nodo right) {
        this.right = right;
    }

    public void addToFirstPos(int number) {
        firstPos.add(number);
    }
    public void addAllToFirstPos(Set set) {
        firstPos.addAll(set);
    }

    public void addToLastPos(int number) {
        lastPos.add(number);
    }
    public void addAllToLastPos(Set set) {
        lastPos.addAll(set);
    }


    public Set<Integer> getFirstPos() {
        return firstPos;
    }

    
    public Set<Integer> getLastPos() {
        return lastPos;
    }

    
    public boolean isNullable() {
        return nullable;
    }

    
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
   
    
}
