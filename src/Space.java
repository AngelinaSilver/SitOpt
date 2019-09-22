import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Space {
	private int _id;
	private boolean _is_room;
	private int _capacity;
	private Building _building;
	
	public Vector<Employee> _persons;
	
	public Space( int id, Building b, boolean is_room, int capacity ) {
		_id = id;
		_building = b;
		_is_room = is_room;
		_capacity = capacity;
	}
	
	public int get_id() { return _id; }
	public boolean is_room() { return _is_room; }
	public int get_capacity() { return _capacity; }
	public Building get_building() { return _building; }
	
	public Element to_xml(Document xml_doc) {
		Element res = xml_doc.createElement("Space");
		res.setAttribute("id", String.valueOf( _id ) );
		res.setAttribute("room", String.valueOf( _is_room ) );
		res.setAttribute("cap", String.valueOf( _capacity ) );
		return res;
	}
}
