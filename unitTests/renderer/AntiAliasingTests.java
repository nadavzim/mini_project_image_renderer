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


        int resolution = 1000, sampleperpixle = 17 ;
        Camera camera = new Camera(new Point(0,-200,1000), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(400, 400).setVPDistance(100);
        camera.setNumberOfRaysInPixel(sampleperpixle); // Turn on/off the test by changing the amount



        Camera camera3 = new Camera(new Point(0,-200,1000), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setVPSize(400, 400).setVPDistance(100);
        camera3.setNumberOfRaysInPixel(2*sampleperpixle); // Turn on/off the test by changing the amount

        Color LightYellow = new Color(254,254,153);

        // spotlight in the middle of the ceiling direct to the floor
        scene.getLights().add(new SpotLight(LightYellow, new Point(0, 200, 1680), new Vector(0, 0, -1)).setKc(1.4).setRadius(30d));
        Box cielinglight = new Box(500,30,250,new Point(-250,200,1580),new Color(YELLOW));


        //bed spotlight's
         scene.getLights().add(new SpotLight(LightYellow, new Point(200, 800, 400), new Vector(0, 0, -1)).setKc(1.25).setRadius(30d)); // right bed light
        Sphere spotBedRight = new Sphere(new Point(200,800,400),40);
        scene.getLights().add(new SpotLight(LightYellow, new Point(-300, 800, 400), new Vector(0, 0, -1)).setKc(1.25).setRadius(30d)); // left bed light
        Sphere spotBedLeft = new Sphere(new Point(-300,800,400),40);

     // pointLight in the left wall (above the closet) direct to the other wall
        scene.getLights().add(new PointLight(LightYellow, new Point(-770, 400, 1200)).setKc(1.5).setRadius(30d));
        Sphere pointSphere = new Sphere(new Point(-800,400,1200),40);

        // ball light
        scene.getLights().add(new DirectionalLight(new Color(WHITE), new Vector(-0.75,0,-1)));


        int size = 800;
        Point A = new Point(size,0,2*size), B = new Point(-size, 0,2*size), C = new Point(size,0,0),
                D = new Point(-size, 0,0), E = new Point(size,size,2*size), F = new Point(size,size,-150),
        G = new Point(-size,size,2*size), H = new Point(-size,size,-150), M1 = new Point(size,size-500,500),
        M2 = new Point(size,size-200,500), M3 = new Point(size,size-500,2*size-400),
        M4 = new Point(size,size-200,2*size-400);


        Material walls = new Material().setKd(0.4).setShininess(300).setKs(0.2);
        Material cube = new Material().setKd(0.6).setShininess(300).setKs(0.5);
        Material mirror = new Material().setKr(1);
        Material ball = new Material().setKd(0.5).setKs(0.5).setShininess(300);
        Material lightBalls = new Material().setKt(0.9).setKd(0.9).setShininess(300);

        // clock spheres
        Sphere s12 =  new Sphere(new Point(-50,750,1300),40);s12.setEmission(new Color(200,0,0)).setMaterial(ball);
        Sphere s3 =  new Sphere(new Point(200,750,1050),40); s3.setEmission(new Color(200,0,0)).setMaterial(ball);
        Sphere s6 =  new Sphere(new Point(-50,750,800),40);s6.setEmission(new Color(200,0,0)).setMaterial(ball);
        Sphere s9 =  new Sphere(new Point(-300,750,1050),40);s9.setEmission(new Color(200,0,0)).setMaterial(ball);


        scene.geometries.add( // walls of the room
                new Polygon( M1,M2,M4,M3).setEmission(new Color(51,204,255))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3).setKr(0.3)), // window
                new Polygon(A,M3,M1,C).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon(A,M3,M4,E).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon(E,M4,M2,F).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon(F,M2,M1,C).setMaterial(walls).setEmission(new Color(GRAY)), // right wall
                new Polygon( B,D,H,G).setMaterial(walls).setEmission(new Color(GRAY)),// left wall
                new Polygon(E,F,H,G).setMaterial(walls).setEmission(new Color(GRAY)),// back wall
                new Polygon(A,B,G,E).setMaterial(walls).setEmission(new Color(LIGHT_GRAY)), // ceiling
                new Polygon(C,D,H,F).setMaterial(new Material().setKd(0.1)).setEmission(new Color(GRAY.darker())),// floor
                pointSphere.setMaterial(lightBalls).setEmission(new Color(YELLOW)), // right wall light sphere
                spotBedRight.setMaterial(lightBalls).setEmission(new Color(YELLOW)), // bed light sphere
                spotBedLeft.setMaterial(lightBalls).setEmission(new Color(YELLOW)), // bed light sphere

                s12, s3, s6, s9); // clock balls

        Box bedSideStandLeft = new Box(300,300,100,new Point(-800,650,-101),new Color( 153,102,0)),
        bedSideStandRight = new Box(400,300,100,new Point(450,650,-101), new Color( 153,102,0)),

        bed = new Box(900,150,500,new Point(-490,490,-15), new Color( 255,102,102)),
        rightPillow = new Box(350,60,150,new Point(0,700,150), new Color( 255,50,50)),
        leftPillow = new Box(350,60,150,new Point(-400,700,150), new Color( 255,50,50));


        Box leftMirror = new Box(300,600,10,new Point(480,752,250), new Color(BLACK));
        Box rightMirror = new Box(300,600,10,new Point(-800,750,250), new Color(BLACK));

        Box closet = new Box(150,1050,200,new Point(-770,300,0), new Color(BLACK));

        Box mirorCloset = new Box(10,1050,100,new Point(-610,320,0),new Color(BLACK));

        Point watchhands = new Point(-60,730,1030);
        Box longhand = new Box(20, 150, 20, watchhands,new Color(BLACK));
        Box shorthand = new Box(80, 20, 20, watchhands,new Color(BLACK));



        for (Polygon rect: bedSideStandRight.getGeo()) {scene.geometries.add(rect.setMaterial(cube));}
        for (Polygon rect: bedSideStandLeft.getGeo()) {scene.geometries.add(rect.setMaterial(cube));}
        for (Polygon rect: bed.getGeo()) {scene.geometries.add(rect.setMaterial(cube));}
        for (Polygon rect: rightPillow.getGeo()) {scene.geometries.add(rect.setMaterial(cube));}
        for (Polygon rect: leftPillow.getGeo()) {scene.geometries.add(rect.setMaterial(cube));}

        for (Polygon rect: rightMirror.getGeo()) {scene.geometries.add(rect.setMaterial(mirror));}
        for (Polygon rect: leftMirror.getGeo()) {scene.geometries.add(rect.setMaterial(mirror));}
        for (Polygon rect: closet.getGeo()) {scene.geometries.add(rect.setMaterial(cube));}
        for (Polygon rect: mirorCloset.getGeo()) {scene.geometries.add(rect.setMaterial(mirror));}
        for (Polygon rect: longhand.getGeo()) {scene.geometries.add(rect.setMaterial(new Material().setShininess(500).setKd(0.6).setKs(0.4)));}
        for (Polygon rect: shorthand.getGeo()) {scene.geometries.add(rect.setMaterial(new Material().setShininess(500).setKd(0.6).setKs(0.4)));}

        for (Polygon rect: cielinglight.getGeo()) {scene.geometries.add(rect.setMaterial(new Material().setKt(0.9).setKd(0.9)));}


        /* critic!*/
        scene.getGeometries().BuildBvhTree(); // build the auto bvh - critical for the run-time improvement


        ImageWriter imageWriter = new ImageWriter("final scene soft+aa", resolution, resolution);
                camera.setImageWriter(imageWriter)//.moveCamera(new Vector(-140, 80, 35)) //
                .setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene).setSoftShadow(false)).setMultithreading(3);

//        camera.moveCamera(new Vector(150,50,0)).spinRightLeft(0.4).spin(0.7); // right angle photo
//        camera.moveCamera(new Vector(-150,50,-200)).spinRightLeft(-0.45).spin(-1.6).spinUpDown(1);// left down photo

        camera.renderImage();
        camera.writeToImage();
    }

}
