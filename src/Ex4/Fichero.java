package Ex4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Fichero {
	
	public static ArrayList<Cliente> leerFichero(){
		ArrayList<Cliente> array=new ArrayList<Cliente>();
		try{
		File fichero=new File("src/Ficheros/Clientes.txt");
		FileReader fr=new FileReader(fichero);
		BufferedReader br=new BufferedReader(fr);
		String linea="";
	
		
		while((linea=br.readLine())!=null){			
				String [] part=linea.split(";");
				array.add(new Cliente(part[0],part[1],part[2],part[3]));
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
			aux_cuenta_corriente=x.getCunetacorriente().consultar();
		}
		if(x.getCunetaahorro()==null){
			aux_cuenta_ahorro=-1;
			aux_penalizacion=-1;
		}else{
			aux_cuenta_ahorro=x.getCunetaahorro().consultar();
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
	public static void sobreescribirFichero(ArrayList<Cliente> array){
		try{
			
			FileWriter fichero=new FileWriter("src/Ficheros/Clientes.txt");
			PrintWriter pw=new PrintWriter(fichero);
			float aux_cuenta_corriente=0, aux_cuenta_ahorro=0, aux_penalizacion=0;
			for(int i=0; i<array.size(); i++){
				if(array.get(i).getCunetacorriente()==null){
					aux_cuenta_corriente=-1;
				}else{
					aux_cuenta_corriente=array.get(i).getCunetacorriente().consultar();
				}
				if(array.get(i).getCunetaahorro()==null){
					aux_cuenta_ahorro=-1;
					aux_penalizacion=-1;
				}else{
					aux_cuenta_ahorro=array.get(i).getCunetaahorro().consultar();
					aux_penalizacion=array.get(i).getCunetaahorro().get_penalizacion();
				}
			pw.println(array.get(i).getDni()+";"+aux_cuenta_corriente+";"+aux_cuenta_ahorro+";"+aux_penalizacion+";");
			}
	
			pw.close();
			fichero.close();
			}catch(Exception e){
				System.out.println("Error al escribir en la BBDD de los clientes");
			}
	}
}
