package no.pxpdev.farmtofame;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by akil_91 on 25.03.2017.
 */
class UiTimedEvent implements Runnable {

    private final Handler mUiHandler;
    private final Callback mCallback;
    private final int mTimeout;

    private boolean mStarted = false;
    private int mCounter;


    public UiTimedEvent(int initial, int timeout, Callback callback) {
        mUiHandler = new Handler(Looper.getMainLooper());
        mCallback = callback;
        mTimeout = timeout;
        mCounter = initial;
    }

    void start() {
        if(!mStarted) {
            mUiHandler.postDelayed(this, mTimeout);
            mStarted = true;
        }
    }

    public boolean isStarted() {
        return mStarted;
    }

    public int getCounter() {
        return mCounter;
    }

    void stop() {
        if(mStarted) {
            mStarted = false;
            mUiHandler.removeCallbacks(this);
        }
    }

    @Override
    public void run() {
        mCounter++;
        mCallback.onTimedEvent(mCounter);
        if(mStarted){
            mUiHandler.postDelayed(this, mTimeout);
        }
    }

    public interface Callback {
        void onTimedEvent(int counter);
    }
}
