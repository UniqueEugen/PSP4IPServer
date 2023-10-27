package ipServer;

import counter.DekartCounter;
import counter.MatrixCounter;
import myLibrary.console.Console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientThread extends Thread {
    Socket serverClient;
    private ObjectInputStream sois = null;//объявление байтового потока ввода
    private ObjectOutputStream soos = null;//объявление байтового потока вывода
    private final String CLOSE_CMD;
    private final String DISCONNECTED_CMD = "DSCNNCTD";
    private final String MATRIX_CMD = "MATRIX";
    private final String DEKART_CMD = "DEKART";
    int clientNo;

    ServerClientThread(Socket inSocket, int counter, String CLOSE_CMD) {
        serverClient = inSocket;
        clientNo = counter;
        this.CLOSE_CMD = CLOSE_CMD;
    }

    public void run() {
        boolean close_server = false;
        try {
            String cmd;
            sois = new ObjectInputStream(serverClient.getInputStream());
            soos = new ObjectOutputStream(serverClient.getOutputStream());
            String clientMessageRecieved = (String) sois.readObject();
            cmd = clientMessageRecieved.split(",")[0];
            while (!cmd.equals(DISCONNECTED_CMD)) {
                clientMessageRecieved = clientMessageRecieved.split(",")[1];
                Console.log("message recieved: '" + clientMessageRecieved + "'");
                switch (cmd) {
                    case MATRIX_CMD:
                        MatrixCounter matrixCounter = new MatrixCounter(clientMessageRecieved);
                        soos.writeObject(matrixCounter.count());
                        break;
                    case DEKART_CMD:
                        DekartCounter dekartCounter = new DekartCounter(clientMessageRecieved);
                        soos.writeObject(dekartCounter.count());
                        break;
                    default:
                        throw new Exception("CMD ERROR");
                }
                clientMessageRecieved = (String) sois.readObject();
                cmd = clientMessageRecieved.split(",")[0];
            }
        } catch (Exception ex) {
            System.out.println(ex);
            try {
                soos.writeObject(ex.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                soos.writeObject(CLOSE_CMD + "D_SERVER_CONNECTION");
                sois.close();//закрытие потока ввода
                soos.close();//закрытие потока вывода
                serverClient.close();//закрытие сокета, выделенного для клиента
            } catch (Exception e) {
                System.out.println(e);
            }
            Console.log("Client -" + clientNo + " exit!! ");
        }
        //if (close_server) {return 1;} else return 0;
    }
}
