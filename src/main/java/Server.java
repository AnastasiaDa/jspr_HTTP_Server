import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {

    public final int maxConnectionsNum = 64;

    public void start(int port) {
        var connectionPool = Executors.newFixedThreadPool(maxConnectionsNum);

        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                final var socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connectionPool.execute(connection);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
