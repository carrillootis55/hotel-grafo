package vistas;

import hotel.Habitacion;
import hotel.Hotel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.QuadCurve2D;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelGrafo extends JPanel {

    private Hotel hotel;
    private int pisoSeleccionado = -1;
    private JTextArea infoArea;

    private Rectangle[][] posiciones;

    public PanelGrafo(Hotel hotel, JTextArea infoArea) {
        this.hotel = hotel;
        this.infoArea = infoArea;

        posiciones = new Rectangle[4][5];

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                detectarClick(e.getX(), e.getY());
            }
        });
    }

    public void setPiso(int piso) {
        this.pisoSeleccionado = piso;
        repaint();
    }

    private void detectarClick(int x, int y) {
        Habitacion[][] habs = hotel.getHabitaciones();

        for (int i = 0; i < habs.length; i++) {
            for (int j = 0; j < habs[i].length; j++) {

                if (posiciones[i][j] != null && posiciones[i][j].contains(x, y)) {

                    Habitacion h = habs[i][j];

                    if (h.isOcupada() && h.getHuespedTitular() != null) {
                        infoArea.setText(
                            "=== HABITACIÓN ===\n" +
                            h.getClave() + "\n\n" +
                            "=== HUÉSPED ===\n" +
                            h.getHuespedTitular().toString()
                        );
                    } else if (h.isReservada()) {
                        infoArea.setText("Habitación reservada: " + h.getClave());
                    } else {
                        infoArea.setText("Habitación libre: " + h.getClave());
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Habitacion[][] habs = hotel.getHabitaciones();

        int radio = 25;

        // ======================
        // RECEPCIÓN
        // ======================
        int recepcionX = getWidth() / 2 - 60;
        int recepcionY = 60;

        g2.setColor(new Color(70,130,180));
        g2.fillOval(recepcionX, recepcionY, 120, 50);

        g2.setColor(Color.BLACK);
        g2.drawOval(recepcionX, recepcionY, 120, 50);
        g2.drawString("RECEPCION", recepcionX + 20, recepcionY + 30);

        int pisoYBase = 150;

        int totalPisos = habs.length;
        int espacio = getWidth() / (totalPisos + 1);

        for (int i = 0; i < totalPisos; i++) {

            if (pisoSeleccionado != -1 && i != pisoSeleccionado)
                continue;

            int pisoX;
            int pisoY;

            if (pisoSeleccionado == -1) {
                // TODOS → en línea horizontal
                pisoX = espacio * (i + 1) - 60;
                pisoY = pisoYBase;
            } else {
                // UNO → centrado
                pisoX = getWidth() / 2 - 60;
                pisoY = getHeight() / 3;
            }

            // ======================
            // NODO PISO
            // ======================
            g2.setColor(new Color(70,130,180));
            g2.fillOval(pisoX, pisoY, 120, 80);

            g2.setColor(Color.BLACK);
            g2.drawOval(pisoX, pisoY, 120, 80);
            g2.drawString("PISO " + (i+1), pisoX + 30, pisoY + 45);

            // ======================
            // CONEXIÓN RECEPCIÓN → PISO
            // ======================
            QuadCurve2D curvaRP = new QuadCurve2D.Float();

            int x1 = recepcionX + 60;
            int y1 = recepcionY + 50;

            int x2 = pisoX + 60;
            int y2 = pisoY;

            int ctrlX = (x1 + x2) / 2;
            int ctrlY = y1 + 50;

            curvaRP.setCurve(x1, y1, ctrlX, ctrlY, x2, y2);
            g2.draw(curvaRP);

            // ======================
            // HABITACIONES
            // ======================
            int totalHab = habs[i].length;
            int separacion = 70;

            //int anchoTotal = totalHab * separacion;
            //int inicioX = pisoX + 60 - (anchoTotal / 2);
            
            int espacioPorPiso = getWidth() / totalPisos;

            int inicioBloque = espacioPorPiso * i;
            int finBloque = espacioPorPiso * (i + 1);

            // centro del bloque
            int centroBloque = (inicioBloque + finBloque) / 2;

            // ancho del grupo de habitaciones
            int anchoTotal = totalHab * separacion;

            // inicio real centrado dentro del bloque
            int inicioX = centroBloque - (anchoTotal / 2);

            int habY;

            if (pisoSeleccionado == -1) {
                habY = pisoY + 160;
            } else {
                habY = pisoY + 180;
            }

            for (int j = 0; j < totalHab; j++) {

                int x = inicioX + j * separacion;
                int y = habY;

                Habitacion h = habs[i][j];

                posiciones[i][j] = new Rectangle(x, y, radio*2, radio*2);

                // COLORES
                if (h.isOcupada())
                    g2.setColor(Color.RED);
                else if (h.isReservada())
                    g2.setColor(Color.YELLOW);
                else
                    g2.setColor(Color.GREEN);

                g2.fillOval(x, y, radio*2, radio*2);

                g2.setColor(Color.BLACK);
                g2.drawOval(x, y, radio*2, radio*2);

                g2.drawString(h.getClave(), x+5, y+30);

                // ======================
                // CONEXIÓN PISO → HABITACIÓN
                // ======================
                QuadCurve2D curva = new QuadCurve2D.Float();

                int origenX = pisoX + 60;
                int origenY = pisoY + 80;

                int destinoX = x + radio;
                int destinoY = y;

                int offset = (j - totalHab / 2) * 25;

                int controlX = (origenX + destinoX) / 2 + offset;
                int controlY = origenY + 70;

                curva.setCurve(origenX, origenY, controlX, controlY, destinoX, destinoY);
                g2.draw(curva);
            }
        }

        // ======================
        // AJUSTE DE ALTURA (para que no se corte)
        // ======================
        int altoNecesario = 400;
        if (pisoSeleccionado == -1) {
            altoNecesario = 500;
        } else {
            altoNecesario = getHeight();
        }

        setPreferredSize(new Dimension(getWidth(), altoNecesario));
        revalidate();
    }
}