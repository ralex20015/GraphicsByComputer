package transforms;

import Fill.Flood;
import Forms.Polygon;
import utilities.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Translation extends JPanel implements Runnable{
    private BufferedImage bufferedImage;
    private final int incrementX = 4;
    private final int incrementY = 4;
    private int dx = incrementX;
    private int dy = incrementY;
    private final Thread drawer;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        Translation example = new Translation();
        frame.add(example);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        example.startDrawer();
        frame.setVisible(true);
    }
    public Translation(){
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
        drawer = new Thread(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon polygon = new Polygon(bufferedImage);
        int y0 = 0;
        int x0 = 0;
        polygon.addVert(new MyPoint(x0, y0));
        int rectWidth = 100;
        polygon.addVert(new MyPoint(x0 + rectWidth, y0));
        int rectHeight = 50;
        polygon.addVert(new MyPoint(x0 + rectWidth, y0 + rectHeight));
        polygon.addVert(new MyPoint(x0, y0 + rectHeight));
        Transform.translate(polygon, dx, dy);
        Flood.apply(polygon.drawPolygon());
        reset(g);
    }

    @Override
    public void run() {

        while (dx < 250 && dy < 250){
            dx += incrementX;
            dy += incrementY;
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("Done");
    }
    public void startDrawer(){
        this.drawer.start();
    }

    private void reset(Graphics  g){
        g.drawImage(bufferedImage,0,0,null);
        bufferedImage = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
    }
}
