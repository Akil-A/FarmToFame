package no.pxpdev.farmtofame;

/**
 * Created by akil_91 on 26.03.2017.
 */

public class Categories {

    int mPrice;
    int mCounter;
    int mTimeout;
    int mCategoryBonus;

    public Categories(int price, int counter, int time, int categoryBonus) {
        mPrice = price;
        mCounter = counter;
        mTimeout = time;
        mCategoryBonus = categoryBonus;
    }

    public int getTimeout() {
        return mTimeout;
    }

    public void setTimeout(int timeout) {
        mTimeout = timeout;
    }

    public int getCounter() {
        return mCounter;
    }

    public void setCounter(int counter) {
        mCounter = counter;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getCategoryBonus() {
        return mCategoryBonus;
    }

    public void setCategoryBonus(int categoryBonus) {
        mCategoryBonus = categoryBonus;
    }


}