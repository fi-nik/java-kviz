/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import java.sql.*;  // Using 'Connection', 'Statement' and 'ResultSet' classes in java.sql package
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author filip
 */
public class Baza {

    private static Connection conn;

    public static void napraviKonekciju() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7302924", "sql7302924", "H5ds1qJMfm");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Baza() {

        try {
            conn = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7302924", "sql7302924", "H5ds1qJMfm");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static List<Kviz> uzmiKvizove() {
        List<Kviz> kvizovi = new LinkedList<>();
        try {

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("Select * from kviz");

            while (rset.next()) {
                Kviz kviz = new Kviz(rset.getInt("KvizID"), rset.getString("Naziv"));
                kvizovi.add(kviz);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return kvizovi;
    }

    public static Kviz uzmiKviz(int idKviza) {
        try {

            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("Select * from kviz where KvizID = " + idKviza);
            Kviz kviz = null;
            while (rset.next()) {
                kviz = new Kviz(rset.getInt("KvizID"), rset.getString("Naziv"));
            }
            if (kviz == null) {
                return null;
            }
            rset = stmt.executeQuery("Select * from pitanje where KvizID = " + idKviza);
            int i = 1;
            List<Pitanje> pitanja = new LinkedList<>();
            while (rset.next()) {
                Pitanje pitanje = new Pitanje(i, rset.getString("Pitanje"), rset.getString("TacanOdgovor"), rset.getInt("BrojPoena"));
                pitanja.add(pitanje);
            }
            System.out.println("KVIZ NIJE NULL");
            kviz.setPitanja(pitanja);
            return kviz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
