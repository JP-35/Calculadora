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
    private JPanel camposPanel;

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

   
        coefFields = new JTextField[9];
        indepFields = new JTextField[3];
        Dimension campoGrande = new Dimension(120, 55); 
        for (int i = 0; i < 9; i++) {
            coefFields[i] = new JTextField();
            coefFields[i].setPreferredSize(campoGrande);
            coefFields[i].setFont(Colores.FUENTE_MATRIX);
            Colores.estilizarCampoTexto(coefFields[i]);
        }
        for (int i = 0; i < 3; i++) {
            indepFields[i] = new JTextField();
            indepFields[i].setPreferredSize(campoGrande);
            indepFields[i].setFont(Colores.FUENTE_MATRIX);
            Colores.estilizarCampoTexto(indepFields[i]);
        }

        camposPanel = crearPanelCampos();
        inputPanel.add(camposPanel);

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

        resultadoArea = new JTextArea(5, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        Colores.estilizarCampoTexto(resultadoArea);

        add(inputPanel, BorderLayout.NORTH);
        add(acciones, BorderLayout.CENTER);
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);

        actualizarCampos();
    }

    private JPanel crearPanelCampos() {
        JPanel panel = new JPanel(new GridBagLayout());
        Colores.estilizarPanel(panel);
        int dim = tipoSistema == null ? 2 : (tipoSistema.getSelectedIndex() == 0 ? 2 : 3);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 12, 8, 12); // Más espacio alrededor de cada campo
        c.anchor = GridBagConstraints.CENTER;

        // Etiquetas de matriz
        JLabel lblCoefs = new JLabel("Coeficientes");
        lblCoefs.setFont(Colores.FUENTE_MATRIX);
        c.gridx = 1; c.gridy = 0; c.gridwidth = dim;
        panel.add(lblCoefs, c);

        JLabel lblIndep = new JLabel("Independientes");
        lblIndep.setFont(Colores.FUENTE_MATRIX);
        c.gridx = dim + 3; c.gridy = 0; c.gridwidth = 1;
        panel.add(lblIndep, c);

        // Campos de la matriz aumentada
        for (int i = 0; i < dim; i++) {
            // Corchete izquierdo
            JLabel corcheteIzq = new JLabel("[");
            corcheteIzq.setFont(Colores.FUENTE_MATRIX);
            c.gridx = 0; c.gridy = i + 1; c.gridwidth = 1;
            panel.add(corcheteIzq, c);

            // Campos de coeficientes
            for (int j = 0; j < dim; j++) {
                c.gridx = j + 1;
                coefFields[i * 3 + j].setVisible(true);
                panel.add(coefFields[i * 3 + j], c);
            }

            // Corchete derecho
            JLabel corcheteDer = new JLabel("]");
            corcheteDer.setFont(Colores.FUENTE_MATRIX);
            c.gridx = dim + 1;
            panel.add(corcheteDer, c);

            // Símbolo igual
            JLabel igual = new JLabel("=");
            igual.setFont(Colores.FUENTE_MATRIX);
            c.gridx = dim + 2;
            panel.add(igual, c);

            // Campo independiente
            c.gridx = dim + 3;
            indepFields[i].setVisible(true);
            panel.add(indepFields[i], c);
        }
        // Esconde los campos no usados en 2x2
        for (int i = dim; i < 3; i++) {
            indepFields[i].setVisible(false);
            for (int j = 0; j < 3; j++) {
                coefFields[i * 3 + j].setVisible(false);
            }
        }
        return panel;
    }

    private void actualizarCampos() {
        remove(camposPanel);
        camposPanel = crearPanelCampos();
        JPanel northPanel = (JPanel)((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.NORTH);
        northPanel.remove(1);
        northPanel.add(camposPanel);
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
