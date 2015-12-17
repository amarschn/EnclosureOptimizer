/**
 * Material class
 * 
 * @author drew
 *
 */
public class Aluminum implements Material {

    private final String name = "Aluminum";
    private final double massDensity = 2700.0;
    private final double costDensity = 5.0;
    private final double thermalConductivity = 205.0;
    private final double yieldStrength = 10.0;
    private final double machineCost = 1.0;

    public Aluminum() {

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
