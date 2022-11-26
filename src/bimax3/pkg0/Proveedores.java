
package bimax3.pkg0;

import BasedeDatos.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Proveedores {
    public long ruc;
    public String nombre;
    public String email;
    public int telefono;
    public String direccion;
    public String productos;

    public Proveedores(long ruc, String nombre, String email, int telefono, String direccion, String productos) {
        this.ruc = ruc;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.productos = productos;
    }
    
    public void registrar(){
    try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement psp = con.prepareStatement("INSERT INTO proveedores (ruc,razonsocial,correoelectronico,Telefono,Direccion,productos) VALUES (?,?,?,?,?,?)");
        psp.setLong(1, ruc);
        psp.setString(2,nombre);
        psp.setString(3,email);
        psp.setInt(4,telefono);
        psp.setString(5,direccion);
        psp.setString(6,productos);
        psp.executeUpdate();
        JOptionPane.showMessageDialog(null,"Registro guardado");
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Registro fallido");
        }
    }
         
    public void consultar(){
        try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement ps;
        ResultSet rs;
        ps = con.prepareStatement("SELECT * FROM proveedores WHERE ruc=?");
        ps.setLong(1, ruc);
        rs=ps.executeQuery();
   while(rs.next()){
       
       ruc=rs.getLong("ruc");
       nombre=rs.getString("razonsocial");
       email=rs.getString("correoelectronico");
       telefono=rs.getInt("telefono");
       direccion=rs.getString("direccion");
       productos=rs.getString("productos");
   }
        }catch(SQLException e){
    JOptionPane.showMessageDialog(null,e);
        }
    }
        public void eliminar(){
    try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement psu = con.prepareStatement("DELETE FROM proveedores WHERE ruc=?");
        psu.setLong(1,ruc);
        psu.executeUpdate();
        JOptionPane.showMessageDialog(null,"Registro eliminado");
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Registro fallido");
        }
    }
         public void modificar(){
    try{
        Connection cont =  ConexionMySQL.getConexion();
        PreparedStatement ps = cont.prepareStatement("UPDATE proveedores SET razonsocial=?, correoelectronico=?,telefono=?, direccion=?, productos=? WHERE ruc=?");
        ps.setString(1,nombre);
        ps.setString(2,email);
        ps.setInt(3,telefono);
        ps.setString(4,direccion);
        ps.setString(5,productos);
        ps.setLong(6, ruc);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null,"Registro modificado");
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Registro fallido");
        }
    }
    
}
