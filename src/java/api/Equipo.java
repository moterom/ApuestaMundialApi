
package api;

import BD.BaseDatos;
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


    
    
    @Path("Equipo")
@WebServlet(name = "Equipo", urlPatterns = {"/Equipo"})
public class Equipo extends HttpServlet {

    @Context
    private UriInfo context;
    
    @Context
    private HttpServletResponse servletResponse;
    Connection conn;
    private Object Entidades;
    
    
    private void allowCrossDomainAccess() {
        if (servletResponse != null){
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            servletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
            servletResponse.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
        }
    }

   
    
    
    @GET
    @Path("/getEquipos")
    public ArrayList<Entidades.Equipo> getAllEquipos(@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Equipo> equipos = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("select * from equipo");
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.Equipo equipo = new Entidades.Equipo();
                equipo.setId_equipo(rs.getString("id_equipo"));
                equipo.setNombre(rs.getString("nombre"));
                equipos.add(equipo);
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return equipos;
        }
        catch(Exception e){
            throw e;
        }
    }       
    
    
    @POST
    @Path("/setEquipo")
    public ArrayList<Entidades.Equipo> setEquipos (@FormParam("id_equipo") int id_equipo, @FormParam("imagen") String p_imagen, @FormParam("nombre") String p_nombre,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Equipo> Equipos  = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("insert into equipo (id_equipo, imagen, nombre) values (?,?,?)");
            ps.setInt(1, id_equipo);
            ps.setString(2, p_imagen);
            ps.setString(3, p_nombre);
            rs = ps.executeQuery();
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return Equipos;
        }
        catch(Exception e){
            throw e;
        }
    }
    
}
