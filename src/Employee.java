
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
}
