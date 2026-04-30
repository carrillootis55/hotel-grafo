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
import hotel.SistemaHotel;

public class VentanaInicio extends JFrame {

    public VentanaInicio() {

        setTitle("HOTEL");
        setSize(400,300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        /*addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Usa los botones del sistema para salir"
                );
            }
        });*/

        JLabel titulo = new JLabel("HOTEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 26));

        JLabel mensaje = new JLabel("Bienvenido Administrador", SwingConstants.CENTER);

        JPanel panelTexto = new JPanel(new GridLayout(2,1));
        panelTexto.add(titulo);
        panelTexto.add(mensaje);

        JButton btnIniciar = new JButton("Iniciar");
        JButton btnSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel(new GridLayout(2,1,10,10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20,80,20,80));

        panelBotones.add(btnIniciar);
        panelBotones.add(btnSalir);

        add(panelTexto, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        /*btnIniciar.addActionListener(e -> {
            new VentanaPrincipal().setVisible(true);
            dispose();
        });*/
        
        btnIniciar.addActionListener(e -> {
            Hotel hotel = new Hotel();
            SistemaHotel.inicializarDatos(hotel);

            new VentanaPrincipal(hotel).setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }
}