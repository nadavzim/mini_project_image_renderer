package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
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
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private int numberOfRaysInPixel = 0;

    /**
     * Camera constructor
     *
     * @param p0  the camera's location
     * @param vTo the vector that point to the view plane
     * @param vUp the vector that point up
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");

        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp);

    }

    public static int getSign(double number) {
        return number < 0 ? -1 : 1;
    }

    public void setNumberOfRaysInPixel(int numberOfRaysInPixel) {
        this.numberOfRaysInPixel = numberOfRaysInPixel;
    }

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
    //------------------end of getters------------------//

    public double getDistance() {
        return distance;
    }

    public int numberOfRaysInPixel() {
        return numberOfRaysInPixel;
    }

    /**
     * set the view plane size
     *
     * @param width  the width of the view plane
     * @param height the height of the view plane
     * @return the camera
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set the distance between the camera and the view plane
     *
     * @param distance the distance between the camera and the view plane
     * @return the camera
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * set the image writer
     *
     * @param imageWriter the image writer
     * @return the camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * set the ray tracer
     *
     * @param rayTracer the ray tracer
     * @return the camera
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * construct a ray from the camera to the view plane
     *
     * @param nX the number of pixels in the width of the view plane
     * @param nY the number of pixels in the height of the view plane
     * @param j  the index of the pixel in the width of the view plane
     * @param i  the index of the pixel in the height of the view plane
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

        //moving on the view-plane according to delta values if necessary
        if (!isZero(Xj))
            Pij = Pij.add(vRight.scale(Xj));
        if (!isZero(Yi))
            Pij = Pij.add(vUp.scale(Yi));

        // vector from camera's eye in the direction of point(i,j) in the view plane
        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);
    }

    /**
     * The function iterates over all the pixels in the image and casts a ray through each pixel
     */
    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("Renderer resource not set", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("Renderer resource not set", RayTracerBase.class.getName(), "");
            }
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            for (int row = 0; row < nY; row++) {
                for (int col = 0; col < nX; col++) {
                    List<Ray> rays = constructRaysThroughPixel(nX, nY, row, col);
                    imageWriter.writePixel(row, col, rayTracer.calcAverageColor(rays));

//                    Color color = castRay(nX, nY, row, col);
//                    this.imageWriter.writePixel(row, col, color);
                }
            }

        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not yet initialized" + e.getClassName());
        }
        return this;
    }

    /**
     * construct a ray from the camera through a specific pixel in the View Plane
     * and get the color of the pixel
     *
     * @param nX how many pixels are in the X dim
     * @param nY how many pixels are in the Y dim
     * @param j  the pixel to go through X dim
     * @param i  the pixel to go through Y dim
     * @return the color of the pixel
     */
    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = this.constructRay(nX, nY, j, i);
        return rayTracer.traceRay(ray);
    }

    /**
     * This function prints a grid on the image, with the given interval and color.
     *
     * @param interval the interval between the lines
     * @param color    the color of the grid
     */
    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null) {
            throw new MissingResourceException("Renderer resource not set", "Render", "Image writer");
        }
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int col = 0; col < nX; col++) {
                if (row % interval == 0 || col % interval == 0) {
                    imageWriter.writePixel(row, col, color);
                }
            }
        }
    }

    /**
     * If the imageWriter is not null, write to the image.
     */
    public void writeToImage() {
        if (this.imageWriter == null) {
            throw new MissingResourceException("Renderer resource not set", "Render", "Image writer");
        }
        this.imageWriter.writeToImage();
    }

    public Camera moveCamera(Vector vector) {
        p0 = p0.add(vector);
        return this;
    }

    /**
     * spin the camera 'angle' degrees clockwise around the To vector
     *
     * @param angle the angle we want to spin the camera
     * @return this instance of camera
     */
    public Camera spin(double angle) {
        double angleRad = Math.toRadians(angle);
        double cos0 = Math.cos(angleRad);
        double sin0 = Math.sin(angleRad);
        if (isZero(cos0)) {
            vUp = vRight.scale(getSign(angle));
        } else if (isZero(sin0)) {
            vUp = vUp.scale(cos0);
        } else {//rotate around the To vector using Rodrigues' rotation formula
            vUp = vUp.scale(cos0)
                    .add(vTo.crossProduct(vUp).scale(sin0));
        }
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * spin the camera 'angle' degrees clockwise around the Up vector
     *
     * @param angle the angle we want to spin the camera
     * @return this instance of camera
     */
    public Camera spinRightLeft(double angle) {
        double angleRad = Math.toRadians(angle);
        double cos0 = Math.cos(angleRad);
        double sin0 = Math.sin(angleRad);
        if (isZero(cos0)) {
            vTo = vRight.scale(getSign(angle));
        } else if (isZero(sin0)) {
            vTo = vTo.scale(cos0);
        } else {//rotate around the To vector using Rodrigues' rotation formula
            vTo = vTo.scale(cos0)
                    .add(vUp.crossProduct(vTo).scale(Math.sin(angle)));
        }
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * spin the camera 'angle' degrees clockwise around the Right vector
     *
     * @param angle the angle we want to spin the camera
     * @return this instance of camera
     */
    public Camera spinUpDown(double angle) {
        double angleRad = Math.toRadians(angle);
        double cos0 = Math.cos(angleRad);
        double sin0 = Math.sin(angleRad);
        if (isZero(cos0)) {
            vTo = vUp.scale(getSign(angle));
        } else if (isZero(sin0)) {
            vTo = vUp.scale(cos0);
        } else {//rotate around the To vector using Rodrigues' rotation formula
            vTo = vTo.scale(cos0)
                    .add(vRight.crossProduct(vTo).scale(sin0));
        }
        vUp = vTo.crossProduct(vRight).scale(-1).normalize();
        return this;
    }


    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {

        // the returned list of rays
        List<Ray> rays = new ArrayList<>();

        // add the center ray to the list
        Ray centerRay = constructRay(nX, nY, j, i);
        rays.add(centerRay);

        // calculate the actual size of a pixel
        // pixel height is the division of the view plane height in the number of rows of pixels
        double pixelHeight = alignZero(height / nY);   //  Ry = h/Ny
        // pixel width is the division of the view plane width in the number of columns of pixels
        double pixelWidth = alignZero(width / nX);   //  Rx = w/Nx

        if (numberOfRaysInPixel != 1) {
            rays.addAll(centerRay.randomRaysInGrid(
                    vUp,
                    vRight,
                    numberOfRaysInPixel,
                    distance,
                    pixelWidth,
                    pixelHeight)
            );
        }

        return rays;
    }

}
