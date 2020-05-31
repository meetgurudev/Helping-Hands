package com.example.gurudev.clienthelpinghand;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    EditText serverIp, smessage;
    TextView chat;
    Button connectPhones, sent;
    String serverIpAddress = "", msg = "", str;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chat = (TextView) findViewById(R.id.msgs);
        serverIp = (EditText) findViewById(R.id.server_ip);
        connectPhones = (Button) findViewById(R.id.connect_phones);
        connectPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverIpAddress = serverIp.getText().toString();
                if (!serverIpAddress.equals("")) {
                    Thread clientThread = new Thread(new ClientThread());
                    clientThread.start();

                }
            }
        });
    }

    public class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    InetAddress serverAddr =
                            InetAddress.getByName(serverIpAddress);
                    Socket socket  = new Socket(serverAddr, 10000);
                    //Toast.makeText(MainActivity.this,"Connected to "+serverIpAddress ,Toast.LENGTH_LONG).show();
                    DataInputStream in = new
                            DataInputStream(socket.getInputStream());
                    String line = null;
                    while ((line = in.readLine()) != null)
                    {
                        msg = msg + "Server : " + line + "\n";
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                chat.setText(msg);
                                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    in.close();
                    socket.close();
                    Thread.sleep(100);
                }
                } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        }
    }


