package Parser;

import java.util.Map;

import geometries.*;
import primitives.*;

public class Utils {
    /**
     * @param strings array of strings
     * @return array of doubles
     */
    public static double[] mapStringToDouble(String[] strings) {
        double[] res = new double[strings.length];
        for (int i = 0; i < strings.length; i++) {
            res[i] = Double.parseDouble(strings[i]);
        }
        return res;
    }

    /**
     * @param string string of doubles
     * @return point
     */
    public static Point makePointFromString(String string) {
        double[] numbers = mapStringToDouble(string.split(" "));
        return new Point(numbers[0], numbers[1], numbers[2]);
    }
    /**
     * @param string string of doubles
     * @return color
     */
    public static Color makeColorFromString(String string) {
        double[] numbers = mapStringToDouble(string.split(" "));
        return new Color(numbers[0], numbers[1], numbers[2]);
    }
    /**
     * @param sphereString of doubles
     * @return sphere
     */
    public static Sphere makeSphere(Map<String, String> sphereString) {
        String centerString = sphereString.get("center");
        Point center = makePointFromString(centerString);
        Double radius = Double.parseDouble(sphereString.get("radius"));
        return new Sphere(center, radius);
    }
    /**
     * @param  triangleString of doubles
     * @return triangle
     */
    public static Triangle makeTriangle(Map<String, String> triangleString) {
        String p0String = triangleString.get("p0");
        Point p0 = makePointFromString(p0String);
        String p1String = triangleString.get("p1");
        Point p1 = makePointFromString(p1String);
        String p2String = triangleString.get("p2");
        Point p2 = makePointFromString(p2String);
        return new Triangle(p0, p1, p2);
    }
}
