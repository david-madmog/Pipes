/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pipes;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 *
 * @author davidp
 *
 * Manages the collection of high score entries
 */
public class PipesHighScores {

    public final int MaxEntries = 20 ;

    public HighScoreEntry [][] HighScores ; // First index: grid size, second index: rank
    public int UsedEntries []; // index: grid size. How much of the main array is used

    public PipesHighScores() {
        // magic number 17 represents 16 grid sizes and one dustbin
        HighScores = new HighScoreEntry[17][MaxEntries] ;
        UsedEntries = new int[17] ;
        Load() ;
    }

    public int AddEntry(HighScoreEntry HSE, boolean bSave) {
        int InsertPos = -1 ;
        if (UsedEntries[HighScoreEntry.SizeKey(HSE.xs, HSE.ys)] < MaxEntries) {
            // free slots - just shove it in at the end
            InsertPos = UsedEntries[HighScoreEntry.SizeKey(HSE.xs, HSE.ys)]++ ;
        } else if (HSE.Score > HighScores[HighScoreEntry.SizeKey(HSE.xs, HSE.ys)][UsedEntries[HighScoreEntry.SizeKey(HSE.xs, HSE.ys)]-1].Score ) {
            // array is full - but we're better than the worst, so dump it in place of the worst
            InsertPos = UsedEntries[HighScoreEntry.SizeKey(HSE.xs, HSE.ys)]-1 ;
        }

        if (InsertPos >= 0) {
            // We've added a new entry - always at the end, so sort.
            HighScores[HighScoreEntry.SizeKey(HSE.xs, HSE.ys)][InsertPos] = HSE ;
            Sort(HighScoreEntry.SizeKey(HSE.xs, HSE.ys)) ;
            if (bSave)
                Save() ;
        }

        return InsertPos ;
    }

    public void Save() {
        Preferences prefs = Preferences.userNodeForPackage(PipesView.class);
        for (int j = 0; j<= 16; j++) {
            for (int i = 0; i< HighScores[j].length; i++) {
                if (HighScores[j][i] != null)
                    HighScores[j][i].Save(prefs, i) ;
            }
        }
        try {
            prefs.flush();
        } catch (BackingStoreException ex) {
            Logger.getLogger(PipesHighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void Load() {
        try {
            Preferences prefs = Preferences.userNodeForPackage(PipesView.class);
            String[] Children = prefs.childrenNames();
            for (int i = 0; i<Children.length; i++) {
                // Loop of sizes
                Preferences SizeNode = prefs.node(Children[i]) ;
                for (int j=0; j< MaxEntries; j++) {
                    // Loop of score positions
                    if (!SizeNode.get("Score" + Integer.toString(j), "xyzzy").equals("xyzzy")) {
                        // score position exists in registry... so load it.
                        HighScoreEntry HSE = new HighScoreEntry() ;
                        HSE.Load(SizeNode, j) ;
                        AddEntry(HSE, false);
                    }
                }
            }
        } catch (BackingStoreException ex) {
            Logger.getLogger(PipesHighScores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void Sort(int Key) {
        HighScoreEntry HSE = new HighScoreEntry() ;
        java.util.Arrays.sort(HighScores[Key], 0, UsedEntries[Key], HSE);
    }

    int LocateHSPosition(int key, int Score) {
        int Pos = -1 ;
        for (int i=0; i< UsedEntries[key]; i++) {
            if (HighScores[key][i].Score == Score) {
                Pos = i ;
            }
        }
        return Pos ;
    }

}