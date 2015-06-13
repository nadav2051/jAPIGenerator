
public class jAPIGeneratorRunner {

	public static void main(String[] args) 
	{
		// Init the working directory path string.
		String presentWorkingDirectory = System.getProperty("user.dir");
		if (args[1].compareTo("-r") == 0)
		{
			jAPIGenerator jenerator = new jAPIGenerator(args[0], true);
		}
		else
		{
			jAPIGenerator jenerator = new jAPIGenerator(args[0], false);
		}

	}


}
