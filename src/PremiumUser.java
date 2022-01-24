import java.util.ArrayList;

public class PremiumUser extends User {

    /* instance variable */
    private String customTitle;

    public PremiumUser(String username, String bio) {
        super(username, bio);
        this.customTitle="Premium";
    }

    public String fetchMessage(MessageExchange me) {
        if (me==null) {
            throw new IllegalArgumentException();
        }else if (!me.getUsers().contains(this)) {
            throw new IllegalArgumentException();
        }
        String result="";
        ArrayList<Message> log_list=me.getLog(this);
        for (int x=0;x<log_list.size();x++){
            result = result + log_list.get(x).getContents()+"\n";
        }
        return result;
    }

    public String displayName() {
        String return_name="<"+this.customTitle+"> "+this.username;
        return return_name;
    }

    public void setCustomTitle(String newTitle) {
        this.customTitle=newTitle;
    }

    public MessageExchange createModeratedRoom(ArrayList<User> users) {
        if (users==null){
            throw new IllegalArgumentException();
        }
        ModeratedRoom new_room=new ModeratedRoom(this);
        for (int x=0;x< users.size();x++) {
            try {
                users.get(x).joinRoom(new_room);
            } catch (OperationDeniedException e) {
                System.out.println(e.getMessage());
            }
        }
        return new_room;
    }

    public boolean banUser(ModeratedRoom room, User u) {
        if (room==null){
            throw new IllegalArgumentException();
        } else if (u==null){
            throw new IllegalArgumentException();
        }else {
            return room.banUser(this,u);
        }
    }

    public boolean unbanUser(ModeratedRoom room, User u) {
        if (room==null){
            throw new IllegalArgumentException();
        } else if (u==null){
            throw new IllegalArgumentException();
        }else {
            return room.unbanUser(this,u);
        }
    }

    public boolean setNumVisibleLog(ModeratedRoom room, int newNum) {
        if(room==null){
            throw new IllegalArgumentException();
        } else if (newNum<10){
            throw new IllegalArgumentException();
        } else {
            return room.setNumVisibleLog(this,newNum);
        }
    }

}
