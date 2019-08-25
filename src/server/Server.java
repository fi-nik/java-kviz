/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import baza.Baza;
import baza.Kviz;
import baza.Pitanje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author filip
 */
public class Server extends Thread {

    public static final String KRECE_KVIZ = "KRECE_KVIZ";
    public static final String USPESNA_PRIJAVA = "USPESNA_PRIJAVA";
    public static final String NEUSPESNA_PRIJAVA = "NEUSPESNA_PRIJAVA";
    public static final String KRAJ = "KRAJ";
    public static final String ODGOVORENO = "ODGOVORENO";

    private final int port = 9000;
    private final int brojIgraca = 2;
    private ServerSocket server;
    private Kviz kviz = null;
    private List<ThreadPerClient> igraci;
    private boolean krajIgre = false;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private double brojPoenaServer = 0;
    private double brojPoenaTim = 0;
    private ServerForma forma;
    private String odgovorServera = null;
    private String odgovorTima = null;
    private int brojPitanja = -1;
    private int brojTacnihTim = 0;
    private int brojTacnihServer = 0;
    private boolean cekanjeKraja = false;
    private int brojProcitanihPoruka = 0;

    public Server(ServerForma forma, int izabranKviz) {
        this.forma = forma;
        this.kviz = Baza.uzmiKviz(izabranKviz);
        kviz.ispisiKviz();

        pokreniIgru();
    }

    public synchronized boolean jelTimOdgovorio() {
        return odgovorTima != null;
    }

    public synchronized boolean jelServerOdgovorio() {
        return odgovorServera != null;
    }

    public synchronized void postaviOdgovorTima(String odgovor) {
        System.out.println("Postavljam odgovor tima " + odgovor);
        if (cekanjeKraja) {
            brojProcitanihPoruka++;
            if (brojProcitanihPoruka == brojIgraca) {
                krajIgre = true;
                zatvoriSve();
                forma.omoguciPokretanjeKviza();
            }
        } else if (odgovorTima == null) {
            odgovorTima = odgovor;
            posaljiSvimKlijentima(ODGOVORENO);
            if (odgovorServera != null) {
                obradiOdgovore();
            }
        }
    }

    public void zatvoriSve() {
        try {
            if (oos != null) {
                oos.close();
            }
            if (ois != null) {
                ois.close();
            }
            if (!server.isClosed()) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void postaviOdgovorServera(String odgovor) {
        System.out.println("Postavljam odgovor servera " + odgovor);
        forma.onemoguciSlanjeOdgovora();
        odgovorServera = odgovor;
        if (odgovorTima != null) {
            obradiOdgovore();
        }
    }

    public synchronized boolean jelKraj() {
        return krajIgre;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);

            while (igraci.size() < brojIgraca) {
                System.out.println("Waiting for the client request");
                //creating socket and waiting for client connection
                Socket socket = server.accept();
                odradiNovoPrikljucivanje(socket);
            }
            posaljiPitanjeSvima();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void posaljiPitanjeSvima() {
        String pitanje = kviz.getPitanja().get(++brojPitanja).getPitanje();
        forma.prikaziPitanje(pitanje);
        forma.omoguciSlanjeOdgovora();
        posaljiSvimKlijentima(pitanje);
        posaljiSvimKlijentima("" + brojPoenaTim);
        posaljiServeruInfo();
        System.out.println("Poslao svimao");
    }

    public void posaljiServeruInfo() {
        forma.prikaziBrojPoena("" + brojPoenaServer);
        System.out.println("");
        double[] tim = {brojTacnihTim, brojPitanja - brojTacnihTim, brojPoenaTim};
        double[] server = {brojTacnihServer, brojPitanja - brojTacnihServer, brojPoenaServer};
        forma.azurirajTabelu(tim, server);
    }

    private void obradiOdgovore() {
        System.out.println("Svi su dali odgovor!");
        Pitanje pitanje = kviz.getPitanja().get(brojPitanja);
        double poeni = obradiOdgovor(pitanje, odgovorServera);
        if (poeni > 0) {
            brojTacnihServer++;
        }
        brojPoenaServer += poeni;
        poeni = obradiOdgovor(pitanje, odgovorTima);
        if (poeni > 0) {
            brojTacnihTim++;
        }
        brojPoenaTim += poeni;
        odgovorServera = null;
        odgovorTima = null;
        if (brojPitanja == kviz.getPitanja().size() - 1) {
            zavrsiIgru();
        } else {
            posaljiPitanjeSvima();
        }
    }

    private double obradiOdgovor(Pitanje pitanje, String odgovor) {
        return jelTacanOdgovor(pitanje, odgovor) ? pitanje.getBrojPoena() : -0.2 * pitanje.getBrojPoena();
    }

    private boolean jelTacanOdgovor(Pitanje pitanje, String odgovor) {
        return pitanje.getOdgovor().toLowerCase().equals(odgovor.toLowerCase());
    }

    private void posaljiSvima(String poruka) {
        System.out.println("Saljem svima: " + poruka);
        forma.prikaziPoruku(poruka);
        posaljiSvimKlijentima(poruka);
    }

    private String obradiPoruku(Socket socket) {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String poruka = (String) ois.readObject();

            return poruka;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void posaljiPoruku(Socket socket, String poruka) {
        try {
            //create ObjectOutputStream object
            oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            oos.writeObject(poruka);
            //close resources
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void odradiNovoPrikljucivanje(Socket socket) {
        //read from socket to ObjectInputStream object
        try {
            String igrac = obradiPoruku(socket);
            System.out.println("Pokusaj prijavljivanja igraca: " + igrac);
            if (igraci.stream().anyMatch(th -> th.getUsername().equals(igrac))) {
                System.out.println("Zao mi je, korisnik vec postoji");
                posaljiPoruku(socket, NEUSPESNA_PRIJAVA);
            } else {
                System.out.println("Igrac " + igrac + " je prihvacen!");
                igraci.add(new ThreadPerClient(socket, this, igrac));
                posaljiPoruku(socket, USPESNA_PRIJAVA);
                if (igraci.size() == brojIgraca) {
                    javiSvimaDaKreceKviz();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void javiSvimaDaKreceKviz() {
        System.out.println("KRECE KVIZ!!");
        igraci.forEach(igrac -> igrac.start());
        forma.prikaziPoruku(KRECE_KVIZ);
    }

    public void posaljiSvimKlijentima(String poruka) {
        igraci.forEach(igrac -> igrac.posaljiPoruku(poruka));
    }

    public void zavrsiIgru() {
        //TODO: azurirati bazu sa krajem igre i pobednikom
        try {
            String pobednik = "Pobednik je: " + (brojPoenaServer > brojPoenaTim ? "Server" : brojPoenaServer == brojPoenaTim ? "Nereseno" : "Tim");
            posaljiSvimKlijentima(KRAJ);
            posaljiServeruInfo();
            posaljiSvimKlijentima("" + brojPoenaTim);
            posaljiSvima(pobednik);
            cekanjeKraja = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initAll() {
        igraci = new LinkedList<>();
        krajIgre = false;
        ois = null;
        oos = null;
        brojPoenaServer = 0;
        brojPoenaTim = 0;
        odgovorServera = null;
        odgovorTima = null;
        brojPitanja = -1;
        brojTacnihTim = 0;
        brojTacnihServer = 0;
        cekanjeKraja = false;
        brojProcitanihPoruka = 0;
        forma.prikaziBrojPoena("");
        forma.prikaziPitanje("");
        forma.prikaziPoruku("");
        forma.ocistiOdgovor();
        forma.omoguciPokretanjeKviza();
        forma.postaviStatistiku();
    }

    public void pokreniIgru() {
        //TODO: Dodati u bazi pocetno vreme kviza
        initAll();
        forma.onemoguciPokretanjeKviza();
        start();
    }

}
