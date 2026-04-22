package hotel;

public class SistemaHotel {
	public static void main(String[] args) {

        Hotel hotel = new Hotel();

        inicializarDatos(hotel);
    }

    public static void inicializarDatos(Hotel hotel) {

        //Crear huespedes
        Huesped h1 = new Huesped("Juan Perez", 30, "123", "juan@mail.com", 2, "VIP");
        Huesped h2 = new Huesped("Ana Lopez", 28, "456", "ana@mail.com", 1, "Cliente frecuente");
        Huesped h3 = new Huesped("Carlos Ruiz", 40, "789", "carlos@mail.com", 3, "Ninguna");
        
        Huesped h4 = new Huesped("Luis Torres", 35, "111", "luis@mail.com", 2, "Ninguna");
        Huesped h5 = new Huesped("Maria Gomez", 27, "222", "maria@mail.com", 1, "VIP");
        Huesped h6 = new Huesped("Pedro Sanchez", 50, "333", "pedro@mail.com", 2, "Cliente frecuente");

        //Servicios
        h1.agregarServicio(new Servicio("Spa", hotel.getPrecioSpa()));
        h2.agregarServicio(new Servicio("Restaurante", hotel.getPrecioRestaurante()));
        h3.agregarServicio(new Servicio("Estacionamiento", hotel.getPrecioEstacionamiento()));

        //Registrar huéspedes
        hotel.agregarHuespedManual(h1);
        hotel.agregarHuespedManual(h2);
        hotel.agregarHuespedManual(h3);
        
        hotel.agregarHuespedManual(h4);
        hotel.agregarHuespedManual(h5);
        hotel.agregarHuespedManual(h6);

        //Reservar habitaciones
        Habitacion hab1 = hotel.buscarHabitacionPorClave("P1-1");
        Habitacion hab2 = hotel.buscarHabitacionPorClave("P1-2");
        Habitacion hab3 = hotel.buscarHabitacionPorClave("P2-3");
        
        Habitacion hab4 = hotel.buscarHabitacionPorClave("P3-1");
        Habitacion hab5 = hotel.buscarHabitacionPorClave("P3-2");
        Habitacion hab6 = hotel.buscarHabitacionPorClave("P4-4");

        hab1.reservar(h1, 3, 2);
        hab2.reservar(h2, 2, 1);
        hab3.reservar(h3, 4, 3);
        
        //SOLO RESERVADAS
        hab4.reservar(h4, 2, 2);
        hab5.reservar(h5, 1, 1);
        hab6.reservar(h6, 2, 3);

        //Check-in
        hab1.ocupar();
        hab2.ocupar();
        hab3.ocupar();
    }

}
