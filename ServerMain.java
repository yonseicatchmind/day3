package application;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
 
public class ServerMain extends Application {
	
	TextArea textArea = new TextArea();
    /*
     * ���� ���� Thread�� ȿ�������� �����ϴ� ��ǥ���� ���̺귯�� -> ThreadPool�� ó���ϰ� �Ǹ� Thread���ڸ� �����ϱ� ������
     * ���������� ����� �� �ִ�.
     */
    public static ExecutorService threadPool;
 
    /* ������ Ŭ���̾�Ʈ���� �����Ѵ� -> ������ �迭 */
    public static Vector<ServerClient> clients = new Vector<ServerClient>();
    ServerSocket serverSocket;
 
    /* ������ �������Ѽ� Ŭ���̾�Ʈ�� ������ ��ٸ��� �޼ҵ� */
    public void startServer(String IP, int port) {
        try {
            serverSocket = new ServerSocket();
            /* Ư�� Ŭ���̾�Ʈ�� ������ ��ٸ��� */
            serverSocket.bind(new InetSocketAddress(IP, port));
        } catch (Exception e) {// ���� �߻�
            if (!serverSocket.isClosed()) {// serverSocket�� ���� �ִ� ��Ȳ�� �ƴ϶��
                stopServer();
            }
            return;
        }
        // Ŭ���̾�Ʈ�� �����Ҷ� ���� ��ٸ���.
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();// Ŭ���̾�Ʈ�� ������ ��ٸ���.
                        clients.add(new ServerClient(socket));
                        textArea.appendText("[Ŭ���̾�Ʈ ����]" + socket.getRemoteSocketAddress() + ":"
                                + Thread.currentThread().getName());
                       // System.out.println("[Ŭ���̾�Ʈ ����]" + socket.getRemoteSocketAddress() + ":"
                        //        + Thread.currentThread().getName());
                    } catch (Exception e) {
                        if (!serverSocket.isClosed()) {
                            stopServer();
                        }
                        break;
                    }
                }
 
            }
        };
        /* threadpool�� �ʱ�ȭ �� ����, ù ��° thread�� �־��ش�. */
        threadPool = Executors.newCachedThreadPool();
        threadPool.submit(thread);
    }// startServer()
    
    /* ������ ������ ������Ű�� �޼ҵ� */
    public void stopServer() {
        try {
            // ���� �۵� ���� ��� ���� �ݱ�
            /* Iterator���ؼ� ��� Ŭ���̾�Ʈ�� ���������� ������ �� �ֵ��� �� �� �ִ� */
            Iterator<ServerClient> iterator = clients.iterator();
            while (iterator.hasNext()) {
                ServerClient client = iterator.next();
                client.socket.close();
                iterator.remove();
            }
            // serversocket ��ü �ݱ�
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            // threadPool �����ϱ�
            if (threadPool != null && threadPool.isShutdown()) {
                threadPool.isShutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// stopServer()
    /* ȭ�鼳�� */
    @Override
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5));
        
        
        textArea.setEditable(false);
        textArea.setFont(new Font("�������",15));
        root.setCenter(textArea);
        
        Button toggleButton = new Button("�����ϱ�");
        toggleButton.setMaxWidth(Double.MAX_VALUE);
        BorderPane.setMargin(toggleButton, new Insets(1,0,0,0));
        root.setBottom(toggleButton);
        
        String IP ="127.0.0.1";
        int port = 1010;
        
        toggleButton.setOnAction(event->{
            if(toggleButton.getText().equals("�����ϱ�")) {
                startServer(IP, port);
                Platform.runLater(()->{
                    String message = String.format("[���� ����]\n",IP,port);
                    textArea.appendText(message);
                    toggleButton.setText("�����ϱ�");
                });
            }else {
                stopServer();
                Platform.runLater(()->{
                    String message = String.format("[���� ����]\n",IP,port);
                    textArea.appendText(message);
                    toggleButton.setText("�����ϱ�");
                });
                
            }
        });
        
        Scene scene = new Scene(root,400,400);
        primaryStage.setTitle("Chat Server");
        primaryStage.setOnCloseRequest(event->stopServer());
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }// start()
 
    public static void main(String[] args) {
        launch(args);
    }// main()
}