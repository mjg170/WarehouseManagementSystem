import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;
public class Demo {
	
	private final static Employee[] EMPLOYEES = {new Employee("Wang", "Mark Wang", 100, true), new Employee("Goldberg", "Mark Goldberg", 150, true),new Employee("Zhao", "Fengxiang", 100, true)};
	private final static Manager MANAGER = new Manager("Podgurski", "Andy Podgurski", 1000, true);
	private static LinkedList<Item> items = new LinkedList<Item>();
	private static LinkedList<String> logs = new LinkedList<String>();
	private static User currentEmployee = null;

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Login Window");
		JFrame employeeWindow = new JFrame("Employee Screen");
		employeeWindow.setVisible(false);
		employeeWindow.setSize(400, 500);
		
		JPanel employeePanel = new JPanel(new FlowLayout());
		
		JButton addItem = new JButton("Add/Remove Item");
		addItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				
				JLabel itemText = new JLabel("Item:");
				itemText.setFont(new Font("Arial", 1, 12));
				itemPanel.add(itemText);
				
				JTextField itemInput = new JTextField(15);
				itemPanel.add(itemInput);
				
				JLabel quantityText = new JLabel("Quantity:");
				quantityText.setFont(new Font("Arial", 1, 12));
				itemPanel.add(quantityText);
				
				JTextField quantityInput = new JTextField(15);
				quantityInput.setText("Input Negative Number to Remove");
				itemPanel.add(quantityInput);
				
				JButton confirmButton = new JButton("Confirm");
				confirmButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						boolean itemPresent = false;
						if(itemInput.getText() != null && quantityInput.getText() != null){
							for(Item item: items){
								if(item.getName().equals(itemInput.getText())){
									itemPresent = true;
									if(item.modifyQuantity(Integer.parseInt(quantityInput.getText())) == -1){
										JOptionPane.showMessageDialog(new JFrame("error"), "There do not exist enough items");
										return;
									}
								}
							}
							if(!itemPresent && Integer.parseInt(quantityInput.getText()) > 0){
								items.add(new Item(itemInput.getText(), Integer.parseInt(quantityInput.getText())));
							}
							else if(!itemPresent && Integer.parseInt(quantityInput.getText()) < 0){
								JOptionPane.showMessageDialog(new JFrame("error"), "There do not exist enough items");
								return;
							}
							if(Integer.parseInt(quantityInput.getText()) > 0){
								logs.add(currentEmployee.getName() + " added " + quantityInput.getText() + " " + itemInput.getText());
							}
							else{
								logs.add(currentEmployee.getName() + " removed " + Math.abs(Integer.parseInt(quantityInput.getText())) + " " + itemInput.getText());
							}
						}
						itemInput.setText("");
						quantityInput.setText("Input Negative Number to Remove");
					}
				});
				itemPanel.add(confirmButton);
				JFrame addItemFrame = new JFrame("Add/Remove Item");
				addItemFrame.getContentPane().add(itemPanel);
				addItemFrame.setSize(200, 300);
				addItemFrame.setVisible(true);
			}
		});
		employeePanel.add(addItem);
		
		Button logoutButton = new Button("Log Out");
		logoutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				employeeWindow.setVisible(false);
				currentEmployee = null;
				window.setVisible(true);
			}
		});
		employeePanel.add(logoutButton);
		
		Button displayLog = new Button("Display Log");
		displayLog.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				for(String log: logs){
					logPanel.add(new JLabel(log));
				}
				JFrame logFrame = new JFrame("Record of Logs");
				logFrame.setVisible(true);
				logFrame.setSize(300, 500);
				logFrame.getContentPane().add(logPanel);
			}
		});
		employeePanel.add(displayLog);
		
		//Buttons to decide a what user it is
		JButton loginButton;
		
		//New panel to choose
		JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		loginButton = new JButton("Login");
		
		JLabel nameText = new JLabel("Name:");
		nameText.setFont(new Font("Arial", 1, 12));
		loginPanel.add(nameText);
		
		JTextField usernameInput = new JTextField(20);
		loginPanel.add(usernameInput);
		
		JLabel passwordText = new JLabel("Password:");
		passwordText.setFont(new Font("Arial", 1, 12));
		loginPanel.add(passwordText);
		
		JTextField passwordInput = new JTextField(18);
		loginPanel.add(passwordInput);
		
		loginPanel.add(loginButton);
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(Employee employee: EMPLOYEES){
					if(employee.getName().equals(usernameInput.getText()) && employee.getPassword().equals(passwordInput.getText())){
						currentEmployee = employee;
						window.setVisible(false);
						employeeWindow.getContentPane().add(employeePanel);
						passwordInput.setText("");
						usernameInput.setText("");
						employeeWindow.setVisible(true);
						
					}
				}
				if(MANAGER.getName().equals(usernameInput.getText()) && MANAGER.getPassword().equals(passwordInput.getText())){
					currentEmployee = MANAGER;
					window.setVisible(false);
					employeeWindow.getContentPane().add(employeePanel);
					passwordInput.setText("");
					usernameInput.setText("");
					employeeWindow.setVisible(true);
				}
				if(window.isVisible()){
					passwordInput.setText("Either Password or Username incorrect");
				}
			}
			
		});
		
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.getContentPane().add(loginPanel);
        window.setSize(300, 125);
        window.setVisible(true);

        

	}
}
