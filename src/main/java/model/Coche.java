package model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Coche implements Serializable {
    private static final long serialVersionUID = 112233L;
    private String matricula;
    private String modelo;
    private String color;
    private Long id;


    public Coche(String matricula, String modelo, String color, Long id) {
        this.id = id;
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
    }

    @Override
    public String toString() {
        return "\n\tID " + id + ":" +
                "\n\tMODELO: " + modelo +
                "\n\tCOLOR: " + color +
                "\n\tMATRICULA: " + matricula;
    }
}
