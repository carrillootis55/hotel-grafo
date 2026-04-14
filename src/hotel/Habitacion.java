package hotel;
/**
 * Clase abstracta Habitacion
 * Se representará cualquier tipo de habitación en el hotel
 */
public abstract class Habitacion {

    //Atributos
    protected String clave; //clave P1,P2,P3,P4, significa el piso de la habitación        
    protected int ocupacionMaxima;
    protected double precioPorNoche;
    protected boolean reservada;
    protected boolean ocupada;
    protected int cantidadNoches;
    protected Huesped huespedTitular;

//___________________________________________________________________________________________________________________________________________________________
    /**
     * Constructor que inicializa los datos de la habitación
     * @param clave               Clave de la habitación
     * @param ocupacionMaxima     Ocupación máxima de la habitación
     * @param precioPorNoche      Precio por noche
     */ 
    public Habitacion(String clave, int ocupacionMaxima, double precioPorNoche) { 
        this.clave = clave;
        this.ocupacionMaxima = ocupacionMaxima;
        this.precioPorNoche = precioPorNoche;
        this.reservada = false;
        this.ocupada = false;
    }

 //___________________________________________________________________________________________________________________________________________________________

    //Getters y Setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getOcupacionMaxima() {
        return ocupacionMaxima;
    }

    public void setOcupacionMaxima(int ocupacionMaxima) {
        this.ocupacionMaxima = ocupacionMaxima;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public boolean isReservada() {
        return reservada;
    }

    public void setReservada(boolean reservada) {
        this.reservada = reservada;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public int getCantidadNoches() {
        return cantidadNoches;
    }

    public void setCantidadNoches(int cantidadNoches) {
        this.cantidadNoches = cantidadNoches;
    }

    public Huesped getHuespedTitular() {
        return huespedTitular;
    }

    public void setHuespedTitular(Huesped huespedTitular) {
        this.huespedTitular = huespedTitular;
    }

//___________________________________________________________________________________________________________________________________________________________
    /**
     * Aparta la habitación
     * No pone al huésped todavía adentro, solo valida la capacidad y guarda las noches
     * 
     * @param huespedTitular      Huésped que hace la reserva
     * @param totalHuespedes      Total de huéspedes (titular + acompañantes)
     * @param noches              Cantidad de noches a reservar
     * @return true si la reserva fue exitosa, false en caso contrario
     */
    public boolean reservar(Huesped huespedTitular, int totalHuespedes, int noches) {

        // Validaciones
    	if(huespedTitular==null) {
    		return false;
    	}
    	
        if (reservada || totalHuespedes > ocupacionMaxima || noches <= 0) {
            return false;
        }

        // Si cumple con las validaciones se reserva
        this.reservada = true;
        this.huespedTitular = huespedTitular;
        this.cantidadNoches = noches;

        return true;
    }

//___________________________________________________________________________________________________________________________________________________________
    /**
     * Método que marca la habitación como ocupada si estaba reservada
     * No recibe parámetros, ya que la reserva establece al titular
     * 
     * @return true si se pudo ocupar, false si ya estaba ocupada o no esta reservada
     */
    public boolean ocupar() {
        if (!reservada || ocupada) {
            return false;
        }
        this.ocupada = true;
        return true;
    }

//___________________________________________________________________________________________________________________________________________________________
    /**
     * Método que libera la habitación y borra el titular
     * @return true si se desocupó, false si ya estaba libre
     */
    public boolean desocupar() {
        if (!ocupada) {
            return false;
        }

        this.ocupada = false;
        this.reservada = false;
        this.huespedTitular = null;
        this.cantidadNoches = 0;

        return true;
    }

//___________________________________________________________________________________________________________________________________________________________
    /**
     * Obtiene el titular de la habitación
     * @return Huesped titular
     */
    public Huesped getTitular() {
        return huespedTitular;
    }

//___________________________________________________________________________________________________________________________________________________________      
    /**
     * Cálculo base del costo sin descuentos ni servicios
     * Las subclases pueden sobrescribir para añadir cargos específicos
     * 
     * @return costo base
     */
    public double calcularCostoBase() {
        return precioPorNoche * cantidadNoches;
    }

    /**
     * Método polimórfico que saca el costo final de la habitación.
     * Considera descuento por membresía del huésped.
     * Las subclases pueden agregar cargos adicionales.
     * 
     * @param huesped  huésped al que se le aplicará el descuento
     * @return costo final con descuento
     */
    public double calcularCosto(Huesped huesped) {
    	if(huesped==null) {
    		return 0.0;
    	}
    
        double base = calcularCostoBase();//Precio de la habitación por la estancia completa
        double costoServicios = huesped.totalServicios();//Total de servicios contratados
        
        double total = base + costoServicios;//total sin el descuento aplicado
        double descuento = huesped.obtenerDescuento() * total; //Descuento dependiendo la membresía del huésped

        return total - descuento;
        
    }

    //___________________________________________________________________________________________________________________________________________________________
    /**
     * Ingreso total que se genera en cada habitación (sin servicios)
     * Si la habitación estuvo ocupada, devuelve el costo calculado con el huésped actual
     * 
     * @return ingreso generado o 0.0 si no hubo huésped
     */
    public double getIngresoTotal() {
        if (huespedTitular != null) {
            return calcularCosto(huespedTitular);
        }
        return 0.0;
    }

    //___________________________________________________________________________________________________________________________________________________________
    /**
     * Método que muestra información de la habitación
     */
    @Override
    public String toString() { 
        return "|" + "Clave de la Habitación: " + clave +"|" + " Ocupación Máxima: " + ocupacionMaxima +
               "\nPrecio por noche $" + precioPorNoche +"| Estado de reserva = " + (reservada ? "Reservada" : "Disponible")  +
               "| Estado de ocupada = " + (ocupada ? "Ocupada" : "Libre") +"| Cantidad de noches = " + cantidadNoches + 
               "| Huesped titular = " +  (huespedTitular != null ? huespedTitular.getNombre() : "Ninguno");
    }

    //Método para definir el tipo de habitación
    /**
     * Método que muestra el tipo de la habitacion en las subclases de habitacion
     * 
     * @return string que me indicara el tipo de habitacion
     */
    public abstract String getTipo();

}
