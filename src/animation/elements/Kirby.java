package animation.elements;

import Curve.Curve;
import Fill.Flood;
import Forms.OutlineShape;
import Forms.Polygon;
import animation.interfaces.Movable;
import transforms.Transform;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Stream;

public class Kirby extends BufferedImage implements Movable {

    private final Polygon polygon;
    private MyPoint position;
    private final MyPoint centerOfKirby;
    private Graphics graphics;
    private final Color kirbyColor = new Color(253, 194, 216);
    private final Color kirbyCheeks = new Color(254, 124, 204);
    private final Color kirbyShoes = new Color(191, 60, 41);
    private KirbyPosition kirbyPosition;
    private Polygon kirby;
    private Polygon kirbyLeftShoe;
    private Polygon rightHand;

    private Polygon kirbyRightShoe;

    public Kirby(int width, int height, int imageType) {
        super(width, height, imageType);
        polygon = new Polygon(this);
        position = new MyPoint(100,280);
        polygon.addVert(new MyPoint(position.getX(),position.getY()));
        polygon.addVert(new MyPoint(position.getX()+width,position.getY()));
        polygon.addVert(new MyPoint(position.getX()+width,position.getY()+height));
        polygon.addVert(new MyPoint(position.getX(),position.getY()+height));
        centerOfKirby = new MyPoint(width/2,height/2);
    }

    public Polygon getPolygon(){
        return polygon;
    }

    public void setGraphics(Graphics graphics){
        this.graphics = graphics;
    }
    public void drawKirby(){
        MyPoint[]points = polygon.getVerts();
//        System.out.println(points[0].getX()+", "+points[0].getY());
        graphics.drawImage(this, points[0].getX(), points[0].getY(),null);
    }
    private void kirbyRight(){
        //Right shoe
        kirbyRightShoe = new Polygon(this);
        kirbyRightShoe.changeColor(kirbyShoes);
        MyPoint[] rightShoe = kirbyRightShoe.getPointsOfArc(centerOfKirby.getX()+25,centerOfKirby.getY()+50,20,20,180,180);
        kirbyRightShoe.setVerts(rightShoe);
        //Colors to Compare
        int []colorsOfRightShoe = {0, Color.BLACK.getRGB(), kirbyColor.getRGB()};
        Flood.apply(kirbyRightShoe.drawPolygon(),colorsOfRightShoe);

        //Right Hand
        rightHand = new Polygon(this);
        rightHand.changeColor(kirbyColor);
        MyPoint[] pointsOfRightHand = rightHand.getPointsOfArc(centerOfKirby.getX()+30,
                centerOfKirby.getY()+10, 20,15,280,180);
        rightHand.setVerts(pointsOfRightHand);
        Flood.apply(rightHand.drawPolygon());
        rightHand.changeColor(Color.BLACK);
        rightHand.drawPolygon();

        kirby = new Polygon(this);
        kirby.changeColor(kirbyColor);
        MyPoint[] headPartTop = kirby.getPointsOfArc(centerOfKirby.getX(),centerOfKirby.getY()-5,40,40,180,180);
        MyPoint[] headBottom = kirby.getPointsOfArc(centerOfKirby.getX(),centerOfKirby.getY()+5,40,40,0,180);

        Curve curve = new Curve(this);
        MyPoint[] pointsHand = curve.getPointsOfCurve(100,new MyPoint(35,90),10,20);
        Polygon leftHand = new Polygon(this);
        leftHand.setVerts(pointsHand);
        pointsHand = Transform.rotate(leftHand,270);

        MyPoint[]kirbyPoints =  Stream.concat(
                        Arrays.stream(headPartTop).limit(98),
                        Arrays.stream(headBottom).limit(98))
                .toArray(MyPoint[]::new);
        kirbyPoints = Stream.concat(
                        Arrays.stream(kirbyPoints),
                        Arrays.stream(pointsHand))
                .toArray(MyPoint[]::new);
        kirby.setVerts(kirbyPoints);
        Flood.apply(kirby.drawPolygon(),new int[]{0, kirbyShoes.getRGB(),Color.BLACK.getRGB(), Color.PINK.getRGB()});
        kirby.changeColor(Color.BLACK);
        kirby.drawPolygon();

        //Left shoe
        kirbyLeftShoe = new Polygon(this);
        kirbyLeftShoe.changeColor(kirbyShoes);
        MyPoint[] leftShoe = kirbyLeftShoe.getPointsOfArc(centerOfKirby.getX()-25,
                centerOfKirby.getY()+50,20,20,180,180);
        kirbyLeftShoe.setVerts(leftShoe);
        //Colors to Compare
        int []colors = {0, Color.BLACK.getRGB(), kirbyColor.getRGB()};
        Flood.apply(kirbyLeftShoe.drawPolygon(),colors);
        //Eyes
        OutlineShape outlineShape = new OutlineShape();
        outlineShape.changeColor(Color.BLACK);
        Flood.apply(outlineShape.drawEllipse(100,60,5,13,this,graphics),new int[]{0, kirbyColor.getRGB(),Color.PINK.getRGB()});
        outlineShape.changeColor(Color.WHITE);
        Flood.apply(outlineShape.drawBasicCircle(100,57,2,this,graphics));

        outlineShape.changeColor(Color.BLACK);
        Flood.apply(outlineShape.drawEllipse(115,60,5,13,this,graphics));
        outlineShape.changeColor(Color.WHITE);
        Flood.apply(outlineShape.drawBasicCircle(115,57,2,this,graphics));

        //Cheeks
        outlineShape.changeColor(kirbyCheeks);
        Flood.apply(outlineShape.drawRectangle(centerOfKirby.getX()-15, centerOfKirby.getY(),
                6,3,this,graphics));
        Flood.apply(outlineShape.drawRectangle(centerOfKirby.getX()+20, centerOfKirby.getY(),
                6,3,this,graphics));

        //Mouth
        outlineShape.changeColor(new Color(229, 160, 179));
        Flood.apply(outlineShape.drawBasicCircle(centerOfKirby.getX() + 6,
                centerOfKirby.getY()+5, 2,this,graphics));
    }

    private void kirbyLeft(){
        kirbyLeftShoe = new Polygon(this);
        kirbyLeftShoe.changeColor(kirbyShoes);
        MyPoint[] leftShoe = kirbyLeftShoe.getPointsOfArc(centerOfKirby.getX()-25,
                centerOfKirby.getY()+50,20,20,180,180);
        kirbyLeftShoe.setVerts(leftShoe);
        //Colors to Compare
        int []colors = {0, Color.BLACK.getRGB(), kirbyColor.getRGB()};
        Flood.apply(kirbyLeftShoe.drawPolygon(),colors);

        //Left Hand (mano corta)
        rightHand = new Polygon(this);
        rightHand.changeColor(kirbyColor);

        MyPoint[] pointsOfRightHand = rightHand.getPointsOfArc(centerOfKirby.getX()-30,
                centerOfKirby.getY()+10,20,15,90,180);
        rightHand.setVerts(pointsOfRightHand);
        Flood.apply(rightHand.drawPolygon());
        rightHand.changeColor(Color.BLACK);
        rightHand.drawPolygon();

        kirby = new Polygon(this);
        kirby.changeColor(kirbyColor);
        MyPoint[] headPartTop = kirby.getPointsOfArc(centerOfKirby.getX(),centerOfKirby.getY()-5,40,40,180,180);
        MyPoint[] headBottom = kirby.getPointsOfArc(centerOfKirby.getX(),centerOfKirby.getY()+5,40,40,0,180);

        Curve curve = new Curve(this);
        MyPoint[] pointsHand = curve.getPointsOfCurve(100,new MyPoint(135, 90),10,20);
        rightHand.setVerts(pointsHand);
        pointsHand = Transform.rotate(rightHand,90);

        MyPoint[]kirbyPoints =  Stream.concat(
                        Arrays.stream(headPartTop).limit(98),
                        Arrays.stream(pointsHand))
                .toArray(MyPoint[]::new);
        kirbyPoints = Stream.concat(
                        Arrays.stream(kirbyPoints),
                        Arrays.stream(headBottom))
                .toArray(MyPoint[]::new);
        kirby.setVerts(kirbyPoints);
        Flood.apply(kirby.drawPolygon(),new int[]{0, kirbyShoes.getRGB(),Color.BLACK.getRGB(), Color.PINK.getRGB()});
        kirby.changeColor(Color.BLACK);
        kirby.drawPolygon();

        //Right shoe
        kirbyRightShoe = new Polygon(this);
        kirbyRightShoe.changeColor(kirbyShoes);
        MyPoint[] rightShoe = kirbyRightShoe.getPointsOfArc(centerOfKirby.getX()+25,centerOfKirby.getY()+50,20,20,180,180);
        kirbyRightShoe.setVerts(rightShoe);
        //Colors to Compare
        int []colorsOfRightShoe = {0, Color.BLACK.getRGB(), kirbyColor.getRGB()};
        Flood.apply(kirbyRightShoe.drawPolygon(),colorsOfRightShoe);

        //Eyes
        OutlineShape outlineShape = new OutlineShape();
        outlineShape.changeColor(Color.BLACK);
        Flood.apply(outlineShape.drawEllipse(100,60,5,13,this,graphics),new int[]{0, kirbyColor.getRGB(),Color.PINK.getRGB()});
        outlineShape.changeColor(Color.WHITE);
        Flood.apply(outlineShape.drawBasicCircle(100,57,2,this,graphics));

        outlineShape.changeColor(Color.BLACK);
        Flood.apply(outlineShape.drawEllipse(85,60,5,13,this,graphics));
        outlineShape.changeColor(Color.WHITE);
        Flood.apply(outlineShape.drawBasicCircle(85,57,2,this,graphics));

        //Cheeks
        outlineShape.changeColor(kirbyCheeks);
        Flood.apply(outlineShape.drawRectangle(centerOfKirby.getX()-30, centerOfKirby.getY(),
                6,3,this,graphics));
        Flood.apply(outlineShape.drawRectangle(centerOfKirby.getX()+5, centerOfKirby.getY(),
                6,3,this,graphics));

        //Mouth
        outlineShape.changeColor(new Color(229, 160, 179));
        Flood.apply(outlineShape.drawBasicCircle(centerOfKirby.getX() - 9,
                centerOfKirby.getY()+5, 2,this,graphics));
    }

    public void setKirbyPosition(KirbyPosition position){
        this.kirbyPosition = position;
        if (kirbyPosition == KirbyPosition.RIGHT){
            kirbyRight();
        }else{
            kirbyLeft();
        }
    }

    @Override
    public void moveRight(int current, int inferiorLimit, int superiorLimit) {
        if (current >= inferiorLimit & current < superiorLimit)
            Transform.translate(this.getPolygon(),5,0);
    }

    @Override
    public void moveLeft(int current, int inferiorLimit, int superiorLimit) {
        if (current >= inferiorLimit & current < superiorLimit)
            Transform.translate(this.getPolygon(),-5,0);
    }
    public enum KirbyPosition{
        RIGHT, LEFT
    }
    public void setPosition(MyPoint position) {
        this.position = position;
        polygon.setVerts(new MyPoint[]{new MyPoint(position.getX(),position.getY()),
                new MyPoint(position.getX()+getWidth(),position.getY()),
                new MyPoint(position.getX()+getWidth(),position.getY()+getHeight()),
                new MyPoint(position.getX(),position.getY()+getHeight())
        });
    }
}
