package Ex4_R;

public class CuentaAhorro implements OperacionesCuentas {
	
	private float saldo;
	private float penalizacion;
	
	public CuentaAhorro(float saldo, float penalizacion)throws SaldoInicialInsuficienteException{
		
		if(saldo<50){
		throw 	new SaldoInicialInsuficienteException("El saldo para abrir una nueva cuenta son 50 €");
		}else{
		this.saldo=saldo;
		this.penalizacion=penalizacion;
		}
	}
	public CuentaAhorro(float saldo, float penalizacion,char f){
		//este constructor es para leer el fichero pq? pq si dejamos a 0 o negativo un cliente a la que vuelva a leer el fichero petaria usando el otro constructor
		this.saldo=saldo;
		this.penalizacion=penalizacion;
		
	}
	
	public float get_penalizacion(){
		
		return this.penalizacion;
	}
	
	public boolean ingresar(float efectivo) {
		
	
			this.saldo+=efectivo;
			return true;
	
		
	}

	
	public boolean retirar(float cantidad) {
			
		if(cantidad+cantidad*this.penalizacion<=saldo){
			saldo-=cantidad+cantidad*this.penalizacion;
			return true;
		}else{
			return false;
		}
		
	}

	
	public float consultarSaldo() {
		return this.saldo;
	}

}
