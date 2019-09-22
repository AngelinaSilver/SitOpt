import java.util.Vector;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class Pair
{
	// Return a map entry (key-value pair) from the specified values
	public static <T, U> Map.Entry<T, U> of(T first, U second)
	{
		return new AbstractMap.SimpleEntry<>(first, second);
	}
}

public class SolverChoco implements SolverIntf {
	private int _max_dist;
	
	private boolean distance_calculated = false;
	private int space_distance[][];
	
	
	public SolverChoco() { }
	
	@Override
	public boolean populate(Company cmpny) throws SolverException {
		HashMap<Employee, Integer> emps_newids = new HashMap<Employee, Integer>();
		
		for ( Employee e : cmpny._employes ) {
			if ( e.get_space() != null ) {
				int new_id = emps_newids.size();
				emps_newids.put(e, new_id);
			}
		}
		
		int emps_seated = emps_newids.size();
		if ( emps_seated == cmpny._employes.size() )
			return false;
		
		for ( Employee e : cmpny._employes ) {
			if ( e.get_space() == null ) {
				int new_id = emps_newids.size();
				emps_newids.put(e, new_id);
			}
		}
		int emps_new = emps_newids.size() - emps_seated;
		
		
		Vector<Integer> emps_to_seats = new Vector<Integer>();
		emps_to_seats.setSize(emps_seated);
		Vector<Integer> emps_to_spaces = new Vector<Integer>();
		emps_to_spaces.setSize(emps_seated);
		
		ArrayList<Map.Entry<Space, Integer>> all_spaces = new ArrayList<Map.Entry<Space, Integer>>();
		int seat_id = 0;
		for ( Building b : cmpny._buildings )
			for ( Space s : b._spaces ) {
				int space_id = all_spaces.size();
				all_spaces.add(Pair.of( s, seat_id ));
				int max_seat_id = seat_id + s.get_capacity(); 
				for(int i=0; i<s._seats.size(); i++) {
					Employee e = s._seats.elementAt(i);
					if ( e != null ) {
						int eid = emps_newids.get(e);
						emps_to_seats.setElementAt(seat_id, eid);
						emps_to_spaces.setElementAt(space_id, eid);
					} else {
						emps_to_seats.add(seat_id);
						emps_to_spaces.add(space_id);
					}
					seat_id++;
				}
				for( ; seat_id < max_seat_id; seat_id++) {
					emps_to_seats.add(seat_id);
					emps_to_spaces.add(space_id);
				}
			}		
		
		int emp_free = seat_id - emps_seated - emps_new;
		if ( emp_free < 0 )
			throw new SolverException("NOT ENOUGH SEATS ! (fire some employees)");

		if ( seat_id != emps_to_spaces.size() )
			throw new SolverException("INTERNAL ERROR");
		
		space_distance = new int[all_spaces.size()][all_spaces.size()];
		for ( int i=0; i<all_spaces.size(); i++) {
			Space si = all_spaces.get(i).getKey();
			for ( int j=i+1; j<all_spaces.size(); j++) {
				Space sj = all_spaces.get(j).getKey();
				int score = calc_dist_score( si, sj);
				space_distance[i][j] = score;
				space_distance[j][i] = score;
			}
		}

		
/*
int N = 100;
// 1. Modelling part
Model model = new Model("all-interval series of size "+ N);
// 1.a declare the variables
IntVar[] S = model.intVarArray("s", N, 0, N - 1, false);
IntVar[] V = model.intVarArray("V", N - 1, 1, N - 1, false);
// 1.b post the constraints
for (int i = 0; i < N - 1; i++) {
    model.distance(S[i + 1], S[i], "=", V[i]).post();
}
model.allDifferent(S).post();
model.allDifferent(V).post();
S[1].gt(S[0]).post();
V[1].gt(V[N - 2]).post();

// 2. Solving part
Solver solver = model.getSolver();
// 2.a define a search strategy
solver.setSearch(Search.minDomLBSearch(S));
if(solver.solve()){
    System.out.printf("All interval series of size %d%n", N);
    for (int i = 0; i < N - 1; i++) {
        System.out.printf("%d <%d> ", 
                            S[i].getValue(), 
                            V[i].getValue());
    }
    System.out.printf("%d", S[N - 1].getValue());
}
*/		
		
		
		
		// mockup
		SolverMockup s = new SolverMockup();
		return s.populate(cmpny);
	}  

	private int calc_dist_score(Space si, Space sj) {
		//TODO: implement
		return 0;
	}

	@Override
	public void set_max_distance( int d ) { _max_dist = d; }
}
