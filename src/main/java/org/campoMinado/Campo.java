package org.campoMinado;

import org.campoMinado.Enum.CampoEvento;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Campo {

    private int linha;
    private int coluna; //Posição do campo em relação a tabela
    private boolean minado = false; //True caso campo tenha uma mina
    private boolean aberto = false; //Todos os campos começam fechados

    private boolean marcado = false; //Todos os campos começam não marcados
    private List<Campo> camposVizinho = new ArrayList<Campo>(); //Lista que armazena uma lista de campos com minas nas
                                                                // proximidades
    private Set<CampoObservador> observers = new LinkedHashSet<>();

    public boolean getMarcado(){return this.marcado;}
    public boolean getAberto(){return this.aberto;}
    public boolean getMinado(){return this.minado;}
    public int getLinha(){return this.linha;}
    public int getColuna(){return this.coluna;}

    public void setAberto(boolean abrir){
        this.aberto=abrir;
        if(aberto)
            ExecutarEvento(CampoEvento.ABRIR);
    }
    public void RegistrarObservador(CampoObservador Obs){
        observers.add(Obs);
    }

    public void ExecutarEvento(CampoEvento evento){
        observers.stream().forEach(o->o.EventoOcorreu(this,evento));
    }

    Campo(int linha, int coluna, boolean minado){
        this.coluna = coluna;
        this.linha =  linha;
        this.minado = minado;
    }
    Campo(int linha, int coluna){
        this.coluna = coluna;
        this.linha =  linha;
    }


    boolean AdicionarVizinho(Campo vizinho){// Verifica se o campo está em adjacência, e se ele é minado
        if(vizinho.linha == this.linha + 1 || vizinho.linha == this.linha-1 || vizinho.linha == this.linha){
            if(vizinho.coluna == this.coluna+1 || vizinho.coluna == this.coluna-1||vizinho.coluna == this.coluna){
                this.camposVizinho.add(vizinho);
                return true;
            }
        }
        return false;
    }

    public void alternarMarcado() {//Marca ou desmarca o campo, apenas se ele estiver fechado
        if (!aberto) {
            this.marcado = !this.marcado;
            if(marcado)
                ExecutarEvento(CampoEvento.MARCAR);
            else
                ExecutarEvento(CampoEvento.DESMARCAR);
        }
    }
    public boolean abrir() { // Abre o campo, se ele não estiver aberto e nem marcado. Se possuir uma bomba, perde o jogo
        if (!aberto && !marcado) {
            this.aberto = true;
            if(minado){
                ExecutarEvento(CampoEvento.EXPLODIR);
                return true;
            }

            ExecutarEvento(CampoEvento.ABRIR);

            //Se nenhum vizinho tem bomba, abre eles também, inicando o processo recursivo
            if(proximidadeSegura()){
                camposVizinho.forEach(v-> v.abrir());
            }

            return true;
        }
        return false;
    }

    public boolean proximidadeSegura(){//Caso nenhum vizinho tenha mina, retorna True
        return camposVizinho.stream().noneMatch(v -> v.minado);
    }

    void minar(){ // coloca uma mina no campo
        this.minado = true;
    }

    public long minasVizinhanca(){ // retorna o número de minas na vizinhança
        return this.camposVizinho.stream().filter(v -> v.minado).count();
    }

    @Override
    public String toString() { //  "x" para marcado, * para  mina explodida, número de bombas na proximidade, " " para aberto sem bombas próximas, e ? para não aberto nem marcado
        if(marcado)
            return "x";
        if( aberto && minado)
            return "*";
        if(aberto && minasVizinhanca() > 0)
            return Long.toString(minasVizinhanca());
        if(aberto)
            return " ";
        return "?";
    }

    boolean isOver(){
        if(minado && marcado) return true;
        if(!minado && aberto) return true;
        return false;
    }

    void reiniciar(){
        this.aberto = false;
        this.minado = false;
        this.marcado= false;
        ExecutarEvento(CampoEvento.REINICIAR);

    }

    boolean ObjetivoAlcancado(){
        return (this.minado && this.marcado) || (this.aberto && !this.minado);
    }

}
