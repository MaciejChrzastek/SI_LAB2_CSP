import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LeftRightHouseConstraint extends Constraint<String,String> {
    private String left_house_parameter, left_parameter_value;
    private String right_house_parameter, right_parameter_value;

    public LeftRightHouseConstraint(String left_house_parameter, String left_parameter_value, String right_house_parameter, String right_parameter_value) {
        super(new ArrayList<String>());
        //ArrayList<String> params = (ArrayList<String>) Arrays.asList(left_house_parameter, right_house_parameter);
        List<String> params = new ArrayList<>(Arrays.asList(left_house_parameter,right_house_parameter));
        List<String> init = new ArrayList<String>();
        this.left_house_parameter = left_house_parameter;
        this.left_parameter_value = left_parameter_value;
        this.right_house_parameter = right_house_parameter;
        this.right_parameter_value = right_parameter_value;

        for(int house_id = 1;house_id<6;house_id++){
            for(String p:params){
                String current = String.valueOf(house_id)+"_house_"+p;
                init.add(current);
            }
        }
        this.variables = init;
    }
    @Override
    public boolean satisfied(Map<String, String> assignment) {
        for(int house_id = 1;house_id<5;house_id++){
            String left_var = ""+ house_id +"_house_"+ this.left_house_parameter;
            String right_var = ""+(house_id+1)+"_house_"+ this.right_house_parameter;

            if(assignment.containsKey(left_var) && assignment.containsKey(right_var)){
                if((assignment.get(left_var).equals(this.left_parameter_value) && !assignment.get(right_var).equals(this.right_parameter_value))||(!assignment.get(left_var).equals(this.left_parameter_value) && assignment.get(right_var).equals(this.right_parameter_value))){
                    return false;
                }
            }

        }
        return true;
    }
}
