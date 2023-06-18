package renderer;

import geometries.Box;
import geometries.Intersectable;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import lighting.*;

import java.util.List;

import static java.awt.Color.*;

public class BoxTest {
    @Test
    public void basicBoxTest() {
        Scene scene = new Scene(new Scene.SceneBuilder("Test scene")
//                .setAmbientLight(new AmbientLight(new Color(255, 191, 191),
//                        new Double3(1, 1, 1))) //
                .setBackground(new Color(0, 0, 0)));
        scene.getLights().add(new DirectionalLight(new Color(GREEN), new Vector(1, 1, -0.5)));

        Box b = new Box(10,30,10,new Point(2, 2, -40), new Color(YELLOW));
        List<Polygon> geoBox = b.getGeo();

//        for (Polygon p : b.getGeo()) {
//            p.setEmission(new Color(java.awt.Color.YELLOW));
//            scene.geometries.add(p);
//        }
        scene.geometries.add(new Polygon(
                        new Point(-70,-10,40),
                        new Point(70,-10,40),
                        new Point(70,-10,-160),
                        new Point(-70,-10,-160))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKs(0.2).setKd(0.5).setKr(0.2)));


        scene.getLights().add(new SpotLight(new Color(WHITE), new Point(-100,200,-800), new Vector(0.1,-0.2,1)));


        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("Box test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));
        camera.moveCamera(new Vector(60,0,0));
        camera.renderImage();
        camera.printGrid(100, new Color(WHITE));
        camera.writeToImage();

    }
}
