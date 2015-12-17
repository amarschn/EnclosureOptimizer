
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

/**
 * Demonstrates using an Executor to solve the UF1 test problem with NSGA-II,
 * one of the most widely-used multiobjective evolutionary algorithms.
 */
public class EnclosureOptimizer {

    public static void main(String[] args) {
        // configure and run this experiment
        NondominatedPopulation results = new Executor()
                .withProblemClass(EnclosureProblem.class)
                .withAlgorithm("NSGAII").withMaxEvaluations(50000)
                .distributeOnAllCores().run();
        /*
         * System.out.println(
         * "Enclosure Material, Enclosure Fabrication Method, Enclosure Fabrication Time, Enclosure Wall Thickness, Power Consumption, Battery Life"
         * );
         */
        try {
            File objectivesFile = new File(
                    "/home/drew/Dropbox/CMU/Classes/16615C/proj2/mobile_objectives.csv");
            File variablesFile = new File(
                    "/home/drew/Dropbox/CMU/Classes/16615C/proj2/mobile_variables.csv");
            if (!objectivesFile.exists()) {
                objectivesFile.createNewFile();
            }
            if (!variablesFile.exists()) {
                variablesFile.createNewFile();
            }

            FileWriter objFw = new FileWriter(objectivesFile.getAbsoluteFile());
            BufferedWriter objBw = new BufferedWriter(objFw);

            FileWriter varFw = new FileWriter(variablesFile.getAbsoluteFile());
            BufferedWriter varBw = new BufferedWriter(varFw);

            objBw.write(
                    "Total Mass, Surface Temperature, Total Cost, Operation Time, Computation Ability, Durability, Market Advantage\n");
            varBw.write(
                    "Enclosure Material, Enclosure Fabrication Method, Enclosure Fabrication Time, Enclosure Wall Thickness, Power Consumption, Battery Life\n");

            for (int i = 0; i < results.size(); i++) {
                Solution solution = results.get(i);

                String objectives = String.format(
                        "%.4f, %.4f, %.4f, %.4f, %.4f, %.4f, %.4f\n", //
                        solution.getObjective(0), //
                        solution.getObjective(1), //
                        solution.getObjective(2), //
                        solution.getObjective(3), //
                        solution.getObjective(4), //
                        solution.getObjective(5), //
                        solution.getObjective(6));

                int matIndex = EncodingUtils.getInt(solution.getVariable(0));
                int fabIndex = EncodingUtils.getInt(solution.getVariable(1));

                String material = "Unknown";
                String fab = "Unknown";

                switch (matIndex) {
                case 0:
                    material = "Aluminum";
                    break;
                case 1:
                    material = "ABS";
                    break;
                case 2:
                    material = "MDF";
                    break;
                case 3:
                    material = "Steel";
                    break;
                default:
                    break;
                }

                switch (fabIndex) {
                case 0:
                    fab = "CNC-Machined";
                    break;
                case 1:
                    fab = "3D-Printed";
                    break;
                case 2:
                    fab = "Laser-cut";
                    break;
                case 3:
                    fab = "Injection-molded";
                    break;
                default:
                    break;
                }

                String variables = String.format(
                        "%s, %s, %d, %.4f, %.4f, %.4f\n", //
                        material, //
                        fab, //
                        EncodingUtils.getInt(solution.getVariable(2)),
                        EncodingUtils.getReal(solution.getVariable(3)),
                        EncodingUtils.getReal(solution.getVariable(4)),
                        EncodingUtils.getReal(solution.getVariable(5)));

                objBw.write(objectives);
                varBw.write(variables);
            }

            objBw.close();
            varBw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
