
package bimax3.pkg0;

import BasedeDatos.ConexionMySQL;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;


public class Facturar {
    int numeroproductos;
    public String vector1[]=new String[5];
    public String vector2[]=new String[6]; 
    public String vector3[]=new String[5];
    public int codigo1;
    public int codigoPorcentaje1;
    public int tarifa1;
    public String claveAcceso;
    public String xmlSinFirmarDireccion;
    public String direccionFirmaElectronica;
    public String contraseñaFirmaElectronica;
    public String carpetaDocumentos;
    public String xmlNombre;
    public String numeroFactura;
    public String numeroEstablecimiento;
    public String[] dFacturador= new String[6];
    public String[] dFactura= new String[12];
    public String[] dComprador= new String[7];
    public String[] dFinales= new String[15];
    
    public void cantidadproductos(int numeroproductos){
    this.numeroproductos=numeroproductos;
    }
    public String matriz[][]=new String[numeroproductos][7];
    String matrizProductos[][]=new String[numeroproductos][10]; 
      
      
    public void generarxml(String vector1[],String matriz[][],String vector2[],String vector3[],String matrizProductos1[][]){
    
        this.vector1=vector1;
        this.matriz=matriz;
        this.vector2=vector2;
        this.vector3=vector3;
        this.matrizProductos=matrizProductos1;
       
    //Obtener Datos del Contribuyente
    String CorreoElectronico=null;
    String razonSocial1=null;
    String nombreComercial1=null;
    String ruc1=null;
    String dirMatriz1=null;
    String obligadoContabilidad1=null;
    try{
        Connection con =  ConexionMySQL.getConexion();
        PreparedStatement ps;
        ResultSet rs;
        ps = con.prepareStatement("SELECT * FROM administrador");
        rs=ps.executeQuery();
       while (rs.next()){
      ruc1=""+rs.getString("RUC");
      razonSocial1=rs.getString("RAZONSOCIAL");
     nombreComercial1=rs.getString("NOMBRECOMERCIAL");
     CorreoElectronico=rs.getString("CORREOELECTRONICO");
     dirMatriz1=rs.getString("DIRECCIONMATRIZ");
     obligadoContabilidad1=rs.getString("OBLIGADOCONTABILIDAD");
     direccionFirmaElectronica=rs.getString("FIRMAELECTRONICA");
     contraseñaFirmaElectronica=rs.getString("CONTRASEÑAFIRMAELECTRONICA");
   }
       dFacturador[0]=razonSocial1;
       dFacturador[1]=nombreComercial1;
       dFacturador[2]=dirMatriz1;
       dFacturador[3]="";
       dFacturador[4]=obligadoContabilidad1;
       dFacturador[5]=ruc1;
        }catch(SQLException e){
    JOptionPane.showMessageDialog(null,e);
        }
    //Obtener datos de la FACTURA (Numero de Factura, Facturero, NumeroEstablecimiento)
    String ambiente1=vector2[0];
    String codDoc1="01";//Codigo que identifica que se trata de una factura
    String estab1=vector2[1];
    String ptoEmail1=vector2[2];
    String secuencial1=vector2[3];
    numeroFactura=secuencial1;
    numeroEstablecimiento=estab1;
    //Obtener la fecha
    LocalDate fecha=LocalDate.now();
    int año=fecha.getYear();
    int mes =fecha.getMonthValue();
    int dia=fecha.getDayOfMonth();
    
    int tipoEmision1=1;
    //Calcular la clave de acceso
    String dia3; 
    String mes3; 
    if (dia<10){
    dia3="0"+dia;
    }else{
    dia3=""+dia;
    }
    if (mes<10){
    mes3="0"+mes;
    }else{
    mes3=""+mes;
    }
    String fechaEmision1=""+dia3+"/"+mes3+"/"+año;
    String año3=""+año;
    String[] dia4 = dia3.split("(?<=.)");
    String[] mes4 = mes3.split("(?<=.)");
    String[] año4 = año3.split("(?<=.)");
    String codDoc3=codDoc1;
    String[] codDoc4 = codDoc3.split("(?<=.)");
    String ruc3=ruc1;
    String[] ruc4 = ruc3.split("(?<=.)");
    String ambiente3=ambiente1;
    String[] ambiente4 = ambiente3.split("(?<=.)");
    String estab3=estab1;
    String[] estab4 = estab3.split("(?<=.)");
    String ptoEmail3=ptoEmail1;
    String[] ptoEmail4 = ptoEmail3.split("(?<=.)");
    String secuencial3=secuencial1;
    String[] secuencial4 = secuencial3.split("(?<=.)");
    String codigoverificador3="12345678";
    String[] codigoverificador4 = codigoverificador3.split("(?<=.)");
    String tipoemision3=""+tipoEmision1;
    String[] tipoemision4 = tipoemision3.split("(?<=.)");
      int vectorcod[]=new int[49];
      String[] auxcod=new String[49];
      int sum=0;
      for(int i = 0; i < dia4.length; i++) {
          vectorcod[i]=Integer.parseInt(dia4[i]);
          auxcod[i]=dia4[i];
          sum=sum+1;
        }
        for (String mes41 : mes4) {
            vectorcod[sum] = Integer.parseInt(mes41);
            auxcod[sum]=mes41;
            sum=sum+1;
        }
        for (String año41 : año4) {
            vectorcod[sum] = Integer.parseInt(año41);
            auxcod[sum]=año41;
            sum=sum+1;
        }
        for (String codDoc41 : codDoc4) {
            vectorcod[sum] = Integer.parseInt(codDoc41);
            auxcod[sum]=codDoc41;
            sum=sum+1;
        }
        for (String ruc41 : ruc4) {
            vectorcod[sum] = Integer.parseInt(ruc41);
            auxcod[sum]=ruc41;
            sum=sum+1;
        }
        for (String ambiente41 : ambiente4) {
            vectorcod[sum] = Integer.parseInt(ambiente41);
            auxcod[sum]=ambiente41;
            sum=sum+1;
        }
        for (String estab41 : estab4) {
            vectorcod[sum] = Integer.parseInt(estab41);
            auxcod[sum]=estab41;
            sum=sum+1;
        }
        for (String ptoEmail41 : ptoEmail4) {
            vectorcod[sum] = Integer.parseInt(ptoEmail41);
            auxcod[sum]=ptoEmail41;
            sum=sum+1;
        }
        for (String secuencial41 : secuencial4) {
            vectorcod[sum] = Integer.parseInt(secuencial41);
            auxcod[sum]=secuencial41;
            sum=sum+1;
        }
        for (String codigoverificador41 : codigoverificador4) {
            vectorcod[sum] = Integer.parseInt(codigoverificador41);
            auxcod[sum]=codigoverificador41;
            sum=sum+1;
        }
        for (String tipoemision41 : tipoemision4) {
            vectorcod[sum] = Integer.parseInt(tipoemision41);
            auxcod[sum]=tipoemision41;
            sum=sum+1;
        }
        int factor=2;
        int producto=0;
        int sumaaux=0;
      for(int i = 0; i < 48; i++) {
         producto=factor*vectorcod[47-i];
         sumaaux=sumaaux+producto;
         if(factor<7){
         factor=factor;
         }else{
         factor=1;
         }
         factor=factor+1;
        }
      int mod11=0;
      mod11=sumaaux%11;
      
      int resultadomod=11-mod11;
      if (resultadomod==11){
      resultadomod=0;
      }else{
          if (resultadomod==10){
          resultadomod=1;
          }else{
          resultadomod=resultadomod;
          }
      }
    
      vectorcod[48]=resultadomod;
      auxcod[48]=""+resultadomod;
      
    String codigoverificador= String.join("", auxcod);
    String claveAcceso1=codigoverificador;
    claveAcceso=claveAcceso1;
    
    //tomar Hora
    LocalDateTime hora=LocalDateTime.now();
    int hor= hora.getHour();
    int min= hora.getMinute();
    int seg= hora.getSecond();
    dFactura[0]=estab1;
    dFactura[1]=ptoEmail1;
    dFactura[2]=secuencial1;
    dFactura[3]=claveAcceso1;
    dFactura[4]=""+dia3;
    dFactura[5]=""+mes3;
    dFactura[6]=""+año;
    dFactura[7]=""+hor;
    dFactura[8]=""+min;
    dFactura[9]=""+seg;
    dFactura[10]=ambiente1;
    dFactura[11]="NORMAL";
    //Datos del comprador
    String concumidorfinal="9999999999999";
    String tipodeIdentificacionComprador1="05";
    int longitudcedula=vector1[0].length();
    if (longitudcedula==10){
    tipodeIdentificacionComprador1="05";
    }else{
        if(vector1[0].equals(concumidorfinal)){
        tipodeIdentificacionComprador1="07";
        }else{
        tipodeIdentificacionComprador1="04";
        }
    
    }
    
    String identificacionComprador1=vector1[0];
    String razonSocialComprador1=vector1[1];
    String campoadicionalemail=vector1[2];
    String campoadicionaltelefono=vector1[3];
    String direccionComprador1=vector1[4];
    String campoadicionaldireccion=vector1[4];
    dComprador[0]=razonSocialComprador1;
    dComprador[1]=identificacionComprador1;
    dComprador[2]=direccionComprador1;
    dComprador[3]="";
    dComprador[4]="";
    dComprador[5]=campoadicionalemail;
    dComprador[6]=campoadicionaltelefono;
    //Datos de la forma de pago
    String moneda1="DOLAR";
    //Determinar la forma de pago
    String forma1="SIN UTILIZACION DEL SISTEMA FINANCIERO";
    String forma2="COMPENSACION DE DEUDAS";
    String forma3="TARJETA DE DEBITO";
    String forma4="DINERO ELECTRONICO";
    String forma5="TARJETA PREPAGO";
    String forma6="TARJETA DE CREDITO";
    String forma7="OTROS CON UTILIZACION DEL SISTEMA FINANCIERO";
    String forma8="ENDOSO DE TITULOS";
    String formap=vector2[4];
    String formadePago1="20";
    if(formap.equals(forma1)){
    formadePago1="01";
    }else{
    if(formap.equals(forma2)){
    formadePago1="15";
    }else{
    if(formap.equals(forma3)){
    formadePago1="16";
    }else{
    if(formap.equals(forma4)){
    formadePago1="17";
    }else{
    if(formap.equals(forma5)){
    formadePago1="18";
    }else{
    if(formap.equals(forma6)){
    formadePago1="19";
    }else{
    if(formap.equals(forma8)){
    formadePago1="21";
    }else{
    formadePago1="20";
    }
    }
    }
    }
    }
    }
    }
    
    int plazo1=Integer.parseInt(vector2[5]);
    String unidadTiempo1="dias";
    
    //Datos de la compra en general
    double totalSinImpuestos1=Double.parseDouble(vector3[0]);
    double totalDescuento1=Double.parseDouble(vector3[1]);
    double propina1=Double.parseDouble(vector3[2]);
    double total1=Double.parseDouble(vector3[3]);
    double calcu12=totalSinImpuestos1*12/100;
    DecimalFormat df = new DecimalFormat("#.00");
    
    
    dFinales[0]="";
    dFinales[1]="";
    dFinales[2]="";
    dFinales[3]="";
    dFinales[7]="0.00";
    String compararf="IVA 0%";
    String comparar1f="No Objeto de Impuesto";
    String comparar2f="Exento de IVA";
    
    if(compararf.equals(matriz[0][6])){
    dFinales[0]="0.00";
    dFinales[1]=""+totalSinImpuestos1;
    dFinales[2]="0.00";
    dFinales[3]="0.00";
    }else{
        if(comparar1f.equals(matriz[0][6])){
    dFinales[0]="0.00";
    dFinales[1]="0.00";
    dFinales[2]=""+totalSinImpuestos1;
    dFinales[3]="0.00";
    }else{
            if(comparar2f.equals(matriz[0][6])){
    dFinales[0]="0.00";
    dFinales[1]="0.00";
    dFinales[2]="0.00";
    dFinales[3]=""+totalSinImpuestos1;
    }else{
    dFinales[0]=""+totalSinImpuestos1;
    dFinales[1]="0.00";
    dFinales[2]="0.00";
    dFinales[3]="0.00";
    dFinales[7]=""+calcu12;
    }
    }}
    
    
    
    dFinales[4]=""+totalSinImpuestos1;
    dFinales[5]=""+totalDescuento1;
    dFinales[6]="0.00";
    
    dFinales[8]="0.00";
    dFinales[9]="0.00";
    dFinales[10]=""+propina1;
    dFinales[11]=""+df.format(total1);
    dFinales[12]="0.00";
    dFinales[13]="0.00";
    dFinales[14]=""+formadePago1+"-"+formap;
    //Inicio de generacion de XML
    Element raiz = new Element("factura");
    raiz.setAttribute("id","comprobante");
    raiz.setAttribute("version","1.1.0");
    Document document = new Document(raiz);
    document.setBaseURI("comprobante");
    document.setProperty("Id", "comprobante");
    Element infoTributaria = new Element("infoTributaria");
    Element infoFactura = new Element("infoFactura");
    Element detalles = new Element("detalles");
    Element infoAdicional = new Element("infoAdicional");
    raiz.addContent(infoTributaria);
    raiz.addContent(infoFactura);
    raiz.addContent(detalles);
    raiz.addContent(infoAdicional);
    
    Element ambiente = new Element("ambiente");
    ambiente.setText(""+ambiente1);
    Element tipoEmision = new Element("tipoEmision");
    tipoEmision.setText(""+tipoEmision1);
    Element razonSocial = new Element("razonSocial");
    razonSocial.setText(razonSocial1);
    Element nombreComercial = new Element("nombreComercial");
    nombreComercial.setText(nombreComercial1);
    Element ruc = new Element("ruc");
    ruc.setText(ruc1);
    Element claveAcceso = new Element("claveAcceso");
    claveAcceso.setText(claveAcceso1);
    Element codDoc = new Element("codDoc");
    codDoc.setText(codDoc1);
    Element estab = new Element("estab");
    estab.setText(estab1);
    Element ptoEmail = new Element("ptoEmi");
    ptoEmail.setText(ptoEmail1);
    Element secuencial = new Element("secuencial");
    secuencial.setText(secuencial1);
    Element dirMatriz = new Element("dirMatriz");
    dirMatriz.setText(dirMatriz1);
    
    infoTributaria.addContent(ambiente);
    infoTributaria.addContent(tipoEmision);
    infoTributaria.addContent(razonSocial);
    infoTributaria.addContent(nombreComercial);
    infoTributaria.addContent(ruc);
    infoTributaria.addContent(claveAcceso);
    infoTributaria.addContent(codDoc);
    infoTributaria.addContent(estab);
    infoTributaria.addContent(ptoEmail);
    infoTributaria.addContent(secuencial);
    infoTributaria.addContent(dirMatriz);
    
    Element fechaEmision = new Element("fechaEmision");
    fechaEmision.setText(""+fechaEmision1);
    Element obligadoContabilidad = new Element("obligadoContabilidad");
    obligadoContabilidad.setText(""+obligadoContabilidad1);
    Element tipoIdentificacionComprador = new Element("tipoIdentificacionComprador");
    tipoIdentificacionComprador.setText(tipodeIdentificacionComprador1);
    Element razonSocialComprador = new Element("razonSocialComprador");
    razonSocialComprador.setText(razonSocialComprador1);
    Element identificacionComprador = new Element("identificacionComprador");
    identificacionComprador.setText(identificacionComprador1);
    Element direccionComprador = new Element("direccionComprador");
    direccionComprador.setText(direccionComprador1);
    
    Element totalSinImpuestos = new Element("totalSinImpuestos");
    totalSinImpuestos.setText(""+totalSinImpuestos1);
    Element totalDescuento = new Element("totalDescuento");
    totalDescuento.setText(""+totalDescuento1);
    infoFactura.addContent(fechaEmision);
    infoFactura.addContent(obligadoContabilidad);
    infoFactura.addContent(tipoIdentificacionComprador);
    infoFactura.addContent(razonSocialComprador);
    infoFactura.addContent(identificacionComprador);
    infoFactura.addContent(direccionComprador);
    infoFactura.addContent(totalSinImpuestos);
    infoFactura.addContent(totalDescuento);
    //Desde aqui se deben repetir las veces que exista un producto
    //Datos de la compra por producto
    //Impuestos aplicado al producto
    //Determinar que % de IVA aplico al producto
    Element totalConImpuestos = new Element("totalConImpuestos");
    String comparar="IVA 0%";
    String comparar1="No Objeto de Impuesto";
    String comparar2="Exento de IVA";
    for(int i = 0; i < numeroproductos; i++) {
    if(comparar.equals(matriz[i][6])){
    codigo1=2;
    codigoPorcentaje1=0;
    tarifa1=0;
    }else{
        if(comparar1.equals(matriz[i][6])){
    codigo1=2;
    codigoPorcentaje1=6;
    tarifa1=0;
    }else{
            if(comparar2.equals(matriz[i][6])){
    codigo1=2;
    codigoPorcentaje1=7;
    tarifa1=0;
    }else{
    codigo1=2;
    codigoPorcentaje1=2;
    tarifa1=12;
    }
    }
    }
    double baseImponible1=Double.parseDouble(matriz[i][5]);
    double valor1=(baseImponible1*tarifa1)/100;
    
    Element totalImpuesto = new Element("totalImpuesto");
    totalConImpuestos.addContent(totalImpuesto);
    Element codigo = new Element("codigo");
    codigo.setText(""+codigo1);
    Element codigoPorcentaje = new Element("codigoPorcentaje");
    codigoPorcentaje.setText(""+codigoPorcentaje1);
    Element baseImponible = new Element("baseImponible");
    baseImponible.setText(""+baseImponible1);
    Element valor = new Element("valor");
    valor.setText(""+valor1);
    totalImpuesto.addContent(codigo);
    totalImpuesto.addContent(codigoPorcentaje);
    totalImpuesto.addContent(baseImponible);
    totalImpuesto.addContent(valor);
    }
    infoFactura.addContent(totalConImpuestos);

    Element propina = new Element("propina");
    propina.setText(""+propina1);
    Element importeTotal = new Element("importeTotal");
    importeTotal.setText(""+total1);
    Element moneda = new Element("moneda");
    moneda.setText(""+moneda1);
    Element pagos = new Element("pagos");
    Element pago = new Element("pago");
    pagos.addContent(pago);
    Element formaPago = new Element("formaPago");
    formaPago.setText(""+formadePago1);
    Element total = new Element("total");
    total.setText(""+total1);
    Element plazo = new Element("plazo");
    plazo.setText(""+plazo1);
    Element unidadTiempo = new Element("unidadTiempo");
    unidadTiempo.setText(""+unidadTiempo1);
    pago.addContent(formaPago);
    pago.addContent(total);
    pago.addContent(plazo);
    pago.addContent(unidadTiempo);
    infoFactura.addContent(propina);
    infoFactura.addContent(importeTotal);
    infoFactura.addContent(moneda);
    infoFactura.addContent(pagos);
    
    
    for(int i = 0; i < numeroproductos; i++) {
    if(comparar.equals(matriz[i][6])){
    codigo1=2;
    codigoPorcentaje1=0;
    tarifa1=0;
    }else{
        if(comparar1.equals(matriz[i][6])){
    codigo1=2;
    codigoPorcentaje1=6;
    tarifa1=0;
    }else{
            if(comparar2.equals(matriz[i][6])){
    codigo1=2;
    codigoPorcentaje1=7;
    tarifa1=0;
    }else{
    codigo1=2;
    codigoPorcentaje1=2;
    tarifa1=12;
    }
    }
    }
    double baseImponible1=Double.parseDouble(matriz[i][5]);
    double valor1=(baseImponible1*tarifa1)/100;
    Element detalle = new Element("detalle");
    detalles.addContent(detalle);
    Element codigoPrincipal = new Element("codigoPrincipal"); 
    codigoPrincipal.setText(matriz[i][1]);
    Element descripcion = new Element("descripcion");
    descripcion.setText(matriz[i][2]);
    Element cantidad = new Element("cantidad");
    cantidad.setText(matriz[i][0]);
    Element precioUnitario = new Element("precioUnitario");
    precioUnitario.setText(matriz[i][3]);
    Element descuento = new Element("descuento");
    descuento.setText(matriz[i][4]);
    Element precioTotalSinImpuesto = new Element("precioTotalSinImpuesto");
    precioTotalSinImpuesto.setText(matriz[i][5]);
    Element impuestos = new Element("impuestos");
    Element impuesto = new Element("impuesto");
    impuestos.addContent(impuesto);
    Element codigoimpuesto = new Element("codigo");
    codigoimpuesto.setText(""+codigo1);
    Element codigoPorcentajeimpuesto = new Element("codigoPorcentaje");
    codigoPorcentajeimpuesto.setText(""+codigoPorcentaje1);
    Element tarifa = new Element("tarifa");
    tarifa.setText(""+tarifa1);
    Element baseImponibleImpuesto = new Element("baseImponible");
    baseImponibleImpuesto.setText(matriz[i][5]);
    Element valorImpuesto = new Element("valor");
    valorImpuesto.setText(""+valor1);
    impuesto.addContent(codigoimpuesto);
    impuesto.addContent(codigoPorcentajeimpuesto);
    impuesto.addContent(tarifa);
    impuesto.addContent(baseImponibleImpuesto);
    impuesto.addContent(valorImpuesto);
    
    detalle.addContent(codigoPrincipal);
    detalle.addContent(descripcion);
    detalle.addContent(cantidad);
    detalle.addContent(precioUnitario);
    detalle.addContent(descuento);
    detalle.addContent(precioTotalSinImpuesto);
    detalle.addContent(impuestos);
    matrizProductos[i][0]=""+matriz[i][1];
    matrizProductos[i][1]=""+matriz[i][1];
    matrizProductos[i][2]=""+matriz[i][0];
    matrizProductos[i][3]=""+matriz[i][2];
    matrizProductos[i][4]=""+matriz[i][2];
    matrizProductos[i][5]=""+matriz[i][3];
    matrizProductos[i][6]="0.00";
    matrizProductos[i][7]="0.00";
    matrizProductos[i][8]=""+matriz[i][4];
    matrizProductos[i][9]=""+matriz[i][5];
    }
    Element campoAdicionalEmail = new Element("campoAdicional");
    campoAdicionalEmail.setText(""+campoadicionalemail);
    Element campoAdicionalDireccion = new Element("campoAdicional");
    campoAdicionalDireccion.setText(""+campoadicionaldireccion);
    Element campoAdicionalTelefono = new Element("campoAdicional");
    campoAdicionalTelefono.setText(""+campoadicionaltelefono);
    campoAdicionalEmail.setAttribute("nombre","Email1");
    campoAdicionalDireccion.setAttribute("nombre","Direccion");
    campoAdicionalTelefono.setAttribute("nombre","Telefono");
    infoAdicional.addContent(campoAdicionalEmail);
    infoAdicional.addContent(campoAdicionalDireccion);
    infoAdicional.addContent(campoAdicionalTelefono);
    //Guardar el documento
    XMLOutputter xm=new XMLOutputter();
    xm.setFormat(Format.getPrettyFormat());
    carpetaDocumentos="C:\\Users\\henry\\Desktop\\FACTURASELECTRONICAS\\";
    xmlNombre="FACTURA-"+estab1+ptoEmail1+secuencial1;
    xmlSinFirmarDireccion=carpetaDocumentos+xmlNombre+".xml";
        try {
            xm.output(document, new FileWriter(xmlSinFirmarDireccion));
        } catch (IOException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        }
        firmar();
        generarPDF();
        controlador();
        
    }
    
public void controlador(){
        try {
            enviarSRI();
            autorizarSRI();
            verificarCorrectaFacturacion();
        } catch (SAXException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}
   
    public void generarPDF(){
        try {
            PDF pdf = new PDF();
            pdf.manipulatePdf(carpetaDocumentos+xmlNombre+".pdf", dFacturador,dFactura,dComprador,dFinales,numeroproductos,matrizProductos);
        } catch (Exception ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void firmar(){
        String xmlPath = xmlSinFirmarDireccion;
        String pathSignature = direccionFirmaElectronica;
        String passSignature = contraseñaFirmaElectronica;
        String xmlPathout = xmlSinFirmarDireccion;
        Conexion c1 = new Conexion();
        c1.conectar(0);
        GenericXMLSignature firm = new GenericXMLSignature(xmlPath,pathSignature,passSignature,xmlPathout);
        try {
            firm.realizarfirma();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarSRI() throws SAXException, IOException, XPathExpressionException, ParserConfigurationException{
            String URLvalidacion= null;
            String URLAutorizacion=null;
            String claveAcceso1=claveAcceso;
            String xmlPath = xmlSinFirmarDireccion;
            String xmlPathout = carpetaDocumentos+xmlNombre+"Autorizado.xml";
            //Determinar en que ambiente se encuentran 
            System.out.println("Este es el ambiente"+vector2[0]);
            String ambientep1="2";
            if(ambientep1.equals(vector2[0])){
            URLvalidacion= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
            URLAutorizacion= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
            }else{
            URLvalidacion= "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
            URLAutorizacion= "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
            }

        //ValidarFirma al SRI
        EnviarFirma enviar=new EnviarFirma();
        try {
            enviar.validarcomprobanteprueba(xmlPath, URLvalidacion,xmlPathout);
            
        } catch (IOException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void autorizarSRI() throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, XPathExpressionException{
            String URLvalidacion= null;
            String URLAutorizacion=null;
            String claveAcceso1=claveAcceso;
            String xmlPathout = carpetaDocumentos+xmlNombre+"Autorizado.xml";
            //Determinar en que ambiente se encuentran 
            String ambientep1="2";
            if(ambientep1.equals(vector2[0])){
            URLvalidacion= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
            URLAutorizacion= "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
            }else{
            URLvalidacion= "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
            URLAutorizacion= "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
            }
        //Autorizar firma al SRI
        EnviarFirma enviar=new EnviarFirma();
        validarAutorizacionSRI auto = new validarAutorizacionSRI();
        int valor = auto.validar(xmlPathout);
        if (valor>0){
                try {
                    enviar.autorizarcomprobante(claveAcceso1, URLAutorizacion,xmlPathout);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else{
        System.out.println("Error en la validacion del comprobante");
        }
    }
    public void verificarCorrectaFacturacion() throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, XPathExpressionException{
        
        validarAutorizacionSRI auto = new validarAutorizacionSRI();
        String xmlPathout = carpetaDocumentos+xmlNombre+"Autorizado.xml";
        int valor = auto.autorizacion(xmlPathout);
        int numerofactura= Integer.parseInt(numeroFactura)+1;
        String nfac=""+numerofactura;
        int lonnfac= nfac.length();
        if(lonnfac==1){
        nfac="00000000"+numerofactura;
        }else{
        if(lonnfac==2){
        nfac="0000000"+numerofactura;
        }else{
        if(lonnfac==3){
        nfac="000000"+numerofactura;
        }else{
        if(lonnfac==4){
        nfac="00000"+numerofactura;
        }else{
        if(lonnfac==5){
        nfac="0000"+numerofactura;
        }else{
        if(lonnfac==6){
        nfac="000"+numerofactura;
        }else{
        if(lonnfac==7){
        nfac="00"+numerofactura;
        }else{
        if(lonnfac==8){
        nfac="0"+numerofactura;
        }else{
        nfac=""+numerofactura;
        }
        }
        }
        }
        }
        }
        }
        }
        AñadirEstablecimientoJ est= new AñadirEstablecimientoJ(numeroEstablecimiento,null,nfac,null,null);
        if (valor>0){
            try {
                est.actualizar();
                correoElectronico enviarCorreoElectronico = new correoElectronico(carpetaDocumentos+xmlNombre+".xml",xmlNombre+".xml",carpetaDocumentos+xmlNombre+".pdf",xmlNombre+".pdf");
            JOptionPane.showMessageDialog(null, "Se ha facturado correctamente");
            } catch (MessagingException ex) {
                Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "No se ha emitido la factura");
            }
         
        }else{
        JOptionPane.showMessageDialog(null, "No se ha emitido la factura");
        }
    }


}
