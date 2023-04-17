

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class GUIApp extends JFrame implements ActionListener {
	
	private static int L;			// lattice size
	private static double T;		// temperature
	public Container c;
	public JPanel sP;
	private final JPanel nP = new JPanel();
	private final JButton start = new JButton("Start");
	private final JButton stop = new JButton("Stop");
	private final JButton clear = new JButton("Clear");
	private final JLabel lato = new JLabel("L");
	private final JLabel temperatura = new JLabel("T");
	private final JTextField latoText = new JTextField("",3);
	private final JTextField tempText = new JTextField("",3);
	
	public static JPanel[] p;
	public static boolean running = false;
	public Ising dynamics;
	
	public GUIApp() {
		
		
		super("2D ISING LATTICE");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		c = this.getContentPane();
		c.setPreferredSize(new Dimension(350,390));
		c.setLayout(new BorderLayout());
		c.add(nP, BorderLayout.NORTH);
		
		nP.add(lato);
		nP.add(latoText);
		nP.add(temperatura);
		nP.add(tempText);
		nP.add(start);
		nP.add(stop);
		nP.add(clear);
			
			
		start.addActionListener(this);
		stop.addActionListener(this);
		clear.addActionListener(this);
		
			
		this.pack();
		this.setLocationRelativeTo(null);
		
	
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equals("Start")) {
			if (latoText.getText().equals("") || tempText.getText().equals("")) {
				JOptionPane.showMessageDialog(null, new String("Insert value of both size and temperature."), "WARNING", JOptionPane.INFORMATION_MESSAGE);
			} else {
				setRunning();
				running = true;
				L = Integer.parseInt(latoText.getText());
				T = Double.parseDouble(tempText.getText());
				init();
				wipeText();
				dynamics  = new Ising(-2 * L * L, T, L);
				new Thread(dynamics).start();				
			}
				
		}
		else if (cmd.equals("Stop")) {
			setReady();
			running = false;
		}
		else if (cmd.equals("Clear")) {
			if (!running) {
				running = false;
				for (JPanel pxl: p) {
					sP.remove(pxl);
				}
			}
		}
		
	
	}
	
	public void setReady() {
		start.setEnabled(true);
		stop.setEnabled(false);
		clear.setEnabled(true);
	}
	public void setRunning() {
		start.setEnabled(false);
		stop.setEnabled(true);
		clear.setEnabled(true);
	}
	public void setZero(JPanel[] pixel) {
		for (JPanel pxl: pixel) {
			if (Math.random() < 0.5) pxl.setBackground(Color.RED);
			else pxl.setBackground(Color.BLUE);
		}
		
	}
	public void init() {
		//makes lattice
		sP = new JPanel();
		p = new JPanel[L*L];
		for (int i = 0; i < L*L; i++) {
			p[i] = new JPanel();
			p[i].setPreferredSize(new Dimension(1,7));
			sP.add(p[i]);
		}
		c.add(sP, BorderLayout.CENTER);
		sP.setLayout(new GridLayout(L,L));
		sP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setZero(p);
	}
	public void wipeText() {
		try {
			latoText.getDocument().remove(0, latoText.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		try {
			tempText.getDocument().remove(0, tempText.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	
}
