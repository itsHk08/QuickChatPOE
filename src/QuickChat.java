import java.util.*;
import prog.poe.Login;

public class QuickChat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Registration details
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Enter cell phone number (+27...): ");
        String cellPhone = sc.nextLine();

        // Create Login 
        Login login = new Login(username, password, firstName, lastName, cellPhone);

        // Login attempt
        System.out.print("Login - Enter username: ");
        String loginUser = sc.nextLine();
        System.out.print("Login - Enter password: ");
        String loginPass = sc.nextLine();

        if (!login.loginUser(loginUser, loginPass)) {
            System.out.println(login.returnLoginStatus(loginUser, loginPass));
            return;
        }

        System.out.println(login.returnLoginStatus(loginUser, loginPass));
        System.out.println("Welcome to QuickChat!");

        // Messaging
        System.out.print("How many messages would you like to enter? ");
        int numMessages = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < numMessages; i++) {
            System.out.print("Enter Message ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Recipient (+27...): ");
            String recipient = sc.nextLine();
            System.out.print("Enter Message (max 250 chars): ");
            String text = sc.nextLine();

            Message msg = new Message(id, recipient, text);

            System.out.println("Choose option: 1) Send  2) Store  0) Disregard");
            int option = sc.nextInt();
            sc.nextLine();

            System.out.println(msg.sendMessage(option));
        }

        // Menu loop
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1) Show all sent messages");
            System.out.println("2) Show stored messages");
            System.out.println("3) Show disregarded messages");
            System.out.println("4) Show all message IDs");
            System.out.println("5) Show all message hashes");
            System.out.println("6) Show longest stored message");
            System.out.println("7) Search by Message ID");
            System.out.println("8) Search stored messages by recipient");
            System.out.println("9) Delete a stored message by hash");
            System.out.println("10) Display report");
            System.out.println("11) Quit");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println(Message.getSentMessages());
                    break;
                case 2:
                    System.out.println(Message.getStoredMessages());
                    break;
                case 3:
                    System.out.println(Message.getDisregardedMessages());
                    break;
                case 4:
                    System.out.println(Message.getMessageIDs());
                    break;
                case 5:
                    System.out.println(Message.getMessageHashes());
                    break;
                case 6:
                    System.out.println(Message.getLongestStoredMessage());
                    break;
                case 7:
                    System.out.print("Enter Message ID to search: ");
                    String id = sc.nextLine();
                    System.out.println(Message.searchByMessageID(id));
                    break;
                case 8:
                    System.out.print("Enter recipient to search: ");
                    String rec = sc.nextLine();
                    System.out.println(Message.searchByRecipient(rec));
                    break;
                case 9:
                    System.out.print("Enter hash to delete: ");
                    String hash = sc.nextLine();
                    System.out.println(Message.deleteByHash(hash));
                    break;
                case 10:
                    System.out.println(Message.displayReport());
                    break;
                case 11:
                    System.out.println("Exiting QuickChat...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 11);

        sc.close();
    }
}