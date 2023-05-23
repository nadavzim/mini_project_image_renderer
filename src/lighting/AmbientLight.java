package lighting;

import primitives.*;


public class AmbientLight extends Light{


    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * constructor
     * @param color the color of the light
     * @param kA the intensity of the light
     */
    public AmbientLight(Color color, Double3 kA) {
        super(color.scale(kA));
    }
    /**
     * constructor
     * @param color the color of the light
     * @param kA the intensity of the light
     */
    public AmbientLight(Color color, double kA) {
        super(color.scale(kA));
    }
    /**
     * constructor
     * @param color the color of the light
     */
    public AmbientLight() {

        super(Color.BLACK);
    }

    /**
     * getter
     * @return the intensity of the light
     */
}
