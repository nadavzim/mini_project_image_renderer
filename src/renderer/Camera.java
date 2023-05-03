package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    // the camera's location
    private Point p0;
    // the vector that point up
    private Vector vUp;
    // the vectors that point to the view plane
    private Vector vTo;
    // the vector that point right
    private Vector vRight;
    // the width and height of the view plane
    private double width;
    // the distance between the camera and the view plane
    private double height;
    // the distance between the camera and the view plane
    private double distance;

    //------------------getters------------------//
    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }
    //------------------end of getters------------------//

    /**
     * Camera constructor
     * @param p0 the camera's location
     * @param vTo the vector that point to the view plane
     * @param vUp the vector that point up
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero( vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");
        else {
            this.p0 = p0;
            this.vUp = vUp.normalize();
            this.vTo = vTo.normalize();
            this.vRight = vTo.crossProduct(vUp).normalize();
        }
    }
    /**
     * set the view plane size
     * @param width the width of the view plane
     * @param height the height of the view plane
     * @return the camera
     */
    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return  this;
    }
    /**
     * set the distance between the camera and the view plane
     * @param distance the distance between the camera and the view plane
     * @return the camera
     */
    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }

    /**
     * construct a ray from the camera to the view plane
     * @param nX the number of pixels in the width of the view plane
     * @param nY the number of pixels in the height of the view plane
     * @param j the index of the pixel in the width of the view plane
     * @param i the index of the pixel in the height of the view plane
     * @return the ray from the camera to the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point Pcenter = p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx = width / nX;
        double Ry = height / nY;

        //Pij point[i,j] in view-plane coordinates
        Point Pij = Pcenter;

        //delta values for moving on the view=plane
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj)) Pij = Pij.add(vRight.scale(Xj));
        if (!isZero(Yi)) Pij = Pij.add(vUp.scale(Yi));

        // vector from camera's eye in the direction of point(i,j) in the view plane
        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);
    }
}
