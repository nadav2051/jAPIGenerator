
public class JAPIGeneratorRunner {

	public static void main(String[] args) 
	{
		// Init the working directory path string.
		String presentWorkingDirectory = System.getProperty("user.dir");
		if (args.length > 1 && args[1].compareTo("-r") == 0)
		{
			JAPIGenerator jenerator = new JAPIGenerator(args[0], true);
		}
		else
		{
			JAPIGenerator jenerator = new JAPIGenerator(args[0], false);
		}

	}


}
