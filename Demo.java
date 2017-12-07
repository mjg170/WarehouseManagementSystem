
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.Date;

//main program
public class Demo {



	private Object aNull;

	//main method
	public static void main(String[] args) {

		
		SaveObject so = new SaveObject();
		Connection conn = connect();
		blockchain[] bc = new blockchain[1];
		blockchain bc1 = new blockchain();
		try {
			bc[0] = so.getBlockchain(conn);
		} catch (Exception e) {
		}
		LinkedList<transaction> transactions = new LinkedList<transaction>();

		//creates window to log in
		JFrame loginWindow = new JFrame("Login Window");
		loginWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JFrame employeeWindow = new JFrame("Employee Screen");
		employeeWindow.setVisible(false);
		employeeWindow.setSize(400, 500);
		String[] choices = { "Staff","Manager", "Administrator"};
		final JComboBox<String> cb = new JComboBox<String>(choices);

		final String[] role = {String.valueOf(cb.getSelectedItem())}; //get the role
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				role[0] = String.valueOf(cb.getSelectedItem());
			}
		});



		//window for an employee user
		JPanel employeePanel = new JPanel(new FlowLayout());

		JButton addItem = new JButton("Add/Remove Item");
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		employeePanel.add(addItem);

		//button to log out
		Button logoutButton = new Button("Log Out");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		employeePanel.add(logoutButton);

		//displays the log for the user
		Button displayLog = new Button("Display Log");
		displayLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		employeePanel.add(displayLog);

		//brings up search menu
		JButton search = new JButton("search");
		employeePanel.add(search);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//the actual search window
				JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				//a label for the textbox
				JLabel searchItemText = new JLabel("Item:");
				searchItemText.setFont(new Font("Arial", 1, 12));
				searchPanel.add(searchItemText);
				//textbox to put item to be searched
				JTextField searchInput = new JTextField(15);
				searchPanel.add(searchInput);
				//button to tell the program to start searching
				JButton confirmButton = new JButton("Confirm");

				//label to detail what the category is
				JLabel categoryText = new JLabel("Category:");
				categoryText.setFont(new Font("Arial", 1, 12));
				searchPanel.add(categoryText);

				//textbox to search by category
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

						//new frame for search results
						JFrame resultFrame = new JFrame("Searching result");
						resultFrame.getContentPane().add(resultPanel);
						resultFrame.setVisible(true);
						resultFrame.setSize(200, 300);
					}
				});
			}
		});

		// Buttons to decide a what user it is
		JButton loginButton;

		// New panel to choose
		JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		//button to log in
		loginButton = new JButton("Login");

		//identifies adjacent text box
		JLabel nameText = new JLabel("Name:");
		nameText.setFont(new Font("Arial", 1, 12));
		loginPanel.add(nameText);

		//textbox for username
		JTextField usernameInput = new JTextField(20);
		loginPanel.add(usernameInput);

		//identifies adjacent text box
		JLabel passwordText = new JLabel("Password:");
		passwordText.setFont(new Font("Arial", 1, 12));
		loginPanel.add(passwordText);

		//textbox for password
		JTextField passwordInput = new JTextField(18);
		loginPanel.add(passwordInput);

		loginPanel.add(cb);

		loginPanel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					bc[0].addBlock(transactions.toArray(new transaction[transactions.size()]));
					so.saveObject(conn,bc[0]);
					//bc = get blockchain from server
				} catch (NoSuchAlgorithmException e1) {
					//e1.printStackTrace();
				} catch (Exception e1) {
					//e1.printStackTrace();
				}



				if (role[0].equals("Staff")) {
					String sql = "SELECT name FROM staff where password = ? AND name = ?";
					try (PreparedStatement stmt = conn.prepareStatement(sql)) {
						stmt.setLong(1, Long.parseLong(passwordInput.getText()));
						stmt.setString(2, usernameInput.getText());
						ResultSet rs = stmt.executeQuery();
						if (rs.next()) { // if query is found in database
							loginStaff(usernameInput.getText(), conn);
						} else {
							popup(loginWindow, "Wrong Password Or Account not exists");
						}
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					} catch (Exception e1) {
						popup(loginWindow,e1.toString());
					}
				} else if (role[0].equals("Manager")) {
					String sql = "SELECT mid,name FROM manager where password = ? AND name = ?";
					try (PreparedStatement stmt = conn.prepareStatement(sql)) {
						stmt.setLong(1, Long.parseLong(passwordInput.getText()));
						stmt.setString(2, usernameInput.getText());
						ResultSet rs = stmt.executeQuery();
						if (rs.next()) { // if query is found in database
							loginManager(rs.getInt("mid"), usernameInput.getText(), conn);
						} else {
							popup(loginWindow, "Wrong Password Or Account not exists");
						}
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					} catch (Exception e1) {
						popup(loginWindow,e1.toString());
					}
					
				} else  {
					String sql = "SELECT name FROM administrator where password = ? AND name = ?";
					try (PreparedStatement stmt = conn.prepareStatement(sql)) {
						stmt.setLong(1, Long.parseLong(passwordInput.getText()));
						stmt.setString(2, usernameInput.getText());
						ResultSet rs = stmt.executeQuery();
						if (rs.next()) { // if query is found in database
							loginAdmin(usernameInput.getText(), conn);
						} else {
							popup(loginWindow, "Wrong Password Or Account not exists");
						}
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					} catch (Exception e1) {
						popup(loginWindow,e1.toString());
					}
				}
			}

		});

		loginWindow.setResizable(false);
		loginWindow.getContentPane().add(loginPanel);
		loginWindow.setSize(300, 125);
		loginWindow.setVisible(true);
	}

	//login for administrator
	private static void loginAdmin(String name, Connection conn) {
		//window for admin users
		JFrame adminWindow = new JFrame("Admin");
		adminWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//panel for window
		JPanel adminPanel = new JPanel();
		//button to open manage area menu
		JButton manageAreaButtom = new JButton("Manage Area");
		manageAreaButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//window to manage area
				JFrame areaManageWindow = new JFrame("Manage Area");
				areaManageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				//panel in window
				JPanel areaManagePanel = new JPanel();
				//button to add a new map
				JButton newMapButtom = new JButton("New Map");
				areaManagePanel.add(newMapButtom);
				newMapButtom.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//window if adding new map
						JFrame uploadMapWindow = new JFrame("Upload");
						uploadMapWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						//panel for window
						JPanel uploadPanel = new JPanel();
						JFileChooser fc = new JFileChooser();
						uploadPanel.add(fc);
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"Images", "jpg", "gif","png");
						fc.setFileFilter(filter);
						fc.setCurrentDirectory(new File("C:/Users/boss/Dropbox/FALL 2017/SOFTWARE ENG/src"));
						int returnVal = fc.showOpenDialog(uploadMapWindow);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							try {
								String sql = "INSERT INTO map(location,name) VALUES(?,?)";
								PreparedStatement pstmt = conn.prepareStatement(sql);
								pstmt.setString(1,fc.getSelectedFile().getPath());
								pstmt.setString(2,fc.getSelectedFile().getName().replaceAll(".png",""));
								pstmt.executeUpdate();
							} catch (SQLException e3) {
								System.out.println(e3.getMessage());
							}
							uploadMapWindow.setVisible(false);
							popup(areaManageWindow,"Upload successfully");
						} else
							uploadMapWindow.setVisible(false);
						uploadMapWindow.getContentPane().add(uploadPanel);
						uploadMapWindow.setSize(600,400);
					}
				});


				areaManageWindow.getContentPane().add(areaManagePanel);
				areaManageWindow.setSize(400,600);
				areaManageWindow.setVisible(true);


			}
		});
		DefaultTableModel areaModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable areaTable = new JTable();
		areaTable.setModel(areaModel);
		JScrollPane jsp = new JScrollPane(areaTable);
		jsp.setPreferredSize(new Dimension(60,500));
		adminPanel.add(jsp);
		areaModel.addColumn("Area");
		JLabel imageLabel = new JLabel(new ImageIcon());
		adminPanel.add(imageLabel);
		String sql = "select name from map";
		ArrayList<String> nameList = new ArrayList<>();
		JButton viewAreaButtom = new JButton("View Area");
		adminPanel.add(viewAreaButtom);
		JButton viewLogButtom = new JButton("View Log");
		adminPanel.add(viewLogButtom);
		String[] file = new String[1];
		viewLogButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLog("Administrator",name,conn);
			}
		});
		viewAreaButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewAreaItemManger(String.valueOf(areaTable.getValueAt(areaTable.getSelectedRow(),0)),name,conn,file[0]);
			}
		});
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		areaTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String sql = "select location from map where name = ?";
				BufferedImage image = null;
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, String.valueOf(areaTable.getValueAt(areaTable.getSelectedRow(),0)));
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						file[0] = rs.getString("location");
						image = ImageIO.read(new File(file[0]));

					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
				catch (IOException e2) {
					System.out.println(e2.getMessage());
				}
				imageLabel.setIcon(new ImageIcon(image));
			}
		});

//put everything to the JTable
		for (int i = 0; i < nameList.size(); i++) {
			areaModel.addRow(new Object[]{nameList.get(i)});
		}

		JButton manageButtom = new JButton("Manage People");
		adminPanel.add(manageButtom);
		manageButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageAdmin(name,conn);
			}
		});

		String sql1 = "select count(*) as count from (SELECT name,sum(num) as quantity,category,warningnum from item  group by name having sum(num) ) where quantity < warningnum";
		int alertCount = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			alertCount = rs.getInt("count");
		} catch (SQLException e1) {
			System.out.println("error" + e1.getMessage());
		}

		JButton alertButtom = new JButton(alertCount + " low quantity alert");
		adminPanel.add(alertButtom);
		alertButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alert(null,conn);
			}
		});

		adminPanel.add(manageAreaButtom,BorderLayout.WEST);
		adminWindow.getContentPane().add(adminPanel);
		adminWindow.setSize(600,800);
		adminWindow.setVisible(true);


	}

	private static void loginStaff(String name, Connection conn) {
		JFrame staffWindow = new JFrame("Staff");
		staffWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel staffPanel = new JPanel();
		DefaultTableModel areaModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable areaTable = new JTable();
		areaTable.setModel(areaModel);
		JScrollPane jsp = new JScrollPane(areaTable);
		jsp.setPreferredSize(new Dimension(60,500));
		staffPanel.add(jsp);
		areaModel.addColumn("Area");
		JLabel imageLabel = new JLabel(new ImageIcon());
		staffPanel.add(imageLabel);
		String sql = "select name from map";
		ArrayList<String> nameList = new ArrayList<>();
		//button to get more details on area
		JButton viewAreaButtom = new JButton("View Area");
		staffPanel.add(viewAreaButtom);
		//button to bring up log
		JButton viewLogButtom = new JButton("View Log");
		staffPanel.add(viewLogButtom);
		String[] file = new String[1];
		viewLogButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLog("Staff",name,conn);
			}
		});
		viewAreaButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewAreaItemManger(String.valueOf(areaTable.getValueAt(areaTable.getSelectedRow(),0)),name,conn,file[0]);
			}
		});
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		areaTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String sql = "select location from map where name = ?";
				//map iamge
				BufferedImage image = null;
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, String.valueOf(areaTable.getValueAt(areaTable.getSelectedRow(),0)));
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						file[0] = rs.getString("location");
						image = ImageIO.read(new File(file[0]));

					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
				catch (IOException e2) {
					System.out.println(e2.getMessage());
				}
				imageLabel.setIcon(new ImageIcon(image));
			}
		});

//put everything to the JTable
		for (int i = 0; i < nameList.size(); i++) {
			areaModel.addRow(new Object[]{nameList.get(i)});
		}
		String sql1 = "select count(*) as count from (SELECT name,sum(num) as quantity,category,warningnum from item  group by name having sum(num) ) where quantity < warningnum";
		int alertCount = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			alertCount = rs.getInt("count");
		} catch (SQLException e1) {
			System.out.println("error" + e1.getMessage());
		}

		JButton alertButtom = new JButton(alertCount + " low quantity alert");
		staffPanel.add(alertButtom);
		alertButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alert(null,conn);
			}
		});

		staffWindow.getContentPane().add(staffPanel);
		staffWindow.setSize(600,800);
		staffWindow.setVisible(true);


	}

	//window for manager
	public static void loginManager(int mid, String name,Connection conn) {
		//frame for manager
		JFrame managerWindow = new JFrame("Manager");
		managerWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//panel for frame
		JPanel managerPanel = new JPanel();
		//brings up menu to manage areas
		JButton manageAreaButtom = new JButton("Manage Area");
		manageAreaButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//window to manage areas
				JFrame areaManageWindow = new JFrame("Manage Area");
				areaManageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				//panel to go into frame
				JPanel areaManagePanel = new JPanel();
				//button to add a new map
				JButton newMapButtom = new JButton("New Map");

				areaManagePanel.add(newMapButtom);
				newMapButtom.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//window for uploading maps
						JFrame uploadMapWindow = new JFrame("Upload");
						uploadMapWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						//panel for window
						JPanel uploadPanel = new JPanel();
						JFileChooser fc = new JFileChooser();
						uploadPanel.add(fc);
						//makes sure image of acceptable file type
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"Images", "jpg", "gif","png");
						fc.setFileFilter(filter);
						fc.setCurrentDirectory(new File("C:/Users/boss/Dropbox/FALL 2017/SOFTWARE ENG/src"));
						int returnVal = fc.showOpenDialog(uploadMapWindow);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							try {
								String sql = "INSERT INTO map(location) VALUES(?,?)";
								PreparedStatement pstmt = conn.prepareStatement(sql);
								pstmt.setString(2,fc.getSelectedFile().getName().replaceAll(".png",""));
								pstmt.setString(1,fc.getSelectedFile().getPath());
								pstmt.executeUpdate();
							} catch (SQLException e3) {
								System.out.println(e3.getMessage());
							}
							uploadMapWindow.setVisible(false);
							popup(areaManageWindow,"Upload successfully");
						} else
							uploadMapWindow.setVisible(false);
						uploadMapWindow.getContentPane().add(uploadPanel);
						uploadMapWindow.setSize(600,400);
					}
				});

				areaManageWindow.getContentPane().add(areaManagePanel);
				areaManageWindow.setSize(400,600);
				areaManageWindow.setVisible(true);


			}
		});
		DefaultTableModel areaModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable areaTable = new JTable();
		areaTable.setModel(areaModel);
		JScrollPane jsp = new JScrollPane(areaTable);
		jsp.setPreferredSize(new Dimension(60,500));
		managerPanel.add(jsp);
		areaModel.addColumn("Area");
		JLabel imageLabel = new JLabel(new ImageIcon());
		managerPanel.add(imageLabel);
		String sql = "select name from map";
		//list of map names
		ArrayList<String> nameList = new ArrayList<>();
		//button to get information on area
		JButton viewAreaButtom = new JButton("View Area");
		managerPanel.add(viewAreaButtom);
		//button to open window and see log
		JButton viewLogButtom = new JButton("View Log");
		managerPanel.add(viewLogButtom);
		String[] file = new String[1];
		viewLogButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLog("Manger",name,conn);
            }
        });
		viewAreaButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewAreaItemManger(String.valueOf(areaTable.getValueAt(areaTable.getSelectedRow(),0)),name,conn,file[0]);
			}
		});
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		areaTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String sql = "select location from map where name = ?";
                BufferedImage image = null;
                try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, String.valueOf(areaTable.getValueAt(areaTable.getSelectedRow(),0)));
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
					    file[0] = rs.getString("location");
                        image = ImageIO.read(new File(file[0]));

					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
					}
					catch (IOException e2) {
						System.out.println(e2.getMessage());
					}
			imageLabel.setIcon(new ImageIcon(image));
			}
		});

		//put everything to the JTable
		for (int i = 0; i < nameList.size(); i++) {
			areaModel.addRow(new Object[]{nameList.get(i)});
		}

		//brings up window to alter data of users
		JButton manageButtom = new JButton("Manage People");
		managerPanel.add(manageButtom);
		manageButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manageManager(name,conn);
			}
		});


		String sql1 = "select count(*) as count from (SELECT name,sum(num) as quantity,category,warningnum from item  group by name having sum(num) ) where quantity < warningnum";
		int alertCount = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			alertCount = rs.getInt("count");
		} catch (SQLException e1) {
			System.out.println("error" + e1.getMessage());
		}

		JButton alertButtom = new JButton(alertCount + " low quantity alert");
		managerPanel.add(alertButtom);
		alertButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alert(null,conn);
			}
		});



		managerPanel.add(manageAreaButtom,BorderLayout.WEST);
		managerWindow.getContentPane().add(managerPanel);
		managerWindow.setSize(600,800);
		managerWindow.setVisible(true);

	}

	//shwos message box with input text
	public static void popup(JFrame frame,String message) {
		JOptionPane.showMessageDialog(frame,message);
	}

	public static byte[] readFile(String file) {
		ByteArrayOutputStream bos = null;
		try {
			File f = new File(file);
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			bos = new ByteArrayOutputStream();
			for (int len; (len = fis.read(buffer)) != -1;) {
				bos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e2) {
			System.err.println(e2.getMessage());
		}
		return bos != null ? bos.toByteArray() : null;
	}

	//manage items in an area
	public static void viewAreaItemManger(String mapname,String name, Connection conn,String filename) {
		//window
		JFrame viewAreaWindow = new JFrame("View Area's Item");
		//panel for window
		JPanel viewAreaPanel = new JPanel();
		viewAreaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewAreaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		DefaultTableModel itemModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable itemTable = new JTable();
		itemTable.setModel(itemModel);
		viewAreaPanel.add(new JScrollPane(itemTable));
		itemModel.addColumn("Name");
		itemModel.addColumn("quantity");
		itemModel.addColumn("Category");
		itemModel.addColumn("Warning Num");
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<Integer> quantityList = new ArrayList<Integer>();
		ArrayList<String> categoryList = new ArrayList<String>();
		ArrayList<Integer> warningnumList = new ArrayList<Integer>();
		//button to add or remove item
		JButton addItemButtom = new JButton("Add/Remove");
		//label explaning text field
		JLabel searchLabel = new JLabel("Search");
		viewAreaPanel.add(searchLabel);
		//textbox to put in name of item
		JTextField searchInput = new JTextField(10);
		viewAreaPanel.add(searchInput);
		//button to begin search
		JButton searchButtom = new JButton("Search");
		viewAreaPanel.add(searchButtom);
		searchButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchItem(mapname,searchInput.getText(),conn,itemModel);
            }
        });
		viewAreaPanel.add(addItemButtom);
		//look at location of item
		JButton manageLocationButtom = new JButton("View Location");
		viewAreaPanel.add(manageLocationButtom);
		manageLocationButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show(String.valueOf(itemTable.getValueAt(itemTable.getSelectedRow(),0)),mapname,filename,conn);
            }
        });
		//change location of item
		JButton editLocationButtom = new JButton("Edit Location");
		viewAreaPanel.add(editLocationButtom);
		editLocationButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLocation(String.valueOf(itemTable.getValueAt(itemTable.getSelectedRow(),0)),mapname,filename,conn);
            }
        });
		addItemButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItem(mapname,"Manager,",name,conn,itemModel);
			}
		});
		String sql = "SELECT name,sum(num) as quantity,category,warningnum from item where mapname = ? group by name having sum(num)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, mapname);
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
				quantityList.add(rs.getInt("quantity"));
				categoryList.add(rs.getString("category"));
				warningnumList.add(rs.getInt("warningnum"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//put everything to the JTable
		for (int i = 0; i < categoryList.size(); i++) {
			itemModel.addRow(new Object[]{nameList.get(i),quantityList.get(i),categoryList.get(i),warningnumList.get(i)});
		}
		viewAreaWindow.getContentPane().add(viewAreaPanel);
		viewAreaWindow.setSize(500,800);
		viewAreaWindow.setVisible(true);

	}

	public static void addItem(String areaname,String role, String name, Connection conn,DefaultTableModel model) {
		//window if adding item
		JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//label for textbox
		JLabel itemText = new JLabel("Item:");
		itemText.setFont(new Font("Arial", 1, 12));
		itemPanel.add(itemText);

		//input name of item
		JTextField itemInput = new JTextField(15);
		itemPanel.add(itemInput);

		//label for textbox
		JLabel quantityText = new JLabel("Quantity:");
		quantityText.setFont(new Font("Arial", 1, 12));
		itemPanel.add(quantityText);

		//input for amount of item
		JTextField quantityInput = new JTextField(15);
		itemPanel.add(quantityInput);

		//label for textbox
		JLabel categoryText = new JLabel("Category:");
		quantityText.setFont(new Font("Arial", 1, 12));
		itemPanel.add(categoryText);

		//imput for category of item
		JTextField categoryInput = new JTextField(15);
		itemPanel.add(categoryInput);

		//label for textbox
		JLabel warningText = new JLabel("Warning Number:");
		warningText.setFont(new Font("Arial", 1, 12));
		itemPanel.add(warningText);

		JTextField warningInput = new JTextField(15);
		itemPanel.add(warningInput);

		//button to create item from input
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean itemPresent = false;
				String sql = "SELECT name,sum(num) as quantity, from item where mapname = ? and name = ? group by name having sum(num)";
				boolean isOverflow = false;
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1,areaname);
					pstmt.setString(2,itemInput.getText());
					ResultSet rs = pstmt.executeQuery();
					if (!rs.next()) {
						//do nothing
					} else {
						if (Integer.parseInt(quantityInput.getText()) + rs.getInt("quantity") < 0)
							isOverflow = true;
					}
				} catch (SQLException e3) {
					System.out.println(e3.getMessage());
				}
				sql = "INSERT INTO ITEM(name,num,category,mapname,warningNum) values(?,?,?,?,?)";
				if (!isOverflow) {
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, itemInput.getText().toLowerCase());
					pstmt.setDouble(2, Integer.parseInt(quantityInput.getText()));
					pstmt.setString(3, categoryInput.getText().toLowerCase());
					pstmt.setString(4,areaname);
					pstmt.setInt(5,Integer.parseInt(warningInput.getText()));
					pstmt.executeUpdate();
					System.out.println("added successfully");
					sql = "SELECT name,sum(num) as quantity,category,warningnum from item where mapname = ? group by name having sum(num)";
					ArrayList<String> nameList = new ArrayList<String>();
					ArrayList<Integer> quantityList = new ArrayList<Integer>();
					ArrayList<String> categoryList = new ArrayList<String>();
					ArrayList<Integer> warningnumList = new ArrayList<Integer>();
					try (PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
						pstmt1.setString(1, areaname);
						ResultSet rs = pstmt1.executeQuery();
						//remove everything
						for (int i = model.getRowCount() - 1; i > -1; i--) {
							model.removeRow(i);
						}

						// loop through the result set
						while (rs.next()) {
							nameList.add(rs.getString("name"));
							quantityList.add(rs.getInt("quantity"));
							categoryList.add(rs.getString("category"));
							warningnumList.add(rs.getInt("warningnum"));
						}
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					}
					sql = "UPDATE item set warningnum = ? WHERE name = ?";
					try (PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
						pstmt1.setInt(1, Integer.parseInt(warningInput.getText()));
						pstmt1.setString(2, itemInput.getText());
						pstmt1.executeUpdate();
					}
					catch (SQLException e2) {
						System.out.println(e2.getMessage());
					}
					//put everything to the JTable
					for (int i = 0; i < categoryList.size(); i++) {
						model.addRow(new Object[]{nameList.get(i),quantityList.get(i),categoryList.get(i),warningnumList.get(i)});
					}

                    if (Integer.parseInt(quantityInput.getText()) > 0) {
                       // for (Item item : items) {
                        //    if (item.getName().equals(itemInput.getText())) {
                                //transactions.add(new transaction(item, Integer.parseInt(quantityInput.getText()), LocalDateTime.now(), currentEmployee));
                         //   }
                     //   }
                        String sql1 = "INSERT INTO log(time,content) values(?,?)";
                        try (PreparedStatement stmt = conn.prepareStatement(sql1)) {
                            java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
                            stmt.setTimestamp(1, time);
                            stmt.setString(2,  name+ " added " + quantityInput.getText()
                                    + " " + itemInput.getText().toLowerCase());
                            stmt.executeUpdate();
                            System.out.println("added successfully");
                        } catch (SQLException excep) {
                            JOptionPane.showMessageDialog(new JFrame("error"), excep.getMessage() + " Error.");
                        } catch (Exception excep) {
                            JOptionPane.showMessageDialog(new JFrame("error"), excep.getMessage()+ " Error..");
                        }
                    } else {
                      //  for (Item item : items) {
                        //    if (item.getName().equals(itemInput.getText())) {
                                //	transactions.add(new transaction(item, Integer.parseInt(quantityInput.getText()), LocalDateTime.now(), currentEmployee));
                         //   }
                    //    }
                        String sql1 = "INSERT INTO log(time,content) values(?,?)";
                        try (PreparedStatement stmt = conn.prepareStatement(sql1)) {
                            java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
                            stmt.setTimestamp(1, time);
                            stmt.setString(2,
                                    name + " removed "
                                            + Math.abs(Integer.parseInt(quantityInput.getText())) + " "
                                            + itemInput.getText().toLowerCase());
                            stmt.executeUpdate();
                            System.out.println("added successfully");
                        } catch (SQLException excep) {
                            System.out.println(excep.getMessage());
                        }
                    }
				} catch (SQLException excep) {
					JOptionPane.showMessageDialog(new JFrame("error"), "ERROR: " + excep.getMessage());
				} catch (Exception exp) {
					JOptionPane.showMessageDialog(new JFrame("error"), "ERROR: " + exp.getMessage());
				}
				/*if (itemInput.getText() != null && quantityInput.getText() != null) {
					for (Item item : items) {
						if (item.getName().equals(itemInput.getText())) {
							itemPresent = true;
							if (item.modifyQuantity(Integer.parseInt(quantityInput.getText())) == -1) {
								JOptionPane.showMessageDialog(new JFrame("error"),
										"There do not exist enough items");
								return;
							}
						}
					}
					if (!itemPresent && Integer.parseInt(quantityInput.getText()) > 0) {
						items.add(new Item(itemInput.getText(), Integer.parseInt(quantityInput.getText())));
					} else if (!itemPresent && Integer.parseInt(quantityInput.getText()) < 0) {
						JOptionPane.showMessageDialog(new JFrame("error"), "There do not exist enough items");
						return;
					}

					}*/
				}
				itemInput.setText("");
				quantityInput.setText("");
			}
		});
		itemPanel.add(confirmButton);
		JFrame addItemFrame = new JFrame("Add/Remove Item");
		addItemFrame.getContentPane().add(itemPanel);
		addItemFrame.setSize(200, 300);
		addItemFrame.setVisible(true);
	}

	//search for item
	public static void searchItem(String areaname, String name, Connection conn, DefaultTableModel model) {
		//list of items
        ArrayList<String> nameList = new ArrayList<String>();
        //list of the amount for each item
        ArrayList<Integer> quantityList = new ArrayList<Integer>();
        //list of category of each item
        ArrayList<String> categoryList = new ArrayList<String>();
        //list of warning numbers for each item
        ArrayList<Integer> warningnumList = new ArrayList<Integer>();
        String sql;
        if (name.isEmpty()) {
            sql = "SELECT name,sum(num) as quantity,category,warningnum from item where mapname = ? group by name having sum(num)";
        } else {
            sql = "SELECT name,sum(num) as quantity,category,warningnum from item where mapname = ? and name LIKE ?  group by name having sum(num)";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (name.isEmpty()) {
                pstmt.setString(1, areaname);
            } else {
                pstmt.setString(1, areaname);
                pstmt.setString(2,"%" + name + "%");
            }



            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                nameList.add(rs.getString("name"));
                quantityList.add(rs.getInt("quantity"));
                categoryList.add(rs.getString("category"));
                warningnumList.add(rs.getInt("warningnum"));
            }
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        //remove everything
        for (int i = model.getRowCount() - 1; i > -1; i--) {
            model.removeRow(i);
        }
        //put everything to the JTable
        for (int i = 0; i < categoryList.size(); i++) {
            model.addRow(new Object[]{nameList.get(i),quantityList.get(i),categoryList.get(i),warningnumList.get(i)});
        }

    }

	//open window that has log info
    public static void showLog(String role, String name, Connection conn) {
    	//panel to go into window
        JPanel logPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String sql = "SELECT content,time from log";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            logPanel.add(new JLabel("Log Record: \n"));
            while (rs.next()) {
                java.util.Date time = rs.getTime("time");
                logPanel.add(new JLabel(rs.getString("content") + " " + time.toString()));
            }
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        //actual window
        JFrame logFrame = new JFrame("Record of Logs");
        logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logFrame.setVisible(true);
        logFrame.setSize(300, 500);
        logFrame.getContentPane().add(logPanel);
    }

    //shows map for item
    public static void show(String name,String areaname,String file,Connection conn) {
	    String sql = "select xloc,yloc from item where mapname = ? and name = ?";
	    //x coordiante
	    int xloc = 0;
	    //y coordinate
	    int yloc = 0;
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1,areaname);
	        pstmt.setString(2,name);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            xloc = rs.getInt("xloc");
	            yloc = rs.getInt("yloc");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	    //temporary item
        Item item = new Item("temp",1,"temp",0,0.0,0.0,0,null,yloc,xloc,10);
        //map to display
	    Map map = new Map("temp",0,0,file);
	    map.show(item);
    }

    //change location of item
    public static void setLocation(String name,String areaname,String file,Connection conn) {
        String sql = "select xloc,yloc from item where mapname = ? and name = ?";
        //termporary item
        Item item = new Item("temp",1,"temp",0,0.0,0.0,0,null,0,0,10);
        //map to show user
        Map map = new Map("temp",0,0,file);
        map.set(item,conn,areaname,name);
    }

    public static void manageAdmin(String name,Connection conn){
		JFrame manageWindow = new JFrame("Manage People");
		manageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel managePanel = new JPanel();
		JTable peopleTable = new JTable();
		DefaultTableModel peopleModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		peopleTable.setModel(peopleModel);
		managePanel.add(new JScrollPane(peopleTable));
		peopleModel.addColumn("Name");
		peopleModel.addColumn("Role");
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> roleList = new ArrayList<String>();

		String sql = "SELECT name from staff";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
				roleList.add("staff");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		sql = "SELECT name from manager";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
				roleList.add("manager");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		sql = "SELECT name from administrator";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
				roleList.add("administrator");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//put everything to the JTable
		for (int i = 0; i < nameList.size(); i++) {
			peopleModel.addRow(new Object[]{nameList.get(i),roleList.get(i)});
		}

		JButton addRoleButtom = new JButton("Add Role");
		managePanel.add(addRoleButtom);
		addRoleButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRoleAdmin(conn);
			}
		});

		JButton deleteRoleButtom = new JButton("Delete Role");
		managePanel.add(deleteRoleButtom);

		deleteRoleButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (peopleTable.getValueAt(peopleTable.getSelectedRow(),0).equals(name)) { //if itself
					JOptionPane.showMessageDialog(new JFrame("error"), "You Cannot delete yourself");
				} else {
					String sql;
					if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(),1)).equals("staff"))
						sql = "delete from staff where name = ?";
					else if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(),1)).equals("manager"))
						sql = "delete from manager where name = ?";
					else
						sql = "delete from administrator where name = ?";

					try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

						// set the corresponding param
						pstmt.setString(1, String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(), 0)));
						// execute the delete statement
						pstmt.executeUpdate();

					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}

		});

		JButton changePassword = new JButton("Change Password");
		managePanel.add(changePassword);
		changePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame changepwWindow = new JFrame("Manager");
				changepwWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel changepwPanel = new JPanel();
				JLabel newpasswordLable = new JLabel("New Password");
				changepwPanel.add(newpasswordLable);
				JTextField newpwInput = new JTextField(15);
				changepwPanel.add(newpwInput);
				JButton changepwButtom = new JButton("Change");
				changepwPanel.add(changepwButtom);
				changepwButtom.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String sql;

						if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(),1)).equals("staff"))
							sql = "update staff set password = ? where name = ?";
						else if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(),1)).equals("manager"))
							sql ="update manager set password = ? where name = ?";
						else
							sql = "update administrator set password = ? where name = ?";

						try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

							// set the corresponding param
							pstmt.setString(2, String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(),0)));
							pstmt.setString(1, newpwInput.getText());
							// execute the delete statement
							pstmt.executeUpdate();
							changepwWindow.setVisible(false);

						} catch (SQLException e1) {
							System.out.println(e1.getMessage());
						}
					}
				});

				changepwWindow.getContentPane().add(changepwPanel);
				changepwWindow.setSize(400,400);
				changepwWindow.setVisible(true);
				}

		});

		manageWindow.getContentPane().add(managePanel);
		manageWindow.setSize(500,600);
		manageWindow.setVisible(true);


	}

	public static void addRoleAdmin(Connection conn) {
		JFrame managerWindow = new JFrame("Addrole");
		managerWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel managerPanel = new JPanel();
		JLabel nameLabel = new JLabel("Name");
		managerPanel.add(nameLabel);
		JTextField nameInput = new JTextField(15);
		managerPanel.add(nameInput);
		JLabel pwLabel = new JLabel("Password");
		managerPanel.add(pwLabel);
		JTextField pwInput = new JTextField(15);
		managerPanel.add(pwInput);
		String[] choices = {"Staff", "Manager", "Administrator"};
		final JComboBox<String> cb = new JComboBox<String>(choices);
		managerPanel.add(cb);
		JButton submitButtom = new JButton("submit");
		managerPanel.add(submitButtom);

		submitButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String sql;
					if (String.valueOf(cb.getSelectedItem()).equals("Staff"))
						sql = "INSERT INTO Staff(name,password) Values(?,?)";
					else if (String.valueOf(cb.getSelectedItem()).equals("Manager"))
						sql = "INSERT INTO Manager(name,password) Values(?,?)";
					else
						sql = "INSERT INTO Administrator(name,password) Values(?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,nameInput.getText());
					pstmt.setString(2, pwInput.getText());
					pstmt.executeUpdate();
				} catch (SQLException e3) {
					System.out.println(e3.getMessage());
				}

			}
		});

		managerWindow.getContentPane().add(managerPanel);
		managerWindow.setSize(200,600);
		managerWindow.setVisible(true);
	}

	public static void manageManager(String name,Connection conn){
		JFrame manageWindow = new JFrame("Manage People");
		manageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel managePanel = new JPanel();
		JTable peopleTable = new JTable();
		DefaultTableModel peopleModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		peopleTable.setModel(peopleModel);
		managePanel.add(new JScrollPane(peopleTable));
		peopleModel.addColumn("Name");
		peopleModel.addColumn("Role");
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> roleList = new ArrayList<String>();

		String sql = "SELECT name from staff";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
				roleList.add("staff");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		sql = "SELECT name from manager where name = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1,name);
			ResultSet rs = pstmt.executeQuery();
			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
				roleList.add("manager");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		//put everything to the JTable
		for (int i = 0; i < nameList.size(); i++) {
			peopleModel.addRow(new Object[]{nameList.get(i),roleList.get(i)});
		}

		JButton addRoleButtom = new JButton("Add Role");
		managePanel.add(addRoleButtom);
		addRoleButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRoleManager(conn);
			}
		});

		JButton deleteRoleButtom = new JButton("Delete Role");
		managePanel.add(deleteRoleButtom);

		deleteRoleButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (peopleTable.getValueAt(peopleTable.getSelectedRow(),1).equals("manager")) { //if itself
					JOptionPane.showMessageDialog(new JFrame("error"), "You can only operate on staff");
				} else {
					String sql;
					if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(),1)).equals("staff"))
						sql = "delete from staff where name = ?";
					else if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(),1)).equals("manager"))
						sql = "delete from manager where name = ?";
					else
						sql = "delete from administrator where name = ?";

					try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

						// set the corresponding param
						pstmt.setString(1, String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(), 0)));
						// execute the delete statement
						pstmt.executeUpdate();

					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}

		});

		JButton changePassword = new JButton("Change Password");
		managePanel.add(changePassword);
		changePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame changepwWindow = new JFrame("Manager");
				changepwWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JPanel changepwPanel = new JPanel();
				JLabel newpasswordLable = new JLabel("New Password");
				changepwPanel.add(newpasswordLable);
				JTextField newpwInput = new JTextField(15);
				changepwPanel.add(newpwInput);
				JButton changepwButtom = new JButton("Change");
				changepwPanel.add(changepwButtom);
				changepwButtom.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						if (peopleTable.getValueAt(peopleTable.getSelectedRow(),0).equals(name)) { //if itself
							JOptionPane.showMessageDialog(new JFrame("error"), "You can only operate on yourself and staff");
						} else {
							String sql;
							if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(), 1)).equals("staff"))
								sql = "update staff set password = ? where name = ?";
							else if (String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(), 1)).equals("manager"))
								sql = "update manager set password = ? where name = ?";
							else
								sql = "update administrator set password = ? where name = ?";

							try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

								// set the corresponding param
								pstmt.setString(2, String.valueOf(peopleTable.getValueAt(peopleTable.getSelectedRow(), 0)));
								pstmt.setString(1, newpwInput.getText());
								// execute the delete statement
								pstmt.executeUpdate();
								changepwWindow.setVisible(false);

							} catch (SQLException e1) {
								System.out.println(e1.getMessage());
							}
						}
					}
				});

				changepwWindow.getContentPane().add(changepwPanel);
				changepwWindow.setSize(400,400);
				changepwWindow.setVisible(true);
			}

		});

		manageWindow.getContentPane().add(managePanel);
		manageWindow.setSize(500,600);
		manageWindow.setVisible(true);


	}

	public static void addRoleManager(Connection conn) {
		JFrame managerWindow = new JFrame("Addrole");
		managerWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel managerPanel = new JPanel();
		JLabel nameLabel = new JLabel("Name");
		managerPanel.add(nameLabel);
		JTextField nameInput = new JTextField(15);
		managerPanel.add(nameInput);
		JLabel pwLabel = new JLabel("Password");
		managerPanel.add(pwLabel);
		JTextField pwInput = new JTextField(15);
		managerPanel.add(pwInput);
		String[] choices = {"Staff"};
		final JComboBox<String> cb = new JComboBox<String>(choices);
		managerPanel.add(cb);
		JButton submitButtom = new JButton("submit");
		managerPanel.add(submitButtom);

		submitButtom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String sql;
						sql = "INSERT INTO Staff(name,password) Values(?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,nameInput.getText());
					pstmt.setString(2, pwInput.getText());
					pstmt.executeUpdate();
				} catch (SQLException e3) {
					System.out.println(e3.getMessage());
				}

			}
		});

		managerWindow.getContentPane().add(managerPanel);
		managerWindow.setSize(200,600);
		managerWindow.setVisible(true);
	}

	public static void alert(String name, Connection conn) {

		JFrame viewAreaWindow = new JFrame("View Low quantity Item");
		JPanel viewAreaPanel = new JPanel();
		viewAreaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		viewAreaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		DefaultTableModel itemModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable itemTable = new JTable();
		itemTable.setModel(itemModel);
		viewAreaPanel.add(new JScrollPane(itemTable));
		itemModel.addColumn("Name");
		itemModel.addColumn("quantity");
		itemModel.addColumn("Area");
		itemModel.addColumn("Warning Num");
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<Integer> quantityList = new ArrayList<Integer>();
		ArrayList<String> categoryList = new ArrayList<String>();
		ArrayList<Integer> warningnumList = new ArrayList<Integer>();
		JButton editLocationButtom = new JButton("Edit Location");
		String sql = "select name,quantity,mapname,warningnum from (SELECT name,sum(num) as quantity,mapname,warningnum from item  group by name having sum(num) ) where quantity < warningnum";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				nameList.add(rs.getString("name"));
				quantityList.add(rs.getInt("quantity"));
				categoryList.add(rs.getString("mapname"));
				warningnumList.add(rs.getInt("warningnum"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//put everything to the JTable
		for (int i = 0; i < categoryList.size(); i++) {
			itemModel.addRow(new Object[]{nameList.get(i),quantityList.get(i),categoryList.get(i),warningnumList.get(i)});
		}
		viewAreaWindow.getContentPane().add(viewAreaPanel);
		viewAreaWindow.setSize(500,800);
		viewAreaWindow.setVisible(true);

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
