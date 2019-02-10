import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ClientWorkingThread implements Runnable {
    Socket userSocket;
    BufferedReader in; // поток чтения из сокета
    BufferedWriter out; // поток завписи в сокет

    public ClientWorkingThread(Socket userSocket) {
        this.userSocket = userSocket;
        try {
            in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(userSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server.story.printStory(out);

    }


    @Override
    public void run() {
        String nickName;
        String message;
        try {
            // первое сообщение отправленное сюда - это никнейм
            nickName = in.readLine();
                out.write(nickName + "\n");
                out.flush(); // выталкивания оставшихся данныхе сли такие есть, и очистки потока для дальнейших нужд
            try {
                while (true) {
                    message = in.readLine();
                    if (message.equals("Выход")) {
                        this.killOneClient(); // харакири
                        break; // если пришла пустая строка - выходим из цикла прослушки
                    }
                    System.out.println("Сообщение: " + message);
                    Server.story.addMessageIntoStory(message);
                    for (ClientWorkingThread clientsList : Server.serverList) {
                        clientsList.sendMessage(message); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                    }
                }
            } catch (NullPointerException ignored) {
            }//бросать эксебшены просто так? да без бз


        } catch (IOException e) {
            this.killOneClient();
        }
    }

    private void sendMessage(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private void killOneClient() {
            try {
                in.close();
                out.close();
                userSocket.close();
                for (ClientWorkingThread clientsList  : Server.serverList) {
                    if (clientsList.equals(this))
                    Server.serverList.remove(this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
}

