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

public class VentanaInicio extends JFrame {

    public VentanaInicio() {

        setTitle("HOTEL");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //TEXTO
        JLabel titulo = new JLabel("HOTEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 26));

        JLabel mensaje = new JLabel("Bienvenido Administrador", SwingConstants.CENTER);

        JPanel panelTexto = new JPanel(new GridLayout(2,1));
        panelTexto.add(titulo);
        panelTexto.add(mensaje);

        //BOTONES
        JButton btnIniciar = new JButton("Iniciar");
        JButton btnSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel(new GridLayout(2,1,10,10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20,80,20,80));

        panelBotones.add(btnIniciar);
        panelBotones.add(btnSalir);

        add(panelTexto, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        //EVENTOS
        btnIniciar.addActionListener(e -> {
            new VentanaMenu().setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }
}