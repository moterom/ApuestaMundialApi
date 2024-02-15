package Entidades;

import java.util.Date;

public class Partido {
    
private String equipo_a;
private String equipo_b;
private int marcador_a;
private int marcador_b;
private Date fecha;
private int id_partido;
private int id_premio;


    public Partido() {
        
    }

    public String getEquipo_a() {
        return equipo_a;
    }

    public void setEquipo_a(String equipo_a) {
        this.equipo_a = equipo_a;
    }

    public String getEquipo_b() {
        return equipo_b;
    }

    public void setEquipo_b(String equipo_b) {
        this.equipo_b = equipo_b;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public int getId_premio() {
        return id_premio;
    }

    public void setId_premio(int id_premio) {
        this.id_premio = id_premio;
    }

    
}
