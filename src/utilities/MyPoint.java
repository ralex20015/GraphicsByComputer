package utilities;

public class MyPoint{
    private int x;
    private int y;
    private int z;
    public MyPoint(){
        x = 0;
        y = 0;
    }
    public MyPoint(int x, int y){
        this.x = x;
        this.y = y;
    }
    public MyPoint(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String toString(){
        return "x = "+x+", y = "+y;
    }
}
