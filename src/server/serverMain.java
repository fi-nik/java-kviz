/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import baza.Baza;
import baza.Kviz;
import java.util.List;

/**
 *
 * @author filip
 */
public class serverMain {

    public static void main(String[] args) {
        Baza.napraviKonekciju();
        List<Kviz> kvizovi = Baza.uzmiKvizove();
        ServerForma forma = new ServerForma(kvizovi);
        forma.setVisible(true);
    }
}
