import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;

import org.w3c.dom.Document;

public class SimpleExample {
	static Company create_company() {
		Company c = new Company();
		Building b1 = new Building(1, "PK");
		c.add_building( b1 );
		Space s1 = new Space(1, b1, false, 3);
		b1.add_space( s1 );
		Space s2 = new Space(2, b1, false, 2);
		b1.add_space( s2 );
		Space s3 = new Space(3, b1, false, 3);
		b1.add_space( s3 );
		Space s4 = new Space(4, b1, true, 1);
		b1.add_space( s4 );
		
		Group g1 = new Group(1,"Perspec");
		c.add_group( g1 );
		Group g2 = new Group(2,"G5");
		c.add_group( g2 );
		
		Employee e1 = new Employee(1,"Amit");
		c.add_employee( e1 );
		e1.set_group( g2 );
		Employee e2 = new Employee(2,"Efrat");
		c.add_employee( e2 );
		e2.set_group( g1 );
		Employee e3 = new Employee(3,"Marat");
		c.add_employee( e3 );
		e3.set_group( g1 );
		Employee e4 = new Employee(4,"Evgeny");
		c.add_employee( e4 );
		e4.set_group( g1 );
		
		return c;
	}

	static void save_to_xml(String xmlFilePath, Company c) 
	{
        try
        {
	        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	        Document document = documentBuilder.newDocument();
	        c.to_xml(document);
	        
	        // create the xml file
	        //transform the DOM Object to an XML File
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource domSource = new DOMSource(document);
	        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
	
	        // If you use
	        // StreamResult streamResult = new StreamResult(System.out);
	        // the output will be pushed to the standard output ...
	        // You can use that for debugging 

	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
 
        System.out.println("Done creating XML File");

	}
	
	static Company load_xml(String xmlFilePath) {
		
		Company c = new Company();

        try {
        	
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
 
            Document document = documentBuilder.parse(xmlFilePath);
 
            NodeList nodeList = document.getElementsByTagName("Employee");
 
            System.out.println("Number of elements with tag name employee : " + nodeList.getLength());
 
            c.load_from_xml(document);
 
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
		
		return c;
	}

}
