package vistas;

import hotel.Hotel;
import hotel.SistemaHotel;
import java.awt.BorderLayout;
import javax.swing.*;

public class VentanaHotel extends JFrame {

    private Hotel hotel;
    private PanelGrafo panel;
    private JTextArea infoArea;

    public VentanaHotel(int pisoSeleccionado) {

        hotel = new Hotel();
        SistemaHotel.inicializarDatos(hotel);

        setTitle("Hotel");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                regresarMenu();
            }
        });

        // ======================
        // ÁREA DE INFO
        // ======================
        infoArea = new JTextArea(8, 40);
        JScrollPane scrollInfo = new JScrollPane(infoArea);

        // ======================
        // PANEL DEL GRAFO
        // ======================
        panel = new PanelGrafo(hotel, infoArea);
        panel.setPiso(pisoSeleccionado);

 
        JScrollPane scrollGrafo = new JScrollPane(panel);

        // ======================
        // BOTÓN
        // ======================
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> regresarMenu());

        JPanel superior = new JPanel();
        superior.add(btnRegresar);

        // ======================
        // AGREGAR COMPONENTES
        // ======================
        add(superior, BorderLayout.NORTH);
        add(scrollGrafo, BorderLayout.CENTER); // 🔥 IMPORTANTE
        add(scrollInfo, BorderLayout.SOUTH);

        // ======================
        // FULLSCREEN AUTOMÁTICO
        // ======================
        if (pisoSeleccionado == -1) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    private void regresarMenu() {
        new VentanaMenu().setVisible(true);
        dispose();
    }
}