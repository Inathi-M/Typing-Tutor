#Makefile for Assignment 2
#Nkosinathi Tshaphile
#25 August 2022

JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES=WordDictionary.class FallingWord.class  SlidingWord.class GamePanel.class\
				 Score.class  ScoreUpdater.class\
				 WordMover.class HungryWordMover.class TypingTutorApp.class
				 
				  
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

compile: $(CLASS_FILES)
	
         
clean: 
	rm $(BINDIR)/*.class
		
run:
	java -cp $(BINDIR) typingTutor.TypingTutorApp $(input) 
	 
	