package mediator;

// The mediator interface declares a method used by components
// to notify the mediator about various events. The mediator may
// react to these events and pass the execution to other
// components.
interface Mediator {
    void notify(Component sender, String event);
}

// The concrete mediator class. The intertwined web of
// connections between individual components has been untangled
// and moved into the mediator.
class AuthenticationDialog implements Mediator {
    private String title;
    private Checkbox loginOrRegisterChkBx;
    private Textbox loginUsername, loginPassword;
    private Textbox registrationUsername, registrationPassword, registrationEmail;
    private Button okBtn, cancelBtn;

    private Checkbox registerToPhoneNotificationsChkBx;
    private Textbox phoneNumberTxtField;

    public AuthenticationDialog() {
        // Create all component objects by passing the current
        // mediator into their constructors to establish links.
        this.loginOrRegisterChkBx = new Checkbox();
        this.loginUsername = new Textbox();
        this.loginPassword = new Textbox();
        this.registrationUsername = new Textbox();
        this.registrationPassword = new Textbox();
        this.registrationEmail = new Textbox();
        this.okBtn = new Button();
        this.cancelBtn = new Button();
        this.registerToPhoneNotificationsChkBx = new Checkbox();
        this.phoneNumberTxtField = new Textbox();

        this.loginOrRegisterChkBx.setMediator(this);
        this.loginUsername.setMediator(this);
        this.loginPassword.setMediator(this);
        this.registrationUsername.setMediator(this);
        this.registrationPassword.setMediator(this);
        this.registrationEmail.setMediator(this);
        this.okBtn.setMediator(this);
        this.cancelBtn.setMediator(this);
        this.registerToPhoneNotificationsChkBx.setMediator(this);
        this.phoneNumberTxtField.setMediator(this);
    }

    // When something happens with a component, it notifies the
    // mediator. Upon receiving a notification, the mediator may
    // do something on its own or pass the request to another
    // component.
    public void notify(Component sender, String event) {
        if (sender == loginOrRegisterChkBx && event.equals("check")) {
            if (loginOrRegisterChkBx.isChecked()) {
                title = "Log in";
                // 1. Show login form components.
                // 2. Hide registration form components.
            } else {
                title = "Register";
                // 1. Show registration form components.
                // 2. Hide login form components
            }
        }

        if (sender == okBtn && event.equals("click")) {
            if (loginOrRegisterChkBx.isChecked()) {
                // Try to find a user using login credentials.
                boolean found = true;
                if (!found) {
                    // Show an error message above the login
                    // field.
                } else {
                    System.out.println("User Found with the given credentials. Logging in... ");
                }
            } else {
                // 1. Create a user account using data from the
                // registration fields.
                // 2. Log that user in.
                // ...
                System.out.println("No Registered User Found. Signing up...");
            }
        }

        if (sender == registerToPhoneNotificationsChkBx && event.equals("check")) {
            if (registerToPhoneNotificationsChkBx.isChecked()) {
                // Show phone number field
                System.out.println("Phone number field is now visible.");
            } else {
                // Hide phone number field
                System.out.println("Phone number field is now hidden.");
            }
        }
    }

    public void simulate() {
        // Example interaction
        this.registerToPhoneNotificationsChkBx.check();
        this.registerToPhoneNotificationsChkBx.check();
        this.registerToPhoneNotificationsChkBx.check();


        this.loginOrRegisterChkBx.check();
        this.okBtn.click();
    }

}

// Components communicate with a mediator using the mediator
// interface. Thanks to that, you can use the same components in
// other contexts by linking them with different mediator
// objects.
interface Component {
    void setMediator(Mediator mediator);
    String getName();
}

// Concrete components don't talk to each other. They have only
// one communication channel, which is sending notifications to
// the mediator.
class Button implements Component {
    private Mediator mediator;

    public void click() {
        mediator.notify(this, "click");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Button";
    }
}

class Textbox implements Component {
    private Mediator mediator;

    public void keypress() {
        mediator.notify(this, "keypress");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "TextBox";
    }
}

class Checkbox implements Component {
    private Mediator mediator;
    private boolean isChecked;

    public void check() {
        this.isChecked = !isChecked;
        mediator.notify(this, "check");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public boolean isChecked(){
        return this.isChecked;
    }

    @Override
    public String getName() {
        return "CheckBox";
    }
}

public class Main {
    public static void main(String[] args) {
        Checkbox loginOrRegisterChkBx = new Checkbox();
        Textbox loginUsername = new Textbox();
        Textbox loginPassword = new Textbox();
        Textbox registrationUsername = new Textbox();
        Textbox registrationPassword = new Textbox();
        Textbox registrationEmail = new Textbox();
        Button okBtn = new Button();
        Button cancelBtn = new Button();

        // mediator
        AuthenticationDialog authenticationDialog = new AuthenticationDialog();
        authenticationDialog.simulate();
    }
}
