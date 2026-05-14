package vistas;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hotel.Hotel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Ventana de menu para visualizar habitaciones del hotel
 * 
 * Desde esta ventana se puede:
 * -Ver todo el hotel
 * -Ver un piso especifico
 * -Regresar al menu principal
 * 
 */

public class VentanaMenu extends JFrame {
	
	private Hotel hotel;
	
	/**
    * Constructor de la ventana menu
    * 
    * @param hotel hotel a visualizar
    */
    public VentanaMenu(Hotel hotel) {
    	
    	this.hotel = hotel;
        setTitle("Menu Administrador");
        setSize(380,420);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("HOTEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 18));

        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6,1,10,10));
        
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20,70,20,70));

        JButton btnVerTodo = new JButton("Ver Todo");
        JButton btnP1 = new JButton("Piso 1");
        JButton btnP2 = new JButton("Piso 2");
        JButton btnP3 = new JButton("Piso 3");
        JButton btnP4 = new JButton("Piso 4");
        JButton btnSalir = new JButton("Regresar");

        panelBotones.add(btnVerTodo);
        panelBotones.add(btnP1);
        panelBotones.add(btnP2);
        panelBotones.add(btnP3);
        panelBotones.add(btnP4);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);


        btnVerTodo.addActionListener(e -> abrirArbol(-1));
        btnP1.addActionListener(e -> abrirArbol(0));
        btnP2.addActionListener(e -> abrirArbol(1));
        btnP3.addActionListener(e -> abrirArbol(2));
        btnP4.addActionListener(e -> abrirArbol(3));

        btnSalir.addActionListener(e -> {
            new VentanaPrincipal(hotel).setVisible(true);
            dispose();
        });
    }
    
    /**
    * Abre la ventana de visualizacion del hotel
    * 
    * @param piso piso a visualizar
    */
    private void abrirArbol(int piso){
        new VentanaHotel(hotel, piso, false).setVisible(true);
        dispose();
    }

}