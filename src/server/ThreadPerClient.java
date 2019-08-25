/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author filip
 */
public class ThreadPerClient extends Thread {

    private Socket socket;
    private Server server;
    private String username;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public ThreadPerClient(Socket socket, Server server, String username) {
        this.socket = socket;
        this.server = server;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        posaljiPoruku(Server.KRECE_KVIZ);
        while (!server.jelKraj()) {
            String odgovor = obradiPoruku(socket);
            synchronized (this) {
                server.postaviOdgovorTima(odgovor);
            }
        }
        try {
            oos.close();
            ois.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void posaljiPoruku(String poruka) {
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

}
