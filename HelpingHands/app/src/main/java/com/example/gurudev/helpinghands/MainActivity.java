package com.example.gurudev.helpinghands;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_contact, btn_sos, btn_medi, btn_enter, btn_msg, btn_loc;
    TextView showIp,chat;
    String str, msg;
    int serverPort = 10000;
    ServerSocket serverSocket;
    Socket client;
    Handler handler = new Handler();
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip;
        ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        btn_contact = (ImageButton) findViewById(R.id.contact);
        btn_sos = (ImageButton) findViewById(R.id.sos);
        btn_medi = (ImageButton) findViewById(R.id.medi);
        btn_enter = (ImageButton) findViewById(R.id.entertainment);
        btn_msg = (ImageButton) findViewById(R.id.msg);
        btn_loc = (ImageButton) findViewById(R.id.location);
        chat =(TextView) findViewById(R.id.status);
        showIp = (TextView) findViewById(R.id.ip);
        showIp.setText("Server hosted is: " + ip);
        Thread serverThread;
        serverThread = new Thread(new serverThread());
        serverThread.start();

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
                msg = "Please I need a person beside me";
            }
        });
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
                msg = "Please switch on the Television";
            }
        });
        btn_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
                msg = "Please someone contact ASAP";
            }
        });
        btn_medi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
                msg = "Please bring my medicines";
            }
        });
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
                msg = "Please share my message";
            }
        });
        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
                msg = "My Location: ";
            }
        });

    }

    class sentMessage implements Runnable
    {

        @Override
        public void run() {
        try{
            Socket client = serverSocket.accept();
            DataOutputStream os = new DataOutputStream(client.getOutputStream());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    chat.setText(msg);
                }
            });
            os.writeBytes(msg);
            os.flush();
            os.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    public class serverThread implements Runnable{

        @Override
        public void run() {
            try {
                while (true)
                {
                    serverSocket = new ServerSocket(serverPort);
                    Socket client = serverSocket.accept();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            chat.setText("connected");
                        }
                    });
                    DataInputStream in = new
                            DataInputStream(client.getInputStream());
                    String line = null;
                    while((line = in.readLine()) != null)
                    {
                        msg = msg + "\n Client : " + line;
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                chat.setText(msg);
                            }
                        });
                    }
                    in.close();
                    client.close();
                    Thread.sleep(100);
                }


            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();

        }
        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this,"Assist Requested: "+msg ,Toast.LENGTH_LONG).show();

    }
}
