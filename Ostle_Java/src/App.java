import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        // ゲーム画面の設定と呼び出し
        GameWindow gw = new GameWindow();
        JFrame obj = new JFrame();
        obj.add(gw);
        obj.setTitle("Ostle");
        obj.setBounds(10 ,10, 450, 550);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}