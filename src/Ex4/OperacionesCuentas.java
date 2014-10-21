package Ex4;

public interface OperacionesCuentas {

	boolean ingresar(float efectivo);
	boolean retirar(float cantidad);
	float consultar();
}
