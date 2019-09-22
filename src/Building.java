import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Building {
	private int _id;
	private String _name;
	
	public Vector<Space> _spaces;
	
	public Building( int id, String name ) {
		_id = id;
		_name = name;
        _spaces = new Vector<Space>(); 
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
	

	public Space get_space(int id)
	{
		for (Space g : _spaces)
		{
			if (g.get_id() == id)
				return g;
		}
		return null;
	}

	public Building( Node node ) 
	{
		
        _spaces = new Vector<Space>(); 

        if (node.getNodeType() == Node.ELEMENT_NODE) {
        	 
            Element eElement = (Element) node;
	
			String id = eElement.getAttribute("id");
			_id = Integer.parseInt(id);

			_name = eElement.getAttribute("name");
			
			NodeList nodeList = node.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++)
			{
			    Node element = nodeList.item(i);
			    if (element.getNodeType() == Node.ELEMENT_NODE) 
			    {
			    	add_space(new Space(element, this));
			    }
			}

        }
	}
	
}
