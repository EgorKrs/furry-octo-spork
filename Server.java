import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server {
    ServerSocket socket;
     private boolean isOpen =true;

     Server(int port){
        try {
            socket=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public void setOpen(boolean open) {
         isOpen = open;
     }

    ExecutorService executorService = Executors.newCachedThreadPool();

    public static LinkedList<ClientWorkingThread > serverList = new LinkedList<>(); // список всех нитей

    public static Story story; // история переписки

    public void applyConnection(){
        while(isOpen){

            try {
                ClientWorkingThread clientWorkingThread = new ClientWorkingThread(socket.accept());
                executorService.submit(clientWorkingThread);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        closeServer();
     }

    private void setClose() {
        isOpen=false;
        closeServer();
    }

    public  void closeServer() {
        try {
            setClose();
            story.deleteStory();
            Server.serverList.remove();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
