import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Company {
	public Vector<Building> _buildings;
	public Vector<Employee> _employes;
	public Vector<Group> _groups;
	
	public Company() {
		_buildings = new Vector<Building>();
		_employes = new Vector<Employee>();
		_groups = new Vector<Group>();
	}
	
	public void add_building( Building b ) { _buildings.add( b ); }
	public void add_group( Group g ) { _groups.add( g ); }
	public void add_employee( Employee e ) { _employes.add( e ); }
	public int get_next_employee_id() { return (_employes.lastElement().get_id() + 1); }

	public boolean is_employee_without_place() {
		Iterator empIt = _employes.iterator();
		//group.addItem("");
		while (empIt.hasNext()) {
			Employee emp = (Employee) empIt.next();
			if (emp.get_space()==null) {
				return true;
			}
		}
		return false;

	}

	public Group getGroupByName(String groupName) {
		Iterator groupIt = _groups.iterator();
		while (groupIt.hasNext()) {
			Group g = (Group) groupIt.next();
			if(g.get_name().equals(groupName)) {
				return g;
			}
		}
		return null;
	}
	
	public Element to_xml(Document xml_doc) {
		Element res = xml_doc.createElement("Company");

		// buildings
		Element bres = xml_doc.createElement("Buildings");
		for ( Building b : _buildings )
			bres.appendChild(b.to_xml(xml_doc));
		res.appendChild(bres);

		// groups
		Element gres = xml_doc.createElement("Groups");
		for ( Group g : _groups )
			gres.appendChild(g.to_xml(xml_doc));
		res.appendChild(gres);
		
		//employees
		Element eres = xml_doc.createElement("Employees");
		for ( Employee e : _employes )
			eres.appendChild(e.to_xml(xml_doc));
		res.appendChild(eres);
		
		return res;
	}

}
