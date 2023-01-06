package org.emmapap;

import org.apache.derby.client.am.SqlException;
import org.apache.derby.drda.NetworkServerControl;
//import org.apache.derby.iapi.sql.Statement;

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
            e.printStackTrace();
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
