import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Message {
    private String messageID;
    private String recipient;
    private String messageText;
    private static int totalMessages = 0;
    private static List<Message> sentMessages = new ArrayList<>();
    private static List<Message> storedMessages = new ArrayList<>();
    private static List<Message> disregardedMessages = new ArrayList<>();

    public Message(String messageID, String recipient, String messageText) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    public String createMessageHash() {
        String[] words = messageText.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String idPart = (messageID.length() >= 2) ? messageID.substring(0, 2) : messageID;
        return (idPart + ":" + totalMessages + ":" + firstWord + lastWord).toUpperCase();
    }

    public String sendMessage(int option) {
        switch (option) {
            case 1: // Send
                totalMessages++;
                sentMessages.add(this);
                storeMessage("messages.json");
                return "Message successfully sent";
            case 0: // Disregard
                disregardedMessages.add(this);
                storeMessage("disregarded.json");
                return "Message disregarded";
            case 2: // Store
                storedMessages.add(this);
                storeMessage("stored.json");
                return "Message successfully stored";
            default:
                return "Invalid option";
        }
    }

    public static String printMessages(List<Message> list) {
        if (list.isEmpty()) return "No messages in this category.";
        StringBuilder sb = new StringBuilder();
        for (Message msg : list) {
            sb.append("Message ID: ").append(msg.messageID).append("\n");
            sb.append("Message Hash: ").append(msg.createMessageHash()).append("\n");
            sb.append("Recipient: ").append(msg.recipient).append("\n");
            sb.append("Message: ").append(msg.messageText).append("\n\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    // Store message in JSON file (relative path)
    public void storeMessage(String filename) {
        try (FileWriter file = new FileWriter(filename, true)) {
            String json = "{"
                    + "\"MessageID\":\"" + messageID + "\","
                    + "\"MessageHash\":\"" + createMessageHash() + "\","
                    + "\"Recipient\":\"" + recipient + "\","
                    + "\"Message\":\"" + messageText + "\""
                    + "}\n";
            file.write(json);
        } catch (IOException e) {
            System.out.println("Error writing to JSON file: " + e.getMessage());
        }
    }

    // Accessors
    public static List<Message> getSentMessages() { return sentMessages; }
    public static List<Message> getStoredMessages() { return storedMessages; }
    public static List<Message> getDisregardedMessages() { return disregardedMessages; }
}




