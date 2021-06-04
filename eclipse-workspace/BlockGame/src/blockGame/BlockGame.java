package blockGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
public class BlockGame extends JFrame{

	static int CANVAS_WIDTH = 500;
	static int CANVAS_HEIGHT = 800;
	static int BLOCK_WIDTH = 47;
	static int BLOCK_HEIGHT = 20;
	static int BALL_WIDTH = 20;
	static int BALL_HEIGHT = 20;
	static int BAR_WIDTH = 100;
	static int BAR_HEIGHT = 20;
	static int BAR_FROM_BASELINE = 150;
	static int GAB = 4;
	
	//���
	static int row = 4;
	static int column = 10;
	static int x;
	static int y;
	static int color = 0;//0:red, 1:blue, 2:yellow, 3:white
	static int dir = 0; //0:������-��, 1:������-�Ʒ�, 2:����-��, 3:����-�Ʒ�
	Block[][] b = new Block[row][column];
	Bar bar = new Bar();
	Ball ball = new Ball();
	MyPanel p = new MyPanel();
	//����
	static int score = 0;
	static int ballSpeed = 5;
	Timer timer = null;
	public BlockGame() {
		super("��ϰ���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(CANVAS_WIDTH,CANVAS_HEIGHT);
		setLocationRelativeTo(null);
		startTimer();
		initData();
		setAction();
		p.repaint();
		getContentPane().add(p,"Center");
		setVisible(true);
	}
	public void initData() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < column; j++) {
				b[i][j] = new Block();
				b[i][j].x = BLOCK_WIDTH*j+GAB*j;
				b[i][j].y = BLOCK_HEIGHT*j+GAB*j;
				b[i][j].width = BLOCK_WIDTH;
				b[i][j].height = BLOCK_HEIGHT;
				b[i][j].color = 4-i; 
				b[i][j].isHidden = false;
			}
		}
	}
	class Ball{
		int x = CANVAS_WIDTH/2-BALL_WIDTH/2;
		int y = CANVAS_HEIGHT-300;
		int width = BALL_WIDTH;
		int height = BALL_HEIGHT;
	}
	
	class Block{
		int x;
		int y;
		int width = BLOCK_WIDTH;
		int height = BLOCK_HEIGHT;
		int color = BlockGame.color; //0:red, 1:blue, 2:yellow, 3:white
		boolean isHidden = false;
	}
	class Bar{
		int x = CANVAS_WIDTH/2-BAR_WIDTH/2;
		int y = CANVAS_HEIGHT-BAR_FROM_BASELINE;
		int width = BAR_WIDTH;
		int height = BAR_HEIGHT;
	}
	public void startTimer() {
		timer = new Timer(30,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				movement();
				checkCollision();
				p.repaint();
				
			}
		});
		timer.start();
	}
	public void checkCollision() {
		
	}
	public void movement() {
		if(dir==0) {//������ �� ���
			ball.x+=10;
			ball.y-=10;
				if(ball.x==CANVAS_WIDTH-ball.width) {
				dir=2;//���� ���� ������� �̵��߿� ������ ���� �ε����� ��θ� ���� ���� �ٲ۴�.
				}else if(ball.y==0) {
				dir=1;//���� ���� ������� �ε��߿� ���� ���� �ε����� ��θ� ������ �Ʒ��� �ٲ۴�.
				}
		}if(dir==1) {//������ �Ʒ� ���
			ball.x+=10;
			ball.y+=10;
			if(ball.x==CANVAS_WIDTH-ball.width) {
				dir=3;//���� ���� �Ϲ� ��η� �̵��ϴٰ� ������ ���� �ε����� ��θ� ���� �Ʒ��� �ٲ۴�.
				}else if(ball.y==CANVAS_HEIGHT-ball.height*2) {
				dir=0;//���� ���� �Ϲ����� �̵��߿� �Ʒ��� ���� �ε����� ��θ� ������ ���� �ٲ۴�.
				}
		}if(dir==2) {//���� �� ���
			ball.x-=10;
			ball.y-=10;
			if(ball.x==0) {
				dir=0;//���� ���� ������� �̵��߿� ���� ���� �ε����� ��θ� ���� ���� �ٲ۴�.
				}else if(ball.y==0) {
				dir=3;//���� ���� ������� �ε��߿� ���� ���� �ε����� ��θ� ���� �Ʒ��� �ٲ۴�.
				}
		}if(dir==3) {//���� �Ʒ� ���
			ball.x-=10;
			ball.y+=10;
			if(ball.x==0) {
				dir=1;//���� ���� �Ϲ����� �̵��߿� ���� ���� �ε����� ��θ� ������ �Ʒ��� �ٲ۴�.
				}else if(ball.y==CANVAS_HEIGHT-ball.height*2) {
				dir=2;//���� ���� �Ϲ����� �ε��߿� �Ʒ��� ���� �ε����� ��θ� ���� ���� �ٲ۴�.
				}
		}
	}
	
	public void setAction() {
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_RIGHT) {//������ Ű ���� �� �ٰ� ���������� ������
					if(bar.x<380) {
					System.out.println("�������� ���Ƚ��ϴ�."+bar.x);
					bar.x +=10;
					}
					else if(bar.x>380) {
					bar.x += 0;
					}
					p.repaint();
				}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {//���� Ű���� �� �ٰ� �������� ������
					if(bar.x>0) {
					System.out.println("������ ���Ƚ��ϴ�."+bar.x);
					bar.x -=10;}
					else if(bar.x<0) {
					bar.x -= 0;
					}
					p.repaint();

				}else {
					System.out.println("�� Ű�� �ƴѵ�? ������");
				}
			}
		});
	}
	
	class MyPanel extends JPanel{
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);	
			g2d.fillRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);//�г� ���ȭ��
			
			for(int i = 0; i < row; i++) {
				for(int j = 0; j < column; j++) {//��� 40�� �׸���
					b = new Block[i][j];
					if(i==0) {
				    color = i;
					g2d.setColor(Color.red);
					g2d.fillRect(GAB*2+BLOCK_WIDTH*j-GAB,GAB+50,BLOCK_WIDTH-GAB,BLOCK_HEIGHT);
					}else if(i==1) {
					color = i;
					g2d.setColor(Color.blue);
					g2d.fillRect(GAB*2+BLOCK_WIDTH*j-GAB,GAB+BLOCK_HEIGHT*i+GAB*i+50,BLOCK_WIDTH-GAB,BLOCK_HEIGHT);
					}else if(i==2) {
					color = i;
					g2d.setColor(Color.yellow);
					g2d.fillRect(GAB*2+BLOCK_WIDTH*j-GAB,GAB+BLOCK_HEIGHT*i+GAB*i+50,BLOCK_WIDTH-GAB,BLOCK_HEIGHT);
					}else if(i==3) {
					color = i;
					g2d.setColor(Color.white);
					g2d.fillRect(GAB*2+BLOCK_WIDTH*j-GAB,GAB+BLOCK_HEIGHT*i+GAB*i+50,BLOCK_WIDTH-GAB,BLOCK_HEIGHT);
					}
				}
			}
			
			g2d.setColor(Color.white);
			g2d.fillRect(bar.x, CANVAS_HEIGHT-BAR_FROM_BASELINE+BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
			
			g2d.setColor(Color.MAGENTA);
			g2d.fillOval(ball.x, ball.y, ball.width, ball.height);
			
			g2d.setColor(Color.white);
			
			Font italicFont = new Font("��Ż��",Font.ITALIC,40);
			g2d.setFont(italicFont);
			g2d.drawString("SCORE :"+score, CANVAS_WIDTH/2-100, 30);
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		new BlockGame();
	}
}
