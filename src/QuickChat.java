import java.util.*;

class AccountRegistration {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhone;

    public AccountRegistration(String username, String password, String firstName, String lastName, String cellPhone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
    }

    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        if (password.length() < 8) return false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public boolean checkCellPhoneNumber() {
        return cellPhone.matches("\\+27\\d{1,10}");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        } else if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        } else if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        } else {
            return "User has been registered successfully.";
        }
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}


public class QuickChat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Registration
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

        AccountRegistration account = new AccountRegistration(username, password, firstName, lastName, cellPhone);
        System.out.println(account.registerUser());

        // Login
        System.out.print("Login - Enter username: ");
        String loginUser = sc.nextLine();
        System.out.print("Login - Enter password: ");
        String loginPass = sc.nextLine();

        if (!account.loginUser(loginUser, loginPass)) {
            System.out.println(account.returnLoginStatus(loginUser, loginPass));
            return;
        }

        System.out.println(account.returnLoginStatus(loginUser, loginPass));
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

            if (text.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters.");
                continue;
            }

            Message msg = new Message(id, recipient, text);

            System.out.println("Choose option: 1) Send  2) Store  0) Disregard");
            int option = sc.nextInt();
            sc.nextLine();

            System.out.println(msg.sendMessage(option));
        }

int choice;
do {
    System.out.println("\nMenu:");
    System.out.println("1) Show all sent messages");
    System.out.println("2) Show stored messages");
    System.out.println("3) Show disregarded messages");
    System.out.println("4) Quit");
    choice = sc.nextInt();
    sc.nextLine();

    switch (choice) {
        case 1:
            System.out.println(Message.printMessages(Message.getSentMessages()));
            break;
        case 2:
            System.out.println(Message.printMessages(Message.getStoredMessages()));
            break;
        case 3:
            System.out.println(Message.printMessages(Message.getDisregardedMessages()));
            break;
        case 4:
            System.out.println("Exiting QuickChat...");
            break;
        default:
            System.out.println("Invalid choice.");
    }
} while (choice != 4);

System.out.println("Total messages sent: " + Message.returnTotalMessages());


    }
}



