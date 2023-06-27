package geometries;

import primitives.Color;
import primitives.Point;

import java.util.List;

public class lamp extends Geometries{
    Point light;
    int width;
    int height;
    Color color;

    List<Polygon> b;


    public lamp(Point light, int width, int height, Color color) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("The width and height need to bigger then zero!");
        this.width = width;
        this.height = height;
        this.light = light;
        this.color = color;
        make_lamp();
    }

    public List<Polygon> getGeo() {
        return b;
    }


    public void make_lamp() {

        // Define the eight vertices of the box
        Point p1 = light;

        Point p2 = new Point(light.getX() + ((width/2f)*(2/3f)), light.getY() + 200 , light.getZ() + height/2f);
        Point p3 = new Point(light.getX() + (width/2f), light.getY() + 200, light.getZ() - height/2f);
        Point p4 = new Point(light.getX() - (width/2f), light.getY() + 200, light.getZ() - height/2f);
        Point p5 = new Point(light.getX() - ((width/2f)*(2/3f)), light.getY() + 200, light.getZ() + height/2f);

        Point p6 = new Point(light.getX() + (width/10f), light.getY() + 100, p2.getZ() + 50);
        Point p7 = new Point(light.getX() + (width/10f), light.getY() + 100, p2.getZ() - (height+20));
        Point p8 = new Point(light.getX() - (width/10f), light.getY() + 100, p2.getZ() - (height+20));
        Point p9 = new Point(light.getX() - (width/10f), light.getY() + 100, p2.getZ() + 50);

        // Draw the six polygons representing the sides of the box
        Polygon polygon1 = new Polygon(p2, p3, p4, p5);
        polygon1.setEmission(color);
        Polygon polygon2 = new Polygon(p6, p7, p8, p9);
        polygon2.setEmission(color);
//        Polygon polygon3 = new Polygon(p2, p3, p7, p6);
//        polygon3.setEmission(color);
        b = List.of(polygon1, polygon2);

    }

}
