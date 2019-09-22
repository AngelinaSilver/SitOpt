import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
}
