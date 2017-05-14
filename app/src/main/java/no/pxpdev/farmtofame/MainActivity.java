package no.pxpdev.farmtofame;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;

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

    private static final String FARMER_VIEW = "mFarmerView";
    private static final String FACTORY_VIEW = "mFactoryView";
    private static final String LAND_VIEW = "mLandView";
    private static final String CHICKEN_VIEW = "mChickenView";
    private static final String PIG_VIEW = "mPigView";
    private static final String SHEEP_VIEW = "mSheepView";
    private static final String COW_VIEW = "mCowView";
    private static final String BULL_VIEW = "mBullView";


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

    private TextView mFarmersCounter;
    private TextView mFactoriesCounter;
    private TextView mLandsCounter;
    private TextView mChickensCounter;
    private TextView mPigsCounter;
    private TextView mSheepsCounter;
    private TextView mCowsCounter;
    private TextView mBullsCounter;

    private TextView mEggsCounter;
    private TextView mWoolsCounter;
    private TextView mBaconsCounter;
    private TextView mMilksCounter;
    private TextView mBeefsCounter;

    private Button mBuyFarmer;
    private Button mBuyFactory;
    private Button mBuyLand;
    private Button mBuyChicken;
    private Button mBuyPig;
    private Button mBuySheep;
    private Button mBuyCow;
    private Button mBuyBull;

    private TextView mSellEggs;
    private TextView mSellBacon;
    private TextView mSellWool;
    private TextView mSellMilk;
    private TextView mSellBeef;
    private Button mResetGame;

    private ImageView mFarmerView;
    private ImageView mFactoryView;
    private ImageView mLandView;
    private ImageView mChickenView;
    private ImageView mPigView;
    private ImageView mSheepView;
    private ImageView mCowView;
    private ImageView mBullView;

    private TextView mFarmerClock;
    private TextView mFactoryClock;
    private TextView mLandClock;
    private TextView mChickenClock;
    private TextView mPigClock;
    private TextView mSheepClock;
    private TextView mCowClock;
    private TextView mBullClock;

    private float mSum = 10;
    private float mTempSum = 10;
    private int mAnimals = 1;
    private int mFarmers = 1;
    private int mLands = 1;
    private int mFactories = 1;

    private double mResetBonus = 1;

    private boolean mPigFactory;
    private boolean mSheepFactory;
    private boolean mCowFactory;
    private boolean mBullFactory;
    private boolean mFactoryOn;
    private boolean mDidReset;

    private boolean mIsFarmerCaretaker;
    private boolean mIsFactoryCaretaker;
    private boolean mIsLandCaretaker;
    private boolean mIsChichkenCaretaker;
    private boolean mIsPigCaretaker;
    private boolean mIsSheepCaretaker;
    private boolean mIsCowCaretaker;
    private boolean mIsBullCaretaker;

    private SharedPreferences mCategoriesPreferences;

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
        mFarmerTextView = (TextView) findViewById(R.id.farmer_income);
        mFactoryTextView = (TextView) findViewById(R.id.factory_income);
        mLandTextView = (TextView) findViewById(R.id.land_income);
        mChickenTextView = (TextView) findViewById(R.id.chicken_income);
        mPigTextView = (TextView) findViewById(R.id.pig_income);
        mSheepTextView = (TextView) findViewById(R.id.sheep_income);
        mCowTextView = (TextView) findViewById(R.id.cow_income);
        mBullTextView = (TextView) findViewById(R.id.bull_income);

        mEggsCounter = (TextView) findViewById(R.id.egg_icon);
        mWoolsCounter = (TextView) findViewById(R.id.wool_icon);
        mBaconsCounter = (TextView) findViewById(R.id.bacon_icon);
        mMilksCounter = (TextView) findViewById(R.id.milk_icon);
        mBeefsCounter = (TextView) findViewById(R.id.beef_icon);

        mFarmersCounter = (TextView)findViewById(R.id.farmer_counter);
        mFactoriesCounter = (TextView) findViewById(R.id.factory_counter);
        mLandsCounter = (TextView) findViewById(R.id.land_counter);
        mChickensCounter = (TextView)findViewById(R.id.chicken_counter);
        mPigsCounter = (TextView)findViewById(R.id.pig_counter);
        mSheepsCounter = (TextView)findViewById(R.id.sheep_counter);
        mCowsCounter = (TextView)findViewById(R.id.cow_counter);
        mBullsCounter = (TextView)findViewById(R.id.bull_counter);

        mFarmerClock = (TextView) findViewById(R.id.clock_farmer);
        mFactoryClock = (TextView) findViewById(R.id.clock_factory);
        mLandClock = (TextView) findViewById(R.id.clock_land);
        mChickenClock = (TextView) findViewById(R.id.clock_chicken);
        mPigClock = (TextView) findViewById(R.id.clock_pig);
        mSheepClock = (TextView) findViewById(R.id.clock_sheep);
        mCowClock = (TextView) findViewById(R.id.clock_cow);
        mBullClock = (TextView) findViewById(R.id.clock_bull);


        mTotalMoney = (TextView) findViewById(R.id.total_money);

        mCategoriesPreferences = getPreferences(MODE_PRIVATE);

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

        mSellEggs = (TextView) findViewById(R.id.sell_eggs);
        mSellEggs.setTag(ID_EGGS);
        mSellEggs.setOnClickListener(mSellListener);

        mSellBacon = (TextView) findViewById(R.id.sell_bacon);
        mSellBacon.setTag(ID_MEAT);
        mSellBacon.setOnClickListener(mSellListener);

        mSellWool = (TextView) findViewById(R.id.sell_wool);
        mSellWool.setTag(ID_WOOL);
        mSellWool.setOnClickListener(mSellListener);

        mSellMilk = (TextView) findViewById(R.id.sell_milk);
        mSellMilk.setTag(ID_MILK);
        mSellMilk.setOnClickListener(mSellListener);

        mSellBeef = (TextView) findViewById(R.id.sell_beef);
        mSellBeef.setTag(ID_BEEF);
        mSellBeef.setOnClickListener(mSellListener);

        mFarmerView = (ImageView)findViewById(R.id.farmer_icon);
        mFarmerView.setTag(FARMER_VIEW);
        mFarmerView.setOnClickListener(mCategoryListener);

        mFactoryView = (ImageView)findViewById(R.id.factory_icon);
        mFactoryView.setTag(FACTORY_VIEW);
        mFactoryView.setOnClickListener(mCategoryListener);

        mLandView = (ImageView)findViewById(R.id.land_icon);
        mLandView.setTag(LAND_VIEW);
        mLandView.setOnClickListener(mCategoryListener);

        mChickenView = (ImageView)findViewById(R.id.chicken_icon);
        mChickenView.setTag(CHICKEN_VIEW);
        mChickenView.setOnClickListener(mCategoryListener);

        mPigView = (ImageView)findViewById(R.id.pig_icon);
        mPigView.setTag(PIG_VIEW);
        mPigView.setOnClickListener(mCategoryListener);

        mSheepView = (ImageView)findViewById(R.id.sheep_icon);
        mSheepView.setTag(SHEEP_VIEW);
        mSheepView.setOnClickListener(mCategoryListener);

        mCowView = (ImageView)findViewById(R.id.cow_icon);
        mCowView.setTag(COW_VIEW);
        mCowView.setOnClickListener(mCategoryListener);

        mBullView = (ImageView)findViewById(R.id.bull_icon);
        mBullView.setTag(BULL_VIEW);
        mBullView.setOnClickListener(mCategoryListener);

        //Declare Category models
        mFarmer = new Categories(20, 1, 3000, 3);
        mFactory = new Categories(2, 1, 10000, 50);
        mLand = new Categories(1, 1, 5000, 2);
        mChicken = new Categories(1, 1, 0, 5);
        mPig = new Categories(5, 0, 0, 30);
        mSheep = new Categories(35, 0, 0, 50);
        mCow = new Categories(80, 0, 0, 70);
        mBull = new Categories(200, 0, 0, 200);

        restoreTimers();

        if(restoreCategories()) {
            //Using this so snackbar in factories doesn't come up after reset
        } else {
            mFarmerTextView.setText("1");
            //mChickenTextView.setText("1");
            mLandTextView.setText("10");
            mTotalMoney.setText(String.format("%.2f", mSum));
        }
    }

    private View.OnClickListener mCategoryListener = v -> {
        String key = (String) v.getTag();
        /*if(!startTimer(key, 0)){
            stopTimer(key);
        }*/

        mDidReset = false;
        startTimer(key, 0);
    };

    private View.OnClickListener mSellListener = v -> {
        String key = (String) v.getTag();
        /*if(!startTimer(key, 0)){
            stopTimer(key);
        }*/

        sellMaterials(key);
    };

    private View.OnClickListener mBuyListener = v -> {
        String key = (String) v.getTag();
        /*if(!startTimer(key, 0)){
            stopTimer(key);
        }*/

        buyListener(key);
    };

    private void sellMaterials(String id) {

        double materials;

        switch (id) {

            case ID_EGGS:
                materials = mChicken.getMaterialCounter();
                Log.d("eggs", String.valueOf(materials));
                mChicken.setMaterialCounter(0);
                mEggsCounter.setText(String.valueOf(0));
                materials *= 1.5;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.format("%.2f", mSum));
                break;

            case ID_MEAT:
                materials = mPig.getMaterialCounter();
                mPig.setMaterialCounter(0);
                mBaconsCounter.setText(String.valueOf(0));
                materials *= 2.1;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.format("%.2f", mSum));
                break;

            case ID_WOOL:
                materials = mSheep.getMaterialCounter();
                mSheep.setMaterialCounter(0);
                mWoolsCounter.setText(String.valueOf(0));
                materials *= 2.5;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.format("%.2f", mSum));
                break;

            case ID_MILK:
                materials = mCow.getMaterialCounter();
                mCow.setMaterialCounter(0);
                mMilksCounter.setText(String.valueOf(0));
                materials *= 2.9;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.format("%.2f", mSum));
                break;

            case ID_BEEF:
                materials = mBull.getMaterialCounter();
                mBull.setMaterialCounter(0);
                mBeefsCounter.setText(String.valueOf(0));
                materials *= 3.5;
                mSum += materials*mResetBonus;
                mTotalMoney.setText(String.format("%.2f", mSum));
                break;

            case RESET_GAME:
                resetGame();
                break;
        }
    }

    private void buyListener(String id) {

        boolean buyFarmer = (mAnimals / mFarmers) > 24;
        boolean buyLand = (mAnimals / mLands) > 99;
        boolean canBuyFactory = (mLands / mFactories) > 9;

        int categoryCounter;
        int categoryPrice;
        double categoryBonus;

        switch (id) {

            case ID_FARMERS:
                categoryCounter = mFarmer.getCounter();
                categoryBonus = mFarmer.getCategoryBonus();
                categoryPrice = mChicken.getPrice();

                if (mSum >= categoryPrice) {
                    if(!isTimerStarted(id))
                        reStartTimer(id);
                    mSum -= categoryPrice;
                    categoryPrice += ++categoryCounter * 2;
                    mFarmers++;
                   /* if(buyFarmer)
                        mAnimals = 0;*/
                } else
                    return;

                mTotalMoney.setText(String.format("%.2f", mSum));
                mFarmer.setCounter(++categoryCounter);
                mFarmer.setPrice(categoryPrice);
                mFarmersCounter.setText(String.valueOf(categoryCounter));
                mBuyFarmer.setText(String.valueOf(categoryPrice));
                mFarmerTextView.setText(String.valueOf(categoryBonus));
                break;

            case ID_FACTORIES:

                categoryCounter = mFactory.getCounter();
                categoryBonus = mFactory.getCategoryBonus();
                categoryPrice = mFactory.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    Log.d("buySum", "categoryPrice: " + categoryPrice + "  mSum: " + mSum);
                    categoryPrice += categoryCounter*2;
                    mFactoryOn = true;
                } else {
                    if (!canBuyFactory && !mDidReset)
                        showSnackBar("Need to buy more land first");
                    return;
                }

                mTotalMoney.setText(String.format("%.2f", mSum));
                mFactory.setCounter(++categoryCounter);
                mFactory.setPrice(categoryPrice);
                mFactoriesCounter.setText(String.valueOf(categoryCounter));
                mBuyFactory.setText(String.valueOf(categoryPrice));
                mFactoryTextView.setText(String.valueOf(categoryBonus));
                break;

            case ID_LANDS:

                categoryCounter = mLand.getCounter();
                categoryBonus = mLand.getCategoryBonus();
                categoryPrice = mLand.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    Log.d("buySum", "categoryPrice: " + categoryPrice + "  mSum: " + mSum);
                    categoryPrice += categoryCounter*2;
                } else
                    return;

                mTotalMoney.setText(String.format("%.2f", mSum));
                mLand.setCounter(++categoryCounter);
                mLand.setPrice(categoryPrice);
                mLandsCounter.setText(String.valueOf(categoryCounter));
                Log.d("landCounter", "mLandsCounter: " + mLandsCounter);
                mBuyLand.setText(String.valueOf(categoryPrice));
                mLandTextView.setText(String.valueOf(categoryBonus));
                break;

            case ID_CHICKENS:

                categoryCounter = mChicken.getCounter();
                categoryBonus = mChicken.getCategoryBonus();
                categoryPrice = mChicken.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    Log.d("ID_CHICKEN", "categoryPrice: " + categoryPrice + "  mSum: " + mSum);
                    categoryPrice += categoryCounter*2;
                    mAnimals++;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");
                    Log.d("false", "categoryPrice: " + categoryPrice + " mSum: " + mSum);
                    return;
                }

                mTotalMoney.setText(String.format("%.2f", mSum));
                mChicken.setCounter(++categoryCounter);
                mChickensCounter.setText(String.valueOf(categoryCounter));
                mBuyChicken.setText(String.valueOf(categoryPrice));
                mChicken.setPrice(categoryPrice);
                mChickenTextView.setText(String.valueOf(categoryBonus));
                break;

            case ID_PIGS:

                categoryCounter = mPig.getCounter();
                categoryBonus = mPig.getCategoryBonus();
                categoryPrice = mPig.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    Log.d("buySum", "categoryPrice: " + categoryPrice + "  mSum: " + mSum);
                    categoryPrice += categoryCounter*2;
                    mAnimals++;
                    mPigFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");
                    Log.d("false", "false");
                    return;
                }

                mTotalMoney.setText(String.format("%.2f", mSum));
                mPig.setCounter(++categoryCounter);
                mPig.setPrice(categoryPrice);
                mPigsCounter.setText(String.valueOf(categoryCounter));
                mBuyPig.setText(String.valueOf(categoryPrice));
                mPigTextView.setText(String.valueOf(categoryBonus));
                break;

            case ID_SHEEPS:

                categoryCounter = mSheep.getCounter();
                categoryBonus = mSheep.getCategoryBonus();
                categoryPrice = mSheep.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    Log.d("buySum", "categoryPrice: " + categoryPrice + "  mSum: " + mSum);
                    categoryPrice += categoryCounter*2;
                    mAnimals++;
                    mSheepFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");
                    Log.d("false", "false");
                    return;
                }

                mTotalMoney.setText(String.format("%.2f", mSum));
                mSheep.setCounter(++categoryCounter);
                mSheep.setPrice(categoryPrice);
                mSheepsCounter.setText(String.valueOf(categoryCounter));
                mBuySheep.setText(String.valueOf(categoryPrice));
                mSheepTextView.setText(String.valueOf(categoryBonus));
                break;

            case ID_COWS:

                categoryCounter = mCow.getCounter();
                categoryBonus = mCow.getCategoryBonus();
                categoryPrice = mCow.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    Log.d("buySum", "categoryPrice: " + categoryPrice + "  mSum: " + mSum);
                    categoryPrice += categoryCounter*2;
                    mAnimals++;
                    mCowFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");
                    Log.d("false", "false");
                    return;
                }

                mTotalMoney.setText(String.format("%.2f", mSum));
                mCow.setCounter(++categoryCounter);
                mCow.setPrice(categoryPrice);
                mCowsCounter.setText(String.valueOf(categoryCounter));
                mBuyCow.setText(String.valueOf(categoryPrice));
                mCowTextView.setText(String.valueOf(categoryBonus));
                break;

            case ID_BULLS:

                categoryCounter = mBull.getCounter();
                categoryBonus = mBull.getCategoryBonus();
                categoryPrice = mBull.getPrice();

                if (mSum >= categoryPrice && !buyFarmer && !buyLand) {
                    mSum -= categoryPrice;
                    Log.d("buySum", "categoryPrice: " + categoryPrice + "  mSum: " + mSum);
                    categoryPrice += categoryCounter*2;
                    mAnimals++;
                    mBullFactory = true;
                } else {
                    if (buyFarmer)
                        showSnackBar("Need to buy farmer");
                    else if (buyLand)
                        showSnackBar("Need to buy land");
                    Log.d("false", "false");
                    return;
                }

                mTotalMoney.setText(String.format("%.2f", mSum));
                mBull.setCounter(++categoryCounter);
                mBull.setPrice(categoryPrice);
                mBullsCounter.setText(String.valueOf(categoryCounter));
                mBuyBull.setText(String.valueOf(categoryPrice));
                mBullTextView.setText(String.valueOf(categoryBonus));
                break;
        }
    }

    private boolean startTimer(String id, int initial) {

        Log.d(TAG, "startTimer: " + id + ", " + initial);

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


        switch (id) {
            case FARMER_VIEW:
                Log.d(TAG, "mFarmerView");
                categoryTimeout = mFarmer.getTimeout();
                categoryBonus = mFarmer.getCategoryBonus();
                categoryPrice = mFarmer.getPrice();

                if (mSum >= categoryPrice) {
                    if(!isTimerStarted(id))
                        reStartTimer(id);
                    mSum -= categoryPrice;
                   /* if(buyFarmer)
                        mAnimals = 0;*/
                } else
                    return false;

                    mFarmerView.setImageResource(R.drawable.farmer_pressed_icon);
                    new Handler().postDelayed(() -> {
                        mFarmerView.setImageResource(R.drawable.farmer_icon);
                    }, 1000);

                callback = (counter) -> {
                    mSum += categoryBonus;
                    if(mTempSum<mSum) {
                        mTempSum = mSum;
                    }
                };
                break;

            case FACTORY_VIEW:

                categoryTimeout = mFactory.getTimeout();
                categoryBonus = mFactory.getCategoryBonus();
                categoryPrice = mFactory.getPrice();

                if (mSum >= categoryPrice) {
                    if(!isTimerStarted(id))
                        reStartTimer(id);
                    mSum -= categoryPrice;
                    //mFactoryOn = true;
                } else
                    return false;


                mFactoryView.setEnabled(false);
                Log.d("beforeHandler", String.valueOf(mFactoryOn));

                mFarmerView.setImageResource(R.drawable.factory_pressed_icon);
                new Handler().postDelayed(() -> {
                    mFarmerView.setImageResource(R.drawable.factory_icon);
                }, 1000);

                callback = (counter) -> {
                    mSum += categoryBonus;

                    /*
                     * TODO Remove shitty booleans
                     */

                    Log.d("beforeFactory", String.valueOf(mFactoryOn));

                    if(mFactoryOn) {

                        int materials;
                        Log.d("insidefactory", "insidefactory");

                        materials = (mChicken.getCounter() * mFactory.getCounter()) + mChicken.getMaterialCounter();
                        mEggsCounter.setText(String.valueOf(materials));
                        mChicken.setMaterialCounter(materials + 15);

                        if (mSheepFactory) {
                            Log.d("mSheepFactory", "mSheepFactory");
                            materials = (mSheep.getCounter() * mFactory.getCounter()) + mSheep.getMaterialCounter();
                            mWoolsCounter.setText(String.valueOf(materials));
                            mSheep.setMaterialCounter(materials + 3);
                        }

                        if (mPigFactory) {
                            materials = (mPig.getCounter() * mFactory.getCounter()) + mPig.getMaterialCounter();
                            mBaconsCounter.setText(String.valueOf(materials));
                            mPig.setMaterialCounter(materials + 2);
                        }

                        if (mCowFactory) {
                            materials = (mCow.getCounter() * mFactory.getCounter()) + mCow.getMaterialCounter();
                            mMilksCounter.setText(String.valueOf(materials));
                            mCow.setMaterialCounter(materials + 33);
                        }

                        if (mBullFactory) {
                            materials = (mBull.getCounter() * mFactory.getCounter()) + mBull.getMaterialCounter();
                            mBeefsCounter.setText(String.valueOf(materials));
                            mBull.setMaterialCounter(materials + 1);
                        }
                    }
                };
                /**Set this before callback?... **/
                break;

            /*
             *TODO bonus for LAND?
             * mResetBonus won't work for categoryBonus = 1 because of rounding
             */

            case LAND_VIEW:
                categoryTimeout = mLand.getTimeout();
                categoryBonus = mLand.getCategoryBonus();
                categoryPrice = mLand.getPrice();

                if (mSum >= categoryPrice) {
                    if(!isTimerStarted(id))
                        reStartTimer(id);
                    mSum -= categoryPrice;
                } else
                    return false;

                mLandView.setImageResource(R.drawable.land_pressed_icon);
                new Handler().postDelayed(() -> {
                    mLandView.setImageResource(R.drawable.land_icon);
                }, 1000);

                callback = (counter) -> {
                    mSum += categoryBonus*mResetBonus;
                    if(mTempSum <mSum) {
                        mTempSum = mSum;
                    }

                };
                break;

            case CHICKEN_VIEW:
                categoryTimeout = mChicken.getTimeout();
                categoryBonus = mChicken.getCategoryBonus();

                if(!isTimerStarted(id))
                    reStartTimer(id);

                mChickenView.setEnabled(false);
                mChickenView.setImageResource(R.drawable.chicken_pressed_icon);
                new Handler().postDelayed(() -> {
                    mChickenView.setEnabled(true);
                    mChickenView.setImageResource(R.drawable.chicken_icon);
                }, 1000);

                smoothClockCounter(1, 0, 1000, mChickenClock);
                callback = (counter) -> {
                    mTempSum = mSum;
                    mSum += categoryBonus*mResetBonus;
                    Log.d("bonusSum", "categoryBonus: " + categoryBonus + "  mSum: " + mSum);
                    if(mTempSum < mSum) {
                        smoothMoneyCounter(mTempSum, mSum, 1000);
                        mTempSum = mSum;
                        pauseTimer(id);
                    }
                };
                mTotalMoney.setText(String.format("%.2f", mSum));
                break;

            case PIG_VIEW:
                categoryTimeout = mPig.getTimeout();
                categoryBonus = mPig.getCategoryBonus();

                if(!isTimerStarted(id))
                    reStartTimer(id);
                    //mPigFactory = true;

                mPigView.setEnabled(false);
                mPigView.setImageResource(R.drawable.pig_pressed_icon);
                new Handler().postDelayed(() -> {
                    mPigView.setEnabled(true);
                    mPigView.setImageResource(R.drawable.pig_icon);
                }, 2000);

                smoothClockCounter(2, 0, 2000, mPigClock);
                callback = (counter) -> {
                    mTempSum = mSum;
                    mSum += categoryBonus*mResetBonus;
                    Log.d("sum", "mTempsum = " + mTempSum + " mSum = " + mSum);
                    if(mTempSum < mSum) {
                        smoothMoneyCounter(mTempSum, mSum, 1000);
                        mTempSum = mSum;
                        pauseTimer(id);
                    }
                };
                mTotalMoney.setText(String.format("%.2f", mSum));
                break;

            case SHEEP_VIEW:
                categoryTimeout = mSheep.getTimeout();
                categoryBonus = mSheep.getCategoryBonus();

                if(!isTimerStarted(id))
                    reStartTimer(id);
                    //mSheepFactory = true;

                mSheepView.setEnabled(false);
                mSheepView.setImageResource(R.drawable.sheep_pressed_icon);
                new Handler().postDelayed(() -> {
                    mSheepView.setEnabled(true);
                    mSheepView.setImageResource(R.drawable.sheep_icon);
                }, 3000);

                smoothClockCounter(3, 0, 3000, mSheepClock);
                callback = (counter) -> {
                    mTempSum = mSum;
                    mSum += categoryBonus*mResetBonus;
                    if(mTempSum <mSum) {
                        smoothMoneyCounter(mTempSum, mSum, 2000);
                        mTempSum = mSum;
                        pauseTimer(id);
                    }
                };
                break;

            case COW_VIEW:
                categoryTimeout = mCow.getTimeout();
                categoryBonus = mCow.getCategoryBonus();

                if(!isTimerStarted(id))
                    reStartTimer(id);
                    //mCowFactory = true;

                mCowView.setEnabled(false);
                mCowView.setImageResource(R.drawable.cow_pressed_icon);
                new Handler().postDelayed(() -> {
                    mCowView.setEnabled(true);
                    mCowView.setImageResource(R.drawable.cow_icon);
                }, 4000);

                smoothClockCounter(4, 0, 4000, mCowClock);
                callback = (counter) -> {
                    mTempSum = mSum;
                    mSum += categoryBonus*mResetBonus;
                    if(mTempSum <mSum) {
                        smoothMoneyCounter(mTempSum, mSum, 2000);
                        mTempSum = mSum;
                        pauseTimer(id);
                    }
                };
                break;

            case BULL_VIEW:
                categoryTimeout = mBull.getTimeout();
                categoryBonus = mBull.getCategoryBonus();
                categoryPrice = mBull.getPrice();

                if(!isTimerStarted(id))
                    reStartTimer(id);
                    //mBullFactory = true;


                mBullView.setEnabled(false);
                mBullView.setImageResource(R.drawable.bull_pressed_icon);
                new Handler().postDelayed(() -> {
                    mBullView.setEnabled(true);
                    mBullView.setImageResource(R.drawable.bull_icon);
                }, 5000);

                smoothClockCounter(5, 0, 5000, mBullClock);
                callback = (counter) -> {
                    mTempSum = mSum;
                    mSum += categoryBonus*mResetBonus;
                    if(mTempSum <mSum) {
                        smoothMoneyCounter(mTempSum, mSum, 2000);
                        mTempSum = mSum;
                        pauseTimer(id);
                    }
                };
                break;

            default:
                Log.w(TAG, "startTimer: invalid id: " + id);
                return false;
        }

        //mAnimalsTextView.setText(String.valueOf(mAnimals));
        //mLandsCounter.setText(String.valueOf(mLands));

        if (mTimedEvents.containsKey(id)) {
            return false;
        } else {
            UiTimedEvent event = new UiTimedEvent(initial, categoryTimeout, callback);
            mTimedEvents.put(id, event);
            event.start();
            return true;
        }
    }

    private void smoothMoneyCounter(float from, float to, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(valueAnimator1 -> mTotalMoney.setText(String.format("%.2f", valueAnimator1.getAnimatedValue())));
        valueAnimator.start();
    }

    private void smoothClockCounter(float from, float to, int duration, TextView textView) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(valueAnimator1 -> textView.setText(String.format("%.2f", valueAnimator1.getAnimatedValue())));
        valueAnimator.start();
    }


    private void showSnackBar(String message) {
        Snackbar.make(mView, message, Snackbar.LENGTH_LONG).show();
    }


    private boolean isTimerStarted(String id) {
        if (!mTimedEvents.containsKey(id)) {
            //return true because first time it doesn't contain key in mTimedEvents
            //and we don't want to try to start the timer when it isn't in mTimedEvents
            return true;
        }
        return mTimedEvents.get(id).isStarted();
        //mTimedEvents.remove(id);
    }

    private boolean reStartTimer(String id) {
        if (!mTimedEvents.containsKey(id)) {
            return false;
        }
        mTimedEvents.get(id).start();
        //mTimedEvents.remove(id);
        return true;
    }

    private boolean pauseTimer(String id) {
        if (!mTimedEvents.containsKey(id)) {
            return false;
        }
        mTimedEvents.get(id).stop();
        //mTimedEvents.remove(id);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTimers();
        saveCategories();
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
            mDidReset = true;
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

    private void saveCategories(){

        SharedPreferences.Editor editor  = mCategoriesPreferences.edit();
        Gson gson = new Gson();

        String farmerJson = gson.toJson(mFarmer);
        editor.putString("farmer", farmerJson);

        String factoryJson = gson.toJson(mFactory);
        editor.putString("factory", factoryJson);

        String landJson = gson.toJson(mLand);
        editor.putString("land", landJson);

        String chickenJson = gson.toJson(mChicken);
        editor.putString("chicken", chickenJson);

        String pigJson = gson.toJson(mPig);
        editor.putString("pig", pigJson);

        String sheepJson = gson.toJson(mSheep);
        editor.putString("sheep", sheepJson);

        String cowJson = gson.toJson(mCow);
        editor.putString("cow", cowJson);

        String bullJson = gson.toJson(mBull);
        editor.putString("bull", bullJson);

        editor.putFloat("sum", mSum);

        editor.putInt("animals", mAnimals);

        editor.putInt("farmers", mFarmers);

        editor.putInt("lands", mLands);

        editor.putInt("factories", mFactories);

        editor.putLong("resetBonus", Double.doubleToRawLongBits(mResetBonus));

        editor.putBoolean("pigfactory", mPigFactory);
        editor.putBoolean("sheepfactory", mSheepFactory);
        editor.putBoolean("cowfactory", mCowFactory);
        editor.putBoolean("bullfactory", mBullFactory);
        editor.putBoolean("factoryon", mFactoryOn);


        editor.apply();

    }

    private boolean restoreCategories() {

        /**
         * TODO dat null checks..
         */

        Gson gson = new Gson();

        String farmerJson = mCategoriesPreferences.getString("farmer", null);
        if(farmerJson == null)
            return false;
        mFarmer = gson.fromJson(farmerJson, Categories.class);


        String factoryJson = mCategoriesPreferences.getString("factory", null);
        if(factoryJson == null)
            return false;
        mFactory = gson.fromJson(factoryJson, Categories.class);

        String landJson = mCategoriesPreferences.getString("land", null);
        if(landJson == null)
            return false;
        mLand = gson.fromJson(landJson, Categories.class);


        String chickenJson = mCategoriesPreferences.getString("chicken", null);
        if(chickenJson == null)
            return false;
        mChicken = gson.fromJson(chickenJson, Categories.class);


        String pigJson = mCategoriesPreferences.getString("pig", null);
        if(pigJson == null)
            return false;
        mPig = gson.fromJson(pigJson, Categories.class);

        String sheepJson = mCategoriesPreferences.getString("sheep", null);
        if(sheepJson == null)
            return false;
        mSheep = gson.fromJson(sheepJson, Categories.class);

        String cowJson = mCategoriesPreferences.getString("cow", null);
        if(cowJson == null)
            return false;
        mCow = gson.fromJson(cowJson, Categories.class);

        String bullJson = mCategoriesPreferences.getString("bull", null);
        if(bullJson == null)
            return false;
        mBull = gson.fromJson(bullJson, Categories.class);

        mSum = mCategoriesPreferences.getFloat("sum", 10);

        mFarmerTextView.setText(String.valueOf(mFarmer.getPrice()));
        mFactoryTextView.setText(String.valueOf(mFactory.getPrice()));
        mLandsCounter.setText(String.valueOf(mFactory.getPrice()));
        mChickenTextView.setText(String.valueOf(mChicken.getPrice()));
        mPigTextView.setText(String.valueOf(mPig.getPrice()));
        mSheepTextView.setText(String.valueOf(mSheep.getPrice()));
        mCowTextView.setText(String.valueOf(mCow.getPrice()));
        mBullTextView.setText(String.valueOf(mBull.getPrice()));

        mFactoriesCounter.setText(String.valueOf(mFactory.getCounter()));
        mLandsCounter.setText(String.valueOf(mLand.getCounter()));

        mAnimals = mCategoriesPreferences.getInt("animals", 0);
        mAnimalsTextView.setText(String.valueOf(mAnimals));

        mEggsCounter.setText(String.valueOf(mChicken.getMaterialCounter()));
        //TODO change meat to bacon
        mBaconsCounter.setText(String.valueOf(mPig.getMaterialCounter()));
        mWoolsCounter.setText(String.valueOf(mSheep.getMaterialCounter()));
        mMilksCounter.setText(String.valueOf(mCow.getMaterialCounter()));
        mBeefsCounter.setText(String.valueOf(mBull.getMaterialCounter()));


        mFarmers = mCategoriesPreferences.getInt("farmers", 1);
        mLands = mCategoriesPreferences.getInt("lands", 1);
        mFactories = mCategoriesPreferences.getInt("factories", 1);
        mResetBonus = Double.longBitsToDouble(mCategoriesPreferences.getLong("resetBonus", Double.doubleToLongBits(1)));

        mPigFactory = mCategoriesPreferences.getBoolean("pigfactory", false);
        mSheepFactory = mCategoriesPreferences.getBoolean("sheepfactory", false);
        mCowFactory = mCategoriesPreferences.getBoolean("cowfactory", false);
        mBullFactory = mCategoriesPreferences.getBoolean("bullfactory", false);
        mFactoryOn = mCategoriesPreferences.getBoolean("factoryon", false);

        return true;
    }

    private void resetGame(){

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
        mLandsCounter.setText("");
        mEggsCounter.setText("");
        mWoolsCounter.setText("");
        mBaconsCounter.setText("");
        mMilksCounter.setText("");
        mBeefsCounter.setText("");
        mFactoriesCounter.setText("");
        mTotalMoney.setText(String.format("%.2f", mSum));


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
