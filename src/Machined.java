
public class Machined implements FabMethod {

    private final String name = "CNC-Machined";

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getCost(int quantity, Material material, double mass,
            int time) {

        double materialCostToMachine = material.getMachineCost() * mass;
        double timeMultiplier = 10 * (1.0 / time);

        if (quantity < 1) {
            quantity = 1;
        }
        double quantityMultiplier = 5 * Math.log(quantity);

        return materialCostToMachine * timeMultiplier * quantityMultiplier;
    }

}
