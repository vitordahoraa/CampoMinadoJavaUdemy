package org.campoMinado.View;

import org.campoMinado.Tabuleiro;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal(){

        Tabuleiro tab1 = new Tabuleiro(15,10,10);
        add(new PainelTabuleiro(tab1));
        setTitle("Campo Minado");
        setSize(690,438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }

}
