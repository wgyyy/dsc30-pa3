import java.util.ArrayList;
import java.util.List;

public class StandardUser extends User {

    /* Message to append when fetching non-text message */
    private static final String FETCH_DENIED_MSG =
            "This message cannot be fetched because you are not a premium user.";

    public StandardUser(String username, String bio) {
        super(username,bio);
    }

    public String fetchMessage(MessageExchange me) {
        if (me==null) {
            throw new IllegalArgumentException();
        }else if (!me.getUsers().contains(this)) {
            throw new IllegalArgumentException();
        }
        String result="";
        ArrayList<Message> log_list=me.getLog(this);
        if (log_list.size()<100){
            for (int x=0;x<log_list.size();x++){
                if (log_list.get(x).getClass()==TextMessage.class) {
                    result = result + log_list.get(x).getContents()+"\n";
                }else{
                    result = result + FETCH_DENIED_MSG+"\n";
                }
            }
        }else{
            for (int x=0;x<100;x++){
                if (log_list.get(x).getClass()==TextMessage.class) {
                    result = result + log_list.get(x).getContents()+"\n";
                }else{
                    result = result + FETCH_DENIED_MSG+"\n";
                }
            }
        }
        return result;
    }

    public String displayName() {
        String name=this.username;
        return name;
    }
}
