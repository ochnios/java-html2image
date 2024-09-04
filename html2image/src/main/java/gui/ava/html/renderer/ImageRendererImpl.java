package gui.ava.html.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.imageio.ImageWriteParam;

import org.w3c.dom.Document;

import gui.ava.html.exception.RenderException;
import gui.ava.html.parser.DocumentHolder;
import gui.ava.html.util.FormatNameUtil;
import org.xhtmlrenderer.render.Box;
import org.xhtmlrenderer.simple.Graphics2DRenderer;

/**
 * @author Yoav Aharoni
 */
public class ImageRendererImpl implements ImageRenderer {

	private static final Set<String> IMAGE_FORMAT_WITH_ALPHA = Set.of("gif","png");

	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 768;

	private final DocumentHolder documentHolder;

	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;
	private boolean autoHeight = true;

	private String imageFormat = null;
	private float writeCompressionQuality = 1.0f;
	private int writeCompressionMode = ImageWriteParam.MODE_COPY_FROM_METADATA;
	private String writeCompressionType = null;
	private Box rootBox;

	private BufferedImage bufferedImage;
	private int cacheImageType = -1;
	private Document cacheDocument;

	public ImageRendererImpl(DocumentHolder documentHolder) {
		this.documentHolder = documentHolder;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public ImageRenderer setWidth(int width) {
		this.width = width;
		return this;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public ImageRenderer setHeight(int height) {
		this.height = height;
		return this;
	}

	@Override
	public boolean isAutoHeight() {
		return autoHeight;
	}

	@Override
	public ImageRenderer setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
		return this;
	}

	public String getImageFormat() {
		return imageFormat;
	}

	public ImageRenderer setImageType(String imageType) {
		this.imageFormat = imageType;
		return this;
	}

	@Override
	public float getWriteCompressionQuality() {
		return writeCompressionQuality;
	}

	@Override
	public ImageRenderer setWriteCompressionQuality(float writeCompressionQuality) {
		this.writeCompressionQuality = writeCompressionQuality;
		return this;
	}

	@Override
	public int getWriteCompressionMode() {
		return writeCompressionMode;
	}

	@Override
	public ImageRenderer setWriteCompressionMode(int writeCompressionMode) {
		this.writeCompressionMode = writeCompressionMode;
		return this;
	}

	@Override
	public String getWriteCompressionType() {
		return writeCompressionType;
	}

	@Override
	public ImageRenderer setWriteCompressionType(String writeCompressionType) {
		this.writeCompressionType = writeCompressionType;
		return this;
	}

	@Override
	public BufferedImage getBufferedImage(int imageType) {
		final Document document = documentHolder.getDocument();
		if (bufferedImage != null || cacheImageType != imageType || cacheDocument != document) {
			cacheImageType = imageType;
			cacheDocument = document;
			Graphics2DRenderer renderer = new Graphics2DRenderer();
			renderer.setDocument(document, document.getDocumentURI());
			Dimension dimension = new Dimension(width, height);
			bufferedImage = new BufferedImage(width, height, imageType);

			if (autoHeight) {
				// do layout with temp buffer
				Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
				renderer.layout(graphics2D, new Dimension(width, height));
				graphics2D.dispose();

				Rectangle size = renderer.getMinimumSize();
				final int autoWidth = (int) size.getWidth();
				final int autoHeight = (int) size.getHeight();
				bufferedImage = new BufferedImage(autoWidth, autoHeight, imageType);
				dimension = new Dimension(autoWidth, autoHeight);
			}

			Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
			renderer.layout(graphics2D, dimension);
			renderer.render(graphics2D);
			rootBox = renderer.getPanel().getRootBox();
			graphics2D.dispose();
		}
		return bufferedImage;
	}

	@Override
	public Box getRootBox() {
		if (rootBox == null) {
			getBufferedImage();
		}
		return rootBox;
	}

	@Override
	public ImageRendererImpl clearCache() {
		bufferedImage = null;
		rootBox = null;
		cacheDocument = null;
		cacheImageType = -1;
		return this;
	}

	public BufferedImage getBufferedImage() {
		return getBufferedImage(BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void saveImage(OutputStream outputStream, boolean closeStream) {
		save(outputStream, null, closeStream);
	}

	@Override
	public void saveImage(File file) {
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
			save(outputStream, file.getName(), true);
		} catch (IOException e) {
			throw new RenderException("IOException while rendering image to " + file.getAbsolutePath(), e);
		}
	}

	@Override
	public void saveImage(String filename) {
		saveImage(new File(filename));
	}

	private void save(OutputStream outputStream, String filename, boolean closeStream) {
		try {
			final String imageFormat = FormatNameUtil.formatForFilename(filename);
			final boolean hasAlpha = IMAGE_FORMAT_WITH_ALPHA.contains(imageFormat);
			final BufferedImage bufferedImage = getBufferedImage(hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
			final CustomizableFSImageWriter imageWriter = getImageWriter(imageFormat);
			imageWriter.write(bufferedImage, outputStream, closeStream);
		}
		catch (IOException e) {
			throw new RenderException("IOException while rendering image", e);
		}
		finally {
			if (closeStream) {
				try {
					outputStream.close();
				}
				catch (IOException ignore) {}
			}
		}
	}

	/**
	 * note: do not cache the format, otherwise multiple calls will return the same value! :)
	 * @deprecated use {@link FormatNameUtil#formatForFilename(String)}
	 */
	@Deprecated
	private String getImageFormat(String filename) {
		if (this.imageFormat != null) {
			return imageFormat;
		}
		if (filename != null) {
			return FormatNameUtil.formatForFilename(filename);
		}
		return FormatNameUtil.DEFAULT_FORMAT;
	}

	private CustomizableFSImageWriter getImageWriter(String imageFormat) {
        return new CustomizableFSImageWriter(imageFormat, writeCompressionMode, writeCompressionQuality, writeCompressionType);
	}

}