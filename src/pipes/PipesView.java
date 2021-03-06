/*
 * PipesView.java
 */

package pipes;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * The application's main frame.
 *
 * This is where all the real work is done!
 */
public class PipesView extends FrameView {
    public int xs, ys ;
    public int TimerSpeed, GreySpeed ;
    PipeCell [][] Cells  ;
    Timer T ;
    int PreTTicks ;
    int TimerCounter = 0 ;
    OwnDrawPanel.DrawModes PrePauseMode ;
    int GreyingStep ;
    int Restarts = 0 ;
    PipesHighScores HS ;

    public PipesView(SingleFrameApplication app) {
        super(app);
        initComponents();
        ClassLoader cl = this.getClass().getClassLoader();
        try {
            this.getFrame().setIconImage(new ImageIcon(ImageIO.read(cl.getResource("pipes/resources/Icon.png"))).getImage());
        } catch (IOException ex) {
            Logger.getLogger(PipesView.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        loadPrefs() ; // saved settings
        HS = new PipesHighScores() ;
        CreateAndLinkCells() ;
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        ODP.HS = HS ;
        mainPanel.requestFocusInWindow() ;
}

    final void CreateAndLinkCells() {
        Cells = new PipeCell[xs][ys] ;
        
        /* Create cells */
        int i, j;
        for (i = 0; i< xs; i++) {
            for (j = 0; j< ys; j++) {
                Cells[i][j] = new PipeCell() ;
                Cells[i][j].MyX = i ;
                Cells[i][j].MyY = j ;
            }
        }

        /* Link cells to neighbours */
        for (i = 0; i< xs; i++) {
            for (j = 0; j< ys; j++) {
                if (i > 0)
                    Cells[i][j].Neighbours[3] = Cells[i-1][j];
                if (j < ys-1)
                    Cells[i][j].Neighbours[2] = Cells[i][j+1];
                if (i < xs-1)
                    Cells[i][j].Neighbours[1] = Cells[i+1][j];
                if (j > 0)
                    Cells[i][j].Neighbours[0] = Cells[i][j-1];
            }
        }
         
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        ODP.Cells = Cells ;
        Rectangle VisRect = new Rectangle() ;
        ODP.computeVisibleRect(VisRect);
        ODP.MyResize(VisRect.getSize().width, VisRect.getSize().height) ;

        newGameMenuItemActionPerformed(null) ;
    }
    
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PipesApp.getApplication().getMainFrame();
            aboutBox = new PipesAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PipesApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newGameMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        Pause = new javax.swing.JMenuItem();
        mnuOptions = new javax.swing.JMenu();
        MnuOptionsOptions = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        StatusPanel = new javax.swing.JPanel();
        Progress = new javax.swing.JProgressBar();
        ProgressLabel = new javax.swing.JLabel();
        ScoreLabel = new javax.swing.JLabel();
        mainPanel = new OwnDrawPanel();

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setMnemonic('F');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(pipes.PipesApp.class).getContext().getResourceMap(PipesView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newGameMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGameMenuItem.setMnemonic('N');
        newGameMenuItem.setText(resourceMap.getString("newGameMenuItem.text")); // NOI18N
        newGameMenuItem.setName("newGameMenuItem"); // NOI18N
        newGameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newGameMenuItem);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(pipes.PipesApp.class).getContext().getActionMap(PipesView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        Pause.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        Pause.setMnemonic('P');
        Pause.setText(resourceMap.getString("Pause.text")); // NOI18N
        Pause.setName("Pause"); // NOI18N
        Pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PauseActionPerformed(evt);
            }
        });
        fileMenu.add(Pause);

        menuBar.add(fileMenu);

        mnuOptions.setMnemonic('O');
        mnuOptions.setText(resourceMap.getString("mnuOptions.text")); // NOI18N
        mnuOptions.setName("mnuOptions"); // NOI18N

        MnuOptionsOptions.setMnemonic('O');
        MnuOptionsOptions.setText(resourceMap.getString("MnuOptionsOptions.text")); // NOI18N
        MnuOptionsOptions.setName("MnuOptionsOptions"); // NOI18N
        MnuOptionsOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuOptionsOptionsActionPerformed(evt);
            }
        });
        mnuOptions.add(MnuOptionsOptions);

        menuBar.add(mnuOptions);

        helpMenu.setMnemonic('H');
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        StatusPanel.setMinimumSize(new java.awt.Dimension(5, 5));
        StatusPanel.setName("StatusPanel"); // NOI18N
        StatusPanel.setPreferredSize(new java.awt.Dimension(550, 20));

        Progress.setName("Progress"); // NOI18N

        ProgressLabel.setText(resourceMap.getString("ProgressLabel.text")); // NOI18N
        ProgressLabel.setName("ProgressLabel"); // NOI18N

        ScoreLabel.setText(resourceMap.getString("ScoreLabel.text")); // NOI18N
        ScoreLabel.setName("ScoreLabel"); // NOI18N

        javax.swing.GroupLayout StatusPanelLayout = new javax.swing.GroupLayout(StatusPanel);
        StatusPanel.setLayout(StatusPanelLayout);
        StatusPanelLayout.setHorizontalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StatusPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(Progress, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProgressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(StatusPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ScoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(532, Short.MAX_VALUE)))
        );
        StatusPanelLayout.setVerticalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatusPanelLayout.createSequentialGroup()
                .addGroup(StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StatusPanelLayout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addComponent(ProgressLabel))
                    .addComponent(Progress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(StatusPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(ScoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        mainPanel.setDoubleBuffered(false);
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainPanelMouseClicked(evt);
            }
        });
        mainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                mainPanelComponentResized(evt);
            }
        });
        mainPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mainPanelKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(StatusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void mainPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseClicked
        int i ;

        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        if ((ODP.DM == OwnDrawPanel.DrawModes.ModeNormal) ||
            (ODP.DM == OwnDrawPanel.DrawModes.ModeGreying)) {
            // Normal operation: rotate the cell by passing the click event on
            ODP.MouseClicked(evt) ;
            i = CheckCellRootConnections() ;
            if ( i == (xs * ys))
                AllCellsDone() ;
        } else if (ODP.DM == OwnDrawPanel.DrawModes.ModeMessage) {
            // click acknowledges the message
            ODP.DM = OwnDrawPanel.DrawModes.ModeNormal ;
            ODP.repaint();
        }
    }//GEN-LAST:event_mainPanelMouseClicked

    private void mainPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_mainPanelComponentResized
        mainPanel.requestFocusInWindow() ;
/*        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        ODP.MyResize(lblSize.getX(), lblSize.getY()) ;
 *
 */
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        Rectangle VisRect = new Rectangle() ;
        ODP.computeVisibleRect(VisRect);
        ODP.MyResize(VisRect.getSize().width, VisRect.getSize().height) ;

        StatusPanel.setSize(VisRect.getSize().width, StatusPanel.getHeight());
    }//GEN-LAST:event_mainPanelComponentResized

    private void newGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameMenuItemActionPerformed
        int i, j, k ;

        // Stop any existing timer
        if ( T != null)
        {
            T.stop();
            T = null ;
        }
        
        // Reset all the cells to empty
        for (i = 0; i< xs; i++) {
            for (j = 0; j< ys; j++) {
                for (k = 0; k < 4; k++) {
                    Cells[i][j].Connections[k] = false ;
                }
                Cells[i][j].IsRoot = false ;
                Cells[i][j].IsConnectedToRoot = false ;
                Cells[i][j].GreyPC = 0 ;
            }
        }
        
        // Prepare new timer
        ActionListener AL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TimerHandler() ;
            }
        } ;

        // Set mode
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        ODP.DM = OwnDrawPanel.DrawModes.ModeWorking ;
        ODP.Score = 10000 ;

        // do the work in timer
        T = new Timer(1, AL);
        T.setInitialDelay(1);
        T.start(); 
    }//GEN-LAST:event_newGameMenuItemActionPerformed
    
    private void mainPanelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mainPanelKeyPressed
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        
        switch (evt.getKeyCode())
        {
            case KeyEvent.VK_P:
                PauseActionPerformed(null); break ;
            case KeyEvent.VK_LEFT:
                if (ODP.CX > 0) ODP.CX -- ; break ;
            case KeyEvent.VK_RIGHT:
                if (ODP.CX < xs - 1) ODP.CX ++ ; break ;
            case KeyEvent.VK_UP:
                if (ODP.CY > 0) ODP.CY -- ; break ;
            case KeyEvent.VK_DOWN:
                if (ODP.CY < ys - 1) ODP.CY ++ ; break ;
            case KeyEvent.VK_SPACE:
                if ((ODP.DM == OwnDrawPanel.DrawModes.ModeNormal) ||
                    (ODP.DM == OwnDrawPanel.DrawModes.ModeGreying))
                {
                    Cells[ODP.CX][ODP.CY].Rotate() ;
                    if (CheckCellRootConnections() == (xs * ys))
                        AllCellsDone() ;
                } else if (ODP.DM == OwnDrawPanel.DrawModes.ModeMessage) {
                    ODP.DM = OwnDrawPanel.DrawModes.ModeNormal ;
                }
                break ;
        }
        ODP.repaint();
    }//GEN-LAST:event_mainPanelKeyPressed

    private void AllCellsDone() {
        PreTTicks = 1 ;
        // They have completed the game... speedy greying of remaining cells
        StartGreying((OwnDrawPanel) mainPanel, 1);
    }

    private void StartGreying(OwnDrawPanel ODP, int speed) {
        // Either speedy cos of finished game or slow cos of grace period elapsed.
        int GreyingSpeed ;

        ODP.DM = OwnDrawPanel.DrawModes.ModeGreying ;

        // Work out real parameters for actual greying progress
        if (speed <= 10) {
            GreyingSpeed = 1 ;
            GreyingStep = 10 ;
        } else {
            GreyingSpeed = speed/5 ;
            GreyingStep = 2 ;
        }
        // Adjust or create timer
        if (T != null)
        {
            T.setDelay(GreyingSpeed);
            T.start() ; // Just in case...
        } else {
            ActionListener AL = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TimerHandler() ;
                }
            } ;

            T = new Timer(GreyingSpeed, AL);
            T.setInitialDelay(GreyingSpeed);
            T.start();
        }
    }
    
    private void MnuOptionsOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuOptionsOptionsActionPerformed
        PipesOpt PO = new PipesOpt(this) ;
        
        PO.setVisible(true);
    }//GEN-LAST:event_MnuOptionsOptionsActionPerformed

    private void PauseActionPerformed(java.awt.event.ActionEvent evt) {
        Pause() ;
    }
    
    public void Pause() {
        // Pass pause mode on to panel
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;

        if ((ODP.DM == OwnDrawPanel.DrawModes.ModeGreying) ||
        (ODP.DM == OwnDrawPanel.DrawModes.ModeNormal)){
            PrePauseMode = ODP.DM ;
            ODP.DM = OwnDrawPanel.DrawModes.ModePaused ;
        } else if (ODP.DM == OwnDrawPanel.DrawModes.ModePaused) {
            ODP.DM = PrePauseMode ;
        }
        ODP.repaint() ;
    }

    public void Pause(boolean bState) {
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;

        if (bState) {
            if ((ODP.DM == OwnDrawPanel.DrawModes.ModeGreying) ||
            (ODP.DM == OwnDrawPanel.DrawModes.ModeNormal)){
                PrePauseMode = ODP.DM ;
                ODP.DM = OwnDrawPanel.DrawModes.ModePaused ;
            }
        } else {
            if (ODP.DM == OwnDrawPanel.DrawModes.ModePaused) {
                ODP.DM = PrePauseMode ;
            }
        }
        this.getFrame().repaint();
        ODP.repaint() ;
    }
    
    private void TimerHandler() {
        // Where a lot of work is done!
        int i, CES ;
        OwnDrawPanel ODP = (OwnDrawPanel) mainPanel ;
        
        switch (ODP.DM) {
            case ModeGreying:

            // have started greying - either fast or slow
            CES = CountGreyCells() ;
            if (CES < xs * ys * 100 ) {
                // not full of grey yet
                if (GreyingStep != 10)
                    ODP.Score -= 1 ;
                if ( !GrowGreyCells(CES, GreyingStep))
                {
                    Object[] options = {"Carry On", "Start Again"};

                    // We failed!
                    T.stop();
                    ODP.Score -= 1000 ;
                    ODP.DM = OwnDrawPanel.DrawModes.ModeMessage ;
                    ODP.Message = "You failed - the water spilt" ;
                    mainPanel.repaint();
                    int n = JOptionPane.showOptionDialog(mainPanel, "Do you want to carry on or start again?", "You failed",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0] ) ;

                    if (n == 0) {
                        // Carry on...
                        PreTTicks = xs * ys * 2 * TimerSpeed ;
                        T.start() ;
                        ODP.DM = OwnDrawPanel.DrawModes.ModeNormal ;
                        Restarts++ ;
                    } else {
                        newGameMenuItemActionPerformed(null) ;
                    }
                }
            } else {
                // Full of grey - this must mean we've won, and fast greying has finished
                T.stop();
                ODP.DM = OwnDrawPanel.DrawModes.ModeMessage ;
                String S = new String();
                if (Restarts == 0) {
                    S = String.format("You Won!") ;
                } else if (Restarts == 1) {
                    S = String.format("You Won. (1 Restart needed)") ;
                } else {
                    S = String.format("You Won. (%d Restarts needed)", Restarts) ;
                }
                ODP.Message = S ;
                // save high score
                HighScoreEntry HSE = new HighScoreEntry () ;
                HSE.Score = ODP.Score ;
                HSE.Restarts = Restarts ;
                HSE.xs = xs ;
                HSE.ys = ys ;
                HSE.When = new Date() ;
                HS.AddEntry(HSE, true);
            }
            mainPanel.repaint();
            break ;

            case ModeNormal:
                // not yet greying - must be in grace period
                ODP.Score -= 1 ;
                PreTTicks -- ;
                Progress.setValue(PreTTicks);
                // Update progress bar
                ProgressLabel.setText(Integer.toString(PreTTicks / 10));
                if (PreTTicks <= 0)
                {
                    // Grace period is over...
                    StartGreying( (OwnDrawPanel) mainPanel, 300 * GreySpeed) ;
                }
            break;

            case ModeWorking:
            // building the initial grid...
            for (i=0; i<10; i++)
            {
                CES = CountEmptyCells() ;
                if (CES > 0) {
                    // not yet fully grown
                    GrowConnection(CES) ;
                } else {
                    // Finished generating new game...
                    RandomiseCells() ;
                    CheckCellRootConnections() ;
                    Restarts = 0 ;

                    // TimerSpeed of 4 meens give them forever...
                    if (TimerSpeed != 4) {
                        // Adjust timer for countdown...
                        PreTTicks = xs * ys * 2 * TimerSpeed ;
                        Progress.setMaximum(PreTTicks) ;
                        Progress.setMinimum(0);

                        T.setDelay(100);
                        T.start();
                    } else {
                        T.stop() ;
                    }

                    ODP.DM = OwnDrawPanel.DrawModes.ModeNormal ;
                    mainPanel.repaint();
                    break ;
                }
                TimerCounter ++ ;
                if (TimerCounter % 25 == 0)
                    mainPanel.repaint();
            }
        } // switch of display mode
        ScoreLabel.setText(Integer.toString(ODP.Score)) ;
    }
    
    private void GrowConnection(int CES) {
        int i, j, k ;
        Random R = new Random() ;
        boolean bDone = false ;
        boolean bFirstTime = true ;
        
        if (CES > 0) {
            // CES is number of empty cells
            i = R.nextInt(xs) ;
            j = R.nextInt(ys) ;
            
            if (CES == xs * ys) {
                // first time, Make root
                Cells[i][j].IsRoot = true ;
            } else {
                // ensure we start on a connected cell
                
                //V2 Algorithm: not only connected, but with empty neighbour
                // So, find all edge cells and pick one at random
                List<PipeCell> EdgeCells = FindEdgeCells() ;
                PipeCell GrowthBase = EdgeCells.get(R.nextInt(EdgeCells.size())) ;
                i = GrowthBase.MyX ;
                j = GrowthBase.MyY ;
            }
            
            while (! bDone ) {
                bDone = true ;
                
                // We've now got a starting cell, connected to the root
                // Continue from this cell, and pick a direction
                k = R.nextInt(4) ;
                
                // First time... must be a neibour somewhere, so ensure we've hit it
                if (bFirstTime){
                    while ((Cells[i][j].Neighbours[k] == null) ||
                           (Cells[i][j].Neighbours[k].IsConnectedToRoot))
                    k = R.nextInt(4) ;
                }
                bFirstTime = false ;
                    
                // stop if falling off the edge or linking back
                if (Cells[i][j].Neighbours[k] != null) {
                    if (! Cells[i][j].Neighbours[k].IsConnectedToRoot) {
                        // Link cells
                        Cells[i][j].Connections[k] = true ;
                        Cells[i][j].Neighbours[k].Connections[PipeCell.opposite(k)] = true ;
                        
                        // and move into this cell
                        switch (k) {
                            case 0: j-- ; break ;
                            case 1: i++ ; break ;
                            case 2: j++ ; break ;
                            case 3: i-- ; break ;
                        }
                        
                        bDone = false ;
                    }
                    // And keep growing this branch
                }
                
                CheckCellRootConnections() ;
            }
            mainPanel.repaint();
        }
    }

    private boolean GrowGreyCells(int CES, int Speed) {
        int i, j, k ;
        boolean bFirstTime = false ;
        boolean bNoneSpilt = true ;
        PipeCell C ;
        
        if (CES < xs * ys * 100) {
            // CES is count of empty cells
            if (CES == 0) {
                // first time, Make root
                bFirstTime = true ;
            } 
            for (i = 0; i< xs; i++) {
                for (j = 0; j< ys; j++) {
                    // Loop thru' all cells in grid
                    C = Cells[i][j] ;
                    if (bFirstTime) {
                        // Bootstrap - no grey to grow yet!
                        if (C.IsRoot)
                            C.GreyPC = Speed;
                    } else {
                        // already started, so increase cells already done
                        if (C.GreyPC > 0) {
                            if (C.GreyPC  == 100 ) {
                                // Just finished, so move greyness to next cells
                                for (k = 0; k<4 ; k++) {
                                    if (C.Connections[k])
                                    {
                                        // First check if we've fallen off the edge
                                        if (C.Neighbours[k] == null) {
                                            bNoneSpilt = false ;
                                        }
                                        // See if neibour links back to us?
                                        else if (! C.Neighbours[k].Connections[PipeCell.opposite(k)]) {
                                            // Not connected... so they've failed ("Spilt the water")
                                            // Reset this cell
                                            bNoneSpilt = false ;
                                        } else {
                                            if (C.Neighbours[k].GreyPC == 0) {
                                                C.Neighbours[k].GreyPC = Speed;
                                                C.Neighbours[k].GreyStart = PipeCell.opposite(k) ;
                                            }
                                        }
                                     }
                                }
                                C.GreyPC = 101 ; //101% means done and propogated to neibours = 101 will stop processing of this cell
                            } else if (C.GreyPC  < 100) {
                                C.GreyPC += Speed ;
                                // now, if we have changed step in the middle, we may have overshot...
                                if (C.GreyPC > 100)
                                    C.GreyPC = 100 ;
                            } else if (C.GreyPC > 101) {
                                // Must have overshot somewhere...
                                C.GreyPC = 100 ;
                            }
                                
                        }
                    }
                }
            }
            mainPanel.repaint();
        }
        return bNoneSpilt ;
    }

    private List<PipeCell> FindEdgeCells() {
        List<PipeCell> ECS = new ArrayList<PipeCell>() ;

        for (int i = 0; i< xs; i++) {
            for (int j = 0; j< ys; j++) {
                // Must be connected
                Cells[i][j].IsEdge = false ;
                if (Cells[i][j].IsConnectedToRoot) {
                    // Does it have any empty neibours?
                    for (int k = 0; k<4 ; k++) {
                        if (Cells[i][j].Neighbours[k] != null) {
                            if (Cells[i][j].Neighbours[k].IsEmpty()) {
                                Cells[i][j].IsEdge = true ;
                                ECS.add(Cells[i][j]);
                            }
                        }
                    }
                }
            }
        }
        return ECS ;
    }
    
    private void RandomiseCells() 
    {
        int kk ; 
        Random R = new Random() ;

        for (int i = 0; i< xs; i++) {
            for (int j = 0; j< ys; j++) {
                kk = R.nextInt(4) ;
                for (int k=0; k < kk; k++) {
                    Cells[i][j].Rotate();
                }
            }
        }
    }

    int CountEmptyCells()
    {
        int k = 0 ;
        
        for (int i = 0; i< xs; i++) {
            for (int j = 0; j< ys; j++) {
                if (Cells[i][j].IsEmpty())
                        k++ ;
            }
        }
        return k ;
    }
    
    int CountGreyCells()
    {
        int k = 0 ;
        
        for (int i = 0; i< xs; i++) {
            for (int j = 0; j< ys; j++) {
                if (Cells[i][j].GreyPC <= 100)
                    k += Cells[i][j].GreyPC ;
                else 
                    k += 100 ;
            }
        }
        return k ;
    }
    
    int CheckCellRootConnections() 
    {
        int i, j, k = 0 ;
        boolean bDoneOne = true ;

        for (i = 0; i< xs; i++) {
            for (j = 0; j< ys; j++) {
                Cells[i][j].IsConnectedToRoot = false ;
            }
        }
        
        // Find root
        while (bDoneOne)
        {
            bDoneOne = false ;
            for (i = 0; i< xs; i++) {
                for (j = 0; j< ys; j++) {
                    if (! Cells[i][j].IsConnectedToRoot) {
                        if (Cells[i][j].IsRoot) {
                            Cells[i][j].IsConnectedToRoot = true ;
                            bDoneOne = true ;
                        }
                        for (k=0; k<4; k++) {
                            if (Cells[i][j].Connections[k]) {
                                if (Cells[i][j].Neighbours[k]!= null) {
                                    if (Cells[i][j].Neighbours[k].Connections[(k + 2) % 4]) {
                                        if (Cells[i][j].Neighbours[k].IsConnectedToRoot) {
                                            Cells[i][j].IsConnectedToRoot = true ;
                                            bDoneOne = true ;                          
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        k = 0 ;
        for (i = 0; i< xs; i++) {
            for (j = 0; j< ys; j++) {
                if (Cells[i][j].IsConnectedToRoot) {
                    k++ ;
//                } else {
//                    Cells[i][j].GreyPC = 0 ;
                }
            }
        }
        return k ;
    }

    private void loadPrefs() {
        Preferences prefs = Preferences.userNodeForPackage(PipesView.class);
        xs = prefs.getInt("XS", 8) ;
        ys = prefs.getInt("YS", 8) ;
        TimerSpeed = prefs.getInt("TS", 2) ;
        GreySpeed = prefs.getInt("GS", 1) ;
    }

    public void savePrefs() {
        Preferences prefs = Preferences.userNodeForPackage(PipesView.class);
        prefs.putInt("XS", xs);
        prefs.putInt("YS", ys);
        prefs.putInt("GS", GreySpeed) ;
        prefs.putInt("TS", TimerSpeed) ;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MnuOptionsOptions;
    private javax.swing.JMenuItem Pause;
    private javax.swing.JProgressBar Progress;
    private javax.swing.JLabel ProgressLabel;
    private javax.swing.JLabel ScoreLabel;
    private javax.swing.JPanel StatusPanel;
    private javax.swing.JSeparator jSeparator2;
    javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnuOptions;
    private javax.swing.JMenuItem newGameMenuItem;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;

}
