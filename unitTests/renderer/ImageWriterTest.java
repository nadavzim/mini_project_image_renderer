package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

class ImageWriterTest {
    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        int nX = 800;
        int nY = 500;

        Color yellow = new Color(255d, 255d, 0d);
        Color red = new Color(255d, 0d, 0d);

        ImageWriter imageWriter = new ImageWriter("yellow submarine", nX, nY);
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(i, j, red);
                else
                    imageWriter.writePixel(i, j, yellow);
            }
        }
        imageWriter.writeToImage();
    }
}