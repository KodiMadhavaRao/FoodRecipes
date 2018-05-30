package foodrecepies.practise.com.foodrecepies.data.model;

/**
 * Created by madhav on 2/28/2018.
 */

public class Nutrition {
    private int calories;
    private int fat;
    private int carbohydrates;
    private int protein;
    private int cholesterol;
    private int sodium;

    public Nutrition(int calories, int fat, int carbohydrates, int protein, int cholesterol, int sodium) {
        this.calories = calories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
    }

    public Nutrition() {

    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

}
