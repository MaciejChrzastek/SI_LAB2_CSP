import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AllFeaturesDifferentConstraint extends Constraint<String,String> {


    public AllFeaturesDifferentConstraint(List<String> variables) {
        super(variables);
        this.variables = variables;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        ArrayList<String> found_feature_values = new ArrayList<>();
        for(int i=0;i<this.variables.size();i++){
            String variable = this.variables.get(i);
            if(assignment.containsKey(variable)){
                boolean contains_curr_value = false;
                String curr_value = assignment.get(variable);
                for(int j=0;j<found_feature_values.size();j++){
                    if(found_feature_values.get(j).equals(curr_value)){
                        contains_curr_value = true;
                    }
                }
                if(contains_curr_value){
                    return false;
                }
                else {
                    found_feature_values.add(curr_value);
                }
            }
        }
        return true;
    }

     /*
     private String parameter1, parameter2;

    public AllFeaturesDifferentConstraint(String parameter1, String parameter2) {
        super(new ArrayList<String>());
        //ArrayList<String> params = (ArrayList<String>) Arrays.asList(first_house_parameter,second_house_parameter);
        List<String> params = new ArrayList<>(Arrays.asList(parameter1,parameter2));
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;


        this.variables = params;


    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        // if either place is not in the assignment, then it is not
        // yet possible for their colors to be conflicting
        if (!assignment.containsKey(parameter1) || !assignment.containsKey(parameter2)) {
            return true;
        }
        // check the color assigned to place1 is not the same as the
        // color assigned to place2
        return !assignment.get(parameter1).equals(assignment.get(parameter2));
    }
      */
}
