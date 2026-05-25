
package prog.poe;

public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhone;

    // Constructor
    public Login(String username, String password, String firstName, String lastName, String cellPhone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
    }

    // 1. Username validation
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // 2. Password complexity validation
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

    // 3. Cell phone validation 
    public boolean checkCellPhoneNumber() {
        return cellPhone.matches("\\+27\\d{1,10}");
    }

    // 4. Registration messaging
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is incorrectly formatted.";
        } else if (!checkPasswordComplexity()) {
            return "Password does not meet complexity requirements.";
        } else if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        } else {
            return "User has been registered successfully.";
        }
    }

    // 5. Login verification
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    // 6. Login status messaging
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

