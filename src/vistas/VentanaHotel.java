package vistas;

import hotel.Hotel;
import hotel.SistemaHotel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
* Ventana encargada de mostrar el arbol del hotel
* Esta ventana permite:
* -Visualizar todas las habitaciones
* -Visualizar un piso especifico
* -Mostrar informacion de habitaciones
* -Desocupar habitaciones
* 
*/

public class VentanaHotel extends JFrame {

    private Hotel hotel;
    private PanelArbol panel;
    private JTextArea infoArea;
    private boolean modoDesocupar;
    
    /**
     * Constructor de la ventana del hotel
     * 
     * @param hotel hotel a visualizar
     * @param pisoSeleccionado piso que se mostrara (-1 = todos los pisos)
     * @param modoDesocupar indica si se usara en modo check-out
     */

    public VentanaHotel(Hotel hotel, int pisoSeleccionado, boolean modoDesocupar) {
    	this.modoDesocupar = modoDesocupar;
    	this.hotel = hotel;

        setTitle(modoDesocupar ? "Desocupar Habitaciones" : "Visualizar Hotel");
        setSize(900, 600);
        //Evitar cambiar tamaño de ventana
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        infoArea = new JTextArea(12, 40);
        
        infoArea.setMargin(new Insets(10, 20, 10, 20));
        infoArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        
        infoArea.setMargin(new Insets(10, 20, 10, 20));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setEditable(false);
        
        JScrollPane scrollInfo = new JScrollPane(infoArea);
        
        //Panel del arbol
        panel = new PanelArbol(hotel, infoArea, modoDesocupar);
        panel.setPiso(pisoSeleccionado); 


        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> regresarMenu());

        JPanel superior = new JPanel();
        superior.add(btnRegresar);

        add(superior, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER); 
        add(scrollInfo, BorderLayout.SOUTH);
        
        //Siempre abrir maximizado
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    /**
    *Regresa al menu correspondiente dependiendo del modo actual
    */
    private void regresarMenu() {
        //Si viene del modo desocupar
        if (modoDesocupar) {
            new VentanaPrincipal(hotel).setVisible(true);

        } else {
            //Si viene de visualizar pisos
            new VentanaMenu(hotel).setVisible(true);
        }

        dispose();
    }
}