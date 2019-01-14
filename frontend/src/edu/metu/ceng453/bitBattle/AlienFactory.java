package edu.metu.ceng453.bitBattle;

import javafx.scene.Node;

public class AlienFactory {

    public Alien getAlien(String type, Node n){
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("circle")){
            return new CircleAlien(n);

        } else if(type.equalsIgnoreCase("triangle")){
            return new TriangleAlien(n);

        } else if(type.equalsIgnoreCase("parallelogram")){
            return new ParallelogramAlien(n);
        }

        return null;
    }
}
