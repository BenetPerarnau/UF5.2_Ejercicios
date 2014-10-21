package Ex4;

import java.util.ArrayList;

public class Test_Banco {

	private static String dni;
	private static Cliente abonado=null;
	private static ArrayList<Cliente> array=Fichero.leerFichero();//cuando inicia se carga automaticamente la BBDD
	
	public static void main(String[]args){
		byte op=0;
		do{
			op=Menus.menuprincipal();
			if(op!=5){
			dni=Menus.pedirDni();
			abonado=Operaciones.isCliente(array, dni);
			}
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
						System.out.println("El saldo se ha extraido correctamente de la cuenta Corriente");
						System.out.println("El saldo actual de la cuenta corriente es: "+abonado.getCunetacorriente().consultar()+"€");
						System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultar()+"€");
						
						//array.get(posarray).getCunetacorriente().retirar(retirar);
						Fichero.sobreescribirFichero(array);
						
					}else if(abonado.getCunetaahorro()!=null){//el saldo a retirar es mayor al saldo de la cuenta
						
						float saldodisponibleccorriente=abonado.getCunetacorriente().consultar();//aux saldo corriente
						float saldodisponiblecahorro=abonado.getCunetaahorro().consultar();// aux saldo ahorro
						float resto=retirar-saldodisponibleccorriente;//valor que se intentara pagar primero desde una cuenta de ahorro o sino se restara a la cuenta corriente dejandola en negativo
						float penalizacionahorro=abonado.getCunetaahorro().get_penalizacion();//% penalizacion por sacar de cuenta de ahorro
						
						abonado.getCunetacorriente().retirar(saldodisponibleccorriente);//retirar todo lo posible de la cuenta corriente primero(pq cuanto menos saquemos de la de ahorros menos penalización tendremos)
						
						if((resto+resto*penalizacionahorro)<=saldodisponiblecahorro){//mirar que el (resto+penalización) pueda ser extraido de la cuenta de ahorros
						
							abonado.getCunetaahorro().retirar(resto+(resto*penalizacionahorro));
							System.out.println("Se ha extraido de la cuenta Corriente "+saldodisponibleccorriente+"€");
							System.out.println("Y "+resto+"€ + "+resto*penalizacionahorro+"€ de penalizacion de la cuenta de ahorros");
							System.out.println("El saldo actual de la cuenta corriente es: "+abonado.getCunetacorriente().consultar()+"€");
							System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultar()+"€");
							Fichero.sobreescribirFichero(array);
							
						}else{//si no puede ser extraido se sacara de la Corriente dejandola en negativo
							
							abonado.getCunetaahorro().retirar(saldodisponiblecahorro);//sacar todo lo posible primero de la cuenta de ahorro pq las replesalias de sacar dinero de cuentas de ahorro son menores que dejar una cuenta corriente al descubierto
							resto-=saldodisponiblecahorro-(saldodisponiblecahorro*penalizacionahorro);//pero claro como hay penalizacion no todo lo que sacamos de la cuenta ahorro es neto solo sera el 90% en este caso pq tenmos una penalizacion del 10%
							abonado.getCunetacorriente().retirar(resto);//en este punto la cuenta corriente ya esta a 0€, + lo que sacamos quedara la cuenta en negativo
							System.out.println("Se ha extraido de la cuenta Corriente "+(saldodisponibleccorriente+resto)+"€");
							System.out.println("Se ha extraido de la cuenta Ahorro "+saldodisponiblecahorro+"€ de los cuales "+saldodisponiblecahorro*penalizacionahorro+"€ son de penalización");
							System.out.println("El saldo actual de la cuenta corriente es: "+abonado.getCunetacorriente().consultar()+"€");
							System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultar()+"€");
							Fichero.sobreescribirFichero(array);
						}
					}	
				}else{//si no se dispone de cuenta corriente o saldo de ella, se extrae de la cuenta de ahorros
					
					float auxiliar=retirar+retirar*abonado.getCunetaahorro().get_penalizacion();//lo que se pide sacar + penalizacion
					
					if(auxiliar<=abonado.getCunetaahorro().consultar()){
						
						abonado.getCunetaahorro().retirar(auxiliar);
						System.out.println("Se ha extraido de la cuenta Ahorro "+auxiliar+"€");
						System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultar()+"€");
						Fichero.sobreescribirFichero(array);
						
					}else{
						
						System.out.println("No puedes sacar de la cuenta de ahorros mas del saldo disponible.");
						System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultar()+"€");
						System.out.println("Estas intentando sacar "+retirar+"€ + la penalizacion "+retirar*abonado.getCunetaahorro().get_penalizacion()+"€ suman "+auxiliar+"€");
					}
				}
				}
				break;
			case 4:
				if(abonado==null){
					System.out.println("El DNI: "+dni+" no corresponde a ningun cliente");
				}else{
					if(abonado.getCunetaahorro()!=null && abonado.getCunetacorriente()!=null){//cliente con 2 cuentas
						byte opcuenta=Menus.pedir_q_cuenta();
						float ingreso=Menus.pedir_cantidad_ingreso();
						if(opcuenta==1){//ingreso en Corriente
							abonado.getCunetacorriente().ingresar(ingreso);
							Menus.ingreso_exito(ingreso);
							System.out.println("Saldo actual cuenta Corriente :"+abonado.getCunetacorriente().consultar()+"€");
						}else{//ingreso en Ahorro
							abonado.getCunetaahorro().ingresar(ingreso);
							Menus.ingreso_exito(ingreso);
							System.out.println("Saldo actual cuenta Ahorro :"+abonado.getCunetaahorro().consultar()+"€");
						}
					}else if(abonado.getCunetacorriente()!=null){//cliente solo con Cuenta Corriente
						float ingreso=Menus.pedir_cantidad_ingreso();
						abonado.getCunetacorriente().ingresar(ingreso);
						Menus.ingreso_exito(ingreso);
						System.out.println("Saldo actual cuenta Corriente :"+abonado.getCunetacorriente().consultar()+"€");
					}else{//Cliente solo con cuenta Ahorro
						float ingreso=Menus.pedir_cantidad_ingreso();
						abonado.getCunetaahorro().ingresar(ingreso);
						Menus.ingreso_exito(ingreso);
						System.out.println("Saldo actual cuenta Ahorro :"+abonado.getCunetaahorro().consultar()+"€");
					}
					Fichero.sobreescribirFichero(array);
				}
				break;
			case 5:
				System.out.println("Bye");
				break;
				default:
					//no entrara nunca pq ya esta controlado en Menus.menuprincipal();
					break;
			}		
		}while(op!=5);	
	}	
}
