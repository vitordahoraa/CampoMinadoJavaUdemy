package org.campoMinado;

import org.campoMinado.Enum.CampoEvento;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObservador{

    private final int linhas;
    private final int colunas;

    private int minas;

    public int getLinhas() {
        return linhas;
    }


    public int getColunas() {
        return colunas;
    }


    private List<Campo> campos = new ArrayList<Campo>();

    private Set<Consumer<Boolean>> observers = new HashSet<>();

    public void RegistrarObservador(Consumer<Boolean> c){
        observers.add(c);
    }
    private void ExecutarEvento(Boolean b){
        observers.stream().forEach(c-> c.accept(b));
    }


    public Tabuleiro(int linhas, int colunas, int minas){
        this.linhas=linhas;
        this.colunas=colunas;
        this.minas=minas;
        criar();
        vizinhar();
        minar();
    }

    public Campo getCampo(int linha, int coluna){
        return campos.stream().filter(c-> c.getLinha()==linha && c.getColuna()==coluna).findFirst().get();
    }

    private void criar(){
        for(int i = 0; i < linhas; i++)
            for(int j = 0;j<colunas;j++) {
                Campo campo1 = new Campo(i, j);
                campo1.RegistrarObservador(this);
                campos.add(campo1);
            }
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
        sb.append("  ");
        for(int i = 0; i < colunas;i++){
            sb.append(i+1).append(" ");
        }

        sb.append("\n");

        for(int i = 0; i < linhas;i++){
            sb.append(i+1).append(" ");
            for(int j = 0; j < colunas; j++){
                sb.append(campos.get((i*linhas) + j).toString()).append(" ");
            }
            sb.append("\n");

        }

        return sb.toString();
    }

    public void reiniciar(){
        campos.stream().forEach(c-> c.reiniciar());
        minar();
    }

    public boolean ObjetivoAlcancado(){
        return campos.stream().allMatch(c-> c.ObjetivoAlcancado());
    }

    @Override
    public void EventoOcorreu(Campo campo, CampoEvento CampoEvento) {
        if(CampoEvento == CampoEvento.EXPLODIR){
            mostrarMinas();
            ExecutarEvento(Boolean.FALSE);
        }else if(ObjetivoAlcancado()){
            ExecutarEvento(Boolean.TRUE);
        }
    }

    private void mostrarMinas() {
        campos.stream().filter(c -> c.getMinado()).filter(c-> !c.getMarcado()).forEach(c -> c.setAberto(true));
    }

    public void ForEachCampo(Consumer<Campo> c){
        campos.forEach(c);
    }
}
