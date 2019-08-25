/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

/**
 *
 * @author filip
 */
public class Pitanje {
    private int id;
    private String pitanje,odgovor;
    private int brojPoena;

    public Pitanje(int id, String pitanje, String odgovor, int brojPoena) {
        this.id = id;
        this.pitanje = pitanje;
        this.odgovor = odgovor;
        this.brojPoena = brojPoena;
    }

    public int getId() {
        return id;
    }

    public String getPitanje() {
        return pitanje;
    }

    public String getOdgovor() {
        return odgovor;
    }

    public int getBrojPoena() {
        return brojPoena;
    }
    
}
