/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pipes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author davidp
 *
 * A Single cell in the grid
 */
public class PipeCell {

    public boolean Connections[] ;
    public boolean IsRoot ;
    public boolean IsConnectedToRoot ;
    public boolean IsEdge ;
    public PipeCell Neighbours[] ;
    public int MyX, MyY ;
    public int GreyPC ;
    public int GreyStart ; 
    
    public PipeCell() {
        Random R = new Random() ;
        
        Connections = new boolean[4] ;
        Neighbours = new PipeCell[4] ;
        
        for (int i=0; i<=3; i++)
        {
            Connections[i] = R.nextBoolean() ;
        }
        GreyPC = 0 ;
        GreyStart = 0 ;
    }
    
    public void Render(Graphics G, int x, int y, int Width, int Height, BufferedImage RedImage, BufferedImage GreenImage, BufferedImage OrangeImage, BufferedImage GreyImage){        
        int Pattern=0 ;
        boolean bGrey ;
        int xx, yy ;
        boolean bFull = true ;
        BufferedImage ImageToUse ;

        // determine shape in the array
        Pattern += (Connections[0] ? 1 : 0) ; 
        Pattern += (Connections[1] ? 8 : 0) ; 
        Pattern += (Connections[2] ? 4 : 0) ; 
        Pattern += (Connections[3] ? 2 : 0) ; 

        // Determine the colour to use
        if (IsConnectedToRoot) {
            // Connected: Green or orange
            for (int Dir=0; Dir < 4; Dir++)
            {
                //see if properly connected in all 4 directions
                if (Connections[Dir]) {
                    if (Neighbours[Dir] != null ) {
                        if (! Neighbours[Dir].Connections[opposite(Dir)]) {
                            bFull = false ;
                        }
                    } else {
                        bFull = false ;
                    }
                }
            }
            if (bFull) {
                ImageToUse = GreenImage ;
            } else {
                ImageToUse = OrangeImage ;
            }
        }
        else
            // not connected
            ImageToUse = RedImage ;

        if (GreyPC > 0)
        {
            // in process of greying
            BufferedImage Img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB) ;
            
            if (GreyPC >= 100)
            {
                // full grey - just blat that out
                G.drawImage(GreyImage, x, y, x+Width, y+Height, 100*Pattern, 0, 100*(Pattern+1), 100, null) ;
            } else {            
                for (int h=0; h < 100; h++)
                {
                    // loop thru all steps of greyness and see if this step is grey yet
                    if (h < GreyPC)
                        bGrey = true ;
                    else
                        bGrey = false ;

                    if (h < 50)
                    {
                        // greyness coming in - one direction only
                        for (int w = 0; w<(50-h); w++) 
                        {
                            // combine grey and colour image
                            xx = 49-w ;
                            yy = h ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                            xx = 50+w ;
                            yy = h ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                        }
                    } else {
                        for (int w = 0; w<=(h-50); w++) 
                        {
                            // greyness coming in - three directions
                            xx = 50-w ;
                            yy = h ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                            xx = 50+w ;
                            yy = h ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                            xx = h ;
                            yy = 50-w ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                            xx = h ;
                            yy = 50+w ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                            xx = 99-h ;
                            yy = 50-w ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                            xx = 99-h ;
                            yy = 50+w ;
                            SetImagePixel(Img, ImageToUse, Pattern, xx, yy, bGrey, GreyStart) ;
                        }                    
                    }
                }
                G.drawImage(Img, x, y, Width, Height, null);
            }        
        } else {
            // not greying
            G.drawImage(ImageToUse, x, y, x+Width, y+Height, 100*Pattern, 0, 100*(Pattern+1), 100, null) ;
        }
    }
    
    public void SetImagePixel(BufferedImage Dest, BufferedImage Src, int Pattern, int x, int y, boolean bGrey, int Dir)
    // Merge the colour and grey images
    {
        Color Col ; 
        Color Col2 ;

        int t ;
        switch (Dir) {
            case 0:
                break ;
            case 1:
                t = x ;
                x = 99 - y ;
                y = t ;
                break ;
            case 2:
                y = 99 - y ;
                break ;
            case 3:
                t = x ;
                x = y ;
                y = t ;
                break ;
        }
        
        try {
        if (bGrey) 
        {
            Col = new Color(Src.getRGB(100*Pattern + x, y)) ;
            int Gr = Col.getGreen()  ;
            Col2 = new Color (Gr, Gr, Gr) ;
            Dest.setRGB(x, y, Col2.getRGB());
        } else {
            Dest.setRGB(x, y, Src.getRGB(100*Pattern + x, y));
        }
        }
        catch (Exception ex)
        {    
            x = 1 ;
        }
    
    }
    
    public static int opposite(int Dir)
    {
        return (Dir + 2) % 4 ;                    
    }
    
    public void Rotate() 
    {
        boolean tmp = Connections[0] ;
        Connections[0] = Connections[1] ;
        Connections[1] = Connections[2] ;
        Connections[2] = Connections[3] ;
        Connections[3] = tmp ;

        // if previously fully greyed, reset to try and respread
        if (GreyPC > 100)
            GreyPC = 100 ;

        // Also reset any fully greyed neibours, in case they now spill
        for (int Dir=0; Dir < 4; Dir++)
        {
            if (Neighbours[Dir] != null ) {
                if (Neighbours[Dir].GreyPC > 100)
                    Neighbours[Dir].GreyPC = 100 ;
            }
        }
    }
    
    public boolean IsEmpty()
    {
        return !(Connections[0] || Connections[1] || Connections[2] || Connections[3]) ;
    }
    
}

