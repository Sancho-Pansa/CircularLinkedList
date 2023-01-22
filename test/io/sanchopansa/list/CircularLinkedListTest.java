package io.sanchopansa.list;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

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
    void testShift() {
        String newLastElement = testArray[0];
        cll.shiftList();
        assertEquals(cll.getLast(), newLastElement);
        for (String s : cll)
            System.out.println(s);
    }

    @org.junit.jupiter.api.Test
    void testReverseShift() {
        String newLastElement = testArray[testArray.length - 2];
        cll.reverseShift();
        assertEquals(cll.getLast(), newLastElement);
        for (String s : cll)
            System.out.println(s);
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        CircularLinkedList<String> cll2 = new CircularLinkedList<>(Arrays.asList(testArray));
        assertEquals(cll, cll2);
    }
}