package org.campoMinado;

import org.campoMinado.View.Menu;

public class Main {
    public static void main(String[] args) {

        Tabuleiro tabuleiro1 = new Tabuleiro(6,6,3);

        tabuleiro1.RegistrarObservador(e-> {
            if(e)
                System.out.println("Ganhou!");
            else {
                System.out.println("Perdeu");
                throw new ExplosionException();
            }
        });
        Menu menu = new Menu(tabuleiro1);

        menu.executar();
        
    }
}