/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pipes;

import java.util.Comparator;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 *
 * @author davidp
 *
 * Represents a single entry in the list of high scores
 */

    public class HighScoreEntry
            implements Comparator<HighScoreEntry>
    {
        public int Score ;
        public int xs, ys ;
        public int Restarts ;
        public String Name = "" ;
        public Date When ;

        public void Save(Preferences prefs, int i) {
            String sPath =  Size() ;
            Preferences SizeNode = prefs.node(sPath) ;
            SizeNode.putInt("Score" + Integer.toString(i), Score);
            SizeNode.putInt("Restarts" + Integer.toString(i), Restarts);
            SizeNode.putLong("When"+ Integer.toString(i), When.getTime()) ;
            SizeNode.put("Name" + Integer.toString(i), Name);
        }

        public void Load(Preferences prefs, int j) {
            Score = prefs.getInt("Score" + Integer.toString(j), 0);
            Restarts  = prefs.getInt("Restarts" + Integer.toString(j), 0);
            Name = prefs.get("Name" + Integer.toString(j), "");
            When  = new Date(prefs.getLong("When" + Integer.toString(j), 0)) ;

            // We have the size of the grid stored in the key, we need to parse it to x and y
            String N = prefs.name() ;
            int i = N.indexOf("x")  ;
            xs = Integer.parseInt(N.substring(0, i)) ;
            ys = Integer.parseInt(N.substring(i+1)) ;
        }

        // Utility function to convert the grid size to a string
        private String Size() {
            return Integer.toString(xs) + "x" + Integer.toString(ys) ;
        }

        // Utility to convert the grid size into a unique key for indexing into an array
        public static int SizeKey(int xs, int ys) {
            return( xs/8 + (ys/2) - 4) ;
        }

    // Comparitor needed to allow sorting. Note: backwards, so sorts descending
    public int compare(HighScoreEntry o1, HighScoreEntry o2) {
        if (o1.Score < o2.Score)
            return 1 ;
        else if (o1.Score > o2.Score)
            return -1 ;
        else
            return 0 ;
    }
}
