package renderer;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



 class IntegrationTest {
     /**
     * Integration test for camera and geometries
     * @param camera the camera
     * @param geo the geometry
     * @param expected the expected number of intersections
     * @param msg the message that will be printed if the test fail
     */
    private void integration_Helper(Camera camera, Geometry geo, int expected, String msg){
        int res = 0;

        // 2 loop that run at the i and j index of the view plane
        for (int i = 0; i < camera.getWidth(); i++) {
            for (int j = 0; j < camera.getHeight(); j++) {
            //calc the ray from Pij
                Ray ray = camera.constructRay((int)camera.getWidth(), (int)camera.getHeight(), j,i);
                // calc the intersections of the plane and the ray
                List<Point> intersect = geo.findIntersections(ray);
                if (intersect != null)
                    res += intersect.size();
            }
        }
        assertEquals(expected, res, msg);
    }
    /**
     * Integration test for camera and sphere
     */
    @Test
    public void sphere_camera_intersections() {
        Camera camera1 = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,-1,0))
                .setVPSize(3,3).
                setVPDistance(1);
        Camera camera2 = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,-1,0))
                .setVPSize(3,3).
                setVPDistance(1);

        //TC01: A small sphere with 2 intersections from the middle of the view plane
        Sphere sphere1 = new Sphere(new Point(0,0,-3), 1);
        integration_Helper(camera1, sphere1,2,"2 intersections expected from the middle of the view plane");

        //TC02: A big sphere with 18 intersections twice from each pixel of the view plane
        Sphere sphere2 = new Sphere(new Point(0,0,-2.5), 2.5);
        integration_Helper(camera2, sphere2,18,"18 intersections expected each ray intersect twice");

        // TC03: Medium Sphere 10 points
        Sphere sphere3 = new Sphere(new Point(0,0,-2), 2);
        integration_Helper(camera2, sphere3, 10, "10 intersections expected");

        // TC04: Inside Sphere 9 points
        Sphere sphere4 = new Sphere(new Point(0,0,-1), 4);
        integration_Helper(camera2, sphere4, 9, "9 intersections expected");

        // TC05: Beyond Sphere 0 points
        Sphere sphere5 = new Sphere(new Point(0,0,1), 0.5);
        integration_Helper(camera1, sphere5, 0, "0 intersections expected");
    }
    /*
    * Integration test for camera and plane
     */
     @Test
      public void plane_camera_intersections() {
         Camera camera1 = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,-1,0))
            .setVPSize(3,3).setVPDistance(1);
         // TC01: Plane against camera 9 points
         Plane Plane1 = new Plane(new Point(0, 0, -5), new Vector(0, 0, 1));
         integration_Helper(camera1, Plane1,9,"9 intersections expected");

         // TC02: Plane with small angle 9 points
         Plane Plane2 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 2));
         integration_Helper(camera1, Plane2,9,"9 intersections expected");

         // TC03: Plane parallel to lower rays 6 points
         Plane Plane3 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 1));
         integration_Helper(camera1, Plane3, 6, "6 intersections expected");

         // TC04: Beyond Plane 0 points
         Plane Plane4 = new Plane(new Point(0, 0, -5), new Vector(0, -1, 0));
         integration_Helper(camera1, Plane4, 0, "0 intersections expected");
     }
     /*
        * Integration test for camera and triangle
      */
     @Test
    public void triangle_camera_intersections(){
        Camera camera = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,-1,0))
                .setVPSize(3,3).
                setVPDistance(1);
        //TC01: A small triangle with 1 intersection only at the middle of the view plane
        Triangle triangle1 = new Triangle(
                new Point(0,1,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2));
        integration_Helper(camera, triangle1, 1, "1 intersections expected at the middle of the view plane");

        //TC01: A small triangle with 2 intersections with the middle cell and the one above
        Triangle triangle = new Triangle(
                new Point(0,20,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2));
        integration_Helper(camera, triangle, 2, "2 intersections expected at the middle cell and above ");
    }
}
