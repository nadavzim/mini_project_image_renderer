package lighting;

import primitives.*;


public class AmbientLight {

    private final Color intensity;

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * constructor
     * @param color the color of the light
     * @param kA the intensity of the light
     */
    public AmbientLight(Color color, Double3 kA) {
        intensity = color.scale(kA);
    }
    /**
     * constructor
     * @param color the color of the light
     * @param kA the intensity of the light
     */
    public AmbientLight(Color color, double kA) {
        intensity = color.scale(kA);
    }
    /**
     * constructor
     * @param color the color of the light
     */
    public AmbientLight() {
        intensity = Color.BLACK;
    }

    /**
     * getter
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
