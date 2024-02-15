
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


@Path("Premio")
@WebServlet(name = "Premio", urlPatterns = {"/Premio"})
public class Premio extends HttpServlet {

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
    @Path("/getPremios")
    public ArrayList<Entidades.Premio> getAllPremios(@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.Premio> premios = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("select * from premio");
            rs = ps.executeQuery();
            while(rs.next()){
                Entidades.Premio premio = new Entidades.Premio();
                premio.setId_premio(rs.getInt("id_premio"));
                premio.setNombre(rs.getString("nombre"));
                premios.add(premio);
            }
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
            return premios;
        }
        catch(Exception e){
            throw e;
        }
    }
    
    
    @POST
    @Path("/setPremio")
    public void setPremio (@FormParam("nombre") String p_nombre,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            PreparedStatement ps = conn.prepareStatement("insert into premio (nombre) values (?)");
            ps.setString(1, p_nombre);
            rs = ps.executeQuery();
            ps.close();
            rs.close();
            
            allowCrossDomainAccess();
        }
        catch(Exception e){
            throw e;
        }
    }
    
    
    @GET
    @Path("/getNumero")
    public String getReporte (@QueryParam("numero") String numero) throws SQLException, ClassNotFoundException, Exception{
        try{
           return "hola mundo "+numero;
        }
        catch(Exception e){
            throw e;
        }
    }

    /**
     * Retrieves representation of an instance of api.persona
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    }

    

    

