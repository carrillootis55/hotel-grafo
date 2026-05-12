package vistas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hotel.Hotel;
import hotel.SistemaHotel;

public class VentanaInicio extends JFrame {

    public VentanaInicio() {

        setTitle("HOTEL");
        //setSize(400,300);
        //setSize(380,500);
        setSize(380,220);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
       

        JLabel titulo = new JLabel("HOTEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 26));

        JLabel mensaje = new JLabel("Bienvenido Administrador", SwingConstants.CENTER);

        JPanel panelTexto = new JPanel(new GridLayout(2,1));
        panelTexto.add(titulo);
        panelTexto.add(mensaje);

        JButton btnIniciar = new JButton("Iniciar");
        JButton btnSalir = new JButton("Salir");
        
        JPanel panelBotones = new JPanel();

        panelBotones.setLayout(
            new BoxLayout(
                panelBotones,
                BoxLayout.Y_AXIS
            )
        );

        panelBotones.setBorder(
            BorderFactory.createEmptyBorder(
                20,
                110,
                20,
                110
            )
        );


        btnIniciar.setPreferredSize(
            new java.awt.Dimension(120,35)
        );

        btnSalir.setPreferredSize(
            new java.awt.Dimension(120,35)
        );

        btnIniciar.setMaximumSize(
            btnIniciar.getPreferredSize()
        );

        btnSalir.setMaximumSize(
            btnSalir.getPreferredSize()
        );

        btnIniciar.setAlignmentX(CENTER_ALIGNMENT);

        btnSalir.setAlignmentX(CENTER_ALIGNMENT);


        btnIniciar.setFocusPainted(false);

        btnSalir.setFocusPainted(false);

        panelBotones.add(btnIniciar);

        panelBotones.add(
            Box.createVerticalStrut(15)
        );

        panelBotones.add(btnSalir);

        add(panelTexto, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

       
        btnIniciar.addActionListener(e -> {
            Hotel hotel = new Hotel();
            SistemaHotel.inicializarDatos(hotel);

            new VentanaPrincipal(hotel).setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }
}