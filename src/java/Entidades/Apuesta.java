package Entidades;

public class Apuesta {

    private int marcador_a;
    private int marcador_b;
    private int cedula;
    private int id_partido;
    private int id_apuesta;
    private String ganador;
    private String nombre; 

    public Apuesta(int marcador_a, int marcador_b, int cedula, int id_partido, int id_apuesta, String ganador, String nombre){
     this.marcador_a = marcador_a;
     this.marcador_b = marcador_b;
     this.cedula = cedula;
     this.id_partido = id_partido;
     this.id_apuesta = id_apuesta;
     this.ganador = ganador;
     this.nombre =nombre;
    }

    public Apuesta() {
    }

    public int getMarcador_a() {
        return marcador_a;
    }

    public void setMarcador_a(int marcador_a) {
        this.marcador_a = marcador_a;
    }

    public int getMarcador_b() {
        return marcador_b;
    }

    public void setMarcador_b(int marcador_b) {
        this.marcador_b = marcador_b;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public int getId_apuesta() {
        return id_apuesta;
    }

    public void setId_apuesta(int id_apuesta) {
        this.id_apuesta = id_apuesta;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
