import java.util.ArrayList;

public class ChatRoom implements MessageExchange {

    /* instance variables */
    private ArrayList<User> users;
    private ArrayList<Message> log;

    public ChatRoom() {
        users=new ArrayList<User>();
        log=new ArrayList<Message>();
    }

    public ArrayList<Message> getLog(User requester) {
        return this.log;
    }

    public boolean addUser(User u) {
        if (this.getUsers().contains(u)){
            return false;
        }else {
            users.add(u);
            u.rooms.add(this);
            return true;
        }
    }


    public boolean removeUser(User requester, User u) {
        if (this.getUsers().contains(u)){
            this.users.remove(u);
            u.quitRoom(this);
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean recordMessage(Message m) {
        log.add(m);
        return true;
    }

}