package main;

import searchAI.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import game.*;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener
{	
	public static final int DFS = 0;
	public static final int BFS = 1;
	public static final int IDS = 2;
	public static final int UCS = 3;
	public static final int GRD = 4;
	public static final int AST = 5;
	public static final int GRD2 = 6;
	public static final int AST2 = 7;
	
	final int windowWidth = 440;
	final int windowHeight = 200;
	final int windowLocationX = 100;
	final int windowLocationY = 100;
	final int fontSize = 12;
	
	JButton startButton;
	JPanel westWidth;
	JPanel centerHeight;
	JPanel eastVisualize;
	JPanel topIntro;
	JLabel widthText;
	JLabel heightText;
	JLabel visualizeText;
	JLabel greetingText;
	JTextField widthIn;
	JTextField heigtIn;
	JComboBox<String> visualize;
	JComboBox<String> function;
	
	public Main()
	{
		super();
		
		// Setting the size Location, layout and other attributes of the frame.
		setSize(windowWidth,windowHeight);
		setLocation(windowLocationX, windowLocationY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setVisible(true);
		setTitle("Introduction To Artificial Intelligence: Project 1");
		
		startButton = new JButton("Start");
		startButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
		startButton.setForeground(Color.BLACK);
		
		widthText = new JLabel();
		widthText.setText("Width:");
		
		widthIn = new JTextField();
		widthIn.setEditable(true);
		widthIn.setText("RND");
		widthIn.setPreferredSize(new Dimension(60, 30));
		
		westWidth = new JPanel();
		westWidth.add(widthText);
		westWidth.add(widthIn);
		
		heightText = new JLabel();
		heightText.setText("Height:");
		
		heigtIn = new JTextField();
		heigtIn.setEditable(true);
		heigtIn.setText("RND");
		heigtIn.setPreferredSize(new Dimension(60, 30));
		
		centerHeight = new JPanel();
		centerHeight.add(heightText);
		centerHeight.add(heigtIn);
		
		visualizeText = new JLabel();
		visualizeText.setText("Visualize?");
		
		String[] options = {"Yes", "No"};
		visualize = new JComboBox<String>(options);
		visualize.setSelectedIndex(0);
		
		eastVisualize = new JPanel();
		eastVisualize.add(visualizeText);
		eastVisualize.add(visualize);
		
		String[] functions = {"DFS", "BFS", "IDS", "UCS", "GRD", "AST", "GRD2", "AST2"};
		function = new JComboBox<String>(functions);
		function.setSelectedIndex(5);
		
		greetingText = new JLabel();
		greetingText.setHorizontalAlignment(JLabel.CENTER);
		greetingText.setVerticalAlignment(JLabel.CENTER);
		greetingText.setText("<html><style>hh{text-align:center}</style>a<br/>Artificial Intelligence Project#1: Save Westeros!"
				+ "<br/> Choose a function!</html>");
		
		topIntro = new JPanel();
		topIntro.add(greetingText);
		topIntro.add(function);
		
		add(startButton, BorderLayout.SOUTH);
		add(westWidth, BorderLayout.WEST);
		add(centerHeight, BorderLayout.CENTER);
		add(eastVisualize, BorderLayout.EAST);
		add(topIntro, BorderLayout.NORTH);
		
		startButton.addActionListener(this);

		repaint();
		validate();
	}
	
	public static void search(GenericSearchProblem p, int searchFunction, boolean visualize)
	{
		p.initialNode.print();
		if(visualize)
		{
			Visualization v = new Visualization();
			v.setProblem(p);
			v.setSearch(searchFunction);
			v.initScreen();
		} else {
			switch(searchFunction)
			{
			case DFS:
				System.out.println("Doing DFS");
				Search.DFS(p);
				break;
			case BFS:
				System.out.println("Doing BFS");
				Search.BFS(p);
				break;
			case IDS:
				System.out.println("Doing IDS");
				Search.IDS(p);
				break;
			case UCS:
				System.out.println("Doing UCS");
				Search.UCS(p);
				break;
			case GRD:
				System.out.println("Doing GRD");
				Search.Greedy(p);
				break;
			case AST:
				System.out.println("Doing AST");
				Search.AStar(p);
				break;
			case GRD2:
				System.out.println("Doing GRD2");
				Search.Greedy2(p);
				break;
			case AST2:
				System.out.println("Doing AST2");
				Search.AStar2(p);
				break;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof JButton)
		{
			JButton actionTrigger = (JButton) e.getSource();
			String buttonText = actionTrigger.getText();
			switch (buttonText)
			{
			case "Start":
				try
				{
					int w = Integer.parseInt(widthIn.getText());
					int h = Integer.parseInt(heigtIn.getText());
					boolean vis = ((String)visualize.getSelectedItem() == "Yes");
					int func = function.getSelectedIndex();
					GenericSearchProblem prob = new SaveWesteros(h, w);
					search(prob, func, vis);
				} catch(Exception ex) {
					boolean vis = ((String)visualize.getSelectedItem() == "Yes");
					int func = function.getSelectedIndex();
					GenericSearchProblem prob = new SaveWesteros();
					search(prob, func, vis);
				}
				break;
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Main();
//		Search.testGrid();
	}
}