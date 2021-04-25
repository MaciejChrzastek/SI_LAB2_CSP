import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NeighbourHouseConstraint extends Constraint<String,String> {
    private String current_house_parameter, current_parameter_value;
    private String neighbour_house_parameter, neighbour_parameter_value;

    public NeighbourHouseConstraint(String current_house_parameter, String current_parameter_value, String neighbour_house_parameter, String neighbour_parameter_value) {
        super(new ArrayList<String>());
        //ArrayList<String> params = (ArrayList<String>) Arrays.asList(current_house_parameter, neighbour_house_parameter);
        List<String> params = new ArrayList<>(Arrays.asList(current_house_parameter,neighbour_house_parameter));

        List<String> init = new ArrayList<String>();
        this.current_house_parameter = current_house_parameter;
        this.current_parameter_value = current_parameter_value;
        this.neighbour_house_parameter = neighbour_house_parameter;
        this.neighbour_parameter_value = neighbour_parameter_value;

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
            String current_value = String.valueOf(house_id)+"_house_"+this.current_house_parameter;
            String previous_variable = null;
            String next_variable = null;

            if(house_id>1){
                previous_variable = String.valueOf(house_id-1)+"_house_"+this.neighbour_house_parameter;
            }
            if(house_id<5){
                next_variable = String.valueOf(house_id+1)+"_house_"+this.neighbour_house_parameter;
            }

            if(assignment.containsKey(current_value)){
                if(1<house_id && house_id<5 && assignment.containsKey(previous_variable) && assignment.containsKey(next_variable)){
                    if(assignment.get(current_value).equals(this.current_parameter_value) && !assignment.get(previous_variable).equals(this.neighbour_parameter_value) && !assignment.get(next_variable).equals(this.neighbour_parameter_value)){
                        return false;
                    }

                }
                else if(house_id==1 &&assignment.containsKey(next_variable)){
                    if(assignment.get(current_value).equals(this.current_parameter_value) && !assignment.get(next_variable).equals(this.neighbour_parameter_value)){
                        return false;
                    }

                }
                else if(house_id==5 && assignment.containsKey(previous_variable)){
                    if(assignment.get(current_value).equals(this.current_parameter_value) && !assignment.get(previous_variable).equals(this.neighbour_parameter_value)){
                        return false;
                    }
                }
            }

        }
        return true;
    }
}
