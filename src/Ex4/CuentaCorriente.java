package Ex4;

public class CuentaCorriente implements OperacionesCuentas {
	
	private float saldo;

	
	public CuentaCorriente(float saldo){
		this.saldo=saldo;
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
