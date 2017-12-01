import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;
import java.util.Date;

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

    JLabel categoryText = new JLabel("Category:");
    quantityText.setFont(new Font("Arial", 1, 12));
    itemPanel.add(categoryText);

    JTextField categoryInput = new JTextField(15);
    quantityInput.setText("Input Negative Number to Remove");
    itemPanel.add(categoryInput);
    
    JButton confirmButton = new JButton("Confirm");
    confirmButton.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e){
      boolean itemPresent = false;
      String sql = "INSERT INTO ITEM(name,num,category) values(?,?,?)";
      try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
       pstmt.setString(1, itemInput.getText());
       pstmt.setDouble(2, Integer.parseInt(quantityInput.getText()));
       pstmt.setString(3, categoryInput.getText());
       pstmt.executeUpdate();
       System.out.println("added successfully");
      } catch (SQLException excep) {
       JOptionPane.showMessageDialog(new JFrame("error"), "ERROR: " +  excep.getMessage());
      }
      catch (Exception exp) {
       JOptionPane.showMessageDialog(new JFrame("error"), "ERROR: " + exp.getMessage());
      }
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
        String sql1 = "INSERT INTO log(time,content) values(?,?)";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql1)) {
         java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
         pstmt.setTimestamp(1, time);
         pstmt.setString(2,currentEmployee.getName() + " added " + quantityInput.getText() + " " + itemInput.getText());
         pstmt.executeUpdate();
         System.out.println("added successfully");
        } catch (SQLException excep) {
         JOptionPane.showMessageDialog(new JFrame("error"), excep.getMessage());
        } catch (Exception excep) {
         JOptionPane.showMessageDialog(new JFrame("error"), excep.getMessage());
        }
       }
       else{
        logs.add(currentEmployee.getName() + " removed " + Math.abs(Integer.parseInt(quantityInput.getText())) + " " + itemInput.getText());
        String sql1 = "INSERT INTO log(time,content) values(?,?)";
        try ( PreparedStatement pstmt = conn.prepareStatement(sql1)) {
         java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
         pstmt.setTimestamp(1, time);
         pstmt.setString(2,currentEmployee.getName() + " removed " + Math.abs(Integer.parseInt(quantityInput.getText())) + " " + itemInput.getText());
         pstmt.executeUpdate();
         System.out.println("added successfully");
        } catch (SQLException excep) {
         System.out.println(excep.getMessage());
        }
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
   public void actionPerformed(ActionEvent e) {
    JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    String sql = "SELECT content,time from log";
    try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
     ResultSet rs = pstmt.executeQuery();
     logPanel.add(new JLabel("Log Record: \n"));
     while (rs.next()) {
      java.util.Date time = rs.getTime("time");
      logPanel.add(new JLabel(rs.getString("content") + " " + time.toString()));
     }
    }
    catch (SQLException e1) {
     System.out.println(e1.getMessage());
    }
    JFrame logFrame = new JFrame("Record of Logs");
    logFrame.setVisible(true);
    logFrame.setSize(300, 500);
    logFrame.getContentPane().add(logPanel);
   }
  });
  employeePanel.add(displayLog);


  JButton search = new JButton("search");
  employeePanel.add(search);
  search.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel searchItemText = new JLabel("Item:");
    searchItemText.setFont(new Font("Arial", 1, 12));
    searchPanel.add(searchItemText);
    JTextField searchInput = new JTextField(15);
    searchPanel.add(searchInput);
    JButton confirmButton = new JButton("Confirm");


    JLabel categoryText = new JLabel("Category:");
    categoryText.setFont(new Font("Arial", 1, 12));
    searchPanel.add(categoryText);

    JTextField categoryInput = new JTextField(15);
    searchPanel.add(categoryInput);
    searchPanel.add(confirmButton);

    JFrame addItemFrame = new JFrame("searching");
    addItemFrame.getContentPane().add(searchPanel);
    addItemFrame.setSize(200, 300);
    addItemFrame.setVisible(true);

    confirmButton.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {

      JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
      String sql;
      if (!searchInput.getText().isEmpty() && !categoryInput.getText().isEmpty()) {
       sql = "select name,sum(num) as count from item where name = ? and category = ?";
       System.out.println(categoryInput.getText().isEmpty());
      }
      else if (searchInput.getText().isEmpty()) {
       sql = "select name,  sum(num) as count from item where category = ? group by name having sum(num)";
      }
      else  {
       sql = "select name,sum(num) as count from item where name = ? ";
      }
      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
       if (!searchInput.getText().isEmpty() && !categoryInput.getText().isEmpty()) {
        pstmt.setString(1, searchInput.getText());
        pstmt.setString(2, categoryInput.getText());
       }
       else if (searchInput.getText().isEmpty()) {
        pstmt.setString(1, categoryInput.getText());
       }
       else  {
        pstmt.setString(1, searchInput.getText());
       }

       ResultSet rs = pstmt.executeQuery();
       resultPanel.add(new JLabel("Search Result: \n"));
       while(rs.next()) {
        resultPanel.add(new JLabel( rs.getString("name") + " " + rs.getInt("count")));
       }
      } catch (SQLException e1) {
       System.out.println(e1.getMessage());
      }
      JFrame resultFrame = new JFrame("Searching result");
      resultFrame.getContentPane().add(resultPanel);
      resultFrame.setVisible(true);
      resultFrame.setSize(200, 300);
     }
    });
   }
  });











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

 /**
  * Connect to a sample database
  */
 public static Connection connect() {
  Connection conn = null;
  try {
   // db parameters
   String url = "jdbc:sqlite:C:/sqlite/Database.db";
   // create a connection to the database
   conn = DriverManager.getConnection(url);
   System.out.println("Connection to SQLite has been established.");

  } catch (SQLException e) {
   System.out.println(e.getMessage());
  }
  return conn;
 }
}
