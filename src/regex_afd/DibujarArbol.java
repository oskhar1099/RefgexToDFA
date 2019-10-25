package regex_afd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DibujarArbol extends JFrame {

    private JPanel contentPane;
    public Nodo node;
    public DrawTree drawer;

    
    public DibujarArbol(Nodo node, JPanel panel) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(600, 0, 700, 500);
        contentPane = panel;
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        drawer = new DrawTree(node);
        contentPane.add(drawer);
        setContentPane(contentPane);
        this.node = node;
        setVisible(true);
    }

}

class DrawTree extends JPanel{

    public Nodo node;
    public static ArrayList listX = new ArrayList();
    public static ArrayList listY = new ArrayList();


    public DrawTree(Nodo node){
        this.node = node;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        g.setFont(new Font("Tahoma", Font.BOLD, 20));
        DrawTree(g, 0, getWidth()+9*getWidth()/20, 0, 50, node,0);
        listX.clear();
        listY.clear();
    }
    
    public Point DrawTree(Graphics g, int StartWidth, int EndWidth, int StartHeight, int Level, Nodo node, int ajuste)
{
    String data = String.valueOf(node.getSymbol());
    String pPos = String.valueOf(node.getFirstPos());
    String uPos = String.valueOf(node.getLastPos());
    g.setFont(new Font("Tahoma", Font.BOLD, 20));
    FontMetrics fm = g.getFontMetrics();
    int dataWidth = fm.stringWidth(data);

    // Calculate position to draw text string
    Point textPos = new Point((StartWidth + EndWidth) / 2 - dataWidth / 2, StartHeight + Level / 2);
    g.drawString(data, textPos.x-5, textPos.y+5);
    g.setFont(new Font("Tahoma",Font.PLAIN, 10));
    g.drawString("PP: "+pPos, textPos.x-5, textPos.y-15);
    g.drawString("UP: "+uPos, textPos.x-5, textPos.y+25);
    g.setColor(Color.black);
    g.drawOval(textPos.x-10, textPos.y-10, 25, 25);
    if (node.getLeft() != null && node.getRight()==null) {
        Point child1 = DrawTree(g, StartWidth, EndWidth, StartHeight + Level+Level/2, Level, node.getLeft(),StartHeight);
        // Draw line from this node to child node
        drawLine(g, textPos, child1);
    }else{
        if (node.getLeft() != null ) {
        Point child1 = DrawTree(g, StartWidth+100, EndWidth-400+ajuste, StartHeight + Level, Level, node.getLeft(),ajuste+40);
        // Draw line from this node to child node
        drawLine(g, textPos, child1);
        }
    }    
    if (node.getRight() != null) {
        Point child2 = DrawTree(g, StartWidth-100, EndWidth+400-ajuste, StartHeight + Level, Level, node.getRight(),ajuste+40);
        // Draw line from this node to child node
        drawLine(g, textPos, child2);
    }
    // Return position for parent to use
    return textPos;
}

public void drawLine(Graphics g, Point p1, Point p2)
{
   g.drawLine(p1.x, p1.y, p2.x, p2.y);
}
}