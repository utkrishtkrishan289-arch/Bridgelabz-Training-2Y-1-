package linkedlist;

// Problem 8: Undo/Redo for a Text Editor using a Doubly Linked List
public final class UndoRedoEditor {

    static class State {
        String text;
        State next;   // redo direction
        State prev;   // undo direction

        State(String text) {
            this.text = text;
        }
    }

    private State head;       // oldest state kept
    private State current;    // current text state
    private int size;
    private final int maxHistory;

    public UndoRedoEditor(int maxHistory) {
        this.maxHistory = maxHistory;
        addState("");         // start with an empty text state
    }

    // Add a new state at the end (after the current one)
    public void addState(String text) {
        State node = new State(text);
        if (head == null) {
            head = current = node;
            size = 1;
            return;
        }
        // A new action after undo discards the old redo states
        State temp = current.next;
        while (temp != null) {
            State nextState = temp.next;
            temp.prev = temp.next = null;
            temp = nextState;
            size--;
        }
        current.next = node;
        node.prev = current;
        current = node;
        size++;

        // Limit the history size: drop the oldest state
        if (size > maxHistory) {
            State oldHead = head;
            head = head.next;
            head.prev = null;
            oldHead.next = null;
            size--;
        }
    }

    public void type(String word) {
        addState(current.text + word);
    }

    public void undo() {
        if (current.prev == null) {
            System.out.println("Nothing to undo.");
            return;
        }
        current = current.prev;
    }

    public void redo() {
        if (current.next == null) {
            System.out.println("Nothing to redo.");
            return;
        }
        current = current.next;
    }

    public void displayCurrent() {
        System.out.println("Current text: \"" + current.text + "\"  (states stored: " + size + ")");
    }

    public static void main(String[] args) {
        UndoRedoEditor editor = new UndoRedoEditor(10);

        System.out.println("--- Typing ---");
        editor.type("Hello");
        editor.type(" World");
        editor.type("!");
        editor.displayCurrent();

        System.out.println("\n--- Undo twice ---");
        editor.undo();
        editor.undo();
        editor.displayCurrent();

        System.out.println("\n--- Redo once ---");
        editor.redo();
        editor.displayCurrent();

        System.out.println("\n--- Type after undo (old redo is discarded) ---");
        editor.undo();
        editor.type(" Java");
        editor.displayCurrent();
        editor.redo();

        System.out.println("\n--- History limit of 10 states ---");
        for (int i = 1; i <= 15; i++) {
            editor.type(" " + i);
        }
        editor.displayCurrent();
        int undoSteps = 0;
        while (editor.current.prev != null) {
            editor.undo();
            undoSteps++;
        }
        System.out.println("Undo steps possible: " + undoSteps + " (max " + (editor.maxHistory - 1) + ")");
        editor.displayCurrent();
    }
}
