package org.campoMinado.View;

import org.campoMinado.Tabuleiro;
import org.campoMinado.View.Components.ButtonCampo;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {
    PainelTabuleiro(Tabuleiro tab1){

        setLayout(new GridLayout(tab1.getLinhas(),tab1.getColunas()));

        tab1.ForEachCampo(c-> add(new ButtonCampo(c)));
        tab1.RegistrarObservador(e->{
            SwingUtilities.invokeLater(()->{

                if(e){
                    JOptionPane.showMessageDialog(this,"Ganhou");
                }    else{
                    JOptionPane.showMessageDialog(this,"Perdeu");
                }
                tab1.reiniciar();
            });
        });
    }
}
