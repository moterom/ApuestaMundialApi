
package Entidades;

public class Premio {

    private int id_premio;
    private String nombre;

    public Premio(int id_premio, String nombre) {
        this.id_premio = id_premio;
        this.nombre = nombre;
    }

    public Premio() {
        
    }

    public int getId_premio() {
        return id_premio;
    }

    public void setId_premio(int id_premio) {
        this.id_premio = id_premio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
