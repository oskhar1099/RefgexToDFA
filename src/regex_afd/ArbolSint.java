package regex_afd;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author oskha
 */
public class ArbolSint {

    private String regex;
    private ArbolB bt;
    private Nodo raiz; 
    private int numOfLeafs;
    private Set<Integer> siguientePos[];
    int ajuste=0;

    public ArbolSint(String regex) {
        this.regex = regex;
        bt = new ArbolB();
        
       
        raiz = bt.generateTree(regex);
        numOfLeafs = bt.getNumberOfLeafs();
        siguientePos = new Set[numOfLeafs];
        for (int i = 0; i < numOfLeafs; i++) {
            siguientePos[i] = new HashSet<>();
        }
       
        generarAnulable(raiz);
        generarFPosyUpos(raiz);
        generarSiguientePos(raiz);
    }

    private void generarAnulable(Nodo node) {
        if (node == null) {
            return;
        }
        if (!(node instanceof NodoHoja) || "&".equals(node.symbol)) {
            Nodo left = node.getLeft();
            Nodo right = node.getRight();
            generarAnulable(left);
            generarAnulable(right);
            switch (node.getSymbol()) {
                case "|":
                        node.setNullable(left.isNullable() | right.isNullable());
                    break;
                case ".":
                        node.setNullable(left.isNullable() & right.isNullable());
                    break;
                case "*":
                    node.setNullable(true);
                    break;
                case "+":
                    node.setNullable(left.isNullable());
                    break;
                case "?":
                    node.setNullable(true);
                    break;
                case "&":
                    node.setNullable(true);
                    break;
            }
        }
    }

    private void generarFPosyUpos(Nodo node) {
        if (node == null) {
            return;
        }
        
        if (node instanceof NodoHoja && !"&".equals(node.getSymbol())) {
            
            NodoHoja lnode = (NodoHoja) node;
                
                node.addToFirstPos(lnode.getPosition());
                node.addToLastPos(lnode.getPosition());
            
        } else {            
            Nodo left = node.getLeft();
            Nodo right = node.getRight();
            generarFPosyUpos(left);
            generarFPosyUpos(right);
            switch (node.getSymbol()) {
                case "|":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToFirstPos(right.getFirstPos());
                    //
                    node.addAllToLastPos(left.getLastPos());
                    node.addAllToLastPos(right.getLastPos());
                    break;
                case ".":
                    if (left.isNullable() || "&".equals(left.symbol)) {
                        node.addAllToFirstPos(left.getFirstPos());
                        node.addAllToFirstPos(right.getFirstPos());
                    } else {

                        node.addAllToFirstPos(left.getFirstPos());
                    }
                    //
                    if (right.isNullable() || "&".equals(right.symbol)) {
                        node.addAllToLastPos(left.getLastPos());
                        node.addAllToLastPos(right.getLastPos());
                    } else {
                        node.addAllToLastPos(right.getLastPos());
                    }
                    break;
                case "*":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    break;
                case "?":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    break;
                case "+":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    break;
            }
        }
    }

    private void generarSiguientePos(Nodo node) {
        if (node == null) {
            return;
        }
        Nodo left = node.getLeft();
        Nodo right = node.getRight();
        switch (node.getSymbol()) {
            case ".":
                Object lastpos_c1[] = left.getLastPos().toArray();
                Set<Integer> firstpos_c2 = right.getFirstPos(); 
                for (int i = 0; i < lastpos_c1.length; i++) {
                    siguientePos[(Integer) lastpos_c1[i] - 1].addAll(firstpos_c2);
                }
                break;
            case "*":
                Object lastpos_n[] = node.getLastPos().toArray();
                Set<Integer> firstpos_n = node.getFirstPos();
                for (int i = 0; i < lastpos_n.length; i++) {
                    siguientePos[(Integer) lastpos_n[i] - 1].addAll(firstpos_n);
                }
                break;
             case "+":
                Object lastpos_n2[] = node.getLastPos().toArray();
                Set<Integer> firstpos_n2 = node.getFirstPos();
                for (int i = 0; i < lastpos_n2.length; i++) {
                    siguientePos[(Integer) lastpos_n2[i] - 1].addAll(firstpos_n2);
                }
                break;
            case "?":
                
                break;
        }
        generarSiguientePos(node.getLeft());
        generarSiguientePos(node.getRight());

    }
   

    public Set<Integer>[] getSiguientePos() {
        return siguientePos;
    }

    public Nodo getroot() {
        return this.raiz;
    }
    public int getnumOfLeafs(){
       return this.numOfLeafs;
    }
}
