import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		res.setAttribute("group_id", String.valueOf( gid ));
		// location info
		int bid = -1;
		int sid = -1;
		if ( _space != null ) {
			bid = _space.get_building().get_id();
			sid = _space.get_id();
		}
		res.setAttribute("building_id", String.valueOf( bid ));
		res.setAttribute("space_id", String.valueOf( sid ));
		// name
		res.appendChild(xml_doc.createTextNode( _name ));
		return res;
	}
	
	public Employee( Node node, Company company ) {
		
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
			
			// group id
			String group_id_str = eElement.getAttribute("group_id");
			int group_id = Integer.parseInt(group_id_str);
			// find the group in the groups
			set_group(company.get_group(group_id));

			// building id
			String building_id_str = eElement.getAttribute("building_id");
			int building_id = Integer.parseInt(building_id_str);
			// find the group in the groups
			Building b = company.get_building(building_id);
			
			// space id
			String space_id_str = eElement.getAttribute("space_id");
			int space_id = Integer.parseInt(space_id_str);
			// find the space in the building
			set_space(b.get_space(space_id));
        }
	}

}
