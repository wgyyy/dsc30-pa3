/*
  Name: Gaoying Wang
  PID:  A16131629
 */

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

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
     */
    MessageExchange room;
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
        room = new ChatRoom();
        marina = new PremiumUser("Marina", "Instructor");
        Jack = new StandardUser("Jack","Student");
    }

    /*
      Recap: Assert exception without message
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserThrowsIAE() {
        marina = new PremiumUser("Marina", null);
    }

    /*
      Assert exception with message
  */
    @Test
    public void testPhotoMessageThrowsODE() {

        try {
            PhotoMessage pm = new PhotoMessage(marina, "PA02.zip");
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }


    }

    /*
     * Assert message content without hardcoding the date
     */
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
        String expected="Jack [" + date + "]: Hello";
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

    @Test
    public void testSTUser(){
        try {
        StandardUser test=new StandardUser("test",null);
        fail("Exception not thrown");
    } catch (IllegalArgumentException IAE) {
        assertEquals(null, IAE.getMessage());
    }
    }

    @Test
    public void testFetchMessage() throws OperationDeniedException {
        room.addUser(Jack);
        TextMessage Ttest1=new TextMessage(Jack,"Hello");
        TextMessage Ttest2=new TextMessage(marina,"How are you");
        room.recordMessage(Ttest1);
        String result=Jack.fetchMessage(room);
        String expected="Jack ["+date +"]: Hello"+"\n";
        assertEquals(expected,result);
        room.recordMessage(Ttest2);
        result=Jack.fetchMessage(room);
        expected=expected+"<Premium> Marina ["+date +"]: How are you"+"\n";
        assertEquals(expected,result);
        PhotoMessage Ptest=new PhotoMessage(marina,"sun.jpg");
        room.recordMessage(Ptest);
        result=Jack.fetchMessage(room);
        expected=expected+"This message cannot be fetched because you are not a premium user."+"\n";
        assertEquals(expected,result);
    }

    @Test
    public void TestDisplayName(){
        String actual=Jack.displayName();
        String expected="Jack";
        assertEquals(expected,actual);
        actual=marina.displayName();
        expected="<Premium> Marina";
        assertEquals(expected,actual);
        PremiumUser tom=new PremiumUser("Tom","Instructor");
        actual=tom.displayName();
        expected="<Premium> Tom";
        assertEquals(expected,actual);
    }

    @Test
    public void TestSetTitle(){
        PremiumUser tom=new PremiumUser("Tom","Instructor");
        tom.setCustomTitle("Kid");
        String actual=tom.displayName();
        String expected="<Kid> Tom";
        assertEquals(expected,actual);
        tom.setCustomTitle("Iron man");
        actual=tom.displayName();
        expected="<Iron man> Tom";
        assertEquals(expected,actual);
        tom.setCustomTitle("Cat");
        actual=tom.displayName();
        expected="<Cat> Tom";
        assertEquals(expected,actual);
    }

    @Test
    public void TestCMRIAE(){
        try {
            marina.createModeratedRoom(null);
            fail("Exception not thrown");
        } catch (IllegalArgumentException IAE) {
            assertEquals(null, IAE.getMessage());
        }
    }

    @Test
    public void TestBANUNBAN(){
        ArrayList<User> u=new ArrayList<User>();
        StandardUser tom=new StandardUser("Tom","Instructor");
        u.add(Jack);
        MessageExchange room;
        room= marina.createModeratedRoom(u);
        int actual=room.getUsers().size();
        int expected=2;
        assertEquals(expected,actual);
        marina.banUser((ModeratedRoom) room,Jack);
        actual=room.getUsers().size();
        expected=1;
        assertEquals(expected,actual);
        boolean exp=false;
        boolean act=room.addUser(Jack);
        assertEquals(exp,act);
        marina.unbanUser((ModeratedRoom) room,Jack);
        exp=true;
        act=room.addUser(Jack);
        assertEquals(exp,act);
    }

    @Test
    public void TestSetNum(){
        ArrayList<User> u=new ArrayList<User>();
        StandardUser tom=new StandardUser("Tom","Instructor");
        u.add(Jack);
        MessageExchange room;
        room= marina.createModeratedRoom(u);
        try {
            marina.setNumVisibleLog((ModeratedRoom) room,1);
            fail("Exception not thrown");
        } catch (IllegalArgumentException IAE) {
            assertEquals(null, IAE.getMessage());
        }
        boolean actual=marina.setNumVisibleLog((ModeratedRoom) room,11);
        boolean expected=true;
        assertEquals(expected,actual);
        actual=((ModeratedRoom) room).setNumVisibleLog(Jack,11);
        expected=false;
        assertEquals(expected,actual);
        actual=((ModeratedRoom) room).setNumVisibleLog(marina,15);
        expected=true;
        assertEquals(expected,actual);
    }

    @Test
    public void TestChatroom() throws OperationDeniedException {
        ChatRoom room=new ChatRoom();
        ArrayList<Message> expected=new ArrayList<Message>();
        TextMessage Ttest1=new TextMessage(Jack,"Hello");
        TextMessage Ttest2=new TextMessage(marina,"How are you");
        room.addUser(Jack);
        int expected_int=1;
        int actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.removeUser(Jack,Jack);
        expected_int=0;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.addUser(Jack);
        room.addUser(marina);
        expected_int=2;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.removeUser(Jack,Jack);
        expected_int=1;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.removeUser(marina,marina);
        expected_int=0;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        ArrayList<Message> record_M=new ArrayList<Message>();
        record_M=room.getLog(Jack);
        ArrayList<Message> actual_M=new ArrayList<Message>();
        assertEquals(record_M,actual_M);
        room.recordMessage(Ttest1);
        record_M=room.getLog(Jack);
        actual_M.add(Ttest1);
        assertEquals(record_M,actual_M);
        room.recordMessage(Ttest2);
        actual_M.add(Ttest2);
        assertEquals(record_M,actual_M);
    }

    @Test
    public void TestMR() throws OperationDeniedException {
        PremiumUser tom=new PremiumUser("Tom","Instructor");
        ModeratedRoom room=new ModeratedRoom(marina);
        ArrayList<Message> expected=new ArrayList<Message>();
        TextMessage Ttest1=new TextMessage(Jack,"Hello");
        TextMessage Ttest2=new TextMessage(marina,"How are you");
        room.addUser(Jack);
        int expected_int=2;
        int actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.removeUser(Jack,Jack);
        expected_int=1;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.addUser(Jack);
        room.addUser(tom);
        expected_int=3;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.removeUser(Jack,marina);
        expected_int=3;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        room.removeUser(marina,marina);
        expected_int=3;
        actual_int=room.getUsers().size();
        assertEquals(expected_int,actual_int);
        ArrayList<Message> record_M=new ArrayList<Message>();
        record_M=room.getLog(Jack);
        ArrayList<Message> actual_M=new ArrayList<Message>();
        assertEquals(record_M,actual_M);
        room.recordMessage(Ttest1);
        record_M=room.getLog(Jack);
        actual_M.add(Ttest1);
        assertEquals(record_M,actual_M);
        room.recordMessage(Ttest2);
        actual_M.add(Ttest2);
        assertEquals(record_M,actual_M);

    }

}
