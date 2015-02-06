
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $.*java

CLASSES = \
	  BullshitPlayer.java \
	  BullshitTable.java \
	  Card.java \
	  Deck.java \
	  Hand.java \
	  InvalidMoveException.java \
	  Makefile \
	  Player.java \
	  Rank.java \
	  Suit.java \
	  Table.java


main: classes

classes: $(CLASSES:.java=.class)




.PHONY: clean

clean:
	rm -f *.class



