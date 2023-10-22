package animation;

import Fill.Flood;
import Fill.ScanLine;
import Forms.Polygon;
import animation.elements.Flower;
import animation.elements.Kirby;
import animation.elements.Sun;
import transforms.Transform;
import utilities.MyPoint;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Animation extends JPanel implements Runnable {
    private BufferedImage bufferedImage;
    private int increment = 5;
    private int currentIncrement = 0;
    private Kirby kirby;
    private final Thread anim;
    private Graphics graphics;
    private final Sun sun;
    private int seconds = 0;
    private int secondsMillis = 0;
    private Polygon canvasOfAnimation;
    private Polygon landScape;
    private final Polygon emptyPlace;
    private final Color landScapeColor = new Color(116, 186, 176);
    private final Color grassColor = new Color(0,255,0,100);
    private Flower[] flowers;
    public Animation(int width, int height){
        bufferedImage = new BufferedImage(5000, 1200, BufferedImage.TYPE_INT_ARGB);
        anim = new Thread(this);
        graphics = bufferedImage.createGraphics();

        kirby = new Kirby(200,150,BufferedImage.TYPE_INT_ARGB);
        kirby.setGraphics(graphics);
        kirby.setKirbyPosition(Kirby.KirbyPosition.RIGHT);
        sun = new Sun(100,100,BufferedImage.TYPE_INT_ARGB);
        canvasOfAnimation = new Polygon(bufferedImage);
        canvasOfAnimation.addVert(new MyPoint(0,0));
        canvasOfAnimation.addVert(new MyPoint(width,0));
        canvasOfAnimation.addVert(new MyPoint(width,height));
        canvasOfAnimation.addVert(new MyPoint(0,height));

        //Blue
        landScape = new Polygon(bufferedImage);
        landScape.addVert(new MyPoint(0,0));
        landScape.addVert(new MyPoint(width,0));
        landScape.addVert(new MyPoint(width,398));
        landScape.addVert(new MyPoint(0,398));

        addFlowers();

        emptyPlace = new Polygon(bufferedImage);
        emptyPlace.changeColor(landScapeColor);
        emptyPlace.addVert(new MyPoint(1200,300));
        emptyPlace.addVert(new MyPoint(1400,300));
        emptyPlace.addVert(new MyPoint(1400,600));
        emptyPlace.addVert(new MyPoint(1200,600));
//        startAudio();
    }

    private void addFlowers() {
        flowers = new Flower[10];
        flowers[0] = new Flower(100,100,BufferedImage.TYPE_INT_ARGB,100,300);
        flowers[0].setGraphicsWhile(graphics,currentIncrement,270);
        flowers[1] = new Flower(100,100,BufferedImage.TYPE_INT_ARGB,300,300);
        flowers[1].setGraphicsWhile(graphics,currentIncrement,440);
        flowers[2] = new Flower(100,100,BufferedImage.TYPE_INT_ARGB,500,300);
        flowers[2].setGraphicsWhile(graphics,currentIncrement,800);
        flowers[3] = new Flower(100,100,BufferedImage.TYPE_INT_ARGB,800,300);
        flowers[3].setGraphicsWhile(graphics,currentIncrement,2200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        landScape();
        MyPoint[]points = landScape.getVerts();
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.valueOf(seconds),points[0].getX()+300,points[0].getY()+50);
        resetBuffer(g);
    }
    private void landScape(){
        Polygon floor = new Polygon(bufferedImage);
        floor.changeColor(new Color(0,255,0,100));
        MyPoint[]floorPoints = {new MyPoint(0,400),
                new MyPoint(1200,400),
                new MyPoint(1200,600),
                new MyPoint(0,600)};
        floor.setVerts(floorPoints);
        ScanLine.apply(floor.getVerts(),floor.drawPolygon());

        if (currentIncrement < 2000){
            Polygon plateau = new Polygon(bufferedImage);
            plateau.changeColor(new Color(0,255,0,100));
            plateau.setVerts(new MyPoint[]{new MyPoint(610,300),
                    new MyPoint(750,300), new MyPoint(750,400), new MyPoint(610,400)});
            Flood.apply(plateau.drawPolygon());
        }

        MyPoint[] pointsOfLandScape = landScape.getVerts();
        drawFillRect(pointsOfLandScape, landScapeColor, null);

        showFlowers();
        if (currentIncrement > 700 && currentIncrement < 1800){
            MyPoint[] pointsOfEmptySpace = emptyPlace.getVerts();
            drawFillRect(pointsOfEmptySpace, landScapeColor, null);
        }
        if (currentIncrement > 1000 && currentIncrement < 3000){
            MyPoint[] grassPoints = {new MyPoint(1400,400), new MyPoint(2250, 400),
                new MyPoint(2250,600), new MyPoint(1400,600)};
            drawFillRect(grassPoints, grassColor,null);
            MyPoint[] roof = {new MyPoint(1400,0), new MyPoint(1800, 0),
                    new MyPoint(1800,200), new MyPoint(1400,200)};
            drawFillRect(roof, Color.gray, landScapeColor);
        }
        //part when kirby jumps to one side to another
        if (currentIncrement > 1300){
            MyPoint[] firstPlatform = {new MyPoint(2100,250), new MyPoint(2300, 250),
                    new MyPoint(2300,300), new MyPoint(2100,300)};
            drawFillRect(firstPlatform, Color.gray, landScapeColor);
        }
        sun.setGraphics(graphics);
        kirby.setGraphics(graphics);
        kirby.drawKirby();

    }

    private void showFlowers(){
        for (int i = 0; i < 4; i++) {
            flowers[i].setGraphicsWhile(graphics,currentIncrement,flowers[i].getLimit());
        }
    }

    private void drawFillRect(MyPoint[]points, Color color, Color colorToCompare){
        int[] colors =  new int[2];
        if (colorToCompare != null){
            colors[1] = colorToCompare.getRGB();
        }
        Polygon rectangle = new Polygon(bufferedImage);
        rectangle.changeColor(color);
        rectangle.addVert(new MyPoint(points[0].getX(), points[0].getY()));
        rectangle.addVert(new MyPoint(points[1].getX(), points[1].getY()));
        rectangle.addVert(new MyPoint(points[2].getX(), points[2].getY()));
        rectangle.addVert(new MyPoint(points[3].getX(), points[3].getY()));
        Flood.apply(rectangle.drawPolygon(),colors);
    }

    private void resetBuffer(Graphics g) {
        MyPoint[]canvas = canvasOfAnimation.getVerts();
        g.drawImage(bufferedImage,canvas[0].getX(),canvas[0].getY(),null);
        bufferedImage = new BufferedImage(5000,1200,BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();
    }

    @Override
    public void run() {
        while(seconds < 91){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentIncrement += increment;

            kirby.moveRight(currentIncrement,0,270);
            sun.moveRight(currentIncrement,0,435);
            kirby.jumpRight(currentIncrement,270,405, kirby.getPolygon());
            kirby.moveRight(currentIncrement,440,480);
            updateCanvasRight(currentIncrement,0,440);//Until the jump

            updateCanvasRight(currentIncrement,550,650);//after the jump
            kirby.moveRight(currentIncrement,550,670);
            sun.moveRight(currentIncrement,545,645);
            kirby.fall(currentIncrement,650,720,kirby.getPolygon());

            updateCanvasRight(currentIncrement,720, 1100);
            sun.moveRight(currentIncrement,715,1095);
            kirby.moveRight(currentIncrement,720,1100);
            //870 start Point make the simulation of eating a enemy

            if (currentIncrement >= 405 && currentIncrement < 440) {
                //la caida de cuando brinca
                Transform.translate(kirby.getPolygon(),7,+5);
            }//15 seconds i have

            updateCanvasRight(currentIncrement,1100, 1230);
            sun.moveRight(currentIncrement,1100,1225);
            kirby.jumpRight(currentIncrement,1095,1230,kirby.getPolygon());
            if (currentIncrement >= 1225 && currentIncrement < 1360) {
                Transform.translate(kirby.getPolygon(),5,+5);
            }

            updateCanvasRight(currentIncrement,1360, 1950);
            kirby.moveRight(currentIncrement,1360,1820);
            kirby.jumpRight(currentIncrement,1950,2150,kirby.getPolygon());
            if (currentIncrement >= 2150 && currentIncrement < 2210){
                Transform.translate(kirby.getPolygon(),5,+5);
            }

            if (currentIncrement == 2210){
                System.out.println("Si entro");
                kirby = new Kirby(200,150,BufferedImage.TYPE_INT_ARGB);
                kirby.setPosition(new MyPoint(2079,138));
                kirby.setGraphics(graphics);
                kirby.setKirbyPosition(Kirby.KirbyPosition.LEFT);
            }

            if (currentIncrement > 2215){
                kirby.moveLeft(currentIncrement,2210,2280);
            }


            secondsMillis += 100;
            if (secondsMillis % 1000 == 0){
                seconds++;
            }
            repaint();
        }
    }

    private void startAudio() {
        Thread audioThread = new Thread(()->{
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(Animation.class.getResource("/animation/atmosphereMusic/song.wav")));
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        audioThread.start();
    }

//    public void drawCloud(MyPoint p, int scale, Color c) {
//        double t, x, y;
//        double dt = 0.01;
//        double maxT = 50.0;
//
//        IPixel pixel = new IPixel() {
//            @Override
//            public void putPixel(int x, int y, BufferedImage bufferedImage, Color c) {
//                IPixel.super.putPixel(x, y, bufferedImage, c);
//            }
//        };
//        // Calculate and draw curve
//        for (t = 0.0; t <= maxT; t += dt) {
//            x = t - 3.0 * Math.sin(t);
//            y = 4.0 - 3.0 * Math.cos(t);
//            int pixelX = (int) Math.round(x * scale);
//            int pixelY = (int) Math.round(y * scale);
//
//            pixel.putPixel(p.getX() + pixelX, p.getY() + pixelY, bufferedImage,c);
//        }
//    }

    public void startAnimation(){
        anim.start();
    }
    private void updateCanvasRight(int current, int inferiorLimit, int superiorLimit) {
        if (current >= inferiorLimit & current < superiorLimit) {
            Transform.translate(canvasOfAnimation, -5, 0);
            Transform.translate(landScape,5,0);
        }
    }
}
