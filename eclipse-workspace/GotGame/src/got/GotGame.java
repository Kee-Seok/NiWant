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
	Image attackBall = new ImageIcon("src/icon/원기옥.png").getImage();
	int SCREEN_WIDTH = 1280;
	int SCREEN_HEIGHT = 720;
	long beforeTime;
	long time;
	int playerX; //플레이어 크기는 60x60
	int playerY;
	int villainX; //빌런 크기는 50x50
	int villainY;
	int villain2X;
	int villain2Y;
	int ballX;
	int ballY;
	int ballSpeed = 20;//원기옥 속도
	boolean isSpaceClick = false;
	int isRight = 0;//0 : 왼쪽, 1 : 오른쪽
	
	int score = 0;
	int playerSpeed = 10;
	int villainSpeed = 20;
	int dir; //0=위,1=아래,2=왼,3=우,4=오른쪽 위,5=오른쪽 아래,6=왼쪽 위,7=왼쪽 아래
	int dir2;
	Villain[] vill = new Villain[9];
	BallAction ballAction = new BallAction();
	public GotGame() {
		setTitle("곶휴게임");
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
		Thread t = new Thread(ballAction);//이렇게 해도 렉걸리네 ;; 미치것네 스레드를 생성자 안에다가 생성하면 안되는건가?
		t.start();
		while(true) {
		try {
			Thread.sleep(100);
		}catch(Exception e) {
			e.printStackTrace();
		}
		dir = (int)(Math.random()*8);
		dir = (int)(Math.random()*8);
		vill[0].addVillain();//빌런 1
		repaint(); 
		setScore();
		setScore2();
		dir2 = (int)(Math.random()*8);
		vill[1].addVillain2();//빌런 2
		repaint();
//		dir = (int)(Math.random()*8);
//		vill[2].addVillain();//빌런 3
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[3].addVillain();//빌런 4
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[4].addVillain();//빌런 5
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[5].addVillain();//빌런 6
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[6].addVillain();//빌런 7
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[7].addVillain();//빌런 8
//		repaint();
//		dir = (int)(Math.random()*8);
//		vill[8].addVillain();//빌런 9
//		repaint();
		}
	}
	class BallAction implements Runnable{//기공파가 for문 while문 겹쳐서 그런지 스무쓰한 애니매이션이 안나옴 ㅠㅠ 그래서 스레드를 나눠서 해보려고 함
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
			if(dir == 0) {//0=위,1=아래,2=왼,3=우,4=오른쪽 위,5=오른쪽 아래,6=왼쪽 위,7=왼쪽 아래
				if(villainY>0) {
				villainY-=villainSpeed;
				}else {
					dir = 1;//윗쪽 벽에 닿이면 방향을 아래로 바꾼다.
				}
			}else if(dir == 1) {
				if(villainY<SCREEN_HEIGHT-50-30) {
					villainY+=villainSpeed;
				}else {
						dir = 0;//아래쪽 벽에 닿이면 방향을 위로 바꾼다.
					}
			}else if(dir == 2) {
				if(villainX>0) {
					villainX-=villainSpeed;
				}else {
						dir = 3;//왼쪽 벽에 닿이면 방향을 오른쪽으로 바꾼다.
					}
			}else if(dir == 3) {
				if(villainX<SCREEN_WIDTH-50) {
					villainX+=villainSpeed;
				}else {
						dir = 2;//오른쪽 벽에 닿이면 방향을 왼쪽으로 바꾼다.
					}
			}else if(dir == 4) {
				if(villainX<SCREEN_WIDTH-50&&villainY>0) {
					villainX+=villainSpeed;
					villainY-=villainSpeed;
				}else {
						dir = 7;//우측 위로 가다가 벽에 닿이면 왼쪽 아래로 변경한다.
					}
			}else if(dir == 5) {
				if(villainX<SCREEN_WIDTH-50&&villainY<SCREEN_HEIGHT-50-30) {
					villainX+=villainSpeed;
					villainY+=villainSpeed;
				}else {
						dir = 6;//우측 아래로 가다가 벽에 닿이면 왼쪽 위로 변경한다.
					}
			}else if(dir == 6) {
				if(villainX>0 && villainY>0) {
					villainX-=villainSpeed;
					villainY-=villainSpeed;
				}else {
						dir = 6;//왼쪽 위로 가다가 벽에 닿이면 오른쪽 아래로 변경한다.
					}
			}else if(dir == 7) {
				if(villainX>0 && villainY<SCREEN_HEIGHT-50-30) {
					villainX-=villainSpeed;
					villainY+=villainSpeed;
				}else {
						dir = 4;//왼쪽 아래로 가다가 벽에 닿이면 오른쪽 위로 변경한다.
					}
			}
		}
		public void addVillain2() {
			if(dir2 == 0) {//0=위,1=아래,2=왼,3=우,4=오른쪽 위,5=오른쪽 아래,6=왼쪽 위,7=왼쪽 아래
				if(villain2Y>0) {
				villain2Y-=villainSpeed;
				}else {
					dir2 = 1;//윗쪽 벽에 닿이면 방향을 아래로 바꾼다.
				}
			}else if(dir2 == 1) {
				if(villain2Y<SCREEN_HEIGHT-50-30) {
					villain2Y+=villainSpeed;
				}else {
						dir2 = 0;//아래쪽 벽에 닿이면 방향을 위로 바꾼다.
					}
			}else if(dir2 == 2) {
				if(villain2X>0) {
					villain2X-=villainSpeed;
				}else {
						dir2 = 3;//왼쪽 벽에 닿이면 방향을 오른쪽으로 바꾼다.
					}
			}else if(dir == 3) {
				if(villain2X<SCREEN_WIDTH-50) {
					villain2X+=villainSpeed;
				}else {
						dir2 = 2;//오른쪽 벽에 닿이면 방향을 왼쪽으로 바꾼다.
					}
			}else if(dir == 4) {
				if(villain2X<SCREEN_WIDTH-50&&villain2Y>0) {
					villain2X+=villainSpeed;
					villain2Y-=villainSpeed;
				}else {
						dir2 = 7;//우측 위로 가다가 벽에 닿이면 왼쪽 아래로 변경한다.
					}
			}else if(dir == 5) {
				if(villain2X<SCREEN_WIDTH-50&&villain2Y<SCREEN_HEIGHT-50-30) {
					villain2X+=villainSpeed;
					villain2Y+=villainSpeed;
				}else {
						dir2 = 6;//우측 아래로 가다가 벽에 닿이면 왼쪽 위로 변경한다.
					}
			}else if(dir2 == 6) {
				if(villain2X>0 && villain2Y>0) {
					villain2X-=villainSpeed;
					villain2Y-=villainSpeed;
				}else {
						dir2 = 6;//왼쪽 위로 가다가 벽에 닿이면 오른쪽 아래로 변경한다.
					}
			}else if(dir == 7) {
				if(villain2X>0 && villain2Y<SCREEN_HEIGHT-50-30) {
					villain2X-=villainSpeed;
					villain2Y+=villainSpeed;
				}else {
						dir2 = 4;//왼쪽 아래로 가다가 벽에 닿이면 오른쪽 위로 변경한다.
					}
			}
		}
	}
	public void init() { //곶붕이들의 위치와 물리칠 악당들의 위치 초기화
		playerX = SCREEN_WIDTH/2-30;
		playerY = SCREEN_HEIGHT/2-30;
		
		villainX = (int)(Math.random()*1281-50);
		villainY = (int)(Math.random()*721-50-30);
		
		villain2X = (int)(Math.random()*1281-50);
		villain2Y = (int)(Math.random()*721-50-30);
		
		beforeTime = System.currentTimeMillis();
	}
	public void ballInit() { //공 위치 초기화
		if(isRight == 0) {
			ballX = playerX;
		}else if(isRight == 1) {
			ballX = playerX+60;
		}
	}
	public void paint(Graphics g) {//더블버퍼링 할꺼임, 화면 깜빡임때문에
		buffImage = createImage(1280,720);
		buffGraphic = buffImage.getGraphics();
		drawScreen(buffGraphic);
		g.drawImage(buffImage, 0,0,null);
	}
	public void drawScreen(Graphics g) {//실질적인 프레임 스크린 그려주기, paint메소드로 넘겨줄거임
		g.drawImage(background,0,0,null);
		
		g.drawImage(villain, villainX, villainY, null);
		g.drawImage(villain, villain2X, villain2Y, null);
		if(isRight == 1) //0 : 왼쪽, 1 : 오른쪽
		g.drawImage(player1,playerX,playerY,null);
		if(isRight == 0) //0 : 왼쪽, 1 : 오른쪽
		g.drawImage(player2, playerX, playerY, null);
		
		
		
		g.setColor(Color.red);
		g.setFont(new Font("serif",Font.BOLD,40));
		g.drawString("SCORE : "+score, 30, 80);
		
		
		    	if(isRight==1) {
		    		if(isSpaceClick == true)//원기옥 발사
		    		g.drawImage(attackBall, playerX+40+ballSpeed, playerY, null);
				}else if(isRight==0) {
					if(isSpaceClick == true)
					g.drawImage(attackBall, playerX-60-ballSpeed, playerY, null);
				}
				
			
			
		
		if(score == 1000) {
			
			time = (System.currentTimeMillis() - beforeTime)/1000;
			
			g.setFont(new Font("serif",Font.ITALIC,100));
			g.drawString("걸린 시간 : "+time+"초", SCREEN_WIDTH/2-280, SCREEN_HEIGHT/2);
		}
	}
	public void catchVillain() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT :
				isRight = 1;//0 : 왼쪽, 1 : 오른쪽
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
		if(playerX+60>villainX&&playerX<villainX+50&&playerY+60>villainY&&playerY<villainY+50) { //플레이어가 오른쪽으로 갈때 부딪히면 점수 올리고 빌런 위치 다른곳으로 옮김
			score+=100;
		villainX = (int)(Math.random()*SCREEN_WIDTH-50);
		villainY = (int)(Math.random()*SCREEN_HEIGHT-50);
		repaint();
		} 
	}
	public void setScore2() {
		if(playerX+60>villain2X&&playerX<villain2X+50&&playerY+60>villain2Y&&playerY<villain2Y+50) { //플레이어가 오른쪽으로 갈때 부딪히면 점수 올리고 빌런 위치 다른곳으로 옮김
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
