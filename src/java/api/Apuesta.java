
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


@Path("Apuesta")
@WebServlet(name = "Apuesta", urlPatterns = {"/Apuesta"})
public class Apuesta extends HttpServlet{
    
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
    @Path("/getApuesta")
    public ArrayList<Entidades.Apuesta> getAllApuestas(@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Apuesta> apuestas = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("select * from apuesta");
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.Apuesta apuesta = new Entidades.Apuesta();
                apuesta.setId_apuesta(rs.getInt("id_apuesta"));
                apuesta.setId_partido(rs.getInt("id_partido"));
                apuesta.setMarcador_a(rs.getInt("marcador_a"));
                apuesta.setMarcador_b(rs.getInt("marcador_b"));
                apuesta.setCedula(rs.getInt("cedula"));
                apuesta.setGanador(rs.getString("ganador"));
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
    @Path("/setApuestas")
    public void setApuestas (@FormParam("id_partido") int id_partido, @FormParam("marcador_a") int marcador_a, @FormParam("marcador_b") int marcador_b, @FormParam("cedula") int cedula, @Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            PreparedStatement ps = conn.prepareStatement("insert into apuesta (id_partido, marcador_a, marcador_b, cedula) select ?,?,?,? from dual where not exists (select cedula from apuesta where id_partido=? and cedula=?)");
            ps.setInt(1, id_partido);
            ps.setInt(2, marcador_a);
            ps.setInt(3, marcador_b);
            ps.setInt(4, cedula);
            ps.setInt(5, id_partido);
            ps.setInt(6, cedula);
            rs = ps.executeQuery();
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
        }
        catch(Exception e){
            throw e;
        }
    }
}
