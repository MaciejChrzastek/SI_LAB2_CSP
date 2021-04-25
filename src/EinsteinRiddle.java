import java.util.*;

public final class EinsteinRiddle {

    public static void main(String[] args) {
        /////////////////////////////////////////////////////////////////
        List<String> variables = new ArrayList<String>();
        for(int i=0;i<5;i++){
            String new_house_color = ""+(i+1)+"_house_color";
            variables.add(new_house_color);

            String new_house_nationality = ""+(i+1)+"_house_nationality";
            variables.add(new_house_nationality);

            String new_house_cigar = ""+(i+1)+"_house_cigar";
            variables.add(new_house_cigar);

            String new_house_beverage = ""+(i+1)+"_house_beverage";
            variables.add(new_house_beverage);

            String new_house_pet = ""+(i+1)+"_house_pet";
            variables.add(new_house_pet);
        }
        /////////////////////////////////////////////////////////////////
        Map<String, List<String>> domains = new HashMap<>();
        for (String variable : variables) {
            if(variable.endsWith("color")) domains.put(variable, Arrays.asList("ZOLTY","NIEBIESKI","CZERWONY","ZIELONY","BIALY"));
            if(variable.endsWith("nationality")) domains.put(variable, Arrays.asList("NORWEG","DUNCZYK","ANGLIK","NIEMIEC","SZWED"));
            if(variable.endsWith("cigar")) domains.put(variable, Arrays.asList("CYGARA","PAPIEROSY_LIGHT","PAPIEROSY_BEZ_FILTRA","FAJKA","PAPIEROSY_MENTOLOWE"));
            if(variable.endsWith("beverage")) domains.put(variable, Arrays.asList("WODA","HERBATA","MLEKO","KAWA","PIWO"));
            if(variable.endsWith("pet")) domains.put(variable, Arrays.asList("KOTY","KONIE","PTAKI","RYBKI","PSY"));
        }
        /////////////////////////////////////////////////////////////////
        CSP<String, String> csp = new CSP<>(variables, domains);




        csp.addConstraint(new AllFeaturesDifferentConstraint(Arrays.asList("1_house_color", "2_house_color", "3_house_color", "4_house_color", "5_house_color")));
        csp.addConstraint(new AllFeaturesDifferentConstraint(Arrays.asList("1_house_nationality", "2_house_nationality", "3_house_nationality", "4_house_nationality", "5_house_nationality")));
        csp.addConstraint(new AllFeaturesDifferentConstraint(Arrays.asList("1_house_cigar", "2_house_cigar", "3_house_cigar", "4_house_cigar", "5_house_cigar")));
        csp.addConstraint(new AllFeaturesDifferentConstraint(Arrays.asList("1_house_beverage", "2_house_beverage", "3_house_beverage", "4_house_beverage", "5_house_beverage")));
        csp.addConstraint(new AllFeaturesDifferentConstraint(Arrays.asList("1_house_pet", "2_house_pet", "3_house_pet", "4_house_pet", "5_house_pet")));

        /*
        for(int i=1;i<6;i++){
            for(int j=i+1;j<6;j++){
                String color1 = String.valueOf(i)+"_house_color";
                String color2 = String.valueOf(j)+"_house_color";
                System.out.println(color1+ " -> "+color2);
                csp.addConstraint(new AllFeaturesDifferentConstraint(color1,color2));
                System.out.println(csp.constraints);

                String nationality1 = ""+i+"_house_nationality";
                String nationality2 = ""+j+"_house_nationality";
                csp.addConstraint(new AllFeaturesDifferentConstraint(nationality1,nationality2));

                String cigar1 = ""+i+"_house_cigar";
                String cigar2 = ""+j+"_house_cigar";
                csp.addConstraint(new AllFeaturesDifferentConstraint(cigar1,cigar2));

                String beverage1 = ""+i+"_house_beverage";
                String beverage2 = ""+j+"_house_beverage";
                csp.addConstraint(new AllFeaturesDifferentConstraint(beverage1,beverage2));

                String pet1 = ""+i+"_house_pet";
                String pet2 = ""+j+"_house_pet";
                csp.addConstraint(new AllFeaturesDifferentConstraint(pet1,pet2));
            }
        }
         */


        csp.addConstraint(new HousePositionConstraint("1","nationality","NORWEG"));
        csp.addConstraint(new SameHouseConstraint("nationality","ANGLIK","color","CZERWONY"));
        csp.addConstraint(new LeftRightHouseConstraint("color","ZIELONY","color","BIALY"));
        csp.addConstraint(new SameHouseConstraint("nationality","DUNCZYK","beverage","HERBATA"));
        csp.addConstraint(new NeighbourHouseConstraint("cigar","PAPIEROSY_LIGHT","pet","KOTY"));
        csp.addConstraint(new SameHouseConstraint("color","ZOLTY","cigar","CYGARA"));
        csp.addConstraint(new SameHouseConstraint("nationality","NIEMIEC","cigar","FAJKA"));
        csp.addConstraint(new HousePositionConstraint("3","beverage","MLEKO"));
        csp.addConstraint(new NeighbourHouseConstraint("cigar","PAPIEROSY_LIGHT","beverage","WODA"));
        csp.addConstraint(new SameHouseConstraint("cigar","PAPIEROSY_BEZ_FILTRA","pet","PTAKI"));
        csp.addConstraint(new SameHouseConstraint("nationality","SZWED", "pet","PSY"));
        csp.addConstraint(new NeighbourHouseConstraint("nationality","NORWEG","color","NIEBIESKI"));
        csp.addConstraint(new NeighbourHouseConstraint("pet","KONIE","color","ZOLTY"));
        csp.addConstraint(new SameHouseConstraint("cigar","PAPIEROSY_MENTOLOWE","beverage","PIWO"));
        csp.addConstraint(new SameHouseConstraint("color","ZIELONY","beverage","KAWA"));

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


    }





}
