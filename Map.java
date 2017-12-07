
//package com.javaworld.media.j2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.imageio.ImageIO;

public class Map {
	//what floor the map depicts
	private int floor;
	//name of map
	private String name;
	//scale of map (to determine size)
	private int scale;


	private String fileName;

	BufferedImage img = null;

	//instantiation
	public Map(String myName, Integer myFloor, Integer myScale, String file) {
		floor = myFloor;
		name = myName;
		scale = myScale;
		fileName = file;

	}

	public Map(String file) {
		fileName = file;

	}


	//changes name of map
	public void changeName(String newName){
		name = newName;
	}


	//makes new panel for map
	public void show(Item itemType) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mapPanel mp = new mapPanel(itemType);
		frame.add(mp);
		frame.pack();
		frame.setSize(mp.getWidth(),mp.getHeight());
		frame.setVisible(true);
	}

	public void set(Item itemType, Connection conn, String areaname, String name){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mapPanelNew mp = new mapPanelNew(itemType,conn,areaname,name);
		frame.add(mp);
		frame.pack();
		frame.setSize(mp.getWidth(),mp.getHeight());
		frame.setVisible(true);
	}

	//actual map panel
	public class mapPanelNew extends JPanel{
		//point where item is
		private Point point;
		//background image
		private BufferedImage image;

		public int getHeight() {
			return image.getHeight();

		}

		public int getWidth() {
			return image.getWidth();
		}

		public mapPanelNew(final Item itemType,Connection conn, String areaname, String name) {
			try {
				image = ImageIO.read(new File(fileName));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					point = (e.getPoint());
					int xloc = (int)point.getX();
					int yloc = (int)point.getY();
					String sql = "UPDATE item SET xloc = ? , yloc = ? where mapname = ? and name = ?";
					try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
						pstmt.setInt(1,xloc);
						pstmt.setInt(2,yloc);
						pstmt.setString(3,areaname);
						pstmt.setString(4,name);
						System.out.println(xloc);
						pstmt.executeUpdate();
					}
					catch (SQLException e1) {
						System.out.println(e1.getMessage());
					}
					repaint();
				}
			});
			JButton confirmButton = new JButton("Confirm");
			confirmButton.addActionListener(new ActionListener(){
				//saves coordinates
				public void actionPerformed(ActionEvent e){
					itemType.setXLoc(point.x);
					itemType.setYLoc(point.y);
				}
			});
		}

		//draws dot
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			if (image != null) {
				g2d.drawImage(image, 0, 0, this);
			}
			g2d.setColor(Color.RED);
			try {
				g2d.fillOval(point.x - 4, point.y - 4, 8, 8);
			} catch (NullPointerException e ){

			}
			g2d.dispose();
		}
	}

	//actual map panel

	public class mapPanel extends JPanel{
		//point where item is
		private Point point;
		//background image

		private BufferedImage image;

		public int getHeight() {
			return image.getHeight();

		}

		public int getWidth() {
			return image.getWidth();
		}


		public mapPanel(final Item itemType) {
			try {
				image = ImageIO.read(new File(fileName));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			point = new Point(itemType.getXLoc(), itemType.getYLoc());
			repaint();


		}

		//draws dot
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			if (image != null) {
				g2d.drawImage(image, 0, 0, this);
			}
			g2d.setColor(Color.RED);
			g2d.fillOval(point.x - 4, point.y - 4, 8, 8);
			g2d.dispose();
		}
	}

	public static void main(String[] args) {

	}
}


