package org.campoMinado;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    private int linhas;
    private int colunas;

    private int minas;

    private List<Campo> campos = new ArrayList<Campo>();

    public Tabuleiro(int linhas, int colunas, int minas){
        this.linhas=linhas;
        this.colunas=colunas;
        this.minas=minas;
        criar();
        vizinhar();
        minar();
    }

    Campo getCampo(int linha, int coluna){
        return campos.stream().filter(c-> c.getLinha()==linha && c.getColuna()==coluna).findFirst().get();
    }

    private void criar(){
        for(int i = 0; i < linhas; i++)
            for(int j = 0;j<colunas;j++)
                campos.add(new Campo(i,j));
    }

    private void vizinhar(){
        for(Campo c1 :campos)
            for(Campo c2 : campos)
                c1.AdicionarVizinho(c2);
    }
    private void minar(){
        for(int i=0;i<minas;i++){
            int pos = (int)(Math.random()*campos.size());

            if(campos.get(pos).getMinado()){
                i--;
                continue;
            }

            campos.get(pos).minar();
        }
    }

    void abrir(int linha, int coluna){
        campos.stream().filter(c->c.getLinha()==linha && c.getColuna()==coluna)
                .findFirst()
                .ifPresent(c-> c.abrir());
    }

    void marcar(int linha, int coluna){
        campos.stream().filter(c->c.getLinha()==linha && c.getColuna()==coluna)
                .findFirst()
                .ifPresent(c-> c.alternarMarcado());
    }

    private boolean objetivo(){
        return campos.stream().allMatch(c -> c.isOver());
    }

    private void reinicar(){
        campos.forEach(c-> c.reiniciar());
        minar();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < linhas;i++){
            for(int j = 0; j < colunas; j++){
                sb.append(campos.get((i*linhas) + j).toString());
                sb.append(" ");
            }
            sb.append("\n");

        }

        return sb.toString();
    }
}
