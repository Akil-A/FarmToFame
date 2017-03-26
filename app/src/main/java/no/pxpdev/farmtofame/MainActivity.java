package no.pxpdev.farmtofame;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String FILE_TIMERS_SERIALIZED = "timers.ser";

    private static final String ID_FARMERS = "farmers";
    private static final String ID_CHICKENS = "chickens";
    private static final String ID_LANDS = "lands";

    private HashMap<String, UiTimedEvent> mTimedEvents = new HashMap<>();

    private TextView mFarmerTextView;
    private TextView mChickenTextView;
    private TextView mLandTextView;
    private TextView mTotalMoney;

    private Button buyFarmer;
    private Button buyChicken;
    private Button buyLand;

    private int mChickens;
    private int mSum = 10;

    Categories mChicken;
    Categories mFarmer;
    Categories mLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Set up statistic Views
        mFarmerTextView = (TextView)findViewById(R.id.farmer_money);
        mChickenTextView = (TextView)findViewById(R.id.chicken_money);
        mLandTextView = (TextView)findViewById(R.id.land_size);
        mTotalMoney = (TextView)findViewById(R.id.total_money);

        mFarmerTextView.setText("1");
        //mChickenTextView.setText("1");
        mLandTextView.setText("10");
        mTotalMoney.setText("10");

        //Set up Button listeners
        buyFarmer = (Button)findViewById(R.id.buy_farmer);
        buyFarmer.setTag(ID_FARMERS);
        buyFarmer.setOnClickListener(mBuyListener);

        buyChicken = (Button)findViewById(R.id.buy_chicken);
        buyChicken.setTag(ID_CHICKENS);
        buyChicken.setOnClickListener(mBuyListener);

        buyLand = (Button)findViewById(R.id.buy_land);
        buyLand.setTag(ID_LANDS);
        buyLand.setOnClickListener(mBuyListener);

        mChicken = new Categories(10, 1, 1000, 1);
        mFarmer = new Categories(20, 1, 3000, 3);
        mLand = new Categories(100, 1, 5000, 2);

        restoreTimers();
    }

    private View.OnClickListener mBuyListener = v -> {
        String key = (String) v.getTag();
        /*if(!startTimer(key, 0)){
            stopTimer(key);
        }*/

        startTimer(key, 0);
    };

    private boolean startTimer(String id, int initial) {

        Log.d(TAG, "startTimer: " + id + ", " + initial);
      /* */

        UiTimedEvent.Callback callback;
        int categoryTimeout;
        int categoryCounter;
        int categoryPrice;
        int categoryBonus;
        switch(id){
            case ID_FARMERS:
                categoryTimeout = mFarmer.getTimeout();
                categoryCounter = mFarmer.getCounter();
                categoryBonus = mFarmer.getCategoryBonus();
                categoryPrice = mFarmer.getPrice();

                if(mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += ++categoryCounter*2;
                }
                else
                    return false;

                mFarmerTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mFarmer.setCounter(++categoryCounter);
                mFarmer.setPrice(categoryPrice);
                break;

            case ID_CHICKENS:
                categoryTimeout = mChicken.getTimeout();
                categoryCounter = mChicken.getCounter();
                categoryBonus = mChicken.getCategoryBonus();
                categoryPrice = mChicken.getPrice();

                if( mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter*2;
                }
                else
                    return false;

                mChickenTextView.setText(String.valueOf(categoryPrice));
                mChickens = initial;
                callback = (counter) -> {
                    //mChickens++;
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mChicken.setCounter(++categoryCounter);
                mChicken.setPrice(categoryPrice);
                break;

            case ID_LANDS:
                categoryTimeout = mLand.getTimeout();
                categoryCounter = mLand.getCounter();
                categoryBonus = mLand.getCategoryBonus();
                categoryPrice = mLand.getPrice();

                if(mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter*2;
                }
                else
                    return false;

                mLandTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mLand.setCounter(++categoryCounter);
                mLand.setPrice(categoryPrice);
                break;

            default:
                Log.w(TAG, "startTimer: invalid id: " + id);
                return false;
        }

        if(mTimedEvents.containsKey(id)){
            return false;
        } else {
            UiTimedEvent event = new UiTimedEvent(initial, categoryTimeout, callback);
            mTimedEvents.put(id, event);
            event.start();
            return true;
        }

    }

    private boolean stopTimer(String id)
    {
        if(!mTimedEvents.containsKey(id)){
            return false;
        }
        mTimedEvents.get(id).stop();
        mTimedEvents.remove(id);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTimers();
    }

    private void saveTimers()
    {
        Log.d(TAG, "saveTimers");
        try(FileOutputStream fos = openFileOutput(FILE_TIMERS_SERIALIZED, Context.MODE_PRIVATE)){
            HashMap<String, Integer> state = new HashMap<>();
            for(HashMap.Entry<String, UiTimedEvent> entry : mTimedEvents.entrySet()){
                entry.getValue().stop();
                state.put(entry.getKey(), entry.getValue().getCounter());
            }
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(state);
        }
        catch(IOException e){
            Log.e(TAG, "saveTimers failed", e);
        }
        mTimedEvents.clear();
    }

    private void restoreTimers()
    {
        Log.d(TAG, "restoreTimers");
        try(FileInputStream fis = openFileInput(FILE_TIMERS_SERIALIZED)){
            ObjectInputStream ois = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            HashMap<String, Integer> state = (HashMap<String, Integer>) ois.readObject();
            for(String key : state.keySet()){
                startTimer(key, state.get(key));
            }
        }
        catch (ClassNotFoundException e) {
            Log.e(TAG, "saveTimers failed", e);
        }
        catch(FileNotFoundException e){
            // Ignore these, we don't have state to restore
        }
        catch(IOException e){
            Log.e(TAG, "saveTimers failed", e);
        }
    }
}
