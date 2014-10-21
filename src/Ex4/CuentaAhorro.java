package Ex4;

public class CuentaAhorro implements OperacionesCuentas {

	private float saldo;
	private float penalizacion;
	
	public CuentaAhorro(float saldo, float penalizacion){
		this.saldo=saldo;
		this.penalizacion=penalizacion;
	}
	
	public float get_penalizacion(){
		
		return this.penalizacion;
	}
	
	public boolean ingresar(float efectivo) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean retirar(float cantidad) {
		boolean aux=false;
		
		if(saldo<cantidad){
			aux=false;
		}else{
			saldo-=cantidad;
		}
		
		return aux;
	}

	
	public float consultar() {
		return this.saldo;
	}

}
