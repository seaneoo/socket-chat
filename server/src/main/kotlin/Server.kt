import Constants.PORT
import java.net.ServerSocket
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.concurrent.thread

class Server {
    private val serverSocket by lazy { ServerSocket(PORT) }
    private val clients = CopyOnWriteArrayList<Client>()

    fun listen() {
        println("Listening on port ${serverSocket.localPort}")

        try {
            while (true) {
                val socket = serverSocket.accept()
                thread {
                    val client = Client(socket)
                    clients.add(client)
                    handleClient(client)
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            serverSocket.close()
        }
    }

    fun handleClient(client: Client) {
        client.socket.use { socket ->
            println("Client connected ${socket.inetAddress.hostAddress} (ID: ${client.id}")
            client.readUsername()
            broadcast(client, "Connected")

            while (true) {
                val message = client.read() ?: break
                println("[${client.id}] $message")
                broadcast(client, message)
            }

            println("Client disconnected (ID: ${client.id})")
            broadcast(client, "Disconnected")
            clients.remove(client)
        }
    }

    fun broadcast(sender: Client, message: String) {
        clients
            .filter { it.id != sender.id }
            .forEach { client ->
                client.message("[${sender.displayName}] $message")
            }
    }
}

fun main() {
    Server().listen()
}
