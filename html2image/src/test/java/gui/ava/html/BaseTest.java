package gui.ava.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * @author Yoav Aharoni
 */
public class BaseTest {

	private static final String PROJECT_NAME = "html2image";
	private static final String TEST_OUTPUT_PATH = "./output";

	static {
		try {
			Files.createDirectories(getPath(TEST_OUTPUT_PATH));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static File getTestOutputFile(String filename) {
		return new File( getFile(TEST_OUTPUT_PATH), filename );
	}

	private static final String TEST1_PATH = "test1.html";

	public static URL getTest1Url() {
		try {
			return getURL(TEST1_PATH);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static URL getURL(String filename) throws FileNotFoundException {
		URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
		if (url == null) throw new FileNotFoundException(filename+" not found");
		return url;
	}

	public static File getTest1File() throws FileNotFoundException {
        return getTestResourceFile(TEST1_PATH);
    }

	public static File getTestResourceFile(String filename) throws FileNotFoundException {
        try {
			File input = getResourceFile(filename);
			if (input.exists()) return input;
        }
		catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
		throw new FileNotFoundException(filename);
	}


	public static String getBasePath() {
        try {
			String sourcePath = getURL(".").getPath();
			String basePath = sourcePath.substring(0, sourcePath.lastIndexOf(PROJECT_NAME)-1);
			return basePath;
        }
		catch (FileNotFoundException e) {
			Logger.getLogger(BaseTest.class.getName()).warning("base path not found, fallback to 'user.dir' system property");
			return getUserProjectRootDirectory().toString();
        }
	}

	public static Path getUserProjectRootDirectory() {
		String envRootDir = System.getProperty("user.dir");
		Path rootDir = Paths.get(".").normalize().toAbsolutePath();
		if ( rootDir.startsWith(envRootDir) ) {
			return rootDir;
		} else {
			throw new RuntimeException("Root dir not found in user directory.");
		}
	}


	private static File getResourceFile(String filename) throws FileNotFoundException, URISyntaxException {
		return new File(getURL(filename).toURI());
	}

	private static File getFile(String filename) {
		return new File(getBasePath(), filename);
	}

	private static Path getPath(String filename) {
		return Path.of(getBasePath(), filename);
	}



//	public static void main(String[] args) throws FileNotFoundException {
////		final File sourceFile = new File(BaseTest.class.getProtectionDomain().getCodeSource().getLocation().toString());
////		System.out.println(sourceFile);
////		System.out.println(BaseTest.class.getClassLoader().getResource(TEST1_PATH));
////		System.out.println(Thread.currentThread().getContextClassLoader().getResource(TEST1_PATH));
//
////		final String packagePath = BaseTest.class.getPackageName().replace('.', '/');
////		final String fullPath = BaseTest.class.getResource("./").toString();
////		final String basePath = fullPath.substring(0, (fullPath.length()-packagePath.length()-1) );
////		System.out.println(packagePath);
////		System.out.println(fullPath);
////		System.out.println(basePath);
//
////		String sourcePath = getURL(".").getPath();
////		String basePath = sourcePath.substring(0, sourcePath.lastIndexOf(PROJECT_NAME));
////		System.out.println(sourcePath);
////		System.out.println(basePath);
//
//		System.out.println(getBasePath());
//		System.out.println(getUsersProjectRootDirectory());
//    }

}
