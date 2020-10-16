package ru.tsedrik.lesson10.hometask1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ListSorterTest {

    private ListSorter sorter = new ListSorter();

    @Test(expected = IllegalArgumentException.class)
    public void testBubbleSortNullList() {
        sorter.bubbleSort(null);
    }

    @Test
    public void testBubbleSortEmptyList() {
        List<Integer> emptyList = new ArrayList<>();
        assertEquals(emptyList, sorter.bubbleSort(emptyList));
    }

    @Test
    public void testBubbleSortOneElementList() {
        List<Integer> oneElementList = new ArrayList<>();
        oneElementList.add(1);
        assertEquals(oneElementList, sorter.bubbleSort(oneElementList));
    }

    @Test
    public void testBubbleSortListWithTheSameElements() {
        List<Integer> sameElementList = new ArrayList<>();
        sameElementList.add(1);
        sameElementList.add(1);
        sameElementList.add(1);
        assertEquals(sameElementList, sorter.bubbleSort(sameElementList));
    }

    @Test
    public void testBubbleSortRandomList() {
        List<Integer> randomElementList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            randomElementList.add(new Random().nextInt(10));
        }
        Collections.sort(randomElementList);
        assertEquals(randomElementList, sorter.bubbleSort(randomElementList));
    }
}