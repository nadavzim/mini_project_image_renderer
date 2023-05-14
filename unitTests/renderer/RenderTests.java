package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.io.File;

import static java.awt.Color.YELLOW;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene(new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191),
                        new Double3(1, 1, 1))) //
                .setBackground(new Color(75, 127, 90)));

        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        // right
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.printGrid(100, new Color(YELLOW));
        camera.writeToImage();
    }


//   // For stage 6 - please disregard in stage 5
//   /* Produce a scene with basic 3D model - including individual lights of the
//    * bodies and render it into a png image with a grid */
//   // @Test
//   // public void basicRenderMultiColorTest() {
//   // Scene scene = new Scene("Test scene")//
//   // .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))); //
//   //
//   // scene.geometries.add( //
//   // new Sphere(new Point(0, 0, -100), 50),
//   // // up left
//   // new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new
//   // Point(-100, 100, -100))
//   // .setEmission(new Color(GREEN)),
//   // // down left
//   // new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new
//   // Point(-100, -100, -100))
//   // .setEmission(new Color(RED)),
//   // // down right
//   // new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
//   // Point(100, -100, -100))
//   // .setEmission(new Color(BLUE)));
//   //
//   // Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1,
//   // 0)) //
//   // .setVPDistance(100) //
//   // .setVPSize(500, 500) //
//   // .setImageWriter(new ImageWriter("color render test", 1000, 1000))
//   // .setRayTracer(new RayTracerBasic(scene));
//   //
//   // camera.renderImage();
//   // camera.printGrid(100, new Color(WHITE));
//   // camera.writeToImage();
//   // }
//

    /*Test for XML based scene - for bonus */
//    @Test
//    public void basicRenderXml() {
//        Scene.SceneBuilder scene = new Scene.SceneBuilder("XML Test scene");
//        File inputFile = new File("basicRenderTestTwoColors.xml");
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder;
//        Document doc = null;
//        try {
//            dBuilder = dbFactory.newDocumentBuilder();
//            doc = dBuilder.parse(inputFile);
//        } catch (ParserConfigurationException e) {
//            fail("ParserConfigurationException");
//        } catch (SAXException e) {
//            fail("SAXException");
//        } catch (IOException e) {
//            fail("IOException");
//        }
//        doc.getDocumentElement().normalize();
//
//        Element root = doc.getDocumentElement();
//
//        String[] backgroundColorString = root.getAttribute("background-color").split(" ");
//        double[] backgroundColorDouble = mapStringToDouble(backgroundColorString);
//        Color backgroundColor = new Color(backgroundColorDouble[0], backgroundColorDouble[1], backgroundColorDouble[2]);
//        scene.setBackground(backgroundColor);
//
//        Element ambientLightElement = (Element) root.getElementsByTagName("ambient-light").item(0);
//        String[] ambientLightString = ambientLightElement.getAttribute("color").split(" ");
//        double[] ambientLightDouble = mapStringToDouble(ambientLightString);
//        Color ambientLightColor = new Color(ambientLightDouble[0], ambientLightDouble[1], ambientLightDouble[2]);
//        AmbientLight ambientLight = new AmbientLight(ambientLightColor, new Double3(1, 1, 1));
//        scene.setAmbientLight(ambientLight);
//
//        Geometries geometries = new Geometries();
//        Node geometriesNode = root.getElementsByTagName("geometries").item(0);
//        NodeList geometriesNodes = geometriesNode.getChildNodes();
//        for (int i = 0; i < geometriesNodes.getLength(); i++) {
//            Node geometryNode = geometriesNodes.item(i);
//            if (geometryNode.getNodeType() != Node.ELEMENT_NODE) {
//                continue;
//            }
//            Element geometryElement = (Element) geometryNode;
//            switch (geometryElement.getNodeName()) {
//                case "sphere":
//                    String centerString = geometryElement.getAttribute("center");
//                    Point center = makePointFromString(centerString);
//                    Double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
//                    Sphere sphere = new Sphere(center, radius);
//                    geometries.add(sphere);
//                    break;
//                case "triangle":
//                    String p0String = geometryElement.getAttribute("p0");
//                    Point p0 = makePointFromString(p0String);
//                    String p1String = geometryElement.getAttribute("p1");
//                    Point p1 = makePointFromString(p1String);
//                    String p2String = geometryElement.getAttribute("p2");
//                    Point p2 = makePointFromString(p2String);
//                    Triangle triangle = new Triangle(p0, p1, p2);
//                    geometries.add(triangle);
//                    break;
//                default:
//                    break;
//            }
//        }
//        scene.setGeometries(geometries);
//
//        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setVPDistance(100) //
//                .setVPSize(500, 500)
//                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
//                .setRayTracer(new RayTracerBasic(scene.build()));
//        camera.renderImage();
//        camera.printGrid(100, new Color(java.awt.Color.YELLOW));
//        camera.writeToImage();
//    }
    @Test
    public void basicRenderXml() {
        File inputFile = new File("basicRenderTestTwoColors.xml");
        Scene.SceneBuilder scene = new Scene.SceneBuilder("XML Test scene").XmlScene(inputFile);
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500)
                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene.build()));
        camera.renderImage();
        camera.printGrid(100, new Color(java.awt.Color.YELLOW));
        camera.writeToImage();
    }
}

