package Ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menus {
	private static BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	public static byte menuprincipal(){
		byte op=0;
		do{
		System.out.println("1 .Abrir cuenta bancaria");
		System.out.println("2. Consultar saldo cuenta bancaria");
		System.out.println("3. Extraer dinero cuenta bancaria");
		System.out.println("4. Ingresar dinero cuenta bancaria");
		System.out.println("5. Salir");
		System.out.print("OP ==> ");
		op=0;
		try {
			 op=Byte.parseByte(stdin.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Se espera un valor numerico "+e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}while(op<1 || op>5);
		
		return op;
	}
	public static String pedirDni(){
		boolean correcto=false;
		String dni="";
		try{
			do{
			System.out.print("DNI => ");
			dni=stdin.readLine();
			correcto=Operaciones.isdni(dni);
			}while(correcto==false);
		}catch(Exception e){
			System.out.println("Error "+e);
		}
		return dni;
	}
	public static byte menuAbrirCuentaClienteNuevo(){
		byte op=0;
		do{
		try{
			System.out.println("Cliente nuevo");
			System.out.println("1. Abrir Cuenta Corriente");
			System.out.println("2. Abrir Cuenta de Ahorro");
			System.out.print("OP => ");
			op=Byte.parseByte(stdin.readLine());
		}catch(Exception e){
			System.out.println("Se espera valor numerico "+e);
		}
		}while(op<1 || op>2);
		return op;
	}
	public static float pedirSalodInicialCuenta() throws SaldoInicialInsuficienteException{
		float inicial=0;
		try{
		System.out.print("Saldo inicial => ");
		inicial=Float.parseFloat(stdin.readLine());
		
		}catch(Exception e){
			System.out.println("Se espera un valor numerico "+e);
		}
		if(inicial<50){
			throw new SaldoInicialInsuficienteException("El saldo minimo para una nueva cuenta son 50€");
		}
		
		return inicial;
	}
	
	public static float pedir_saldo_retirar(){
		float retirar=0;
		do{
			try{
			System.out.print("Saldo a retirar => ");
			retirar=Float.parseFloat(stdin.readLine());
			if(retirar<1){
				System.out.println("El saldo a retirar debe ser mayor a 0€");
			}
			}catch(Exception e){
				System.out.println("Se espera un valor numerico "+e);
			}
		}while(retirar<1);
		return retirar;
	}
}
