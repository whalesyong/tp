package seedu.cookingaids.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipe {
    private String recipeName;
    private ArrayList<String> ingredients;

    @JsonCreator
    public Recipe(@JsonProperty("name") String recipeName,
                  @JsonProperty("ingredients") ArrayList<String> ingredients) {
        this.recipeName = recipeName;
        this.ingredients = (ingredients != null) ? ingredients : new ArrayList<>();
    }

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
        this.ingredients = new ArrayList<>();
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<String> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Recipe named '" + recipeName + "'" +
                " calls for ingredients " + ingredients ;
    }

    public String getName() {
        return this.recipeName;
    }
}
