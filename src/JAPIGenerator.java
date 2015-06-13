import java.io.File;
import java.util.ArrayList;


public class JAPIGenerator {

	JAPIGeneratorFileCollector files = null;
	ArrayList<JAPIGeneratorClassHolder> classes = null;
	
	public JAPIGenerator(String path, boolean recursive)
	{
		/* Obtain all files from the path. */
		files = new JAPIGeneratorFileCollector(path, recursive);
		classes = new ArrayList<JAPIGeneratorClassHolder>();
		for (File f : files.getFileList())
		{
			classes.add(new JAPIGeneratorClassHolder(f, path));
		}
		for (JAPIGeneratorClassHolder c : classes)
		{
			new JAPIGeneratorWriter(c);
		}
	}
	
	

}
