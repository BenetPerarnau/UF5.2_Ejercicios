package Ex4;

public class CuentaCorriente implements OperacionesCuentas {
	
	private float saldo;

	
	public CuentaCorriente(float saldo){
		this.saldo=saldo;
	}
	
	public boolean ingresar(float efectivo) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retirar(float cantidad) {
		
		saldo-=cantidad;
		
		return false;
	}

	public float consultar() {
		return this.saldo;
	}
	
}
