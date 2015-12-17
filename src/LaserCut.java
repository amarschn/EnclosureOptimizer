
public class LaserCut implements FabMethod {

    private final String name = "Laser-cut";

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getCost(int quantity, Material material, double mass,
            int time) {
        double materialCostToMachine = material.getMachineCost() * mass;

        switch (material.getName()) {
        case "Steel":
            materialCostToMachine = materialCostToMachine * 10;
            break;
        case "Aluminum":
            materialCostToMachine = materialCostToMachine * 10;
            break;
        case "MDF":
            break;
        case "ABS":
            break;
        default:
            break;
        }

        double timeMultiplier = 10 * (1.0 / time);
        if (quantity < 1) {
            quantity = 1;
        }
        double quantityMultiplier = Math.log(quantity);

        return materialCostToMachine * timeMultiplier * quantityMultiplier;
    }

}
