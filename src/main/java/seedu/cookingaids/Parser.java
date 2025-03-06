package seedu.cookingaids;


/**
 * Holds methods that manipulate input received from user into useful data
 * that can be understood by other methods
 */
public class Parser {
    static final int LENGTH_OF_DEADLINE = 9;
    static final int LENGTH_OF_TODO = 5;
    static final int LENGTH_OF_EVENT = 6;
    static final int LENGTH_OF_BY = 4;
    static final int LENGTH_OF_TO = 4;
    static final int LENGTH_OF_FROM = 6;
    static final int LENGTH_OF_FIND = 5;


    /**
     * deciphers users input for commands and calls methods to execute
     * if unable to decipher message display instructions that helps user give commands
     *
     * @param receivedText entire command input received
     */
    public static void decipherCommand(String receivedText) {//method to determine what kind of task to call

        String command = receivedText.strip().split(" ")[0];
        switch (command) {
        case DisplayCommand.ADD_TODO_COMMAND:
            Command.executeAddTodo(receivedText); //create Todo task
            Ui.printLineDivider();
            break;

        default:
            System.out.println("I DO NOT UNDERSTAND " + receivedText);
            System.out.println("type \"help\" to see available commands");

        }


    }

    /**
     * parses input text for creating deadline task to extract information
     *
     * @param receivedText entire command input received
     * @return array containing task description and deadline
     * @throws EmptyStringException when description received is empty
     * @throws MissingDateException when deadline field is empty
     */
    static public String[] parseDeadline(String receivedText) throws EmptyStringException, MissingDateException {
        String[] returnedArray = new String[2];
        receivedText = receivedText.trim();
        if (receivedText.split(" ").length == 1) { //if only command word throw empty string statement
            throw new EmptyStringException();
        }
        int indexOfBy = receivedText.indexOf("/by");
        if (indexOfBy == -1) { // if by does not exist throw missing Date exception
            throw new MissingDateException();

        }

        returnedArray[0] = receivedText.substring(LENGTH_OF_DEADLINE, indexOfBy).trim();
        // saving text in front of /by as description, starting from after "deadline"

        returnedArray[1] = receivedText.substring(indexOfBy + LENGTH_OF_BY);
        //saving everything after /by as deadline

        // obtaining index of first /by

        if (returnedArray[0].isEmpty()) {
            throw new EmptyStringException();
        }
        if (returnedArray[1].isEmpty()) {
            throw new MissingDateException();
        }

        return returnedArray;

    }

    /**
     * parses input text for creating event task to extract information
     *
     * @param receivedText entire command input received
     * @return array containing task description, startTime and endTime
     * @throws EmptyStringException when description received is empty
     * @throws MissingDateException when startTime or endTime field is empty
     */
    static public String[] parseEvent(String receivedText) throws MissingDateException, EmptyStringException {

        receivedText = receivedText.trim();
        String[] words = receivedText.split("\\s+");
        if (words.length <= 1) {  // If there's only the command word or less
            throw new EmptyStringException();
        }
        String[] returnedArray = new String[3];
        int indexOfFrom = receivedText.indexOf("/from"); // obtaining index of first /from
        int indexOfTo = receivedText.indexOf("/to"); // obtaining index of first /to
        if (indexOfFrom == -1 || indexOfTo == -1) { // if any of the keywords do not exist throw missing Date exception
            throw new MissingDateException();
        }


        returnedArray[0] = receivedText.substring(LENGTH_OF_EVENT, indexOfFrom).trim();
        // saving text in front of /from as description after cutting out first word "event"

        returnedArray[1] = receivedText.substring(indexOfFrom + LENGTH_OF_FROM, indexOfTo).trim();
        //saving everything in between /from and /to as startTime

        returnedArray[2] = receivedText.substring(indexOfTo + LENGTH_OF_TO).trim();
        //saving everything after /to as endTime

        if (returnedArray[0].isEmpty()) {

            throw new EmptyStringException();
        }
        if (returnedArray[1].isEmpty() || returnedArray[2].isEmpty()) {

            throw new MissingDateException();
        }
        return returnedArray;

    }

    /**
     * parses input text for creating todo task to extract information
     *
     * @param receivedText entire command input received
     * @return String containing task description
     * @throws EmptyStringException when description received is empty
     */
    static public String parseTodo(String receivedText) throws EmptyStringException {
        String parsedTodo = receivedText.substring(LENGTH_OF_TODO).trim(); // cut out todo command
        if (parsedTodo.isEmpty()) {
            throw new EmptyStringException();
        }
        return receivedText.substring(LENGTH_OF_TODO).trim();
    }

    /**
     * checks Task for type of task todo, deadline or event
     *
     * @return char that signifies task Type
     */
    static char checkTaskType(Task task) {
        char taskType = 'T';
        if (task instanceof EventTask) {
            taskType = 'E';
        } else if (task instanceof Deadline) {
            taskType = 'D';
        } else if (task instanceof Todo) {
            taskType = 'T';
        }

        return taskType;
    }
    /**
     * extracts task number for commands that consist of 2 words. "{command} {task number}"
     * e.g. delete 2, done 1, undone 4
     *
     * @param receivedText entire command input received
     * @return int of task number based on taskList
     */
    public static int getTaskNumber(String receivedText) {

        return Integer.parseInt(receivedText.split(" ")[1]);
    }
    /**
     * converts task into an efficient String format for saving
     *
     * @return String version of task
     */
    public static String getTaskAsSavedFormat(Task task) {
        String returnedText = "";
        char taskType = checkTaskType(task);
        returnedText += taskType;
        returnedText += "|" + task.getIsDone();
        returnedText += "|" + task.getDescription(); //add tasks to String
        switch (taskType) {
        case 'D':
            Deadline deadlineTask = (Deadline) task; // class cast to Deadline to extract Deadline

            returnedText += "|" + deadlineTask.getDeadlineAsString();// extract Deadline
            break;
        case 'E':
            EventTask eventTask = (EventTask) task; // class cast to Event to extract startTime
            returnedText += "|" + eventTask.getStartTimeAsString();// extract startTime
            returnedText += "|" + eventTask.getEndTimeAsString();// extract startTime
            break;
        }
        return returnedText;
    }


    public static String[] parseKeywords(String receivedText) {
        String parsedKeywords = receivedText.substring(LENGTH_OF_FIND).trim();
        return parsedKeywords.split(" ");
    }

}