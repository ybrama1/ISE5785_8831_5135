package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link renderer.ImageWriter}.
 *
 * @author Jeshurun and Binyamin
 */
public class ImageWriterTest {
    /**
     * Test method for
     * {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    //write a grid of 16X10 cells, 800X500 pixels
    void writeToImage() {
        // ============ Equivalence Partitions Tests ==============
        ImageWriter imageWriter = new ImageWriter(800, 500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(255, 0, 255));
                } else {
                    imageWriter.writePixel(i, j, new Color(0, 255, 0));
                }
            }
        }

        // TC01: Test for a correct writing of the image
        // the result should be true
        imageWriter.writeToImage("test");
    }
}
