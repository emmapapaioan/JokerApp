package org.emmapap;

import org.apache.derby.client.am.SqlException;
import org.apache.derby.drda.NetworkServerControl;
//import org.apache.derby.iapi.sql.Statement;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author emmapap
 */
public class Connect {

    public Connection con = null;
    //public Statement stmt = null;
    public String URL = "jdbc:derby://localhost:1527/JokerGameStatsDB;create=true";

    public void connect() {
        try {
            NetworkServerControl server = new NetworkServerControl();
            server.start(null);
            con = DriverManager.getConnection(URL, "joker", "joker");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη σύνδεση με το server. Πραγματοποιήστε επανεκκίνηση του προγράμματος.", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void disconnect() {
        if(con!=null){
            try{
                con.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
