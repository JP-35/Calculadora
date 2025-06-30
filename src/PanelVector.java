package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelVector extends JPanel {
    private CalculadoraGUI frame;
    private JTextField vectorAField, vectorBField, escalarField;
    private JTextArea resultadoArea;

    public PanelVector(CalculadoraGUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        Colores.estilizarPanel(this);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        Colores.estilizarPanel(inputPanel);

        vectorAField = new JTextField();
        vectorBField = new JTextField();
        escalarField = new JTextField();

        Colores.estilizarCampoTexto(vectorAField);
        Colores.estilizarCampoTexto(vectorBField);
        Colores.estilizarCampoTexto(escalarField);

        JLabel labelA = new JLabel("Vector A (1,2,3):");
        JLabel labelB = new JLabel("Vector B (4,5,6):");
        JLabel labelEscalar = new JLabel("Escalar:");
        Colores.estilizarEtiqueta(labelA);
        Colores.estilizarEtiqueta(labelB);
        Colores.estilizarEtiqueta(labelEscalar);

        inputPanel.add(labelA);
        inputPanel.add(vectorAField);
        inputPanel.add(labelB);
        inputPanel.add(vectorBField);
        inputPanel.add(labelEscalar);
        inputPanel.add(escalarField);

        JButton btnLimpiar = new JButton("Limpiar");
        Colores.estilizarBoton(btnLimpiar);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        inputPanel.add(btnLimpiar);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        Colores.estilizarPanel(buttonPanel);

        String[] ops = {"A + B", "A - B", "Escalar × A", "A ⋅ B", "A × B"};
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

    private double[] leerVector(JTextField field) {
        String[] partes = field.getText().trim().split(",");
        double[] vector = new double[partes.length];
        for (int i = 0; i < partes.length; i++) {
            vector[i] = Double.parseDouble(partes[i].trim());
        }
        return vector;
    }

    private void operar(String operacion) {
        try {
            double[] a = leerVector(vectorAField);
            double[] b = vectorBField.getText().isEmpty() ? null : leerVector(vectorBField);
            double escalar = escalarField.getText().isEmpty() ? 1 : Double.parseDouble(escalarField.getText().trim());

            VectorCalculator calc = new VectorCalculator();
            String res = "";
            switch (operacion) {
                case "A + B":
                    res = VectorCalculator.vectorToString(calc.sumar(a, b));
                    break;
                case "A - B":
                    res = VectorCalculator.vectorToString(calc.restar(a, b));
                    break;
                case "Escalar × A":
                    res = VectorCalculator.vectorToString(calc.multiplicarPorEscalar(a, escalar));
                    break;
                case "A ⋅ B":
                    res = String.valueOf(calc.productoEscalar(a, b));
                    break;
                case "A × B":
                    res = VectorCalculator.vectorToString(calc.productoVectorial(a, b));
                    break;
            }
            resultadoArea.setText(res);
            try {
                frame.setResult(Double.parseDouble(res));
            } catch (NumberFormatException ex) {
                frame.setResult(0);
            }
        } catch (Exception ex) {
            resultadoArea.setText("Error en los datos: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        vectorAField.setText("");
        vectorBField.setText("");
        escalarField.setText("");
        resultadoArea.setText("");
    }
}