package ipServer;

import myLibrary.console.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket = null;
    private Socket clientAccepted = null;//объявление объекта класса Socket
    private final String  CLOSE_CMD = "CLOSE";

    public void runServer() throws IOException {
        boolean endFlag;
        try {
            Console.log("server starting....");
            serverSocket = new ServerSocket(2525);
            endFlag=false;
            int counter=0;
            while (!endFlag) {
                counter++;
                clientAccepted = serverSocket.accept();
                Console.log("connection established....");
                ServerClientThread sct = new ServerClientThread(clientAccepted,counter, CLOSE_CMD); // отправляем запрос в отдельный поток
                sct.start();
            }
        }
        catch (Exception e) {
            clientAccepted.close();//закрытие сокета, выделенного для клиента
        } finally {
            try {
                serverSocket.close();//закрытие сокета сервера
            } catch (Exception e) {
                e.printStackTrace();//вызывается метод исключения е
            }
        }
    }

}
