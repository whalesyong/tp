# Jeromy - Project Portfolio Page

## Overview

Cooking AIDS is a Command Line Interface (CLI) application that simplifies meal planning by allowing users to store, view, and organize their meals efficiently. It enables users to quickly retrieve recipes, add new ones, and plan meals across a calendar year, through intuitive commands in a terminal environment.

The application was built using Java and follows Object-Oriented Programming principles. It features a modular architecture that separates command parsing, user interface, data storage, and logic handling.

### **Enhancements Implemented**

- **User Command Handling:**  
  I developed the logic for user commands, including:
    - `add` – allows users to add new recipes, ingredients, and dishes.
    - `delete` – handles removal of recipes, ingredients and dishes by name, which also includes logic to handle duplicate dishes for user to choose from.
    - `help` – generates a help message to guide user and provides a link to user guide.

These command classes were designed to follow the Command Pattern and work seamlessly with the parser and user interface.

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

- Reported 5+ bugs in other teams’ applications during mock PE.

{to insert screenshots}
