package transforms;

import Fill.Flood;
import Forms.Polygon;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Escalation extends JPanel implements Runnable {
    private BufferedImage bufferedImage;
    private final Thread anim;
    private double increment = 0.1;
    private double currentScale = 0.5;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Escalation escalation = new Escalation();
        frame.add(escalation);
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Escalation program");
        escalation.startThread();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Escalation(){
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
        anim = new Thread(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage,0,0,null);

        Polygon polygon = new Polygon(bufferedImage);
        polygon.addVert(new MyPoint(10,10));
        polygon.addVert(new MyPoint(110,10));
        polygon.addVert(new MyPoint(110,60));
        polygon.addVert(new MyPoint(10,60));
        polygon.changeColor(Color.BLUE);

        Transform.scaleWithMatrix(polygon, currentScale, currentScale);
        Flood.apply(polygon.drawPolygon());

        reset(g);
//        ScanLine.setBufferedImage(bufferedImage);
//        ScanLine.apply(polygonScaled.getVerts(),Color.RED,null);
    }

    private void reset(Graphics g) {
        g.drawImage(bufferedImage,0,0,null);
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void run() {
        while(true){
            double minimumScale = 0.5;
            double maximScale = 5.5;
            if (currentScale >= maximScale){
                increment = -0.1;
            } else if (currentScale <= minimumScale) {
                increment = 0.1;
            }
            currentScale += increment;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repaint();
        }
    }

    public void startThread(){
        this.anim.start();
    }
}
