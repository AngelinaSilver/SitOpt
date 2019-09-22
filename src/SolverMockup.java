import java.util.Vector;

public class SolverMockup implements SolverIntf {

	@Override
	public boolean populate(Company cmpny) throws SolverException {
		Vector<Employee> es = new Vector<Employee>();
		for ( Employee e : cmpny._employes )
			if ( e.get_space() == null )
				es.add( e );

		if( es.isEmpty() )
			return false;
		
		for ( Building b : cmpny._buildings )
			for ( Space s : b._spaces )
				while ( s.get_capacity() > s._seats.size() ) {
					Employee e = es.lastElement();
					es.remove(es.size() - 1);
					e.set_space( s );
					s._seats.add( e );
					if ( es.isEmpty() )
						return true;
				}
		
		throw new SolverException("NOT ENOUGH SPACE");
	}
	
	@Override
	public void set_max_distance( int distance ) { }
}
