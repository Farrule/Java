import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;


public class GameWindow extends JPanel implements KeyListener, MouseListener {
    private GameBoard gb;
    private int xCoordinate;
    private int yCoordinate;
    private int pointOfP1 = 0;
    private int pointOfP2 = 0;
    private boolean canStart = false;
    private boolean canSeStop = false;
    private final int P1 = 1;
    private final int P2 = 2;
    private final int HOLE = 3;
    Clip endSE = soundEffect(new File("assets/audio/20220614110208.wav"));
    Clip cancelSE = soundEffect(new File("assets/audio/20220614110155.wav"));
    Clip moveSE = soundEffect(new File("assets/audio/se_sac02.wav"));
    Clip pushSE = soundEffect(new File("assets/audio/se_sac03.wav"));
    Clip selectSE = soundEffect(new File("assets/audio/se_sad07.wav"));
    Clip pointSE = soundEffect(new File("assets/audio/se_sad03.wav"));
    Image arrow = Toolkit.getDefaultToolkit().getImage("assets/image/arrow.png");

    // コンストラクタ
    public GameWindow() {
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        this.gb = new GameBoard();
    }

    // 画面の描画
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g.setColor(Color.black);
        Font turnFont = new Font("MS Pゴシック", Font.BOLD, 30);
        Font tipsFont = new Font("MS Pゴシック", Font.BOLD, 20);
        Font endFont = new Font("MS Pゴシック", Font.BOLD, 40);
        Font pointFont = new Font("MS Pゴシック", Font.ITALIC, 15);
        Font conFont = new Font("MS Pゴシック", Font.ITALIC, 25);
        Font titleFont = new Font("MS Pゴシック", Font.BOLD, 90);
        Font startFont = new Font("MS Pゴシック", Font.BOLD, 40);
        Font minimalFont = new Font("MS Pゴシック", Font.BOLD, 10);
        xCoordinate = 10;
        yCoordinate = 10;
        // * スタート画面
        if (!canStart) {
            // タイトル表示
            g.setFont(titleFont);
            g.setColor(Color.black);
            g.drawString("Ostle", 107, 200);
            // ランダムに駒の塊を表示
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j < 4 - i; j++) {
                    Random r = new Random();
                    int n = r.nextInt(2);
                    switch(n) {
                        case 0:
                            g.drawRect(j * 52, i * 52, 50, 50);
                            break;
                        case 1:
                            g.fillRect(j * 52, i * 52, 51, 51);
                            break;
                    }
                }
            }
            // スタートボタン表示
            g.drawRect(120, 350, 200, 90);
            g.drawRect(115, 345, 210, 100);
            g.setFont(tipsFont);
            g.drawString("クリックして", 162, 380);
            g.setFont(startFont);
            g.drawString("スタート", 140, 418);
        } else {
            // * リザルト画面
            if (gb.isFinished()) {
                // 終了SE再生
                endSE.setFramePosition(0);
                endSE.start();
                // SE 受付止め
                canSeStop = true;
                // ターン数表示
                g.setColor(Color.black);
                g.setFont(tipsFont);
                g.drawString("ターン数: " + gb.getNumberOfTurns(), 23, 20);
                // 勝者表示
                g.setFont(endFont);
                g.setColor(Color.red);
                if (gb.piecesOfP2() >= 2) g.drawString("プレイヤー１の勝利！", 20, 55);
                else g.drawString("プレイヤー２の勝利！", 20, 55);
                // ゲーム終了時のボード表示
                g.setColor(Color.black);
                for (int y = 0; y < 7; y++) {
                    xCoordinate = 10;
                    for (int x = 0; x < 7; x++) {
                        if (gb.getBoard(y, x) == P1) g.drawRect(xCoordinate, yCoordinate, 50, 50);
                        else if (gb.getBoard(y, x) == P2) g.fillRect(xCoordinate, yCoordinate, 51, 51);
                        else if (gb.getBoard(y, x) == HOLE) g.fillOval(xCoordinate, yCoordinate, 50, 50);
                        if (0 < x && x < 6 && 0 < y && y < 6) g.drawRect(xCoordinate - 5, yCoordinate - 5, 60, 60);
                        xCoordinate += 60;
                    }
                    yCoordinate += 60;
                }
                // コンティニューボタン
                g.setFont(conFont);
                g.setColor(Color.black);
                g.drawRect(120, 400, 200, 90);
                g.drawRect(115, 395, 210, 100);
                g.drawString("コンティニュー", 132, 455);
            } else {
                // * ゲーム画面
                g.setFont(minimalFont);
                g.drawString("□ :プレイヤー１ ■ :プレイヤー２ ● :穴 ○ :カーソル", 5, 12);
                g.drawString("ターン数: " + gb.getNumberOfTurns(), 375, 12);
                // ボード表示
                for (int y = 0; y < 7; y++) {
                    xCoordinate = 10;
                    for (int x = 0; x < 7; x++) {
                        if (y == gb.getCursorY() && x == gb.getCursorX()) {
                            if (gb.getBoard(y, x) == P1) g.drawRect(xCoordinate, yCoordinate, 50, 50);
                            else if (gb.getBoard(y, x) == P2) g.fillRect(xCoordinate, yCoordinate, 51, 51);
                            else if (gb.getBoard(y, x) == HOLE) g.fillOval(xCoordinate, yCoordinate, 50, 50);
                            g.drawImage(arrow, xCoordinate + 14, yCoordinate + 50, this);
                        }
                        else if (gb.getBoard(y, x) == P1) g.drawRect(xCoordinate, yCoordinate, 50, 50);
                        else if (gb.getBoard(y, x) == P2) g.fillRect(xCoordinate, yCoordinate, 51, 51);
                        else if (gb.getBoard(y, x) == HOLE) g.fillOval(xCoordinate, yCoordinate, 50, 50);
                        if (0 < x && x < 6 && 0 < y && y < 6) g.drawRect(xCoordinate - 5, yCoordinate - 5, 60, 60);
                        xCoordinate += 60;
                    }
                    yCoordinate += 60;
                }
                // ターン表示
                g.setFont(turnFont);
                if (gb.getTurnOfP1()) g.drawString("プレイヤー１のターン", 10, 430);
                else g.drawString("プレイヤー２のターン", 10, 430);
                // ポイント獲得時にSEを出力
                if (gb.piecesOfP1() != pointOfP1 || gb.piecesOfP2() != pointOfP2) {
                    pointSE.setFramePosition(0);
                    pointSE.start();
                    pointOfP1 = gb.piecesOfP1();
                    pointOfP2 = gb.piecesOfP2();
                }
                // マッチポイントを表示
                g.setFont(pointFont);
                g.drawString("P1 match point:", 65, 55);
                if (0 < pointOfP2) g.drawOval(172, 40, 20, 20);
                g.drawString("P2 match point:", 242, 384);
                if (0 < pointOfP1) g.fillOval(345, 368, 20, 20);
                // カーソルの状態を表示
                g.setFont(turnFont);
                if (gb.getStatPushing() || gb.getStatHole()) {
                    g.setColor(Color.red);
                    g.drawString("プッシュモード", 10, 500);
                } else {
                    g.setColor(Color.blue);
                    g.drawString("カーソルモード", 10, 500);
                }
                // 対応していないキーが入力された時に警告を表示
                g.setFont(tipsFont);
                g.setColor(Color.red);
                if (gb.getOnOther()) {
                    g.drawString("WASDまたは矢印キーで移動できます", 50, 40);
                    gb.setOnOther();
                }
                // 選択した駒が操作できない時に警告を表示
                if (gb.getCanOperate()) {
                    g.drawString("操作できない駒です", 50, 38);
                    gb.setCanOperate();
                }
                // 駒が駒を押したときにランダムでエフェクトを表示
                if (gb.getPushOtherPiece()) {
                    Random rand = new Random();
                    int num = rand.nextInt(3) + 1;
                    switch(num) {
                        case 1:
                            try {
                                BufferedImage image = ImageIO.read(
                                    new File("assets/image/effect1.png"));
                                g2d.drawImage(image, null, 250, 250);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                BufferedImage image = ImageIO.read(
                                    new File("assets/image/effect2.png"));
                                g2d.drawImage(image, null, 0, 0);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                BufferedImage image = ImageIO.read(
                                    new File("assets/image/effect3.png"));
                                g2d.drawImage(image, null, 340, 10);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    gb.setPushOtherPieceToFalse();
                }
            }
        }
    }

    //Not use
    @Override
    public void keyTyped(KeyEvent e) {}
    //Not use
    @Override
    public void keyReleased(KeyEvent e) {}
    // キー入力受付 & 処理
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                onUpPressed();
                break;
            case KeyEvent.VK_W:
                onUpPressed();
                break;
            case KeyEvent.VK_LEFT:
                onLeftPressed();
                break;
            case KeyEvent.VK_A:
                onLeftPressed();
                break;
            case KeyEvent.VK_RIGHT:
                onRightPressed();
                break;
            case KeyEvent.VK_D:
                onRightPressed();
                break;
            case KeyEvent.VK_DOWN:
                onDownPressed();
                break;
            case KeyEvent.VK_S:
                onDownPressed();
                break;
            case KeyEvent.VK_SPACE: // pushモードとcursorモード切り替え
                onSpacePressed();
                break;
            default:
                onOtherPressed();
                break;
        }
    }
    // マウス左クリック処理
    @Override
	public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        if (
            e.getButton() == MouseEvent.BUTTON1 &&
            125 < point.x && point.x <= 325 && 355 < point.y && point.y <= 450 && !canStart
        ) {
            canStart = true;
            endSE.setFramePosition(0);
            endSE.start();
            repaint();
        }
        if (
            e.getButton() == MouseEvent.BUTTON1 &&
            115 < point.x && point.x <= 325 && 395 < point.y && point.y <= 495 && gb.isFinished()
        ) {
            gb.initBoard();
                    endSE.setFramePosition(0);
                    endSE.start();
                    canSeStop = false;
                    repaint();
        }
	}

    // Not use
    @Override
	public void mousePressed(MouseEvent e) {}

    //Not use
	@Override
	public void mouseReleased(MouseEvent e) {}

    // Not use
	@Override
	public void mouseEntered(MouseEvent e) {}

    // Not use
	@Override
	public void mouseExited(MouseEvent e) {}
    // SEフォーマット処理
    public static Clip soundEffect(File path) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){
			AudioFormat af = ais.getFormat();			
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);			
			Clip c = (Clip)AudioSystem.getLine(dataLine);			
			c.open(ais);
			
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
    // 入力方向に駒が動かせるかで出力する音を変える処理
    private void rollBack() {
        if (gb.getCanPush()) {
            cancelSE.setFramePosition(0);
            cancelSE.start();
            gb.setCanPush();
        } else {
            pushSE.setFramePosition(0);
            pushSE.start();
        }
    }
    // w or ↑ キー入力時の処理
    private void onUpPressed() {
        if (gb.getStatPushing()) {
            gb.canPushUp();
            rollBack();
            repaint();
        } else if (gb.getStatHole()) {
            gb.pushUpHole();
            rollBack();
            repaint();
        } else if (!canSeStop) {
            moveSE.setFramePosition(0);
            gb.moveUp();
            repaint();
            moveSE.start();
        }
    }
    // s or ↓ キー入力時の処理
    private void onDownPressed() {
        if (gb.getStatPushing()) {
            gb.canPushDown();
            rollBack();
            repaint();
        } else if (gb.getStatHole()){
            gb.pushDownHole();
            rollBack();
            repaint();
        } else if (!canSeStop) {
            moveSE.setFramePosition(0);
            gb.moveDown();
            repaint();
            moveSE.start();
        }
    }
    // a or ← キー入力時の処理
    private void onLeftPressed() {
        if (gb.getStatPushing()) {
            gb.canPushLeft();
            rollBack();
            repaint();
        } else if (gb.getStatHole()) {
            gb.pushLeftHole();
            rollBack();
            repaint();
        } else if (!canSeStop) {
            moveSE.setFramePosition(0);
            gb.moveLeft();
            repaint();
            moveSE.start();
        }
    }
    // d or → キー入力時の処理
    private void onRightPressed() {
        if (gb.getStatPushing()) {
            gb.canPushRight();
            rollBack();
            repaint();
        } else if (gb.getStatHole()) {
            gb.pushRightHole();
            rollBack();
            repaint();
        } else if (!canSeStop) {
            moveSE.setFramePosition(0);
            gb.moveRight();
            repaint();
            moveSE.start();
        }
    }
    // スペースキー入力時の処理
    private void onSpacePressed() {
        gb.pieceSelect();
        if (gb.getCanOperate()) {
            cancelSE.setFramePosition(0);
            cancelSE.start();
        } else {
            selectSE.setFramePosition(0);
            selectSE.start();
        }
        repaint();
    }
    // 処理できるキー入力以外のキー入力時の処理
    private void onOtherPressed() {
        cancelSE.setFramePosition(0);
        gb.setOnOther();
        repaint();
        cancelSE.start();
    }
}