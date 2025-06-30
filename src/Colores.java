package src;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Colores {
    public static final Color FONDO = new Color(10, 20, 10);
    public static final Color VERDE_CLARO = new Color(80, 255, 80);
    public static final Color VERDE_FUERTE = new Color(0, 220, 70);
    public static final Color VERDE_BOTON = new Color(40, 200, 80);
    public static final Color LETRA = VERDE_CLARO;
    public static final Color LETRA_BOTON = new Color(10, 255, 120);

    public static final Font FUENTE_MATRIX = new Font("Consolas", Font.BOLD, 16);
    public static final Font FUENTE_TITULO = new Font("Consolas", Font.BOLD, 20);

    public static void estilizarBoton(JButton boton) {
        boton.setBackground(VERDE_BOTON);
        boton.setForeground(FONDO);
        boton.setFont(FUENTE_MATRIX);
        boton.setBorder(BorderFactory.createLineBorder(VERDE_CLARO, 2));
        boton.setFocusPainted(false);
    }

    public static void estilizarPanel(JPanel panel) {
        panel.setBackground(FONDO);
        panel.setForeground(LETRA);
    }

    public static void estilizarCampoTexto(JTextComponent campo) {
        campo.setBackground(FONDO);
        campo.setForeground(LETRA);
        campo.setCaretColor(VERDE_CLARO);
        campo.setFont(FUENTE_MATRIX);
        campo.setBorder(BorderFactory.createLineBorder(VERDE_CLARO, 1));
    }

    public static void estilizarEtiqueta(JLabel label) {
        label.setForeground(LETRA);
        label.setFont(FUENTE_MATRIX);
    }
}