import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.text.DecimalFormat;

public class Tetoris extends JPanel {

    
    int speed = 300;

    int sizeX = 10;
    int sizeY = 20;

    int blocksize = 30;

    int tetroSize = 4;

    //block zahyou
    int tetroX = 3;
    int tetroY = 0;

    int Score = 0;

    boolean gameover = false;


int[][] tetro = {
	    { 0, 1, 0, 0},
	    { 0, 1, 0, 0},
	    { 0, 1, 0, 0},
	    { 0, 1, 0, 0}
};

   int [][][] randomTetro = {
       {
	    { 0, 1, 0, 0},
	    { 0, 1, 0, 0},
	    { 0, 1, 0, 0},
	    { 0, 1, 0, 0}
    },
       {
	    { 0, 0, 0, 0},
        { 0, 2, 2, 0},
        { 0, 2, 2, 0},
        { 0, 0, 0, 0}
},
       {
	    { 0, 0, 0, 0},
	    { 0, 3, 3, 0},
	    { 3, 3, 0, 0},
	    { 0, 0, 0, 0}
},
   		{
       	{ 0, 4, 0, 0},
 	    { 4, 4, 4, 0},
	    { 0, 0, 0, 0},
	    { 0, 0, 0, 0}
}, 
   		{
       	{ 0, 5, 0, 0},
 	    { 0, 5, 5, 5},
	    { 0, 0, 0, 0},
	    { 0, 0, 0, 0}
}, 
   		
   
   };

    Random rand = new Random();
    int randomInt = rand.nextInt(randomTetro.length);
	

    int[][] field = new int[sizeY][sizeX];

    long tm0 = System.currentTimeMillis();
    double tm1 = 0.0;
    double tm2;
    long lastUpdateTime = tm0;

    public Tetoris() {
	setOpaque(false);

	new javax.swing.Timer(30, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		    double tm = 0.001*(System.currentTimeMillis() - tm0);
		    long currentTime = System.currentTimeMillis();
		    double elapsedTime = 0.001*(currentTime - lastUpdateTime);
		    
		    if(elapsedTime >= 1.0){
			if( check(tetroX, tetroY+1, tetro)){
			tetroY++;
			repaint();
			}else{
			    for( int y = 0; y < tetroSize; y++ ){
				for( int x = 0; x < tetroSize; x++ ){
				    if( tetro[y][x] == 1 ){
					field[tetroY+y][tetroX+x] = 1;
				    }else if( tetro[y][x] == 2 ){
					field[tetroY+y][tetroX+x] = 2;
				    }else if( tetro[y][x] == 3 ){
					field[tetroY+y][tetroX+x] = 3;
				    }else if( tetro[y][x] == 4 ){
					field[tetroY+y][tetroX+x] = 4;
				    }else if( tetro[y][x] == 5 ){
					field[tetroY+y][tetroX+x] = 5;
				    }else if( tetro[y][x] == 6 ){
					field[tetroY+y][tetroX+x] = 6;
				    }else if( tetro[y][x] == 7 ){
					field[tetroY+y][tetroX+x] = 7;
				    }
				    	
				}
			    }
			    tetroX = 3;
			    tetroY = 0;
			    generateRandomBlock();
				if( field[0][3] >= 1 || field[0][4] >= 1 || field[0][5] >= 1 || field[0][6] >= 1 ){
				gameover = true;
				System.out.println(gameover);
				
			    }

			}
			lastUpdateTime = currentTime;
		    }
		}
	    }).start();


	
	setFocusable(true); 
    addKeyListener(new KeyAdapter() {
	    public void keyPressed(KeyEvent evt){
		if( gameover ){return;}
		if( evt.getKeyChar() == 'w'){
		    if( check( tetroX, tetroY-1, tetro ) ){
			//tetroY--;
		    }
		}
		if( evt.getKeyChar() == 'a'){
		    if( check( tetroX-1, tetroY, tetro ) ){
		    tetroX--;
		}
		}
		if( evt.getKeyChar() == 's'){
		    if( check( tetroX, tetroY+1, tetro ) ){
		    tetroY++;
		    }else {
		    }
		
		}
		if( evt.getKeyChar() == 'd'){
		    if( check( tetroX+1, tetroY, tetro ) ){
		    tetroX++;
		}
		}
		if( evt.getKeyChar() == ' '){
		    if( check( tetroX, tetroY, tetro ) ){
			tetro = rotate();
		    }
		}

		    repaint();
	    }
	});
    }

    private void generateRandomBlock() {
        Random rand = new Random();
        int randomInt1 = rand.nextInt(randomTetro.length);
        for (int i = 0; i < tetroSize; i++) {
            System.arraycopy(randomTetro[randomInt1][i], 0, tetro[i], 0, tetroSize);
        }
    }
    
    public boolean check( int tX, int tY, int[][] rotateTetro ){
	
	for( int y = 0; y < tetroSize; y++ ){
	    for( int x = 0; x < tetroSize; x++ ){
		int checkX = tX + x;
		int checkY = tY + y;
		if( rotateTetro[y][x] >= 1 ){
		    if( checkX < 0 || checkY < 0 ||
			 checkX >= sizeX || checkY >= sizeY || field[checkY][checkX] >= 1 ){
			return false;
		    }
		}
	    }
	}
	return true;
    }

    public int[][] rotate(){

	int[][] rotateTetro = new int[tetroSize][tetroSize];

	for( int y = 0; y < tetroSize; y++ ){
	    for( int x = 0; x < tetroSize; x++ ){

		rotateTetro[y][x] = tetro[tetroSize-x-1][y];
		
	    }
	}
	return rotateTetro;
	
    }

    public void paintComponent(Graphics g){


	for( int y = 0; y < sizeY; y++ ){
	    for( int x = 0; x < sizeX; x++ ){
		int fx = x * blocksize + 30;
		int fy = y * blocksize + 30;
		if( field[y][x] == 1 ){
		Rect field = new Rect(Color.RED, fx, fy, blocksize, blocksize );
		field.draw(g);
		}else if( field[y][x] == 2 ){
		    Rect field3 = new Rect(Color.BLUE, fx, fy, blocksize, blocksize );
		    field3.draw(g);
		}else if( field[y][x] == 3 ){
		    Rect field4 = new Rect(Color.GREEN, fx, fy, blocksize, blocksize );
		    field4.draw(g);
		}else if( field[y][x] == 4 ){
		    Rect field5 = new Rect(Color.YELLOW, fx, fy, blocksize, blocksize );
		    field5.draw(g);
		}else if( field[y][x] == 5 ){
		    Rect field6 = new Rect(Color.MAGENTA, fx, fy, blocksize, blocksize );
		    field6.draw(g);
		}else if( field[y][x] == 6 ){
		    Rect field7 = new Rect(Color.BLACK, fx, fy, blocksize, blocksize );
		    field7.draw(g);
		}else if( field[y][x] == 7 ){
		    Rect field8 = new Rect(Color.GRAY, fx, fy, blocksize, blocksize );
		    field8.draw(g);
		}else {
		    Rect field2 = new Rect(Color.WHITE, fx, fy, blocksize, blocksize);
		    field2.draw(g);
		}
	    }
	}

	int fx1 = 3 * blocksize + 30;
	int fy1 = 0 * blocksize + 30;
        Rect fi = new Rect(Color.CYAN, fx1, fy1, blocksize * 4, blocksize);
        fi.draw(g);
		    
	
        for( int y = 0; y < tetroSize; y++ ){
	    for( int x = 0; x < tetroSize; x++ ){
            if( tetro[y][x] == 1){
                int px = 30+ (tetroX + x) * blocksize;
                int py = 30 + (tetroY + y) * blocksize;
                Rect r = new Rect(Color.RED, px, py, blocksize, blocksize);
                r.draw(g);
            }
	    if( tetro[y][x] == 2){
                int px = 30+ (tetroX + x) * blocksize;
                int py = 30 + (tetroY + y) * blocksize;
                Rect r2 = new Rect(Color.BLUE, px, py, blocksize, blocksize);
                r2.draw(g);
            }
	    if( tetro[y][x] == 3){
                int px = 30+ (tetroX + x) * blocksize;
                int py = 30 + (tetroY + y) * blocksize;
                Rect r3 = new Rect(Color.GREEN, px, py, blocksize, blocksize);
                r3.draw(g);
            }
	    if( tetro[y][x] == 4){
                int px = 30+ (tetroX + x) * blocksize;
                int py = 30 + (tetroY + y) * blocksize;
                Rect r4 = new Rect(Color.YELLOW, px, py, blocksize, blocksize);
                r4.draw(g);
            }
	    if( tetro[y][x] == 5){
                int px = 30+ (tetroX + x) * blocksize;
                int py = 30 + (tetroY + y) * blocksize;
                Rect r5 = new Rect(Color.MAGENTA, px, py, blocksize, blocksize);
                r5.draw(g);
            }
	    if( tetro[y][x] == 6){
                int px = 30+ (tetroX + x) * blocksize;
                int py = 30 + (tetroY + y) * blocksize;
                Rect r6 = new Rect(Color.BLACK, px, py, blocksize, blocksize);
                r6.draw(g);
            }
	    if( tetro[y][x] == 7){
                int px = 30+ (tetroX + x) * blocksize;
                int py = 30 + (tetroY + y) * blocksize;
                Rect r7 = new Rect(Color.GRAY, px, py, blocksize, blocksize);
                r7.draw(g);
            }
        }
    }

	for( int y = 0; y < sizeY; y++ ){
	    
	    boolean Line = true;
	    for( int x = 0; x < sizeX; x++ ){

		if( field[y][x] == 0 ){
		    Line = false;
	    }
	}
	    if( Line ){
	        Score += 1;
		for( int i = y; i > 0; i-- ){
		    for( int j = 0; j < sizeX; j++ ){
			field[i][j] = field[i-1][j];
		    }
		}
	    }
	}

	String Score1 = String.valueOf(Score);
	Font font2 = new Font("ＭＳ Ｐゴシック",Font.PLAIN,20);
	    g.setFont(font2);
	    g.setColor(Color.RED);
	    g.drawString("SCORE",330, 280);
	    g.drawString(Score1, 350, 300);
	    
	if( gameover == true ){
	    System.out.println("FFFF");
	    
	    Font font1 = new Font("ＭＳ Ｐゴシック",Font.PLAIN,20);
	    g.setFont(font1);
	    g.setColor(Color.RED);
	    g.drawString("GAMEOVER", 150, 300);
    }
	
    }

   
	public static void main(String[] args) {
		JFrame app = new JFrame();
		app.add(new Tetoris());
		app.setSize(400, 800);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}

class Rect {
    Color col;
    int xpos, ypos, width, height;
    public Rect(Color c, int x, int y, int w, int h) {
	col = c; xpos = x; ypos = y; width = w; height = h;
    }

    public void moveTo(int x, int y){
        xpos = x; ypos = y;
    }

    public void draw(Graphics g){
	g.setColor(col);
	g.fillRect(xpos, ypos, width, height);
	g.setColor(Color.BLACK);
	g.drawRect(xpos, ypos, width, height);
    }
}
