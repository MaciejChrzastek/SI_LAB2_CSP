import java.awt.*;
import java.util.*;
import java.util.List;

public final class MapColoringConstraint extends Constraint<String, String> {
    private String place1, place2;

    public MapColoringConstraint(String place1, String place2) {
        super(Arrays.asList(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        // if either place is not in the assignment, then it is not
        // yet possible for their colors to be conflicting
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
            return true;
        }
        // check the color assigned to place1 is not the same as the
        // color assigned to place2
        return !assignment.get(place1).equals(assignment.get(place2));
    }
    public static void main(String[] args) {
        int number_of_points=5;
        int map_width=5;
        int map_height=5;
        int number_of_colors=3;
        MapProblemGenerator mpg = new MapProblemGenerator(5,5,5);
        mpg.generate_points();
        System.out.println("________________________________________");
        System.out.println("POINTS:");
        mpg.print_points();
        System.out.println("________________________________________");
        System.out.println("CONNECTIONS:");
        mpg.generate_connections_between_points();
        mpg.fix_connections();
        mpg.print_connections();
        /////////////////////////////////////////////////////////////////
        List<String> variables = new ArrayList<String>();
        for(int i=0;i<number_of_points;i++){
            Point current_point = mpg.points.get(i);
            String curr_point_string = "[x="+current_point.x+",y="+current_point.y+"]";
            variables.add(curr_point_string);
        }
        /////////////////////////////////////////////////////////////////
        Map<String, List<String>> domains = new HashMap<>();
        if(number_of_colors==4){
            for (String variable : variables) {
                domains.put(variable, Arrays.asList("red", "green", "blue","yellow"));
            }
        }
        else{
            for (String variable : variables) {
                domains.put(variable, Arrays.asList("red", "green", "blue"));
            }
        }
        /////////////////////////////////////////////////////////////////
        CSP<String, String> csp = new CSP<>(variables, domains);
        for(int i=0;i<mpg.connections.size();i++){
            ArrayList<Point> current_connection = mpg.connections.get(i);
            Point current_point_1 = current_connection.get(0);
            Point current_point_2 = current_connection.get(1);

            String str_point_1  = "[x="+current_point_1.x+",y="+current_point_1.y+"]";
            String str_point_2  = "[x="+current_point_2.x+",y="+current_point_2.y+"]";

            csp.addConstraint(new MapColoringConstraint(str_point_1, str_point_2));
        }
        /*
        Map<String, String> solution = csp.backtrackingSearch();

        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }

         */

        csp.generateBacktrackingSearchResults(new HashMap<>(),"pierwsza","pierwsza");
        System.out.println("BACKTRACK SEARCH: Way of choosing variable: pierwsza, Way of choosing domain value: pierwsza, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateBacktrackingSearchResults(new HashMap<>(),"pierwsza","najmniej_wystepujace");
        System.out.println("BACKTRACK SEARCH: Way of choosing variable: pierwsza, Way of choosing domain value: najmniej_wystepujace, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateBacktrackingSearchResults(new HashMap<>(),"najwiecej_oragniczen","pierwsza");
        System.out.println("BACKTRACK SEARCH: Way of choosing variable: najwiecej_oragniczen, Way of choosing domain value: pierwsza, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateBacktrackingSearchResults(new HashMap<>(),"najwiecej_oragniczen","najmniej_wystepujace");
        System.out.println("BACKTRACK SEARCH: Way of choosing variable: najwiecej_oragniczen, Way of choosing domain value: najmniej_wystepujace, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();

        System.out.println("________________________________________");

        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"pierwsza","pierwsza");
        System.out.println("FORWARD CHECKING: Way of choosing variable: pierwsza, Way of choosing domain value: pierwsza, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"pierwsza","najmniej_wystepujace");
        System.out.println("FORWARD CHECKING: Way of choosing variable: pierwsza, Way of choosing domain value: najmniej_wystepujace, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"najwiecej_oragniczen","pierwsza");
        System.out.println("FORWARD CHECKING: Way of choosing variable: najwiecej_oragniczen, Way of choosing domain value: pierwsza, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"najwiecej_oragniczen","najmniej_wystepujace");
        System.out.println("FORWARD CHECKING: Way of choosing variable: najwiecej_oragniczen, Way of choosing domain value: najmniej_wystepujace, Number of visited nodes: "+csp.number_of_visited_nodes);
        csp.reset_solutions();
        //csp.print_generated_results();

        /*
        List<String> variables = Arrays.asList("Western Australia", "Northern Territory",
                "South Australia", "Queensland", "New South Wales", "Victoria", "Tasmania");

        Map<String, List<String>> domains = new HashMap<>();
        for (String variable : variables) {
            domains.put(variable, Arrays.asList("red", "green", "blue"));
        }
        CSP<String, String> csp = new CSP<>(variables, domains);
        csp.addConstraint(new MapColoringConstraint("Western Australia", "Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Western Australia", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("South Australia", "Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Queensland", "Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Queensland", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("Queensland", "New South Wales"));
        csp.addConstraint(new MapColoringConstraint("New South Wales", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("Victoria", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("Victoria", "New South Wales"));
        csp.addConstraint(new MapColoringConstraint("Victoria", "Tasmania"));
        Map<String, String> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }

         */
    }

}
