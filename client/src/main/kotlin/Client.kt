import Constants.PORT
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client {
    var username: String? = null

    fun start() {
        while (username.isNullOrEmpty()) {
            print("What is your name? ")
            username = readlnOrNull()
        }

        Socket("127.0.0.1", PORT).use { socket ->
            val writer = PrintWriter(socket.getOutputStream(), true)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

            writer.println(username)
            println("Hello, $username!")
            println("Connected to ${socket.inetAddress.hostAddress}:${socket.port}\n")

            Thread {
                    while (true) {
                        val line = readln()
                        writer.println(line)
                    }
                }
                .start()

            reader.forEachLine { line -> println(line) }
        }
    }
}

fun main() {
    Client().start()
}
