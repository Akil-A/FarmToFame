package no.pxpdev.farmtofame;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
    private static final String ID_FACTORIES = "factories";
    private static final String ID_LANDS = "lands";
    private static final String ID_CHICKENS = "chickens";
    private static final String ID_PIGS = "pigs";
    private static final String ID_SHEEPS = "sheeps";
    private static final String ID_COWS = "cows";
    private static final String ID_BULLS = "bulls";
    private static final String ID_EGGS = "sellEggs";
    private static final String ID_MEAT = "sellMeat";
    private static final String ID_WOOL = "sellWool";
    private static final String ID_MILK = "sellMilk";
    private static final String ID_BEEF = "sellBeef";
    private static final String RESET_GAME = "resetGame";


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
    private TextView mAnimalsTextView;
    private TextView mLandsTextView;
    private TextView mEggsTextView;
    private TextView mFactoriesTextView;
    private TextView mWoolTextView;
    private TextView mMeatTextView;
    private TextView mMilkTextView;
    private TextView mBeefTextView;

    private Button mBuyFarmer;
    private Button mBuyFactory;
    private Button mBuyLand;
    private Button mBuyChicken;
    private Button mBuyPig;
    private Button mBuySheep;
    private Button mBuyCow;
    private Button mBuyBull;
    private Button mSellEggs;
    private Button mSellMeat;
    private Button mSellWool;
    private Button mSellMilk;
    private Button mSellBeef;
    private Button mResetGame;

    private int mChickens;
    private double mSum = 10;
    private int mAnimals = 1;
    private int mFarmers = 1;
    private int mLands = 1;
    private int mFactories = 1;

    private double mResetBonus = 1;

    private boolean mPigFactory;
    private boolean mSheepFactory;
    private boolean mCowFactory;
    private boolean mBullFactory;

    private View mView;

    Categories mFarmer;
    Categories mFactory;
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

        mView = findViewById(R.id.activity_main_layout);

        //Set up statistic Views
        mFarmerTextView = (TextView) findViewById(R.id.farmer_money);
        mFactoryTextView = (TextView) findViewById(R.id.factory_money);
        mLandTextView = (TextView) findViewById(R.id.land_size);
        mChickenTextView = (TextView) findViewById(R.id.chicken_money);
        mPigTextView = (TextView) findViewById(R.id.pig_money);
        mSheepTextView = (TextView) findViewById(R.id.sheep_money);
        mCowTextView = (TextView) findViewById(R.id.cow_money);
        mBullTextView = (TextView) findViewById(R.id.bull_money);
        mAnimalsTextView = (TextView) findViewById(R.id.animals_counter_text_view);
        mLandsTextView = (TextView) findViewById(R.id.lands_counter_text_view);
        mEggsTextView = (TextView) findViewById(R.id.egg_counter_text_view);
        mWoolTextView = (TextView) findViewById(R.id.wool_counter_text_view);
        mMeatTextView = (TextView) findViewById(R.id.meat_counter_text_view);
        mMilkTextView = (TextView) findViewById(R.id.milk_counter_text_view);
        mBeefTextView = (TextView) findViewById(R.id.beef_counter_text_view);
        mFactoriesTextView = (TextView) findViewById(R.id.factory_counter_text_view);

        mTotalMoney = (TextView) findViewById(R.id.total_money);

        mFarmerTextView.setText("1");
        //mChickenTextView.setText("1");
        mLandTextView.setText("10");
        mTotalMoney.setText("10");

        //Set up Button listeners
        mBuyFarmer = (Button) findViewById(R.id.buy_farmer);
        mBuyFarmer.setTag(ID_FARMERS);
        mBuyFarmer.setOnClickListener(mBuyListener);

        mBuyFactory = (Button) findViewById(R.id.buy_factory);
        mBuyFactory.setTag(ID_FACTORIES);
        mBuyFactory.setOnClickListener(mBuyListener);

        mBuyLand = (Button) findViewById(R.id.buy_land);
        mBuyLand.setTag(ID_LANDS);
        mBuyLand.setOnClickListener(mBuyListener);

        mBuyChicken = (Button) findViewById(R.id.buy_chicken);
        mBuyChicken.setTag(ID_CHICKENS);
        mBuyChicken.setOnClickListener(mBuyListener);

        mBuyPig = (Button) findViewById(R.id.buy_pig);
        mBuyPig.setTag(ID_PIGS);
        mBuyPig.setOnClickListener(mBuyListener);

        mBuySheep = (Button) findViewById(R.id.buy_sheep);
        mBuySheep.setTag(ID_SHEEPS);
        mBuySheep.setOnClickListener(mBuyListener);

        mBuyCow = (Button) findViewById(R.id.buy_cow);
        mBuyCow.setTag(ID_COWS);
        mBuyCow.setOnClickListener(mBuyListener);

        mBuyBull = (Button) findViewById(R.id.buy_bull);
        mBuyBull.setTag(ID_BULLS);
        mBuyBull.setOnClickListener(mBuyListener);

        mSellEggs = (Button) findViewById(R.id.sell_eggs_button);
        mSellEggs.setTag(ID_EGGS);
        mSellEggs.setOnClickListener(mSellListener);

        mSellMeat = (Button) findViewById(R.id.sell_meat_button);
        mSellMeat.setTag(ID_MEAT);
        mSellMeat.setOnClickListener(mSellListener);

        mSellWool = (Button) findViewById(R.id.sell_wool_button);
        mSellWool.setTag(ID_WOOL);
        mSellWool.setOnClickListener(mSellListener);

        mSellMilk = (Button) findViewById(R.id.sell_milk_button);
        mSellMilk.setTag(ID_MILK);
        mSellMilk.setOnClickListener(mSellListener);

        mSellBeef = (Button) findViewById(R.id.sell_beef_button);
        mSellBeef.setTag(ID_BEEF);
        mSellBeef.setOnClickListener(mSellListener);

        mResetGame = (Button) findViewById(R.id.reset_game);
        mResetGame.setTag(RESET_GAME);
        mResetGame.setOnClickListener(mSellListener);

        //Declare Category models
        mFarmer = new Categories(20, 1, 3000, 3);
        mFactory = new Categories(2, 1, 10000, 50);
        mLand = new Categories(1, 1, 5000, 2);
        mChicken = new Categories(1, 1, 1000, 1);
        mPig = new Categories(1, 0, 3000, 60);
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

    private View.OnClickListener mSellListener = v -> {
        String key = (String) v.getTag();
        /*if(!startTimer(key, 0)){
            stopTimer(key);
        }*/

        sellMaterials(key);
    };

    public void sellMaterials(String id) {
        //TODO Multiplying decimals with int bad idea?

        double materials;

        switch (id) {

            case ID_EGGS:
                materials = mChicken.getMaterialCounter();
                mChicken.setMaterialCounter(0);
                mEggsTextView.setText(String.valueOf(0));
                materials *= 1.5;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.valueOf(mSum));
                break;

            case ID_MEAT:
                materials = mPig.getMaterialCounter();
                mPig.setMaterialCounter(0);
                mMeatTextView.setText(String.valueOf(0));
                materials *= 2.1;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.valueOf(mSum));
                break;

            case ID_WOOL:
                materials = mSheep.getMaterialCounter();
                mSheep.setMaterialCounter(0);
                mWoolTextView.setText(String.valueOf(0));
                materials *= 2.5;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.valueOf(mSum));
                break;

            case ID_MILK:
                materials = mCow.getMaterialCounter();
                mCow.setMaterialCounter(0);
                mMilkTextView.setText(String.valueOf(0));
                materials *= 2.9;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.valueOf(mSum));
                break;

            case ID_BEEF:
                materials = mBull.getMaterialCounter();
                mBull.setMaterialCounter(0);
                mBeefTextView.setText(String.valueOf(0));
                materials *= 3.5;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.valueOf(mSum));
                break;

            case RESET_GAME:
                resetGame();
                break;
        }
    }

    private boolean startTimer(String id, int initial) {

        Log.d(TAG, "startTimer: " + id + ", " + initial);
      /* */


        /*
        *  Timeout = Update after x milli seconds.
        *  Counter = Count how much of each category is bought
        *  CategoryBonus = How much money we get after each Timeout
        * */
        UiTimedEvent.Callback callback;
        int categoryTimeout;
        int categoryCounter;
        int categoryPrice;
        double categoryBonus;
        boolean buyFarmer = (mAnimals / mFarmers) > 24;
        boolean buyLand = (mAnimals / mLands) > 99;
        boolean canBuyFactory = (mLands / mFactories) > 9;

        switch (id) {
            case ID_FARMERS:
                categoryTimeout = mFarmer.getTimeout();
                categoryCounter = mFarmer.getCounter();
                categoryBonus = mFarmer.getCategoryBonus();
                categoryPrice = mFarmer.getPrice();


                if (mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    categoryPrice += ++categoryCounter * 2;
                    mFarmers++;
                   /* if(buyFarmer)
                        mAnimals = 0;*/
                } else
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
                categoryTimeout = mFactory.getTimeout();
                categoryCounter = mFactory.getCounter();
                categoryBonus = mFactory.getCategoryBonus();
                categoryPrice = mFactory.getPrice();

                if (mSum >= categoryPrice && canBuyFactory) {
                    mSum -= categoryPrice;
                    //categoryPrice += ++categoryCounter*2;
                    mFactories++;
                } else {
                    if (!canBuyFactory)
                        showSnackBar("Need to buy more land first");

                    return false;
                }
                mFactoryTextView.setText(String.valueOf(categoryPrice));
                mFactoriesTextView.setText(String.valueOf(mFactories));
                callback = (counter) -> {
                    mSum += categoryBonus;
                    mTotalMoney.setText(String.valueOf(mSum));

                    int materials;


                    materials = (mChicken.getCounter() * mFactory.getCounter()) + mChicken.getMaterialCounter();
                    mEggsTextView.setText(String.valueOf(materials));
                    mChicken.setMaterialCounter(materials + 15);


                    /**
                     * TODO Remove booleans?
                     */

                    if (mSheepFactory) {
                        materials = (mSheep.getCounter() * mFactory.getCounter()) + mSheep.getMaterialCounter();
                        mWoolTextView.setText(String.valueOf(materials));
                        mSheep.setMaterialCounter(materials + 3);
                    }

                    if (mPigFactory) {
                        materials = (mPig.getCounter() * mFactory.getCounter()) + mPig.getMaterialCounter();
                        mMeatTextView.setText(String.valueOf(materials));
                        mPig.setMaterialCounter(materials + 2);
                    }

                    if (mCowFactory) {
                        materials = (mCow.getCounter() * mFactory.getCounter()) + mCow.getMaterialCounter();
                        mMilkTextView.setText(String.valueOf(materials));
                        mCow.setMaterialCounter(materials + 33);
                    }

                    if (mBullFactory) {
                        materials = (mBull.getCounter() * mFactory.getCounter()) + mBull.getMaterialCounter();
                        mBeefTextView.setText(String.valueOf(materials));
                        mBull.setMaterialCounter(materials + 1);
                    }


                };
                /**Set this before callback?... **/
                Toast.makeText(MainActivity.this, "(" + mCow.getCounter() + "*" + mFactory.getCounter() + ")" + " + " + mCow.getMaterialCounter(), Toast.LENGTH_LONG).show();
                mFactory.setCounter(++categoryCounter);
                mFactory.setPrice(categoryPrice);
                break;

            /*
             *TODO bonus for LAND?
             * mResetBonus won't work for categoryBonus = 1 because of rounding
             */
            case ID_LANDS:
                categoryTimeout = mLand.getTimeout();
                categoryCounter = mLand.getCounter();
                categoryBonus = mLand.getCategoryBonus();
                categoryPrice = mLand.getPrice();

                if (mSum >= categoryPrice) {
                    mSum -= categoryPrice;
                    //categoryPrice += categoryCounter*2;
                    mLands++;
                } else
                    return false;

                mLandTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus*mResetBonus;
                    mTotalMoney.setText(String.valueOf((int)mSum));
                };
                mLand.setCounter(++categoryCounter);
                mLand.setPrice(categoryPrice);
                break;

            case ID_CHICKENS:
                categoryTimeout = mChicken.getTimeout();
                categoryCounter = mChicken.getCounter();
                categoryBonus = mChicken.getCategoryBonus();
                categoryPrice = mChicken.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter*2;
                    mAnimals++;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");

                    return false;
                }

                mChickenTextView.setText(String.valueOf(categoryPrice));
                mChickens = initial;
                callback = (counter) -> {
                    //mChickens++;
                    mSum += categoryBonus*mResetBonus;
                    mTotalMoney.setText(String.valueOf((int)mSum));
                };
                mChicken.setCounter(++categoryCounter);
                mChicken.setPrice(categoryPrice);
                break;

            case ID_PIGS:
                categoryTimeout = mPig.getTimeout();
                categoryCounter = mPig.getCounter();
                categoryBonus = mPig.getCategoryBonus();
                categoryPrice = mPig.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter * 2;
                    mAnimals++;
                    mPigFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");

                    return false;
                }

                mPigTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus*mResetBonus;
                    mTotalMoney.setText(String.valueOf((int)mSum));
                };
                mPig.setCounter(++categoryCounter);
                mPig.setPrice(categoryPrice);
                break;

            case ID_SHEEPS:
                categoryTimeout = mSheep.getTimeout();
                categoryCounter = mSheep.getCounter();
                categoryBonus = mSheep.getCategoryBonus();
                categoryPrice = mSheep.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter * 2;
                    mAnimals++;
                    mSheepFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");

                    return false;
                }

                mSheepTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus*mResetBonus;
                    mTotalMoney.setText(String.valueOf((int)mSum));
                };
                mSheep.setCounter(++categoryCounter);
                mSheep.setPrice(categoryPrice);
                break;

            case ID_COWS:
                categoryTimeout = mCow.getTimeout();
                categoryCounter = mCow.getCounter();
                categoryBonus = mCow.getCategoryBonus();
                categoryPrice = mCow.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter * 2;
                    mAnimals++;
                    mCowFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");

                    return false;
                }

                mCowTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus*mResetBonus;
                    mTotalMoney.setText(String.valueOf((int)mSum));
                };
                mCow.setCounter(++categoryCounter);
                mCow.setPrice(categoryPrice);
                break;

            case ID_BULLS:
                categoryTimeout = mBull.getTimeout();
                categoryCounter = mBull.getCounter();
                categoryBonus = mBull.getCategoryBonus();
                categoryPrice = mBull.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    categoryPrice += categoryCounter * 2;
                    mAnimals++;
                    mBullFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");

                    return false;
                }

                mBullTextView.setText(String.valueOf(categoryPrice));
                callback = (counter) -> {
                    mSum += categoryBonus*mResetBonus;
                    mTotalMoney.setText(String.valueOf((int)mSum));
                };
                mBull.setCounter(++categoryCounter);
                mBull.setPrice(categoryPrice);
                break;


            default:
                Log.w(TAG, "startTimer: invalid id: " + id);
                return false;
        }

        mAnimalsTextView.setText(String.valueOf(mAnimals));
        mLandsTextView.setText(String.valueOf(mLands));

        if (mTimedEvents.containsKey(id)) {
            return false;
        } else {
            UiTimedEvent event = new UiTimedEvent(initial, categoryTimeout, callback);
            mTimedEvents.put(id, event);
            event.start();
            return true;
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(mView, message, Snackbar.LENGTH_LONG).show();
    }


    private boolean stopTimer(String id) {
        if (!mTimedEvents.containsKey(id)) {
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

    private void saveTimers() {
        Log.d(TAG, "saveTimers");
        try (FileOutputStream fos = openFileOutput(FILE_TIMERS_SERIALIZED, Context.MODE_PRIVATE)) {
            HashMap<String, Integer> state = new HashMap<>();
            for (HashMap.Entry<String, UiTimedEvent> entry : mTimedEvents.entrySet()) {
                entry.getValue().stop();
                state.put(entry.getKey(), entry.getValue().getCounter());
            }
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(state);
        } catch (IOException e) {
            Log.e(TAG, "saveTimers failed", e);
        }
        mTimedEvents.clear();
    }

    private void restoreTimers() {
        Log.d(TAG, "restoreTimers");
        try (FileInputStream fis = openFileInput(FILE_TIMERS_SERIALIZED)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            HashMap<String, Integer> state = (HashMap<String, Integer>) ois.readObject();
            for (String key : state.keySet()) {
                startTimer(key, state.get(key));
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "saveTimers failed", e);
        } catch (FileNotFoundException e) {
            // Ignore these, we don't have state to restore
        } catch (IOException e) {
            Log.e(TAG, "saveTimers failed", e);
        }
    }

    private void resetGame(){

        HashMap<String, Integer> state = new HashMap<>();
        for (HashMap.Entry<String, UiTimedEvent> entry : mTimedEvents.entrySet()) {
            entry.getValue().stop();
            //mTimedEvents.remove(entry.getKey());
        }

        mTimedEvents = new HashMap<>();

        mFarmerTextView.setText("");
        mFactoryTextView.setText("");
        mLandTextView.setText("");
        mChickenTextView.setText("");
        mPigTextView.setText("");
        mSheepTextView.setText("");
        mCowTextView.setText("");
        mBullTextView.setText("");
        mAnimalsTextView.setText("");
        mLandsTextView.setText("");
        mEggsTextView.setText("");
        mWoolTextView.setText("");
        mMeatTextView.setText("");
        mMilkTextView.setText("");
        mBeefTextView.setText("");
        mFactoriesTextView.setText("");
        mTotalMoney.setText(String.valueOf(10));


        mFarmer = new Categories(20, 1, 3000, 3);
        mFactory = new Categories(2, 1, 10000, 50);
        mLand = new Categories(1, 1, 5000, 2);
        mChicken = new Categories(1, 1, 1000, 1);
        mPig = new Categories(1, 0, 3000, 60);
        mSheep = new Categories(35, 0, 2000, 50);
        mCow = new Categories(80, 0, 5000, 70);
        mBull = new Categories(200, 0, 6000, 200);

        mSum = 10;
        mAnimals = 1;
        mFarmers = 1;
        mLands = 1;
        mFactories = 1;

        mResetBonus += 0.02;

        mPigFactory = false;
        mSheepFactory = false;
        mCowFactory = false;
        mBullFactory = false;
    }
}
