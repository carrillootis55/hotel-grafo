package vistas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hotel.Hotel;

public class VentanaPrincipal extends JFrame {

    private Hotel hotel;

    public VentanaPrincipal(Hotel hotel) {
        this.hotel = hotel;

        setTitle("Menu Hotel");
        setSize(350,300);
        setResizable(false);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        


        setLayout(new BorderLayout());


        JLabel titulo = new JLabel("MENU HOTEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);


        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3,1,10,10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30,60,30,60));

        JButton btnVer = new JButton("Ver Hotel");
        JButton btnDesocupar = new JButton("Desocupar");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnVer);
        panelBotones.add(btnDesocupar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);


        btnVer.addActionListener(e -> {
            new VentanaMenu(hotel).setVisible(true);
            dispose();
        });

        btnDesocupar.addActionListener(e -> {
            new VentanaHotel(hotel, -1, true).setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> {
            new VentanaInicio().setVisible(true);
            dispose();
        });
    }
}