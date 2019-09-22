/*
 * Copyright (C) 2017 COSLING S.A.S.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Simple Hello World in Choco Solver
 * @author Jean-Guillaume FAGES (cosling)
 * @version choco-solver-4.0.4
 */
public class HelloWorld {

//    static Company the_company;

    public static void main(String[] args) {

        final String xmlFilePath = "C:\\test\\SipOpt.xml";
/*
        Solver s = new Solver();
    
        Company c = SimpleExample.create_company();
        
        try
        {
        	s.populate(c);
        }
        catch(SolverException e)
        {
            e.printStackTrace();
        }
        
        SimpleExample.save_to_xml(xmlFilePath, c);
*/
        // read the xml to company c2
        Company c2 = SimpleExample.load_xml(xmlFilePath);
        final String xmlFilePath2 = "C:\\test\\SipOpt2.xml";
        SimpleExample.save_to_xml(xmlFilePath2, c2);

    }
}
            
