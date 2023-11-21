package _3D.Transforms;

import _3D.projection.Cube;
import utilities.MyPoint;


public class Transform {

    public static void translate(Cube cube, int tx, int ty, int tz){
        int [][]translationMatrix = {
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
        double[][] matrixOfPoints =  cube.getMatrixOfPoints();
        double[][] translatedPoints = new double[8][3];
        int xIndex = 0;
        int yIndex = 1;
        int zIndex = 2;
        int matrixHomogeneous = 1;
        for (int i = 0; i < matrixOfPoints.length; i++) {
            translatedPoints[i][xIndex] = ((int)(matrixOfPoints[i][xIndex]) + (translationMatrix[0][3] * matrixHomogeneous));
            translatedPoints[i][yIndex] = ((int)(matrixOfPoints[i][yIndex]) + (translationMatrix[1][3]* matrixHomogeneous));
            translatedPoints[i][zIndex] = ((int)(matrixOfPoints[i][zIndex]) + (translationMatrix[2][3] * matrixHomogeneous));
        }
        MyPoint[]points = new MyPoint[8];
        for (int i = 0; i < translatedPoints.length; i++) {
            points[i] = new MyPoint((int)translatedPoints[i][0], (int)translatedPoints[i][1], (int)translatedPoints[i][2]);
        }
        cube.setPoints(points);
    }

    public static void rotationInX(Cube cube, double rotation){
        double radians = Math.toRadians(rotation);
        double[][]matrixOfRotation = {{Math.cos(radians), Math.sin(radians)},
                {-Math.sin(radians), Math.cos(radians)}};
        double [][]matrixOfPoints = cube.getMatrixOfPoints();
        int j = 0;
        int k = 1;
        int yPoint = 1;
        int zPoint = 2;
        int[][]resultingMatrix = new int[8][2];
        MyPoint center = cube.getCenter();
        for (int i = 0; i < matrixOfPoints.length; i++) {
            for (int l = 0; l < 2; l++) {
                resultingMatrix[i][l] = (int)(((matrixOfRotation[l][j] * (matrixOfPoints[i][yPoint] - center.getY()))
                        + (matrixOfRotation[l][k] * (matrixOfPoints[i][zPoint] - center.getZ()))) + center.getZ());
            }
        }
        MyPoint[]points = new MyPoint[8];
        int x = 0;
        int y  = 1;
        int z = 2;
        for (int i = 0; i < resultingMatrix.length; i++) {
            points[i] = new MyPoint((int)matrixOfPoints[i][x],resultingMatrix[i][0], resultingMatrix[i][1]);
        }
        cube.setPoints(points);
    }

    public static void rotationInY(Cube cube, double rotation){
        double radians = Math.toRadians(rotation);
        double[][]matrixOfRotation = {{Math.cos(radians), -Math.sin(radians)},
                {Math.sin(radians), Math.cos(radians)}};
        double [][]matrixOfPoints = cube.getMatrixOfPoints();
        int j = 0;
        int k = 1;
        int xPoint = 0;
        int zPoint = 2;
        int[][]resultingMatrix = new int[8][2];
        MyPoint center = cube.getCenter();
        for (int i = 0; i < matrixOfPoints.length; i++) {
            for (int l = 0; l < 2; l++) {
                resultingMatrix[i][l] = (int)(((matrixOfRotation[l][j] * (matrixOfPoints[i][xPoint] - center.getX()))
                        + (matrixOfRotation[l][k] * (matrixOfPoints[i][zPoint] - center.getZ()))) + center.getZ());
            }
        }
        MyPoint[]points = new MyPoint[8];
        int y = 1;
        for (int i = 0; i < resultingMatrix.length; i++) {
            points[i] = new MyPoint(resultingMatrix[i][0], (int)matrixOfPoints[i][y], resultingMatrix[i][1]);
        }
        cube.setPoints(points);
    }

    public static void rotationInZ(Cube cube, double rotation){
        double radians = Math.toRadians(rotation);
        double[][]matrixOfRotation = {{Math.cos(radians), Math.sin(radians)},
                {-Math.sin(radians), Math.cos(radians)}};
        double [][]matrixOfPoints = cube.getMatrixOfPoints();
        int j = 0;
        int k = 1;
        int[][]resultingMatrix = new int[8][2];
        MyPoint center = cube.getCenter();
        for (int i = 0; i < matrixOfPoints.length; i++) {
            for (int l = 0; l < 2; l++) {
                resultingMatrix[i][l] = (int)(((matrixOfRotation[l][j] * (matrixOfPoints[i][j] - center.getX()))
                        + (matrixOfRotation[l][k] * (matrixOfPoints[i][k] - center.getY()))) + center.getZ());
            }
        }
        MyPoint[]points = new MyPoint[8];
        int x = 0;
        int y  = 1;
        int z = 2;
        for (int i = 0; i < resultingMatrix.length; i++) {
            points[i] = new MyPoint(resultingMatrix[i][x], resultingMatrix[i][y], (int)matrixOfPoints[i][z]);
        }
        cube.setPoints(points);
    }

    public static void scale(Cube cube, double scaleFactorOfX, double scaleFactorOfY, double scaleFactorOfZ){
        double[][] matrixOfScale = {
                {scaleFactorOfX, 0, 0 , 0},
                {0, scaleFactorOfY, 0, 0},
                {0, 0, scaleFactorOfZ, 0},
                {0, 0, 0, 1}
        };

        double sx;
        double sy;
        double sz;

        if (scaleFactorOfX >= 2){
            sx = scaleFactorOfX - 1;
        }else{
            sx = 1 - scaleFactorOfX;
        }
        if (scaleFactorOfY >= 2){
            sy = scaleFactorOfY - 1;
        }else{
            sy = 1 - scaleFactorOfY;
        }
        if (scaleFactorOfZ >= 2){
            sz = scaleFactorOfZ - 1;
        }else{
            sz = 1 - scaleFactorOfZ;
        }

        double[][] matrixOfPoints =  cube.getMatrixOfPoints();
        MyPoint origin = new MyPoint((int)matrixOfPoints[4][0], (int)matrixOfPoints[4][1], (int)matrixOfPoints[4][2]);
        double[][] scaledPoints = new double[8][3];
        int xIndex = 0;
        int yIndex = 1;
        int zIndex = 2;
        for (int i = 0; i < matrixOfPoints.length; i++) {

            if (scaleFactorOfX >=2){
                scaledPoints[i][xIndex]  = (int) ((matrixOfPoints[i][xIndex] * matrixOfScale[0][0])
                        - (int)(origin.getX() * sx));
            }else{
                scaledPoints[i][xIndex]  = (int) ((matrixOfPoints[i][xIndex] * matrixOfScale[0][0])
                        + (int)(origin.getX() * sx));
            }

            if (scaleFactorOfY >=2){
                scaledPoints[i][yIndex]  = (int) ((matrixOfPoints[i][yIndex] * matrixOfScale[1][1])
                        - (int)(origin.getY() * sy));
            }else{
                scaledPoints[i][yIndex]  = (int) ((matrixOfPoints[i][yIndex] * matrixOfScale[1][1])
                        + (int)(origin.getY() * sy));
            }

            if (scaleFactorOfZ >=2){
                scaledPoints[i][zIndex]  = (int) ((matrixOfPoints[i][zIndex] * matrixOfScale[2][2])
                        - (int)(origin.getZ() * sz));
            }else{
                scaledPoints[i][zIndex]  = (int) ((matrixOfPoints[i][zIndex] * matrixOfScale[2][2])
                        + (int)(origin.getZ() * sz));
            }
        }

        MyPoint[]points = new MyPoint[8];
        for (int i = 0; i < scaledPoints.length; i++) {
            points[i] = new MyPoint((int)scaledPoints[i][0], (int)scaledPoints[i][1], (int)scaledPoints[i][2]);
        }
        cube.setPoints(points);
    }
}
