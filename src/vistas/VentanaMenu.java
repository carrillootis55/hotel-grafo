package vistas;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class VentanaMenu extends JFrame {

    public VentanaMenu() {

        setTitle("Menu Administrador");
        setSize(300,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("MENU HOTEL", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 18));

        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6,1,10,10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));

        JButton btnVerTodo = new JButton("Ver Todo");
        JButton btnP1 = new JButton("Piso 1");
        JButton btnP2 = new JButton("Piso 2");
        JButton btnP3 = new JButton("Piso 3");
        JButton btnP4 = new JButton("Piso 4");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnVerTodo);
        panelBotones.add(btnP1);
        panelBotones.add(btnP2);
        panelBotones.add(btnP3);
        panelBotones.add(btnP4);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // EVENTOS
        btnVerTodo.addActionListener(e -> abrirGrafo(-1));
        btnP1.addActionListener(e -> abrirGrafo(0));
        btnP2.addActionListener(e -> abrirGrafo(1));
        btnP3.addActionListener(e -> abrirGrafo(2));
        btnP4.addActionListener(e -> abrirGrafo(3));

        //Salir al menu de inicio
        btnSalir.addActionListener(e -> {
            new VentanaInicio().setVisible(true);
            dispose();
        });
    }

    private void abrirGrafo(int piso){
        VentanaHotel v = new VentanaHotel(piso);
        v.setVisible(true);
        dispose();
    }
}