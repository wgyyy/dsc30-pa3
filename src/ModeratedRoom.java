import java.util.ArrayList;
import java.util.List;

public class ModeratedRoom implements MessageExchange {
    /* instance variables */
    private ArrayList<User> users, banned;
    private ArrayList<Message> log;
    private User moderator;
    private int numVisibleLog;

    public ModeratedRoom(PremiumUser moderator) {
        users=new ArrayList<User>();
        banned=new ArrayList<User>();
        log=new ArrayList<Message>();
        this.moderator=moderator;
        numVisibleLog=Integer.MAX_VALUE;
        users.add(moderator);
    }

    public ArrayList<Message> getLog(User requester) {
        ArrayList<Message> result=new ArrayList<Message>();
        if (requester==moderator){
            return log;
        }else {
            if (log.size()<=numVisibleLog){
                return log;
            }else{
                for (int x=0;x<numVisibleLog;x++){
                    result.add(log.get(x));
                }
                return result;
            }
        }
    }

    public boolean addUser(User u) {
        if (banned.contains(u)){
            return false;
        }else if(users.contains(u)){
            return false;
        }else{
            users.add(u);
            u.rooms.add(this);
            return true;
        }
    }

    public boolean removeUser(User requester, User u) {
        if (users.contains(u)){
            if (requester==moderator){
                if (u==moderator){
                    return false;
                }else{
                    users.remove(u);
                    u.quitRoom(this);
                    return true;
                }
            }else if (requester==u){
                users.remove(u);
                u.quitRoom(this);
                return true;
            }else{
                return false;
            }
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

    public boolean banUser(User requester, User u) {
        if (requester==moderator){
            if (u==moderator){
                return false;
            }else{
                if (users.contains(u)){
                    users.remove(u);
                    banned.add(u);
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }

    public boolean unbanUser(User requester, User u) {
        if (requester==moderator){
            if (banned.contains(u)){
                banned.remove(u);
                return true;
            }else{
                return true;
            }
        }else {
            return false;
        }
    }

    public boolean setNumVisibleLog(User requester, int newNum) {
        if (requester==moderator){
            numVisibleLog=newNum;
            return true;
        }else{
            return false;
        }
    }
}
