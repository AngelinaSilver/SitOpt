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
