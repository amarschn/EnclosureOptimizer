
public class Printed implements FabMethod {

    private final String name = "3D-printed";

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return this.name;
    }

    @Override
    public double getCost(int quantity, Material material, double mass,
            int time) {
        double materialCostToMachine = material.getMachineCost() * mass;

        switch (material.getName()) {
        case "Steel":
            materialCostToMachine = materialCostToMachine * 5;
            break;
        case "Aluminum":
            materialCostToMachine = materialCostToMachine * 5;
            break;
        case "MDF":
            materialCostToMachine = materialCostToMachine * 10;
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
