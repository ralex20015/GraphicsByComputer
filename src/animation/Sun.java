package animation;

import Curve.Curve;
import transforms.Transform;
import utilities.MyPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import Forms.Polygon;

public class Sun extends BufferedImage {
    private Polygon polygon;
    private int sunAngle = 0;
    private Graphics graphics;

    public Sun(int width, int height, int imageType) {
        super(width, height, imageType);
        Curve curve = new Curve(this);
//        polygon = new Polygon(this);
//        polygon.addVert(new MyPoint(0,0));
//        polygon.addVert(new MyPoint((width),0));
//        polygon.addVert(new MyPoint(width,height));
//        polygon.addVert(new MyPoint(0,height));
        curve.drawSun(new MyPoint(width/2,height/2),2, Color.ORANGE);
    }

    public Polygon getSun(){
        return polygon;
    }

//    public void rotateSun(){
//    }

    public void setGraphics(Graphics g){
        this.graphics = g;
//        g.drawImage(this,0,0,null);
//        Transform.rotate(polygon, sunAngle);
    }

    public void setAngle(int angle){
        this.sunAngle = angle;
    }

    public int getAngle(){
        return sunAngle;
    }
}
