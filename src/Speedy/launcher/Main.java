package Speedy.launcher;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.vecmath.Color4f;

import Speedy.launcher.auth.LoginManager;



public class Main extends JFrame{

	String version = "0.0.0 preAlfa1";
	
	Image bg1 = null;
	
	public Main() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(800, 560);
		setTitle("Tierra Lancher --(c)Speedy3210-- v: "+version);
		Image icon = null;
		try {
			
			icon = ImageIO.read(new File("res/icon.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIconImage(icon);
		setLocationRelativeTo(null);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setResizable(false);
		
		loadImages();
		menubar();
		inside();
		
		
		setVisible(true);
				
	}
	
	private void loadImages() {
		
		try {
			bg1 = ImageIO.read(new File("res/bg1.png"));
			//other images
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void menubar() {
		
		JMenuBar mbar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		JMenuItem testItem = new JMenuItem("1-2,1-2,Testing...");
		JMenuItem about = new JMenuItem("About");
		JMenuItem options = new JMenuItem("Options");
		
		setJMenuBar(mbar);
		mbar.add(file);
		mbar.add(help);
		file.add(testItem);
		help.add(about);
		file.addSeparator();
		file.add(options);
		
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				JOptionPane.showMessageDialog(null, "Autor: Speedy3210 \nVersion: "+version+"\nPlease do not distibute in any form"); } 
			});
		
	}
	
	private final static JTextArea logArea = new JTextArea();
	private final static ScrollPane consolePanel = new ScrollPane();
	
	private void inside() {
		
		JTabbedPane tabs = new JTabbedPane();
		SpringLayout layout = new SpringLayout();
		add(tabs);
		
		//loginTab
		ImagePanel loginPanel = new ImagePanel(bg1);
		final JTextField username = new JTextField();
		final JPasswordField userpass = new JPasswordField();
		JLabel nameLabel = new JLabel("User Name :");
		JLabel passLabel = new JLabel("Password :");
		JButton login = new JButton(" Login ");
		
		loginPanel.setLayout(layout);
		
		
		
		tabs.add(loginPanel, "  Login  ");
		loginPanel.add(nameLabel);
			layout.putConstraint(SpringLayout.WEST, nameLabel, 550, SpringLayout.WEST, loginPanel);
			layout.putConstraint(SpringLayout.NORTH, nameLabel, 380, SpringLayout.NORTH, loginPanel);
			nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, nameLabel.getFont().getSize()+3));
		loginPanel.add(username);
			layout.putConstraint(SpringLayout.WEST, username, 10, SpringLayout.EAST, nameLabel);
			layout.putConstraint(SpringLayout.NORTH, username, 378, SpringLayout.NORTH, loginPanel);
			layout.putConstraint(SpringLayout.EAST, username, -35, SpringLayout.EAST, loginPanel);
		loginPanel.add(passLabel);
			layout.putConstraint(SpringLayout.WEST, passLabel, 550, SpringLayout.WEST, loginPanel);
			layout.putConstraint(SpringLayout.NORTH, passLabel, 26, SpringLayout.NORTH, nameLabel);
			passLabel.setFont(new Font(passLabel.getFont().getFontName(), Font.BOLD, passLabel.getFont().getSize()+3));
		loginPanel.add(userpass);
			layout.putConstraint(SpringLayout.WEST, userpass, 10, SpringLayout.EAST, passLabel);
			layout.putConstraint(SpringLayout.NORTH, userpass, 26, SpringLayout.NORTH, username);
			layout.putConstraint(SpringLayout.EAST, userpass, -35, SpringLayout.EAST, loginPanel);
		loginPanel.add(login);
			layout.putConstraint(SpringLayout.NORTH, login, 10, SpringLayout.SOUTH, passLabel);
			layout.putConstraint(SpringLayout.WEST, login, 650, SpringLayout.WEST, loginPanel);
			login.setOpaque(false);
			
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				log("trying to log in");
				
				if (!username.getText().isEmpty()) {
					log("username : " + username.getText());
					
					if (userpass.getPassword().length >= 1) {
						log("pass : "+userpass.getText());
						
						LoginManager.authenticateUser(username.getText(), userpass.getPassword().toString(), "");
						
						
					} else { log("no password"); }
					
				} else { log("no username"); }
				
			} });
		
		//modpackTab
		JPanel modpackPanel = new JPanel();
		
		modpackPanel.setLayout(layout);
		
		tabs.add(modpackPanel, "  Modpacks  ");
		
		//consoleTab
		logArea.setBackground(Color.BLACK);
		logArea.setForeground(Color.ORANGE);
		logArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		logArea.setSelectedTextColor(Color.BLACK);
		logArea.setSelectionColor(Color.ORANGE);
		
		logArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		logArea.setEditable(false);
		
		DefaultCaret caret = (DefaultCaret)logArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		tabs.add(consolePanel, "  Console  ");
		consolePanel.add(logArea);
		log("Starting...");
		
	}
	
	public static void log(String s) {
		logArea.append(s+"\n");
		//System.out.println(s);

		logArea.setCaretPosition(logArea.getDocument().getLength());
		consolePanel.setScrollPosition(consolePanel.getScrollPosition().x, logArea.getCaretPosition());
	}
	
	public static void errorLog(Exception e) {
		log("[Error] : \n"+ e.toString());
	}
	
	public static void main(String[] args) {
		
		new Main();
	}

}
