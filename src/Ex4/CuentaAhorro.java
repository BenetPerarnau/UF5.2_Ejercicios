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
	
	public void ingresar(float efectivo) {
		this.saldo+=efectivo;
		
	}

	
	public void retirar(float cantidad) {
			saldo-=cantidad;		
	}

	
	public float consultar() {
		return this.saldo;
	}

}
