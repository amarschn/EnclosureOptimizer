import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

/**
 * 
 * @author drew SimpleEnclosure is a formulation of the PCB enclosure design
 *         problem that has been simplified to mass, temperature, and cost
 *         objectives, where each objective is simplified significantly.
 * 
 *
 */

public class EnclosureProblem extends AbstractProblem {

    // Dimensions of the enclosure, these need to be encapsulated in a separate
    // class
    public final double ENC_HEIGHT = 0.01;
    public final double ENC_LENGTH = 0.05;
    public final double ENC_WIDTH = 0.02;
    public final double OUTER_VOLUME = ENC_HEIGHT * ENC_LENGTH * ENC_WIDTH;
    public final double POWER = 10;

    public final Material[] MATERIALS = new Material[] {
            new Material("Aluminum", 2700., 5., 205),
            new Material("ABS", 1420., 3., 0.23),
            new Material("MDF", 800., 2., 0.3),
            new Material("Carbon Steel", 7850., 8., 58) };

    public EnclosureProblem() {
        // Decision variables are wall thickness and material
        // Objectives are Mass, Temperature, and Cost
        super(2, 3);
    }

    @Override
    public void evaluate(Solution solution) {
        // Initialize the objectives
        double mass = 0.0;
        double temperature = 0.0;
        double cost = 0.0;

        // Get the values for the decision variables (magic numbers are the
        // indices in the decision variable array of the solution
        double thickness = EncodingUtils.getReal(solution.getVariable(0));
        int material = EncodingUtils.getInt(solution.getVariable(1));

        // This is the inner void subtracted from the outer volume
        double V = this.OUTER_VOLUME - (this.ENC_HEIGHT - thickness)
                * (this.ENC_WIDTH - thickness) * (this.ENC_LENGTH - thickness);

        // Evaluate mass
        mass = V * this.MATERIALS[material].getMassDensity();

        // Evaluate temperature
        temperature = 25.0 + (this.POWER * thickness)
                / (this.MATERIALS[material].getThermalConductivity()
                        * this.ENC_WIDTH * this.ENC_LENGTH);

        // Evaluate cost
        cost = mass * this.MATERIALS[material].getCostDensity();

        // Set the objectives for the evaluation
        solution.setObjectives(new double[] { mass, temperature, cost });
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, numberOfObjectives);

        // Set each of the decision variables according to their bounds
        for (int i = 0; i < numberOfVariables; i++) {

            // Wall thickness
            if (i == 0) {
                // Set bounds to be real values from 0.001m to 0.1m
                solution.setVariable(i, new RealVariable(0.001, 0.1));
            }
            // Material
            else if (i == 1) {
                // Set bounds to be from 0 to the length of the material list
                solution.setVariable(i,
                        EncodingUtils.newInt(0, MATERIALS.length - 1));
            }
        }

        return solution;
    }

}
