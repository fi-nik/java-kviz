/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clijent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import server.Server;

/**
 *
 * @author filip
 */
public class Client extends Thread {

    private int port = 9000;
    private boolean prijavljen = false;
    private Socket socket;
    private ClientForma forma;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private String naziv;

    public Client(ClientForma forma,String naziv) {
        this.port = 9000;
        prijavljen = false;
        this.forma = forma;
        this.naziv = naziv;
        prijaviSe(naziv);
    }

    public void prijaviSe(String naziv) {
        this.naziv = naziv;
        start();
    }

    @Override
    public void run() {
        System.out.println("Prijavljujem igraca " + naziv);
//        if (prijavljen) {
//            forma.prikaziPorukuOdServera("Vec ste prijavljeni!");
//            return;
//        }
        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), 9000);
            prijavljen = prijaviSeServeru(naziv);
            if (prijavljen) {
                cekajPocetak();
            }
        } catch (Exception e) {
            forma.prikaziPorukuOdServera("Nije moguca konekcija na kviz");
            e.printStackTrace();
        }
    }

    public void posaljiOdgovor(String odgovor) {
        posaljiPoruku(socket, odgovor);
    }

    public void initAll() {
        forma.prikaziBrojPoena("");
        forma.prikaziPitanje("");
        forma.prikaziPorukuOdServera("");
        forma.onemoguciPrijavu();
        forma.ocistiOdgovor();
    }

    public synchronized void cekajPocetak() throws InterruptedException, IOException {
        initAll();
        System.out.println("Cekam pocetak!");
        String poruka = obradiPoruku(socket);
        System.out.println("Stigla poruka! " + poruka);
        if (poruka.equals(Server.KRECE_KVIZ)) {
            forma.prikaziPorukuOdServera("KRECE KVIZ!");
        }
        boolean kraj = false;
        while (!kraj) {
            forma.onemoguciSlanjeOdgovora();
            poruka = obradiPoruku(socket);
            System.out.println("Dosla poruka" + poruka);
            if (poruka.equals(Server.KRAJ)) {
                String rezultat = obradiPoruku(socket);
                forma.prikaziBrojPoena(rezultat);
                String pobednik = obradiPoruku(socket);
                forma.prikaziPorukuOdServera(pobednik);
                posaljiOdgovor("OK");
                kraj = true;
            } else {
                forma.prikaziPitanje(poruka);
                String rezultat = obradiPoruku(socket);
                forma.prikaziBrojPoena(rezultat);
                obradiPoruku(socket);
                forma.prikaziPorukuOdServera("Odgovoreno je na pitanje");
            }

        }
        forma.omoguciPrijavu();
        if (oos != null) {
            oos.close();
        }
        if (ois != null) {
            ois.close();
        }
        if (!socket.isClosed()) {
            socket.close();
        }
    }

    private boolean prijaviSeServeru(String naziv) {
        try {
            posaljiPoruku(socket, naziv);
            String odgovor = obradiPoruku(socket);
            if (odgovor.equals(Server.USPESNA_PRIJAVA)) {
                forma.prikaziPorukuOdServera("Uspesno prijavljen, cekamo ostale");
                return true;
            } else {
                forma.prikaziPorukuOdServera("Igrac sa nazivom " + naziv + " je vec prijavljen, probajte drugi naziv");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            forma.prikaziPorukuOdServera("Nesto nije uredu, probajte opet");

            return false;
        }

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
            System.out.println("SALJEM PORUKU: " + poruka);
            //create ObjectOutputStream object
            oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            oos.writeObject(poruka);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
