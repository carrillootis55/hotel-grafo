package hotel;

/**
*Clase VistaAlMar
*Hereda de Habitacion
*Vista al Mar, se encuentra en el cuarto piso del hotel
*/

public class VistaAlMar extends Habitacion {
	/**
    *Constructor de habitacion Vista al Mar
    * 
    *@param clave        				Clave de la habitacion
    *@param ocupacionMaxima          	Ocupacion maxima de la habitacion
    *@param precioPorNoche      		Precio por noche
    */	
	public VistaAlMar(String clave, int ocupacionMaxima) {
		super(clave, ocupacionMaxima, 17000); 
		// TODO Auto-generated constructor stub
	} 
	
	
	/**
	* Metodo que calcula el costo total de la estadia, se considera:
	*-Precio por noche
	*-Descuento por tipo de membresia
	*-Servicios adicionales
	*@param huesped Huesped para el que se calcula el costo
	*@return costo final total
	*/
	@Override
	public double calcularCosto(Huesped huesped) {
		// TODO Auto-generated method stub
		if(huespedTitular == null) {
			return 0;
		}
		/*El costo base es igual al precio por cada noche multiplicado por la cantidad de noches
		 *descuento aplicado por la membresía del huesped
		 *Costo de servicios contratados
		 *Costo final*/
		
		double costoBase = precioPorNoche * cantidadNoches;
		double descuento = costoBase * huespedTitular.obtenerDescuento();
		double costoServicios = huespedTitular.totalServicios();
		
        return costoBase - descuento + costoServicios;
	}
	
	
	@Override
	public String getTipo() {
	    return "P4";
	}


}

