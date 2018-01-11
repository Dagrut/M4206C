package info.dagrut.www.chatcliexample;

/*
Public methods of NetHandler :
public NetHandler(String ip, int port, EventsHandler evtHdl)
- ip: The IP address to connect to
- port: The TCP port to connect to
- evtHdl: an implementation of EventsHandler interface, to receive messages & errors from this class

public void sendMessage(final String msg)
=> This method sends the given message string to the server we connected to. If the server is not connected, it will not send messages.
- msg: The message to send to the other side

public void stop()
=> Stops the network connection. All calls to sendMessage will become ineffective.
 */

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;

public class NetHandler extends AsyncTask<Void, Void, Void> {
    public interface EventsHandler {
        void onConnect();
        void onMessage(String msg);
        void onError(Exception reason);
        boolean onDisconnect(); /** < Returns true to reconnect, false otherwise. This is *NOT* called on the UI thread. */
    }

    final private EventsHandler mEvtHdl;
    final private String mIp;
    final private int mPort;
    private Socket mSock = null;
    final private Handler mUIHandler = new Handler(Looper.getMainLooper());
    final private HandlerThread mWriteThread = new HandlerThread("NetHandlerWriterThread");
    final private Handler mWriteHandler;

    private BufferedReader mReader = null;
    private BufferedWriter mWriter = null;

    public NetHandler(String ip, int port, EventsHandler evtHdl) {
        mEvtHdl = evtHdl;
        mIp = ip;
        mPort = port;
        mWriteThread.start();
        mWriteHandler = new Handler(mWriteThread.getLooper());
    }

    public void sendMessage(final String msg) {
        if(mWriter == null)
            return;
        mWriteHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mWriter == null)
                    return;
                try {
                    mWriter.write(msg + "\n");
                    mWriter.flush();
                }
                catch(IOException e) {
                    mEvtHdl.onError(e);
                }
            }
        });
    }

    public void stop() {
        closeAll();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            do {
                mSock = new Socket(mIp, mPort);
                if(mSock.isConnected()) {
                    mReader = new BufferedReader(new InputStreamReader(mSock.getInputStream()));
                    mWriter = new BufferedWriter(new OutputStreamWriter(mSock.getOutputStream()));
                    sendConnected();
                    String line;
                    while (mSock.isConnected()) {
                        line = mReader.readLine();
                        receivedMessage(line);
                    }
                }
                else {
                    sendError(new ConnectException("Could not connect to " + mIp));
                }
            } while(mEvtHdl.onDisconnect());
        }
        catch(Exception e) {
            sendError(e);
        }
        finally {
            if(mSock != null) {
                try {
                    mSock.close();
                    mSock = null;
                }
                catch(IOException e) {
                    sendError(e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        closeAll();
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
        closeAll();
    }

    private void sendError(final Exception ex) {
        mUIHandler.post(new Runnable() {
            public void run() {
                mEvtHdl.onError(ex);
            }
        });
    }

    private void receivedMessage(final String msg) {
        mUIHandler.post(new Runnable() {
            public void run() {
                mEvtHdl.onMessage(msg);
            }
        });
    }

    private void sendConnected() {
        mUIHandler.post(new Runnable() {
            public void run() {
                mEvtHdl.onConnect();
            }
        });
    }

    private void closeAll() {
        if(mReader != null) {
            try {
                mReader.close();
                mReader = null;
            }
            catch(IOException e) {
                sendError(e);
            }
        }
        if(mSock != null) {
            try {
                mSock.close();
                mSock = null;
            }
            catch(IOException e) {
                sendError(e);
            }
        }
        mWriter = null;
    }
}
