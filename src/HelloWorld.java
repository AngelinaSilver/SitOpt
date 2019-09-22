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

/**
 * Simple Hello World in Choco Solver
 * @author Jean-Guillaume FAGES (cosling)
 * @version choco-solver-4.0.4
 */
public class HelloWorld {

    public static void main(String[] args) {
       // The model is the main component of Choco Solver
       Model model = new Model("Choco Solver Hello World xxx");
       // Integer variables
       IntVar a = model.intVar("a", new int[]{4, 6, 8}); // takes value in { 4, 6, 8 }
       IntVar b = model.intVar("b", 0, 2); // takes value in [0, 2]

       // Add an arithmetic constraint between a and b
       // BEWARE : do not forget to call post() to force this constraint to be satisfied
       model.arithm(a, "+", b, "<", 8).post();

       int i = 1;
       // Computes all solutions : Solver.solve() returns true whenever a new feasible solution has been found
       while (model.getSolver().solve()) {
          System.out.println("Solution " + i++ + " found : " + a + ", " + b);
       }
   }
}
            
