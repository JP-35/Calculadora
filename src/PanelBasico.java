package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelBasico extends JPanel {
    private CalculadoraGUI frame;
    private JTextField campoA, campoB;
    private JTextArea resultadoArea;

    public PanelBasico(CalculadoraGUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        Colores.estilizarPanel(this);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        Colores.estilizarPanel(inputPanel);

        campoA = new JTextField();
        campoB = new JTextField();

        Colores.estilizarCampoTexto(campoA);
        Colores.estilizarCampoTexto(campoB);

        JLabel labelA = new JLabel("A:");
        JLabel labelB = new JLabel("B:");
        Colores.estilizarEtiqueta(labelA);
        Colores.estilizarEtiqueta(labelB);

        inputPanel.add(labelA);
        inputPanel.add(campoA);
        inputPanel.add(labelB);
        inputPanel.add(campoB);

        JButton btnUsarUlt = new JButton("ANS");
        Colores.estilizarBoton(btnUsarUlt);
        btnUsarUlt.addActionListener(e -> campoA.setText(String.valueOf(frame.getLastResult())));
        inputPanel.add(btnUsarUlt);

        JButton btnLimpiar = new JButton("Limpiar");
        Colores.estilizarBoton(btnLimpiar);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        inputPanel.add(btnLimpiar);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        Colores.estilizarPanel(buttonPanel);

        String[] ops = {"Suma", "Resta", "Multiplicación", "División", "Potencia", "Raíz"};
        for (String op : ops) {
            JButton btn = new JButton(op);
            Colores.estilizarBoton(btn);
            btn.addActionListener(e -> operar(op));
            buttonPanel.add(btn);
        }

        resultadoArea = new JTextArea(3, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        Colores.estilizarCampoTexto(resultadoArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);
    }

    private void operar(String operacion) {
        try {
            double a = campoA.getText().isEmpty() ? 0 : Double.parseDouble(campoA.getText().trim());
            double b = campoB.getText().isEmpty() ? 0 : Double.parseDouble(campoB.getText().trim());
            CalculadoraBasica calc = new CalculadoraBasica();
            double res = 0;
            switch (operacion) {
                case "Suma": res = calc.suma(a, b); break;
                case "Resta": res = calc.resta(a, b); break;
                case "Multiplicación": res = calc.multiplicacion(a, b); break;
                case "División": res = calc.division(a, b); break;
                case "Potencia": res = calc.potencia(a, b); break;
                case "Raíz": res = calc.raiz(a, b); break;
            }
            resultadoArea.setText(String.valueOf(res));
            frame.setResult(res);
        } catch (Exception ex) {
            resultadoArea.setText("Error en los datos: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        campoA.setText("");
        campoB.setText("");
        resultadoArea.setText("");
    }
}
