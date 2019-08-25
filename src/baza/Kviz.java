/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author filip
 */
public class Kviz {

    private final int id;
    private final String naziv;
    private String datumPocetka, datumKraja, pobednik;
    private List<Pitanje> pitanja;

    public Kviz(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
        this.datumPocetka = null;
        this.datumKraja = null;
        this.pobednik = null;
        this.pitanja = new LinkedList<>();
    }

    public Kviz(int id, String naziv, String datumPocetka, String datumKraja) {
        this.id = id;
        this.naziv = naziv;
        this.datumPocetka = datumPocetka;
        this.datumKraja = datumKraja;
        this.pobednik = null;
        this.pitanja = new LinkedList<>();
    }

    public Kviz(int id, String naziv, String datumPocetka, String datumKraja, String pobednik) {
        this.id = id;
        this.naziv = naziv;
        this.datumPocetka = datumPocetka;
        this.datumKraja = datumKraja;
        this.pobednik = pobednik;
        this.pitanja = new LinkedList<>();
    }

    public List<Pitanje> getPitanja() {
        return pitanja;
    }

    public void setPitanja(List<Pitanje> pitanja) {
        this.pitanja = pitanja;
    }

    public String getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(String datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public String getDatumKraja() {
        return datumKraja;
    }

    public void setDatumKraja(String datumKraja) {
        this.datumKraja = datumKraja;
    }

    public String getPobednik() {
        return pobednik;
    }

    public void setPobednik(String pobednik) {
        this.pobednik = pobednik;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void ispisiKviz() {
        System.out.println("KVIZ:");
        pitanja.forEach(pitanje -> {
            System.out.println("    Pitanje: " + pitanje.getPitanje());
        }
        );
    }

}
