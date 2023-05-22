package cosc202.andie;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * <p>
 * MatrixFilter is an ImageOperation that applies a Matrix-like filter to an
 * image.
 * The Matrix filter is a green-on-black filter that uses a random selection of
 * characters
 * to represent the image.
 * </p>
 * 
 * @author Orion Soti
 * @version 1.0
 */
public class MatrixFilter implements ImageOperation, java.io.Serializable {

    // Constants for character mapping and colors
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$*+-=[]{}|]<>?アイウエオカキクケコ"
            + "αβγδεζηθικλμνξοπρςστυφχψωΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ" // Greek characters
            + "אבגדהוזחטיךכלםמןנסעףפץצקרשת" // Hebrew characters
            + "!@#%^&*()" // More special characters
            + "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをん" // Japanese characters
            + "0123456789" // Duplicated numbers to increase their frequency
            + "\u2620\u2622\u2623\u2625\u2626\u2627\u2628\u2629\u262A\u262B\u262C"; // Special Unicode symbols

    private static final int NUM_SHADES = 16;
    private static final char[] CHAR_MAP = new char[NUM_SHADES];
    // private static final Color MATRIX_GREEN = new Color(0, 255, 65);

    // Initialize the character map with random characters
    static {
        Random random = new Random();
        for (int i = 0; i < NUM_SHADES; ++i) {
            CHAR_MAP[i] = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
    }

    /**
     * <p>
     * Applies the Matrix filter to an image.
     * </p>
     * 
     */
    public BufferedImage apply(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Use ConvertToGrey class to convert the input image to grayscale
        ConvertToGrey convertToGrey = new ConvertToGrey();
        BufferedImage grayImage = convertToGrey.apply(inputImage);

        // Apply Sobel filter (horizontal)
        SobelFilter sobelHorizontal = new SobelFilter(SobelFilter.HORIZONTAL);
        BufferedImage sobelHorizontalImage = sobelHorizontal.apply(grayImage);
        // Apply Sobel filter (vertical)
        SobelFilter sobelVertical = new SobelFilter(SobelFilter.VERTICAL);
        BufferedImage sobelVerticalImage = sobelVertical.apply(grayImage);

        // Blend Sobel filter results with the original grayscale image
        BufferedImage blendedSobel = blend(sobelHorizontalImage, sobelVerticalImage);
        BufferedImage blendedImage = blend(grayImage, blendedSobel);

        // Apply the Matrix filter
        Graphics2D g = outputImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        Random random = new Random();

        Font plainFont = new Font(Font.MONOSPACED, Font.PLAIN, 2);// ADJUST THIS NUMBER TO CHANGE FONT SIZE
        Font boldFont = new Font(Font.MONOSPACED, Font.BOLD, 5);
        FontMetrics fm = g.getFontMetrics(plainFont);

        int charWidth = fm.charWidth('A') * 2;
        int charHeight = fm.getAscent() - fm.getDescent();
        for (int y = 0; y < height; y += Math.round(charHeight * 2)) { // ADJUST THIS NUMBER TO CHANGE DENSITY OF
                                                                       // CHARACTERS PER PIXEL
            // boolean skipNext = false;//
            for (int x = 0; x < width; x += Math.round(charWidth * 2)) {
                // if (skipNext) {
                // skipNext = false;
                // continue;
                // }
                int pixel = blendedImage.getRGB(x, y);
                int intensity = (pixel & 0xFF) / (256 / NUM_SHADES);
                char symbol = CHAR_MAP[random.nextInt(CHAR_MAP.length)];
                int shadeOfGreen = intensity * 15 + 50; // Will range from 50 to 305
                shadeOfGreen = Math.min(shadeOfGreen, 255); // Cap it at 255
                g.setColor(new Color(0, shadeOfGreen, 0));

                if (intensity > NUM_SHADES / 2) {
                    g.setFont(boldFont);
                } else {
                    g.setFont(plainFont);
                }

                g.drawString(Character.toString(symbol), x, y + fm.getAscent());

                // 10% chance to skip the next character
                // ADJUST THIS NUMBER TO CHANGE FREQUENCY OF SKIPS
                // if (random.nextDouble() < 0.1) {
                // skipNext = true;
                // }
            }
        }
        return outputImage;
    }

    /**
     * Blend two images together.
     * 
     * @param img1        The first image
     * @param img2        The second image
     * @param blendFactor The blend factor (0.0 - 1.0)
     * @return The blended image
     */
    private BufferedImage blend(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        BufferedImage blendedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int pixel1 = img1.getRGB(x, y);
                int pixel2 = img2.getRGB(x, y);

                int red1 = (pixel1 & 0x00FF0000) >> 16;
                int green1 = (pixel1 & 0x0000FF00) >> 8;
                int blue1 = (pixel1 & 0x000000FF);

                int red2 = (pixel2 & 0x00FF0000) >> 16;
                int green2 = (pixel2 & 0x0000FF00) >> 8;
                int blue2 = (pixel2 & 0x000000FF);

                int blendedRed = Math.max(red1, red2);
                int blendedGreen = Math.max(green1, green2);
                int blendedBlue = Math.max(blue1, blue2);

                int blendedPixel = (blendedRed << 16) | (blendedGreen << 8) | blendedBlue;
                blendedImage.setRGB(x, y, blendedPixel);
            }
        }

        return blendedImage;
    }

}
