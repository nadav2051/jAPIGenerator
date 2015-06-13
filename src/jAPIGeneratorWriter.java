import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;



public class jAPIGeneratorWriter {

	public jAPIGeneratorWriter(jAPIGeneratorClassHolder cholder)
	{
		writeClassName(cholder.className);
		writeConstructors(cholder.constructors);
		writeProperties(cholder.declaredFields);
		writeMethods(cholder.declaredMethods);

	}

	private void writeClassName(String className)
	{
		System.out.println("====================================================================");
		System.out.println("CLASS NAME: " + className);
		System.out.println("====================================================================");
	}
	
	private void writeConstructors(Constructor[] cs)
	{
		if (cs == null || cs.length == 0)
		{
			System.out.println("------------- NO CONSTRUCTORS PRESENT ---------------");
			return;
		}
		System.out.println("------------- CONSTRUCTORS FOR CLASS: " + cs[0].getDeclaringClass().getSimpleName() + " ---------------");
		int i = 0;
		for (Constructor c: cs)
		{
			int j = 0;
			System.out.println("Constructor #" + i++ + ": ");
			System.out.print(cs[0].getDeclaringClass().getSimpleName() + " - Arguments: ");
			// Args
			String args = "";
			for (Class<?> t : c.getParameterTypes())
			{
				args += t.getSimpleName() +" arg" + j++ + ", ";
			}
			if (args.length() == 0)
			{
				args = "None";
			}
			else
			{
				args = args.substring(0, args.length()-2);
			}
			System.out.println(args);
			System.out.println("Modifiers: " + modifiersToString(c.getModifiers()));
			System.out.println();
		}
		
		System.out.println();
	}
	private void writeProperties(Field[] fs)
	{
		if (fs == null || fs.length == 0)
		{
			return;
		}
		System.out.println("----------------------- CLASS PROPERTIES ------------------------");
		for (Field f : fs)
		{
			String mods = modifiersToString(f.getModifiers());

			System.out.println("Field name: " + f.getName() +  ", Field type: " + f.getType().getSimpleName() + ", Modifiers: " + mods );
		}
		System.out.println();
	}
	private void writeMethods(Method[] ms)
	{
		if (ms == null || ms.length == 0)
		{
			return;
		}
		System.out.println("------------------------- METHODS --------------------------------");
		for (Method m : ms)
		{
			// Method name
			System.out.println("Method name:\t" + m.getName());
			// Modifiers
			System.out.println("Modifiers:\t" + modifiersToString(m.getModifiers()));
			// Return type
			System.out.println("Return type:\t" + m.getReturnType().getSimpleName());
			// Arguments
			System.out.print("Arguments:\t");
			String args = "";
			int i = 0;
			for (Class<?> t : m.getParameterTypes())
			{
				args += t.getSimpleName() + " arg"+ i++ +  ", ";
			}
			if (args.length() == 0)
			{
				args = "None";
			}
			else
			{
				args = args.substring(0, args.length()-2);
			}
			System.out.println(args);

			System.out.println();
		}
	}

	private static String modifiersToString(int mods)
	{
		if (mods == 0)
		{
			return "None";
		}
		else
		{
			return Modifier.toString(mods);
		}
	}
}
