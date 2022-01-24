import java.util.ArrayList;

public abstract class User {

    /* Error message to use in OperationDeniedException */
    protected static final String JOIN_ROOM_FAILED =
            "Failed to join the chat room.";
    protected static final String INVALID_MSG_TYPE =
            "Cannot send this type of message to the specified room.";

    /* instance variables */
    protected String username;
    protected String bio;
    protected ArrayList<MessageExchange> rooms;

    public User(String username, String bio) {
        if (username==null) {
            throw new IllegalArgumentException();
        }
        if (bio==null) {
            throw new IllegalArgumentException();
        }
        this.username=username;
        this.bio=bio;
        this.rooms=new ArrayList<MessageExchange>();
    }

    public void setBio(String newBio) {
        if (newBio==null) {
            throw new IllegalArgumentException();
        }
        this.bio=newBio;
    }

    public String displayBio() {
        return this.bio;
    }

    public void joinRoom(MessageExchange me) throws OperationDeniedException {
        if (rooms.contains(me)){
            throw new OperationDeniedException("JOIN_ROOM_FAILED");
        }else if(me==null){
            throw new IllegalArgumentException();
        }
        boolean result=me.addUser(this);
        if (!result){
            throw new OperationDeniedException("JOIN_ROOM_FAILED");
        }
    }

    public void quitRoom(MessageExchange me) {
        if(me==null){
            throw new IllegalArgumentException();
        }
        me.removeUser(this,this);
    }

    public MessageExchange createChatRoom(ArrayList<User> users) {
        if (users==null){
            throw new IllegalArgumentException();
        }
        ChatRoom new_room=new ChatRoom();
        for (int x=0;x< users.size();x++) {
            try {
                users.get(x).joinRoom(new_room);
            } catch (OperationDeniedException ode) {
                System.out.println(ode.getMessage());
            }
        }
        return new_room;
    }

    public void sendMessage(MessageExchange me, MessageType msgType, String contents) {
        if (me==null) {
            throw new IllegalArgumentException();
        }else if (msgType==null) {
            throw new IllegalArgumentException();
        }else if (contents==null) {
            throw new IllegalArgumentException();
        }
        if (!me.getUsers().contains(this)){
            throw new IllegalArgumentException();
        }
        Message new_msg =null;
        if (msgType==MessageType.TEXT){
            try {
                new_msg=new TextMessage(this,contents);
            } catch (OperationDeniedException e) {
                System.out.println(e.getMessage());
            }
        }else if (msgType==MessageType.PHOTO){
            try {
                new_msg=new PhotoMessage(this,contents);
            } catch (OperationDeniedException e) {
                System.out.println(e.getMessage());
            }
        }
        me.recordMessage(new_msg);

    }

    public abstract String fetchMessage(MessageExchange me);

    public abstract String displayName();
}
