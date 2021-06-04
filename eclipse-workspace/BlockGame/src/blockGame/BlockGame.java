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
	
	//상수
	static int row = 4;
	static int column = 10;
	static int x;
	static int y;
	static int color = 0;//0:red, 1:blue, 2:yellow, 3:white
	static int dir = 0; //0:오른쪽-위, 1:오른쪽-아래, 2:왼쪽-위, 3:왼쪽-아래
	Block[][] b = new Block[row][column];
	Bar bar = new Bar();
	Ball ball = new Ball();
	MyPanel p = new MyPanel();
	//변수
	static int score = 0;
	static int ballSpeed = 5;
	Timer timer = null;
	public BlockGame() {
		super("블록게임");
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
		if(dir==0) {//오른쪽 위 경로
			ball.x+=10;
			ball.y-=10;
				if(ball.x==CANVAS_WIDTH-ball.width) {
				dir=2;//공이 우측 상방으로 이동중에 오른쪽 벽에 부딪히면 경로를 왼쪽 위로 바꾼다.
				}else if(ball.y==0) {
				dir=1;//공이 우측 상방으로 인동중에 위쪽 벽에 부딪히면 경로를 오른쪽 아래로 바꾼다.
				}
		}if(dir==1) {//오른쪽 아래 경로
			ball.x+=10;
			ball.y+=10;
			if(ball.x==CANVAS_WIDTH-ball.width) {
				dir=3;//공이 우측 하방 경로로 이동하다가 오른쪽 벽에 부딪히면 경로를 왼쪽 아래로 바꾼다.
				}else if(ball.y==CANVAS_HEIGHT-ball.height*2) {
				dir=0;//공이 우측 하방으로 이동중에 아랫쪽 벽에 부딪히면 경로를 오른쪽 위로 바꾼다.
				}
		}if(dir==2) {//왼쪽 위 경로
			ball.x-=10;
			ball.y-=10;
			if(ball.x==0) {
				dir=0;//공이 좌측 상방으로 이동중에 좌측 벽에 부딪히면 경로를 우측 위로 바꾼다.
				}else if(ball.y==0) {
				dir=3;//공이 좌측 상방으로 인동중에 위쪽 벽에 부딪히면 경로를 좌측 아래로 바꾼다.
				}
		}if(dir==3) {//왼쪽 아래 경로
			ball.x-=10;
			ball.y+=10;
			if(ball.x==0) {
				dir=1;//공이 좌측 하방으로 이동중에 좌측 벽에 부딪히면 경로를 오른쪽 아래로 바꾼다.
				}else if(ball.y==CANVAS_HEIGHT-ball.height*2) {
				dir=2;//공이 좌측 하방으로 인동중에 아랫쪽 벽에 부딪히면 경로를 좌측 위로 바꾼다.
				}
		}
	}
	
	public void setAction() {
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_RIGHT) {//오른쪽 키 누를 때 바가 오른쪽으로 움직임
					if(bar.x<380) {
					System.out.println("오른쪽이 눌렸습니다."+bar.x);
					bar.x +=10;
					}
					else if(bar.x>380) {
					bar.x += 0;
					}
					p.repaint();
				}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {//왼쪽 키누를 때 바가 왼쪽으로 움직임
					if(bar.x>0) {
					System.out.println("왼쪽이 눌렸습니다."+bar.x);
					bar.x -=10;}
					else if(bar.x<0) {
					bar.x -= 0;
					}
					p.repaint();

				}else {
					System.out.println("그 키는 아닌데? ㅋㅋㅋ");
				}
			}
		});
	}
	
	class MyPanel extends JPanel{
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);	
			g2d.fillRect(0,0,CANVAS_WIDTH,CANVAS_HEIGHT);//패널 배경화면
			
			for(int i = 0; i < row; i++) {
				for(int j = 0; j < column; j++) {//블록 40개 그리기
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
			
			Font italicFont = new Font("이탈릭",Font.ITALIC,40);
			g2d.setFont(italicFont);
			g2d.drawString("SCORE :"+score, CANVAS_WIDTH/2-100, 30);
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		new BlockGame();
	}
}
