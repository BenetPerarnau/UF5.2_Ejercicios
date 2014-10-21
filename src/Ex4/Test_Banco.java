package Ex4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Test_Banco {

	//private static BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	private static String dni;
	private static ArrayList<Cliente> array=Fichero.leerFichero();//cuando inicia se carga automaticamente la BBDD
	public static void main(String[]args){
		byte op=0;
		do{
			
			op=Menus.menuprincipal();
			dni=Menus.pedirDni();
			Cliente abonado=Operaciones.isCliente(array, dni);
			switch(op){
			case 1://abrir cuenta bancaria		
				if(abonado==null){//el dni es nuevo
					byte tipo=0;
					tipo=Menus.menuAbrirCuentaClienteNuevo();
					switch(tipo){
					case 1://nueva cuenta corriente
						float inicial=0;
						try {
							inicial=Menus.pedirSalodInicialCuenta();
							abonado=new Cliente(dni);
							abonado.setCunetacorriente(new CuentaCorriente(inicial));
							Fichero.escribirFichero(abonado);//guardar nuevo cliente
							array.add(abonado);
							System.out.println("Todo correcto. Ya tienes una nueva cuenta Corriente");
						} catch (SaldoInicialInsuficienteException e) {
							System.out.println("ERROR: "+e.getMessage());
						}
						break;
					case 2://nueva cuenta ahorro	
						try {
							inicial=Menus.pedirSalodInicialCuenta();
							abonado=new Cliente(dni);
							abonado.setCunetaahorro(new CuentaAhorro(inicial,(float)0.10));
							Fichero.escribirFichero(abonado);//guardar nuevo cliente
							array.add(abonado);//añadir el nuevo cliente en el array para no tener que volver a leer el fichero
							System.out.println("Todo correcto. Ya tienes una nueva cuenta de Ahorro");
						} catch (SaldoInicialInsuficienteException e) {
							System.out.println("ERROR: "+e.getMessage());
						}
						break;
						default:
							//no entrara pq controlamos la op en menuAbrirCuentaClienteNuevo()
							break;
					}
				}else{//el dni existe, es decir ya es cliente del banco, como el usuario existe no conteplo la op de que no tenga ninguna de las dos
					if(abonado.getCunetaahorro()==null){//no tiene cuenta de ahorro
						System.out.println("Ya tienes una Cuenta Corriente.\nPuedes abrir una cuneta de Ahorro:");
						try {
							float inicial=Menus.pedirSalodInicialCuenta();
							for(int i=0; i<array.size(); i++){
								if(array.get(i).equals(abonado)){
									array.get(i).setCunetaahorro(new CuentaAhorro(inicial,(float)0.10));
								}
							}
							Fichero.sobreescribirFichero(array);
							System.out.println("Todo correcto. Ya tienes una nueva cuenta de Ahorro");
						} catch (SaldoInicialInsuficienteException e) {
							System.out.println("ERROR: "+e.getMessage());
						}
						
					}
					else if(abonado.getCunetacorriente()==null){//no tiene cuneta corriente
						System.out.println("Ya tienes una Cuenta Ahorro.\nPuedes abrir una cuneta Corriente:");
						try {
							float inicial=Menus.pedirSalodInicialCuenta();
							for(int i=0; i<array.size(); i++){
								if(array.get(i).equals(abonado)){
									array.get(i).setCunetacorriente(new CuentaCorriente(inicial));
								}
							}
							Fichero.sobreescribirFichero(array);
							System.out.println("Todo correcto. Ya tienes una nueva cuenta Corriente");
						} catch (SaldoInicialInsuficienteException e) {
							System.out.println("ERROR: "+e.getMessage());
						}
						
					}else{
						System.out.println("No puede solicitar la apertura de una nueva cuenta"
								+ "ya tienes una cuenta de ahorro y una corriente");
					}
				}
				break;
			case 2:// Consulta saldo cuenta bancaria
				if(abonado==null){
					System.out.println("El DNI: "+dni+" no corresponde a ningun cliente");
				}else{
					if(abonado.getCunetaahorro()!=null){
						System.out.println("Saldo Cuenta Ahorro => "+abonado.getCunetaahorro().consultar()+"€");
					}
					if(abonado.getCunetacorriente()!=null){
						System.out.println("Saldo Cuenta Corriente => "+abonado.getCunetacorriente().consultar()+"€");
					}
				}
				break;
			case 3://Extraer dinero cuenta bancaria
				if(abonado==null){
					System.out.println("El DNI: "+dni+" no corresponde a ningun cliente");
				}else{
				float retirar=Menus.pedir_saldo_retirar();
				if(abonado.getCunetacorriente()!=null){//por defecto al retirar dinero se extrae de la cuenta corriente
					
					if(retirar<=abonado.getCunetacorriente().consultar()){//si el saldo a retirar es menor al saldo de la cuenta
						abonado.getCunetacorriente().retirar(retirar);
						System.out.println("El saldo se ha extraido correctamente de la cuenta de ahorros");
					}else if(abonado.getCunetaahorro()!=null){//el saldo a retirar es mayor al saldo de la cuenta
						float saldodisponibleccorriente=abonado.getCunetacorriente().consultar();//aux saldo corriente
						float saldodisponiblecahorro=abonado.getCunetaahorro().consultar();// aux saldo ahorro
						float resto=retirar-saldodisponibleccorriente;//valor que se intentara pagar primero desde una cuenta de ahorro o sino se restara a la cuenta corriente
						abonado.getCunetacorriente().retirar(saldodisponibleccorriente);//retirar todo lo posible de la cuenta corriente primero(pq cuanto menos saquemos de la de ahorros menos penalización tendremos)
						if(resto<=saldodisponiblecahorro){//mirar que el resto pueda ser extraido de la cuenta de ahorros
						abonado.getCunetaahorro().retirar(resto);
						System.out.println("Se ha extraido de la cuenta Corriente "+saldodisponibleccorriente+"€");
						System.out.println("Y "+resto+"€ de la cuenta de ahorros");
						}else{//si no puede ser extraido se sacara de la Corriente dejandola en negativo
							
							abonado.getCunetacorriente().retirar(resto);// quedara la cuenta corriente en negativo
							System.out.println("Se ha extraido de la cuenta Corriente "+retirar+"€");
							System.out.println("Ten en cuenta que ahora el sado de la cuenta es negativo "+abonado.getCunetacorriente().consultar());
						}
					}	
				}else{//si no se dispone de cuenta corriente o saldo de ella, se extrae de la cuenta de ahorros
					if(retirar<=abonado.getCunetaahorro().consultar()){
						abonado.getCunetaahorro().retirar(retirar);
					}else{
						System.out.println("No puedes sacar de la cuenta de ahorros mas del saldo disponible.");
					}
				}
				}
				break;
			case 4:
				if(abonado==null){
					System.out.println("El DNI: "+dni+" no corresponde a ningun cliente");
				}else{
					
				}
				break;
			case 5:
				System.out.println("Bye");
				break;
				default:
					System.out.println("Valores entra el 1 y el 5");
					break;
			}
			
			
			
		}while(op!=5);	
	}	
}
