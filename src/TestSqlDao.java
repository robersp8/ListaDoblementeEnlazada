import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;

/**
 * Mejorar cada uno de los métodos a nivel SQL y código cuando sea necesario
 * Razonar cada una de las mejoras que se han implementado
 * No es necesario que el código implementado funcione 
 */
public class TestSqlDao {

	private static TestSqlDao instance = new TestSqlDao();
	private Hashtable<Long, Long> maxOrderUser;
	
	private TestSqlDao() {

	}

	private static TestSqlDao getInstance() {

		return instance;
	}

	/**
	 * Obtiene el ID del último pedido para cada usuario
	 */
	public Hashtable<Long, Long> getMaxUserOrderId(long idTienda) throws Exception {

		// Con esta sentencia SQL evitaremos tener que evaluar más tarde cual de los pedidos es el último, directamente seleccionaremos el mayor ID_PEDIDO de todas las selecciones que tengamos que cumplan los requisitos evaluados en el SELECT.
		String query = String.format("SELECT ID_PEDIDO, ID_USUARIO FROM PEDIDOS AS P " + "WHERE p1.ID_PEDIDO = (SELECT MAX(ID_PEDIDO) FROM PEDIDOS "
                + "WHERE ID_USUARIO = P.ID_USUARIO)" + " AND ID_TIENDA = %s", idTienda);
		Connection connection = getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		maxOrderUser = new Hashtable<Long, Long>();
		
		// Como comentabamos los if anidados serán innecesarios ya que nuestra query directamente nos proporcionará como primer valor el pedido con el ID más alto de todos.
		//Entendemos que unicamente se deberia devolver un valor, por eso usaremos el método first()
		if (rs.first()) {

			long idPedido = rs.getInt("ID_PEDIDO");
			long idUsuario = rs.getInt("ID_USUARIO");
			
			maxOrderUser.put(idUsuario, idPedido);
		}

		return maxOrderUser;
	}

	/**
	 * Copia todos los pedidos de un usuario a otro
	 */
	public void copyUserOrders(long idUserOri, long idUserDes) throws Exception {
		String query = String.format("SELECT FECHA, TOTAL, SUBTOTAL, DIRECCION FROM PEDIDOS WHERE ID_USUARIO = %s", idUserOri);
		Connection connection = getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			//Trasladaremos la conversión de datos a un paso anterior, ya que no resulta adecuado hacer el get de los datos en la misma sentencia SQL en la que vamos a realizar la inserción.
			Date fecha = rs.getTimestamp("FECHA");
			double total = rs.getDouble("TOTAL");
			double subtotal = rs.getDouble("SUBTOTAL");
			String direccion = rs.getString("DIRECCION");
			String insert = String.format("INSERT INTO PEDIDOS (FECHA, TOTAL, SUBTOTAL, DIRECCION) VALUES (%s, %s, %s, %s)", fecha, total, subtotal, direccion);

			Connection connection2 = getConnection();
			connection2.setAutoCommit(false);
			PreparedStatement stmt2 = connection2.prepareStatement(insert);
			stmt2.executeUpdate();
			connection2.commit();
		}
	}

	/**
	 * Obtiene los datos del usuario y pedido con el pedido de mayor importe para la tienda dada
	 */
	public void getUserMaxOrder(long idTienda, long userId, long orderId, String name, String address) throws Exception {
		//Al igual que en el metodo getMaxUserOrderId con la clausula MAX recogeremos el campo TOTAL de mayor valor en la tabla, lo que nos simplificará el codigo facilitará la lectura del mismo.
		String query = String.format("SELECT U.ID_USUARIO, P.ID_PEDIDO, MAX(P.TOTAL), U.NOMBRE, U.DIRECCION FROM PEDIDOS AS P "
                + "INNER JOIN USUARIOS AS U ON P.ID_USUARIO = U.ID_USUARIO WHERE P.ID_TIENDA = %", idTienda);
		Connection connection = getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		//Entendemos que unicamente se deberia devolver un valor, por eso usaremos el método first()
		if(rs.first()){
			//La variable total estaba sin inicializar
				long total = rs.getLong("TOTAL");
				userId = rs.getInt("ID_USUARIO");
				orderId = rs.getInt("ID_PEDIDO");
				name = rs.getString("NOMBRE");
				address = rs.getString("DIRECCION");
		}
	}

	private Connection getConnection() {
		// return JDBC connection
		return null;
	}
}
