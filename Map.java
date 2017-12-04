
//package com.javaworld.media.j2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
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
	
	BufferedImage img = null;

	//instantiation
	public Map(String myName, Integer myFloor, Integer myScale) {
		floor = myFloor;
		name = myName;
		scale = myScale;
	}
	
	//changes name of map
	public void changeName(String newName){
		name = newName;
	}
	
	//makes new panel for map
	public void show(String fileName, Item itemType) {
        JFrame frame = new JFrame();
        frame.add(new mapPanel(fileName, itemType));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
    //actual map panel
	public class mapPanel extends JPanel{
		//point where item is
		private Point point;
		//background image
        private BufferedImage image;

        public mapPanel(String fileName, final Item itemType) {
            try {
                image = ImageIO.read(new File(fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //checks if user clicks
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    point = (e.getPoint());
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
            
            g2d.fillOval(point.x - 4, point.y - 4, 8, 8);
            
            g2d.dispose();
        }
	}
}
