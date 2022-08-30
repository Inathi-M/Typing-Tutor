package typingTutor;
public class SlidingWord {

    private String word; // the word
    private int x; // position - width
    private int y; // postion - height
    private int maximumX; //maximum height

    private int slidingSpeed; //how fast this word is
    private boolean vanished; //flag for if user does not manage to catch word in time


    private static int maxWait = 1000;
    private static int minWait = 100;

    public static WordDictionary dict;

    SlidingWord() { // constructor with defaults
        word = "computer"; // a default - not used
        x = 0;
        y = 0;
        maximumX = 300;
        vanished = false;
        slidingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
    }

    SlidingWord(String text) {
        this();
        this.word = text;
    }

    SlidingWord(String text, int maxX) {
        this(text);
        this.maximumX = maxX;
    }

    public static void increaseSpeed() {
        minWait += 50;
        maxWait += 50;
    }

    public static void resetSpeed() {
        maxWait = 1000;
        minWait = 100;
    }

    // all getters and setters must be synchronized
    public synchronized void setY(int y) {
        this.y = y;
    }

    public synchronized void setWord(String text) {
        this.word = text;
    }

    public synchronized void setX(int x) {
        if (x > maximumX) {

            vanished = true;
            x = maximumX;
        }
        this.x = x;
    }

    public synchronized String getWord() {
        return word;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized void setMaximumX(int maxX) {
        this.maximumX = maxX;
    }

    public synchronized int getSpeed() {
        return slidingSpeed;
    }

    public synchronized void setPos(int x, int y) {
        setY(y);
        setX(x);
    }

    public synchronized void resetPos() {
        setX(0);
        ;
    }

    public synchronized void resetWord() {
        resetPos();
        word = dict.getNewWord();
        vanished = false;
        slidingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
    }

    public synchronized boolean matchWord(String typedText) {
        // System.out.println("Matching against: "+text);
        if (typedText.equals(this.word)) {
            resetWord();
            return true;
        } else
            return false;
    }

    public synchronized  boolean vanished() {
        return vanished;
    }

    public synchronized  void slide(int inc) {
        setX(x+inc);
    }
}
