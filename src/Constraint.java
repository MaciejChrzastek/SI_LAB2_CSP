import java.util.List;
import java.util.Map;

// V to typ dla zmiennej
// D to typ dla domeny
public abstract class Constraint<V,D> {
    // lista zmiennych pomiędzy którymi jest dane ograniczenie
    protected List<V> variables;

    public Constraint(List<V> variables) {
        this.variables = variables;
    }

    // metoda określająca spełnienie ograniczeń, musi być nadpisana w nadklasie
    public abstract boolean satisfied(Map<V, D> assignment);
}
