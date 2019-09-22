import java.util.Vector;

public class Building {
	private int _id;
	private String _name;
	
	public Vector<Space> spaces;
	
	public Building( int id, String name ) {
		_id = id;
		_name = name;
	}
	
	public int get_id() { return _id; }
	public String get_name() { return _name; }
}
