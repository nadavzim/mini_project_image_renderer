package lighting;

import primitives.Color;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     * create a directional light source with a color
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

}
