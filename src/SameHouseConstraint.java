import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SameHouseConstraint extends Constraint<String,String> {
    private String first_house_parameter, first_parameter_value;
    private String second_house_parameter, second_parameter_value;

    public SameHouseConstraint(String first_house_parameter, String first_parameter_value, String second_house_parameter, String second_parameter_value) {
        super(new ArrayList<String>());
        //ArrayList<String> params = (ArrayList<String>) Arrays.asList(first_house_parameter,second_house_parameter);
        List<String> params = new ArrayList<>(Arrays.asList(first_house_parameter,second_house_parameter));
        List<String> init = new ArrayList<String>();
        this.first_house_parameter = first_house_parameter;
        this.first_parameter_value = first_parameter_value;
        this.second_house_parameter = second_house_parameter;
        this.second_parameter_value = second_parameter_value;

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
        for(int house_id = 1;house_id<6;house_id++){
            String var1 = String.valueOf(house_id)+"_house_"+first_house_parameter;
            String var2 = String.valueOf(house_id)+"_house_"+second_house_parameter;
            if(assignment.containsKey(var1) && assignment.containsKey(var2)){
                if((assignment.get(var1).equals(this.first_parameter_value) && !assignment.get(var2).equals(this.second_parameter_value))||(!assignment.get(var1).equals(this.first_parameter_value) && assignment.get(var2).equals(this.second_parameter_value))){
                return false;
                }
            }
        }
        return true;
    }
}
