package src;

import javax.swing.*;
import java.awt.*;

public class CalculadoraGUI extends JFrame {
    private JPanel mainPanel;
    private JLabel resultLabel;
    private double lastResult = 0;

    public CalculadoraGUI() {
        setTitle("Calculadora Grupo 1 - Matrix Style");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colores.FONDO);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new CardLayout());
        Colores.estilizarPanel(mainPanel);
        mainPanel.add(new PanelBasico(this), "Basico");
        mainPanel.add(new PanelVector(this), "Vector");
        mainPanel.add(new PanelMatriz(this), "Matriz");
        mainPanel.add(new PanelSistemas(this), "Sistemas");

        JPanel menuPanel = new JPanel();
        Colores.estilizarPanel(menuPanel);

        JButton btnBasico = new JButton("Basico");
        JButton btnVector = new JButton("Vectores");
        JButton btnMatriz = new JButton("Matrices");
        JButton btnSistemas = new JButton("Sistemas");

        Colores.estilizarBoton(btnBasico);
        Colores.estilizarBoton(btnVector);
        Colores.estilizarBoton(btnMatriz);
        Colores.estilizarBoton(btnSistemas);

        btnBasico.addActionListener(e -> showPanel("Basico"));
        btnVector.addActionListener(e -> showPanel("Vector"));
        btnMatriz.addActionListener(e -> showPanel("Matriz"));
        btnSistemas.addActionListener(e -> showPanel("Sistemas"));

        menuPanel.add(btnBasico);
        menuPanel.add(btnVector);
        menuPanel.add(btnMatriz);
        menuPanel.add(btnSistemas);

        add(menuPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        resultLabel = new JLabel("Resultado: ");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(Colores.FUENTE_TITULO);
        resultLabel.setForeground(Colores.LETRA);

        add(resultLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setResult(double result) {
        this.lastResult = result;
        resultLabel.setText("Resultado: " + result);
    }

    public double getLastResult() {
        return lastResult;
    }

    public void showPanel(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraGUI::new);
    }
}
