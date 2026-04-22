package vistas;

import hotel.Habitacion;
import hotel.Hotel;
import hotel.Huesped;
import hotel.Servicio;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.QuadCurve2D;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelArbol extends JPanel {

    private Hotel hotel;
    private int pisoSeleccionado = -1;
    private JTextArea infoArea;
    private boolean modoDesocupar;

    private Rectangle[][] posiciones;

    public PanelArbol(Hotel hotel, JTextArea infoArea, boolean modoDesocupar) {
        this.hotel = hotel;
        this.infoArea = infoArea;
        this.modoDesocupar = modoDesocupar;

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
                    
                    // =========================
                    // MODO DESOCUPAR
                    // =========================
                    if (modoDesocupar && h.isOcupada()) {

                        int opcion = javax.swing.JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas hacer check-out de " + h.getClave() + "?",
                            "Confirmar",
                            javax.swing.JOptionPane.YES_NO_OPTION
                        );

                        if (opcion == javax.swing.JOptionPane.YES_OPTION) {

                            hotel.checkOut(h);

                            javax.swing.JOptionPane.showMessageDialog(
                                this,
                                "Pago realizado correctamente"
                            );

                            repaint(); //ACTUALIZA EL ÁRBOL
                        }

                        return;
                    }

                    // =========================
                    // HABITACIÓN OCUPADA
                    // =========================
                    if (h.isOcupada() && h.getHuespedTitular() != null) {

                        Huesped huesped = h.getHuespedTitular();

                        String serviciosTexto = "";

                        Servicio[] servicios = huesped.getServicios();

                        for (int k = 0; k < huesped.getCantidadServicios(); k++) {
                            Servicio s = servicios[k];
                            if (s != null) {
                                serviciosTexto += "- " + s.getNombre() + 
                                                  " ($" + s.getCosto() + ")\n";
                            }
                        }

                        if (serviciosTexto.isEmpty()) {
                            serviciosTexto = "Sin servicios contratados";
                        }

                        infoArea.setText(
                            "=== HABITACIÓN OCUPADA ===\n" +
                            h.getClave() + "\n\n" +

                            "=== HUÉSPED ===\n" +
                            huesped.toString() + "\n\n" +

                            "=== SERVICIOS ===\n" +
                            serviciosTexto
                        );

                    // =========================
                    // HABITACIÓN RESERVADA
                    // =========================
                    } else if (h.isReservada() && h.getHuespedTitular() != null) {

                        infoArea.setText(
                            "=== HABITACIÓN RESERVADA ===\n" +
                            h.getClave() + "\n\n" +
                            "Reservado por:\n" +
                            h.getHuespedTitular().getNombre()
                        );

                    // =========================
                    // HABITACIÓN LIBRE
                    // =========================
                    } else {
                        infoArea.setText("Habitación libre: " + h.getClave());
                    }
                }
            }
        }
    }
    /*
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
                    } else if (h.isReservada() && h.getHuespedTitular() != null) {
                    	infoArea.setText(
                    	        "=== HABITACIÓN RESERVADA ===\n" +
                    	        h.getClave() + "\n\n" +
                    	        "Reservado por:\n" +
                    	        h.getHuespedTitular().getNombre()
                    	    );
                    } else {
                        infoArea.setText("Habitación libre: " + h.getClave());
                    }
                }
            }
        }
    }*/

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Habitacion[][] habs = hotel.getHabitaciones();

        int radio = 25;

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

                pisoX = espacio * (i + 1) - 60;
                pisoY = pisoYBase;
            } else {

                pisoX = getWidth() / 2 - 60;
                pisoY = getHeight() / 3;
            }

            g2.setColor(new Color(70,130,180));
            g2.fillOval(pisoX, pisoY, 120, 80);

            g2.setColor(Color.BLACK);
            g2.drawOval(pisoX, pisoY, 120, 80);
            g2.drawString("PISO " + (i+1), pisoX + 30, pisoY + 45);

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
                
                //FILTRO PARA MODO DESOCUPAR
                if (modoDesocupar && !h.isOcupada()) {
                    posiciones[i][j] = null;
                    continue;
                }

                posiciones[i][j] = new Rectangle(x, y, radio*2, radio*2);


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