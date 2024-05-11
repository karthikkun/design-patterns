package state;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Account
 * Debit Card
 * AtmContext
 *
 */



class Account {
    private double balance;
    private String userName; // for now only having name of the account's owner, we can also have a seperate
    // model User and have several properties like name, dob, address etc.

    public Account(double balance, String userName) {
        this.balance = balance;
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

class Card {
    Account account;

    public Card(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}


interface State {
    void handleFlow();
}

enum StateEnum {
    WELCOME,
    INSERT_CARD,
    TRANSACTION,
    REMOVE_CARD,
    THANK_YOU;
}

class WelcomeState implements State {
    @Override
    public void handleFlow() {
        System.out.println("Hello! Welcome to XYZ atm");
    }
}

class InsertCardState implements State {
    @Override
    public void handleFlow() {
        System.out.println("Insert your card");
    }
}

class TransactionState implements State {
    Card card;
    Scanner sc;

    public TransactionState(Card card, Scanner sc) {
        this.card = card;
        this.sc = sc;
    }

    @Override
    public void handleFlow() {
        System.out.println("Press 1 to withdraw or 2 to deposit");
        int keyPress = sc.nextInt();
        System.out.println("Enter amount");
        double amount = sc.nextDouble();
        double accountBalance = card.getAccount().getBalance();
        if (keyPress == 1) {
            if (accountBalance < amount)
                System.out.println("Insufficient funds!");
            else
                card.getAccount().setBalance(accountBalance - amount);
        } else if (keyPress == 2) {
            card.getAccount().setBalance(accountBalance + amount);
        }
        System.out.println("Your current balance : " + card.getAccount().getBalance());
    }
}

class RemoveCardState implements State {
    @Override
    public void handleFlow() {
        System.out.println("Please remove your card");
    }
}


class EndState implements State {
    @Override
    public void handleFlow() {
        System.out.println("Thank you!");
    }
}

class AtmContext {
    Map<Class<? extends State>, State> cache; // to store the state objects for a particular user,
    // no need to create states everytime the user is in that step
    StateEnum[] allStates;
    int currentState, totalStates;
    State state;
    Card card;
    Scanner sc;

    // create a new ATM context
    public AtmContext(Card card, Scanner sc) {
        this.card = card;
        cache = new HashMap<>();
        allStates = StateEnum.values();
        currentState = -1;
        totalStates = allStates.length;
        this.sc = sc;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void execute(boolean cancel) {
        if (cancel) {
            currentState = totalStates - 1;
        } else {
            // set the step to next one
            currentState++;
            // if we are at the last state, set it to 0 i.e welcome state
            if (currentState == totalStates) {
                currentState = 0;
            }
        }
        handleFlow(allStates[currentState]);
    }

    public void handleFlow(StateEnum eStateEnum) { // passing the current state we are in and executing respective
        // state's handle flow
        switch (eStateEnum) {
            case WELCOME:
                if (!cache.containsKey(WelcomeState.class)) {
                    cache.put(WelcomeState.class, new WelcomeState());
                }
                setState(cache.get(WelcomeState.class));
                break;

            case INSERT_CARD:
                if (!cache.containsKey(InsertCardState.class)) {
                    cache.put(InsertCardState.class, new InsertCardState());
                }
                setState(cache.get(InsertCardState.class));
                break;

            case TRANSACTION:
                if (!cache.containsKey(TransactionState.class)) {
                    cache.put(TransactionState.class, new TransactionState(card, sc));
                }
                setState(cache.get(TransactionState.class));
                break;

            case REMOVE_CARD:
                if (!cache.containsKey(RemoveCardState.class)) {
                    cache.put(RemoveCardState.class, new RemoveCardState());
                }
                setState(cache.get(RemoveCardState.class));
                break;

            case THANK_YOU:
                if (!cache.containsKey(EndState.class)) {
                    cache.put(EndState.class, new EndState());
                }
                setState(cache.get(EndState.class));
                break;
            default:
                throw new IllegalArgumentException("Unknown state: " + eStateEnum);
        }
        this.state.handleFlow();
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account account = new Account(1000.50, "DemoUser");
        Card card = new Card(account);
        AtmContext atmContext = new AtmContext(card, sc);

        while (true) {
            System.out.println("Press 1 to continue or any other number to cancel \n");
            int inputValue = sc.nextInt();
            if (inputValue == 1) {
                atmContext.execute(false);
            } else {
                atmContext.execute(true);
                break;
            }
        }
        sc.close();
    }
}
