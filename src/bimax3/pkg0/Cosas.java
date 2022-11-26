
package bimax3.pkg0;

import BasedeDatos.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Cosas {
    public int id;
    public String codigo;
    public String Descripcion;
    public float costo;
    public float valorGanancia;
    public float valorFinal;

    public Cosas(int id, String codigo, String Descripcion, float costo, float valorGanancia, float valorFinal) {
        this.id = id;
        this.codigo = codigo;
        this.Descripcion = Descripcion;
        this.costo = costo;
        this.valorGanancia=valorGanancia;
        this.valorFinal=valorFinal;
    }
    public void registrar(){
    try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement ps = con.prepareStatement("INSERT INTO productos (codigo,descripcion,costo,valorGanancia,valorFinal) VALUES (?,?,?,?,?)");
        ps.setString(1, codigo);
        ps.setString(2,Descripcion);
        ps.setFloat(3,costo);
        ps.setFloat(4,valorGanancia);
        ps.setFloat(5,valorFinal);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null,"Registro guardado");
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Registro fallido");
        }
    }
        public void consultar(){
        try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement ps;
        PreparedStatement psu;
        ResultSet rs;
        ResultSet rsu;
        System.out. println(codigo);
        ps = con.prepareStatement("SELECT * FROM productos WHERE codigo=?");
        ps.setString(1,codigo);
        rs=ps.executeQuery();
   while(rs.next()){
       codigo=rs.getString("codigo");
       Descripcion=rs.getString("descripcion");
       costo=rs.getFloat("costo");
       valorGanancia=rs.getFloat("valorganancia");
       valorFinal=rs.getFloat("valorfinal");
   }

        }catch(SQLException e){
    JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void modificar(){
    try{
        Connection cont =  ConexionMySQL.getConexion();
        PreparedStatement ps = cont.prepareStatement("UPDATE productos SET descripcion=?, costo=?, valorganancia=?, valorfinal=? WHERE codigo=?");
        ps.setString(1,Descripcion);
        ps.setFloat(2, costo);
        ps.setFloat(3,valorGanancia);
        ps.setFloat(4,valorFinal);
        ps.setString(5,codigo);
        
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null,"Registro modificado");
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Registro fallido");
        }
    }
    public void eliminar(){
    try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement psu = con.prepareStatement("DELETE FROM productos WHERE codigo=?");
        psu.setString(1,codigo);
        psu.executeUpdate();
        JOptionPane.showMessageDialog(null,"Registro eliminado");
        }catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Registro fallido");
        }
    }
    
}
