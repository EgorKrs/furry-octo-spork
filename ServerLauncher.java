import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerLauncher {


    public static void main(String[] args) {
        Server server = new Server(8000);
        while (true){
            BufferedReader inputServer;
            inputServer = new BufferedReader(new InputStreamReader(System.in));
            try {
                String command=inputServer.readLine();
                if(command.equals("closeServer")) {
                    server.closeServer();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            server.applyConnection();
        }

    }
}

