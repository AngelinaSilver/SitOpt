import java.util.Vector;

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
}
