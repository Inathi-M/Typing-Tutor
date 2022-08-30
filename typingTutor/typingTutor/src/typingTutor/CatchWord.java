package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static int noWords; //how many
	private static Score score; //user score

	private static int lowerWordindex;

    private static SlidingWord horizontalWord;
	
	CatchWord(String typedWord) {
		target=typedWord;
	}

	// New constructor
	CatchWord(String typedWord, int lwi) {
		this(typedWord);
		CatchWord.lowerWordindex = lwi;

	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}

    public static void setSlidingWord(SlidingWord horizontal) {
       horizontalWord  = horizontal;
    }
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	
	public void run() {
		int i=0;
		while (i<noWords) {		
			while(pause.get()) {};

			i = (lowerWordindex == -1) ? i : lowerWordindex;

			if (horizontalWord.matchWord(target)) {
				System.out.println(" score! hungry: " + target); // for checking
				score.caughtWord(target.length());
				break;
			}
			if (words[i].matchWord(target)) {
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length());	
				//FallingWord.increaseSpeed();
				break;
			}
		   i++;
		}
		
	}	
}
