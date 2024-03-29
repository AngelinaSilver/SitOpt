import java.util.Vector;

public class UIBuildingPopulation {

    Seating _seating;
    public UIBuildingPopulation(Seating seating){
        this._seating=seating;
    }
    public void populateOffice(Company currCompany, Company plannedCompany){
        _seating.clearComponents();
        if (plannedCompany == null){
            plannedCompany = currCompany;//they will be one and the same in this case
        }
        Building currBuilding = currCompany._buildings.get(0);//working on a single building for now
        Building plannedBuilding = plannedCompany._buildings.get(0);

        Vector<Space> currSpaces = currBuilding._spaces;
        Vector<Space> plannedSpaces = plannedBuilding._spaces;

        if (currSpaces.size() != plannedSpaces.size()){
            System.out.println("error!! number of spaces shouldnt change");
        }
        int index=0;
        while (index < currBuilding._spaces.size()){
            Space currSpace = currSpaces.get(index);
            Space plannedSpace = plannedSpaces.get(index);

            Vector<Employee> movedEmployees = new Vector<>();
            Vector<Employee> newEmployees = new Vector<>();

            for (int i =0; i< currSpace._seats.size(); i++){
                if (plannedSpace._seats.contains(currSpace._seats.get(i))==false){
                    movedEmployees.add(currSpace._seats.get(i));
                }
            }
            for (int i =0; i<plannedSpace._seats.size(); i++) {
                if (currSpace._seats.contains(plannedSpace._seats.get(i)) == false) {
                    newEmployees.add(plannedSpace._seats.get(i));
                }
            }
            _seating.occupyPlace(currSpace, movedEmployees, newEmployees);

            index++;
        }

    }
}
