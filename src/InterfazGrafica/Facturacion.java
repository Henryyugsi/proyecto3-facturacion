
package InterfazGrafica;

import BasedeDatos.ConexionMySQL;
import bimax3.pkg0.Cosas;
import bimax3.pkg0.Facturar;
import bimax3.pkg0.Persona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public class Facturacion extends javax.swing.JFrame implements ActionListener{

    public Facturacion() {
        initComponents();
        txtnumeroestablecimiento.setText("001");
        txtnumerofacturero.setText("001");
        txtpropina.setText("0");
        txtsubtotalsinimpuestos.setEnabled(false);
        txttotaldescuento.setEnabled(false);
        txtbase0.setEnabled(false);
        txtbase12.setEnabled(false);
        txtiva12.setEnabled(false);
        txtunidadtiempo.setEnabled(false);
        txtunidadtiempo.setText("dias");
        txtplazo.setText("0");
        numerofacturas();
        DefaultTableModel modeloTabla= (DefaultTableModel) tblProductos.getModel();
        modeloTabla.setRowCount(0);
    } 
    public void busquedaClientes(){
        String cedula = txtruc.getText();
        Persona usu = new Persona(cedula,null,null,null,1,null,null);
        usu.consultar();
        if (usu.Nombres==null){
        JOptionPane.showMessageDialog(null, "No existe el usuario");
        }else{
        String CDI = usu.CDI;
        String Nombres = usu.Nombres;
        String Apellidos = usu.Apellidos;
        String eMail = usu.eMail;
        long Celular = usu.Celular;
        String Direccion = usu.Direccion;
        txtruc.setText(""+CDI);
        txtnombres.setText(Nombres+" "+Apellidos);
        txtcorreoelectronico.setText(eMail);
        txttelefono.setText(""+Celular);
        txtdireccion.setText(Direccion);
        //Consultar si el usuario tiene un servicio relacionado
        if(usu.Producto!=null){   
        Cosas servi = new Cosas(1,usu.Producto,null,1,1,1);
        servi.consultar();
        cargartabla(1,servi.codigo,servi.Descripcion,servi.valorFinal,0);
        calculovalores();
        }  
    }
    }
    public int verificarDatosEstenLlenosComprador(){
    //Este metodo se utilizara para verificar que todos los datos de la tabla esten llenados correctamente
    int verificador=0;
            int longitud2= txtruc.getText().length();
            if(longitud2==0){
                verificador=1;
            JOptionPane.showMessageDialog(null, "El campo RUC/CEDULA esta vacio");
            }else{
                int longitud = txtruc.getText().length();
                if (longitud==13 || longitud==10){
                }else{
                    verificador=1;
                JOptionPane.showMessageDialog(null, "El campo RUC/CEDULA es incorrecto");
                }
            }
            if(txtnombres.getText().length()==0){
                verificador=1;
            JOptionPane.showMessageDialog(null, "Los nombres no pueden estar vacio");
            }
            if(txtcorreoelectronico.getText().length()==0){
                verificador=1;
            JOptionPane.showMessageDialog(null, "El Correo Electronico no pueden estar vacio");
            }
            if(txttelefono.getText().length()==0){
                verificador=1;
            JOptionPane.showMessageDialog(null, "El Telefono no pueden estar vacio");
            }
            if(txtdireccion.getText().length()==0){
                verificador=1;
            JOptionPane.showMessageDialog(null, "La direccion no pueden estar vacio");
            }
            return verificador;
    }
    
    public int verificarDatosEstenLlenosProducto(){
    int verificador=0;
            int numerodefilas=tblProductos.getRowCount();
            if(numerodefilas==0){
                verificador=1;
            JOptionPane.showMessageDialog(null, "No ha añadido ningun producto a la Lista");
            }
            
    for(int i = 0; i < numerodefilas; i++) {
    for(int j = 0; j < 6; j++) {
    if(tblProductos.getValueAt(i, j)==null){
                verificador=1;
            JOptionPane.showMessageDialog(null, "La Tabla productos tiene algun o algunos campos vacios");
            }
    }
    }
            return verificador;
    }
    public void cargartabla(int cantidad, String codigo, String descripcion,double preciounitario,double descuento){
    //Calcular el precio total del producto
    double total=cantidad*preciounitario-descuento;
    DefaultTableModel modeloTabla= (DefaultTableModel) tblProductos.getModel();
    modeloTabla.setRowCount(0);
    Object[]vec= new Object[6];
    vec[0]=cantidad;
    vec[1]=codigo;
    vec[2]=descripcion;
    vec[3]=preciounitario;
    vec[4]=descuento;
    vec[5]=total;
    modeloTabla.addRow(vec);
    }
    public void calculovalores(){
    //Total del producto
    float totalsum=0;
    float descuentototal=0;
    int numerodefilas=tblProductos.getRowCount();
    for(int i = 0; i < numerodefilas; i++) {
    int cantidad=Integer.parseInt(tblProductos.getValueAt(i, 0).toString());
    float preciounitario=Float.parseFloat(tblProductos.getValueAt(i, 3).toString());
    float descuento=Float.parseFloat(tblProductos.getValueAt(i, 4).toString());
    descuentototal=descuentototal+descuento;
    float total=cantidad*preciounitario-descuento;
    tblProductos.setValueAt(total, i, 5);
    totalsum=totalsum+total;
    }
    txttotaldescuento.setText(""+descuentototal);
    double propina = Double.parseDouble(txtpropina.getText());
    //Valores sumados en factura
    float subtotalsinimpuesto=totalsum;
    txtsubtotalsinimpuestos.setText(""+subtotalsinimpuesto);
    String comparar1="IVA 12%";
    if(comparar1.equals(jComboBoxIVA.getSelectedItem().toString())){
    txtbase12.setText(""+subtotalsinimpuesto);
    float tarifa12=(subtotalsinimpuesto*12)/100;
    txtiva12.setText(""+tarifa12);
    double valortotal=tarifa12+subtotalsinimpuesto+propina;
    txtvalortotal.setText(""+valortotal);
    }else{
     txtbase0.setText(""+subtotalsinimpuesto);
    double valortotal=subtotalsinimpuesto+propina;
    txtvalortotal.setText(""+valortotal);
    }
    }
  public void tomaDatos() throws SAXException, IOException, XPathExpressionException, ParserConfigurationException{
        //Obtener todos los datos necesarios
        //Datos del comprador
        String ruc=txtruc.getText();
        String razonsocial=txtnombres.getText();
        String correo=txtcorreoelectronico.getText();
        String telefono=txttelefono.getText();
        String direccion=txtdireccion.getText();
        //Datos de la factura
        String ambiente="PRODUCCION";
        if(ambiente.equals(jComboBox2.getSelectedItem().toString())){
        ambiente="2";
        }else{
        ambiente="1";
        }
        String numeroestablecimiento=txtnumeroestablecimiento.getText();
        String numerofacturero=txtnumerofacturero.getText();
        String numerofactura=txtnumerodefactura.getText();
        String formapago=jComboBox3.getSelectedItem().toString();
        String plazo=txtplazo.getText();
        //datos generales de los valores finales de la factura
        String propina=txtpropina.getText();
        String vector1[]=new String[5];
        vector1[0]=ruc;
        vector1[1]=razonsocial;
        vector1[2]=correo;
        vector1[3]=telefono;
        vector1[4]=direccion;
        //datos del producto
        int numerodefilas=tblProductos.getRowCount();
        Facturar crearxml=new Facturar();
        crearxml.cantidadproductos(numerodefilas);
        String matriz[][]=new String[numerodefilas][7];
        String matrizp[][]=new String[numerodefilas][10];
        for(int i = 0; i < numerodefilas; i++) {
         for(int j = 0; j < 6; j++) {
          matriz[i][j]=tblProductos.getValueAt(i, j).toString();
        } 
        }
        for(int i = 0; i < numerodefilas; i++) {
          matriz[i][6]=jComboBoxIVA.getSelectedItem().toString();
        }
        
        //Datos de la factura
        String vector2[]=new String[6];
        vector2[0]=ambiente;
        vector2[1]=numeroestablecimiento;
        vector2[2]=numerofacturero;
        vector2[3]=numerofactura;
        vector2[4]=formapago;
        vector2[5]=plazo;
        //Datos finales
        String vector3[]=new String[4];
        vector3[0]=txtsubtotalsinimpuestos.getText();
        vector3[1]=txttotaldescuento.getText();
        vector3[2]=txtpropina.getText();
        vector3[3]=txtvalortotal.getText();
        crearxml.generarxml(vector1, matriz,vector2,vector3,matrizp);
        
        
  }
public void limpiar(){
           txtruc.setText("");
           txtnombres.setText("");
           txtcorreoelectronico.setText("");
           txttelefono.setText("");
           txtdireccion.setText("");
           DefaultTableModel modeloTabla= (DefaultTableModel) tblProductos.getModel();
           modeloTabla.setRowCount(0);
           txtbase0.setText("");
           txtbase12.setText("");
           txtsubtotalsinimpuestos.setText("");
           txtiva12.setText("");
           txtvalortotal.setText("");
           txtpropina.setText("0");
}  
public void limpiarDatosFinales(){
           txtbase0.setText("");
           txtbase12.setText("");
           txtsubtotalsinimpuestos.setText("");
           txtiva12.setText("");
           txtvalortotal.setText("");
}
    public void numerofacturas(){
        txtnumerodefactura.setEnabled(false);
    try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement ps;
        ResultSet rs;
        ps = con.prepareStatement("SELECT DETALLESFACTURA.NUMEROFACTURA FROM DETALLESFACTURA");
        rs=ps.executeQuery();
        while (rs.next()){
        txtnumerodefactura.setText(rs.getString("NUMEROFACTURA"));
        }
        }catch(SQLException e){
    JOptionPane.showMessageDialog(null,e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtruc = new javax.swing.JTextField();
        txtnombres = new javax.swing.JTextField();
        txtcorreoelectronico = new javax.swing.JTextField();
        txttelefono = new javax.swing.JTextField();
        txtdireccion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txttotaldescuento = new javax.swing.JTextField();
        txtpropina = new javax.swing.JTextField();
        txtiva12 = new javax.swing.JTextField();
        txtvalortotal = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtsubtotalsinimpuestos = new javax.swing.JTextField();
        txtbase0 = new javax.swing.JTextField();
        txtbase12 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        botonEmitirFactura = new javax.swing.JButton();
        botonGenerarNuevaFactura = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtnumeroestablecimiento = new javax.swing.JTextField();
        txtnumerofacturero = new javax.swing.JTextField();
        txtnumerodefactura = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jComboBoxIVA = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        txtplazo = new javax.swing.JTextField();
        txtunidadtiempo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("DATOS COMPRADOR");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RUC O NUMERO DE CEDULA", "CONSUMIDOR FINAL" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("RUC/CEDULA");

        jLabel4.setText("NOMBRES");

        jLabel5.setText("CORREO ELECTRONICO");

        jLabel6.setText("TELEFONO");

        jLabel7.setText("DIRECCION");

        txtruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtrucKeyPressed(evt);
            }
        });

        txttelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnombres)
                            .addComponent(txtcorreoelectronico)
                            .addComponent(txttelefono)
                            .addComponent(txtdireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(txtruc, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtnombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtcorreoelectronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("DATOS PRODUCTOS");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cantidad", "Codigo", "Descripcion", "Precio Unitario", "Descuento", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblProductosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblProductosMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProductosMousePressed(evt);
            }
        });
        tblProductos.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tblProductosInputMethodTextChanged(evt);
            }
        });
        tblProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProductosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblProductosKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        jButton3.setText("AÑADIR +");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setText("BASE 0%");

        jLabel12.setText("TOTAL DESCUENTO");

        jLabel13.setText("PROPINA");

        jLabel14.setText("BASE 12%");

        jLabel15.setText("IVA 12%");

        jLabel16.setText("VALOR TOTAL");

        txtpropina.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpropinaKeyReleased(evt);
            }
        });

        jLabel18.setText("SUBTOTAL SIN IMPUESTOS");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txttotaldescuento)
                    .addComponent(txtpropina)
                    .addComponent(txtiva12)
                    .addComponent(txtvalortotal)
                    .addComponent(txtsubtotalsinimpuestos)
                    .addComponent(txtbase0)
                    .addComponent(txtbase12, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtsubtotalsinimpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtbase0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtbase12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txttotaldescuento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtpropina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtiva12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtvalortotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        botonEmitirFactura.setText("EMITIR FACTURA");
        botonEmitirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEmitirFacturaActionPerformed(evt);
            }
        });

        botonGenerarNuevaFactura.setText("GENERAR NUEVA FACTURA");
        botonGenerarNuevaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarNuevaFacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(botonEmitirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonGenerarNuevaFactura)
                .addGap(36, 36, 36))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonEmitirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonGenerarNuevaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PRODUCCION", "PRUEBAS" }));

        jLabel9.setText("NUMERO DE ESTABLECIMIENTO:");

        jLabel10.setText("NUMERO DE FACTURERO:");

        jLabel8.setText("NUMERO DE FACTURA:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtnumerofacturero, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(txtnumeroestablecimiento)
                    .addComponent(txtnumerodefactura))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtnumeroestablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtnumerofacturero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnumerodefactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setText("DETALLES DE IMPUESTOS");

        jComboBoxIVA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IVA 0%", "IVA 12%", "No Objeto de Impuesto", "Exento de IVA" }));
        jComboBoxIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxIVAActionPerformed(evt);
            }
        });
        jComboBoxIVA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBoxIVAKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxIVA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel21.setText("FORMAS DE PAGO");

        jLabel23.setText("PLAZO");

        jLabel24.setText("UNIDAD DE TIEMPO");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "OTROS CON UTILIZACION DEL SISTEMA FINANCIERO", "SIN UTILIZACION DEL SISTEMA FINANCIERO", "COMPENSACION DE DEUDAS", "TARJETA DE DEBITO", "DINERO ELECTRONICO", "TARJETA PREPAGO", "TARJETA DE CREDITO", "ENDOSO DE TITULOS" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtplazo)
                    .addComponent(txtunidadtiempo))
                .addGap(12, 12, 12))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtunidadtiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtplazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String comparar="CONSUMIDOR FINAL";
        if(comparar.equals(jComboBox1.getSelectedItem().toString())){
           txtruc.setText("9999999999999");
           txtnombres.setText("CONSUMIDOR FINAL");
           txtcorreoelectronico.setText("NO APLICA");
           txttelefono.setText("NO APLICA");
           txtdireccion.setText("NO APLICA");  
           txtruc.setEnabled(false);
           txtnombres.setEnabled(false);
           txtcorreoelectronico.setEnabled(false);
           txttelefono.setEnabled(false);
           txtdireccion.setEnabled(false);
          }else{      
         txtruc.setEnabled(true);
         txtnombres.setEnabled(true);
           txtcorreoelectronico.setEnabled(true);
           txttelefono.setEnabled(true);
           txtdireccion.setEnabled(true);
           txtruc.setText("");
           txtnombres.setText("");
           txtcorreoelectronico.setText("");
           txttelefono.setText("");
           txtdireccion.setText("");
          }  
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txttelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelefonoActionPerformed

    private void txtrucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrucKeyPressed
        
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            int longitud2= txtruc.getText().length();
            if(longitud2==0){
            JOptionPane.showMessageDialog(null, "El campo RUC/CEDULA esta vacio");
            }else{
                int longitud = txtruc.getText().length();
                if (longitud==13 || longitud==10){
                busquedaClientes();
                }else{
                JOptionPane.showMessageDialog(null, "El campo RUC/CEDULA es incorrecto");
                }
            
            }
        
        }
        
    }//GEN-LAST:event_txtrucKeyPressed

    private void botonGenerarNuevaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarNuevaFacturaActionPerformed
           limpiar();
        
    }//GEN-LAST:event_botonGenerarNuevaFacturaActionPerformed

    private void jComboBoxIVAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxIVAKeyPressed
        
    }//GEN-LAST:event_jComboBoxIVAKeyPressed

    private void jComboBoxIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxIVAActionPerformed
        limpiarDatosFinales();
        int verificador = verificarDatosEstenLlenosProducto();
        if (verificador==0){
            calculovalores();
        }else{
        JOptionPane.showMessageDialog(null, "Datos del Producto Incorrectos. Por favor, llenelos nuevamente");
        }
        
    }//GEN-LAST:event_jComboBoxIVAActionPerformed

    private void botonEmitirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEmitirFacturaActionPerformed
        
        int verificador = verificarDatosEstenLlenosComprador();
        int verificador1 = verificarDatosEstenLlenosProducto();
        try {
            if (verificador==0&&verificador1==0){
            tomaDatos();
            }else{
            JOptionPane.showMessageDialog(null, "Datos llenados Incorrectamente. Por favor, llenelos nuevamente");
            }
            
        } catch (SAXException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonEmitirFacturaActionPerformed

    private void tblProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProductosKeyPressed
        
    }//GEN-LAST:event_tblProductosKeyPressed

    private void tblProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseEntered
      
    }//GEN-LAST:event_tblProductosMouseEntered

    private void tblProductosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProductosKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblProductosKeyTyped

    private void tblProductosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblProductosAncestorAdded
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblProductosAncestorAdded

    private void tblProductosInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tblProductosInputMethodTextChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblProductosInputMethodTextChanged

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked

    }//GEN-LAST:event_tblProductosMouseClicked

    private void tblProductosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMousePressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblProductosMousePressed

    private void tblProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProductosKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
        int verificador = verificarDatosEstenLlenosProducto();
        if (verificador==0){
            calculovalores();
        }else{
        JOptionPane.showMessageDialog(null, "Datos del Producto Incorrectos. Por favor, llenelos nuevamente");
        }
        }
    }//GEN-LAST:event_tblProductosKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       AñadirProductoenFacturacion pro;
        pro = new AñadirProductoenFacturacion();
        pro.setVisible(true);
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtpropinaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpropinaKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
        int verificador = verificarDatosEstenLlenosProducto();
        if (verificador==0){
            calculovalores();
        }else{
        JOptionPane.showMessageDialog(null, "Datos del Producto Incorrectos. Por favor, llenelos nuevamente");
        }
        }
    }//GEN-LAST:event_txtpropinaKeyReleased



    
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Facturacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEmitirFactura;
    private javax.swing.JButton botonGenerarNuevaFactura;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBoxIVA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtbase0;
    private javax.swing.JTextField txtbase12;
    private javax.swing.JTextField txtcorreoelectronico;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtiva12;
    private javax.swing.JTextField txtnombres;
    private javax.swing.JTextField txtnumerodefactura;
    private javax.swing.JTextField txtnumeroestablecimiento;
    private javax.swing.JTextField txtnumerofacturero;
    private javax.swing.JTextField txtplazo;
    private javax.swing.JTextField txtpropina;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txtsubtotalsinimpuestos;
    private javax.swing.JTextField txttelefono;
    private javax.swing.JTextField txttotaldescuento;
    private javax.swing.JTextField txtunidadtiempo;
    private javax.swing.JTextField txtvalortotal;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        AñadirProductoenFacturacion pro = new AñadirProductoenFacturacion();
        pro.setVisible(true);
        
    }
}
