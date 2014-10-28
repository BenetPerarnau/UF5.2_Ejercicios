package Ex4_R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Fichero {
	
	public static Map<String,Cliente> leerFichero(){
		Map<String,Cliente> array=new HashMap<String,Cliente>();
		try{
		File fichero=new File("src/Ficheros/Clientes.txt");
		FileReader fr=new FileReader(fichero);
		BufferedReader br=new BufferedReader(fr);
		String linea="";
	
		
		while((linea=br.readLine())!=null){			
				String [] part=linea.split(";");
				array.put(part[0],new Cliente(part[0],part[1],part[2],part[3]));
		}
		
		}catch(Exception e){
			System.out.println("Error en leer el archivo");
		}
		
		return array;
	}
	
	public static void escribirFichero(Cliente x){
		float aux_cuenta_corriente=0, aux_cuenta_ahorro=0, aux_penalizacion=0;
		try{
		if(x.getCunetacorriente()==null){
			aux_cuenta_corriente=-1;
		}else{
			aux_cuenta_corriente=x.getCunetacorriente().consultarSaldo();
		}
		if(x.getCunetaahorro()==null){
			aux_cuenta_ahorro=-1;
			aux_penalizacion=-1;
		}else{
			aux_cuenta_ahorro=x.getCunetaahorro().consultarSaldo();
			aux_penalizacion=x.getCunetaahorro().get_penalizacion();
		}
		}catch(Exception e){
			System.out.println("Error en la extraccion de los datos");
		}
		
		try{
			
		FileWriter fichero=new FileWriter("src/Ficheros/Clientes.txt",true);
		PrintWriter pw=new PrintWriter(fichero);
		
		pw.println(x.getDni()+";"+aux_cuenta_corriente+";"+aux_cuenta_ahorro+";"+aux_penalizacion+";");
		pw.close();
		fichero.close();
		}catch(Exception e){
			System.out.println("Error al escribir en la BBDD de los clientes");
		}
	}
	public static void sobreescribirFichero(Map<String,Cliente> array){
		try{
			
			FileWriter fichero=new FileWriter("src/Ficheros/Clientes.txt");
			PrintWriter pw=new PrintWriter(fichero);
			float aux_cuenta_corriente=0, aux_cuenta_ahorro=0, aux_penalizacion=0;
			
			Iterator it=array.entrySet().iterator();
			
			while(it.hasNext()){
				Map.Entry a=(Map.Entry)it.next();
				if(((Cliente)(a.getValue())).getCunetacorriente()==null){
					aux_cuenta_corriente=-1;
				}else{
					aux_cuenta_corriente=((Cliente)(a.getValue())).getCunetacorriente().consultarSaldo();
				}
				if(((Cliente)(a.getValue())).getCunetaahorro()==null){
					aux_cuenta_ahorro=-1;
					aux_penalizacion=-1;
				}else{
					aux_cuenta_ahorro=((Cliente)(a.getValue())).getCunetaahorro().consultarSaldo();
					aux_penalizacion=((Cliente)(a.getValue())).getCunetaahorro().get_penalizacion();
				}
				pw.println(((Cliente)(a.getValue())).getDni()+";"+aux_cuenta_corriente+";"+aux_cuenta_ahorro+";"+aux_penalizacion+";");
			}
			
			
	
			pw.close();
			fichero.close();
			}catch(Exception e){
				System.out.println("Error al sobreescribir en la BBDD de los clientes");
			}
	}
}
