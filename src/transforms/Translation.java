package transforms;

import Fill.Flood;
import Forms.OutlineShape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Translation extends JPanel implements Runnable{
    private int x0 = 0;
    private int y0 = 0;
    private int rectWidth = 100;
    private int rectHeight = 50;
    private final OutlineShape shape;
    private BufferedImage bufferedImage;
    private final int incrementX = 4;
    private final int incrementY = 4;
    private int dx = incrementX;
    private int dy = incrementY;
    private Thread drawer;

    private int[][] translationMatrix = {
            {1, 0, dx},
            {0, 1, dy},
            {0, 0, 1}
    };

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
        shape = new OutlineShape();
        drawer = new Thread(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[] topLeft = {x0, y0, 1};
        int[] topRight = {x0 + rectWidth, y0, 1};
        int[] bottomLeft = {x0, y0 + rectHeight, 1};
        int[] bottomRight = {x0 + rectWidth, y0 + rectHeight, 1};

        int[] translatedTopLeft = new int[3];
        int[] translatedTopRight = new int[3];
        int[] translatedBottomLeft = new int[3];
        int[] translatedBottomRight = new int[3];

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                translatedTopLeft[j] += translationMatrix[j][k] * topLeft[k];
                translatedTopRight[j] += translationMatrix[j][k] * topRight[k];
                translatedBottomLeft[j] += translationMatrix[j][k] * bottomLeft[k];
                translatedBottomRight[j] += translationMatrix[j][k] * bottomRight[k];
            }
        }


        x0 = translatedTopLeft[0];
        y0 = translatedTopLeft[1];

        Flood.apply(shape.drawRectangle(x0, y0,rectWidth,rectHeight,bufferedImage,g));
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
