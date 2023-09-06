import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class HotelApp {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String JDBC_USER = "tu_usuario";
    private static final String JDBC_PASSWORD = "tu_contraseña";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");

                // Registro de usuario
                if (registrarUsuario("usuario1", "contraseña1")) {
                    System.out.println("Usuario registrado exitosamente.");
                } else {
                    System.out.println("No se pudo registrar el usuario.");
                }

                // Agregar plato al menú del restaurante
                if (agregarPlato("Plato1", "Descripción del plato 1", 15.99)) {
                    System.out.println("Plato agregado al menú exitosamente.");
                } else {
                    System.out.println("No se pudo agregar el plato al menú.");
                }

                // Hacer una reserva de habitación
                Date fechaEntrada = Date.valueOf("2023-09-10");
                Date fechaSalida = Date.valueOf("2023-09-15");
                if (hacerReserva(1, fechaEntrada, fechaSalida, "Habitación individual")) {
                    System.out.println("Reserva realizada exitosamente.");
                } else {
                    System.out.println("No se pudo hacer la reserva.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registrarUsuario(String nombreUsuario, String contraseña) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contraseña) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, contraseña);
            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean agregarPlato(String nombre, String descripcion, double precio) {
        String sql = "INSERT INTO menu_restaurante (nombre, descripcion, precio) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setDouble(3, precio);
            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hacerReserva(int idCliente, Date fechaEntrada, Date fechaSalida, String tipoHabitacion) {
        String sql = "INSERT INTO reservas (id_cliente, fecha_entrada, fecha_salida, tipo_habitacion) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idCliente);
            preparedStatement.setDate(2, fechaEntrada);
            preparedStatement.setDate(3, fechaSalida);
            preparedStatement.setString(4, tipoHabitacion);
            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
