/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InterfazGrafica;

/**
 *
 * @author henry
 */
public class MenuPrincipal extends javax.swing.JFrame {

    public MenuPrincipal() {
        initComponents();
        this.setExtendedState(6);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Paneladministracion = new javax.swing.JPanel();
        administrador = new javax.swing.JButton();
        proveedores = new javax.swing.JButton();
        balance = new javax.swing.JButton();
        productos = new javax.swing.JButton();
        proformas = new javax.swing.JButton();
        usuarios = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabladecontrolprincipal = new javax.swing.JTable();
        PanelUsuariosProductos = new javax.swing.JPanel();
        facturacion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);

        administrador.setText("Administrador");
        administrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administradorActionPerformed(evt);
            }
        });

        proveedores.setText("Proveedores");
        proveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proveedoresActionPerformed(evt);
            }
        });

        balance.setText("Balance");

        productos.setText("Productos");
        productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosActionPerformed(evt);
            }
        });

        proformas.setText("Proformas");

        usuarios.setText("Clientes");
        usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PaneladministracionLayout = new javax.swing.GroupLayout(Paneladministracion);
        Paneladministracion.setLayout(PaneladministracionLayout);
        PaneladministracionLayout.setHorizontalGroup(
            PaneladministracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneladministracionLayout.createSequentialGroup()
                .addGroup(PaneladministracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PaneladministracionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(administrador, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PaneladministracionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PaneladministracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(proveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                            .addComponent(balance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(proformas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(usuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        PaneladministracionLayout.setVerticalGroup(
            PaneladministracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneladministracionLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(administrador)
                .addGap(18, 18, 18)
                .addComponent(proveedores)
                .addGap(18, 18, 18)
                .addComponent(productos)
                .addGap(18, 18, 18)
                .addComponent(usuarios)
                .addGap(18, 18, 18)
                .addComponent(proformas)
                .addGap(18, 18, 18)
                .addComponent(balance)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        Tabladecontrolprincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Numero Factura", "Nombre Cliente", "RUC/CEDULA", "Monto", "Detalles"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabladecontrolprincipal);

        facturacion.setText("Facturacion");
        facturacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelUsuariosProductosLayout = new javax.swing.GroupLayout(PanelUsuariosProductos);
        PanelUsuariosProductos.setLayout(PanelUsuariosProductosLayout);
        PanelUsuariosProductosLayout.setHorizontalGroup(
            PanelUsuariosProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuariosProductosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(facturacion)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        PanelUsuariosProductosLayout.setVerticalGroup(
            PanelUsuariosProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUsuariosProductosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(facturacion)
                .addContainerGap(244, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Paneladministracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelUsuariosProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(PanelUsuariosProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(Paneladministracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(189, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void administradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administradorActionPerformed
        InterfazAdministrador admi = new InterfazAdministrador();
        admi.setVisible(true);
    }//GEN-LAST:event_administradorActionPerformed

    private void proveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedoresActionPerformed
        InterfazProveedores proveedor = new InterfazProveedores();
        proveedor.setVisible(true);
    }//GEN-LAST:event_proveedoresActionPerformed

    private void usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuariosActionPerformed
        InterfazUsuarios usu = new InterfazUsuarios();
        usu.setVisible(rootPaneCheckingEnabled);

    }//GEN-LAST:event_usuariosActionPerformed

    private void productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosActionPerformed
        InterfazProductos pro = new InterfazProductos();
        pro.setVisible(true);
    }//GEN-LAST:event_productosActionPerformed

    private void facturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturacionActionPerformed
        
        Facturacion factu= new Facturacion();
        factu.setVisible(true);
    }//GEN-LAST:event_facturacionActionPerformed


    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelUsuariosProductos;
    private javax.swing.JPanel Paneladministracion;
    private javax.swing.JTable Tabladecontrolprincipal;
    private javax.swing.JButton administrador;
    private javax.swing.JButton balance;
    private javax.swing.JButton facturacion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton productos;
    private javax.swing.JButton proformas;
    private javax.swing.JButton proveedores;
    private javax.swing.JButton usuarios;
    // End of variables declaration//GEN-END:variables
}
