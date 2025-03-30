# **CookingAids User Guide**  

## **Table of Contents**  
- [Introduction](#introduction)  
- [Notes About the Command Format](#notes-about-the-command-format)  
- [Features](#features)  
  1. [Viewing Help: `help`](#viewing-help-help)  
  2. [Adding Dish to Schedule: `add -dish`](#adding-dish-to-schedule-add--dishdishname)  
  3. [Adding an Ingredient: `add -ingredient`](#adding-an-ingredient-add--ingredientingredientname)  
  4. [View Scheduled Dishes: `list -dish`](#view-scheduled-dishes-list--dish)  
  5. [View Available Ingredients: `list -ingredient`](#view-available-ingredients-list--ingredient)  
  6. [Delete Dish from Schedule: `delete -dish`](#delete-dish-from-schedule-delete--dishdishname)  
  7. [Delete Ingredient: `delete -ingredient`](#delete-ingredient-delete--ingredientingredientname)  
  8. [Adding Recipe to Recipe Bank: `add -recipe`](#adding-recipe-to-recipe-bank-add--reciperecipename)  
  9. [View Available Recipes: `list -recipe`](#view-available-recipes-list--recipe)  
  10. [Delete Recipe from Recipe Bank: `delete -recipe`](#delete-recipe-from-recipe-bank-delete--reciperecipename)  
  11. [Suggest Dishes: `suggest`](#suggest-dishes-suggest)  
- [Command List](#command-list)  

---

## **Introduction**  

CookingAids is an app designed to help busy students living overseas on a budget save time and effort when planning and cooking meals.  
With CookingAids, you can:  
- Input your current ingredients.  
- Schedule dishes from a list of recipes.  
- View full recipes and cooking methods.  
- Track remaining ingredients and scheduled dishes.  

---

## **Notes About the Command Format**  

> **Command Format Notes:**  
> - Items in square brackets are optional.  
>   - Example: `add -dish={dishName} [-when{when}]` can be used as `add -dish={dishName}`.  
> - Parameters can be in any order.  
>   - Example: `add -dish={dishName} -when{when}` is equivalent to `add -when{when} -dish={dishName}`.  
> - Extraneous parameters for commands that do not take parameters (e.g., `help`) will be ignored.  
>   - Example: `help 123` will be interpreted as `help`.  

---

## **Features**  

### **1. Viewing Help: `help`** {#viewing-help-help}  

Displays a message showing how to access the help page.  

**Format:**  
```plaintext
help
```

**Expected Output:**  
```plaintext
help: <photo of CLI showing the link to User Guide on GitHub>
```

---

### **2. Adding Dish to Schedule: `add -dish={dishName}`** {#adding-dish-to-schedule-add--dishdishname}  

Adds a dish to the calendar. If no date is specified, the date is set to today.  

**Format:**  
```plaintext
add -dish={dishName}  
add -dish={dishName} [-when{when}]
```

**Expected Output:**  
- `add -dish={dishName}`:  
  ```plaintext
  {dishName} has been added to today’s cooking list!
  ```
- `add -dish={dishName} -when{when}`:  
  ```plaintext
  {dishName} has been added to your cooking list on {when}!
  ```

---

### **3. Adding an Ingredient: `add -ingredient={ingredientName}`** {#adding-an-ingredient-add--ingredientingredientname}  

Adds an ingredient to the ingredient database. If no quantity is specified, it defaults to 1.  

> **Warning:** Ingredients will not expire if no expiry date is set.  

**Format:**  
```plaintext
add -ingredient={ingredientName} [-expiry={expiryDate}] [-qty={quantity}]
```

**Expected Output:**  
- `add -ingredient={ingredientName}`:  
  ```plaintext
  {ingredientName} has been added to your available ingredients!
  ```
- `add -ingredient={ingredientName} -qty{quantity}`:  
  ```plaintext
  Nice, {quantity} {ingredientName} has been added to your ingredients list! (WARNING: This ingredient will not expire)
  ```
- `add -ingredient={ingredientName} -expiry={expiryDate}`:  
  ```plaintext
  Nice, {ingredientName} has been added to your ingredients list with an expiry date: {expiryDate}!
  ```

---

### **4. View Scheduled Dishes: `list -dish`** {#view-scheduled-dishes-list--dish}  

Displays a list of scheduled dishes.  In Chronological order

**Format:**  
```plaintext
list -dish  
list -dish={dishName}  
list -dish -when={when}
```

**Expected Output:**  
- `list -dish`:  
  ```plaintext
  All dishes:  
  {when1} - {dishName1}  
  {when1} - {dishName2}  
  {when2} - {dishName2}
  ```

---

### **5. View Available Ingredients: `list -ingredient`** {#view-available-ingredients-list--ingredient}  

Displays a list of available ingredients.  

**Format:**  
```plaintext
list -ingredient
```

**Expected Output:**  
```plaintext
All ingredients:  
1 - {ingredientName1}  
2 - {ingredientName2}  
3 - {ingredientName3}
```

---

### **6. Delete Dish from Schedule: `delete -dish={dishName}`** {#delete-dish-from-schedule-delete--dishdishname}  

Deletes a dish from the schedule.  

**Format:**  
```plaintext
delete -dish={dishName}  
delete -when={when}  
delete -dish={dishName} -when={when}
```

**Expected Output:**  
- If multiple dishes are scheduled:  
  ```plaintext
  1. {when1} - {dishName}  
  2. {when2} - {dishName}  
  Which would you like to delete?  
  Input a number.  
  {when} - {dishName} Successfully deleted!
  ```
- If only one dish is scheduled:  
  ```plaintext
  {when} - {dishName} Successfully deleted!
  ```

---

### **7. Delete Ingredient: `delete -ingredient={ingredientName}`** {#delete-ingredient-delete--ingredientingredientname}  

Deletes an ingredient from the list of available ingredients.  

**Format:**  
```plaintext
delete -ingredient={ingredientName}
```

**Expected Output:**  
```plaintext
Deleted {ingredientName} from the list of available ingredients.
```

---

### **8. Adding Recipe to Recipe Bank: `add -recipe={recipeName}`** {#adding-recipe-to-recipe-bank-add--reciperecipename}  

Adds a recipe to the recipe bank.  

> **Warning:** Ingredients *must* be surrounded by quotation marks (`"`) and comma-separated to be processed properly.  

**Format:**  
```plaintext
add -recipe={recipeName}  
add -recipe={recipeName} [-callsfor="{ingredient1, ingredient2, ingredient3}"]
```

**Expected Output:**  
- `add -recipe={recipeName}`:  
  ```plaintext
  {recipeName} has been added to the recipe bank!
  ```
- `add -recipe={recipeName} -callsfor="{ingredient1, ingredient2, ingredient3}"`:  
  ```plaintext
  {recipeName} has been added to the recipe bank! It uses the following ingredients: (1) {ingredient1}, (2) {ingredient2}, (3) {ingredient3}
  ```

---

### **9. View Available Recipes: `list -recipe`** {#view-available-recipes-list--recipe}  

Displays a list of available recipes and/or their ingredients.  

**Format:**  
```plaintext
list -recipe  
list -recipe={recipeName}
```

**Expected Output:**  
- `list -recipe`:  
  ```plaintext
  Recipes:  
  - recipeName1  
  - recipeName2  
  - ...
  ```
- `list -recipe={recipeName}`:  
  ```plaintext
  recipeName:  
  - ingredientName1  
  - ingredientName2  
  - ...
  ```

---

### **10. Delete Recipe from Recipe Bank: `delete -recipe={recipeName}`** {#delete-recipe-from-recipe-bank-delete--reciperecipename}  

Deletes a recipe from the recipe bank.  

**Format:**  
```plaintext
delete -recipe={recipeName}
```

**Expected Output:**  
```plaintext
{recipeName} has been deleted from the recipe bank!
```

---

### **11. Suggest Dishes: `suggest`**
Suggests dishes based on available ingredients. 
**Format:**
```
suggest
``` 
**Expected Output:**
```
You have enough ingredients to make: 
1: carbonara
2: garlic_bread
```

## **Command List**  

| **Action** | **Format, Examples** |  
| :---- | :---- |  
| **Add** | `add -ingredient`, `add -dish`, `add -recipe` <br> Example: `add -ingredient tomato`, `add -dish tomato soup` |  
| **Delete** | `delete -ingredient`, `delete -dish`, `delete -recipe` <br> Example: `delete -ingredient tomato`, `delete -dish tomato soup` |  
| **List** | `list -ingredient`, `list -dish`, `list -recipe` <br> Example: `list -ingredient` |  
| **Help** | `help` |


