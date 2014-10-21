package Ex4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Test_Banco {

	private static BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
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
					
					if(retirar<=abonado.getCunetacorriente().consultar()){
						abonado.getCunetacorriente().retirar(retirar);
					}else if(abonado.getCunetaahorro()!=null){
						float saldodisponibleccorriente=abonado.getCunetacorriente().consultar();
						float saldodisponiblecahorro=abonado.getCunetaahorro().consultar();
						float resto=retirar-saldodisponibleccorriente;
						abonado.getCunetacorriente().retirar(saldodisponibleccorriente);
						boolean resultat=abonado.getCunetaahorro().retirar(resto);
						if(resultat==false){
							abonado.getCunetacorriente().retirar(resto);
						}else{
							//no fa res
						}
					}
					
				}else{//si no se dispone de cuenta corriente o saldo en ella se extrae de la cuenta de ahorros
					abonado.getCunetaahorro().retirar(retirar);
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
