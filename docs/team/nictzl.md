# Nicholas - Project Portfolio Page

## Overview

Cooking AIDS is a Command Line Interface (CLI) application that simplifies meal planning by allowing users to store, view, and organize their meals efficiently. It enables users to quickly retrieve recipes, add new ones, and plan meals across a calendar year, through intuitive commands in a terminal environment.

The application was built using Java and follows Object-Oriented Programming principles. It features a modular architecture that separates command parsing, user interface, data storage, and logic handling.

### Code contributed
https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=nictzl&breakdown=true

### **Enhancements Implemented**

- **Ingredient class:**  
  I developed the class for Ingredients containing its attributes and methods. Attributes include:
    - `name` - name of ingredient.
    - `quantity` - quantity of ingredient.
    - `expiryDate` - expiry date of ingredient.
    - `expiringSoon` - a boolean flag to indicate if ingredient is expiring soon.
    - `expired` - a boolean flag to indicate if ingredient has expired


- **Ingredient Storage class:**
<br>I developed the class for Ingredient Storage containing a Hashmap to hold the result of CRUD operations on the ingredient inventory. The class also contains methods to help implement the CRUD operations on the ingredient inventory 


- **Unit Config:**
<br>I developed the class for Unit Config to aid in the display of amount of ingredients by their type. For example (kg,g,ml,pcs) 

These classes were designed to increase modularity of project and work seamlessly with the classes.

- **Modularity of project:**  
  Our project has many interlinked components needing data from each other and may also need the data to be modified. For example, Storage class needs the cached data from IngredientStorage, RecipeBank and DishCalendar classes to store in JSON file. DishCalendar may need to update Ingredient data via useIngredients from IngredientStorage. Hence, by creating multiple classes with different public methods, the project work seamlessly with data being consistent throughout the project
  

### **Contributions to the UG (User Guide)**

- Wrote detailed usage instructions for commands:
    - `add -ingredient`
    - `update -ingredient`
    - `list -ingredient`
- Added example inputs and expected outputs for each command.

### **Contributions to the DG (Developer Guide)**

- Authored the “Design and Implementation” and “Collection” implementation sections.
- Added UML diagrams:
    - **Class Diagram** for the Collection-Item relationship.
    - **Sequence Diagram** illustrating the flow from user to larger architecture components.

### **Contributions to Team-Based Tasks**

- Participated actively in weekly team sync-ups and code walkthroughs.
- Wrote unit and integration test cases that validated implementation of the above classes.
- Coordinated with the rest of the team to ensure the classes can interact with each other in a seamless manner

### **Review / Mentoring Contributions**

- Reviewed pull requests, offering suggestions on:
    - Improving code modularity and adherence to coding standards.
    - Adding inline documentation for public methods.

### **Contributions Beyond the Project Team**

- Reported bugs in other teams’ applications during mock PE.

