package lighting;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
   private Scene scene1 = new Scene.SceneBuilder("Test scene").build();
   private Scene scene2 = new Scene.SceneBuilder("Test scene")//
           .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();
   private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
           .setVPSize(150, 150) //
           .setVPDistance(1000);
   private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
           .setVPSize(200, 200) //
           .setVPDistance(1000);

   private static final int     SHININESS               = 301;
   private static final double  KD                      = 0.5;
   private static final Double3 KD3                     = new Double3(0.2, 0.6, 0.4);

   private static final double  KS                      = 0.5;
   private static final Double3 KS3                     = new Double3(0.2, 0.4, 0.3);
   private Point[] p = { // The Triangles' vertices:
           new Point(-110, -110, -150), // the shared left-bottom
           new Point(95, 100, -150), // the shared right-top
           new Point(110, -110, -150), // the right-bottom
           new Point(-75, 78, 100) }; // the left-top
   private Point trPL = new Point(30, 10, -100); // Triangles test Position of Light
   private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
   private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
   private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
   private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
   private Material material = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
   private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
   private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
   private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
           .setEmission(new Color(BLUE).reduce(2)) //
           .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

   /**
    * Produce a picture of a sphere lighted by a directional light
    */
   @Test
   public void sphereDirectional() {
      scene1.getGeometries().add(sphere);
      scene1.getLights().add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

      ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage()
              .writeToImage();//
   }

   /**
    * Produce a picture of a sphere lighted by a point light
    */
   @Test
   public void spherePoint() {
      scene1.getGeometries().add(sphere);
      scene1.getLights().add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));

      ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of a sphere lighted by a spotlight
    */
   @Test
   public void sphereSpot() {
      scene1.getGeometries().add(sphere);
      scene1.getLights().add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

      ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of two triangles lighted by a directional light
    */
   @Test
   public void trianglesDirectional() {
      scene2.getGeometries().add(triangle1, triangle2);
      scene2.getLights().add(new DirectionalLight(trCL, trDL));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of two triangles lighted by a point light
    */
   @Test
   public void trianglesPoint() {
      scene2.getGeometries().add(triangle1, triangle2);
      scene2.getLights().add(new PointLight(trCL, trPL).setKl(0.001).setKq(0.0002));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of two triangles lighted by a spotlight
    */
   @Test
   public void trianglesSpot() {
      scene2.getGeometries().add(triangle1, triangle2);
      scene2.getLights().add(new SpotLight(trCL, trPL, trDL).setKl(0.001).setKq(0.0001));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of a sphere lighted by a narrow spotlight
    */
   @Test
   public void sphereSpotSharp() {
      scene1.getGeometries().add(sphere);
      scene1.getLights()
              .add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setNarrowBeam(10).setKl(0.001).setKq(0.00004));

      ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Produce a picture of two triangles lighted by a narrow spotlight
    */
   @Test
   public void trianglesSpotSharp() {
      scene2.getGeometries().add(triangle1, triangle2);
      scene2.getLights().add(new SpotLight(trCL, trPL, trDL).setNarrowBeam(10).setKl(0.001).setKq(0.00004));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

   /**
    * Test Produce a picture of two triangles with multiple light sources.
    */
   @Test
   void trianglesMultiple() {
      scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
              triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
      scene2.getLights().add(new DirectionalLight(new Color(0, 255, 0), trDL));
      scene2.getLights().add(new PointLight(new Color(255, 0, 0), new Point(40, -70, -100))
              .setKl(0.0005).setKq(0.0005));
      scene2.getLights().add(new SpotLight(new Color(0, 0, 255), new Point(12, 0, 0), new Vector(0, -2, -1)) //
              .setKl(0.0001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesMultiple", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2))
              .renderImage() //
              .writeToImage();
   }
   /**
    * Test produce a sphere with multiple light sources.
    */
   @Test
   void SphereMultiple() {
      scene1.geometries.add(sphere);
      scene1.getLights().add(new DirectionalLight(new Color(400, 0, 0), new Vector(-1, 1, -1)));
      scene1.getLights().add(new PointLight(new Color(500, 500, 0), new Point(0, 30, 10))
              .setKl(0.0000003).setKq(0.0000001));
      scene1.getLights().add(new SpotLight(new Color(0, 900, 0), new Point(-100, -70, 50), new Vector(1, -1, -2))
              .setKl(0.0000000001).setKq(0.000000001));

      ImageWriter imageWriter = new ImageWriter("lightSphereMultiple", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage()//
              .writeToImage();
   }
}