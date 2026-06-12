import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class QuickChatTests {

    @Test
    public void testSentMessagesArray() {
        Message msg1 = new Message("ID01", "+27834557896", "Did you get the cake?");
        msg1.sendMessage(1);

        Message msg4 = new Message("0838884567", "+27838884567", "It is dinner time !");
        msg4.sendMessage(1);

        assertEquals(List.of("Did you get the cake?", "It is dinner time !"), Message.getSentMessages());
    }

    @Test
    public void testLongestStoredMessage() {
        Message msg2 = new Message("ID02", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.sendMessage(2);

        String longest = Message.getLongestStoredMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchByMessageID() {
        Message msg4 = new Message("0838884567", "+27838884567", "It is dinner time !");
        msg4.sendMessage(1);

        String result = Message.searchByMessageID("0838884567");
        assertTrue(result.contains("It is dinner time !"));
    }

    @Test
    public void testSearchByRecipient() {
        Message msg2 = new Message("ID02", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.sendMessage(2);

        Message msg5 = new Message("ID05", "+27838884567", "Ok, I am leaving without you.");
        msg5.sendMessage(2);

        List<String> results = Message.searchByRecipient("+27838884567");
        assertEquals(2, results.size());
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteByHash() {
        Message msg2 = new Message("ID02", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.sendMessage(2);

        String hash = Message.getMessageHashes().get(0);
        String result = Message.deleteByHash(hash);

        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
    }

    @Test
    public void testDisplayReport() {
        Message msg1 = new Message("ID01", "+27834557896", "Did you get the cake?");
        msg1.sendMessage(1);

        String report = Message.displayReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Message ID"));
    }
}