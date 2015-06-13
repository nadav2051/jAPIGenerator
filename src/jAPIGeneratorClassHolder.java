import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
public class jAPIGeneratorClassHolder 
{
	Method[] declaredMethods = null;
	Field[]	declaredFields = null;
	Constructor[] constructors = null;
	String className;

	@SuppressWarnings("resource")
	public jAPIGeneratorClassHolder(File f, String basePath)
	{
		/* Var initialization */
		String path = jAPIGeneratorFileCollector.stripFileExtension(f.getAbsolutePath());
		String base = basePath;
		String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();

		URL[] classLoaderUrl = null;
		URLClassLoader urlClassLoader = null;
		URL filePath = null;

		className = relative.replaceAll("\\/", ".");
		Class c = null;
		try 
		{
			/* Set up the URL */
			try 
			{
				filePath = f.getParentFile().toURI().toURL();
				classLoaderUrl = new URL[] { filePath };
			} 
			catch (MalformedURLException e) 
			{
				System.out.println("Error: Bad path to load class from.");
				e.printStackTrace();
			}

			/* Set up the URLClassLoader */
			urlClassLoader = new URLClassLoader(classLoaderUrl);
			String className = jAPIGeneratorFileCollector.getFileName(f);
			c = urlClassLoader.loadClass(className);
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println("ERROR: Couldn't load class from path " + f.getAbsolutePath());
		}
		catch (NoClassDefFoundError e)
		{
			String classPackage = "";
			String newPath = "";
			// Set up the URL again, this time remove the unneccesary filepath data
			try 
			{
				filePath = f.getParentFile().toURI().toURL();
				classPackage = getClassPackage(e.getMessage());
				// Take the packageDir length, and subtract from the whole dir to get the correct dir address
				String packageDir = getPackageDir(classPackage);
				newPath = filePath.toString().substring(0,  filePath.toString().length() - packageDir.length() - 1);
				classLoaderUrl = new URL[] { new URI(newPath).toURL() };
				urlClassLoader = new URLClassLoader(classLoaderUrl);
			} 
			catch (MalformedURLException | URISyntaxException e2) 
			{
				System.out.println("Error: Bad path to load class from.");
				e.printStackTrace();
			}
			// Basing on error message get the class package name
			className = classPackage;
			try 
			{             
				c = urlClassLoader.loadClass(classPackage);
			} 
			catch (ClassNotFoundException ex) 
			{
				System.out.println("ERROR: Tried loading class: " + classPackage + " but couldn't - needs fully qualified name.");
				ex.printStackTrace();
			}

		}

		boolean retry = true;

		if (c != null)
		{
			this.declaredFields = c.getDeclaredFields();
			this.declaredMethods = c.getDeclaredMethods();
			this.constructors = c.getConstructors();
		}

	}

	private static String getClassPackage(String errorMsg)
	{
		// Start and end index of cutting
		int startIndex = errorMsg.lastIndexOf(" ") + 1;
		int endIndex = errorMsg.length() - 1;

		// Let's save a substring
		String classPackage = errorMsg.substring(startIndex, endIndex);

		// Replace char '/' to '.'
		classPackage = classPackage.replace('/', '.');

		return classPackage;
	}

	private static String getPackageDir(String fullyQualifiedName)
	{
		int endIdx = fullyQualifiedName.lastIndexOf('.');

		return fullyQualifiedName.substring(0, endIdx).replaceAll("\\.","\\/");
	}
}
