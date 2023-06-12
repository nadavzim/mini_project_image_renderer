package geometries;
import primitives.Double3;
import primitives.Point;

import java.util.List;

public class Box {
    Point origin;
    int width;
    int height;
    int depth;
    List<Polygon> b;

    public List<Polygon> getGeo() {
        return b;
    }

    public Box(int width, int height, int depth, Point origin) {
        if (width <= 0 || height <= 0 || depth <= 0)
            throw new IllegalArgumentException("The width, height and depth need to bigger then zero!");
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.origin = origin;
        make_Box();
    }
    public void make_Box() {

        // Define the eight vertices of the box
        Point p1 = origin;
        Point p2 = new Point(origin.getX() + width, origin.getY(), origin.getZ());
        Point p3 = new Point(origin.getX() + width, origin.getY() + depth, origin.getZ());
        Point p4 = new Point(origin.getX(), origin.getY() + depth, origin.getZ());
        Point p5 = new Point(origin.getX(), origin.getY(), origin.getZ() + height);
        Point p6 = new Point(origin.getX() + width, origin.getY(), origin.getZ() + height);
        Point p7 = new Point(origin.getX() + width, origin.getY() + depth, origin.getZ() + height);
        Point p8 = new Point(origin.getX(), origin.getY() + depth, origin.getZ() + height);

        // Draw the six polygons representing the sides of the box
        Polygon polygon1 = new Polygon(p1, p2, p3, p4);
        Polygon polygon2 = new Polygon(p1, p4, p8, p5);
        Polygon polygon3 = new Polygon(p2, p3, p7, p6);
        Polygon polygon4 = new Polygon(p1, p2, p6, p5);
        Polygon polygon5 = new Polygon(p4, p3, p7, p8);
        Polygon polygon6 = new Polygon(p5, p6, p7, p8);
        b = List.of(polygon1, polygon2,polygon3, polygon4, polygon5, polygon6);
    }

}
