package gui.graphics;

import gui.graphics.coordinatesystem.CartesianScreenPlane;
import gui.graphics.coordinatesystem.Converter;
import math.polynoms.Newton;
import math.polynoms.Polynom;

import java.awt.*;

public class PolynomPainter extends Painter{
    private final CartesianScreenPlane plane;
    private Newton p = null;
    public PolynomPainter(CartesianScreenPlane plane){
        this.plane = plane;
    }
    public void setPolynom(Newton p){
        this.p = p;

    }

    @Override
    public void paint(Graphics g) {
        if(g!=null){//мб еще p!=0
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            if(p.getKeys().length!=1) {
                for (var i = 0; i < p.getKeys().length; i++) {
                    for (var k = 0; k < 100; k++) {//было до 99
                        if (i != p.getKeys().length - 1) {
                            g.drawLine(Converter.xCrt2Scr(p.getKeys()[i] + k * (p.getKeys()[i + 1] - p.getKeys()[i]) / 100, plane), Converter.yCrt2Scr(p.invoke(p.getKeys()[i] + k * (p.getKeys()[i + 1] - p.getKeys()[i]) / 100), plane), Converter.xCrt2Scr(p.getKeys()[i] + (k + 1) * (p.getKeys()[i + 1] - p.getKeys()[i]) / 100, plane), Converter.yCrt2Scr(p.invoke(p.getKeys()[i] + (k + 1) * (p.getKeys()[i + 1] - p.getKeys()[i]) / 100), plane));
                        }
                    }
                }
            }else{
                g.drawLine(Converter.xCrt2Scr(plane.getXMin(), plane), Converter.yCrt2Scr(p.invoke(p.getKeys()[0]), plane),
                        Converter.xCrt2Scr(plane.getXMax(), plane), Converter.yCrt2Scr(p.invoke(p.getKeys()[0]), plane));
            }
        }
    }
}
