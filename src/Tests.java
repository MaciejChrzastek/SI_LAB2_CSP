import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class Tests {

    public static void main(String[] args) {
        //BADANIE HEURYSTYK - wezly
        ArrayList<Integer> BT_WEZLY_PIERWSZA_PIERWSZA = new ArrayList<>();
        ArrayList<Integer> BT_WEZLY_PIERWSZA_NW = new ArrayList<>();
        ArrayList<Integer> BT_WEZLY_NO_PIERWSZA = new ArrayList<>();
        ArrayList<Integer> BT_WEZLY_NO_NW = new ArrayList<>();

        ArrayList<Integer> FC_WEZLY_PIERWSZA_PIERWSZA = new ArrayList<>();
        ArrayList<Integer> FC_WEZLY_PIERWSZA_NW = new ArrayList<>();
        ArrayList<Integer> FC_WEZLY_NO_PIERWSZA = new ArrayList<>();
        ArrayList<Integer> FC_WEZLY_NO_NW = new ArrayList<>();

        /////////////////////////////////////////////////////////////////
        //BADANIE HEURYSTYK CZASY
        ArrayList<Long> BT_CZASY_PIERWSZA_PIERWSZA = new ArrayList<>();
        ArrayList<Long> BT_CZASY_PIERWSZA_NW = new ArrayList<>();
        ArrayList<Long> BT_CZASY_NO_PIERWSZA = new ArrayList<>();
        ArrayList<Long> BT_CZASY_NO_NW = new ArrayList<>();

        ArrayList<Long> FC_CZASY_PIERWSZA_PIERWSZA = new ArrayList<>();
        ArrayList<Long> FC_CZASY_PIERWSZA_NW = new ArrayList<>();
        ArrayList<Long> FC_CZASY_NO_PIERWSZA = new ArrayList<>();
        ArrayList<Long> FC_CZASY_NO_NW = new ArrayList<>();

        /////////////////////////////////////////////////////////////////////////////

        ArrayList<Integer> rozmiary = new ArrayList<>();
        rozmiary.add(2);
        rozmiary.add(3);
        rozmiary.add(4);
        rozmiary.add(6);
        rozmiary.add(8);
        rozmiary.add(9);
        rozmiary.add(10);
        rozmiary.add(12);
        rozmiary.add(13);
        rozmiary.add(14);

        //////////////////////////////////////////////////////////////////////////
        //
        for (int i=0;i<rozmiary.size();i++){
            ArrayList<Integer> wezly_wyniki = generate_nodes_results(rozmiary.get(i));
            BT_WEZLY_PIERWSZA_PIERWSZA.add(wezly_wyniki.get(0));
            BT_WEZLY_PIERWSZA_NW.add(wezly_wyniki.get(1));
            BT_WEZLY_NO_PIERWSZA.add(wezly_wyniki.get(2));
            BT_WEZLY_NO_NW.add(wezly_wyniki.get(3));

            FC_WEZLY_PIERWSZA_PIERWSZA.add(wezly_wyniki.get(4));
            FC_WEZLY_PIERWSZA_NW.add(wezly_wyniki.get(5));
            FC_WEZLY_NO_PIERWSZA.add(wezly_wyniki.get(6));
            FC_WEZLY_NO_NW.add(wezly_wyniki.get(7));

            ArrayList<Long> czasy_wyniki = generate_times_results(rozmiary.get(i));
            BT_CZASY_PIERWSZA_PIERWSZA.add(czasy_wyniki.get(0));
            BT_CZASY_PIERWSZA_NW.add(czasy_wyniki.get(1));
            BT_CZASY_NO_PIERWSZA.add(czasy_wyniki.get(2));
            BT_CZASY_NO_NW.add(czasy_wyniki.get(3));

            FC_CZASY_PIERWSZA_PIERWSZA.add(czasy_wyniki.get(4));
            FC_CZASY_PIERWSZA_NW.add(czasy_wyniki.get(5));
            FC_CZASY_NO_PIERWSZA.add(czasy_wyniki.get(6));
            FC_CZASY_NO_NW.add(czasy_wyniki.get(7));
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////
        //mamy wygenerowane wszystkie wyniki do porównywania heurystyk, teraz je zapisujemy do pliku xcf
        String wezly_wyniki_do_zapisu = "";
        wezly_wyniki_do_zapisu += "BT_WEZLY_PIERWSZA_PIERWSZA,"+ zwroc_wiersz_wezly(BT_WEZLY_PIERWSZA_PIERWSZA)+"\n";
        wezly_wyniki_do_zapisu += "BT_WEZLY_PIERWSZA_NW,"+ zwroc_wiersz_wezly(BT_WEZLY_PIERWSZA_NW)+"\n";
        wezly_wyniki_do_zapisu += "BT_WEZLY_NO_PIERWSZA,"+ zwroc_wiersz_wezly(BT_WEZLY_NO_PIERWSZA)+"\n";
        wezly_wyniki_do_zapisu += "BT_WEZLY_NO_NW,"+ zwroc_wiersz_wezly(BT_WEZLY_NO_NW)+"\n";

        wezly_wyniki_do_zapisu += "FC_WEZLY_PIERWSZA_PIERWSZA,"+ zwroc_wiersz_wezly(FC_WEZLY_PIERWSZA_PIERWSZA)+"\n";
        wezly_wyniki_do_zapisu += "FC_WEZLY_PIERWSZA_NW,"+ zwroc_wiersz_wezly(FC_WEZLY_PIERWSZA_NW)+"\n";
        wezly_wyniki_do_zapisu += "FC_WEZLY_NO_PIERWSZA,"+ zwroc_wiersz_wezly(FC_WEZLY_NO_PIERWSZA)+"\n";
        wezly_wyniki_do_zapisu += "FC_WEZLY_NO_NW,"+ zwroc_wiersz_wezly(FC_WEZLY_NO_NW)+"\n";

        zapisz_wyniki_do_pliku("wezly_wyniki.csv",wezly_wyniki_do_zapisu);

        String czasy_wyniki_do_zapisu = "";
        czasy_wyniki_do_zapisu += "BT_CZASY_PIERWSZA_PIERWSZA,"+ zwroc_wiersz_czasy(BT_CZASY_PIERWSZA_PIERWSZA)+"\n";
        czasy_wyniki_do_zapisu += "BT_CZASY_PIERWSZA_NW,"+ zwroc_wiersz_czasy(BT_CZASY_PIERWSZA_NW)+"\n";
        czasy_wyniki_do_zapisu += "BT_CZASY_NO_PIERWSZA,"+ zwroc_wiersz_czasy(BT_CZASY_NO_PIERWSZA)+"\n";
        czasy_wyniki_do_zapisu += "BT_CZASY_NO_NW,"+ zwroc_wiersz_czasy(BT_CZASY_NO_NW)+"\n";

        czasy_wyniki_do_zapisu += "FC_CZASY_PIERWSZA_PIERWSZA,"+ zwroc_wiersz_czasy(FC_CZASY_PIERWSZA_PIERWSZA)+"\n";
        czasy_wyniki_do_zapisu += "FC_CZASY_PIERWSZA_NW,"+ zwroc_wiersz_czasy(FC_CZASY_PIERWSZA_NW)+"\n";
        czasy_wyniki_do_zapisu += "FC_CZASY_NO_PIERWSZA,"+ zwroc_wiersz_czasy(FC_CZASY_NO_PIERWSZA)+"\n";
        czasy_wyniki_do_zapisu += "FC_CZASY_NO_NW,"+ zwroc_wiersz_czasy(FC_CZASY_NO_NW)+"\n";

        zapisz_wyniki_do_pliku("czasy_wyniki.csv",czasy_wyniki_do_zapisu);

    }

    private static String zwroc_wiersz_czasy(ArrayList<Long> lista_wynikow) {
        String result = "";
        for(int i=0;i<lista_wynikow.size();i++){
            result += lista_wynikow.get(i) + ",";
        }
        return result;
    }

    private static String zwroc_wiersz_wezly(ArrayList<Integer> lista_wynikow) {
        String result = "";
        for(int i=0;i<lista_wynikow.size();i++){
            result += lista_wynikow.get(i) + ",";
        }
        return result;
    }

    public static void zapisz_wyniki_do_pliku(String nazwa, String napis){
        try (PrintWriter writer = new PrintWriter(new File(nazwa))) {

            StringBuilder sb = new StringBuilder();
            writer.write(napis);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList<Integer> generate_nodes_results(int number_of_points) {
        int map_width=20;
        int map_height=10;
        int number_of_colors=3;
        MapProblemGenerator mpg = new MapProblemGenerator(number_of_points,map_width,map_height);
        mpg.generate_points();
        mpg.generate_connections_between_points();
        mpg.fix_connections();
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

        ArrayList<Integer> results = new ArrayList<>();

        csp.generateBacktrackingSearchResults(new HashMap<>(),"pierwsza","pierwsza");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateBacktrackingSearchResults(new HashMap<>(),"pierwsza","najmniej_wystepujace");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateBacktrackingSearchResults(new HashMap<>(),"najwiecej_oragniczen","pierwsza");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateBacktrackingSearchResults(new HashMap<>(),"najwiecej_oragniczen","najmniej_wystepujace");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();


        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"pierwsza","pierwsza");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"pierwsza","najmniej_wystepujace");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"najwiecej_oragniczen","pierwsza");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();

        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"najwiecej_oragniczen","najmniej_wystepujace");
        results.add(csp.number_of_visited_nodes);
        csp.reset_solutions();

        return results;
    }

    private static ArrayList<Long> generate_times_results(int number_of_points) {
        int map_width=20;
        int map_height=10;
        int number_of_colors=3;
        MapProblemGenerator mpg = new MapProblemGenerator(number_of_points,map_width,map_height);
        mpg.generate_points();
        mpg.generate_connections_between_points();
        mpg.fix_connections();
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

        ArrayList<Long> results = new ArrayList<>();

        Long start_time = System.nanoTime();
        csp.generateBacktrackingSearchResults(new HashMap<>(),"pierwsza","pierwsza");
        Long end_time = System.nanoTime();
        Long current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();

        start_time = System.nanoTime();
        csp.generateBacktrackingSearchResults(new HashMap<>(),"pierwsza","najmniej_wystepujace");
        end_time = System.nanoTime();
        current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();

        start_time = System.nanoTime();
        csp.generateBacktrackingSearchResults(new HashMap<>(),"najwiecej_oragniczen","pierwsza");
        end_time = System.nanoTime();
        current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();

        start_time = System.nanoTime();
        csp.generateBacktrackingSearchResults(new HashMap<>(),"najwiecej_oragniczen","najmniej_wystepujace");
        end_time = System.nanoTime();
        current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();


        start_time = System.nanoTime();
        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"pierwsza","pierwsza");
        end_time = System.nanoTime();
        current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();

        start_time = System.nanoTime();
        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"pierwsza","najmniej_wystepujace");
        end_time = System.nanoTime();
        current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();

        start_time = System.nanoTime();
        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"najwiecej_oragniczen","pierwsza");
        end_time = System.nanoTime();
        current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();

        start_time = System.nanoTime();
        csp.generateForwardCheckingSearchResults(new HashMap<>(),csp.domains,"najwiecej_oragniczen","najmniej_wystepujace");
        end_time = System.nanoTime();
        current_time = end_time-start_time;
        results.add(current_time);
        csp.reset_solutions();

        return results;
    }
}
