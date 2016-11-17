/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pipes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author davidp
 *
 * Takes care of all drawing of the game area - also owns the grid of cells
 */
public class OwnDrawPanel extends javax.swing.JPanel {
    public PipeCell [][] Cells  ;
    private int s = 1 ;
    public DrawModes DM = DrawModes.ModeNormal;
    public String Message ;
    public int Score ;
    public int CX = 0, CY = 0;
    public PipesHighScores HS ;

    private BufferedImage RedImages ;
    private BufferedImage GreenImages ;
    private BufferedImage OrangeImages ;
    private BufferedImage GreyImages ;

    private Font MessageFont ;
    private Font HiScoreFont ;
    private double PointsToPixels = 0;
    private boolean mbResized ;

     public enum DrawModes {
        ModeNormal ,
        ModeMessage ,
        ModeWorking ,
        ModeGreying,
        ModePaused
    }
    
    @SuppressWarnings("LeakingThisInConstructor")
    public OwnDrawPanel(){
        mbResized = true ;

        // Get current classloader
        ClassLoader cl = this.getClass().getClassLoader();

        // Create icons in three colours
        try {
            RedImages = ImageIO.read(cl.getResource("pipes/resources/RedPipes.bmp")) ;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        try {
            GreenImages = ImageIO.read(cl.getResource("pipes/resources/GreenPipes.bmp")) ;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        try {
            OrangeImages = ImageIO.read(cl.getResource("pipes/resources/OrangePipes.bmp")) ;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        try {
            GreyImages = ImageIO.read(cl.getResource("pipes/resources/GreyPipes.bmp")) ;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        MessageFont = new Font("Helvetica", Font.BOLD, 24) ;
    }

    public void MouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, j ;
        
        i = evt.getX() / s ;
        j = evt.getY() / s ;
        
        if (i < Cells.length)
        {
            if (j< Cells[i].length) 
            {
                CX = i ;
                CY = j ;
                Cells[i][j].Rotate() ;
                this.repaint();
                return ;
            } 
        }
    }
    
    public void MyResize(int x, int y)
    {
        int sx, sy ;
        
        sx = x / Cells.length ;
        sy = y / Cells[0].length ;
        mbResized = true ;
        
        s = Math.min(sx, sy);
        
        this.repaint();
    }
    
@Override
    protected void paintComponent(Graphics g)  
    {
        if (mbResized) {
            mbResized = false ;
            DrawClearAll(g) ;
        }
        switch (DM)
        {
            case ModeMessage:
                DrawMessage(g);
                break ;
            case ModeNormal:
            case ModeGreying:
                DrawCells(g);
                break ;
            case ModeWorking:
                DrawWorking(g) ;
                break ;
            case ModePaused:
                DrawHighScores(g);
                break ;
        }
    }

    private void DrawClearAll(Graphics g) {
        Rectangle R = g.getClipBounds() ;
        g.setColor(Color.LIGHT_GRAY) ;
        g.fillRect(R.x, R.y, R.width, R.height);
    }

    private void DrawCells(Graphics g) {
        int i, j ;
        
        if (Cells != null)
        {
            for (i = 0; i< Cells.length; i++) {
                for (j = 0; j<Cells[0].length; j++) {
                    if (Cells[i][j] != null)
                        Cells[i][j].Render(g, i*s, j*s, s, s, RedImages, GreenImages, OrangeImages, GreyImages) ;
                    if (i == CX && j == CY) {
                        // This is the focus cell
                        g.setColor(Color.BLUE);                        
                        g.draw3DRect(i*s, j*s, s-1, s-1, true);
                        
                    }
                        
                }
            }
        }
        
    }
    
   private void DrawMessage(Graphics g) {
        int x = Cells.length * s ;
        int y = Cells[0].length * s ;

        g.setColor(Color.lightGray) ;
        g.fill3DRect(0, 0, x, y, true) ;
        g.setFont(MessageFont) ;

        if ((Message != null) && (DM != DrawModes.ModePaused))
       {
           DrawPrettyText(g, Message, x/2, y/2);
           String S = String.format("Score %d", Score) ;
           int h = g.getFontMetrics().getHeight();
           DrawPrettyText(g, S, x/2, y/2+ h);
           int pos = HS.LocateHSPosition(HighScoreEntry.SizeKey(Cells.length, Cells[0].length), Score) ;
           if (pos>= 0) {
           S = String.format("High Score position %d", pos+1) ;
           DrawPrettyText(g, S, x/2, y/2+ 2 * h);
           }
       } else {
           int w = g.getFontMetrics().stringWidth("Paused") ;
           g.setColor(Color.BLACK) ;
           g.drawString("Paused", (x-w)/2, y/2);
       }
   }
   
   private void DrawPrettyText(Graphics g, String Mess, int x, int y)
   {
           int w = g.getFontMetrics().stringWidth(Mess) ;
           g.setColor(Color.WHITE) ;
           g.drawString(Mess, x - (w/2)+1, y + 1);
           g.setColor(Color.BLACK) ;
           g.drawString(Mess, x-(w/2), y);
   }

   private void DrawWorking(Graphics g) {
        int i, j ;
        Random R = new Random() ;

        if (Cells != null)
        {
            for (i = 0; i< Cells.length; i++) {
                for (j = 0; j<Cells[0].length; j++) {
                    if (Cells[i][j] != null) {
                        if (Cells[i][j].IsEmpty()) {
                            g.setColor(Color.LIGHT_GRAY);
                        } else if(Cells[i][j].IsEdge)  {
                            Color C = new Color(R.nextInt()) ;
                            g.setColor(C);
                        } else {
                            g.setColor(Color.DARK_GRAY);
                        }
                        g.fill3DRect(i*s, j*s, s, s, true);
                    }
                }
            }
        }
    } 

    private void DrawHighScores(Graphics g) {
        int x = Cells.length * s ;
        int y = Cells[0].length * s ;
        String S ;

        if (PointsToPixels == 0)
            PointsToPixels = GetFontRatio(g) ;

        g.setColor(Color.lightGray) ;
        g.fill3DRect(0, 0, x, y, true) ;

        int ReqFontSize = (int)(y /(PointsToPixels * (HS.MaxEntries + 2.0))) ;

        if (HiScoreFont == null) {
            HiScoreFont= new Font("Helvetica", Font.PLAIN, ReqFontSize) ;
        } else if (HiScoreFont.getSize() != ReqFontSize) {
            HiScoreFont= new Font("Helvetica", Font.PLAIN, ReqFontSize) ;
        }
        g.setFont(HiScoreFont) ;

        S = "High Scores:" ;
        g.setColor(Color.WHITE) ;
        g.drawString( S, 6,  g.getFontMetrics().getHeight()+1 );
        g.setColor(Color.BLACK) ;
        g.drawString( S, 5,  g.getFontMetrics().getHeight() );
        for (int i = 0; i<HS.UsedEntries[HighScoreEntry.SizeKey(Cells.length, Cells[0].length)]; i++)
        {
            S = String.format("  %2d. %6d    %3$tR %3$te %3$tb %3$tY",
                    i + 1,
                    HS.HighScores[HighScoreEntry.SizeKey(Cells.length, Cells[0].length)][i].Score,
                    HS.HighScores[HighScoreEntry.SizeKey(Cells.length, Cells[0].length)][i].When) ;
            g.setColor(Color.WHITE) ;
            g.drawString( S, 6, ((i+2)* g.getFontMetrics().getHeight())+1 );
            g.setColor(Color.BLACK) ;
            g.drawString( S, 5, ((i+2)* g.getFontMetrics().getHeight()) );
        }
   }

   private double GetFontRatio(Graphics g) {
        Font MeasureFont = new Font("Helvetica", Font.PLAIN, 100) ;
        return (g.getFontMetrics(MeasureFont).getHeight()  / 100.0) ;
   }



}
