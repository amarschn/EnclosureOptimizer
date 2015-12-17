/**
 *
 * All materials will follow this interface.
 * 
 * @author drew
 *
 */
public interface Material {
    public String getName();

    public double getMassDensity();

    public double getCostDensity();

    public double getThermalConductivity();

    public double getYieldStrength();

    public double getMachineCost();
}
