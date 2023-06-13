package renderer;


import geometries.*;
import org.junit.jupiter.api.Test;
import lighting.*;
import scene.Scene;
import primitives.*;
import static java.awt.Color.*;

/**
 * @author nadav, bed-room Test for Anti-aliasing improvment
 */

public class AntiAliasingTests {
    /**
     * Produce a picture of a pyramid lighted using Anti-aliasing improvement
     **/
    private final Scene scene = new Scene( new Scene.SceneBuilder("Test scene"));

    @Test
    public void AA_test() {
        int resolution = 1000, sampleperpixle = 9;
        Camera camera = new Camera(new Point(0,-200,1000), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(400, 400).setVPDistance(100);
        camera.setNumberOfRaysInPixel(sampleperpixle); // Turn on/off the test by changing the amount

        Camera camera2 = new Camera(new Point(0,-200,1000), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(400, 400).setVPDistance(100);
        camera.setNumberOfRaysInPixel(0); // Turn on/off the test by changing the amount

        Camera camera3 = new Camera(new Point(0,-200,1000), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(400, 400).setVPDistance(100);
        camera.setNumberOfRaysInPixel(sampleperpixle); // Turn on/off the test by changing the amount

        Color LightYellow = new Color(254,254,153);

        // spotlight in the middle of the ceiling direct to the floor
        scene.getLights().add(new SpotLight(LightYellow, new Point(0, 400, 1580), new Vector(0, 0, -1)).setKc(1.6).setRadius(50d));
        // pointLight in the left wall (above the closet) direct to the other wall
        scene.getLights().add(new PointLight(LightYellow, new Point(-770, 400, 350+800)).setKc(1.5).setRadius(50d));
        // ball light
//        scene.getLights().add(new PointLight(LightYellow, new Point(-0, 700, 600)).setKc(3));
        scene.getLights().add(new DirectionalLight(new Color(WHITE), new Vector(-0.75,0,-1)));


        int size = 800;
        Point A = new Point(size,0,2*size), B = new Point(-size, 0,2*size), C = new Point(size,0,0),
                D = new Point(-size, 0,0), E = new Point(size,size,2*size), F = new Point(size,size,-150),
        G = new Point(-size,size,2*size), H = new Point(-size,size,-150), M1 = new Point(size,size-500,500),
        M2 = new Point(size,size-200,500), M3 = new Point(size,size-500,2*size-400),
        M4 = new Point(size,size-200,2*size-400);


        Material walls = new Material().setKd(0.4).setShininess(300).setKs(0.2);
        Material cube = new Material().setKd(0.6).setShininess(300).setKs(0.5);
        Material mirror = new Material().setKr(0.8).setKs(0.3);
        Material ball = new Material().setKd(0.5).setKs(0.5).setShininess(300);

        Sphere s1 =  new Sphere(new Point(280,750,size+300),30); s1.setEmission(new Color(BLUE)).setMaterial(ball);
        Sphere s2 =  new Sphere(new Point(140,750,size+400),30);s2.setEmission(new Color(GREEN)).setMaterial(ball);
        Sphere s3 =  new Sphere(new Point(-40,750,size+500),30);s3.setEmission(new Color(ORANGE)).setMaterial(ball);
        Sphere s4 =  new Sphere(new Point(-200,750,size+400),30);s4.setEmission(new Color(PINK)).setMaterial(ball);
        Sphere s5 =  new Sphere(new Point(-340,750,size+300),30);s5.setEmission(new Color(RED)).setMaterial(ball);

        scene.geometries.add( // walls of the room
                new Polygon( M1,M2,M4,M3).setEmission(new Color(51,204,255)).setMaterial(new Material().setKt(0.2).setKd(0.3).setKr(0.3)), // window
                new Polygon(A,M3,M1,C).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon(A,M3,M4,E).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon(E,M4,M2,F).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon(F,M2,M1,C).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon( B,D,H,G).setMaterial(walls).setEmission(new Color(GRAY)),// left wall
                new Polygon(E,F,H,G).setMaterial(walls).setEmission(new Color(GRAY)),// back wall
                new Polygon(A,B,G,E).setMaterial(walls).setEmission(new Color(LIGHT_GRAY)), // ceiling
                new Polygon(C,D,H,F).setMaterial(new Material().setKd(0.1).setKr(0.1)).setEmission(new Color(GRAY.darker())),// floor
                s1, s2, s3, s4, s5); new Sphere(new Point(-40,700,600),30).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)
                        ); // ball

        Box bedSideStandLeft = new Box(300,300,100,new Point(-800,650,0)),
        bedSideStandRight = new Box(400,300,100,new Point(450,650,0)),
        bed = new Box(900,150,500,new Point(-490,400,0));

        Box leftMirror = new Box(300,600,10,new Point(-800,750,50));
        Box rightMirror = new Box(300,600,10,new Point(480,752,48));

        Box closet = new Box(100,1000,200,new Point(-770,300,0));



        for (Polygon rect: bedSideStandRight.getGeo()) {
            scene.geometries.add(rect.setMaterial(cube).setEmission(new Color( 153,102,0)));
        }
        for (Polygon rect: bedSideStandLeft.getGeo()) {
            scene.geometries.add(rect.setMaterial(cube).setEmission(new Color( 153,102,0)));
        }

        for (Polygon rect: bed.getGeo()) {
            scene.geometries.add(rect.setMaterial(cube).setEmission(new Color( 255,102,102)));
        }

        for (Polygon rect: rightMirror.getGeo()) {
            scene.geometries.add(rect.setMaterial(mirror));
        }
        for (Polygon rect: leftMirror.getGeo()) {
            scene.geometries.add(rect.setMaterial(mirror));
        }

        for (Polygon rect: closet.getGeo()) {
            scene.geometries.add(rect.setMaterial(cube));
        }


        ImageWriter imageWriter = new ImageWriter("AA with 33", resolution, resolution);
                camera.setImageWriter(imageWriter)//.moveCamera(new Vector(-140, 80, 35)) //
                .setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene));//turnAllBoxesOn())
//                .setMultithreading(3).setDebugPrint();

        camera.renderImage();
        camera.writeToImage();


        ImageWriter imageWriter2 = new ImageWriter("AA without", resolution, resolution);
        camera2.setImageWriter(imageWriter2)//.moveCamera(new Vector(-140, 80, 35)) //
                .setRayTracer(new RayTracerBasic(scene));//turnAllBoxesOn())
//                .setMultithreading(3).setDebugPrint();

        camera2.renderImage();
        camera2.writeToImage();


        ImageWriter imageWriter3 = new ImageWriter("AA + soft shadows", resolution, resolution);
        camera3.setImageWriter(imageWriter3)//.moveCamera(new Vector(-140, 80, 35)) //
                .setRayTracer(new RayTracerBasic(scene).setSoftShadow(true));//turnAllBoxesOn())
//                .setMultithreading(3).setDebugPrint();

        camera3.renderImage();
        camera3.writeToImage();
    }

}
