# Project 1 - Deterministic Finite Automata

* Authors: Julia Melchert and Axel Murillo
* Class: CS361 Section 002
* Semester: Spring 2024

## Overview

This project uses Java to mimic a deterministic finite automaton (DFA).

## Reflection

- What worked well and what was a struggle?
We were able to decipher the given skeleton code well and quickly begin working
on the project. Our conceptual knowledge of DFAs really helped us complete it. 
However, our team struggled to work together with differing schedules and lacking
communication, so that made it difficult to complete the project together. 
However, near the end, we decided on how to split the work to suit our
skills and time remaining best, and ended up finishing the project well.

- What concepts still aren't quite clear?
The concepts of packages, the Java Collections API, implementing interfaces, extending
abstract classes, test-based development using JUnit, and DFAs in general all makes sense
to us, so we don't think there are any loose ends needing clarification.

- What techniques did you use to make your code easy to debug and modify?
We used the original project's structure to our advantage in order to break
functions up and choose data types wisely. For instance, having our transitions
represented as a hashmap with the states as the keys and a hashmap as the values 
allowed us to easily see which states had what transitions and on what characters. 
This was easy to print out to the console for debugging and verification, too.

- What would you change about your design process?
Something we may change moving forward would be to assign specific, related methods to each
person, as this could help with organization. As for our code design, the only thing we think
we would change for this project is the transition data types, as the ArrayList in the
values added some complexity.

- If you could go back in time, what would you tell yourself about doing this project?
We would have told ourselves that it is not as hard as it seems at first! With all of the files 
and different classes/interfaces, it seems intimidating at first. However, once we started coding, 
it all fell into place and made sense. I would also tell myself to start writing junit tests a bit earlier.
Some tests were getting too complex to write, so they were removed instead of being fully implemented.

## Compiling and Using

In order to compile and run the test suite, use the following commands:
```
javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java
java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.dfa.DFATest
```

If you want to make a DFA of your own, follow the structure of DFATest.java to import all the appropriate packages/libraries 
and initialize the DFA object correctly.

## Sources used

* https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html
* https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
* https://www.geeksforgeeks.org/hashmap-containskey-method-in-java/
* https://stackoverflow.com/questions/4564414/delete-first-character-of-string-if-it-is-0
* https://www.geeksforgeeks.org/treeset-in-java-with-examples/
* https://www.w3schools.com/java/java_arraylist.asp
* https://stackoverflow.com/questions/25352329/how-to-use-asserttrue
* https://learn.microsoft.com/en-us/dotnet/api/system.text.stringbuilder.tostring?view=net-8.0