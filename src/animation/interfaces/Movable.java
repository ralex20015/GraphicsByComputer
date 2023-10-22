package animation.interfaces;

import Forms.Polygon;
import transforms.Transform;

public interface Movable {
    void moveRight(int current, int inferiorLimit,int superiorLimit);
    void moveLeft(int current, int inferiorLimit, int superiorLimit);
//    void jump();
    default void jumpLeft(int current, int inferiorLimit, int superiorLimit, Polygon polygon){
        if (current >= inferiorLimit & current < superiorLimit)
            Transform.translate(polygon,-5,-5);

    }
    default void jumpRight(int current, int inferiorLimit, int superiorLimit, Polygon polygon){
        if (current >= inferiorLimit & current < superiorLimit){
            Transform.translate(polygon,5,-5);
        }
    }
    default void fall(int current, int inferiorLimit, int superiorLimit, Polygon polygon){
        if (current >= inferiorLimit & current < superiorLimit)
            Transform.translate(polygon,0,+7);
    }

    default void fallNormal(int current, int inferiorLimit, int superiorLimit, Polygon polygon){
        if (current >= inferiorLimit & current < superiorLimit)
            Transform.translate(polygon,0,+5);
    }
}
