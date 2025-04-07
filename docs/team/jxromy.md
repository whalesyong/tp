# Jeromy - Project Portfolio Page

## Overview

Cooking AIDS is a Command Line Interface (CLI) application that simplifies meal planning by allowing users to store, view, and organize their meals efficiently. It enables users to quickly retrieve recipes, add new ones, and plan meals across a calendar year, through intuitive commands in a terminal environment.

The application was built using Java and follows Object-Oriented Programming principles. It features a modular architecture that separates command parsing, user interface, data storage, and logic handling.

## **Summary of Contributions**

### **Code Contributed**
[View my code contributions on the tP Code Dashboard](<https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=jxromy&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=jxromy&tabRepo=AY2425S2-CS2113-T11b-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false>)

### **Enhancements Implemented**

- **User Command Handling:**  
  I developed the logic for user commands, including:
    - `add` – allows users to add new recipes, ingredients, and dishes.
    - `delete` – handles removal of recipes, ingredients and dishes by name, which also includes logic to handle duplicate dishes for user to choose from.
    - `list` - displays a list of saved recipes, available ingredients and scheduled dishes
    - `help` – generates a help message to guide user and provides a link to user guide.

  Further assisted with the implementation of secondary commands `suggest`, `view`, `update` and `search`

The `add` and `delete` command classes that I developed were designed to recognise different command patterns and manipulate different item types, storing / removing them from their respective collections. All command class methods had to work seamlessly in the parser, and I ensured that this was always true by fixing bugs whenever they arose. The command classes significantly contributes to the functionality of Cooking Aids as they are flexibly used for multiple collections and methods.

- **Wrote JUnit Tests**

I developed comprehensive JUnit tests to ensure the correctness and robustness of core command functionalities and utility components in the Cooking Aids CLI application:

- **`CookingAidsTest`**  
  Verified the behavior of utility methods that assist with formatting outputs, parsing strings, and validating user inputs. These tests ensured edge case handling and robustness across different command inputs.

- **`AddCommandTest`**  
  Tested the logic for adding new recipes, ingredients, and dishes.
  - Validated correct parsing of arguments and proper updating of internal data structures.
  - Verified that appropriate error messages and guiding outputs are shown when invalid input is detected.

- **`DeleteCommandTest`**  
  Ensured the correct behavior when users attempt to delete recipes, ingredients, or dishes.
  - Included cases for detecting and resolving duplicate dish names, where users are prompted to confirm their intended selection.
  - Verified that appropriate error messages and guiding outputs are shown when invalid input is detected.

- **`HelpCommandTest`**  
  Validated the generation of our help message.
  - Ensured output content is accurate, consistent, and includes a link to our User Guide.

- **`ListCommandTest`**  
  Tested the listing functionality for various categories like dishes, ingredients, and recipes.
  - Confirmed that the output is well-formatted and dynamically reflects the current state of stored data.

- **`SuggestCommandTest`**  
  Verified the logic that suggests meals based on available ingredients and known recipes.
  - Covered edge case where no suggestions are available due to the lack of ingredients.


- **Command Parsing Logic:**  
  Refined the central command parser that interprets user inputs and maps them to their corresponding actions. This involved:
    - Refactoring early parsing implementations into a more modular and testable structure.
    - Implementing a robust parsing structure to extract command keywords and arguments for the addition and deletion of recipes, ingredients or dishes.
    - Handling edge cases such as missing flags, malformed inputs, and whitespace errors.


- **Input Validation & Feedback Mechanism:**  
  Introduced input sanitization and user-friendly error messages for invalid commands, improving the overall UX of the CLI.

### **Contributions to the UG (User Guide)**

- Wrote detailed usage instructions for commands:
    - `add -dish`
    - `delete -dish`
    - `suggest`
    - `bye`
    - `help`
- Added example inputs and expected outputs for each command.

### **Contributions to the DG (Developer Guide)**

- Authored the “Parser” and “Command” implementation sections.
- Added UML diagrams:
    - **Class Diagram** for the Parser-Command relationship.
    - **Sequence Diagram** illustrating the flow from user input to command execution.
- Documented the command execution lifecycle and described how input strings are tokenized and passed through the system.

### **Contributions to Team-Based Tasks**

- Participated actively in weekly team sync-ups and code walkthroughs.
- Wrote unit and integration test cases that validated command parsing logic.
- Coordinated with the rest of the team to ensure command logic aligned with the different collections' data structure. (Dish, Ingredient, Recipe)

### **Review / Mentoring Contributions**

- Reviewed 10+ pull requests, offering suggestions on:
    - Improving code modularity and adherence to coding standards.
    - Simplifying conditional logic in command execution.
    - Adding inline documentation for public methods.

{to insert screenshots}

### **Contributions Beyond the Project Team**

- Reported 5+ bugs in other team's application during mock PE.
- Reviewed other team project which was similar to Cooking Aids and gave comments for improvement.

![Contribution_1](images/jeromy_other_contributions_1.png)
![Contribution_1](images/jeromy_other_contributions_2.png)
