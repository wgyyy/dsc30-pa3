public class TextMessage extends Message {

    /* Error message to use in OperationDeniedException */
    private static final String EXCEED_MAX_LENGTH =
            "Your input exceeded the maximum length limit.";

    public TextMessage(User sender, String text)
                        throws OperationDeniedException {
        super(sender);
        if (text.length()>500){
            throw new OperationDeniedException(EXCEED_MAX_LENGTH);
        }
        if (sender==null||text==null) {
            throw new IllegalArgumentException();
        }
        contents=text;
    }

    public String getContents() {
        String result=getSender().displayName()+" "+getDate().toString()+": "+contents;
        return result;
    }

}
