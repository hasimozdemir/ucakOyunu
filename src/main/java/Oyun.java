
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates {

    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

public class Oyun extends JPanel implements KeyListener, ActionListener {

    private static Object player;

    Timer timer = new Timer((int) 0.9, this);

    private int gecenSure = 0;
    private int harcananates = 0;

    private BufferedImage image;

    private ArrayList<Ates> atesler = new ArrayList<Ates>();

    private int atesdirY = 1;

    private int topX = 0;

    private int topdirX = 2;

    private int ucakX = 0;

    private int ucakdirX = 20;
    
    public boolean Kontrol(){
        for (Ates ates : atesler){
            
            if(new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX , 0,20,20))){
                return true;
            }
            
        }
        return false;
    }

    public Oyun() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("asd.jpeg")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }

        setBackground(Color.BLACK);
        

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gecenSure+=5;
        
        g.setColor(Color.red);

        g.fillOval(topX, 0, 20, 20);

        g.drawImage(image, ucakX, 620, image.getWidth() / 5, image.getHeight() / 5, this);
        
        for(Ates ates : atesler){
            if(ates.getY() < 0){
                atesler.remove(ates);
                        
            }
        }
        g.setColor(Color.RED);
        for(Ates ates : atesler){
            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }
        if(Kontrol()){
            timer.stop();
            String message = "Kazandınız...\n"+
                             "Harcanan Ateş : "+ harcananates+
                             "\nGeçen Süre : " + gecenSure/1000.0 + "saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
        
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (c == KeyEvent.VK_LEFT) {
            if (ucakX <= 0) {
                ucakX = 0;
            } else {
                ucakX -= ucakdirX;
            }

        } else if (c == KeyEvent.VK_RIGHT) {
            if (ucakX >= 760) {
                ucakX = 760;
            } else {
                ucakX += ucakdirX;
            }
        }
        else if (c == KeyEvent.VK_CONTROL){
            atesler.add(new Ates(ucakX+68, 600));
            
            harcananates++;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for(Ates ates : atesler){
            ates.setY(ates.getY()- atesdirY);
        }
        
        topX += topdirX;
        if (topX >= 880) {
            topdirX = -topdirX;
        }
        if (topX <= 0) {
            topdirX = -topdirX;
        }
        repaint();
    }

}
