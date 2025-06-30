package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelSistemas extends JPanel {
    private CalculadoraGUI frame;
    private JComboBox<String> tipoSistema;
    private JTextField[] coefFields;
    private JTextField[] indepFields;
    private JTextArea resultadoArea;

    public PanelSistemas(CalculadoraGUI frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        Colores.estilizarPanel(this);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        Colores.estilizarPanel(inputPanel);

        tipoSistema = new JComboBox<>(new String[]{"2x2", "3x3"});
        tipoSistema.setFont(Colores.FUENTE_MATRIX);
        tipoSistema.setBackground(Colores.FONDO);
        tipoSistema.setForeground(Colores.LETRA);
        tipoSistema.addActionListener(e -> actualizarCampos());

        JPanel filaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Colores.estilizarPanel(filaTipo);
        filaTipo.add(new JLabel("Tipo de sistema:"));
        filaTipo.add(tipoSistema);
        inputPanel.add(filaTipo);

        // Inicialización de campos
        coefFields = new JTextField[9];
        indepFields = new JTextField[3];
        for (int i = 0; i < 9; i++) {
            coefFields[i] = new JTextField(2);
            Colores.estilizarCampoTexto(coefFields[i]);
        }
        for (int i = 0; i < 3; i++) {
            indepFields[i] = new JTextField(2);
            Colores.estilizarCampoTexto(indepFields[i]);
        }

        inputPanel.add(crearPanelCampos());

        JButton btnResolver = new JButton("Resolver");
        Colores.estilizarBoton(btnResolver);
        btnResolver.addActionListener(e -> resolver());

        JButton btnLimpiar = new JButton("Limpiar");
        Colores.estilizarBoton(btnLimpiar);
        btnLimpiar.addActionListener(e -> limpiarCampos());

        JPanel acciones = new JPanel();
        Colores.estilizarPanel(acciones);
        acciones.add(btnResolver);
        acciones.add(btnLimpiar);

        resultadoArea = new JTextArea(3, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        Colores.estilizarCampoTexto(resultadoArea);

        add(inputPanel, BorderLayout.NORTH);
        add(acciones, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        actualizarCampos();
    }

    private JPanel crearPanelCampos() {
        JPanel panel = new JPanel();
        Colores.estilizarPanel(panel);
        panel.setLayout(new GridLayout(4, 4, 5, 5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panel.add(coefFields[i * 3 + j]);
            }
            panel.add(indepFields[i]);
        }
        return panel;
    }

    private void actualizarCampos() {
        int dim = tipoSistema.getSelectedIndex() == 0 ? 2 : 3;
        for (int i = 0; i < 3; i++) {
            indepFields[i].setVisible(i < dim);
            for (int j = 0; j < 3; j++) {
                coefFields[i * 3 + j].setVisible(i < dim && j < dim);
            }
        }
        revalidate();
        repaint();
    }

    private void resolver() {
        try {
            int dim = tipoSistema.getSelectedIndex() == 0 ? 2 : 3;
            double[][] coefs = new double[dim][dim];
            double[] indep = new double[dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    coefs[i][j] = Double.parseDouble(coefFields[i * 3 + j].getText().trim());
                }
                indep[i] = Double.parseDouble(indepFields[i].getText().trim());
            }
            double[] sol;
            if (dim == 2)
                sol = SistemaEcuaciones.resolver2x2(coefs, indep);
            else
                sol = SistemaEcuaciones.resolver3x3(coefs, indep);

            StringBuilder sb = new StringBuilder("Solución: ");
            for (int i = 0; i < sol.length; i++) {
                sb.append("x").append(i + 1).append(" = ").append(sol[i]).append("  ");
            }
            resultadoArea.setText(sb.toString());
            frame.setResult(sol[0]);
        } catch (Exception ex) {
            resultadoArea.setText("Error: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        for (JTextField f : coefFields) f.setText("");
        for (JTextField f : indepFields) f.setText("");
        resultadoArea.setText("");
    }
}