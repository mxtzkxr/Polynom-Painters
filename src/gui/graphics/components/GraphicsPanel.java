package gui.graphics.components;

import javax.swing.*;

import gui.graphics.CartesianPainter;
import gui.graphics.Painter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
    ArrayList<Painter> p = new ArrayList<>();
    public void addPainter(Painter p){
        this.p.add(p);
    }
    public void removePainter(Painter p){
        this.p.remove(p);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
         p.forEach(it->it.paint(g));
    }
}
