package panels;

import jdk.swing.interop.SwingInterOpUtils;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

public class Arma extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    Image imgArma, imgBersaglio, imgStick1, imgStick2, imgBanco;
    int xImg, yImg;
    int xCentro, yCentro;
    int xMirino = 5000, yMirino = 5000;
    int xStick1 = 0, xStick2 = 0;
    int r;
    Timer t = new Timer(10, this);
    Ellipse2D centro;
    Clip sparo;
    AudioInputStream sparoFile;
    boolean mira = false;

    public Arma() {
        addMouseMotionListener(this);
        addMouseListener(this);

        try {
            File fileArma = new File("C:\\Users\\leona\\OneDrive\\SCUOLA\\4\\java\\arma\\src\\immagini\\arma.png");
            imgArma = ImageIO.read(fileArma);

            File fileBersaglio = new File("C:\\Users\\leona\\OneDrive\\SCUOLA\\4\\java\\arma\\src\\immagini\\bersaglio.png");
            imgBersaglio = ImageIO.read(fileBersaglio);

            File fileStick1 = new File("C:\\Users\\leona\\OneDrive\\SCUOLA\\4\\java\\arma\\src\\immagini\\stick1.png");
            imgStick1 = ImageIO.read(fileStick1);

            File fileStick2 = new File("C:\\Users\\leona\\OneDrive\\SCUOLA\\4\\java\\arma\\src\\immagini\\stick2.png");
            imgStick2 = ImageIO.read(fileStick2);

            File fileBanco = new File("C:\\Users\\leona\\OneDrive\\SCUOLA\\4\\java\\arma\\src\\immagini\\banco.png");
            imgBanco = ImageIO.read(fileBanco);

        } catch (IOException e) {
            System.out.println("IMMAGINE NON TROVATA");
        }

        yImg = 500 - imgArma.getWidth(this);
        t.start();

        //"C:\\Users\\Leonardo\\OneDrive - Moe, Inc\\SCUOLA\\4\\java\\arma\\suoni\\sparo.wav"

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (xStick1 <= 1000 || xStick2 <= 1000) {
            r = (int) (Math.random() * 2);
            xStick1 = xStick1 + r;
            r = (int) (Math.random() * 2);
            xStick2 = xStick1 + r;
        } else {
            xStick1 = 0;
            xStick2 = 0;
        }
        repaint();
    }

    //MOUSE MOTION LISTENER
    @Override
    public void mouseDragged(MouseEvent e) {
        if (mira == true) {
            xCentro = 5000;
            xMirino = e.getX();
            yMirino = e.getY();
        } else {
            xCentro = e.getX();
            yCentro = e.getY();
            xMirino = 5000;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (mira == true) {
            xCentro = 5000;
            xMirino = e.getX();
            yMirino = e.getY();
        } else {
            xCentro = e.getX();
            yCentro = e.getY();
            xMirino = 5000;
        }
    }

    //MOUSE LISTENER
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            try {
                sparo = AudioSystem.getClip();
                sparoFile = AudioSystem.getAudioInputStream(new File("C:\\Users\\leona\\OneDrive\\SCUOLA\\4\\java\\arma\\src\\suoni\\sparo_01.au"));
                sparo.open(sparoFile);
            } catch (LineUnavailableException er1) {
                System.out.println("FILE ILLEGGIBILE");
            } catch (IOException er2) {
                System.out.println("ERRORE DI LETTURA");
            } catch (UnsupportedAudioFileException er3) {
                System.out.println("FILE NON SUPPORTATO");
            }

            sparo.start();
            if ((e.getY() <= 250 && e.getY() >= 200) && (e.getX() >= xStick1 && e.getX() <= xStick1 + 50)) {
                System.out.println("STICK 1 COLPITO");
            }

            if ((e.getY() <= 250 && e.getY() >= 200) && (e.getX() >= xStick2 && e.getX() <= xStick2 + 50)) {
                System.out.println("STICK 2 COLPITO");
            }
        }

        if (e.getButton() == 3) {
            mira = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 3) {
            mira = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //PAINT COMPONENT
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBanco, 0, 250, 1000, 250, this);
        g.drawImage(imgArma, xCentro - imgArma.getWidth(this) / 2, yImg, this);
        //g.drawImage(imgBersaglio, 0, 0, this);
        g.drawImage(imgStick1, xStick1, 200, 50, 50, this);
        g.drawImage(imgStick2, xStick2, 200, 50, 50, this);
        g.drawLine(0, 250, 1000, 250);

        Graphics2D g2 = (Graphics2D) g;
        centro = new Ellipse2D.Double(xCentro - 5, yCentro - 5, 10, 10);
        g2.setColor(Color.red);
        g2.fill(centro);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(4));
        g2.drawOval(xMirino - 100, yMirino - 100, 200, 200);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(xMirino, yMirino - 50, xMirino, yMirino + 50);
        g2.drawLine(xMirino - 50, yMirino, xMirino + 50, yMirino);
    }
}
