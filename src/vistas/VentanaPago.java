package vistas;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaPago extends JFrame {

    public VentanaPago(String ticket) {

        setTitle("Ticket de Pago");

        setSize(500, 500);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();

        area.setEditable(false);

        area.setFont(
            new Font(
                "Times New Roman",
                Font.PLAIN,
                16
            )
        );

        area.setText(ticket);

        JScrollPane scroll =
            new JScrollPane(area);

        JButton btnAceptar =
            new JButton("Aceptar");

        btnAceptar.addActionListener(
            e -> dispose()
        );

        add(scroll, BorderLayout.CENTER);

        add(btnAceptar, BorderLayout.SOUTH);
    }
}
