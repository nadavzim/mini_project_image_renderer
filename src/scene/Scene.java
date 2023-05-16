package scene;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import geometries.Geometries;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Double3;
import primitives.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static Parser.Utils.makePointFromString;
import static Parser.Utils.mapStringToDouble;
import static org.junit.jupiter.api.Assertions.fail;


public class Scene {

    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    /**
     *
     * @param builder The builder object.
     */
    public Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }

    /**
     * inner class SceneBuilder builds Scene object using builder pattern
     */
    public static class SceneBuilder {

        public  String name;
        public Color background = Color.BLACK;
        public AmbientLight ambientLight = new AmbientLight();
        public Geometries geometries = new Geometries();

        /**
         * This is the builder
         *
         * @param name The name of the scene
         */
        public SceneBuilder(String name) {
            this.name = name;
        }

        // chaining method

        /**
         * This function sets the background color of the scene and returns the scene builder.
         *
         * @param background The background color of the scene.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * > Sets the ambient light of the scene
         *
         * @param ambientLight The ambient light of the scene.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * This function sets the geometries of the scene
         *
         * @param geometries The geometries of the scene.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * This function returns the scene that we built.
         *
         * @return A new instance of the Scene class.
         */
        public Scene build() {
            return new Scene(this);
        }

        /**
         * This function builds a scene from an xml file.
         *
         * @param file The xml file.
         * @return The SceneBuilder object.
         */
        public SceneBuilder XmlScene (File file){
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            Document doc = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(file);
            } catch (ParserConfigurationException e) {
                fail("ParserConfigurationException");
            } catch (SAXException e) {
                fail("SAXException");
            } catch (IOException e) {
                fail("IOException");
            }
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            String[] backgroundColorString = root.getAttribute("background-color").split(" ");
            double[] backgroundColorDouble = mapStringToDouble(backgroundColorString);
            Color backgroundColor = new Color(backgroundColorDouble[0], backgroundColorDouble[1], backgroundColorDouble[2]);
            this.setBackground(backgroundColor);

            Element ambientLightElement = (Element) root.getElementsByTagName("ambient-light").item(0);
            String[] ambientLightString = ambientLightElement.getAttribute("color").split(" ");
            double[] ambientLightDouble = mapStringToDouble(ambientLightString);
            Color ambientLightColor = new Color(ambientLightDouble[0], ambientLightDouble[1], ambientLightDouble[2]);
            AmbientLight ambientLight = new AmbientLight(ambientLightColor, new Double3(1, 1, 1));
            this.setAmbientLight(ambientLight);

            Geometries geometries = new Geometries();
            Node geometriesNode = root.getElementsByTagName("geometries").item(0);
            NodeList geometriesNodes = geometriesNode.getChildNodes();
            for (int i = 0; i < geometriesNodes.getLength(); i++) {
                Node geometryNode = geometriesNodes.item(i);
                if (geometryNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element geometryElement = (Element) geometryNode;
                switch (geometryElement.getNodeName()) {
                    case "sphere" -> {
                        String centerString = geometryElement.getAttribute("center");
                        Point center = makePointFromString(centerString);
                        Double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
                        Sphere sphere = new Sphere(center, radius);
                        geometries.add(sphere);
                    }
                    case "triangle" -> {
                        String p0String = geometryElement.getAttribute("p0");
                        Point p0 = makePointFromString(p0String);
                        String p1String = geometryElement.getAttribute("p1");
                        Point p1 = makePointFromString(p1String);
                        String p2String = geometryElement.getAttribute("p2");
                        Point p2 = makePointFromString(p2String);
                        Triangle triangle = new Triangle(p0, p1, p2);
                        geometries.add(triangle);
                    }
                    default -> {
                    }
                }
            }
            this.setGeometries(geometries);
            return this;
        }
    }
}