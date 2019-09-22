
public class SimpleExample {
	static Company create_company() {
		Company c = new Company();
		Building b1 = new Building(1, "PK");
		c.add_building( b1 );
		Space s1 = new Space(1, b1, false, 3);
		b1.add_space( s1 );
		Space s2 = new Space(2, b1, false, 2);
		b1.add_space( s2 );
		Space s3 = new Space(3, b1, false, 3);
		b1.add_space( s3 );
		Space s4 = new Space(4, b1, true, 1);
		b1.add_space( s4 );
		
		Group g1 = new Group(1,"Perspec");
		c.add_group( g1 );
		Group g2 = new Group(2,"G5");
		c.add_group( g2 );
		
		Employee e1 = new Employee(1,"Amit");
		c.add_employee( e1 );
		e1.set_group( g2 );
		Employee e2 = new Employee(2,"Efrat");
		c.add_employee( e2 );
		e2.set_group( g1 );
		Employee e3 = new Employee(3,"Marat");
		c.add_employee( e3 );
		e3.set_group( g1 );
		Employee e4 = new Employee(4,"Evgeny");
		c.add_employee( e4 );
		e4.set_group( g1 );
		
		return c;
	}
}
