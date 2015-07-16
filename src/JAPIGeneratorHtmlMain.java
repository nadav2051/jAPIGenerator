import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;



public class JAPIGeneratorHtmlMain {

	JAPIGeneratorHtml html;

	public JAPIGeneratorHtmlMain(ArrayList<JAPIGeneratorClassHolder> classes)
	{
		try{
			File theDir = new File("Output");
			theDir.mkdir();
			File myfile = new File("Output//index.html");
			FileWriter fw = new FileWriter(myfile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<html>\n<head>\n<title>\nIndexer\n</title>\n</head>\n");
			bw.write("<body>\n");
			bw.write("<h3>Classes:</h3>\n");
			bw.write("<table>");
			for (JAPIGeneratorClassHolder c : classes){ 
				bw.write("<tr>");
				bw.write("<td>");
				addClassesIndex(bw, c);
				bw.write("</td>");
				bw.write("</tr>");
				outputToHtml(c);
			}
			bw.write("</table>");
			bw.write("</body>\n");
			bw.write("</html>\n");
			bw.close();
		}
		catch(Exception e){};
	}

	private void outputToHtml(JAPIGeneratorClassHolder cholder){
		html = new JAPIGeneratorHtml(cholder);
	}

	private void addClassesIndex(BufferedWriter bw, JAPIGeneratorClassHolder cholder){
		try{
			bw.write("<a href=\"" + cholder.className + ".html\">" + cholder.className + "</a>\n");
		}
		catch(Exception e){};
	}
}