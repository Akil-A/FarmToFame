package no.pxpdev.farmtofame;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private static final String ID_FACTORIES = "factories";
    private static final String ID_LANDS = "lands";
    private static final String ID_CHICKENS = "chickens";
    private static final String ID_PIGS = "pigs";
    private static final String ID_SHEEPS = "sheeps";
    private static final String ID_COWS = "cows";
    private static final String ID_BULLS = "bulls";

    private HashMap<String, UiTimedEvent> mTimedEvents = new HashMap<>();

    private TextView mFarmerTextView;
    private TextView mFactoryTextView;
    private TextView mLandTextView;
    private TextView mChickenTextView;
    private TextView mPigTextView;
    private TextView mSheepTextView;
    private TextView mCowTextView;
    private TextView mBullTextView;
    private TextView mTotalMoney;

    private Button mBuyFarmer;
    private Button mBuyFactory;
    private Button mBuyLand;
    private Button mBuyChicken;
    private Button mBuyPig;
    private Button mBuySheep;
    private Button mBuyCow;
    private Button mBuyBull;

    private int mChickens;
    private int mSum = 10;

    Categories mFarmer;
    Categories mFactories;
    Categories mLand;
    Categories mChicken;
    Categories mPig;
    Categories mSheep;
    Categories mCow;
    Categories mBull;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Set up statistic Views
        mFarmerTextView = (TextView)findViewById(R.id.farmer_money);
        mFactoryTextView = (TextView)findViewById(R.id.factory_money);
        mLandTextView = (TextView)findViewById(R.id.land_size);
        mChickenTextView = (TextView)findViewById(R.id.chicken_money);
        mPigTextView = (TextView)findViewById(R.id.pig_money);
        mSheepTextView = (TextView)findViewById(R.id.sheep_money);
        mCowTextView = (TextView)findViewById(R.id.cow_money);
        mBullTextView = (TextView)findViewById(R.id.bull_money);


        mTotalMoney = (TextView)findViewById(R.id.total_money);

        mFarmerTextView.setText("1");
        //mChickenTextView.setText("1");
        mLandTextView.setText("10");
        mTotalMoney.setText("10");

        //Set up Button listeners
        mBuyFarmer = (Button)findViewById(R.id.buy_farmer);
        mBuyFarmer.setTag(ID_FARMERS);
        mBuyFarmer.setOnClickListener(mBuyListener);

        mBuyFactory = (Button)findViewById(R.id.buy_factory);
        mBuyFarmer.setTag(ID_FACTORIES);
        mBuyFarmer.setOnClickListener(mBuyListener);

        mBuyLand = (Button)findViewById(R.id.buy_land);
        mBuyLand.setTag(ID_LANDS);
        mBuyLand.setOnClickListener(mBuyListener);

        mBuyChicken = (Button)findViewById(R.id.buy_chicken);
        mBuyChicken.setTag(ID_CHICKENS);
        mBuyChicken.setOnClickListener(mBuyListener);

        mBuyPig = (Button)findViewById(R.id.buy_pig);
        mBuyPig.setTag(ID_PIGS);
        mBuyPig.setOnClickListener(mBuyListener);

        mBuySheep = (Button)findViewById(R.id.buy_sheep);
        mBuySheep.setTag(ID_SHEEPS);
        mBuySheep.setOnClickListener(mBuyListener);

        mBuyCow = (Button)findViewById(R.id.buy_cow);
        mBuyCow.setTag(ID_COWS);
        mBuyCow.setOnClickListener(mBuyListener);

        mBuyBull = (Button)findViewById(R.id.buy_bull);
        mBuyBull.setTag(ID_BULLS);
        mBuyBull.setOnClickListener(mBuyListener);

        //Declare Category models
        mFarmer = new Categories(20, 1, 3000, 3);
        mFactories = new Categories(200, 0, 2000, 50);
        mLand = new Categories(100, 1, 5000, 2);
        mChicken = new Categories(10, 1, 1000, 1);
        mPig = new Categories(40, 0, 3000, 60);
        mSheep = new Categories(35, 0, 2000, 50);
        mCow = new Categories(80, 0, 5000, 70);
        mBull = new Categories(200, 0, 6000, 200);

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

            case ID_FACTORIES:
                categoryTimeout = mFactories.getTimeout();
                categoryCounter = mFactories.getCounter();
                categoryBonus = mFactories.getCategoryBonus();
                categoryPrice = mFactories.getPrice();

                if(mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += ++categoryCounter*2;
                }
                else
                    return false;

                mFactoryTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mFactories.setCounter(++categoryCounter);
                mFactories.setPrice(categoryPrice);
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

            case ID_PIGS:
                categoryTimeout = mPig.getTimeout();
                categoryCounter = mPig.getCounter();
                categoryBonus = mPig.getCategoryBonus();
                categoryPrice = mPig.getPrice();

                if(mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter*2;
                }
                else
                    return false;

                mPigTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mPig.setCounter(++categoryCounter);
                mPig.setPrice(categoryPrice);
                break;

            case ID_SHEEPS:
                categoryTimeout = mSheep.getTimeout();
                categoryCounter = mSheep.getCounter();
                categoryBonus = mSheep.getCategoryBonus();
                categoryPrice = mSheep.getPrice();

                if(mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter*2;
                }
                else
                    return false;

                mSheepTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mSheep.setCounter(++categoryCounter);
                mSheep.setPrice(categoryPrice);
                break;

            case ID_COWS:
                categoryTimeout = mCow.getTimeout();
                categoryCounter = mCow.getCounter();
                categoryBonus = mCow.getCategoryBonus();
                categoryPrice = mCow.getPrice();

                if(mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter*2;
                }
                else
                    return false;

                mCowTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mCow.setCounter(++categoryCounter);
                mCow.setPrice(categoryPrice);
                break;

            case ID_BULLS:
                categoryTimeout = mBull.getTimeout();
                categoryCounter = mBull.getCounter();
                categoryBonus = mBull.getCategoryBonus();
                categoryPrice = mBull.getPrice();

                if(mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter*2;
                }
                else
                    return false;

                mBullTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));
                };
                mBull.setCounter(++categoryCounter);
                mBull.setPrice(categoryPrice);
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
