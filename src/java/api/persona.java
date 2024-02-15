/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * REST Web Service
 *
 * @author Programador
 */

@Path("persona")
@WebServlet(name = "persona", urlPatterns = {"/persona"})
public class persona extends HttpServlet {

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

    /**
     * Creates a new instance of persona
     */
    public persona() {
    }
    
    
    @GET
    @Path("/getPersonas")
    public ArrayList<Entidades.persona> getAllPersonas(@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.persona> personas = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("select cedula, upper(nombre) nombre from persona");
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
    
    
    @POST
    @Path("/setPersona")
    public ArrayList<Entidades.persona> setPersonas (@FormParam("cedula") int p_cedula, @FormParam("nombre") String p_nombre,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            
            ArrayList<Entidades.persona> personas = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("insert into persona (cedula,nombre) select ?, ? from dual where not exists (select * from persona where cedula=?)");
            ps.setInt(1, p_cedula);
            ps.setString(2, p_nombre);
            ps.setInt(3, p_cedula);
            rs = ps.executeQuery();
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
    @Path("/getNumero")
    public String getReporte (@QueryParam("numero") String numero) throws SQLException, ClassNotFoundException, Exception{
        try{
           return "hola mundo "+numero;
        }
        catch(Exception e){
            throw e;
        }
    }
    
    @POST
    @Path("/getPersonaByCedula")
    public String getPersonaByCedula (@FormParam("cedula") int cedula,@Context ServletContext context) throws SQLException, ClassNotFoundException, Exception{
        try{
            ResultSet rs=null;
            
            conn = BaseDatos.getConexion(context.getRealPath("/Archivos de Configuracion/conec.properties"));
            PreparedStatement ps = conn.prepareStatement("select nombre from persona where cedula=?");
            ps.setInt(1, cedula);
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

    /**
     * PUT method for updating or creating an instance of persona
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
