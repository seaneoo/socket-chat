import Constants.CHARSET
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket
import java.util.UUID

class Client(
    val socket: Socket,
    private val writer: PrintWriter = PrintWriter(socket.getOutputStream(), true, CHARSET),
    private val reader: BufferedReader = socket.getInputStream().bufferedReader(CHARSET),
) {
    val id: UUID = UUID.randomUUID()
    var username: String? = null
    val displayName: String
        get() = if (username == null) id.toString() else username!!

    fun readUsername() {
        username = reader.readLine()
    }

    fun read(): String? {
        return reader.readLine() ?: return null
    }

    fun message(message: String) {
        writer.println(message)
    }
}
