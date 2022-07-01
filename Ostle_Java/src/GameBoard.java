import java.util.Arrays;

public class GameBoard {
    private int cursor_y = 2;
    private int cursor_x = 3;
    private boolean isTurnOfP1 = true;
    private boolean isStatPushing = false;
    private boolean isStatHole = false;
    private boolean hasOnOther = false;
    private boolean canOperate = false;
    private boolean canPush = false;
    private boolean doPushOtherPiece = false;
    private int numberOfTurns = 0;
    private final int BOARD_X = 5;
    private final int BOARD_Y = 5;
    private final int maxPlayerPiece = 5;
    private final int P1 = 1;
    private final int P2 = 2;
    private final int HOLE = 3;
    private final int NONE = 0;
    private final int OUT = -1;
    private int[][] initBoard = {
        {-1, -1, -1, -1, -1, -1, -1},// y 0
        {-1,  1,  1,  1,  1,  1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  0,  0,  3,  0,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  2,  2,  2,  2,  2, -1},
        {-1, -1, -1, -1, -1, -1, -1},
    }; // x 0
    private int[][] board = {
        {-1, -1, -1, -1, -1, -1, -1},// y 0
        {-1,  1,  1,  1,  1,  1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  0,  0,  3,  0,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  2,  2,  2,  2,  2, -1},
        {-1, -1, -1, -1, -1, -1, -1},
    };
    private int[][] p1ComBoard = {
        {-1, -1, -1, -1, -1, -1, -1},// y 0
        {-1,  1,  1,  1,  1,  1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  0,  0,  3,  0,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  2,  2,  2,  2,  2, -1},
        {-1, -1, -1, -1, -1, -1, -1},
    };
    private int[][] p2ComBoard = {
        {-1, -1, -1, -1, -1, -1, -1},// y 0
        {-1,  1,  1,  1,  1,  1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  0,  0,  3,  0,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  2,  2,  2,  2,  2, -1},
        {-1, -1, -1, -1, -1, -1, -1},
    };
    private int[][] backUpBoard = {
        {-1, -1, -1, -1, -1, -1, -1},// y 0
        {-1,  1,  1,  1,  1,  1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  0,  0,  3,  0,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  2,  2,  2,  2,  2, -1},
        {-1, -1, -1, -1, -1, -1, -1},
    };
    
    // * DEBUG 右 下
    // private int[][] board = {
        // {-1, -1, -1, -1, -1, -1, -1},// y 0
        // {-1,  1,  1,  1,  1,  1, -1},
        // {-1,  0,  0,  0,  0,  0, -1},
        // {-1,  0,  0,  3,  0,  0, -1},
        // {-1,  0,  0,  0,  0,  0, -1},
        // {-1,  2,  2,  2,  2,  2, -1},
        // {-1, -1, -1, -1, -1, -1, -1},
    // }; // x 0

    // * DEBUG 左 上
    // private int[][] board = {
    //     {-1, -1, -1, -1, -1, -1, -1},// y 0
    //     {-1,  2,  2,  2,  2,  1, -1},
    //     {-1,  2,  2,  2,  1,  2, -1},
    //     {-1,  2,  2,  1,  0,  0, -1},
    //     {-1,  2,  1,  0,  0,  2, -1},
    //     {-1,  1,  0,  0,  0,  1, -1},
    //     {-1, -1, -1, -1, -1, -1, -1},
    // }; // x 0

    // * DEBUG 穴
    // private int[][] board = {
        //     {-1, -1, -1, -1, -1, -1, -1},// y 0
        //     {-1,  1,  1,  1,  1,  1, -1},
        //     {-1,  0,  0,  2,  0,  0, -1},
        //     {-1,  0,  1,  3,  1,  0, -1},
        //     {-1,  0,  0,  2,  0,  0, -1},
        //     {-1,  2,  2,  2,  2,  2, -1},
        //     {-1, -1, -1, -1, -1, -1, -1},
        // }; // x 0

    // ゲームの決着判定
    public boolean isFinished() {
        if (piecesOfP1() >= 2 || piecesOfP2() >= 2) return true;
        else return false;
    }
    // プレイヤー１の駒の数の判定
    public int piecesOfP1() {
        int pieceOfP1 = 0;
        for (int y = 1; y <= BOARD_Y; y++) {
            for (int x = 1; x <= BOARD_X; x++) {
                switch (board[y][x]) {
                    case P1:
                        pieceOfP1++;
                        break;
                    default:
                        break;
                }
            }
        }
        return pieceOfP1 = maxPlayerPiece - pieceOfP1;
    }
    // プレイヤー２の駒の数の判定
    public int piecesOfP2() {
        int pieceOfP2 = 0;
        for (int y = 1; y <= BOARD_Y; y++) {
            for (int x = 1; x <= BOARD_X; x++) {
                switch (board[y][x]) {
                    case P2:
                        pieceOfP2++;
                        break;
                    default:
                        break;
                }
            }
        }
        return pieceOfP2 = maxPlayerPiece - pieceOfP2;
    }
    // 二次元配列の上書き ディープコピー処理
    public void overwrite(int[][] array1, int[][] array2) {
        for (int i = 0; i < array2.length; i++) {
            array1[i] = array2[i].clone();
        }
    }
    // 二次元配列(ボード)の比較 おてつき時にボードの状態を直前の状態にする
    public void comparator() {
        // 直近の相手のターンのボードと比較して同じなら巻き戻す
        if (getTurnOfP1()) {
            if (Arrays.deepEquals(board, p2ComBoard)) {
                overwrite(board, backUpBoard);
                setPushOtherPieceToFalse();
                setCanPush();
            } else {
                setTurnOfP1();
                setStatPushing();
            }
        } else {
            if (Arrays.deepEquals(board, p1ComBoard)) {
                overwrite(board, backUpBoard);
                setPushOtherPieceToFalse();
                setCanPush();
            } else {
                setTurnOfP1();
                setStatPushing();
            }
        }
    }
    // 上に駒を移動させるとき駒の入れ替えと移動させる
    public void canPushUp() {
        // 上方向に駒が連続している長さ計算 length
        int length = 0;
        while(true) {
            if (
                board[getCursorY() - length][getCursorX()] != NONE &&
                board[getCursorY() - length][getCursorX()] != OUT &&
                board[getCursorY() - length][getCursorX()] != HOLE
            ) {
                length++;
            } else {
                length--;
                break;
            }
        }
        // 操作中のプレイヤーに対応したボードをバックアップする
        if (getTurnOfP1()) overwrite(p1ComBoard, board);
        else overwrite(p2ComBoard, board);
        overwrite(backUpBoard, board);
        // 一番上から駒をずらしていく
        for (int y = getCursorY() - length; y <= getCursorY(); y++) {
            if (board[y - 1][getCursorX()] == OUT) {
                board[y - 1][getCursorX()] = OUT;
                board[y][getCursorX()] = NONE;
            } else if (board[y - 1][getCursorX()] == HOLE) {
                board[y - 1][getCursorX()] = HOLE;
                board[y][getCursorX()] = NONE;
            } else if (board[y - 1][getCursorX()] == P1) {
                board[y - 1][getCursorX()] = P1;
                board[y][getCursorX()] = NONE;
            } else if (board[y - 1][getCursorX()] == P2) {
                board[y - 1][getCursorX()] = P2;
                board[y][getCursorX()] = NONE;
            } else if (board[y - 1][getCursorX()] == NONE) {
                board[y - 1][getCursorX()] = board[y][getCursorX()];
                board[y][getCursorX()] = NONE;
            } else {
                board[y - 1][getCursorX()] = board[y][getCursorX()];
                board[y][getCursorX()] = NONE;
            }
        }
        // 操作した駒以外に駒を押したときにtrueにする
        if(length > 0) setPushOtherPieceToTrue();
        comparator();
    }   
    // 左に駒を移動させるとき駒の入れ替えと移動させる
    public void canPushLeft() {
        // 左方向に駒が連続している長さ計算 length
        int length = 0;
        while(true) {
            if (
                board[getCursorY()][getCursorX() - length] != NONE &&
                board[getCursorY()][getCursorX() - length] != OUT &&
                board[getCursorY()][getCursorX() - length] != HOLE
            ) {
                length++;
            } else {
                length--;
                break;
            }
        }
        if (getTurnOfP1()) overwrite(p1ComBoard, board);
        else overwrite(p2ComBoard, board);
        overwrite(backUpBoard, board);
        // 一番左から駒をずらしていく
        for (int x = getCursorX() - length; x <= getCursorX(); x++) {
            if (board[getCursorY()][x - 1] == OUT) {
                board[getCursorY()][x - 1] = OUT;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorY()][x - 1] == HOLE) {
                board[getCursorY()][x - 1] = HOLE;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorY()][x - 1] == P1) {
                board[getCursorY()][x - 1] = P1;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorY()][x - 1] == P2) {
                board[getCursorY()][x - 1] = P2;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorY()][x - 1] == NONE) {
                board[getCursorY()][x - 1] = board[getCursorY()][x];
                board[getCursorY()][x] = NONE;
            } else {
                board[getCursorY()][x - 1] = board[getCursorY()][x];
                board[getCursorY()][x] = NONE;
            }
        }
        if(length > 0) setPushOtherPieceToTrue();
        comparator();
    }
    // 右に駒を移動させるとき駒の入れ替えと移動させる
    public void canPushRight() {
        // 右方向に駒が連続している長さ計算 length
        int length = 0;
        while(true) {
            if (
                board[getCursorY()][getCursorX() + length] != NONE &&
                board[getCursorY()][getCursorX() + length] != OUT &&
                board[getCursorY()][getCursorX() + length] != HOLE
            ) {
                length++;
            } else {
                length--;
                break;
            }
        }
        if (getTurnOfP1()) overwrite(p1ComBoard, board);
        else overwrite(p2ComBoard, board);
        overwrite(backUpBoard, board);
        // 一番右から駒をずらしていく
        for (int x = getCursorX() + length; x >= getCursorX(); x--) {
            if (board[getCursorY()][x + 1] == OUT) {
                board[getCursorY()][x + 1] = OUT;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorY()][x + 1] == HOLE) {
                board[getCursorY()][x + 1] = HOLE;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorY()][x + 1] == P1) {
                board[getCursorY()][x + 1] = P1;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorY()][x + 1] == P2) {
                board[getCursorY()][x + 1] = P2;
                board[getCursorY()][x] = NONE;
            } else if (board[getCursorX()][x + 1] == NONE) {
                board[getCursorY()][x + 1] = board[getCursorY()][x];
                board[getCursorY()][x] = NONE;
            } else {
                board[getCursorY()][x + 1] = board[getCursorY()][x];
                board[getCursorY()][x] = NONE;
            }
        }
        if(length > 0) setPushOtherPieceToTrue();
        comparator();
    }
    // 下に駒を移動させるとき駒の入れ替えと移動させる
    public void canPushDown() {
        // 下方向に駒が連続している長さ計算 length
        int length = 0;
        while(true) {
            if (
                board[getCursorY() + length][getCursorX()] != NONE &&
                board[getCursorY() + length][getCursorX()] != OUT &&
                board[getCursorY() + length][getCursorX()] != HOLE
            ) {
                length++;
            } else {
                length--;
                break;
            }
        }
        if (getTurnOfP1()) overwrite(p1ComBoard, board);
        else overwrite(p2ComBoard, board);
        overwrite(backUpBoard, board);
        // 一番下から駒をずらしていく
        for (int y = getCursorY() + length; y >= getCursorY(); y--) {
            if (board[y + 1][getCursorX()] == OUT) {
                board[y + 1][getCursorX()] = OUT;
                board[y][getCursorX()] = NONE;
            } else if (board[y + 1][getCursorX()] == HOLE) {
                board[y + 1][getCursorX()] = HOLE;
                board[y][getCursorX()] = NONE;
            } else if (board[y + 1][getCursorX()] == P1) {
                board[y + 1][getCursorX()] = P1;
                board[y][getCursorX()] = NONE;
            } else if (board[y + 1][getCursorX()] == P2) {
                board[y + 1][getCursorX()] = P2;
                board[y][getCursorX()] = NONE;
            } else if(board[y + 1][getCursorX()] == NONE) {
                board[y + 1][getCursorX()] = board[y][getCursorX()];
                board[y][getCursorX()] = NONE;
            } else {
                board[y + 1][getCursorX()] = board[y][getCursorX()];
                board[y][getCursorX()] = NONE;
            }
        }
        if(length > 0) setPushOtherPieceToTrue();
        comparator();
    }
    // 穴が移動できるかの判定
    public boolean canPushHole() {
        if (board[getCursorY()][getCursorX()] == HOLE) {
            if (board[getCursorY() - 1][getCursorX()] == NONE) return true;
            else if (board[getCursorY()][getCursorX() + 1] == NONE) return true;
            else if (board[getCursorY() + 1][getCursorX()] == NONE) return true;
            else if (board[getCursorY()][getCursorX() - 1] == NONE) return true;
            else return false;
        } else {
            return false;
        }
    }
    // 駒とターンがプレイヤー１なら操作権を得る
    public boolean canOperateP1() {
        if (getTurnOfP1() == true && checkPieceCategory() == P1) return true;
        else return false;
    }
    // 駒とターンがプレイヤー２なら操作権を得る
    public boolean canOperateP2() {
        if (getTurnOfP1() == false && checkPieceCategory() == P2) return true;
        else return false;
    }
    // カーソルで選択した駒の種類の判定
    public int checkPieceCategory() {
        if (board[getCursorY()][getCursorX()] == P1) return P1;
        else if (board[getCursorY()][getCursorX()] == P2) return P2;
        else if (board[getCursorY()][getCursorX()] == HOLE) return HOLE;
        else if (board[getCursorY()][getCursorX()] == OUT) return OUT;
        else return NONE;
    }

    public void setTurnOfP1() {
        this.isTurnOfP1 = !isTurnOfP1;
        numberOfTurns++;
    }

    public boolean getTurnOfP1() {
        return this.isTurnOfP1;
    }

    public int getNumberOfTurns() {
        return this.numberOfTurns;
    }

    public void setPushOtherPieceToTrue() {
        this.doPushOtherPiece = true;
    }

    public void setPushOtherPieceToFalse() {
        this.doPushOtherPiece = false;
    }

    public boolean getPushOtherPiece() {
        return this.doPushOtherPiece;
    }

    public void setStatPushing() {
        this.isStatPushing = !isStatPushing;
    }

    public boolean getStatPushing() {
        return this.isStatPushing;
    }

    public void setStatHole() {
        this.isStatHole = !isStatHole;
    }

    public boolean getStatHole() {
        return this.isStatHole;
    }

    public void setCanPush() {
        this.canPush = !canPush;
    }

    public boolean getCanPush() {
        return this.canPush;
    }

    public int getBoard(int y, int x) {
        return this.board[y][x];
    }

    public int getBoardLengthX(int y) {
        return board[y].length;
    }

    public int getBoardLength() {
        return board.length;
    }

    //ボードの初期化
    public void initBoard() {
        overwrite(board, initBoard);
        overwrite(p1ComBoard, initBoard);
        overwrite(p2ComBoard, initBoard);
        overwrite(backUpBoard, initBoard);
        this.cursor_y = 2;
        this.cursor_x = 3;
        this.isTurnOfP1 = true;
        this.numberOfTurns = 0;
    }

    public int getCursorY() {
        return this.cursor_y;
    }

    public int getCursorX() {
        return this.cursor_x;
    }

    // カーソルを1マス上に移動
    public void moveUp() {
        if (board[cursor_y - 1][cursor_x] != OUT) this.cursor_y--;
    }
    // カーソルを１マス左に移動
    public void moveLeft() {
        if (board[cursor_y][cursor_x - 1] != OUT) this.cursor_x--;
    }
    // カーソルを１マス右に移動
    public void moveRight() {
        if (board[cursor_y][cursor_x + 1] != OUT) this.cursor_x++;
    }
    // カーソルを１マス下に移動
    public void moveDown() {
        if (board[cursor_y + 1][cursor_x] != OUT) this.cursor_y++;
    }
    // 選択した駒に対応した操作状態に設定する
    public void pieceSelect() {
        if (canOperateP1()) setStatPushing();
        else if (canOperateP2()) setStatPushing();
        else if (canPushHole()) setStatHole();
        else setCanOperate();
    }

    public boolean getCanOperate() {
        return this.canOperate;
    }

    public void setCanOperate() {
        this.canOperate = !canOperate;
    }

    public void setOnOther() {
        hasOnOther = !hasOnOther;
    }

    public boolean getOnOther() {
        return hasOnOther;
    }
    // 上方向に移動できるか判定 & 移動
    public void pushUpHole() {
        if (board[getCursorY() - 1][getCursorX()] == NONE) {
            board[getCursorY() - 1][getCursorX()] = HOLE;
            board[getCursorY()][getCursorX()] = NONE;
            setTurnOfP1();
            setStatHole();
        } else setCanPush();
    }
    // 左方向に移動できるか判定 & 移動
    public void pushLeftHole() {
        if (board[getCursorY()][getCursorX() - 1] == NONE) {
            board[getCursorY()][getCursorX() - 1] = HOLE;
            board[getCursorY()][getCursorX()] = NONE;
            setTurnOfP1();
            setStatHole();
        } else setCanPush();
    }
    // 右方向に移動できるか判定 & 移動
    public void pushRightHole() {
        if (board[getCursorY()][getCursorX() + 1] == NONE) {
            board[getCursorY()][getCursorX() + 1] = HOLE;
            board[getCursorY()][getCursorX()] = NONE;
            setTurnOfP1();
            setStatHole();
        } else setCanPush();
    }
    // 下方向に移動できるか判定 & 移動
    public void pushDownHole() {
        if (board[getCursorY() + 1][getCursorX()] == NONE) {
            board[getCursorY() + 1][getCursorX()] = HOLE;
            board[getCursorY()][getCursorX()] = NONE;
            setTurnOfP1();
            setStatHole();
        } else setCanPush();
    }
}