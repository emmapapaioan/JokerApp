package org.emmapap;

import frames.Home;

public class JokerGameStats {

    public static void main(String[] args) {

        Home home = new Home();
        home.setVisible(true);

        //Start the server for the database
        Connect con = new Connect();
        con.connect();
    }
}