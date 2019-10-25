/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex_afd;

import java.util.*;

/**
 *
 * @author oskha
 */
class ArbolB {
       

    private int leafNodeID = 0;
    
    private Stack<Nodo> stackNode = new Stack<>();
    private Stack<Character> operator = new Stack<Character>();

    private Set<Character> entrada = new HashSet<Character>();
    private ArrayList<Character> op = new ArrayList<>();

    public Nodo generateTree(String regular) {

        Character[] ops = {'*', '|', '.', '+', '?'};
        op.addAll(Arrays.asList(ops));

        
        Character ch[] = new Character[26 + 26+1];
        for (int i = 65; i <= 90; i++) {
            ch[i - 65] = (char) i;
            ch[i - 65 + 26] = (char) (i + 32);
        }
        ch[52]='&';
        Character integer[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Character others[] = {'#','\\', '=', '_', '.', '*', '/', '+', '-', ' ', '(', ')', '?'};
        entrada.addAll(Arrays.asList(ch));
        entrada.addAll(Arrays.asList(integer));
        entrada.addAll(Arrays.asList(others));

        regular = AddConcat(regular);
        
        stackNode.clear();
        operator.clear();

        boolean isSymbol = false;

        for (int i = 0; i < regular.length(); i++) {

            if (regular.charAt(i) == '\\') {
                isSymbol = true;
                continue;
            }
            if (isSymbol || isEntradaCharacter(regular.charAt(i))) {
                if (isSymbol) {
                    pushStack("\\"+Character.toString(regular.charAt(i)));
                }
                else{
                    pushStack(Character.toString(regular.charAt(i)));
                }
                isSymbol = false;
            } else if (operator.isEmpty()) {
                operator.push(regular.charAt(i));

            } else if (regular.charAt(i) == '(') {
                operator.push(regular.charAt(i));

            } else if (regular.charAt(i) == ')') {
                while (operator.get(operator.size() - 1) != '(') {
                    doOperation();
                }

                operator.pop();

            } else {
                while (!operator.isEmpty()
                        && Priority(regular.charAt(i), operator.get(operator.size() - 1))) {
                    doOperation();
                }
                operator.push(regular.charAt(i));
            }
        }

        while (!operator.isEmpty()) {
            doOperation();
        }

        Nodo completeTree = stackNode.pop();
        return completeTree;
    }

    private boolean Priority(char first, Character second) {
        if (first == second) {
            return true;
        }
        if (first == '+') {
            return false;
        }
        if (second == '+') {
            return true;
        }
        if (first == '*') {
            return false;
        }
        if (second == '*') {
            return true;
        }
        if (first == '?') {
            return false;
        }
        if (second == '?') {
            return true;
        }
        if (first == '.') {
            return false;
        }
        if (second == '.') {
            return true;
        }        
        if (first == '|') {
            return false;
        }
        return true;
    }

    private void doOperation() {
        if (this.operator.size() > 0) {
            char charAt = operator.pop();

            switch (charAt) {
                case ('|'):
                    union();
                    break;
                case ('.'):
                    concatenar();
                    break;
                case ('*'):
                    asterisco(0);
                    break;
                case ('+'):
                    mas();
                    break;
                case ('?'):
                    asterisco(1);
                    break;    
                default:                    
                    break;
            }
        }
    }

    private void asterisco(int opc) {
        Nodo node = stackNode.pop();
        Nodo raiz;
        if(opc==0){
            raiz = new Nodo("*");
        }else{
            raiz = new Nodo("?");
        }        
        raiz.setLeft(node);
        raiz.setRight(null);
        node.setParent(raiz);

        stackNode.push(raiz);
    }
    
    
    
    
    private void mas() {
        
        Nodo node = stackNode.pop();

        Nodo raiz = new Nodo("+");
        raiz.setLeft(node);
        raiz.setRight(null);
        node.setParent(raiz);

        
        stackNode.push(raiz);
    }

    private void concatenar() {
        Nodo node2 = stackNode.pop();
        Nodo node1 = stackNode.pop();

        Nodo raiz = new Nodo(".");
        raiz.setLeft(node1);
        raiz.setRight(node2);
        node1.setParent(raiz);
        node2.setParent(raiz);

        stackNode.push(raiz);
    }

    private void union() {
        Nodo node2 = stackNode.pop();
        Nodo node1 = stackNode.pop();

        Nodo raiz = new Nodo("|");
        raiz.setLeft(node1);
        raiz.setRight(node2);
        node1.setParent(raiz);
        node2.setParent(raiz);

        stackNode.push(raiz);
    }

    private void pushStack(String symbol) {
        Nodo node = new NodoHoja(symbol);
        if(!node.getSymbol().equals("&")){
            leafNodeID++;     
            node.setPosition(leafNodeID);        }
          
        node.setLeft(null);
        node.setRight(null);

        stackNode.push(node);
    }

    
    private String AddConcat(String regular) {
        String newRegular = new String("");

        for (int i = 0; i < regular.length() - 1; i++) {
            
            if (regular.charAt(i) == '\\' && isEntradaCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i);
            } else if (regular.charAt(i) == '\\' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i);
            } else if ((isEntradaCharacter(regular.charAt(i)) || (regular.charAt(i) == '(' && i > 0 && regular.charAt(i - 1) == '\\')) && isEntradaCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i) + ".";

            } else if ((isEntradaCharacter(regular.charAt(i)) || (regular.charAt(i) == '(' && i > 0 && regular.charAt(i - 1) == '\\')) && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i) + ".";

            } else if (regular.charAt(i) == ')' && isEntradaCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i) + ".";

            } else if (regular.charAt(i) == '*' && isEntradaCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i) + ".";

            } else if (regular.charAt(i) == '*' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i) + ".";
            } else if (regular.charAt(i) == '+' && isEntradaCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i) + ".";

            } else if (regular.charAt(i) == '+' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i) + ".";
            } else if (regular.charAt(i) == '?' && isEntradaCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i)+".";
            } else if (regular.charAt(i) == '?' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i)+".";
            } else if (regular.charAt(i) == ')' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i) + ".";
            } else {
                newRegular += regular.charAt(i);
            }

        }
        newRegular += regular.charAt(regular.length() - 1);
        return newRegular;
    }

    private boolean isEntradaCharacter(char charAt) {

        if (op.contains(charAt)) {
            return false;
        }
        for (Character c : entrada) {
            if ((char) c == charAt && charAt != '(' && charAt != ')') {
                return true;
            }
        }
        return false;
    }
    
    
    public int getNumberOfLeafs(){
        return leafNodeID;
    }

}
