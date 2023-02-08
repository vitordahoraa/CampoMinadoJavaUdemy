package org.campoMinado.View.Components;

import org.campoMinado.Campo;
import org.campoMinado.CampoObservador;
import org.campoMinado.Enum.CampoEvento;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class ButtonCampo extends JButton implements CampoObservador, MouseListener {

    private Campo campo;
    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCADO = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);

    public ButtonCampo(Campo campo) {

        this.campo = campo;
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.RegistrarObservador(this);
    }

    @Override
    public void EventoOcorreu(Campo campo, CampoEvento CampoEvento) {
        switch (CampoEvento) {
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcado();
                break;
            case DESMARCAR:
                aplicarEstiloDefault();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloDefault();
        }
    }

    private void aplicarEstiloDefault() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setText("X");
    }

    private void aplicarEstiloMarcado() {

            setBackground(BG_MARCADO);
            setText("M");
            setForeground(Color.BLACK);
    }

    private void aplicarEstiloAbrir() {

        if(campo.getMinado()){
            aplicarEstiloExplodir();
            return;
        }

        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        switch((int) campo.minasVizinhanca()){
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
                setForeground(Color.PINK);
                break;
            case 5:
                setForeground(Color.MAGENTA);
                break;
            case 6:
                setForeground(Color.RED);
                break;
            case 7:
                setForeground(Color.ORANGE);
                break;
            case 8:
                setForeground(Color.BLACK);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = (!campo.proximidadeSegura() && !campo.getMinado()) ? campo.minasVizinhanca() + "" : "";
        setText(valor);
    }


    public void mousePressed(MouseEvent e) {

        if(e.getButton()==1){
            this.campo.abrir();
        }else{
            this.campo.alternarMarcado();
        }
    }
//Funções não implementadas da interface.
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
