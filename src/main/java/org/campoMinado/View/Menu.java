package org.campoMinado.View;

import org.campoMinado.Campo;
import org.campoMinado.ExplosionException;
import org.campoMinado.SairException;
import org.campoMinado.Tabuleiro;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    private Tabuleiro tabuleiro;

    Scanner scan = new Scanner(System.in);
    public Menu(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;
    }

    public void executar(){
        try{
            boolean jogoAberto = true;
            while(jogoAberto){
                Jogo();
                System.out.println("Você deseja continuar? (s/n)");
                String resposta;
                resposta=scan.nextLine();
                if(resposta.equalsIgnoreCase("n"))
                    jogoAberto=false;
                else
                    tabuleiro.reiniciar();
            }
        }
        catch (SairException e){
            System.out.println("Você saiu do jogo!");
        }
        finally{
            scan.close();
        }
    }

    private void Jogo(){
        try{
            while(!tabuleiro.ObjetivoAlcancado()) {
                System.out.println(tabuleiro);
                this.TomarAcao(this.getCampoByCord());
            }
            System.out.println("Você ganhou!");
        }
        catch(ExplosionException e){
            System.out.println("Você perdeu!");
        }
    }

    private Campo getCampoByCord(){
        System.out.println("Digite o número da linha");
        int linha = Integer.parseInt(tratarInput());
        System.out.println("Digite o número da coluna");
        int coluna = Integer.parseInt(tratarInput());

        return tabuleiro.getCampo(linha-1,coluna-1);
    }

    private void TomarAcao(Campo campo){

        System.out.println("Digite 1 para abrir o campo, e 2 para alternar a marcacao");
        int input = Integer.parseInt(tratarInput());

        if(input == 1)
            campo.abrir();
        if(input==2)
            campo.alternarMarcado();
    }

    private String tratarInput(){
        String resposta = scan.nextLine();

        if(resposta.equalsIgnoreCase("sair"))
            throw new SairException();
        return resposta;
    }

}
