package no.pxpdev.farmtofame;

/**
 * Created by akil_91 on 26.03.2017.
 */

public class Categories {

    private int mPrice;
    private int mCounter;
    private int mTimeout;
    private  double mCategoryBonus;
    private int mMaterials;
    private int mMaterialCounter;

    public Categories(int price, int counter, int time, double categoryBonus) {
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

    public double getCategoryBonus() {
        return mCategoryBonus;
    }

    public void setCategoryBonus(double categoryBonus) {
        mCategoryBonus = categoryBonus;
    }

    public int getMaterials() {
        return mMaterials;
    }

    public void setMaterials(int materials) {
        mMaterials = materials;
    }

    public int getMaterialCounter() {
        return mMaterialCounter;
    }

    public void setMaterialCounter(int materialCounter) {
        mMaterialCounter = materialCounter;
    }
}
