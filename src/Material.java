
/**
 * 
 * @author drew
 * 
 *         All enclosure materials will be defined by this class
 *
 */
public class Material {

    private String name;
    private double massDensity;
    private double costDensity;
    private double thermalConductivity;

    public Material(String n, double mDensity, double cDensity, double tC) {
        this.name = n;
        this.massDensity = mDensity;
        this.costDensity = cDensity;
        this.thermalConductivity = tC;
    }

    public String getName() {
        return name;
    }

    public double getMassDensity() {
        return massDensity;
    }

    public double getCostDensity() {
        return costDensity;
    }

    public double getThermalConductivity() {
        return thermalConductivity;
    }

}
