package pl.derleta.nebula.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Set;

/**
 * Utility class for image processing tasks such as cropping, scaling, converting formats,
 * and validating file extensions. This class provides methods for manipulating images to
 * achieve specific output formats and dimensions.
 * <p>
 * The class is designed to work with image data and files, performing operations like
 * cropping an image to a 1x1 aspect ratio, scaling images based on predefined thresholds,
 * and converting them into JPG format. Additionally, it includes file extension validation
 * and methods for saving processed image data as files.
 * <p>
 * This class cannot be instantiated as it is a utility class with static methods only.
 */
public final class ImageUtil {

    /**
     * A set of valid image file extensions used to determine whether a given file
     * is recognized as an acceptable image format. This set includes the extensions
     * "jpg", "jpeg", and "png", which are considered valid for processing within
     * the application.
     */
    private static final Set<String> VALID_IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    /**
     * Processes the provided image by cropping it to a 1x1 aspect ratio, scaling it if necessary,
     * and converting it to a JPG format. The resulting image is returned as a byte array.
     *
     * @param image the MultipartFile containing the image to be processed; must not be null
     * @return a byte array containing the processed image data in JPG format
     * @throws IOException if an error occurs during reading or writing the image
     */
    public static byte[] cropAndConvertToJpg(MultipartFile image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getBytes()));
        BufferedImage croppedImage = cropImageTo1x1(bufferedImage);
        BufferedImage scaledImage = scaleImage(croppedImage);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, "jpg", outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Crops the given BufferedImage to a 1x1 square by extracting a centered square region
     * with equal width and height based on the shorter side of the image.
     *
     * @param bufferedImage the BufferedImage to crop; must not be null
     * @return a new BufferedImage representing the cropped 1x1 square region
     */
    private static BufferedImage cropImageTo1x1(BufferedImage bufferedImage) {
        int squareSize = getShorterSide(bufferedImage);
        Point cropStart = getCropStartPoint(bufferedImage, squareSize);
        return bufferedImage.getSubimage(cropStart.x, cropStart.y, squareSize, squareSize);
    }

    /**
     * Determines the shorter side of a given BufferedImage.
     *
     * @param bufferedImage the BufferedImage instance whose dimensions are to be evaluated
     * @return the length of the shorter side (width or height) of the provided BufferedImage
     */
    private static int getShorterSide(BufferedImage bufferedImage) {
        return Math.min(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    /**
     * Calculates the starting point (top-left corner) to crop a square section
     * from the center of the given BufferedImage based on the specified square size.
     *
     * @param bufferedImage the BufferedImage from which the square crop will be calculated
     * @param squareSize    the size of the square section to be cropped
     * @return a Point representing the coordinates of the top-left corner of the square crop
     */
    private static Point getCropStartPoint(BufferedImage bufferedImage, int squareSize) {
        int x = (bufferedImage.getWidth() - squareSize) / 2;
        int y = (bufferedImage.getHeight() - squareSize) / 2;
        return new Point(x, y);
    }

    /**
     * Resizes the given BufferedImage to scaled dimensions based on predefined thresholds.
     * If the original image dimensions do not require scaling, the original image is returned.
     *
     * @param bufferedImage the original BufferedImage to be scaled
     * @return a new BufferedImage object with the scaled dimensions, or the original image
     * if no scaling is applied
     */
    private static BufferedImage scaleImage(BufferedImage bufferedImage) {
        Point targetDimensions = getTargetDimensions(bufferedImage.getWidth(), bufferedImage.getHeight());
        if (targetDimensions == null) {
            return bufferedImage;
        }

        Image scaledImage = bufferedImage.getScaledInstance(targetDimensions.x, targetDimensions.y, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetDimensions.x, targetDimensions.y, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }

    /**
     * Calculates the target dimensions for resizing an image based on its original dimensions.
     * The resizing is constrained by maximum width and height thresholds.
     *
     * @param originalWidth  the original width of the image
     * @param originalHeight the original height of the image
     * @return a Point representing the target dimensions (width, height) for the image resize,
     * or null if the original dimensions do not exceed the minimum thresholds
     */
    private static Point getTargetDimensions(int originalWidth, int originalHeight) {
        if (originalWidth > 1024 || originalHeight > 1024) {
            return new Point(Math.min(originalWidth, 1024), Math.min(originalHeight, 1024));
        } else if (originalWidth > 512 || originalHeight > 512) {
            return new Point(Math.min(originalWidth, 512), Math.min(originalHeight, 512));
        }
        return null;
    }

    /**
     * Validates if the given image file has an extension that is considered valid.
     *
     * @param image the MultipartFile object representing the image file to be validated
     * @return true if the image file has a valid extension, false otherwise
     */
    public static boolean isValidImageFile(MultipartFile image) {
        String fileExtension = getFileExtension(image);
        return VALID_IMAGE_EXTENSIONS.contains(fileExtension);
    }

    /**
     * Retrieves the file extension of a given MultipartFile object.
     *
     * @param image the MultipartFile object whose file extension is to be extracted
     * @return the file extension in lowercase if present, or an empty string if no extension is found
     */
    public static String getFileExtension(MultipartFile image) {
        String fileName = image.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) return "";

        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) return "";

        return fileName.substring(lastIndex + 1).toLowerCase();
    }

    /**
     * Saves the given image data as a JPG file at the specified output path.
     * The file is made readable by all users after it is saved.
     *
     * @param imageData  The byte array containing the image data to be saved as a JPG.
     * @param outputPath The path where the resulting JPG file will be stored.
     * @throws IOException If an error occurs during the file writing process.
     */
    public static void saveAsJpg(byte[] imageData, String outputPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(imageData);
            new File(outputPath).setReadable(true, false);
        } catch (IOException e) {
            throw new IOException("An error occurred while saving the image as JPG", e);
        }
    }

}
