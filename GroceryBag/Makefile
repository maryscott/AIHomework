GS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        grocerybag/Bag.java \
        grocerybag/Constraint.java \
        grocerybag/ConstraintType.java \
        grocerybag/FileParser.java \
        grocerybag/GraphNode.java \
        grocerybag/ListItem.java \
        grocerybag/ListItemSizeComparator.java \
        grocerybag/ItemNumberComparator.java \
        grocerybag/LocalSearch.java \
        grocerybag/MostRestrictedValueComparator.java \
        grocerybag/Problem.java \
        grocerybag/SearchArcConsistent.java \
        grocerybag/SearchWithOutArcConsistency.java \
        grocerybag/leastContainingValueComparator.java \
        grocerybag/MaryScott.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) grocerybag/*.class
