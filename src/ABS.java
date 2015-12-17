/**
 * Material class
 * 
 * @author drew
 *
 */
public class ABS implements Material {

    private final String name = "ABS";
    private final double massDensity = 1420.0;
    private final double costDensity = 3.0;
    private final double thermalConductivity = 0.23;
    private final double yieldStrength = 5.0;
    private final double machineCost = 1.0;

    public ABS() {

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
