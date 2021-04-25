import java.util.*;
import java.util.stream.Collectors;

public class CSP <V,D> {

    /*
    Lista zmiennych- Variables
    Mapa domen/zbioru możliwych wartości dla zmiennych- Domains
    Mapa ograniczeń dla zmiennych - Constraints
     */
     List<V> variables;
     Map<V, List<D>> domains;
     Map<V, List<Constraint<V, D>>> constraints;

     //////////////////////////////////////////
     ArrayList<Map<V, D>> solutions;
     int number_of_visited_nodes;

     ArrayList<Integer>  list_of_visited_nodes;
     ArrayList<Long> list_of_times;
     long start_time;
     long end_time;
    //////////////////////////////////////////

    /*
    W konstruktorze przypisujemy podane w parametrze zmienne i domeny
    Inicjujemy mapę ograniczeń, tworząc dla każdego klucza zmiennej nową, pustą listę ograniczeń
     */
    public CSP(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;
        constraints = new HashMap<>();
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)) {
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
        //////////////////////////////////////////
        solutions = new ArrayList<>();
        number_of_visited_nodes = 0;

        list_of_visited_nodes= new ArrayList<>();
        list_of_times = new ArrayList<>();
        start_time=System.nanoTime();
        end_time=System.nanoTime();
        //////////////////////////////////////////
    }

    /*
    Metoda dodająca zdefiniowane wcześniej ograniczenie, chyba, że zmienna nie została zdefiniowana w problemie
     */
    public void addConstraint(Constraint<V, D> constraint) {
        for (V variable : constraint.variables) {
            if (!variables.contains(variable)) {
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            } else {
                constraints.get(variable).add(constraint);
            }
        }
    }

    /*
    Metoda consistent sprawdza, czy przypisana wartość z domeny nie jest sprzeczna z ograniczeniami dla podanej zmiennej
     */
    // Check if the value assignment is consistent by checking all constraints
    // for the given variable against it
    public boolean consistent(V variable, Map<V, D> assignment) {
        for (Constraint<V, D> constraint : constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    /*
    Metoda implementująca backtracking
     */
    public void generateBacktrackingSearchResults(Map<V, D> assignment, String way_of_chosing_variable, String way_of_choosing_domain_value) {
        // assignment is complete if every variable is assigned (our base case)
        if(this.solutions.size()==0){
            this.start_time=System.nanoTime();
        }
        if (assignment.size() == variables.size()) {
            this.solutions.add(assignment);

            this.end_time= System.nanoTime();
            long current_duration = this.end_time - this.start_time;
            this.list_of_times.add(current_duration);
            this.list_of_visited_nodes.add(this.number_of_visited_nodes);

            return;
        }
        List<V> variables_to_assign = new ArrayList<>();
        for(V variable : this.variables){
            if(!assignment.containsKey(variable)){
                variables_to_assign.add(variable);
            }
        }
        //V current_variable = variables_to_assign.get(0);
        V current_variable = select_variable(variables_to_assign, way_of_chosing_variable);
        //for(D domain_value : this.domains.get(current_variable)){
        List<D> currently_added_domain_values = new ArrayList<>();
        for(int i=0; i< this.domains.get(current_variable).size();i++){
            D domain_value = select_domain_value(this.domains.get(current_variable), i, way_of_choosing_domain_value, assignment);
            /////////////////////////////////////
            if(currently_added_domain_values.contains(domain_value)){
                while (currently_added_domain_values.contains(domain_value)){
                    domain_value = select_domain_value(this.domains.get(current_variable), i, way_of_choosing_domain_value, assignment);
                }
            }
            else{
                currently_added_domain_values.add(domain_value);
            }
            //////////////////////////////////
            Map<V, D> current_assignment = new HashMap<>(assignment);
            current_assignment.put(current_variable,domain_value);
            this.number_of_visited_nodes+=1;

            if(this.consistent(current_variable,current_assignment)){
                this.generateBacktrackingSearchResults(current_assignment, way_of_chosing_variable, way_of_choosing_domain_value);
            }
        }
    }

    public void generateForwardCheckingSearchResults(Map<V, D> assignment, Map<V, List<D>> domains_copy, String way_of_chosing_variable, String way_of_choosing_domain_value) {
        // assignment is complete if every variable is assigned (our base case)
        if(this.solutions.size()==0){
            this.start_time=System.nanoTime();
        }
        if (assignment.size() == variables.size()) {
            this.solutions.add(assignment);

            this.end_time= System.nanoTime();
            long current_duration = this.end_time - this.start_time;
            this.list_of_times.add(current_duration);
            this.list_of_visited_nodes.add(this.number_of_visited_nodes);

            return;
        }
        List<V> variables_to_assign = new ArrayList<>();
        for(V variable : this.variables){
            if(!assignment.containsKey(variable)){
                variables_to_assign.add(variable);
            }
        }
        // z current domains copy usuwany wartości sprzeczne z aktualnymi przypisaniami
        Map<V, List<D>> current_domains_copy = new HashMap<>(domains_copy);

        //usuwanie z curr domains copy
        if (assignment.size() > 0) {
            for( int i=0; i< variables_to_assign.size();i++){
                V curr_variable = variables_to_assign.get(i);
                List<D> old_domain_values = current_domains_copy.get(curr_variable);
                List<D> currently_added_domain_values = new ArrayList<>();
                for(int j=0;j<old_domain_values.size();j++){
                    D curr_domain_value = select_domain_value(old_domain_values, j, way_of_choosing_domain_value, assignment);
                    /////////////////////////////////////
                    if(currently_added_domain_values.contains(curr_domain_value)){
                        while (currently_added_domain_values.contains(curr_domain_value)){
                            curr_domain_value = select_domain_value(old_domain_values, j, way_of_choosing_domain_value, assignment);
                        }
                    }
                    else{
                        currently_added_domain_values.add(curr_domain_value);
                    }
                    //////////////////////////////////
                    Map<V, D> temp_assignment = new HashMap<>(assignment);
                    temp_assignment.put(curr_variable,curr_domain_value);

                    if(!this.consistent(curr_variable,temp_assignment)){
                        List<D> curr_domain_values = new ArrayList<>(current_domains_copy.get(curr_variable));
                        curr_domain_values.remove(curr_domain_value);
                        //current_domains_copy.remove(curr_variable);
                        current_domains_copy.put(curr_variable,curr_domain_values);
                    }

                }
            }
        }

        //
        //V current_variable = variables_to_assign.get(0);
        V current_variable = select_variable(variables_to_assign, way_of_chosing_variable);
        //for(D domain_value : this.domains.get(current_variable)){
        List<D> currently_added_domain_values = new ArrayList<>();
        for(int i=0; i< current_domains_copy.get(current_variable).size();i++){
            D domain_value = select_domain_value(current_domains_copy.get(current_variable), i, way_of_choosing_domain_value, assignment);
            /////////////////////////////////////
            if(currently_added_domain_values.contains(domain_value)){
                while (currently_added_domain_values.contains(domain_value)){
                    domain_value = select_domain_value(current_domains_copy.get(current_variable), i, way_of_choosing_domain_value, assignment);
                }
            }
            else{
                currently_added_domain_values.add(domain_value);
            }
            //////////////////////////////////
            Map<V, D> current_assignment = new HashMap<>(assignment);
            current_assignment.put(current_variable,domain_value);
            this.number_of_visited_nodes+=1;

            if(this.consistent(current_variable,current_assignment)){
                this.generateForwardCheckingSearchResults(current_assignment, current_domains_copy,way_of_chosing_variable,way_of_choosing_domain_value);
            }
        }
    }

    private D select_domain_value(List<D> ds, int indeks, String way_of_choosing_domain_value, Map<V, D> assignment) {
        if(way_of_choosing_domain_value.equals("pierwsza")) return ds.get(indeks);
        else if(way_of_choosing_domain_value.equals("najmniej_wystepujace")) return least_chosen(ds,assignment,indeks);
        else return null;
    }

    private D least_chosen(List<D> ds, Map<V, D> assignment, int indeks) {
        Map<D,Integer> mapa_wystapien = new HashMap<>();

        for( D curr_domain_value : ds){
            mapa_wystapien.put(curr_domain_value, 0);
        }

        for ( V key : assignment.keySet() ) {
            D curr_var_value = assignment.get(key);
            if(mapa_wystapien.containsKey(curr_var_value)){
                int new_value = mapa_wystapien.get(curr_var_value) + 1;
                mapa_wystapien.put(curr_var_value, new_value);
            }
        }
        Map<D,Integer> sorted_mapa_wystapien = sortByComparator(mapa_wystapien,true);

        List<D> list = new ArrayList<>(sorted_mapa_wystapien.keySet());

        return list.get(indeks);
    }

    private Map<D, Integer> sortByComparator(Map<D, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<D, Integer>> list = new LinkedList<Map.Entry<D, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<D, Integer>>()
        {
            public int compare(Map.Entry<D, Integer> o1,
                               Map.Entry<D, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<D, Integer> sortedMap = new LinkedHashMap<D, Integer>();
        for (Map.Entry<D, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private V select_variable(List<V> variables_to_assign, String way_of_chosing_variable) {
        if(way_of_chosing_variable.equals("pierwsza")) return variables_to_assign.get(0);
        else if(way_of_chosing_variable.equals("najwiecej_oragniczen")) return max_constrained_variable(variables_to_assign);
        else return null;
    }

    private V max_constrained_variable(List<V> variables_to_assign) {
        V variable_to_return = variables_to_assign.get(0);
        int constraints_number = this.constraints.get(variable_to_return).size();
        for(int i=1;i<variables_to_assign.size();i++){
            if(constraints_number<this.constraints.get(variables_to_assign.get(i)).size()){
                variable_to_return = variables_to_assign.get(i);
                constraints_number = this.constraints.get(variables_to_assign.get(i)).size();
            }
        }
        return variable_to_return;
    }

    public void print_generated_results(){
        for(int i=0;i<this.solutions.size();i++){
            System.out.println("________________________________________");
            System.out.println("SOLUTION "+ (i+1)+":\n");
            System.out.println(solutions.get(i));

        }
    }
    public void reset_solutions(){
        this.solutions.clear();
        this.number_of_visited_nodes=0;

        this.list_of_visited_nodes.clear();
        this.list_of_times.clear();
        this.start_time=System.nanoTime();
        this.end_time=System.nanoTime();
    }
    /*
    public Map<V, D> backtrackingSearch(Map<V, D> assignment) {
        // assignment is complete if every variable is assigned (our base case)
        if (assignment.size() == variables.size()) {
            return assignment;
        }
        // get the first variable in the CSP but not in the assignment
        //V unassigned = variables.stream().filter(v -> (!assignment.containsKey(v))).findFirst().get();
        V unassigned = select_variable(variables.stream().filter(v -> (!assignment.containsKey(v))).collect(Collectors.toList()), way_of_chosing_variable);
        // get the every possible domain value of the first unassigned variable

        //for (D value : domains.get(unassigned)) {
        List<D> currently_added_domain_values = new ArrayList<>();
        for (int i=0; i< this.domains.get(unassigned).size();i++) {
            D value = select_domain_value(this.domains.get(unassigned), i);
            /////////////////////////////////////
            if(currently_added_domain_values.contains(value)){
                while (currently_added_domain_values.contains(value)){
                    value = select_domain_value(this.domains.get(unassigned), i);
                }
            }
            else{
                currently_added_domain_values.add(value);
            }
            //////////////////////////////////
            // shallow copy of assignment that we can change
            Map<V, D> localAssignment = new HashMap<>(assignment);
            localAssignment.put(unassigned, value);
            // if we're still consistent, we recurse (continue)
            if (consistent(unassigned, localAssignment)) {
                Map<V, D> result = backtrackingSearch(localAssignment);
                // if we didn't find the result, we will end up backtracking
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    // helper for backtrackingSearch when nothing known yet
    public Map<V, D> backtrackingSearch() {
        return backtrackingSearch(new HashMap<>());
    }

     */
}
