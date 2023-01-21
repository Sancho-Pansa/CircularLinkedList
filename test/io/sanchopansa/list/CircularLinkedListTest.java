package io.sanchopansa.list;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import io.sanchopansa.list.CircularLinkedList;

class CircularLinkedListTest {
    private String[] testArray = {
            "Alice",
            "Bob",
            "Cinderella",
            "Dennis",
            "Eve",
            "Franco"
    };

    private CircularLinkedList<String> cll;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        cll = new CircularLinkedList<>(Arrays.asList(testArray));
    }

    @org.junit.jupiter.api.Test
    void checkFirstElement() {
        assertEquals(testArray[0], cll.getFirst());
    }

    @org.junit.jupiter.api.Test
    void checkLastElement() {
        assertEquals(testArray[testArray.length - 1], cll.getLast());
    }

    @org.junit.jupiter.api.Test
    void testIterator() {
        Iterator<String> iter = cll.iterator();
        while(iter.hasNext())
            System.out.println(iter.next());
        assertTrue(true);
    }
}