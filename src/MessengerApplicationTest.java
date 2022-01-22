/*
  Name: Gaoying Wang
  PID:  A16131629
 */

import java.time.LocalDate;
import org.junit.*;

import javax.xml.soap.Text;

import static org.junit.Assert.*;

/**
 * Messenger Application Test
 * @author Gaoying Wang
 * @since  ${2022-01-21}
 */
public class MessengerApplicationTest {

    /*
      Messages defined in starter code. Remember to copy and paste these strings to the
      test file if you cannot directly access them. DO NOT change the original declaration
      to public.
     */
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";

    /*
      Global test variables. Initialize them in @Before method.

    MessageExchange room;     */
    PremiumUser marina;
    StandardUser Jack;


    /*
      The date used in Message and its subclasses. You can directly
      call this in your test methods.
     */
    LocalDate date = LocalDate.now();

    /*
     * Setup
     */

    @Before
    public void setup() {
        /*
        room = new ChatRoom();
         */
        marina = new PremiumUser("Marina", "Instructor");
        Jack = new StandardUser("Jack","Student");
    }

    /*
      Recap: Assert exception without message

    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserThrowsIAE() {
        marina = new PremiumUser("Marina", null);
    }
     */
    /*
      Assert exception with message

    @Test
    public void testPhotoMessageThrowsODE() {
        try {
            PhotoMessage pm = new PhotoMessage(marina, "PA02.zip");
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }
  */
    /*
     * Assert message content without hardcoding the date

    @Test
    public void testTextMessageGetContents() {
        try {
            TextMessage tm = new TextMessage(marina, "A sample text message.");

            // concatenating the current date when running the test
            String expected = "<Premium> Marina [" + date + "]: A sample text message.";
            assertEquals(expected, tm.getContents());
        } catch (OperationDeniedException ode) {
            fail("ODE should not be thrown");
        }
    }
  */
    @Test
    public void testMessageGetSender() throws OperationDeniedException {
            Message Mtest=new TextMessage(Jack,"Hello");
            User expected=Jack;
            User return_sender= Mtest.getSender();
            assertEquals(expected,return_sender);
    }
    @Test
    public void testMessageGetContent() throws OperationDeniedException {
        TextMessage Ttest=new TextMessage(Jack,"Hello");
        String expected="Placeholder [" + date + "]: Hello";
        String actual=Ttest.getContents();
        assertEquals(expected,actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIAE() throws OperationDeniedException{
        Message Mtest=new TextMessage(Jack,null);
    }

    @Test(expected = OperationDeniedException.class)
    public void testODE1() throws OperationDeniedException{
        PhotoMessage Test1=new PhotoMessage(Jack,"Hello.jpg");
    }

    @Test(expected = OperationDeniedException.class)
    public void testODE2() throws OperationDeniedException{
        PhotoMessage Test1=new PhotoMessage(marina,"Hello.txt");
    }

    @Test
    public void testExtension() throws OperationDeniedException{
        PhotoMessage Test1=new PhotoMessage(marina,"Hello.jpeg");
        String expected="jpeg";
        String actual=Test1.getExtension();
        assertEquals(expected,actual);
        PhotoMessage Test2=new PhotoMessage(marina,"Hello.jpg");
        expected="jpg";
        actual=Test2.getExtension();
        assertEquals(expected,actual);
        PhotoMessage Test3=new PhotoMessage(marina,"Hello.gif");
        expected="gif";
        actual=Test3.getExtension();
        assertEquals(expected,actual);
    }


}
