import java.io.File;

public class MainPanel {

    static Company company;
    static SolverIntf solver;

    public static void main(final String[] args){


        Seating seating = new Seating();
        seating.initPanel();
        company = SimpleExample.create_company1();
        solver = new SolverChoco();
        solver.set_max_distance(500);
        UIBuildingPopulation ui = new UIBuildingPopulation(seating);
        ui.populateOffice(company,null);



    }

    private static void readJson(String fileName){
        File jsonFile = new File(fileName);

        if(jsonFile == null) {
            //error!
            return;
        }
        if(!jsonFile.exists()) {
            //error
            return;
        }

//        Reader fileReader = readZipFile(fileName, NUM_OF_FILE_READ_RETRIES);
//            if(fileReader != null) {
        //ObjectMapper mapper = new ObjectMapper();
//                mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
//                return mapper.readValue(fileReader, cls);
//            }
//            //      GUFIUI.showMessageDialog(GUFIMainWindow.INSTANCE, "Cannot read file: " + fileName, "Cannot read file", JOptionPane.ERROR_MESSAGE);
//            logger.error("Cannot read file: " + fileName);
//
//            return (T)Class.forName(cls.getName()).getConstructor().newInstance();
        //}
    }

}
