package geometries;

public abstract class RadialGeometry implements Geometry{
    protected double radius;

    public RadialGeometry(float radius) {
        this.radius = radius;
    }
}
