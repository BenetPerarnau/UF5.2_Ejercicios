package Ex4_R;

public class CuentaCorriente implements OperacionesCuentas {
	
	private float saldo;

	
	
	public CuentaCorriente(float saldo) throws SaldoInicialInsuficienteException{
		if(saldo<50){
			throw new SaldoInicialInsuficienteException("El saldo para abrir una nueva cuenta son 50 €");
		}else{
			this.saldo=saldo;
		}
		
	}
	public CuentaCorriente(float saldo,char f){
		//este constructor es para leer el fichero pq? pq si dejamos a 0 o negativo un cliente a la que vuelva a leer el fichero petaria usando el otro constructor
			this.saldo=saldo;
		
		
	}
	
	public boolean ingresar(float efectivo) {
		this.saldo+=efectivo;
		return true;
		
	}

	public boolean retirar(float cantidad) {	
		if(cantidad<=saldo){
		saldo-=cantidad;
		return true;
		}
		else{
			return false;
		}
	}
	public boolean retirarACredito(float cantidad){
		saldo-=cantidad;
		return true;
	}

	@Override
	public float consultarSaldo() {
		// TODO Auto-generated method stub
		return saldo;
	}
	
}
