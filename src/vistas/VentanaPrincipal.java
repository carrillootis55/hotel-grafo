package vistas;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hotel.Hotel;

/**
 * Ventana principal del sistema 
* 
* Desde esta ventana el administrador puede:
* -Visualizar el hotel
* -Desocupar habitaciones
* -Regresar a la ventana inicial
* 
*/
public class VentanaPrincipal extends JFrame {

    private Hotel hotel;
    
    /**
    * Constructor de la ventana principal
    * 
    * @param hotel hotel que sera administrado
    */
    public VentanaPrincipal(Hotel hotel) {

        this.hotel = hotel;

        setTitle("Menu Hotel");
        setSize(380,250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("MENU HOTEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

  
        JButton btnVer = new JButton("Ver Hotel");
        JButton btnDesocupar = new JButton("Desocupar");
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
                25,
                90,
                25,
                90
            )
        );

        //TAMAÑO BOTONES

        btnVer.setPreferredSize(
            new java.awt.Dimension(140,35)
        );

        btnDesocupar.setPreferredSize(
            new java.awt.Dimension(140,35)
        );

        btnSalir.setPreferredSize(
            new java.awt.Dimension(140,35)
        );


        btnVer.setMaximumSize(
            btnVer.getPreferredSize()
        );

        btnDesocupar.setMaximumSize(
            btnDesocupar.getPreferredSize()
        );

        btnSalir.setMaximumSize(
            btnSalir.getPreferredSize()
        );


        btnVer.setAlignmentX(CENTER_ALIGNMENT);

        btnDesocupar.setAlignmentX(CENTER_ALIGNMENT);

        btnSalir.setAlignmentX(CENTER_ALIGNMENT);

        btnVer.setFocusPainted(false);

        btnDesocupar.setFocusPainted(false);

        btnSalir.setFocusPainted(false);

        panelBotones.add(btnVer);

        panelBotones.add(
            Box.createVerticalStrut(15)
        );

        panelBotones.add(btnDesocupar);

        panelBotones.add(
            Box.createVerticalStrut(15)
        );

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