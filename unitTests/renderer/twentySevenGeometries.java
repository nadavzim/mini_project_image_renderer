package renderer;


import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.Random;

import static java.awt.Color.*;


public class twentySevenGeometries {
    Random rand = new Random();
    private Scene scene1 = new Scene.SceneBuilder("Test scene").build();

    @Test
    // A test for the scene of the spheres.
    public void sphereSpot() {
        int resolution = 3000;
        Point center = new Point(-60,0,0);
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                Sphere sphere = (Sphere) new Sphere(center.add(new Vector(30*(j+0.001),0,-40*(i+0.001))),10d)
                        .setMaterial(new Material().setKs(0.5).setKd(0.5));
                sphere.setEmission(new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));
                scene1.geometries.add(sphere);

            }
            scene1.geometries.add(
                    new Sphere(new Point(-40,55,-150), 20)
                            .setEmission(new Color(136,139,141))
                            .setMaterial(new Material().setKs(0.5).setKd(0.5).setKr(1))
            );
        }
        scene1.geometries.add(new Polygon(
                new Point(-70,-10,40),
                new Point(70,-10,40),
                new Point(70,-10,-160),
                new Point(-70,-10,-160))
                .setEmission(new Color(BLACK))
                .setMaterial(new Material().setKs(0.2).setKd(0.5).setKr(0.7))

        );
        scene1.getLights().add(new SpotLight(new Color(WHITE), new Point(-100,200,-800), new Vector(0.1,-0.2,1)));
        Camera camera1, camera2, camera3;
        camera1 = camera2 = camera3 = new Camera(new Point(0, 500, 950), new Vector(0, -0.432, -0.901), new Vector(0, 0.901, -0.432)) //
                .setVPSize(150, 150).setVPDistance(1000);

        ImageWriter imageWriter1 = new ImageWriter("balls picture no moves", resolution, resolution);
        camera1.setImageWriter(imageWriter1) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage(); //
        camera1.writeToImage();//

        ImageWriter imageWriter2 = new ImageWriter("move right 450 and spin left 0.4", resolution, resolution);
        camera2.moveCamera(new Vector(450, 0, 0)). spinRightLeft(0.4);
        camera2.setImageWriter(imageWriter2) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage(); //
        camera2.writeToImage();//

        ImageWriter imageWriter3 = new ImageWriter("move right 450, towards 20,down 30 and spin left 0.35 and down 1", resolution, resolution);
        camera3.moveCamera(new Vector(500, 20, -30)). spinRightLeft(0.35).spinUpDown(-1).spin(10);
        camera3.setImageWriter(imageWriter3) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage(); //
        camera3.writeToImage();//
    }
}