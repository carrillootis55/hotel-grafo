package hotel;
/**
*Clase Servicio
*Permite agregar cargos extra por servicios como SPA, Restaurante o Estacionamiento
*/

public class Servicio {
	//Atributos
	protected String nombre;
	protected double costo;
	
	//Constructores
	/**
    *Constructor que inicializa los datos del servicio
    * 
    *@param nombre        	Nombre del cargo extra
    *@param costo          	Costo del cargo extra
    */
	public Servicio(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }
	
	//Getters
	public String getNombre() {
		return nombre;
	}

	public double getCosto() {
		return costo;
	}

	@Override
	public String toString() {
		return nombre + "(" + costo + ")";
	}
	
	
}

