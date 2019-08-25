/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import baza.Kviz;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Ivana
 */
public class ServerForma extends javax.swing.JFrame {

    /**
     * Creates new form ServerForma
     */
    List<Kviz> kvizovi;
    int izabranKviz;
    Server server;

    public ServerForma(List<Kviz> kvizovi) {
        this.kvizovi = kvizovi;
        if (kvizovi.size() > 0) {
            izabranKviz = 0;
        } else {
            izabranKviz = -1;
        }
        initComponents();
        cmbKviz.setModel(new DefaultComboBoxModel(kvizovi.stream().map(kviz -> kviz.getNaziv()).toArray()));
    }

    public void omoguciSlanjeOdgovora() {
        btnPosaljiOdgovor.setEnabled(true);
    }

    public void onemoguciSlanjeOdgovora() {
        btnPosaljiOdgovor.setEnabled(false);
    }

    public void prikaziBrojPoena(String poeni) {
        txtUkupnoPoena.setText(poeni);
    }

    public void prikaziPitanje(String pitanje) {

        txtPitanje.setText(pitanje);
        if (!pitanje.equals("")) {
            omoguciSlanjeOdgovora();
        }
        ocistiOdgovor();
        prikaziPoruku("");
    }

    public void prikaziPoruku(String pitanje) {
        txtPoruka.setText(pitanje);
    }

    public void ocistiOdgovor() {
        txtOdgovor.setText("");
    }

    public void postaviStatistiku() {
        DefaultTableModel model = (DefaultTableModel) tblStatistika.getModel();
        if (model.getRowCount() == 2) {
            model.removeRow(0);
            model.removeRow(0);
        }
        model.addRow(new Object[]{"Tim", "0", "0", "0"});
        model.addRow(new Object[]{"Server", "0", "0", "0"});
    }

    public void azurirajTabelu(double[] tim, double[] server) {
        DefaultTableModel model = (DefaultTableModel) tblStatistika.getModel();

        for (int i = 0; i < 3; i++) {
            model.setValueAt(tim[i], 0, i + 1);
        }
        for (int i = 0; i < 3; i++) {
            model.setValueAt(server[i], 1, i + 1);
        }
    }

    public void omoguciPokretanjeKviza() {
        btnPokreniKviz.setEnabled(true);
    }

    public void onemoguciPokretanjeKviza() {
        btnPokreniKviz.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbKviz = new javax.swing.JComboBox();
        btnPokreniKviz = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPitanje = new javax.swing.JTextField();
        txtOdgovor = new javax.swing.JTextField();
        btnPosaljiOdgovor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPoruka = new javax.swing.JTextField();
        txtUkupnoPoena = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStatistika = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Kviz:");

        cmbKviz.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKviz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKvizActionPerformed(evt);
            }
        });

        btnPokreniKviz.setText("Pokreni kviz");
        btnPokreniKviz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPokreniKvizActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Kviz"));

        jLabel2.setText("Pitanje:");

        jLabel3.setText("Odgovor:");

        txtPitanje.setEditable(false);

        btnPosaljiOdgovor.setText("Posalji odgovor");
        btnPosaljiOdgovor.setEnabled(false);
        btnPosaljiOdgovor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPosaljiOdgovorActionPerformed(evt);
            }
        });

        jLabel4.setText("Poruka:");

        jLabel5.setText("Ukupno poena:");

        txtPoruka.setEditable(false);

        txtUkupnoPoena.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPitanje)
                            .addComponent(txtOdgovor, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPosaljiOdgovor))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtUkupnoPoena))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(59, 59, 59)
                        .addComponent(txtPoruka)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPitanje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtOdgovor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPosaljiOdgovor)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPoruka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUkupnoPoena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        tblStatistika.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Igrac", "Broj tacnih odgovora", "Broj netacnih odgovora", "Ukupno poena"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblStatistika);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbKviz, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPokreniKviz))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbKviz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(btnPokreniKviz)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKvizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKvizActionPerformed
        JComboBox cb = (JComboBox) evt.getSource();
        izabranKviz = cb.getSelectedIndex();
    }//GEN-LAST:event_cmbKvizActionPerformed

    private void btnPokreniKvizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPokreniKvizActionPerformed
        if (izabranKviz >= 0) {
            this.server = new Server(this, izabranKviz + 1);
        }

    }//GEN-LAST:event_btnPokreniKvizActionPerformed

    private void btnPosaljiOdgovorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPosaljiOdgovorActionPerformed
        server.postaviOdgovorServera(txtOdgovor.getText());
    }//GEN-LAST:event_btnPosaljiOdgovorActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPokreniKviz;
    private javax.swing.JButton btnPosaljiOdgovor;
    private javax.swing.JComboBox cmbKviz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblStatistika;
    private javax.swing.JTextField txtOdgovor;
    private javax.swing.JTextField txtPitanje;
    private javax.swing.JTextField txtPoruka;
    private javax.swing.JTextField txtUkupnoPoena;
    // End of variables declaration//GEN-END:variables
}