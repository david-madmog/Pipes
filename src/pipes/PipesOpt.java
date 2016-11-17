/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PipesOpt.java
 *
 * Created on 23-Nov-2009, 11:19:17
 */

package pipes;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author davidp
 */
public class PipesOpt extends javax.swing.JFrame {
    PipesView mOwner ;

    Timer T ;
    private BufferedImage GreenImages ;

    /** Creates new form PipesOpt */
    public PipesOpt(PipesView owner) {
        mOwner = owner ;
        mOwner.Pause(true) ;

        initComponents();
        //Create the label table
        Hashtable labelTable1 = new Hashtable();
        labelTable1.put( new Integer( 0 ), new JLabel("None") );
        labelTable1.put( new Integer( 1 ), new JLabel("Short") );
        labelTable1.put( new Integer( 2 ), new JLabel("Medium") );
        labelTable1.put( new Integer( 3 ), new JLabel("Long") );
        labelTable1.put( new Integer( 4 ), new JLabel("Infinite") );
        sliInitialDelay.setLabelTable( labelTable1 );

        //Create the label table
        Hashtable labelTable2 = new Hashtable();
        labelTable2.put( new Integer( 1 ), new JLabel("Fast") );
        labelTable2.put( new Integer( 2 ), new JLabel("Medium") );
        labelTable2.put( new Integer( 3 ), new JLabel("Slow") );
        sliSpeed.setLabelTable( labelTable2 );

        // Get current classloader
        ClassLoader cl = this.getClass().getClassLoader();

        try {
            GreenImages = ImageIO.read(cl.getResource("pipes/resources/GreenPipes.bmp")) ;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        sliX.setValue(owner.xs) ;
        sliY.setValue(owner.ys) ;
        sliSpeed.setValue(owner.GreySpeed) ;
        sliInitialDelay.setValue(owner.TimerSpeed) ;

        ActionListener AL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TimerHandler() ;
            }
        } ;

        T = new Timer(2000, AL);
        T.setInitialDelay(1);
        T.start();

        DrawLogo() ;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlLogo = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        sliX = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        sliY = new javax.swing.JSlider();
        jLabel9 = new javax.swing.JLabel();
        sliInitialDelay = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        sliSpeed = new javax.swing.JSlider();
        jPanel4 = new javax.swing.JPanel();
        cmdOK = new javax.swing.JButton();
        cmdCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(pipes.PipesApp.class).getContext().getResourceMap(PipesOpt.class);
        jPanel1.setForeground(resourceMap.getColor("jPanel1.foreground")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18));
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        pnlLogo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlLogo.setName("pnlLogo"); // NOI18N
        pnlLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        jPanel2.add(jLabel6, gridBagConstraints);

        sliX.setMajorTickSpacing(8);
        sliX.setMaximum(32);
        sliX.setMinimum(8);
        sliX.setSnapToTicks(true);
        sliX.setValue(8);
        sliX.setName("sliX"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 11);
        jPanel2.add(sliX, gridBagConstraints);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel2.add(jLabel8, gridBagConstraints);

        sliY.setMajorTickSpacing(8);
        sliY.setMaximum(32);
        sliY.setMinimum(8);
        sliY.setPaintLabels(true);
        sliY.setPaintTicks(true);
        sliY.setSnapToTicks(true);
        sliY.setName("sliY"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 11);
        jPanel2.add(sliY, gridBagConstraints);

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel2.add(jLabel9, gridBagConstraints);

        sliInitialDelay.setMajorTickSpacing(1);
        sliInitialDelay.setMaximum(4);
        sliInitialDelay.setPaintLabels(true);
        sliInitialDelay.setPaintTicks(true);
        sliInitialDelay.setSnapToTicks(true);
        sliInitialDelay.setName("sliInitialDelay"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 11);
        jPanel2.add(sliInitialDelay, gridBagConstraints);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel2.add(jLabel3, gridBagConstraints);

        sliSpeed.setMajorTickSpacing(1);
        sliSpeed.setMaximum(3);
        sliSpeed.setMinimum(1);
        sliSpeed.setPaintLabels(true);
        sliSpeed.setPaintTicks(true);
        sliSpeed.setSnapToTicks(true);
        sliSpeed.setName("sliSpeed"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 11);
        jPanel2.add(sliSpeed, gridBagConstraints);

        jPanel4.setName("jPanel4"); // NOI18N

        cmdOK.setText(resourceMap.getString("cmdOK.text")); // NOI18N
        cmdOK.setName("cmdOK"); // NOI18N
        cmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOKActionPerformed(evt);
            }
        });

        cmdCancel.setText(resourceMap.getString("cmdCancel.text")); // NOI18N
        cmdCancel.setName("cmdCancel"); // NOI18N
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdCancel)
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdOK)
                    .addComponent(cmdCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2))
                            .addGap(18, 18, 18)
                            .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlLogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void TimerHandler() {
        DrawLogo() ;
    }

    private void DrawLogo() {
        Random R = new Random() ;
        Graphics G = pnlLogo.getGraphics() ;

        int Pattern = R.nextInt(16) ;
        G.drawImage(GreenImages, 0, 0, pnlLogo.getWidth(), pnlLogo.getHeight(), 100*Pattern, 0, 100*(Pattern+1), 100, null) ;
   //     pnlLogo.repaint();

    }

    private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOKActionPerformed
        formWindowClosed(null);
        SaveSettings( mOwner ) ;
        this.setVisible(false);
    }//GEN-LAST:event_cmdOKActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
          mOwner.Pause(false) ;
    }//GEN-LAST:event_formWindowClosed

    private void pnlLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLogoMouseClicked
        DrawLogo() ;
    }//GEN-LAST:event_pnlLogoMouseClicked

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        formWindowClosed(null);
        this.setVisible(false);
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void SaveSettings(PipesView owner) {
        owner.xs = sliX.getValue() ;
        owner.ys = sliY.getValue() ;
        owner.GreySpeed = sliSpeed.getValue() ;
        owner.TimerSpeed =  sliInitialDelay.getValue() ;

        owner.savePrefs() ;
        owner.CreateAndLinkCells();
    }


    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PipesOpt(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JSlider sliInitialDelay;
    private javax.swing.JSlider sliSpeed;
    private javax.swing.JSlider sliX;
    private javax.swing.JSlider sliY;
    // End of variables declaration//GEN-END:variables

}
