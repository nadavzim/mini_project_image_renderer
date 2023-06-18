package lighting;

import primitives.Color;

public abstract class Light {

    /**
     * The color of the light.
     */
    private final Color intensity;


    /**
     * Constructs a Light object with the given color.
     *
     * @param color the color of the light
     */
    protected Light(Color color) {
        this.intensity = color;
    }

    /**
     * Returns the color of the light.
     *
     * @return the color of the light
     */
    public Color getIntensity() {
        return intensity;
    }

}
