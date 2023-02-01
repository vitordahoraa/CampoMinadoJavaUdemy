package org.campoMinado;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private Campo campo1;
    private Tabuleiro tabuleiro1;
    private Menu menu;
    @BeforeEach
    void iniciarCampo() {
        campo1 = new Campo(1, 1, false);
        tabuleiro1 =  new Tabuleiro(6,6,6);
        menu = new Menu(tabuleiro1);
    }
    @Test
    void testVizinho1(){
        Campo campo2 = new Campo(2,2,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }

    @Test
    void testVizinho2(){
        Campo campo2 = new Campo(1,2,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }
    @Test
    void testVizinho3(){
        Campo campo2 = new Campo(1,0,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }
    @Test
    void testVizinho4(){
        Campo campo2 = new Campo(2,0,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }

    @Test
    void testVizinho5(){
        Campo campo2 = new Campo(2,1,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }

    @Test
    void testVizinho6(){
        Campo campo2 = new Campo(0,0,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }

    @Test
    void testVizinho7(){
        Campo campo2 = new Campo(0,1,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }

    @Test
    void testVizinho8(){
        Campo campo2 = new Campo(0,2,true);
        boolean resultado = campo1.AdicionarVizinho(campo2);
        assertTrue(resultado);
    }

    @Test
    void isMarcado(){
        campo1.alternarMarcado();
        assertTrue(campo1.getMarcado());
    }

    @Test
    void DoubleMarcado(){
        campo1.alternarMarcado();
        campo1.alternarMarcado();
        assertFalse(campo1.getMarcado());
    }

    @Test
    void AbrirNMinadoNMarcado(){
        assertTrue(campo1.abrir());
    }
    @Test
    void AbrirNMinadoMarcado(){
        campo1.alternarMarcado();
        assertFalse(campo1.abrir());
    }
    @Test
    void AbrirMinadoNMarcado(){
        campo1.minar();
        assertThrows(ExplosionException.class,()->{campo1.abrir();});
    }


    @Test
    void ProximidadeSegura(){
        Campo campo2 = new Campo(1,2,false);
        campo1.AdicionarVizinho(campo2);


        assertTrue(campo1.proximidadeSegura());
    }

    @Test
    void ProximidadeNaoSegura(){

        Campo campo2 = new Campo(1,2);

        campo1.AdicionarVizinho(campo2);


        assertTrue(campo1.proximidadeSegura());
    }
    @Test
    void AbirVizinho(){
        Campo campo2 = new Campo(1,2,false);
        campo1.AdicionarVizinho(campo2);

        campo1.abrir();

        assertTrue(campo2.getAberto());
    }

    @Test
    void AbirVizinhoVizinho(){
        Campo campo2 = new Campo(1,2,false);
        Campo campo3 = new Campo(1,3,false);
        campo1.AdicionarVizinho(campo2);
        campo2.AdicionarVizinho(campo3);

        campo1.abrir();

        assertTrue(campo2.getAberto() && campo3.getAberto());
    }
    @Test
    void AbirVizinhoVizinhoMinado(){
        Campo campo2 = new Campo(1,2,false);
        Campo campo3 = new Campo(2,1,true);
        campo1.AdicionarVizinho(campo2);
        campo1.AdicionarVizinho(campo3);
        campo1.abrir();

        assertTrue(!campo2.getAberto() && !campo3.getAberto());
    }

    @Test
    void TabuleiroToString(){
        Tabuleiro t = new Tabuleiro(6,6,6);
        System.out.println(t.toString());
        assertTrue(true);
    }

    @Test
    void TabuleiroAbrir(){
        tabuleiro1.abrir(1,1);
        System.out.println(tabuleiro1);
        assertTrue(tabuleiro1.getCampo(1,1).getAberto());
    }
    @Test
    void TabuleiroMarcar(){
        tabuleiro1.marcar(1,1);
        System.out.println(tabuleiro1);
        assertTrue(tabuleiro1.getCampo(1,1).getMarcado());
    }
    @Test
    void MenuSair(){
        menu.executar();
        assertTrue(true);
    }

}