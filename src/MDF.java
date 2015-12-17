/**
 * Material class
 * 
 * @author drew
 *
 */
public class MDF implements Material {

    private final String name = "MDF";
    private final double massDensity = 800.0;
    private final double costDensity = 2.0;
    private final double thermalConductivity = 0.3;
    private final double yieldStrength = 3.0;
    private final double machineCost = 1.5;

    public MDF() {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getMassDensity() {
        return this.massDensity;
    }

    @Override
    public double getCostDensity() {
        return this.costDensity;
    }

    @Override
    public double getThermalConductivity() {
        return this.thermalConductivity;
    }

    @Override
    public double getYieldStrength() {
        return this.yieldStrength;
    }

    @Override
    public double getMachineCost() {
        return machineCost;
    }

}
