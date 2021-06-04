package got;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GotGame extends JFrame{

	Image buffImage;
	Graphics buffGraphic;
	Image background = new ImageIcon("src/icon/backgroundImage.png").getImage();
	Image player1 = new ImageIcon("src/icon/player1.jpg").getImage();
	Image player2 = new ImageIcon("src/icon/player-left.jpg").getImage();
	Image villain = new ImageIcon("src/icon/villain.png").getImage();
	Image attackBall = new ImageIcon("src/icon/�����.png").getImage();
	int SCREEN_WIDTH = 1280;
	int SCREEN_HEIGHT = 720;
	long beforeTime;
	long time;
	int playerX; //�÷��̾� ũ��� 60x60
	int playerY;
	int villainX; //���� ũ��� 50x50
	int villainY;
	int villain2X;
	int villain2Y;
	int ballX;
	int ballY;
	int ballSpeed = 20;//����� �ӵ�
	boolean isSpaceClick = false;
	int isRight = 0;//0 : ����, 1 : ������
	
	int score = 0;
	int playerSpeed = 10;
	int villainSpeed = 20;
	int dir; //0=��,1=�Ʒ�,2=��,3=��,4=������ ��,5=������ �Ʒ�,6=���� ��,7=���� �Ʒ�
	int dir2;
	Villain[] vill = new Villain[9];
	BallAction ballAction = new BallAction();
	public GotGame() {
		setTitle("���ް���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		init();
		catchVillain();
		for(int i = 0; i < 9; i++) {
			vill[i] = new Villain();
		}
		Thread t = new Thread(ballAction);//�̷��� �ص� ���ɸ��� ;; ��ġ�ͳ� �����带 ������ �ȿ��ٰ� �����ϸ� �ȵǴ°ǰ�?
		t.start();
		while(true) {
		try {
			Thread.sleep(100);
		}catch(Exception e) {
			e.printStackTrace();
		}
		dir = (int)(Math.random()*8);
		dir = (int)(Math.random()*8);
		vill[0].addVillain();//���� 1
		repaint(); 
		setScore();
		setScore2();
		dir2 = (int)(Math.random()*8);
		vill[1].addVillain2();//���� 2
		repaint();
//		dir = (int)(Math.random()*8);
//		vill[2].addVillain();//���� 3
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[3].addVillain();//���� 4
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[4].addVillain();//���� 5
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[5].addVillain();//���� 6
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[6].addVillain();//���� 7
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[7].addVillain();//���� 8
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[8].addVillain();//���� 9
//		repaint();
		}
	}
	class BallAction implements Runnable{//����İ� for�� while�� ���ļ� �׷��� �������� �ִϸ��̼��� �ȳ��� �Ф� �׷��� �����带 ������ �غ����� ��
		public void run() {
			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_SPACE) {
						if(ballX>SCREEN_WIDTH&&ballX<0) {
							ballX = playerX;
							repaint();
						}else {
							for(int i = 0; i < 100; i++) {
							try {
								Thread.sleep(100);
								ballSpeed+=i;
								repaint();
							}catch(Exception ex) {
								ex.printStackTrace();
							}
							}
						}
					}
				}
			});
		}
	}
	
	class Villain {
		public Villain() {
			addVillain();
			addVillain2();
		}
		public void addVillain() {
			if(dir == 0) {//0=��,1=�Ʒ�,2=��,3=��,4=������ ��,5=������ �Ʒ�,6=���� ��,7=���� �Ʒ�
				if(villainY>0) {
				villainY-=villainSpeed;
				}else {
					dir = 1;//���� ���� ���̸� ������ �Ʒ��� �ٲ۴�.
				}
			}else if(dir == 1) {
				if(villainY<SCREEN_HEIGHT-50-30) {
					villainY+=villainSpeed;
				}else {
						dir = 0;//�Ʒ��� ���� ���̸� ������ ���� �ٲ۴�.
					}
			}else if(dir == 2) {
				if(villainX>0) {
					villainX-=villainSpeed;
				}else {
						dir = 3;//���� ���� ���̸� ������ ���������� �ٲ۴�.
					}
			}else if(dir == 3) {
				if(villainX<SCREEN_WIDTH-50) {
					villainX+=villainSpeed;
				}else {
						dir = 2;//������ ���� ���̸� ������ �������� �ٲ۴�.
					}
			}else if(dir == 4) {
				if(villainX<SCREEN_WIDTH-50&&villainY>0) {
					villainX+=villainSpeed;
					villainY-=villainSpeed;
				}else {
						dir = 7;//���� ���� ���ٰ� ���� ���̸� ���� �Ʒ��� �����Ѵ�.
					}
			}else if(dir == 5) {
				if(villainX<SCREEN_WIDTH-50&&villainY<SCREEN_HEIGHT-50-30) {
					villainX+=villainSpeed;
					villainY+=villainSpeed;
				}else {
						dir = 6;//���� �Ʒ��� ���ٰ� ���� ���̸� ���� ���� �����Ѵ�.
					}
			}else if(dir == 6) {
				if(villainX>0 && villainY>0) {
					villainX-=villainSpeed;
					villainY-=villainSpeed;
				}else {
						dir = 6;//���� ���� ���ٰ� ���� ���̸� ������ �Ʒ��� �����Ѵ�.
					}
			}else if(dir == 7) {
				if(villainX>0 && villainY<SCREEN_HEIGHT-50-30) {
					villainX-=villainSpeed;
					villainY+=villainSpeed;
				}else {
						dir = 4;//���� �Ʒ��� ���ٰ� ���� ���̸� ������ ���� �����Ѵ�.
					}
			}
		}
		public void addVillain2() {
			if(dir2 == 0) {//0=��,1=�Ʒ�,2=��,3=��,4=������ ��,5=������ �Ʒ�,6=���� ��,7=���� �Ʒ�
				if(villain2Y>0) {
				villain2Y-=villainSpeed;
				}else {
					dir2 = 1;//���� ���� ���̸� ������ �Ʒ��� �ٲ۴�.
				}
			}else if(dir2 == 1) {
				if(villain2Y<SCREEN_HEIGHT-50-30) {
					villain2Y+=villainSpeed;
				}else {
						dir2 = 0;//�Ʒ��� ���� ���̸� ������ ���� �ٲ۴�.
					}
			}else if(dir2 == 2) {
				if(villain2X>0) {
					villain2X-=villainSpeed;
				}else {
						dir2 = 3;//���� ���� ���̸� ������ ���������� �ٲ۴�.
					}
			}else if(dir == 3) {
				if(villain2X<SCREEN_WIDTH-50) {
					villain2X+=villainSpeed;
				}else {
						dir2 = 2;//������ ���� ���̸� ������ �������� �ٲ۴�.
					}
			}else if(dir == 4) {
				if(villain2X<SCREEN_WIDTH-50&&villain2Y>0) {
					villain2X+=villainSpeed;
					villain2Y-=villainSpeed;
				}else {
						dir2 = 7;//���� ���� ���ٰ� ���� ���̸� ���� �Ʒ��� �����Ѵ�.
					}
			}else if(dir == 5) {
				if(villain2X<SCREEN_WIDTH-50&&villain2Y<SCREEN_HEIGHT-50-30) {
					villain2X+=villainSpeed;
					villain2Y+=villainSpeed;
				}else {
						dir2 = 6;//���� �Ʒ��� ���ٰ� ���� ���̸� ���� ���� �����Ѵ�.
					}
			}else if(dir2 == 6) {
				if(villain2X>0 && villain2Y>0) {
					villain2X-=villainSpeed;
					villain2Y-=villainSpeed;
				}else {
						dir2 = 6;//���� ���� ���ٰ� ���� ���̸� ������ �Ʒ��� �����Ѵ�.
					}
			}else if(dir == 7) {
				if(villain2X>0 && villain2Y<SCREEN_HEIGHT-50-30) {
					villain2X-=villainSpeed;
					villain2Y+=villainSpeed;
				}else {
						dir2 = 4;//���� �Ʒ��� ���ٰ� ���� ���̸� ������ ���� �����Ѵ�.
					}
			}
		}
	}
	public void init() { //�����̵��� ��ġ�� ����ĥ �Ǵ���� ��ġ �ʱ�ȭ
		playerX = SCREEN_WIDTH/2-30;
		playerY = SCREEN_HEIGHT/2-30;
		
		villainX = (int)(Math.random()*1281-50);
		villainY = (int)(Math.random()*721-50-30);
		
		villain2X = (int)(Math.random()*1281-50);
		villain2Y = (int)(Math.random()*721-50-30);
		
		beforeTime = System.currentTimeMillis();
	}
	public void ballInit() { //�� ��ġ �ʱ�ȭ
		if(isRight == 0) {
			ballX = playerX;
		}else if(isRight == 1) {
			ballX = playerX+60;
		}
	}
	public void paint(Graphics g) {//������۸� �Ҳ���, ȭ�� �����Ӷ�����
		buffImage = createImage(1280,720);
		buffGraphic = buffImage.getGraphics();
		drawScreen(buffGraphic);
		g.drawImage(buffImage, 0,0,null);
	}
	public void drawScreen(Graphics g) {//�������� ������ ��ũ�� �׷��ֱ�, paint�޼ҵ�� �Ѱ��ٰ���
		g.drawImage(background,0,0,null);
		
		g.drawImage(villain, villainX, villainY, null);
		g.drawImage(villain, villain2X, villain2Y, null);
		if(isRight == 1) //0 : ����, 1 : ������
		g.drawImage(player1,playerX,playerY,null);
		if(isRight == 0) //0 : ����, 1 : ������
		g.drawImage(player2, playerX, playerY, null);
		
		
		
		g.setColor(Color.red);
		g.setFont(new Font("serif",Font.BOLD,40));
		g.drawString("SCORE : "+score, 30, 80);
		
		
		    	if(isRight==1) {
		    		if(isSpaceClick == true)//����� �߻�
		    		g.drawImage(attackBall, playerX+40+ballSpeed, playerY, null);
				}else if(isRight==0) {
					if(isSpaceClick == true)
					g.drawImage(attackBall, playerX-60-ballSpeed, playerY, null);
				}
				
			
			
		
		if(score == 1000) {
			
			time = (System.currentTimeMillis() - beforeTime)/1000;
			
			g.setFont(new Font("serif",Font.ITALIC,100));
			g.drawString("�ɸ� �ð� : "+time+"��", SCREEN_WIDTH/2-280, SCREEN_HEIGHT/2);
		}
	}
	public void catchVillain() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT :
				isRight = 1;//0 : ����, 1 : ������
				playerX += playerSpeed;
				repaint();
				break;
			case KeyEvent.VK_LEFT :
				isRight = 0;
				playerX -= playerSpeed;
				repaint();
				break;
			case KeyEvent.VK_UP :
				playerY -= playerSpeed;
				repaint();
				break;
			case KeyEvent.VK_DOWN :
				playerY += playerSpeed;
				repaint();
				break;
			case KeyEvent.VK_SPACE :
				isSpaceClick=true;
//				for(int i = 0; i < 100; i+=10) {
//					try {
//					Thread.sleep(20);
					ballSpeed+=10;
//					}catch(Exception ex) {
//						ex.printStackTrace();
//					}
//				}
				break;
			}
			}
		});
	}
	public void setScore() {
		if(playerX+60>villainX&&playerX<villainX+50&&playerY+60>villainY&&playerY<villainY+50) { //�÷��̾ ���������� ���� �ε����� ���� �ø��� ���� ��ġ �ٸ������� �ű�
			score+=100;
		villainX = (int)(Math.random()*SCREEN_WIDTH-50);
		villainY = (int)(Math.random()*SCREEN_HEIGHT-50);
		repaint();
		} 
	}
	public void setScore2() {
		if(playerX+60>villain2X&&playerX<villain2X+50&&playerY+60>villain2Y&&playerY<villain2Y+50) { //�÷��̾ ���������� ���� �ε����� ���� �ø��� ���� ��ġ �ٸ������� �ű�
			score+=100;
		villain2X = (int)(Math.random()*SCREEN_WIDTH-50);
		villain2Y = (int)(Math.random()*SCREEN_HEIGHT-50);
		repaint();
		} 
	}
	public static void main(String[] args) {
		new GotGame();
	}
	
}
