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

    /*
     * General constants
     */

    public final double LI_SPECIFIC_ENERGY = 100.0; // W*h/kg
    public final double LI_ENERGY_DENSITY = 500000.0; // W*h/m^3
    public final double AMBIENT_TEMP = 25.0; // celsius
    public final double LI_COST_DENSITY = 2.5; // W*h/$

    /*
     * User-set variables
     */
    // Dimensions of the enclosure
    public final double ENC_HEIGHT = 0.01;
    public final double ENC_LENGTH = 0.05;
    public final double ENC_WIDTH = 0.02;
    public final double ENC_OUTER_VOLUME = ENC_HEIGHT * ENC_LENGTH * ENC_WIDTH;

    // Dimensions of the PCB
    public final double PCB_HEIGHT = 0.0015;
    public final double PCB_LENGTH = 0.010;
    public final double PCB_WIDTH = 0.015;
    public final double PCB_VOLUME = PCB_HEIGHT * PCB_LENGTH * PCB_WIDTH;
    public final double PCB_MASS = PCB_VOLUME * 1850; // 1850 is density of FR4

    public final boolean MOBILE = true;
    public final int ENC_QUANTITY = 100;

    /*
     * Materials that are allowed
     */
    public final Material[] MATERIALS = new Material[] { new Aluminum(),
            new ABS(), new MDF(), new Steel() };

    public final FabMethod[] FAB_METHODS = new FabMethod[] { new Machined(),
            new Printed(), new LaserCut(), new InjectionMolded() };

    /*
     * Different indices used to select different parameter variables
     */
    private int encMaterialIndex = 0;
    private int encFabMethodIndex = 1;
    private int encFabTimeIndex = 2;
    private int encWallThicknessIndex = 3;
    private int powerConsumptionIndex = 4;
    private int batteryLifeIndex = 5;

    public EnclosureProblem() {
        // Decision variables are wall thickness and material
        // Objectives are Mass, Temperature, and Cost
        super(6, 7);
    }

    @Override
    public void evaluate(Solution solution) {
        // Initialize the objectives
        double totalMass = 0.0;
        double surfaceTemperature = 0.0;
        double totalCost = 0.0;
        double operationTime = 0.0;
        double computationAbility = 0.0;
        double durability = 0.0;
        double marketAdvantage = 0.0;

        /*
         * Get the values for the decision variables
         */

        // thickness is in millimeters
        double encWallThickness = EncodingUtils
                .getReal(solution.getVariable(encWallThicknessIndex));

        // material is one of 4 types
        int encMaterial = EncodingUtils
                .getInt(solution.getVariable(encMaterialIndex));

        // fabrication method is one of 3 types
        int encFabMethod = EncodingUtils
                .getInt(solution.getVariable(encFabMethodIndex));

        // manufacturing time is in days
        int encFabTime = EncodingUtils
                .getInt(solution.getVariable(encFabTimeIndex));

        // power consumption is in watts
        double powerConsumption = EncodingUtils
                .getReal(solution.getVariable(powerConsumptionIndex));

        /*
         * Perform needed calculations based on decision variables
         */
        // This is the inner void subtracted from the outer volume
        double materialVolume = this.ENC_OUTER_VOLUME
                - (this.ENC_HEIGHT - encWallThickness)
                        * (this.ENC_WIDTH - encWallThickness)
                        * (this.ENC_LENGTH - encWallThickness);

        double materialMass = materialVolume
                * this.MATERIALS[encMaterial].getMassDensity();

        // cost of the material
        double materialCost = materialMass
                * this.MATERIALS[encMaterial].getCostDensity() * ENC_QUANTITY;

        // cost of fabrication
        double fabCost = this.FAB_METHODS[encFabMethod].getCost(ENC_QUANTITY,
                this.MATERIALS[encMaterial], materialMass, encFabTime);

        /*
         * Set the objective functions
         */

        // Evaluate temperature
        surfaceTemperature = AMBIENT_TEMP
                + (powerConsumption * encWallThickness)
                        / (this.MATERIALS[encMaterial].getThermalConductivity()
                                * this.ENC_WIDTH * this.ENC_LENGTH);

        // Computation ability is a function of power consumption and we
        // want to maximize it, so we use a negative sign
        computationAbility = powerConsumption;

        // Durability is a function of wall thickness and material. We want to
        // maximize it.
        durability = this.MATERIALS[encMaterial].getYieldStrength()
                * encWallThickness;

        // Market advantage is a function related to the quantity of
        // units and time of fabrication.
        marketAdvantage = ENC_QUANTITY / encFabTime;

        if (MOBILE) {
            // battery life is in watt-hours
            double batteryLife = EncodingUtils
                    .getReal(solution.getVariable(batteryLifeIndex));
            // the mass of the battery is a function of battery life
            double batteryMass = batteryLife / LI_SPECIFIC_ENERGY;
            // cost of the battery
            double batteryCost = batteryLife / LI_COST_DENSITY;
            // Evaluate total mass
            totalMass = materialMass + PCB_MASS + batteryMass;
            // Operation time (we want to maximize this, so we use a negative
            // sign)
            operationTime = batteryLife / powerConsumption;
            // Evaluate cost
            totalCost = materialCost + fabCost + batteryCost;
            // Set the objectives for the evaluation
            solution.setObjectives(new double[] { totalMass, surfaceTemperature,
                    totalCost, -operationTime, -computationAbility, -durability,
                    -marketAdvantage });
        } else {
            // Evaluate total mass
            totalMass = materialMass + PCB_MASS;
            // Evaluate cost
            totalCost = materialCost + fabCost;
            // Set the objectives for the evaluation
            solution.setObjectives(new double[] { totalMass, surfaceTemperature,
                    totalCost, -computationAbility, -durability,
                    -marketAdvantage });
        }
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, numberOfObjectives);
        // different kinds of materials
        solution.setVariable(encMaterialIndex,
                EncodingUtils.newInt(0, MATERIALS.length - 1));
        // different kinds of fab methods
        solution.setVariable(encFabMethodIndex,
                EncodingUtils.newInt(0, FAB_METHODS.length - 1));
        // goes from 1 to 10 days
        solution.setVariable(encFabTimeIndex, EncodingUtils.newInt(1, 10));

        // thickness in meters
        solution.setVariable(encWallThicknessIndex,
                new RealVariable(0.001, 0.1));
        // power consumption in watts
        solution.setVariable(powerConsumptionIndex,
                new RealVariable(0.001, 1000.0));

        if (MOBILE) {
            double batteryLimit = LI_ENERGY_DENSITY * ENC_OUTER_VOLUME;
            // W-hrs of battery life.
            solution.setVariable(batteryLifeIndex,
                    new RealVariable(0.001, batteryLimit));
        }

        return solution;
    }
}
