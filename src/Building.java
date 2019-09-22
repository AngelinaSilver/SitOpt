import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Building {
	private int _id;
	private String _name;
	
	public Vector<Space> _spaces;
	
	public Building( int id, String name ) {
		_id = id;
		_name = name;
	}
	
	public int get_id() { return _id; }
	public String get_name() { return _name; }
	
	public void add_space( Space s ) { _spaces.add( s ); }
	
	public Element to_xml(Document xml_doc) {
		Element res = xml_doc.createElement("Building");
		res.setAttribute("id", String.valueOf( _id ) );
		res.setAttribute( "name", _name );
		for ( Space s : _spaces )
			res.appendChild( s.to_xml(xml_doc) );
		return res;
	}
	
}
