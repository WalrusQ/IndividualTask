import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AVLTreeTest {
    @Test
    public void myTest(){
        AVLTree<Integer, Integer> st = new AVLTree<Integer, Integer>();
        assertTrue(st.isEmpty());

        st.put(15, 500);
        st.put(10, 100);
        st.put(11, 200);
        st.put(8, 300);
        st.put(16, 600);
        st.put(20, 700);

        assertFalse(st.isEmpty());

        assertTrue(st.containsKey(15));
        assertTrue(st.containsKey(10));
        assertTrue(st.containsKey(11));
        assertTrue(st.containsKey(8));
        assertTrue(st.containsKey(16));
        assertTrue(st.containsKey(20));

        assertEquals((int)st.get(11), 200);

        assertTrue(st.size() == 6);
        st.remove(10);

        assertTrue(st.containsKey(15));
        assertFalse(st.containsKey(10));
        assertTrue(st.containsKey(11));
        assertTrue(st.containsKey(8));
        assertTrue(st.containsKey(16));
        assertTrue(st.containsKey(20));

        assertTrue(st.size() == 5);

        st.deleteMax();
        assertFalse(st.containsKey(20));

        st.deleteMin();
        assertFalse(st.containsKey(8));

        assertTrue(st.containsValue(200));

        assertEquals((int)st.size(), 3);

        assertTrue(st.keySet().contains(11));
        assertTrue(st.keySet().contains(15));
        assertTrue(st.keySet().contains(16));
        assertEquals(st.keySet().size(), 3);


        assertTrue(st.values().contains(200));
        assertTrue(st.values().contains(500));
        assertTrue(st.values().contains(600));
        assertEquals(st.values().size(), 3);

        assertTrue(st.headMap(16).containsKey(11));
        assertTrue(st.headMap(16).containsKey(15));
        assertEquals(st.headMap(16).size(), 2);

        assertEquals(st.tailMap(10).size(), 3);
        assertEquals(st.tailMap(12).size(), 2);


        assertEquals(st.subMap(11, 16).size(), 2);
        assertEquals(st.subMap(11, 17).size(), 3);

        Map testMap= new HashMap<Integer, Integer>();
        testMap.put(1, 50);
        testMap.put(2, 70);
        st.putAll(testMap);
        assertTrue(st.containsKey(1));
        assertTrue(st.containsKey(2));

        }


    }

