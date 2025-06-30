package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelMatriz extends JPanel {
    private CalculadoraGUI frame;
    private JTextField matrizAField, matrizBField, escalarField;
    private JTextArea resultadoArea;

    public PanelMatriz(CalculadoraGUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        Colores.estilizarPanel(this);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        Colores.estilizarPanel(inputPanel);

        matrizAField = new JTextField();
        matrizBField = new JTextField();
        escalarField = new JTextField();

        Colores.estilizarCampoTexto(matrizAField);
        Colores.estilizarCampoTexto(matrizBField);
        Colores.estilizarCampoTexto(escalarField);

        JLabel labelA = new JLabel("Matriz A (1,2;3,4):");
        JLabel labelB = new JLabel("Matriz B (5,6;7,8):");
        JLabel labelEscalar = new JLabel("Escalar:");
        Colores.estilizarEtiqueta(labelA);
        Colores.estilizarEtiqueta(labelB);
        Colores.estilizarEtiqueta(labelEscalar);

        inputPanel.add(labelA);
        inputPanel.add(matrizAField);
        inputPanel.add(labelB);
        inputPanel.add(matrizBField);
        inputPanel.add(labelEscalar);
        inputPanel.add(escalarField);

        JButton btnLimpiar = new JButton("Limpiar");
        Colores.estilizarBoton(btnLimpiar);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        inputPanel.add(btnLimpiar);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        Colores.estilizarPanel(buttonPanel);

        String[] ops = {
            "A + B", "A - B", "Escalar × A",
            "A × B", "Det(A)", "A^T",
            "A^-1", "A / B"
        };
        for (String op : ops) {
            JButton btn = new JButton(op);
            Colores.estilizarBoton(btn);
            btn.addActionListener(e -> operar(op));
            buttonPanel.add(btn);
        }

        resultadoArea = new JTextArea(5, 50);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        Colores.estilizarCampoTexto(resultadoArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);
    }

    private double[][] leerMatriz(JTextField field) {
        String text = field.getText().trim();
        if (text.isEmpty()) return null;
        String[] filas = text.split(";");
        double[][] matriz = new double[filas.length][];
        for (int i = 0; i < filas.length; i++) {
            String[] elementos = filas[i].trim().split(",");
            matriz[i] = new double[elementos.length];
            for (int j = 0; j < elementos.length; j++) {
                matriz[i][j] = Double.parseDouble(elementos[j].trim());
            }
        }
        return matriz;
    }

    private void operar(String operacion) {
        try {
            double[][] a = leerMatriz(matrizAField);
            double[][] b = leerMatriz(matrizBField);
            double escalar = escalarField.getText().isEmpty() ? 1 : Double.parseDouble(escalarField.getText().trim());

            MatrixCalculator calc = new MatrixCalculator();
            String res = "";
            switch (operacion) {
                case "A + B":
                    res = MatrixCalculator.matrixToString(calc.sumar(a, b));
                    break;
                case "A - B":
                    res = MatrixCalculator.matrixToString(calc.restar(a, b));
                    break;
                case "Escalar × A":
                    res = MatrixCalculator.matrixToString(calc.multiplicarPorEscalar(a, escalar));
                    break;
                case "A × B":
                    res = MatrixCalculator.matrixToString(calc.multiplicar(a, b));
                    break;
                case "Det(A)":
                    res = String.valueOf(calc.determinante(a));
                    break;
                case "A^T":
                    res = MatrixCalculator.matrixToString(calc.transpuesta(a));
                    break;
                case "A^-1":
                    res = MatrixCalculator.matrixToString(calc.inversa(a));
                    break;
                case "A / B":
                    res = MatrixCalculator.matrixToString(calc.dividir(a, b));
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
        matrizAField.setText("");
        matrizBField.setText("");
        escalarField.setText("");
        resultadoArea.setText("");
    }
}