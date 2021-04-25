import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HousePositionConstraint extends Constraint<String,String> {

    String house_id, parameter, value, variable;

    public HousePositionConstraint(String house_id,String parameter,String value) {
        super(Arrays.asList(house_id+"_house_"+parameter));
        this.house_id = house_id;
        this.parameter=parameter;
        this.value=value;
        this.variable=house_id+"_house_"+parameter;

    }


    @Override
    public boolean satisfied(Map<String, String> assignment) {
        if(assignment.containsKey(this.variable) && !assignment.get(this.variable).equals(this.value)){
            return false;
        }
        return true;
    }
}
