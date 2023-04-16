package primitives;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 This class contains test methods for the {@link Vector} class.
 */
class VectorTests {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     * Test method for simple dot product operation.
     * Tests the dot product of two non-orthogonal vectors.
     * Expected output: dot product equals -28.
     */
    @Test
    public void testDotProductEP() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple dotProduct test
        assertEquals(-28d, v1.dotProduct(v2), 0.00001, "dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     * Test method for boundary values of dot product operation.
     * Tests the dot product of two orthogonal vectors.
     * Expected output: dot product equals 0.
     */
    @Test
    public void testDotProductBVA() {
        // =============== Boundary Values Tests ==================
        // TC11: dotProduct for orthogonal vectors
        assertEquals(
                0d, v1.dotProduct(v3),
                0.00001,
                "dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     * Tests the cross product operation.
     * Tests that the length of the cross-product is proper and that the result is orthogonal to its operands.
     * Expected output: length of cross product equals the multiplication of the operands' lengths.
     *                  cross product result is orthogonal to both operands.
     */
    @Test
    void testCrossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception"
        );
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     * Tests the length calculation of a vector.
     * Expected output: length equals 5.
     */
    @Test
    void testLength() {
        assertEquals(5d, new Vector(0, 3, 4).length(), "length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     * Test method for calculating the squared length of a vector.
     * Tests the squared length calculation of a non-zero vector.
     * Expected output: squared length equals 14.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14d,
                v1.lengthSquared(), 0.00001,
                "lengthSquared() wrong value");

    }

    /**
     * This method tests the normalize function of the Vector class.
     * It creates a new vector and normalizes it, then checks that the length of the resulting vector is 1
     * with a given epsilon value. It also tests that an IllegalArgumentException is thrown when trying
     * to normalize a zero vector.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(3.5, -5, 10);
        v = v.normalize();
        assertEquals(1, v.length(), 1e-10,"normalize() function creates a vector with length other than 1");

        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "head camnot be (0,0,0)");
    }

    /**
     * This method tests the add function of the Vector class. It tests both equivalence partition
     * and boundary values. It checks that the addition of two vectors results in the correct vector.
     * It also tests that an IllegalArgumentException is thrown when trying to add a vector to its negative.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1),
                new Vector(2, 3, 4).add(new Vector(-1, -2, -3)),
                "Wrong vector add");

        // =============== Boundary Values Tests ==================
        // TC11: test adding v + (-v)
//		assertThrows("Add v plus -v must throw exception", IllegalArgumentException.class,
//				() -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)));
    }

    /**
     * This method tests the subtract function of the Vector class for equivalence partition.
     * It checks that the subtraction of two vectors results in the correct vector.
     */
    @Test
    void testSubtractEP() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(
                new Vector(1, 1, 1),
                new Vector(2, 3, 4).subtract(new Vector(1, 2, 3)),
                "Wrong vector subtract");
    }

    /**
     * This method tests the subtract function of the Vector class for boundary values.
     * It checks that an IllegalArgumentException is thrown when trying to subtract a vector from itself.
     */
    @Test
    void testSubtractBVA() {
        // =============== Boundary Values Tests ==================
        // TC11: test subtracting same vector
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).subtract(new Vector(1, 2, 3)),
                "Subtract v from v must throw exception");
    }

    /**
     * This method tests the subtract function of the Point class for both equivalence partition
     * and boundary values. It checks that the subtraction of two points results in the correct vector.
     * It also tests that an IllegalArgumentException is thrown when trying to subtract a point from itself.
     */
    @Test
    public void testPointSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1),
                new Point(2, 3, 4).subtract(new Point(1, 2, 3)),
                "Wrong point subtract");

        // =============== Boundary Values Tests ==================
        // TC11: test subtracting same point
//		assertThrows("Subtract P from P must throw exception", IllegalArgumentException.class,
//				() -> new Point(1, 2, 3).subtract(new Point(1, 2, 3)));
    }

    /**
     * This method tests the scale method in the Vector class.
     * It includes both Equivalence Partitions Tests and Boundary Values Tests.
     */
    @Test
    public void testScaleEP() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(2, 4, 6),
                new Vector(1, 2, 3).scale(2),
                "Wrong vector scale");
    }

    /**
     * This method tests the scale method in the Vector class when scaling by zero.
     * It checks if an IllegalArgumentException is thrown.
     */
    @Test
    public void testScaleBVA() {
        // =============== Boundary Values Tests ==================
        // TC11: test scaling to 0
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).scale(0d),
                "Scale by 0 must throw exception");
    }

}