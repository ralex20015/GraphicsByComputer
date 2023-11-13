package animation;

import Curve.Curve;
import Fill.Flood;
import Fill.ScanLine;
import Forms.Polygon;
import Forms.Shape;
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
import java.util.ArrayList;
import java.util.Arrays;

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
    private MyPoint[] cosaQueLanzaKirby;

    private Polygon landScape;
    private final Polygon emptyPlace;
    private MyPoint[] platform1, platform2, platform3;
    private MyPoint[]roof;
    private final Color landScapeColor = new Color(116, 186, 176);
    private final Color grassColor = new Color(0, 255, 0, 100);
    private Flower[] flowers;
    private int updaterWhenKirbyJumpsOnPlatforms = 0;
    private MyPoint[] pointsOfEnemy;
    private MyPoint[]centers = {new MyPoint(1025,400), new MyPoint(1075,400)};

    public Animation(int width, int height) {
        bufferedImage = new BufferedImage(5000, 1200, BufferedImage.TYPE_INT_ARGB);
        anim = new Thread(this);
        graphics = bufferedImage.createGraphics();

        Curve curve = new Curve(bufferedImage);
        pointsOfEnemy = curve.getPointsOfInfinite(50,new MyPoint(1050,400));

        kirby = new Kirby(200, 150, BufferedImage.TYPE_INT_ARGB);
        kirby.setGraphics(graphics);
        kirby.setKirbyPosition(Kirby.KirbyPosition.RIGHT);
        sun = new Sun(100, 100, BufferedImage.TYPE_INT_ARGB);
        canvasOfAnimation = new Polygon(bufferedImage);
        canvasOfAnimation.addVert(new MyPoint(0, 0));
        canvasOfAnimation.addVert(new MyPoint(width, 0));
        canvasOfAnimation.addVert(new MyPoint(width, height));
        canvasOfAnimation.addVert(new MyPoint(0, height));

        //Blue
        landScape = new Polygon(bufferedImage);
        landScape.addVert(new MyPoint(0, 0));
        landScape.addVert(new MyPoint(width, 0));
        landScape.addVert(new MyPoint(width, 400));
        landScape.addVert(new MyPoint(0, 400));

        addFlowers();

        emptyPlace = new Polygon(bufferedImage);
        emptyPlace.changeColor(landScapeColor);
        emptyPlace.addVert(new MyPoint(1200, 300));
        emptyPlace.addVert(new MyPoint(1400, 300));
        emptyPlace.addVert(new MyPoint(1400, 600));
        emptyPlace.addVert(new MyPoint(1200, 600));
        platform1 = new MyPoint[]{new MyPoint(2100, 250), new MyPoint(2300, 250),
                new MyPoint(2300, 300), new MyPoint(2100, 300)};
        platform2 = new MyPoint[]{new MyPoint(1800, 150), new MyPoint(1950, 150),
                new MyPoint(1950, 200), new MyPoint(1800, 200)};
        platform3 = new MyPoint[]{new MyPoint(2100, 25), new MyPoint(2300, 25),
                new MyPoint(2300, 75), new MyPoint(2100, 75)};

        roof = new MyPoint[] {new MyPoint(1400, 0), new MyPoint(1800, 0),
                new MyPoint(1800, 200), new MyPoint(1400, 200)};


        cosaQueLanzaKirby= new MyPoint[]{new MyPoint(800,250),new MyPoint(750,200),
                new MyPoint(800,150),new MyPoint(850,200)};
        startAudio();
    }

    private void addFlowers() {
        flowers = new Flower[10];
        flowers[0] = new Flower(100, 100, BufferedImage.TYPE_INT_ARGB, 100, 300);
        flowers[0].setGraphicsWhile(graphics, currentIncrement, 270);
        flowers[1] = new Flower(100, 100, BufferedImage.TYPE_INT_ARGB, 300, 300);
        flowers[1].setGraphicsWhile(graphics, currentIncrement, 440);
        flowers[2] = new Flower(100, 100, BufferedImage.TYPE_INT_ARGB, 500, 300);
        flowers[2].setGraphicsWhile(graphics, currentIncrement, 800);
        flowers[3] = new Flower(100, 100, BufferedImage.TYPE_INT_ARGB, 800, 300);
        flowers[3].setGraphicsWhile(graphics, currentIncrement, 2200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAll();
        resetBuffer(g);
    }

    private void drawAll() {
        if (currentIncrement < 1800){
            Color colorOfMontania = new Color(244,203,114);
            Curve curve = new Curve(bufferedImage);
            curve.setColor(Color.BLACK);
            MyPoint[] montania = curve.getPointsOfCurve(100,new MyPoint(150,400),50,300);
            Polygon polygonOfMontania = new  Polygon(bufferedImage,montania);
            polygonOfMontania.changeColor(colorOfMontania);
            Flood.apply(polygonOfMontania.drawPolygon());

            MyPoint midPointOfMontania = polygonOfMontania.getMidPoint(montania);
            MyPoint[] cresta = curve.getPointsOfCurve(50, new MyPoint(
                    midPointOfMontania.getX()-45, midPointOfMontania.getY()), 29,110);
            Polygon crestaOfMontania = new Polygon(bufferedImage,cresta);
            crestaOfMontania.changeColor(Color.WHITE);
            Flood.apply(crestaOfMontania.drawPolygon(), new int[]{0, colorOfMontania.getRGB()});
            drawFillTriangle(new MyPoint[]{
                    new MyPoint(midPointOfMontania.getX()-23, midPointOfMontania.getY()+20),
                    new MyPoint(midPointOfMontania.getX()-45,midPointOfMontania.getY()),
                    new MyPoint(midPointOfMontania.getX(),midPointOfMontania.getY())},Color.WHITE, colorOfMontania);
            drawFillTriangle(new MyPoint[]{
                    new MyPoint(midPointOfMontania.getX()+23, midPointOfMontania.getY()+20),
                    new MyPoint(midPointOfMontania.getX(),midPointOfMontania.getY()),
                    new MyPoint(midPointOfMontania.getX()+45,midPointOfMontania.getY())},Color.WHITE, colorOfMontania);

            Polygon floor = new Polygon(bufferedImage);
            floor.changeColor(new Color(0, 255, 0, 100));
            MyPoint[] floorPoints = {new MyPoint(0, 400),
                    new MyPoint(1200, 400),
                    new MyPoint(1200, 600),
                    new MyPoint(0, 600)};
            floor.setVerts(floorPoints);
            ScanLine.apply(floor.getVerts(), floor.drawPolygon());

            curve.setColor(Color.BLACK);
            curve.drawParticleMovement(new MyPoint(200,50),3,200);
            curve.drawParticleMovement(new MyPoint(400,75),3,200);
            curve.drawParticleMovement(new MyPoint(400,150),3,200);

//            curve.drawSmoke(new MyPoint(980,400),new Dimension(250,150),Color.BLACK);
            if (currentIncrement >= 480 && currentIncrement <= 680){
                Polygon cosaQueLanzaKirby = new Polygon(bufferedImage,this.cosaQueLanzaKirby);
                cosaQueLanzaKirby.changeColor(Color.YELLOW);
                Flood.apply(cosaQueLanzaKirby.drawPolygon(),new int[]{landScapeColor.getRGB(), 0});

                Polygon enemy = new Polygon(bufferedImage,pointsOfEnemy);
                enemy.changeColor(Color.BLACK);
                enemy.drawPolygon();
                enemy.setVerts(pointsOfEnemy);

                Flood.apply(new Shape(new ArrayList<>(),centers[0].getX(),centers[0].getY(),
                        bufferedImage,graphics,Color.RED,
                        Shape.Figure.RECTANGLE),new int[]{0,grassColor.getRGB()});
                Flood.apply(new Shape(new ArrayList<>(),centers[1].getX(),centers[1].getY(),
                        bufferedImage,graphics,Color.RED,
                        Shape.Figure.RECTANGLE),new int[]{0,grassColor.getRGB()});
            }
        }

        if (currentIncrement < 2000) {
            Polygon plateau = new Polygon(bufferedImage);
            plateau.changeColor(new Color(0, 255, 0, 100));
            plateau.setVerts(new MyPoint[]{new MyPoint(610, 300),
                    new MyPoint(750, 300), new MyPoint(750, 400), new MyPoint(610, 400)});
            Flood.apply(plateau.drawPolygon());
        }

        MyPoint[] pointsOfLandScape = landScape.getVerts();
        drawFillRect(pointsOfLandScape, landScapeColor, null);

        showFlowers();
        if (currentIncrement > 700 && currentIncrement < 1800) {

            MyPoint[] pointsOfEmptySpace = emptyPlace.getVerts();
            drawFillRect(pointsOfEmptySpace, landScapeColor, null);
            new Curve(bufferedImage).drawSmoke(new MyPoint(1300, 450), new Dimension(100,100),Color.BLACK);
        }

        if (currentIncrement > 1000 && currentIncrement < 3000) {

            MyPoint[] grassPoints = {new MyPoint(1400, 400), new MyPoint(2250, 400),
                    new MyPoint(2250, 600), new MyPoint(1400, 600)};
            if (currentIncrement < 1300){
                MyPoint[] roof = {new MyPoint(1400, 0), new MyPoint(1800, 0),
                        new MyPoint(1800, 200), new MyPoint(1400, 200)};
                drawFillRect(roof, Color.gray, landScapeColor);
            }
            if (currentIncrement >= 2280) {
                grassPoints = Transform.translate(new Polygon(bufferedImage, grassPoints), 0,
                        updaterWhenKirbyJumpsOnPlatforms);
            }
            drawFillRect(grassPoints, grassColor, null);
        }
        //part when kirby jumps to one side to another
        if (currentIncrement > 1300  && currentIncrement < 3400) {
            drawFillRect(platform1, Color.gray, landScapeColor);
            drawFillRect(platform2, Color.gray, landScapeColor);
            drawFillRect(platform3, Color.gray, landScapeColor);
            drawFillRect(roof, Color.gray, landScapeColor);
        }

        if (currentIncrement > 2800){
            Polygon polygon = new Polygon(bufferedImage);
            polygon.addVert(new MyPoint(2300, 1000));
            polygon.addVert(new MyPoint(2300,705));
            polygon.addVert(new MyPoint(2750,1000));
            drawFillTriangle(polygon.getVerts(),grassColor,landScapeColor);
        }
        sun.setGraphics(graphics);
        kirby.setGraphics(graphics);
        kirby.drawKirby();

    }

    private void showFlowers() {
        for (int i = 0; i < 4; i++) {
            flowers[i].setGraphicsWhile(graphics, currentIncrement, flowers[i].getLimit());
        }
    }

    private void drawFillRect(MyPoint[] points, Color color, Color colorToCompare) {
        int[] colors = new int[2];
        if (colorToCompare != null) {
            colors[1] = colorToCompare.getRGB();
        }
        Polygon rectangle = new Polygon(bufferedImage);
        rectangle.changeColor(color);
        rectangle.addVert(new MyPoint(points[0].getX(), points[0].getY()));
        rectangle.addVert(new MyPoint(points[1].getX(), points[1].getY()));
        rectangle.addVert(new MyPoint(points[2].getX(), points[2].getY()));
        rectangle.addVert(new MyPoint(points[3].getX(), points[3].getY()));
        Flood.apply(rectangle.drawPolygon(), colors);
    }

    private void drawFillTriangle(MyPoint[] points, Color color, Color colorToCompare){
        int[] colors = new int[3];
        if (colorToCompare != null) {
            colors[1] = colorToCompare.getRGB();
            colors[2] = Color.gray.getRGB();
        }
        Polygon triangle = new Polygon(bufferedImage);
        triangle.changeColor(color);
        triangle.addVert(new MyPoint(points[0].getX(), points[0].getY()));
        triangle.addVert(new MyPoint(points[1].getX(), points[1].getY()));
        triangle.addVert(new MyPoint(points[2].getX(), points[2].getY()));
        Flood.apply(triangle.drawPolygon(), colors);
    }

    private void resetBuffer(Graphics g) {
        MyPoint[] canvas = canvasOfAnimation.getVerts();
        g.drawImage(bufferedImage, canvas[0].getX(), canvas[0].getY(), null);
        bufferedImage = new BufferedImage(5000, 1200, BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();
    }

    @Override
    public void run() {
        while (seconds < 91) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentIncrement += increment;

            kirby.moveRight(currentIncrement, 0, 270);
            sun.moveRight(currentIncrement, 0, 435);
            kirby.jumpRight(currentIncrement, 270, 405, kirby.getPolygon());
            kirby.moveRight(currentIncrement, 440, 480);
            updateCanvasRight(currentIncrement, 0, 440);//Until the jump

            if (currentIncrement >= 480 && currentIncrement < 680){
                Polygon polygon = new Polygon(bufferedImage,cosaQueLanzaKirby);
                Polygon polygon2 = new Polygon(bufferedImage,pointsOfEnemy);
               cosaQueLanzaKirby = Transform.rotate(polygon, 30);
               cosaQueLanzaKirby = Transform.translate(polygon,10,5);
               cosaQueLanzaKirby = Transform.scaleWithMatrix(polygon,1.01,1.01);
//               if (currentIncrement >= 530){
//                   pointsOfEnemy = Transform.translate(polygon2,0,10);
//                   centers = Transform.translate(polygon2,0,10);
//               }
            }

            updateCanvasRight(currentIncrement, 550, 650);//after the jump
            kirby.moveRight(currentIncrement, 550, 670);
            sun.moveRight(currentIncrement, 545, 645);
            kirby.fall(currentIncrement, 650, 720, kirby.getPolygon());

            updateCanvasRight(currentIncrement, 720, 1100);
            sun.moveRight(currentIncrement, 715, 1095);
            kirby.moveRight(currentIncrement, 720, 1100);
            //870 start Point make the simulation of eating a enemy

            if (currentIncrement >= 405 && currentIncrement < 440) {
                //la caida de cuando brinca
                Transform.translate(kirby.getPolygon(), 7, +5);
            }

            updateCanvasRight(currentIncrement, 1100, 1230);
            sun.moveRight(currentIncrement, 1100, 1225);
            kirby.jumpRight(currentIncrement, 1095, 1230, kirby.getPolygon());
            if (currentIncrement >= 1225 && currentIncrement < 1360) {
                Transform.translate(kirby.getPolygon(), 5, +5);
            }

            updateCanvasRight(currentIncrement, 1360, 1950);
            kirby.moveRight(currentIncrement, 1360, 1820);
            kirby.jumpRight(currentIncrement, 1950, 2150, kirby.getPolygon());
            if (currentIncrement >= 2150 && currentIncrement < 2210) {
                Transform.translate(kirby.getPolygon(), 5, +5);
            }

            if (currentIncrement == 2215) {
                kirby = new Kirby(200, 150, BufferedImage.TYPE_INT_ARGB);
                kirby.setPosition(new MyPoint(2079, 138));
                kirby.setGraphics(graphics);
                kirby.setKirbyPosition(Kirby.KirbyPosition.LEFT);
            }

            kirby.moveLeft(currentIncrement, 2210, 2280);
            updateCanvasTop(currentIncrement, 2280, 2450);
            kirby.moveLeft(currentIncrement, 2280, 2400);

            if (currentIncrement >= 2350 && currentIncrement < 2465) {
                Transform.translate(kirby.getPolygon(), -5, +10);//fall of kirby when jumps
            }
            kirby.moveLeft(currentIncrement,2470,2500);
            //Change the drawOfKirby to Right
            if (currentIncrement == 2505){
                kirby = new Kirby(200,150,BufferedImage.TYPE_INT_ARGB);
                kirby.setPosition(new MyPoint(1749,368));
                kirby.setGraphics(graphics);
                kirby.setKirbyPosition(Kirby.KirbyPosition.RIGHT);
            }
            kirby.moveRight(currentIncrement, 2505, 2565);
            updateCanvasTop(currentIncrement,2565, 2735);
            kirby.moveRight(currentIncrement, 2565, 2645);
//
            if (currentIncrement >= 2635 && currentIncrement < 2745) {
                Transform.translate(kirby.getPolygon(), 5, +10);//fall of kirby when jumps
            }
            updateCanvasRight(currentIncrement,2745,3250);
            kirby.moveRight(currentIncrement,3000,3250);

            secondsMillis += 100;
            if (secondsMillis % 1000 == 0) {
                seconds++;
            }
            repaint();
        }
    }

    private void startAudio() {
        Thread audioThread = new Thread(() -> {
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

    public void startAnimation() {
        anim.start();
    }

    private void updateCanvasRight(int current, int inferiorLimit, int superiorLimit) {
        if (current >= inferiorLimit & current < superiorLimit) {
            Transform.translate(canvasOfAnimation, -5, 0);
            Transform.translate(landScape, 5, 0);
        }
    }

    public void updateCanvasTop(int current, int inferiorLimit, int superiorLimit) {
        if (current >= inferiorLimit & current < superiorLimit) {
            Transform.translate(canvasOfAnimation, 0, -5);
            updaterWhenKirbyJumpsOnPlatforms += 10;
            Transform.scaleWithMatrix(landScape, 1, 1.025);
            if (currentIncrement < 2850 ){
                platform1 = Transform.translate(new Polygon(bufferedImage, platform1),
                        0, 10);
                platform2 = Transform.translate(new Polygon(bufferedImage, platform2),
                        0, 10);
                platform3 = Transform.translate(new Polygon(bufferedImage, platform3),
                        0, 10);
                roof = new MyPoint[]{new MyPoint(1400, 0), new MyPoint(1800, 0),
                        new MyPoint(1800, 200 + updaterWhenKirbyJumpsOnPlatforms), new MyPoint(1400, 200 + updaterWhenKirbyJumpsOnPlatforms)};
            }
        }
    }
}
