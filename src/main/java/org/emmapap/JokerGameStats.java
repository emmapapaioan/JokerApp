package org.emmapap;

import frames.Home;

public class JokerGameStats {

    public static void main(String[] args) {

        //Κατασκεύασε ένα αντικείμενο τύπου Home Jframe
        Home home = new Home();
        //Κάνε το αντικείμενο τύπου Home ορατό (Home menu)
        home.setVisible(true);

        //Εκκίνηση server για τη ΒΔ
        Connect con = new Connect();
        con.connect();
    }
}