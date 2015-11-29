
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

/**
 * Demonstrates using an Executor to solve the UF1 test problem with NSGA-II,
 * one of the most widely-used multiobjective evolutionary algorithms.
 */
public class Test {

    public static void main(String[] args) {
        // configure and run this experiment
        NondominatedPopulation result = new Executor()
                .withProblemClass(EnclosureProblem.class)
                .withAlgorithm("NSGAII").withMaxEvaluations(10000)
                .distributeOnAllCores().run();

        // display the results
        System.out.format("Mass     Temperature    Cost%n");

        for (Solution solution : result) {
            System.out.format("%.4f      %.4f      %.4f%n",
                    solution.getObjective(0), solution.getObjective(1),
                    solution.getObjective(2));
        }

        for (Solution solution : result) {
            System.out.println(EncodingUtils.getInt(solution.getVariable(1)));
        }
    }

}
