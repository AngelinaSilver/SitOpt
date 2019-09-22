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
	private int space_distance[];
	
	
	public SolverChoco() { _max_dist = 1; }
	
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

		Vector<Integer> seat_to_spaces = new Vector<Integer>();
		
		ArrayList<Space> all_spaces = new ArrayList<Space>(); 
		int seat_id = 0;
		for ( Building b : cmpny._buildings )
			for ( Space s : b._spaces ) {
				int space_id = all_spaces.size();
				all_spaces.add(s);
				int max_seat_id = seat_id + s.get_capacity(); 
				for(int i=0; i<s._seats.size(); i++) {
					Employee e = s._seats.elementAt(i);
					if ( e != null ) {
						int eid = emps_newids.get(e);
						emps_to_seats.setElementAt(seat_id, eid);
					} else {
						seat_to_spaces.add(space_id);
						seat_id++;
					}
					for( ; seat_id < max_seat_id; seat_id++)
						seat_to_spaces.add(space_id);
				}
			}
		
		int emp_free = seat_id - emps_seated - emps_new;
		if ( emp_free < 0 )
			throw new SolverException("NOT ENOUGH SEATS ! (fire some employees)");

		space_distance = new int[all_spaces.size() * all_spaces.size()];
		for ( int i=0; i<all_spaces.size(); i++) {
			Space si = all_spaces.get(i);
			for ( int j=i+1; j<all_spaces.size(); j++) {
				Space sj = all_spaces.get(j);
				int score = calc_dist_score( si, sj);
				space_distance[i*all_spaces.size() + j] = score;
				space_distance[j*all_spaces.size() + i] = score;
			}
		}

		Model model = new Model("Sit Finder");
		// 1.a declare the variables
		int ttl_emps = emps_seated + emps_new;
		IntVar[] seatchange = model.intVarArray("sc", emps_seated, 0, 1, false);
		IntVar[] emp2seat = model.intVarArray("e2s", ttl_emps, 0, seat_id - 1, false);
		IntVar[] emp2space = model.intVarArray("e2s", ttl_emps, 0, all_spaces.size() - 1, false);
		IntVar changeCnt = model.intVar("changes", 0, emps_seated );
		model.allDifferent(emp2seat).post();
		for (int i=0; i<emps_seated; i++)
			seatchange[i].eq( emp2seat[i].eq( emps_to_seats.get(i) ) ).post();
		model.sum(seatchange, "=", changeCnt).post();
		
		int[] seat_to_spaces_int = new int[seat_to_spaces.size()];
		for(int i=0; i<seat_to_spaces.size(); i++)
			seat_to_spaces_int[i] = seat_to_spaces.elementAt(i);
		for( int i =0; i<emp2seat.length; i++) {
			IntVar vseat = emp2seat[i];
			IntVar vspace = emp2space[i];
			model.element(vspace, seat_to_spaces_int, vseat).post();
		}

		for( int i =0; i<cmpny._employes.size(); i++) {
			Employee ei = cmpny._employes.get(i);
			Group gi = ei.get_group();
			if ( gi != null) {
				int new_ei_id = emps_newids.get(ei);
				IntVar ei_space = emp2space[new_ei_id];
				for(int j=i+1; j<cmpny._employes.size(); j++) {
					Employee ej = cmpny._employes.get(j);
					Group gj = ej.get_group();
					if ( gi == gj ) {
						int new_ej_id = emps_newids.get(ej);
						IntVar ej_space = emp2space[new_ej_id];
						// 
						IntVar calced_index = model.intVar("index_"+new_ei_id+"_"+new_ej_id, 0, all_spaces.size()*all_spaces.size() );
						ei_space.mul(all_spaces.size()).add(ej_space).eq(calced_index).post();
						
						IntVar dist_ij = model.intVar("dist_"+new_ei_id+"_"+new_ej_id, 0, _max_dist );
						model.element(dist_ij, space_distance, calced_index).post();
					}
				}
			}
		}

		// 2. Solving part
		Solver solver = model.getSolver();
		
		IntVar[] vs = new IntVar[emp2seat.length+1];
		for( int i =0; i<emp2seat.length; i++)
			vs[i+1] = emp2seat[i];
		vs[0] = changeCnt;
		// 2.a define a search strategy
		solver.setSearch(Search.minDomLBSearch(vs));
		if(solver.solve()) {
			// delete spaces
			for (int i=0; i<cmpny._employes.size(); i++ )
				cmpny._employes.get(i).set_space(null);
			for ( Building b : cmpny._buildings )
				for ( Space s : b._spaces )
					s._seats.clear();
			// reading results
			for ( Employee e : cmpny._employes ) {
				int eid = emps_newids.get(e);
				int sindx = emp2space[eid].getValue();
				System.out.println(eid + " -> " + sindx + " seat = "+emp2seat[eid].getValue());
				Space s = all_spaces.get(sindx);
				e.set_space(s);
				s._seats.add(e);
			}

			System.out.printf("Solving succeed. Changes: %d ", changeCnt.getValue()); 
		} else {
			throw new SolverException("Solved Failed");
		}
		return true;
	}  

	private int calc_dist_score(Space si, Space sj) {
		int x1 = si.get_x() + si.get_w()/2;
		int y1 = si.get_y() + si.get_h()/2;
		
		int x2 = sj.get_x() + sj.get_w()/2;
		int y2 = sj.get_y() + sj.get_h()/2;
		
		
		int xx = (x1 - x2);
		xx = xx * xx;
		
		int yy = (y1 - y2);
		yy = yy * yy;
		
		return (int)Math.sqrt(yy + xx);
	}

	@Override
	public void set_max_distance( int d ) { _max_dist = d; }
}
