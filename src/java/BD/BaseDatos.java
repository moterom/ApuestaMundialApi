/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author NELSON
 */
public class BaseDatos {
    private static String archivoPropiedades = "sicad.properties";
    private static String url;
    private static String usuario  = "proy";
    private static String clave    = "pr0y";
    private static String servidor = "localhost";
    private static String servicio = "XE";
    private static String puerto   = "1521";
    private static String formato   = "yyyy-MM-dd";
    private static String separador = "-";
    private static Connection conexion;

    public BaseDatos() {

    }

    public static Connection getConexion(String archivo) throws Exception{
        if (conexion==null){
            if (!archivo.isEmpty()) archivoPropiedades = archivo;
            try {
                final Properties props = leePropiedades(archivoPropiedades);
                servidor    = props.getProperty("servidor",servidor);
                usuario     = props.getProperty("usuario",usuario);
                clave       = props.getProperty("clave",clave);
                servicio    = props.getProperty("servicio",servicio);
                puerto      = props.getProperty("puerto",puerto);
                formato     = props.getProperty("formatoFecha",formato);
                separador   = props.getProperty("separadorFecha",separador);

                escribePropiedades(props, archivoPropiedades);

                //System.setProperty("formatoFecha",formato);
                //System.setProperty("separadorFecha",separador);

                Class.forName("oracle.jdbc.OracleDriver");
                url	= "jdbc:oracle:thin:@"+servidor+":"+puerto+":"+servicio;
                conexion = DriverManager.getConnection(url, usuario, clave);
            } catch (SQLException e) {
                //MensajeError.sError(e);
                throw e;
            } catch (Exception e) {
                //MensajeError.sError(e);
                throw e;
            }
        }
        return conexion;
    }

    public static void cerrar(Connection connection)throws Exception{
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            //MensajeError.sError(e);
            throw e;
        }
    }
    
    public static Properties leePropiedades(String archivo) throws Exception{
        Properties props = new Properties();
        File x = new File(archivo);
        try {
            if (!x.exists()) {
                if (!x.createNewFile()){
                    return null;
                }
            }
            FileInputStream fis = new FileInputStream(archivo);
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            //MensajeError.sError(e);
            throw e;
        }
        return props;
    }

    public static void escribePropiedades(Properties props, String archivo) throws Exception{
        props.setProperty("servidor", servidor);
        props.setProperty("usuario", usuario);
        props.setProperty("servicio", servicio);
        props.setProperty("puerto", puerto);
        props.setProperty("formatoFecha",formato);
        props.setProperty("separadorFecha",separador);
        File x = new File(archivo);
        try {
            if (!x.exists()) {
                if (!x.createNewFile()){
                }
            }
            FileOutputStream fos = new FileOutputStream(archivo);
            props.store(fos, "SICAD PROPIEDADES");
            fos.close();
        } catch (FileNotFoundException ex) {
            //MensajeError.sError(ex);
            throw ex;
        } catch (IOException ex) {
            //MensajeError.sError(ex);
            throw ex;
        }
    }
}
