import java.io.File;
import java.util.ArrayList;


public class jAPIGenerator {

	jAPIGeneratorFileCollector files = null;
	ArrayList<jAPIGeneratorClassHolder> classes = null;
	
	public jAPIGenerator(String path, boolean recursive)
	{
		/* Obtain all files from the path. */
		files = new jAPIGeneratorFileCollector(path, recursive);
		classes = new ArrayList<jAPIGeneratorClassHolder>();
		for (File f : files.getFileList())
		{
			classes.add(new jAPIGeneratorClassHolder(f, path));
		}
		for (jAPIGeneratorClassHolder c : classes)
		{
			new jAPIGeneratorWriter(c);
		}
	}
	
	

}
