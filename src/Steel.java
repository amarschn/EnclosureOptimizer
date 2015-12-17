/**
 * Material class
 * 
 * @author drew
 *
 */
public class Steel implements Material {

    private final String name = "Steel";
    private final double massDensity = 7850.0;
    private final double costDensity = 8.0;
    private final double thermalConductivity = 58.0;
    private final double yieldStrength = 20.0;
    private final double machineCost = 1.2;

    public Steel() {

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
