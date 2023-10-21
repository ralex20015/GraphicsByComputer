package transforms;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import Fill.Flood;
import Fill.ScanLine;
import Forms.Polygon;
import utilities.MyPoint;

public class Rotation extends JPanel implements Runnable {
    private BufferedImage bufferedImage;
    private int angle = 0;
    private final Thread anim;
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        Rotation rotation = new Rotation();
        frame.add(rotation);
        rotation.startThread();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Rotation program");
    }

    public Rotation(){
        anim = new Thread(this);
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon polygon = new Polygon(bufferedImage);
        polygon.addVert(new MyPoint(100,100));
        polygon.addVert(new MyPoint(200,100));
        polygon.addVert(new MyPoint(200,150));
        polygon.addVert(new MyPoint(100,150));
        Transform.rotateInBaseOfPivot(polygon,angle,new MyPoint(100,100));
        Flood.apply(polygon.drawPolygon());
//        ScanLine.setBufferedImage(bufferedImage);
//        ScanLine.apply(polygon.getVerts(),Color.RED,null);
        reset(g);
    }

    private void reset(Graphics g) {
        g.drawImage(bufferedImage,0,0,null);
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void run() {
        while (angle < 1800){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int incrementAngle = 15;
            angle += incrementAngle;
            System.out.println(angle);
            repaint();
        }
    }

    public void startThread(){
        this.anim.start();
    }

}
