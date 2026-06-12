import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Message {
    private String messageID;
    private String recipient;
    private String messageText;

    private static List<String> sentMessages = new ArrayList<>();
    private static List<String> storedMessages = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();
    private static List<String> messageIDs = new ArrayList<>();
    private static List<String> messageHashes = new ArrayList<>();

    public Message(String messageID, String recipient, String messageText) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    public String checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() <= 12) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        String[] words = messageText.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String idPart = (messageID.length() >= 2) ? messageID.substring(0, 2) : messageID;
        String hash = (idPart + ":" + messageIDs.size() + ":" + firstWord + lastWord).toUpperCase();
        messageHashes.add(hash);
        return hash;
    }

    public String sendMessage(int option) {
        if (messageText.length() > 250) {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }

        switch (option) {
            case 1: // Sent
                sentMessages.add(messageText);
                messageIDs.add(messageID);
                storeMessage("messages.json");
                return "Message successfully sent";
            case 0: // Disregard
                disregardedMessages.add(messageText);
                messageIDs.add(messageID);
                storeMessage("disregarded.json");
                return "Message disregarded";
            case 2: // Store
                storedMessages.add(messageText);
                messageIDs.add(messageID);
                storeMessage("stored.json");
                return "Message successfully stored";
            default:
                return "Invalid option";
        }
    }

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
    public static List<String> getSentMessages() { return sentMessages; }
    public static List<String> getStoredMessages() { return storedMessages; }
    public static List<String> getDisregardedMessages() { return disregardedMessages; }
    public static List<String> getMessageIDs() { return messageIDs; }
    public static List<String> getMessageHashes() { return messageHashes; }

    // Extra features for screenshots
    public static String getLongestStoredMessage() {
        return storedMessages.stream().max(Comparator.comparingInt(String::length)).orElse("No stored messages.");
    }

    public static String searchByMessageID(String id) {
        int index = messageIDs.indexOf(id);
        if (index != -1) {
            return "Message: " + (sentMessages.size() > index ? sentMessages.get(index) : storedMessages.get(index));
        }
        return "Message ID not found.";
    }

    public static List<String> searchByRecipient(String recipient) {
        List<String> results = new ArrayList<>();
        for (String msg : storedMessages) {
            if (msg.contains(recipient)) results.add(msg);
        }
        return results;
    }

    public static String deleteByHash(String hash) {
        int index = messageHashes.indexOf(hash);
        if (index != -1 && index < storedMessages.size()) {
            String removed = storedMessages.remove(index);
            return "Message: \"" + removed + "\" successfully deleted.";
        }
        return "Message hash not found.";
    }

    public static String displayReport() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageIDs.size(); i++) {
            sb.append("Message Hash: ").append(messageHashes.get(i)).append("\n");
            sb.append("Message ID: ").append(messageIDs.get(i)).append("\n");
            if (i < sentMessages.size()) sb.append("Sent: ").append(sentMessages.get(i)).append("\n");
            if (i < storedMessages.size()) sb.append("Stored: ").append(storedMessages.get(i)).append("\n");
            if (i < disregardedMessages.size()) sb.append("Disregarded: ").append(disregardedMessages.get(i)).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}