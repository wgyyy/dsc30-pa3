import java.security.cert.Extension;
import java.util.Objects;

public class PhotoMessage extends Message {

    /* Error message to use in OperationDeniedException */
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";

    /* instance variable */
    private String extension;

    public PhotoMessage(User sender, String photoSource)
                        throws OperationDeniedException {
        super(sender);
        String lastdigits="";
        if (sender==null|| photoSource==null) {
            throw new IllegalArgumentException();
        }
        if(sender.getClass().equals(StandardUser.class)){
            throw new OperationDeniedException();
        }
        if (photoSource.length()>3){
            lastdigits=photoSource.substring(photoSource.length()-5);
            if (!(lastdigits.contains(".jpg") || lastdigits.contains(".jpeg")
                    || lastdigits.contains(".gif") || lastdigits.contains(".png")
                    || lastdigits.contains(".tif") || lastdigits.contains(".tiff")
                    || lastdigits.contains(".raw"))){
                throw new OperationDeniedException(INVALID_INPUT);
            }
        }else{
            lastdigits=photoSource.substring(photoSource.length()-3);
            if (!(lastdigits.contains(".jpg")
                    || lastdigits.contains(".gif") ||
                    lastdigits.contains(".png")
                    || lastdigits.contains(".tif") ||
                    lastdigits.contains(".raw"))){
                throw new OperationDeniedException(INVALID_INPUT);
            }
        }
        contents=photoSource;
    }

    public String getContents() {
        String result=getSender().displayName()+" "+getDate().toString()+": Picture at "+contents;
        return result;
    }

    public String getExtension() {
        extension="";
        if (contents.charAt(contents.length()-4)=='.') {
            for (int x = contents.length() - 3; x < contents.length(); x++) {
                extension = extension + contents.charAt(x);
            }
        }else if (contents.charAt(contents.length()-5)=='.'){
            for (int x = contents.length() - 4; x < contents.length(); x++) {
                extension = extension + contents.charAt(x);
            }
        }
        return extension;
    }

}
