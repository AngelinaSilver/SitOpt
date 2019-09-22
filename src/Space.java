import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Space {
	private int _id;
	private boolean _is_room;
	private int _capacity;
	private Building _building;
	private int _x;
	private int _y;
	private int _w;
	private int _h;


	
	public Vector<Employee> _seats;
	
	public Space( int id, Building b, boolean is_room, int capacity, int x, int y ) {
		_id = id;
		_building = b;
		_is_room = is_room;
		_capacity = capacity;
		_seats = new Vector<Employee>();
		_x=x;
		_y=y;
	}
	
	public int get_id() { return _id; }
	public boolean is_room() { return _is_room; }
	public int get_capacity() { return _capacity; }
	public Building get_building() { return _building; }
	public int get_x(){ return _x;}
	public int get_y(){ return _y;}
	public int get_w(){ return _w;}
	public int get_h(){ return _h;}



	public Element to_xml(Document xml_doc) {
		Element res = xml_doc.createElement("Space");
		res.setAttribute("id", String.valueOf( _id ) );
		res.setAttribute("room", String.valueOf( _is_room ) );
		res.setAttribute("cap", String.valueOf( _capacity ) );
		return res;
	}
}
