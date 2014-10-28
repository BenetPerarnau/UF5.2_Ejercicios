package Ex4_R;

import java.util.ArrayList;
import java.util.Map;

import java.util.Iterator;
public class Test_Banco {

	private static String dni;
	private static Cliente abonado=null;
	private static Map<String,Cliente> array=Fichero.leerFichero();//cuando inicia se carga automaticamente la BBDD
	
	public static void main(String[]args){
		byte op=0;
		do{
			Iterator it=array.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry x=(Map.Entry) it.next();
				if(((Cliente)x.getValue()).getCunetacorriente()!=null && ((Cliente)x.getValue()).getCunetaahorro()!=null){
					System.out.println("DNI  => "+((Cliente)x.getValue()).getDni()+" Saldo Corriente => "+((Cliente)x.getValue()).getCunetacorriente().consultarSaldo()+" Saldo Ahorro => "+((Cliente)x.getValue()).getCunetaahorro().consultarSaldo());
				}else if(((Cliente)x.getValue()).getCunetacorriente()!=null){
					System.out.println("DNI  => "+((Cliente)x.getValue()).getDni()+" Saldo Corriente => "+((Cliente)x.getValue()).getCunetacorriente().consultarSaldo());
				}else{
					System.out.println("DNI  => "+((Cliente)x.getValue()).getDni()+" Saldo Ahorro => "+((Cliente)x.getValue()).getCunetaahorro().consultarSaldo());
				}
				
			}
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
							array.put(dni,abonado);
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
							array.put(dni,abonado);//añadir el nuevo cliente en el array para no tener que volver a leer el fichero
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
							
							array.get(dni).setCunetaahorro(new CuentaAhorro(inicial,(float)0.10));
							
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
							array.get(dni).setCunetacorriente(new CuentaCorriente(inicial));
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
						System.out.println("Saldo Cuenta Ahorro => "+abonado.getCunetaahorro().consultarSaldo()+"€");
					}
					if(abonado.getCunetacorriente()!=null){
						System.out.println("Saldo Cuenta Corriente => "+abonado.getCunetacorriente().consultarSaldo()+"€");
					}
				}
				break;
			case 3://Extraer dinero cuenta bancaria
				if(abonado==null){
					System.out.println("El DNI: "+dni+" no corresponde a ningun cliente");
				}else{
					
				float retirar=Menus.pedir_saldo_retirar();
				
				if(abonado.getCunetacorriente()!=null){//por defecto al retirar dinero se extrae de la cuenta corriente
					
						if(abonado.getCunetacorriente().retirar(retirar)){//nuevo //si el saldo a retirar es menor al saldo de la cuenta
						System.out.println("El saldo se ha extraido correctamente de la cuenta Corriente");
						System.out.println("El saldo actual de la cuenta corriente es: "+abonado.getCunetacorriente().consultarSaldo()+"€");
						
						Fichero.sobreescribirFichero(array);
						
						}else if(abonado.getCunetaahorro()!=null){//el saldo a retirar es mayor al saldo de la cuenta corriente se mira si dispoe de cuenta de ahorro
						
						if(abonado.getCunetaahorro().retirar(retirar)){
						//perfecto se ha podido extraer todo del saldo de la cuenta de ahorro
							System.out.println("Se ha extraido de la cuenta Ahorro"+ retirar+" € + "+abonado.getCunetaahorro().get_penalizacion()*100+"% Toal => "+retirar+retirar*abonado.getCunetaahorro().get_penalizacion());
							System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultarSaldo()+"€");
						}
					}else{//si no puede ser extraido se sacara de la Corriente dejandola en negativo
						
						abonado.getCunetacorriente().retirarACredito(retirar);
						
						System.out.println("El saldo actual de la cuenta corriente es: "+abonado.getCunetacorriente().consultarSaldo()+"€");
						
						Fichero.sobreescribirFichero(array);
					}	
				}else{//si no se dispone de cuenta corriente o saldo de ella, se extrae de la cuenta de ahorros
					
					if(abonado.getCunetaahorro().retirar(retirar)){
						
						System.out.println("Se ha extraido de la cuenta Ahorro "+retirar+"€");
						System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultarSaldo()+"€");
						Fichero.sobreescribirFichero(array);
						
					}else{
						
						System.out.println("No puedes sacar de la cuenta de ahorros mas del saldo disponible.");
						System.out.println("El saldo actual de la cuenta ahorro es: "+abonado.getCunetaahorro().consultarSaldo()+"€");
						System.out.println("Estas intentando sacar "+retirar+"€ + la penalizacion "+retirar*abonado.getCunetaahorro().get_penalizacion()+"€ suman "+retirar+retirar*abonado.getCunetaahorro().get_penalizacion()+"€");
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
							System.out.println("Saldo actual cuenta Corriente :"+abonado.getCunetacorriente().consultarSaldo()+"€");
						}else{//ingreso en Ahorro
							abonado.getCunetaahorro().ingresar(ingreso);
							Menus.ingreso_exito(ingreso);
							System.out.println("Saldo actual cuenta Ahorro :"+abonado.getCunetaahorro().consultarSaldo()+"€");
						}
					}else if(abonado.getCunetacorriente()!=null){//cliente solo con Cuenta Corriente
						float ingreso=Menus.pedir_cantidad_ingreso();
						abonado.getCunetacorriente().ingresar(ingreso);
						Menus.ingreso_exito(ingreso);
						System.out.println("Saldo actual cuenta Corriente :"+abonado.getCunetacorriente().consultarSaldo()+"€");
					}else{//Cliente solo con cuenta Ahorro
						float ingreso=Menus.pedir_cantidad_ingreso();
						abonado.getCunetaahorro().ingresar(ingreso);
						Menus.ingreso_exito(ingreso);
						System.out.println("Saldo actual cuenta Ahorro :"+abonado.getCunetaahorro().consultarSaldo()+"€");
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
