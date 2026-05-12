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
  //______________________________________________________________________________________
    public void setPiso(int piso) {
        this.pisoSeleccionado = piso;
        repaint();
    }
  //______________________________________________________________________________________
    private void detectarClick(int x, int y) {
        Habitacion[][] habs = hotel.getHabitaciones();

        for (int i = 0; i < habs.length; i++) {
            for (int j = 0; j < habs[i].length; j++) {

                if (posiciones[i][j] != null && posiciones[i][j].contains(x, y)) {

                    Habitacion h = habs[i][j];
                    double precio = h.getPrecioPorNoche();
                    
                    //DESOCUPAR
                    if (modoDesocupar && h.isOcupada()) {

                        int opcion = javax.swing.JOptionPane.showConfirmDialog(
                            this,
                            "¿Deseas hacer check-out de " + h.getClave() + "?",
                            "Confirmar",
                            javax.swing.JOptionPane.YES_NO_OPTION
                        );

                        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                        	
                        	String ticket = hotel.checkOut(h);

                        	new VentanaPago(ticket).setVisible(true);

                        	repaint();
                            
                        }

                        return;
                    }

                    //Habitacion ocupada
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
                            		
                            "Precio por noche: $" + precio + "\n\n" +
                            
							"Cantidad de noches: " +
							h.getCantidadNoches() + "\n\n" +

                            "=== HUÉSPED ===\n" +
                            huesped.toString() + "\n\n" +

                            "=== SERVICIOS ===\n" +
                            serviciosTexto
                        );

                    //HABITACION RESERVADA
                    } else if (h.isReservada() && h.getHuespedTitular() != null) {

                        infoArea.setText(
                            "=== HABITACIÓN RESERVADA ===\n" +
                            h.getClave() + "\n\n" +
                            		
							"Precio por noche: $" + precio + "\n\n" +
							
                            "Reservado por:\n" +
                            h.getHuespedTitular().getNombre()
                        );


                    //HABITACION LIBRE
       
                    } else {
                        infoArea.setText("=== HABITACIÓN LIBRE ===\n" +
                        	    h.getClave() + "\n" +
                        	    "Precio por noche: $" + precio);
                    }
                }
            }
        }
    }
  //______________________________________________________________________________________
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        Habitacion[][] habs = hotel.getHabitaciones();

        int radio = 25;

        //=====================================================================
        // RECEPCION
        int recepcionX = getWidth() / 2 - 60;
        int recepcionY = 20;

        g2.setColor(new Color(70,130,180));

        g2.fillOval(recepcionX, recepcionY, 120, 50);

        g2.setColor(Color.BLACK);

        g2.drawOval(recepcionX, recepcionY, 120, 50);

        g2.drawString(
            "RECEPCION",
            recepcionX + 20,
            recepcionY + 30
        );

        int totalPisos = habs.length;

        int espacio = getWidth() / (totalPisos + 1);

        //=====================================================================
        //RECORRER PISOS
        for (int i = 0; i < totalPisos; i++) {

            if (pisoSeleccionado != -1 &&
                i != pisoSeleccionado) {

                continue;
            }

            int pisoX;
            int pisoY;

            //=============================================================
            //VISTA COMPLETA
            if (pisoSeleccionado == -1) {

                pisoX = espacio * (i + 1) - 60;
                pisoY = 150;

            //=============================================================
            // VISTA INDIVIDUAL
            } else {

                pisoX = getWidth() / 2 - 50;
                pisoY = 120;
            }

            //=============================================================
            // DIBUJAR PISO
            g2.setColor(new Color(70,130,180));

            g2.fillOval(pisoX, pisoY, 100, 70);

            g2.setColor(Color.BLACK);

            g2.drawOval(pisoX, pisoY, 100, 70);

            g2.drawString(
                "PISO " + (i+1),
                pisoX + 22,
                pisoY + 40
            );

            //Lineas curvas
            QuadCurve2D curvaRP = new QuadCurve2D.Float();

            int x1 = recepcionX + 60;
            int y1 = recepcionY + 50;

            int x2 = pisoX + 50;
            int y2 = pisoY;

            int ctrlX = (x1 + x2) / 2;
            int ctrlY = y1 + 50;

            curvaRP.setCurve(
                x1,
                y1,
                ctrlX,
                ctrlY,
                x2,
                y2
            );

            g2.draw(curvaRP);

            //=============================================================
            //HABITACIONES
            int totalHab = habs[i].length;

            int separacion = 70;

            int anchoTotal = totalHab * separacion;

            int inicioX;

            // VISTA COMPLETA
            if (pisoSeleccionado == -1) {

                int espacioPorPiso =
                    getWidth() / totalPisos;

                int inicioBloque =
                    espacioPorPiso * i;

                int finBloque =
                    espacioPorPiso * (i + 1);

                int centroBloque =
                    (inicioBloque + finBloque) / 2;

                inicioX =
                    centroBloque - (anchoTotal / 2);

            //=============================================================
            // VISTA INDIVIDUAL
            } else {

                inicioX =
                    (getWidth() / 2) -
                    (anchoTotal / 2);
            }

            int habY;

            //=============================================================
            // POSICION HABITACIONES
            if (pisoSeleccionado == -1) {

                habY = pisoY + 160;

            } else {

                habY = pisoY + 130;
            }

            //=============================================================
            //DIBUJAR HABITACIONES
            for (int j = 0; j < totalHab; j++) {

                int x = inicioX + j * separacion;
                int y = habY;

                Habitacion h = habs[i][j];

                //=========================================================
                //FILTRO MODO DESOCUPAR
                if (modoDesocupar &&
                    !h.isOcupada()) {

                    posiciones[i][j] = null;
                    continue;
                }

                posiciones[i][j] =
                    new Rectangle(
                        x,
                        y,
                        radio * 2,
                        radio * 2
                    );

                //=========================================================
                //COLOR HABITACION
                if (h.isOcupada()) {

                    g2.setColor(Color.RED);

                } else if (h.isReservada()) {

                    g2.setColor(Color.YELLOW);

                } else {

                    g2.setColor(Color.GREEN);
                }

                g2.fillOval(
                    x,
                    y,
                    radio * 2,
                    radio * 2
                );

                g2.setColor(Color.BLACK);

                g2.drawOval(
                    x,
                    y,
                    radio * 2,
                    radio * 2
                );

                g2.drawString(
                    h.getClave(),
                    x + 5,
                    y + 30
                );

                //=========================================================
                //Lineas curvas
                QuadCurve2D curva =
                    new QuadCurve2D.Float();

                int origenX = pisoX + 50;
                int origenY = pisoY + 70;

                int destinoX = x + radio;
                int destinoY = y;

                int offset =
                    (j - totalHab / 2) * 25;

                int controlX =
                    (origenX + destinoX) / 2 + offset;

                int controlY = origenY + 70;

                curva.setCurve(
                    origenX,
                    origenY,
                    controlX,
                    controlY,
                    destinoX,
                    destinoY
                );

                g2.draw(curva);
            }
        }
        
        //=========================================================
        //SIMBOLOGÍA

        int leyendaX = 20;
        int leyendaY = 20;

        g2.setColor(Color.BLACK);
        g2.drawString("SIMBOLOGÍA:", leyendaX, leyendaY);

        //ROJO 
        g2.setColor(Color.RED);
        g2.fillRect(leyendaX, leyendaY + 10, 20, 20);

        g2.setColor(Color.BLACK);
        g2.drawRect(leyendaX, leyendaY + 10, 20, 20);

        g2.drawString(
            "Ocupada",
            leyendaX + 30,
            leyendaY + 25
        );

        //AMARILLO
        g2.setColor(Color.YELLOW);
        g2.fillRect(leyendaX, leyendaY + 40, 20, 20);

        g2.setColor(Color.BLACK);
        g2.drawRect(leyendaX, leyendaY + 40, 20, 20);

        g2.drawString(
            "Reservada",
            leyendaX + 30,
            leyendaY + 55
        );

        //VERDE
        g2.setColor(Color.GREEN);
        g2.fillRect(leyendaX, leyendaY + 70, 20, 20);

        g2.setColor(Color.BLACK);
        g2.drawRect(leyendaX, leyendaY + 70, 20, 20);

        g2.drawString(
            "Libre",
            leyendaX + 30,
            leyendaY + 85
        );

    }
}