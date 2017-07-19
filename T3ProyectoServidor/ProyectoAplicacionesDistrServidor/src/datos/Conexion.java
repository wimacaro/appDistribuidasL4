package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class Conexion {
	//Singleton
	public static Conexion _Instancia;
	private Conexion(){};
	public static Conexion Instancia(){
		if(_Instancia==null){
			_Instancia = new Conexion();
		}
		return _Instancia;
	}
	//endSingleton

	static ResourceBundle msj = ResourceBundle.getBundle("database");
	
	
	
	public Connection getConnection() throws Exception {
		Connection con = null;
		
		try {
			Class.forName(msj.getString("driver"));
			con = DriverManager.getConnection(
					msj.getString("url"),msj.getString("username"),msj.getString("password"));
			System.out.println("Exito Conexion a la BD");
		} catch (Exception e) {
			System.out.println("Error Conexion a la BD " + e.getMessage());
			e.printStackTrace();
			System.out.println(e);
		}
		return con;
	}
	
	
	
	
}
