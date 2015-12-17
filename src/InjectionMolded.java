
public class InjectionMolded implements FabMethod {

    private final String name = "Injection-molded";

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
            materialCostToMachine = materialCostToMachine * 5;
            break;
        case "Aluminum":
            materialCostToMachine = materialCostToMachine * 5;
            break;
        case "MDF":
            // this should never happen because injection molding MDF is
            // impossible (I think)
            materialCostToMachine = materialCostToMachine * 100;
            break;
        case "ABS":
            break;
        default:
            break;
        }

        double timeMultiplier = 15 * (1.0 / time);
        if (quantity < 1) {
            quantity = 1;
        }
        double quantityMultiplier = Math.log(quantity);

        int toolingCost = 5000;

        return materialCostToMachine * timeMultiplier * quantityMultiplier
                + toolingCost; // take into account fixed tooling cost
    }

}
