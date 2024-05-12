package command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

abstract class Command {
    public Editor editor;
    private String backup;

    Command(Editor editor) {
        this.editor = editor;
    }

    void backup() {
        backup = editor.textField.getText();
    }

    public void undo() {
        editor.textField.setText(backup);
    }

    public abstract boolean execute();

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

class CopyCommand extends Command {
    public CopyCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        editor.clipboard = editor.textField.getSelectedText();
        return false;
    }
}

class PasteCommand extends Command {
    public PasteCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        if (editor.clipboard == null || editor.clipboard.isEmpty()) return false;

        backup();
        editor.textField.insert(editor.clipboard, editor.textField.getCaretPosition());
        return true;
    }
}

class CutCommand extends Command {

    public CutCommand(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        if (editor.textField.getSelectedText().isEmpty()) return false;

        backup();
        String source = editor.textField.getText();
        editor.clipboard = editor.textField.getSelectedText();
        editor.textField.setText(cutString(source));
        return true;
    }

    private String cutString(String source) {
        String start = source.substring(0, editor.textField.getSelectionStart());
        String end = source.substring(editor.textField.getSelectionEnd());
        return start + end;
    }
}

class CommandHistory {
    private Stack<Command> history = new Stack<>();

    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return history.pop();
    }

    public boolean isEmpty() { return history.isEmpty(); }
}

class Editor {
    public JTextArea textField;
    public String clipboard;
    private CommandHistory history = new CommandHistory();

    public void init() {
        JFrame frame = new JFrame("Text editor (type & use buttons, Luke!)");
        JPanel content = new JPanel();
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        textField = new JTextArea();
        textField.setLineWrap(true);
        content.add(textField);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton ctrlC = new JButton("Ctrl+C");
        JButton ctrlX = new JButton("Ctrl+X");
        JButton ctrlV = new JButton("Ctrl+V");
        JButton ctrlZ = new JButton("Ctrl+Z");
        Editor editor = this;
        ctrlC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(new CopyCommand(editor));
            }
        });
        ctrlX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(new CutCommand(editor));
            }
        });
        ctrlV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(new PasteCommand(editor));
            }
        });
        ctrlZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        buttons.add(ctrlC);
        buttons.add(ctrlX);
        buttons.add(ctrlV);
        buttons.add(ctrlZ);
        content.add(buttons);
        frame.setSize(450, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void executeCommand(Command command) {
        if (command.execute()) {
            history.push(command);
        }
    }

    private void undo() {
        if (history.isEmpty()) return;

        Command command = history.pop();
        if (command != null) {
            command.undo();
        }
    }
}

/**
 *  1. If you see a set of related classes that represent specific actions (such as “Copy”, “Cut”, “Send”, “Print”, etc.)
 *  2. The commands may implement the relevant actions on their own or delegate the work to separate objects—that will be the receivers
 *
 */


/**
 * The original Command pattern separates the Command objects from the Receiver objects because the responsibility of the two sets are different.
 *
 * Receivers own the business logic of the application. These types should exist outside the pattern. In a practical case, Receivers were probably already present in the code base, before commands.
 *
 * For example, thinking to a bank application, a receiver can be represented by the following type:
 *
 * public class Account {
 *     private final double balance;
 *     // Construct omissis
 *     public Account deposit(double amount) {
 *         // Deposit code
 *     }
 *     public Account withdraw(double amount) {
 *         // Withdraw code
 *     }
 * }
 * One of the aims of the command design pattern is that of give a unified, homogeneous and standard way to execute operations on a set of objects (i.e. the receivers). They have not to care how to perform the real business logic. This will limit the reusability of the code implementing business logic.
 *
 * For this reason, the Commands implementation has to forward the information to the Receiver. It follows an example.
 *
 * public class DepositCommand implements Command {
 *     private final Account account;
 *     // An instance of Command should reprenset only a single request
 *     private final double amount;
 *     public DepositCommand(Account account, double amount) {
 *         this.account = account;
 *         this.amount = amount;
 *     }
 *
 *     public void execute() {
 *         account.deposit(amount);
 *     }
 *
 *     // Omissis..
 * }
 * In conclusion, imho, the statement in the accepted answer is not correct.
 */

public class Main {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.init();
    }
}

