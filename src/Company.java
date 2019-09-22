import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

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
	
	public Group get_group(int id)
	{
		for (Group g : _groups)
		{
			if (g.get_id() == id)
				return g;
		}
		return null;
	}

	public Building get_building(int id)
	{
		for (Building g : _buildings)
		{
			if (g.get_id() == id)
				return g;
		}
		return null;
	}
	

	public Element to_xml(Document xml_doc) {
		Element res = xml_doc.createElement("Company");
		xml_doc.appendChild(res);

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

	public void load_from_xml(Document xml_doc) 
	{
		_buildings = new Vector<Building>();
		_employes = new Vector<Employee>();
		_groups = new Vector<Group>();

		// check that there is only one company...
		
		// buildings
		NodeList nodeList = xml_doc.getElementsByTagName("Building");
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
        	 
            Node node = nodeList.item(itr);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
			    add_building(new Building(eElement));
            }
        }
        
		// groups
		NodeList groupList = xml_doc.getElementsByTagName("Group");
        for (int itr = 0; itr < groupList.getLength(); itr++) {
        	 
            Node node = groupList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
			    add_group(new Group(eElement));

            }
        }
        
		// employees
		NodeList empList = xml_doc.getElementsByTagName("Employee");
        for (int itr = 0; itr < empList.getLength(); itr++) {
        	 
            Node node = empList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
			    add_employee(new Employee(eElement, this));

            }
        }
 	
	}
}
