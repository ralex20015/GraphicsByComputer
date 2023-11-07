package transforms;

import Forms.Polygon;
import utilities.MyPoint;

public class Transform {

    public static MyPoint[] translate(Polygon polygon, int incrementX, int incrementY){
        MyPoint[] vertex = polygon.getVerts();
        MyPoint[] translatedVertex = new MyPoint[vertex.length];

        for (int i = 0; i < vertex.length; i++) {
            translatedVertex[i] = new MyPoint(0,0);
        }

       int [][]translationMatrix = {
                {1, 0, incrementX},
                {0, 1, incrementY},
                {0, 0, 1}
        };

        int xOfCurrentVertex = 0;
        int yOfCurrentVertex = 1;
        int two = 2;

        for (int i = 0; i < vertex.length; i++) {
            int [] pointToTranslate = {
                    vertex[i].getX(),
                    vertex[i].getY(),
                    1
            };
            for (int j = 3; j <= 9; j+=3) {
                int k = 0;
                int l = 1;
                int m = 2;

                if (j == 3) {
                    int result = (pointToTranslate[xOfCurrentVertex] * translationMatrix[k][k]) +
                            (pointToTranslate[yOfCurrentVertex] * translationMatrix[k][l]) +
                            (pointToTranslate[two] * translationMatrix[k][m]);
                    translatedVertex[i].setX(result);
                }else if(j == 6){
                    int result =  ((pointToTranslate[xOfCurrentVertex]) *  translationMatrix[l][k]) +
                            ((pointToTranslate[yOfCurrentVertex]) * translationMatrix[l][l]) +
                            (pointToTranslate[two] * translationMatrix[l][m]);
                    translatedVertex[i].setY(result);
                }
            }
        }
        polygon.setVerts(translatedVertex);
        return translatedVertex;
    }

    public static MyPoint[] rotate(Polygon polygon, double angle){
        MyPoint[] vertex = polygon.getVerts();
        MyPoint[] rotatedVertices = new MyPoint[vertex.length];

        for (int i = 0; i < vertex.length; i++) {
            rotatedVertices[i] = new MyPoint(0,0);
        }

        double radians = Math.toRadians(angle);

        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        double[][] rotationMatrix = {
                {cos, -sin, 0},
                {sin , cos, 0},
                {0, 0, 1}
        };
        MyPoint center = polygon.getMidPoint(vertex);

        int xOfCurrentVertex = 0;
        int yOfCurrentVertex = 1;
        int one = 2;
        for (int i = 0; i < vertex.length; i++) {
            int [] pointToRotate = {
                    vertex[i].getX(),
                    vertex[i].getY(),
                    1
            };
            for (int j = 3; j <= 9; j+=3) {
                int k = 0;
                int l = 1;
                int m = 2;

                if (j == 3) {
                    int result =  (int)((pointToRotate[xOfCurrentVertex] - center.getX()) *  rotationMatrix[k][k]) +
                            (int)((pointToRotate[yOfCurrentVertex] - center.getY()) * rotationMatrix[k][l]) +
                            (int)(pointToRotate[one] * rotationMatrix[k][m]) + center.getX();

                    rotatedVertices[i].setX(result);
                }else if(j == 6){
                    int result =  (int)((pointToRotate[xOfCurrentVertex] - center.getX()) *  rotationMatrix[l][k]) +
                            (int)((pointToRotate[yOfCurrentVertex] - center.getY()) * rotationMatrix[l][l]) +
                            (int)(pointToRotate[one] * rotationMatrix[l][m]) + center.getY();
                    rotatedVertices[i].setY(result);
                }
            }
        }
        polygon.setVerts(rotatedVertices);
        return rotatedVertices;
    }


    public static void rotateInBaseOfPivot(Polygon polygon, double angle, MyPoint pivot) {
        MyPoint[] vertex = polygon.getVerts();
        MyPoint[] rotatedVertices = new MyPoint[vertex.length];

        double radians = Math.toRadians(angle);

        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        for (int i = 0; i < vertex.length; i++) {
            rotatedVertices[i] = new MyPoint(
                    vertex[i].getX() - pivot.getX(),
                    vertex[i].getY() - pivot.getY()
            );
        }

        for (MyPoint myPoint : rotatedVertices) {
            double x = myPoint.getX();
            double y = myPoint.getY();
            myPoint.setX((int) (x * cos - y * sin));
            myPoint.setY((int) (x * sin + y * cos));
        }

        for (MyPoint rotatedVertex : rotatedVertices) {
            rotatedVertex.setX(pivot.getX() + rotatedVertex.getX());
            rotatedVertex.setY(pivot.getY() + rotatedVertex.getY());
        }

        polygon.setVerts(rotatedVertices);
    }

    public static MyPoint[] scaleWithMatrix(Polygon polygon, double sx, double sy){
        double [][]matrixOfScale= {
                {sx, 0, 0},
                {0, sy, 0},
                {0, 0, 1}
        };
        MyPoint[]vertex = polygon.getVerts();
        double scaleFactorX;
        double scaleFactorY;
        //this makes the polygon stay on the original position, and just scale
        if (sx >= 2){
            scaleFactorX = sx - 1;
        }else{
            scaleFactorX = 1 - sx;
        }
        if (sy >= 2){
            scaleFactorY = sy - 1;
        }else{
            scaleFactorY = 1 - sy;
        }
        MyPoint origin = vertex[0];

        for (MyPoint myPoint : vertex) {
            double[] currentPoint = {
                    myPoint.getX(),
                    myPoint.getY(),
                    1
            };
            for (int j = 3; j <= 9; j += 3) {
                int k = 0;
                int l = 1;
                int m = 2;
                if (j == 3) {
                    int result =0;
                    if (sx >=2){
                        result = (int) (currentPoint[k] * matrixOfScale[k][k]) - (int)(origin.getX() * scaleFactorX);
                    }else{
                        result = (int) (currentPoint[k] * matrixOfScale[k][k]) + (int)(origin.getX() * scaleFactorX) ;
                    }
                    myPoint.setX(result);
                } else if (j == 6) {
                    int result;
                    if (sy >= 2){
                        result = (int) (currentPoint[l] * matrixOfScale[l][l]) - (int)(origin.getY() * scaleFactorY);
                    }else{
                        result = (int) (currentPoint[l] * matrixOfScale[l][l]) + (int)(origin.getY() * scaleFactorY);
                    }
                    myPoint.setY(result);
                }
            }
        }
        polygon.setVerts(vertex);
        return vertex;
    }


}
