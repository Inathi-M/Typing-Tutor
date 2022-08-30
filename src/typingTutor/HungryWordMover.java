package typingTutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends  Thread{

    private static int maxWait=1000;
    private static int minWait=100;
    public static WordDictionary dict;
    private SlidingWord myWord;
    private AtomicBoolean done;
    private AtomicBoolean pause;
    CountDownLatch startLatch; //so all can start at once
    private Score score;
    private FallingWord[] newWords;
    HungryWordMover( SlidingWord word) {
        myWord = word;
    }

    HungryWordMover( SlidingWord word,WordDictionary dict, Score score,
               CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
        this(word);
     //   this.dict = dict;
        this.score=score;
        this.startLatch = startLatch;
        this.done=d;
        this.pause=p;

    }

    public void setWords(FallingWord[] words){
        this.newWords = words;
    }


    public void run() {

        //System.out.println(myWord.getWord() + " falling speed = " + myWord.getSpeed());
        try {
            System.out.println(myWord + " waiting to start " );
            startLatch.await();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } //wait for other threads to start
        System.out.println(myWord + " started" );

        while (!done.get()) {
            // animate the word
            while (!myWord.vanished() && !done.get()) {

                // If the hungry word touches others words they should disappear

                for (int i = 0; i < newWords.length; i++) {
                    // Starting x
                    int firstXstart = myWord.getX();
                    // approx x where the word ends
                    int firstXend = myWord.getX() + myWord.getWord().length() * 15;
                    // starting y
                    int firstYstart = myWord.getY();
                    // approx y where the word ends
                    int firstYend = myWord.getY() + 15;

                    int secondXstart = newWords[i].getX();
                    int secondXend = newWords[i].getX() + newWords[i].getWord().length() * 15;
                    int secondYstart = newWords[i].getY();
                    int secondYend = newWords[i].getY() + 15;

                    if ((firstXend >= secondXstart && firstXstart <= secondXend) && (secondYend >= firstYstart && secondYstart <= firstYend)) {
                        newWords[i].resetWord();
                        score.missedWord();
                    } else if ((firstXend >= secondXstart && firstXstart <= secondXend)
                            && (firstYend >= secondYstart && firstYstart <= secondYend)) {
                        newWords[i].resetWord();
                        score.missedWord();
                    } else if ((secondXend >= firstXstart && secondXstart <= firstXend)
                            && (secondYend >= firstYstart && secondYstart <= firstYend)) {
                        newWords[i].resetWord();
                        score.missedWord();
                    } else if ((secondXend >= firstXstart && secondXstart <= firstXend)
                            && (firstYend >= secondYstart && firstYstart <= secondYend)) {
                        newWords[i].resetWord();
                        score.missedWord();
                    }

                }
                myWord.slide(15);
                try {
                    sleep(myWord.getSpeed());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ;
                while (pause.get() && !done.get()) {
                }
                ;
            }
            if (!done.get() && myWord.vanished()) {
                score.missedWord();
                myWord.resetWord();
            }
            myWord.resetWord();
        }
    }
}
