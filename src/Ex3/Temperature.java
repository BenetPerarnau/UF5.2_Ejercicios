package Ex3;

public class Temperature {

	private float gradosC;
	private float gradosK;
	private final float varemo=(float)273.15;
	
	public Temperature(){
		
	}
	public Temperature(float grados, char tipo)throws IllegalTemperatureException{
		if(tipo=='c'){
			if(grados>273.15 || grados<-273.15){
				throw new IllegalTemperatureException("Valor fuera de rango no se aceptan valores inferiores "
						+ "a los -273.15º ni superiores a 273.15º");
			}else{
				this.gradosC=grados;
			}
		}else if(tipo=='k'){
			if(grados<0 || grados>546.30){
				throw new IllegalTemperatureException("Valor fuera de rango no se aceptan valores "
						+ "inferiores a los 0ºK=-273.15ºC ni superiores a 546.30ºK=273.15ºC");
			}else{
				this.gradosK=grados;
			}
		}else{
			System.out.println("Valor no contemplado, c=> Celius k=> Kelvin");
		}
	}
	
	protected float convert(char tipo, float grados){
		float aux=0;
		switch(tipo){	
		case 'c':
			aux=(float)(this.gradosK-273.15);
			break;
		case 'k':
			aux=(float)(this.gradosC+273.15);
			break;
			default:
				System.out.println("Error el tipo es c o k");
				break;
		}		
		return aux;
	}
	//se que el enunciado los pide pero en mi metodo para solucionar el problema no me hace falta utilizarlos
	//si el programa continuara si que podrian llegar a ser utiles
	
	
	
	protected float getGradosC(float gradosK) throws IllegalTemperatureException{
		
		if(gradosK<0){
			throw new IllegalTemperatureException("Los grados k no pueden ser inferiores a 0");
		}else{
			return gradosK-varemo;
		}
		
	}
	
	protected float getGradosK() {
		return gradosK;
	}
	
	
	protected void setGradosC(float gradosC) {
		this.gradosC = gradosC;
	}
	protected void setGradosK(float gradosK) {
		this.gradosK = gradosK;
	}
	
	

	
	
	
	
}
