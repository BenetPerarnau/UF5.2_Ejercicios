package Ex4_R;

public class Cliente {

	private String dni;	
	private CuentaAhorro cunetaahorro;
	private CuentaCorriente cunetacorriente;
	
	public Cliente(String dni){
		this.dni=dni;
	}
	public Cliente(String dni, String ccorriente, String cahorro, String penalizacion){//constructor para los que entramos leyendo el fichero
		this.dni=dni;
		if((int)(Float.parseFloat(ccorriente))!=-1){
			try {
				this.cunetacorriente=new CuentaCorriente(Float.parseFloat(ccorriente),'f');
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//this.cunetacorriente=null;
		}
		if((int)(Float.parseFloat(cahorro))!=-1){
			try {
				this.cunetaahorro=new CuentaAhorro(Float.parseFloat(cahorro),Float.parseFloat(penalizacion),'f');
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//this.cunetaahorro=null;
		}
	}
	
	protected String getDni() {
		return dni;
	}
	

	protected CuentaAhorro getCunetaahorro() {
		return cunetaahorro;
	}
	protected void setCunetaahorro(CuentaAhorro cunetaahorro) {
		this.cunetaahorro = cunetaahorro;
	}
	protected CuentaCorriente getCunetacorriente() {
		return cunetacorriente;
	}
	protected void setCunetacorriente(CuentaCorriente cuentacorriente) {
		this.cunetacorriente = cuentacorriente ;
	}
	
}
