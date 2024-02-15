
package api;
import BD.BaseDatos;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;



    @Path("Partido")
@WebServlet(name = "Partido", urlPatterns = {"/Partido"})
public class Partido extends HttpServlet {

    @Context
    private UriInfo context;
    
    @Context
    private HttpServletResponse servletResponse;
    Connection conn;
    
    
    private void allowCrossDomainAccess() {
        if (servletResponse != null){
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            servletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
            servletResponse.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
        }
    }

   
    
    
    @GET
    @Path("/getPartidos")
    public ArrayList<Entidades.Partido> getAllPartidos(@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Partido> partidos = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("select * from partido");
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.Partido partido = new Entidades.Partido();
                partido.setEquipo_a(rs.getString("equipo_a"));
                partido.setEquipo_b(rs.getString("equipo_b"));
                partido.setFecha(rs.getDate("fecha"));
                partido.setMarcador_a(rs.getInt("marcador_a"));
                partido.setMarcador_b(rs.getInt("marcador_b"));
                partido.setId_premio(rs.getInt("id_premio"));
                partido.setId_partido(rs.getInt("id_partido"));
                partidos.add(partido);
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return partidos;
        }
        catch(Exception e){
            throw e;
        }
    }
    
    
    @POST
    @Path("/setPartido")
    public ArrayList<Entidades.Partido> setPartidos (@FormParam("equipo_a") String equipo_a, @FormParam("equipo_b") String equipo_b, @FormParam("fecha") long fecha,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Partido> Partidos = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("insert into partido (equipo_a, equipo_b, fecha) select ?,?,? from dual where not exists (select id_partido from partido where equipo_a=? and equipo_b=? and trunc(fecha)=trunc(?))");
            ps.setString(1, equipo_a);
            ps.setString(2, equipo_b);
            ps.setDate(3, new java.sql.Date(fecha));
            ps.setString(4, equipo_a);
            ps.setString(5, equipo_b);
            ps.setDate(6, new java.sql.Date(fecha));
            rs = ps.executeQuery();
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return Partidos;
        }
        catch(Exception e){
            throw e;
        }
    }
    
    @POST
    @Path("/updatePremioPorPartido")
    public void setPremioPorPartido (@FormParam("id_partido") int id_partido, @FormParam("id_premio") int id_premio,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            PreparedStatement ps = conn.prepareStatement("update partido set id_premio=? where id_partido=?");
            ps.setInt(1, id_premio);
            ps.setInt(2, id_partido);
            rs = ps.executeQuery();
            ps.close();
            rs.close();
        }
        catch(Exception e){
            throw e;
        }
    
    }
    
    @POST
    @Path("/updateMarcadorPartido")
    public void setMarcadorPorPartido (@FormParam("id_partido") int id_partido, @FormParam("marcador_a") int marcador_a,@FormParam("marcador_b") int marcador_b,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            PreparedStatement ps = conn.prepareStatement("update partido set marcador_a=? , marcador_b=? where id_partido=?");
            ps.setInt(1, marcador_a);
            ps.setInt(2, marcador_b);
            ps.setInt(3, id_partido);
            rs = ps.executeQuery();
            ps.close();
            rs.close();
        }
        catch(Exception e){
            throw e;
        }
    }
    
    @POST
    @Path("/getAcertados")
    public ArrayList<Entidades.persona> getAcertados(@FormParam ("id_partido") int id_partido, @Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.persona> personas = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("SELECT A.CEDULA, upper(PER.NOMBRE) NOMBRE\n" +
            "FROM APUESTA A INNER JOIN PARTIDO P ON A.ID_PARTIDO=P.ID_PARTIDO \n" +
            "INNER JOIN PERSONA PER ON PER.CEDULA=A.CEDULA\n" +
            "WHERE A.MARCADOR_A=P.MARCADOR_A AND A.MARCADOR_B= P.MARCADOR_B AND P.ID_PARTIDO=?");
            ps.setInt(1,id_partido);
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.persona persona = new Entidades.persona();
                persona.setCedula(rs.getInt("CEDULA"));
                persona.setNombre(rs.getString("NOMBRE"));
                
                personas.add(persona);
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return personas;
        }
        catch(Exception e){
            throw e;
        }
    }
    
    @POST
    @Path("/getApuestasPorPartido")
    public ArrayList<Entidades.Apuesta> getApuestasPorPartido(@FormParam ("id_partido") int id_partido, @Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Apuesta> apuestas = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("select a.cedula, upper(per.nombre) nombre, p.equipo_a, p.equipo_b,a.marcador_a, a.marcador_b from apuesta a inner join \n" +
            "partido p on a.id_partido=p.id_partido \n" +
            "inner join persona per on per.cedula=a.cedula where a.id_partido=?");
            ps.setInt(1,id_partido);
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.Apuesta apuesta = new Entidades.Apuesta();
                apuesta.setCedula(rs.getInt("CEDULA"));
                apuesta.setNombre(rs.getString("NOMBRE"));
                apuesta.setMarcador_a(rs.getInt("marcador_a"));
                apuesta.setMarcador_b(rs.getInt("marcador_b"));                
                apuestas.add(apuesta);
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return apuestas;
        }
        catch(Exception e){
            throw e;
        }
    }
    
    
    @POST
    @Path("/getPremioPorPartido")
    public String GetPremioPorPartido (@FormParam("id_partido") int id_partido,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Premio> premios = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("SELECT PAR.ID_PARTIDO, P.NOMBRE FROM PARTIDO PAR INNER JOIN PREMIO P ON PAR.ID_PREMIO=P.ID_PREMIO WHERE PAR.ID_PARTIDO=?");
            ps.setInt(1, id_partido);
            rs = ps.executeQuery();
            String nombre="";
            while(rs.next()){
                nombre=rs.getString("nombre");
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            
           return (nombre);
        }
        catch(Exception e){
            throw e;
        }
    }
    
    @POST
    @Path("/getGanador")
    public Entidades.persona getGanador (@FormParam("id_partido") int id_partido,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            Entidades.persona vPersona = new Entidades.persona();
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Premio> premios = new ArrayList<>();
            CallableStatement callableStatement  = conn.prepareCall("call ganador(?,?,?)");
            callableStatement.setInt(1, id_partido);
            callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
            callableStatement.executeUpdate();
            
            vPersona.setCedula(callableStatement.getInt(2));
            vPersona.setNombre(callableStatement.getString(3));
            allowCrossDomainAccess();
            
           return (vPersona);
        }
        catch(Exception e){
            throw e;
        }
    }
    
    @GET
    @Path("/getGanadores")
    public ArrayList<Entidades.persona> getGanadores(@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.persona> personas = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("SELECT A.CEDULA,upper(P.NOMBRE) NOMBRE FROM APUESTA A INNER JOIN PERSONA P ON A.CEDULA=P.CEDULA WHERE GANADOR='S' GROUP BY a.cedula, P.NOMBRE ");
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.persona persona = new Entidades.persona();
                persona.setCedula(rs.getInt("cedula"));
                persona.setNombre(rs.getString("nombre"));
                personas.add(persona);
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return personas;
        }
        catch(Exception e){
            throw e;
        }
    }
   
    @GET
    @Path("/getPartidosSinMarcador")
    public ArrayList<Entidades.Partido> getPartidoSinMarcador(@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
             conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Partido> partidos = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("select * from partido where marcador_a is null and marcador_b is null");
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.Partido partido = new Entidades.Partido();
                partido.setEquipo_a(rs.getString("equipo_a"));
                partido.setEquipo_b(rs.getString("equipo_b"));
                partido.setFecha(rs.getDate("fecha"));
                partido.setMarcador_a(-1);
                partido.setMarcador_b(-1);
                partido.setId_premio(rs.getInt("id_premio"));
                partido.setId_partido(rs.getInt("id_partido"));
                partidos.add(partido);
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return partidos;
        }
        catch(Exception e){
            throw e;
        }
    }
}
