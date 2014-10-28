package Ex4_R;

public interface OperacionesCuentas {

	boolean ingresar(float efectivo);
	boolean retirar(float cantidad);
	float consultarSaldo();
}
