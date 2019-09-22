import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Employee {
	private int _id;
	private String _name;
	private Group _group;
	private Space _space;
	
	public Employee( int id, String name ) {
		_id = id;
		_name = name;
	}
	
	public int get_id() { return _id; }
	public String get_name() { return _name; }
	public Group get_group() { return _group; }
	public void set_group( Group g ) { _group = g; }
	public Space get_space() { return _space; }
	public void set_space( Space s ) { _space = s; }
	
	public Element to_xml(Document xml_doc) {
		Element res = xml_doc.createElement("Employee");
		res.setAttribute("id", String.valueOf( _id ) );
		// groupd info
		int gid = -1;
		if ( _group != null )
			gid = _group.get_id();
		res.setAttribute("gid", String.valueOf( gid ));
		// location info
		int bid = -1;
		int sid = -1;
		if ( _space != null ) {
			bid = _space.get_building().get_id();
			gid = _space.get_id();
		}
		res.setAttribute("bid", String.valueOf( bid ));
		res.setAttribute("sid", String.valueOf( sid ));
		// name
		res.appendChild(xml_doc.createTextNode( _name ));
		return res;
	}
	
}
