package foodrecepies.practise.com.foodrecepies.data.model;

/**
 * Created by madhav on 2/28/2018.
 */

public class Ingredients {
    private String ingredientName;
    private String quantity;

    public Ingredients(String ingredientName, String quantity) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
