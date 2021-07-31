package button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Test {
	
		public static void main(String[] args) {
			JFrame f = new JFrame("¸Á´ÚÀÌ ´©¸£±â");
			JTextField tf = new JTextField();
			tf.setBounds(50,100,320,40);
			tf.createImage(null);
			JButton b = new JButton(new ImageIcon("btn.jpg"));
			b.setBounds(160,200,180,60);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tf.setText("¸Á´ÚÀÌ¿Í ¾Æ±â ¾È³ç?");
				}
			});
			f.add(b);
			f.add(tf);
			f.setSize(600,600);
			f.setResizable(true);
			f.setLocationRelativeTo(null);
			f.setLayout(null);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
