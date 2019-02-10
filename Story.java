import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

class Story {

    private LinkedList<String> story = new LinkedList<>();

    public void addMessageIntoStory(String message) {
        // если сообщений больше 10, удаляем первое и добавляем новое
        // иначе просто добавить
        if (story.size() >= 10) {
            story.removeFirst();
            story.add(message);
        } else {
            story.add(message);
        }
    }

    public void printStory(BufferedWriter writer) {
        if(story.size() > 0) {
            try {
                writer.write("Предыдущие сообщения" + "\n");
                for (String previousMessage : story) {
                    writer.write(previousMessage + "\n");
                }
                writer.write("/...." + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void deleteStory(){
        if(story.size() > 0) {
            story.clear();
        }
    }
}