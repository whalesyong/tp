# Jeromy - Project Portfolio Page

## Overview

Cooking AIDS is a Command Line Interface (CLI) application that simplifies meal planning by allowing users to store, view, and organize their meals efficiently. It enables users to quickly retrieve recipes, add new ones, and plan meals across a calendar year, through intuitive commands in a terminal environment.

The application was built using Java and follows Object-Oriented Programming principles. It features a modular architecture that separates command parsing, user interface, data storage, and logic handling. This design allows for scalability and easier debugging.

⸻

### Summary of Contributions

Code Contributed

View my code contributions on the [tP Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=11b&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=jxromy&tabRepo=AY2425S2-CS2113-T11b-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

Enhancements Implemented
•	Command Parsing Logic:
I implemented and refined the central command parser that interprets user inputs and maps them to their corresponding actions. This involved:
•	Designing a robust parsing structure to extract command keywords and arguments.
•	Handling edge cases such as missing arguments, malformed inputs, and whitespace errors.
•	Refactoring early parsing implementations into a more modular and testable structure to improve maintainability.
•	User Command Handling:
I developed the logic for several user commands, including:
•	add-meal – allows users to add new meals with details such as name, ingredients, and optional tags.
•	delete-meal – handles removal of meals by name or index.
•	view-meals – displays all stored meals in a formatted list.
•	help – dynamically generates help messages based on command metadata.
These command classes were designed to follow the Command Pattern and work seamlessly with the parser and UI components.
•	Input Validation & Feedback Mechanism:
Introduced input sanitization and user-friendly error messages for invalid commands, improving the overall UX of the CLI.

⸻

Contributions to the UG (User Guide)
•	Wrote detailed command usage instructions for:
•	add-meal
•	delete-meal
•	view-meals
•	help
•	Added example inputs and expected outputs for each command.
•	Contributed to the “Common Errors” section with troubleshooting tips for parsing-related issues.

⸻

Contributions to the DG (Developer Guide)
•	Authored the “Parser” and “Command” implementation sections.
•	Added UML diagrams:
•	Class Diagram for the Parser-Command relationship.
•	Sequence Diagram illustrating the flow from user input to command execution.
•	Documented the command execution lifecycle and described how input strings are tokenized and passed through the system.

⸻

Contributions to Team-Based Tasks
•	Participated actively in weekly team sync-ups and code walkthroughs.
•	Wrote integration test cases that validated command parsing against the data model.
•	Coordinated with the storage team to ensure command logic aligned with the meal data structure.

⸻

Review / Mentoring Contributions
•	Reviewed 10+ pull requests, offering suggestions on:
•	Improving code modularity and adherence to coding standards.
•	Simplifying conditional logic in command execution.
•	Adding inline documentation for public methods.
•	Helped onboard a teammate by walking them through the parsing and command class structure.

⸻

Contributions Beyond the Project Team
•	Reported 3 bugs in other teams’ applications during peer testing week.
•	Shared a template for writing parser unit tests in the module forum, which was adopted by at least 2 other teams.
•	Answered a forum question about exception handling best practices in CLI-based applications.
