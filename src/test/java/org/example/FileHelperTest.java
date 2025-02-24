package org.example;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileHelperTest {

    @BeforeClass
    public static void setUpDir() {
        File dir = new File("temp_files");
        if (!dir.exists()) {
            dir.mkdirs(); // Create output directory if it doesn't exist
            System.out.println("temp_dir created before tests");
        }
    }

    @Test
    public void parseTiffToJpeg() throws IOException {
        byte[] tiffBytes = Files.readAllBytes(Paths.get("src/test/resources/multipage_tiff_example.tif"));
        // Convert to JPEG
        FileHelper.parseTiffToJpeg(tiffBytes, "temp_files");
    }

    @Test
    public void scaleTest() throws IOException {
        FileInputStream is = new FileInputStream("src/test/resources/image.png");
        byte[] img = new byte[is.available()];
        is.read(img);

        byte[] scaled = FileHelper.scalePhoto(img, 100, 100);
        String res = "temp_files/scaled_0.png";
        FileOutputStream fos = new FileOutputStream(res);
        fos.write(scaled);
        fos.close();
        File result = new File(res);
        assertNotNull(result);
    }

    @Test
    public void zipFileTest() throws IOException {
        String[] images = {
                "src/test/resources/i1.png",
                "src/test/resources/i2.png"
        };
        String outputZip = "temp_files/output.zip";
        FileHelper.zipFiles(images, outputZip);

        File result = new File(outputZip);
        assertTrue("ZIP file should be created", result.exists());
        assertTrue("ZIP file should not be empty", result.length() > 0);

        //Files.deleteIfExists(Paths.get(outputZip));
    }

    @Test
    public void testUnzipFiles() throws IOException {
        String zipFile = "temp_files/output.zip";
        String outputDir = "zip_files";

        //  Unzipping the file
        FileHelper.unzipFiles(zipFile, outputDir);

        // Check if files exist in the extracted directory
        File extractedFile1 = new File(outputDir, "i1.png");
        File extractedFile2 = new File(outputDir, "i2.png");

        assertTrue("Extracted file 1 should exist", extractedFile1.exists());
        assertTrue("Extracted file 2 should exist", extractedFile2.exists());

        // Clean up test files
//        Files.deleteIfExists(Paths.get(image1));
//        Files.deleteIfExists(Paths.get(image2));
//        Files.deleteIfExists(Paths.get(zipFile));
//        Files.deleteIfExists(extractedFile1.toPath());
//        Files.deleteIfExists(extractedFile2.toPath());
//        Files.deleteIfExists(Paths.get(outputDir)); // Delete empty directory
    }
}