package gui;

import gui.graphics.CartesianPainter;
import gui.graphics.PointPainter;
import gui.graphics.PolynomPainter;
import gui.graphics.components.ControlPanel;
import gui.graphics.components.GraphicsPanel;
import gui.graphics.coordinatesystem.CartesianScreenPlane;
import gui.graphics.coordinatesystem.Converter;
import gui.graphics.events.CPEvent;
import math.polynoms.Newton;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    GraphicsPanel mainPanel;
    ControlPanel controlPanel;
    CartesianPainter painter;
    PointPainter ppp;
    PolynomPainter np;
    Newton newt;
    static final Dimension MIN_SIZE = new Dimension(450, 350);
    static final Dimension MIN_FRAME_SIZE = new Dimension(600, 500);

    public MainWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(MIN_FRAME_SIZE);
        setTitle("Полиномы");

        mainPanel = new GraphicsPanel();
        controlPanel = new ControlPanel();

        mainPanel.setBackground(Color.WHITE);
        //mainPanel.addPainter(new CartesianPainter());
        controlPanel.setBorder(new EtchedBorder());

        GroupLayout gl = new GroupLayout(getContentPane());
        setLayout(gl);
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, (int)(MIN_SIZE.height*0.8), MIN_SIZE.height, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(4)
        );
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(4)
                .addGroup(
                        gl.createParallelGroup()
                                .addComponent(mainPanel, MIN_SIZE.width, MIN_SIZE.width, GroupLayout.DEFAULT_SIZE)
                                .addComponent(controlPanel, MIN_SIZE.width, MIN_SIZE.width, GroupLayout.DEFAULT_SIZE)
                )
                .addGap(4)
        );
        pack();
        painter = new CartesianPainter(new CartesianScreenPlane(
                mainPanel.getWidth(), mainPanel.getHeight(), controlPanel.getXMin(), controlPanel.getXMax(),
                controlPanel.getYMin(), controlPanel.getYMax()
        ));
        //painter.paint(mainPanel.getGraphics());
        controlPanel.addCPEListener(new CPEvent() {
            @Override
            public void dataChanged() {
                painter.getCSP().set_xMin(controlPanel.getXMin());
                painter.getCSP().set_xMax(controlPanel.getXMax());
                painter.getCSP().set_yMin(controlPanel.getYMin());
                painter.getCSP().set_yMax(controlPanel.getYMax());
                mainPanel.repaint();
            }
        });
        mainPanel.addPainter(painter);
        ppp = new PointPainter(painter.getCSP());
        np = new PolynomPainter(painter.getCSP());
        mainPanel.addPainter(ppp);
        newt = new Newton();
        np.setPolynom(newt);
        mainPanel.addPainter(np);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                painter.getCSP().set_realWidth(mainPanel.getWidth());
                painter.getCSP().set_realHeight(mainPanel.getHeight());
                mainPanel.repaint();
            }
        });
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1&&!ppp.pointExists(e.getX())){
                    ppp.addPoint(e.getX(),e.getY());
                    var cx = Converter.xScr2Crt(e.getX(),painter.getCSP());
                    var cy = Converter.yScr2Crt(e.getY(),painter.getCSP());
                    newt.addPoint(cx,cy);
                    mainPanel.repaint();
                }
                if(e.getButton()==MouseEvent.BUTTON3&&ppp.pointExists(e.getX())){
                    ppp.RemoveDot(e.getX());
                    newt = new Newton(ppp.getDots());
                    np.setPolynom(newt);
                    mainPanel.repaint();
                }
            }
        });




    }
}