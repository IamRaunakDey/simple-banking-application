import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SBA extends JFrame {
    private static Map<String, String> userDatabase = new HashMap<>();
    private static Map<String, Double> balanceDatabase = new HashMap<>();

    static {
        // Predefined users
        userDatabase.put("12345", "password1");
        userDatabase.put("67890", "password2");
        userDatabase.put("11111", "password3");
        userDatabase.put("22222", "password4");
        userDatabase.put("33333", "password5");

        // Initial balances
        balanceDatabase.put("12345", 500.0);
        balanceDatabase.put("67890", 1000.0);
        balanceDatabase.put("11111", 750.0);
        balanceDatabase.put("22222", 200.0);
        balanceDatabase.put("33333", 1200.0);
    }

    private String currentUser;
    private double balance;

    private JLabel balanceLabel;
    private JTextField amountField;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton checkBalanceButton;
    private JButton exitButton;

    public SBA() {
        setTitle("Simple Banking Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        showLoginScreen();
    }

    private void showLoginScreen() {
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(50, 30, 150, 30);
        add(accountLabel);

        JTextField accountField = new JTextField();
        accountField.setBounds(200, 30, 150, 30);
        add(accountField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 150, 30);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 70, 150, 30);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 110, 100, 30);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(account, password)) {
                    currentUser = account;
                    balance = balanceDatabase.get(account);
                    removeLoginScreen();
                    showBankingScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid account number or password.");
                }
            }
        });
    }

    private void removeLoginScreen() {
        getContentPane().removeAll();
        repaint();
    }

    private void showBankingScreen() {
        balanceLabel = new JLabel("Balance: $" + balance);
        balanceLabel.setBounds(50, 30, 300, 30);
        add(balanceLabel);

        JLabel amountLabel = new JLabel("Enter amount:");
        amountLabel.setBounds(50, 70, 100, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(150, 70, 150, 30);
        add(amountField);

        depositButton = new JButton("Deposit");
        depositButton.setBounds(50, 110, 100, 30);
        add(depositButton);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(200, 110, 100, 30);
        add(withdrawButton);

        checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(50, 150, 250, 30);
        add(checkBalanceButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(150, 190, 100, 30);
        add(exitButton);

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });

        revalidate();
        repaint();
    }

    private boolean authenticate(String account, String password) {
        return userDatabase.containsKey(account) && userDatabase.get(account).equals(password);
    }

    private void deposit() {
        double amount = getAmount();
        if (amount > 0) {
            balance += amount;
            balanceDatabase.put(currentUser, balance);
            JOptionPane.showMessageDialog(this, "Deposited: $" + amount);
            updateBalanceLabel();
        } else {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    private void withdraw() {
        double amount = getAmount();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            balanceDatabase.put(currentUser, balance);
            JOptionPane.showMessageDialog(this, "Withdrew: $" + amount);
            updateBalanceLabel();
        } else if (amount > balance) {
            JOptionPane.showMessageDialog(this, "Insufficient balance.");
        } else {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
        }
    }

    private void checkBalance() {
        JOptionPane.showMessageDialog(this, "Current Balance: $" + balance);
    }

    private void exitApplication() {
        System.exit(0);
    }

    private double getAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + balance);
    }

    public static void main(String[] args) {
        SBA app = new SBA();
        app.setVisible(true);
    }
}
