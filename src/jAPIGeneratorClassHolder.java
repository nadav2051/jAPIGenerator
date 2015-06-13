import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URI;
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
		String path = jAPIGeneratorFileCollector.stripFileExtension(f.getAbsolutePath());
		String base = basePath;
		
		String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
		className = relative.replaceAll("\\/", ".");
		
		System.out.println(className);
		
		Class c = null;
		try 
		{
			/* Var initialization */
			URL[] classLoaderUrl = null;
			URLClassLoader urlClassLoader = null;
			
			/* Set up the URL */
			try 
			{
				URL filePath = f.getParentFile().toURI().toURL();
				classLoaderUrl = new URL[] { filePath};
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
		if (c != null)
		{
			this.declaredFields = c.getDeclaredFields();
			this.declaredMethods = c.getDeclaredMethods();
			this.constructors = c.getConstructors();
		}
		
	}

}
