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
    private final int radius = 50;
    private MyPoint position;
    private final MyPoint centerOfKirby;
    private Graphics graphics;
    private final Color kirbyColor = new Color(253, 194, 216);
    private final Color kirbyCheeks = new Color(254, 124, 204);
    private final Color kirbyShoes = new Color(191, 60, 41);

    public Kirby(int width, int height, int imageType) {
        super(width, height, imageType);
        polygon = new Polygon(this);
        position = new MyPoint(100,275);
        polygon.addVert(new MyPoint(position.getX(),position.getY()));
        polygon.addVert(new MyPoint(position.getX()+width,position.getY()));
        polygon.addVert(new MyPoint(position.getX()+width,position.getY()+height));
        polygon.addVert(new MyPoint(position.getX(),position.getY()+height));
        centerOfKirby = new MyPoint(width/2,height/2);
        getKirby();
    }

    public Polygon getPolygon(){
        return polygon;
    }

    public void setGraphics(Graphics graphics){
        this.graphics = graphics;
    }
    public void drawKirby(){
        MyPoint[]points = polygon.getVerts();
        graphics.drawImage(this, points[0].getX(), points[0].getY(),null);
    }
    private void getKirby(){
        //Right shoe
        Polygon polygonOfRightShoe = new Polygon(this);
        polygonOfRightShoe.changeColor(kirbyShoes);
        MyPoint[] rightShoe = polygonOfRightShoe.getPointsOfArc(centerOfKirby.getX()+25,centerOfKirby.getY()+50,20,20,180,180);
        polygonOfRightShoe.setVerts(rightShoe);
        //Colors to Compare
        int []colorsOfRightShoe = {0, Color.BLACK.getRGB(), kirbyColor.getRGB()};
        Flood.apply(polygonOfRightShoe.drawPolygon(),colorsOfRightShoe);

        //Right Hand
        Polygon rightHand = new Polygon(this);
        rightHand.changeColor(kirbyColor);
        MyPoint[] pointsOfRightHand = rightHand.getPointsOfArc(centerOfKirby.getX()+30,
                centerOfKirby.getY()+10, 20,15,280,180);
        rightHand.setVerts(pointsOfRightHand);
        Flood.apply(rightHand.drawPolygon());
        rightHand.changeColor(Color.BLACK);
        rightHand.drawPolygon();

        Polygon kirby = new Polygon(this);
        kirby.changeColor(kirbyColor);
        MyPoint[] headPartTop = kirby.getPointsOfArc(centerOfKirby.getX(),centerOfKirby.getY()-5,40,40,180,180);
        MyPoint[] headBottom = kirby.getPointsOfArc(centerOfKirby.getX(),centerOfKirby.getY()+5,40,40,0,180);

        Curve curve = new Curve(this);
        MyPoint[] pointsHand = curve.getPointsOfCurve(100,new MyPoint(35,90),10,20);
        Polygon polygon = new Polygon(this);
        polygon.setVerts(pointsHand);
        pointsHand = Transform.rotate(polygon,270);

        MyPoint[]kirbyPoints =  Stream.concat(
                        Arrays.stream(headPartTop).limit(98),
                        Arrays.stream(headBottom).limit(98))
                .toArray(MyPoint[]::new);
        kirbyPoints = Stream.concat(
                        Arrays.stream(kirbyPoints),
                        Arrays.stream(pointsHand))
                .toArray(MyPoint[]::new);
        kirby.setVerts(kirbyPoints);
        Flood.apply(kirby.drawPolygon(),new int[]{0, kirbyShoes.getRGB(),Color.BLACK.getRGB()});
        kirby.changeColor(Color.BLACK);
        kirby.drawPolygon();

        //Left shoe
        Polygon polygonOfKirbyLeftShoe = new Polygon(this);
        polygonOfKirbyLeftShoe.changeColor(kirbyShoes);
        MyPoint[] leftShoe = polygonOfKirbyLeftShoe.getPointsOfArc(centerOfKirby.getX()-25,
                centerOfKirby.getY()+50,20,20,180,180);
        polygonOfKirbyLeftShoe.setVerts(leftShoe);
        //Colors to Compare
        int []colors = {0, Color.BLACK.getRGB(), kirbyColor.getRGB()};
        Flood.apply(polygonOfKirbyLeftShoe.drawPolygon(),colors);
        //Eyes
        OutlineShape outlineShape = new OutlineShape();
        outlineShape.changeColor(Color.BLACK);
        Flood.apply(outlineShape.drawEllipse(100,60,5,13,this,graphics));
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

    @Override
    public void moveRight(int current, int inferiorLimit, int superiorLimit) {
        if (current >= inferiorLimit & current < superiorLimit)
            Transform.translate(this.getPolygon(),5,0);
    }

    @Override
    public void moveLeft(int current, int inferiorLimit, int superiorLimit) {
        if (current >= inferiorLimit & current < superiorLimit)
            Transform.translate(this.getPolygon(),5,0);
    }
}
