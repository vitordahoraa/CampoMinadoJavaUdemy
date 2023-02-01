package org.campoMinado;

public class Main {
    public static void main(String[] args) {

        Tabuleiro tabuleiro1 = new Tabuleiro(6,6,3);
        Menu menu = new Menu(tabuleiro1);

        menu.executar();
        
    }
}