import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class JAPIGeneratorFileCollector {

	Queue<File> directories = null;
	ArrayList<File> files = null;


	// Constructor.
	public JAPIGeneratorFileCollector(String path, boolean recursive)
	{
		loadAllClassFiles(path, recursive);

	}

	public ArrayList<File> getFileList()
	{
		return files;
	}
	/***
	 * This runs untill directories are empty.
	 */
	private void loadAllClassFiles(String path, boolean recursive)
	{
		/* Init the directories and files structure.*/
		if (this.directories == null)
		{
			this.directories = new LinkedList<File>();
		}
		else
		{
			System.out.println("ERROR: directories ArrayList is unexpecetdly initialized.");
		}
		if (this.files == null)
		{
			this.files = new ArrayList<File>();
		}
		else
		{
			System.out.println("ERROR: directories ArrayList is unexpecetdly initialized.");
		}
		/* Load the initial data from the root folder given to us. */
		loadDirectoryClassFileContent(path);
		if (recursive)
		{
			while (directories.isEmpty() == false)
			{
				File directoryPath = directories.remove();
				/* Pop the first index from the list */
				loadDirectoryClassFileContent(directoryPath.getAbsolutePath());
			}
		}
	}
	/***
	 * This function loads the file and directory list in a directory, and adds it to the global array.
	 * @param path Path of the directory files to load - works recursively.
	 */
	private void loadDirectoryClassFileContent(String path)
	{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();


		for (int i = 0; i < listOfFiles.length; i++) 
		{
			// If is file, and extension is .class, add it to our to-do list.
			if (listOfFiles[i].isFile())
			{
				String extension = getFileExtension(listOfFiles[i]);
				if (extension.compareTo(".class") == 0)
				{
					this.files.add(listOfFiles[i]);
				}
			} 
			// If directory, push into the directories.
			else if (listOfFiles[i].isDirectory()) 
			{
				this.directories.add(listOfFiles[i]);
			}
		}
	}

	public static String getFileExtension(File f)
	{
		String retval = "";
		if (f == null)
		{
			return retval;
		}
		/* Get the interval in which the extension located. */
		String fullname = f.getName();
		int startIdx = fullname.lastIndexOf('.');
		/* If no extension, return empty. */
		if (startIdx == -1)
		{
			return retval;
		}
		int endIdx = fullname.length();
		/* Return substring of the extension. */
		retval = fullname.substring(startIdx, endIdx);
		return retval;
	}

	public static String getFileName(File f)
	{
		if (f == null)
		{
			return "";
		}
		/* Get the interval in which the extension located. */
		String fullname = f.getName();
		int endIdx = fullname.lastIndexOf('.');
		/* If no extension, return empty. */
		if (endIdx == -1)
		{
			return f.getName();
		}
		/* Return substring of the extension. */
		return fullname.substring(0, endIdx);
	}

	public static String stripFileExtension(String filename)
	{
		if (filename == null)
		{
			return "";
		}
		/* Get the interval in which the extension located. */
		int endIdx = filename.lastIndexOf('.');
		/* If no extension, return empty. */
		if (endIdx == -1)
		{
			return filename;
		}
		/* Return substring of the extension. */
		return filename.substring(0, endIdx);

	}

}
