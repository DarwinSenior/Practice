package manicBall;


import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class JDom {
	private static void pharse(String fileName){
		SAXBuilder builder=new SAXBuilder();
		try {
			Document doc=builder.build(fileName);
			listNodes(doc,0);
		} catch (JDOMException e) {
			System.out.println(e.getMessage());		
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	private static void listNodes(Object object, int depth){
		if (object instanceof Element) {
			Element element=(Element) object;
			System.out.println(element.getName()+" ");
			if(element.getName()=="interfaceConfig"){
				String a=element.getAttributeValue("resource");
				System.out.println(element.getName()+" "+"="+a);
	            }
			List children=element.getContent();
			while (children.iterator().hasNext()) {
				Object child = children.iterator().next();
				listNodes(child, depth+1);
			}
		}else if (object instanceof Document) {
			Document document=(Document) object;
			List children=document.getContent();
		}
	}
}
