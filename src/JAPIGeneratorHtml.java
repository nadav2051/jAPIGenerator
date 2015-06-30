import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JAPIGeneratorHtml {
	
	BufferedWriter bw;
	FileWriter fw;

	public JAPIGeneratorHtml(JAPIGeneratorClassHolder cholder){

		try{
			File myfile = new File(cholder.className + ".html");
			fw = new FileWriter(myfile);
			bw = new BufferedWriter(fw);
			bw.write("<html>\n<head>\n<title>\n" + cholder.className + "\n</title>\n</head>\n");
			bw.write("<body>\n");
			writeConstructors(cholder.constructors);
			writeProperties(cholder.declaredFields);
			writeMethods(cholder.declaredMethods);
			bw.write("</body>\n");
			bw.write("</html>\n");
			close();
		}
		catch(Exception e){};
	}

	public void close(){
		try{
			bw.close();
			fw.close();
		}
		catch(Exception e){};
	}

	public void writeConstructors(Constructor[] cs){
		try{
			bw.write("<h3>Constructors</h3>\n");
			if (cs == null || cs.length == 0)
			{
				bw.write("No Constructors\n");
				return;
			}
			bw.write("<table>");
			int i = 0;
			for (Constructor c: cs)
			{
				int j = 0;
				bw.write("<tr>\n");
				bw.write("<td>");
				bw.write("Constructor #" + i++ + ": \n");
				bw.write("</tb>");
				bw.write("</tr>\n");
				bw.write("<tr>\n");
				bw.write("<td>");
				bw.write(modifiersToString(c.getModifiers()) + " ");
				bw.write(cs[0].getDeclaringClass().getSimpleName() + "(");
				String args = "";
				for (Class<?> t : c.getParameterTypes())
				{
					args += "<b>" + t.getSimpleName() + "</b> arg" + j++ + ", ";
				}
				if (args.length() == 0)
				{
					args = "";
				}
				else
				{
					args = args.substring(0, args.length()-2);
				}
				bw.write(args + ")\n");
				bw.write("</td>");
				bw.write("</tr>\n");
			}
			bw.write("</table>");
		}
		catch(Exception e){};
	}

	public void writeProperties(Field[] fs)
	{
		try{
			bw.write("<h3>Properties:</h3>\n");
			if (fs == null || fs.length == 0)
			{
				bw.write("No Properties\n");
				return;
			}
			bw.write("<table>");
			for (Field f : fs)
			{
				String mods = modifiersToString(f.getModifiers());
				bw.write("<tr>\n");
				bw.write("<td>");
				if (mods != "None"){
					bw.write(mods + " ");
				}
				bw.write(f.getType().getSimpleName() + " " + f.getName() + ";");
				bw.write("</td>");
				bw.write("</tr>\n");
			}
			bw.write("</table>");
		}
		catch(Exception e){};
	}

	public void writeMethods(Method[] ms)
	{
		try{
			bw.write("<h3>Methods:</h3>\n");
			if (ms == null || ms.length == 0)
			{
				bw.write("No Methods\n");
				return;
			}
			bw.write("<table>");
			for (Method m : ms)
			{
				bw.write("<tr>\n");
				bw.write("<td>");
				bw.write(modifiersToString(m.getModifiers()) + " ");
				bw.write(m.getReturnType().getSimpleName() + " ");
				bw.write(m.getName() + " (");
				String args = "";
				int i = 0;
				for (Class<?> t : m.getParameterTypes())
				{
					args += "<b>" + t.getSimpleName() + "</b> arg"+ i++ +  ", ";
				}
				if (args.length() == 0)
				{
					args = "";
				}
				else
				{
					args = args.substring(0, args.length()-2);
				}
				bw.write(args + ")");
				bw.write("</td>");
				bw.write("</tr>\n");
				
			}
			bw.write("</table>");
		}
		catch(Exception e){};
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