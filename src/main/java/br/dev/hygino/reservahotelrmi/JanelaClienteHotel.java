package br.dev.hygino.reservahotelrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class JanelaClienteHotel extends javax.swing.JFrame {
    
    private List<Quarto> quartosDesocupados;
    private List<Reserva> quartosReservados;
    
    public JanelaClienteHotel() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        btnReservar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstQuartosDesocupados = new javax.swing.JList<>();
        lbQuartosLivres = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtReservas = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbNome.setText("Nome");

        txtNome.setColumns(20);

        btnReservar.setText("Reservar");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lstQuartosDesocupados);

        lbQuartosLivres.setText("Quartos disponíveis");

        txtReservas.setColumns(20);
        txtReservas.setRows(5);
        jScrollPane2.setViewportView(txtReservas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbQuartosLivres)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbNome)
                        .addGap(18, 18, 18)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReservar))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReservar))
                .addGap(24, 24, 24)
                .addComponent(lbQuartosLivres)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        listarQuartosDesocupados();
        listarQuartosReservados();
    }//GEN-LAST:event_formWindowOpened
    
    private void listarQuartosReservados() {
        try {
            IGerenciadorQuartos stub = (IGerenciadorQuartos) Naming.lookup("rmi://127.0.0.1/hotel");
            quartosReservados = stub.exibirReservas();
            txtReservas.setText("");
            quartosReservados.forEach(r -> txtReservas.append(r + "\n"));
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(JanelaClienteHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listarQuartosDesocupados() {
        try {
            IGerenciadorQuartos stub = (IGerenciadorQuartos) Naming.lookup("rmi://127.0.0.1/hotel");
            quartosDesocupados = stub.exibirQuartosDesocupados();

            // quartosDesocupados.forEach(System.out::println);
            // Cria um modelo para o JList
            DefaultListModel<Quarto> modelo = new DefaultListModel<>();

            // Adiciona os quartos desocupados ao modelo
            for (Quarto quarto : quartosDesocupados) {
                modelo.addElement(quarto);
            }
            lstQuartosDesocupados.setModel(modelo);
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(JanelaClienteHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        String nome = txtNome.getText().trim(); // Remove espaços em branco
        Quarto quartoSelecionado = lstQuartosDesocupados.getSelectedValue();
        
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira seu nome.", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Sai do método se o nome estiver vazio
        }
        
        if (quartoSelecionado != null) {
            try {
                IGerenciadorQuartos stub = (IGerenciadorQuartos) Naming.lookup("rmi://127.0.0.1/hotel");
                stub.ocuparQuarto(quartoSelecionado, nome);
                JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                DefaultListModel<Quarto> modelo = (DefaultListModel<Quarto>) lstQuartosDesocupados.getModel();
                modelo.removeElement(quartoSelecionado);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(this, "Erro ao reservar o quarto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(JanelaClienteHotel.class.getName()).log(Level.SEVERE, null, e);
            } catch (NotBoundException | MalformedURLException e) {
                JOptionPane.showMessageDialog(this, "Erro de conexão com o servidor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(JanelaClienteHotel.class.getName()).log(Level.SEVERE, null, e);
            }

            // Atualiza a lista de quartos desocupados
            //listarQuartosDesocupados();
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum quarto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        listarQuartosReservados();
    }//GEN-LAST:event_btnReservarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JanelaClienteHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaClienteHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaClienteHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaClienteHotel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaClienteHotel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReservar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbQuartosLivres;
    private javax.swing.JList<Quarto> lstQuartosDesocupados;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextArea txtReservas;
    // End of variables declaration//GEN-END:variables
}
