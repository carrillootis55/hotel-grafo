package vistas;

import hotel.Hotel;
import hotel.SistemaHotel;
import java.awt.BorderLayout;
import javax.swing.*;

public class VentanaHotel extends JFrame {

    private Hotel hotel;
    private PanelArbol panel;
    private JTextArea infoArea;
    private boolean modoDesocupar;

    public VentanaHotel(Hotel hotel, int pisoSeleccionado, boolean modoDesocupar) {
    	this.modoDesocupar = modoDesocupar;
    	this.hotel = hotel;

        //hotel = new Hotel();
        //SistemaHotel.inicializarDatos(hotel);

        //setTitle("Hotel");
        setTitle(modoDesocupar ? "Desocupar Habitaciones" : "Visualizar Hotel");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                regresarMenu();
            }
        });


        infoArea = new JTextArea(8, 40);
        JScrollPane scrollInfo = new JScrollPane(infoArea);
        
        panel = new PanelArbol(hotel, infoArea, modoDesocupar);
        //panel = new PanelArbol(hotel, infoArea);
        panel.setPiso(pisoSeleccionado);

 
        JScrollPane scrollGrafo = new JScrollPane(panel);


        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> regresarMenu());

        JPanel superior = new JPanel();
        superior.add(btnRegresar);

        add(superior, BorderLayout.NORTH);
        add(scrollGrafo, BorderLayout.CENTER); 
        add(scrollInfo, BorderLayout.SOUTH);


        if (pisoSeleccionado == -1) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    private void regresarMenu() {
        new VentanaMenu(hotel).setVisible(true);
        dispose();
    }
}