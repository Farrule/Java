## dokcer 起動方法

// dockerビルド
% docker-compose build

// dockerをバックグラウンドで起動
% docker-compose up -d

// 確認
% docker-compose ps
       Name          Command   State           Ports
-------------------------------------------------------------
java-spring_java_1   jshell    Up      0.0.0.0:8080->8080/tcp


## コンパイルと起動方法
// インスペクション
% docker-compose exec java bash

// コンパイル
root@5b7be900c329:/usr/src# javac Main.java

// 実行
root@5b7be900c329:/usr/src# java Main
Hello World!
