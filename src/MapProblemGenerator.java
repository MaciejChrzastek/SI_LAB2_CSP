import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapProblemGenerator {

    int number_of_points;
    int map_width;
    int map_height;

    ArrayList<Point> points;

    private Random rand = new Random();
    public ArrayList<ArrayList<Point>> connections;

    public MapProblemGenerator(int number_of_points, int map_width, int map_height) {
        this.number_of_points = number_of_points;
        this.map_width = map_width;
        this.map_height = map_height;

        this.points = new ArrayList<>();
        this.connections = new ArrayList<ArrayList<Point>>();
    }

    public void generate_points(){
        int iterator=0;
        while(iterator<this.number_of_points){
            boolean point_already_exists = false;
            Point new_point = new Point(rand.nextInt(map_width),rand.nextInt(map_height));
            if(this.points.contains(new_point)) point_already_exists = true;
            if(!point_already_exists){
                this.points.add(new_point);
                iterator++;
            }
        }
    }

    public void print_points(){
        for(Point current_point:this.points){
            System.out.println("[x="+current_point.x+",y="+current_point.y+"]");
        }
    }

    public void generate_connections_between_points(){

        for(int i=0; i<this.points.size();i++){
            for(int j=i+1;j<this.points.size();j++){

                //System.out.println(points.get(i) + " -> "+points.get(j));
                ArrayList<Point> connection_points = new ArrayList<>();
                connection_points.add(this.points.get(i));
                connection_points.add(this.points.get(j));
                this.connections.add(connection_points);

            }
        }
    }

    public void fix_connections(){
        ArrayList<ArrayList<Point>> connections_copy = new ArrayList<>(this.connections);
        for(int i=0; i<this.connections.size();i++){
            ArrayList<Point> first_connection = this.connections.get(i);
            for(int j=i+1;j< this.connections.size();j++){
                if(i!=j){
                    ArrayList<Point> second_connection = this.connections.get(j);
                    if(!first_connection.get(0).equals(second_connection.get(0)) && !first_connection.get(0).equals(second_connection.get(1)) &&!first_connection.get(1).equals(second_connection.get(1)) &&!first_connection.get(1).equals(second_connection.get(0))&& connections_intersect(first_connection.get(0),first_connection.get(1),second_connection.get(0),second_connection.get(1))) connections_copy.remove(second_connection);
                }

            }
        }
        this.connections = connections_copy;

    }

    private boolean connections_intersect(Point first_connection_start_point, Point first_connection_end_point, Point second_connection_start_point, Point second_connection_end_point) {

        Line2D line1 = new Line2D.Float(first_connection_start_point.x,first_connection_start_point.y,first_connection_end_point.x,first_connection_end_point.y);
        Line2D line2 = new Line2D.Float(second_connection_start_point.x,second_connection_start_point.y,second_connection_end_point.x,second_connection_end_point.y);

        return line1.intersectsLine(line2);
    }


    public void print_connections(){
        for(ArrayList<Point> current_points:this.connections){
            System.out.println("[x="+current_points.get(0).x+",y="+current_points.get(0).y+"] -> [x=" + current_points.get(1).x+",y="+current_points.get(1).y+"]");
        }
    }

    public static void main(String[] args) {
        MapProblemGenerator mpg = new MapProblemGenerator(5,5,5);
        mpg.generate_points();
        mpg.print_points();
        mpg.generate_connections_between_points();
        System.out.println("___________________________________________");
        mpg.print_connections();
        System.out.println("___________________________________________");
        mpg.fix_connections();
        mpg.print_connections();

    }
}
