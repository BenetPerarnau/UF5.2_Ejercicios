package Ex3;

public class Temperature {

	private float gradosC;
	private float gradosK;
	
	
	public Temperature(float grados, char tipo)throws IllegalTemperatureException{
		if(tipo=='c'){
			if(grados>50 || grados<-50){
				throw new IllegalTemperatureException("Valor fuera de rango no se aceptan valores inferiores "
						+ "a los -50º ni superiores a 50º");
			}else{
				this.gradosC=grados;
			}
		}else if(tipo=='k'){
			System.out.println("Grados => "+grados);
			if(grados<223.15 || grados>323.15){
				System.out.println("Grados => "+grados);
				throw new IllegalTemperatureException("Valor fuera de rango no se aceptan valores "
						+ "inferiores a los 223.15ºK ni superiores a 323.15ºK");
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
	
	protected float getGradosC() {
		return gradosC;
	}
	
	protected void setGradosC(float gradosC) {
		
		
		this.gradosC = gradosC;
	}
	
	protected float getGradosK() {
		return gradosK;
	}
	
	protected void setGradosK(float gradosK) {
		this.gradosK = gradosK;
	}
	
	
	
	
}
