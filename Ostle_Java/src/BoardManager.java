// import java.awt.event.KeyEvent;
// import java.awt.event.KeyListener;

// import javax.swing.JFrame;

public class BoardManager {
    private GameBoard gb;
    // private boolean isTurnOfP1 = true;
    private int[][] copy_board = gb.getBoard();

    public BoardManager() {
    }

    // public void doTest() {
    //     addKeyListener(this);
    // }

    public boolean canOperate() {
        if (gb.getTurnOfP1() && checkPieceCategory() == 1) {
            return true;
        } else if (!gb.getTurnOfP1() && checkPieceCategory() == 2) {
            return true;
        } else {
            return false;
        }
    }

    public int checkPieceCategory() {
        if (copy_board[gb.getCursorY()][gb.getCursorX()] == 1) {
            return 1;
        } else if (copy_board[gb.getCursorY()][gb.getCursorX()] == 2) {
                return 2;
        } else if (copy_board[gb.getCursorY()][gb.getCursorX()] == 3) {
            return 3;
        } else if (copy_board[gb.getCursorY()][gb.getCursorX()] == -1) {
            return -1;
        } else {
            return 0;
        }
    }
    
    // public boolean getTurnOfP1() {
    //     return this.isTurnOfP1;
    // }

    // public void setTurnIdP1() {
    //     this.isTurnOfP1 = !isTurnOfP1;
    // }

    // @Override
    // public void keyTyped(KeyEvent e) {}

    // @Override
    // public void keyPressed(KeyEvent e) {}

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     switch (e.getKeyCode()) {
    //         case KeyEvent.VK_UP:
    //             pushUp();
    //             break;
    //         case KeyEvent.VK_LEFT:
    //             pushLeft();
    //             break;
    //         case KeyEvent.VK_RIGHT:
    //             pushRight();
    //             break;
    //         case KeyEvent.VK_DOWN:
    //             pushDown();
    //             break;
    //         default:
    //             onOtherPressed();
    //             break;
    //     }
    // }

    // private void pushUp() {
    //     copy_board[gb.getCursorY() - 1][gb.getCursorX()] = copy_board[gb.getCursorY()][gb.getCursorX()];
    //     copy_board[gb.getCursorY()][gb.getCursorX()] = 0;
    //     gb.drawBoard();
    // }

    // private void pushLeft() {
    //     copy_board[gb.getCursorY()][gb.getCursorX() - 1] = copy_board[gb.getCursorY()][gb.getCursorX()];
    //     copy_board[gb.getCursorY()][gb.getCursorX()] = 0;
    //     gb.drawBoard();
    // }

    // private void pushRight() {
    //     copy_board[gb.getCursorY()][gb.getCursorX() + 1] = copy_board[gb.getCursorY()][gb.getCursorX()];
    //     copy_board[gb.getCursorY()][gb.getCursorX()] = 0;
    //     gb.drawBoard();
    // }

    // private void pushDown() {
    //     copy_board[gb.getCursorY() + 1][gb.getCursorX()] = copy_board[gb.getCursorY()][gb.getCursorX()];
    //     copy_board[gb.getCursorY()][gb.getCursorX()] = 0;
    //     gb.drawBoard();
    // }

    // private void onOtherPressed() {
    //     gb.drawBoard();
    //     System.out.println("矢印キーで操作できます");
    // }
}
