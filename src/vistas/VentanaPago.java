package vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class VentanaPago extends JFrame {

    public VentanaPago(String ticket) {

        setTitle("Ticket de Pago");

        setSize(470, 320);

        setResizable(false);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JTextPane area = new JTextPane();

        area.setEditable(false);

        area.setFont(
            new Font(
                "Times New Roman",
                Font.PLAIN,
                16
            )
        );

        area.setText(ticket);

        area.setBackground(getBackground());

        StyledDocument doc = area.getStyledDocument();

        SimpleAttributeSet center = new SimpleAttributeSet();

        StyleConstants.setAlignment(
            center,
            StyleConstants.ALIGN_CENTER
        );

        doc.setParagraphAttributes(
            0,
            doc.getLength(),
            center,
            false
        );

        area.setPreferredSize(
            new Dimension(450, 220)
        );

        JButton btnAceptar =
            new JButton("Aceptar");

        btnAceptar.setPreferredSize(
            new Dimension(120, 35)
        );

        btnAceptar.setFocusPainted(false);

        btnAceptar.addActionListener(
            e -> dispose()
        );

        JPanel panelBoton =
            new JPanel(
                new FlowLayout(
                    FlowLayout.CENTER
                )
            );

        panelBoton.add(btnAceptar);
        
        add(area, BorderLayout.CENTER);

        add(panelBoton, BorderLayout.SOUTH);
    }
}