package animation;

import Curve.Curve;
import Fill.Flood;
import Fill.ScanLine;
import Forms.OutlineShape;
import Forms.Polygon;
import Forms.Shape;
import transforms.Transform;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Stream;

public class Kirby extends BufferedImage{

    private final Polygon polygon;
    private int radius = 50;
    private MyPoint position;
    private MyPoint centerOfKirby;
    private Graphics graphics;
    private Color kirbyColor = new Color(253, 194, 216);

    public Kirby(int width, int height, int imageType) {
        super(width, height, imageType);
        polygon = new Polygon(this);
        position = new MyPoint(100,250);
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

    public MyPoint getPosition() {
        return position;
    }

    public void translate(){

    }
    public void setGraphics(Graphics graphics){
        this.graphics = graphics;
    }
    public void drawKirby(){
        MyPoint[]points = polygon.getVerts();
        graphics.drawImage(this, points[0].getX(), points[0].getY(),null);
    }

    public void setPosition(MyPoint position) {
        this.position = position;
    }

    private void getKirby(){
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
        Flood.apply(kirby.drawPolygon());
        kirby.changeColor(Color.BLACK);
        kirby.drawPolygon();

        Polygon p2 = new Polygon(this);
        MyPoint[] leftShoe = p2.getPointsOfArc(centerOfKirby.getX()-25,centerOfKirby.getY()+50,20,20,180,180);
        p2.setVerts(leftShoe);
        //Colors to Compare
        int []colors = {0, Color.BLACK.getRGB(), kirbyColor.getRGB()};
        Flood.apply(p2.drawPolygon(),colors);
        //Ojos
        OutlineShape eye = new OutlineShape();
        eye.changeColor(Color.BLACK);
        Flood.apply(eye.drawEllipse(100,60,5,13,this,graphics));
        eye.changeColor(Color.WHITE);
        Flood.apply(eye.drawBasicCircle(100,57,2,this,graphics));

        eye.changeColor(Color.BLACK);
        Flood.apply(eye.drawEllipse(115,60,5,13,this,graphics));
        eye.changeColor(Color.WHITE);
        Flood.apply(eye.drawBasicCircle(115,57,2,this,graphics));

    }
}
