import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Group {
	private int _id;
	private String _name;
	
	public Group( int id, String name ) {
		_id = id;
		_name = name;
	}
	
	public int get_id() { return _id; }
	public String get_name() { return _name; }
	
	public Element to_xml(Document xml_doc) {
		Element res = xml_doc.createElement("Group");
		res.setAttribute("id", String.valueOf( _id ) );
		res.appendChild(xml_doc.createTextNode( _name ));
		return res;
	}

	public Group( Node node ) {
		
        if (node.getNodeType() == Node.ELEMENT_NODE) {
        	 
            Element eElement = (Element) node;
	
			String id = eElement.getAttribute("id");
			_id = Integer.parseInt(id);

			_name = eElement.getAttribute("name");
			
			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++)
			{
			    Node element = nodeList.item(i);
			    if (element.getNodeType() == Node.TEXT_NODE) 
			    {
			    	_name = element.getTextContent();
			    }

			}

        }
	}
}
