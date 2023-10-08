package Figuras_Terminado;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MyWindow myWindow = new MyWindow();
        myWindow.setVisible(true);
    }

    static class MyWindow extends JFrame {
        private final CustomGraphics customGraphics;
        private boolean isPainted;

        public MyWindow(){
            setTitle("Ventana");
            setSize(600,600);
            setLayout(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            customGraphics = new CustomGraphics(this);
        }

        @Override
        public void paint(Graphics g) {
            if (!isPainted){
                super.paint(g);
                isPainted = true;
                customGraphics.drawLineWithBresenham(20,120,120,220,Color.RED);
                customGraphics.drawLineWithBresenham(150,120,250,120,Color.RED);
                customGraphics.drawLineWithBresenham(350,120,250,220,Color.RED);
                customGraphics.drawLineWithBresenham(480,120,380,120,Color.RED);

                customGraphics.drawMidPointCircle(120,350,100,Color.BLACK);
                customGraphics.drawMidPointCircle(120,350,80,Color.BLACK);
                customGraphics.drawMidPointCircle(120,350,50,Color.BLACK);
                customGraphics.drawMidPointCircle(120,350,10,Color.BLACK);

                customGraphics.drawRectangle(250,250,200,100,Color.BLUE);
                customGraphics.drawRectangle(300,275, 100,50,Color.BLUE);

                customGraphics.drawEllipse(400,450,100,50);
                customGraphics.drawEllipse(400,450,80,40);
                customGraphics.drawEllipse(400,450,60,20);
                customGraphics.drawEllipse(400,450,30,10);
            }
        }
    }
}
