package gui.ava.html.renderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.xhtmlrenderer.util.FSImageWriter;

/**
 * Flying Saucer 9.x forgot to create a constructor to set the compression parameters!
 */
public class CustomizableFSImageWriter extends FSImageWriter {

    private final String imageFormat;
    private final int writeCompressionMode;
    private final float writeCompressionQuality;
    private final String writeCompressionType;

    public CustomizableFSImageWriter(String imageFormat, int writeCompressionMode, float writeCompressionQuality, String writeCompressionType) {
        super(imageFormat);
        this.imageFormat = imageFormat;
        this.writeCompressionMode = writeCompressionMode;
        this.writeCompressionQuality = writeCompressionQuality;
        this.writeCompressionType = writeCompressionType;
    }

    /**
     * Write the passed image to the passed OutputStream.
     * Close the output stream only if the closeStream param is true.
     */
    public void write(BufferedImage image, OutputStream os, boolean closeStream) throws IOException {
        ImageWriter writer = lookupImageWriterForFormat(this.imageFormat);

        try {
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);

            try {
                writer.setOutput(ios);
                ImageWriteParam parameters = getImageWriteParameters(writer);
                writer.write(null, new IIOImage(image, null, null), parameters);
                ios.flush();
            }
            catch (Throwable t) {
                if (closeStream && ios != null) {
                    try {
                        ios.close();
                    } catch (Throwable t2) {
                        t.addSuppressed(t2);
                    }
                }

                throw t;
            }

            if (closeStream && ios != null) {
                ios.close();
            }
        } finally {
            writer.dispose();
        }
    }

    @Override
    protected ImageWriteParam getImageWriteParameters(ImageWriter writer) {
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed() && this.writeCompressionMode != 3) {
            param.setCompressionMode(this.writeCompressionMode);
            if (this.writeCompressionMode == 2) {
                param.setCompressionType(this.writeCompressionType);
                param.setCompressionQuality(this.writeCompressionQuality);
            }
        }

        return param;
    }

    private static ImageWriter lookupImageWriterForFormat(String imageFormat) {
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(imageFormat);
        if (iter.hasNext()) {
            return iter.next();
        } else {
            throw new IllegalArgumentException("Image writer not found for format " + imageFormat);
        }
    }

}
