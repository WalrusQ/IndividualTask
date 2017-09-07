import org.junit.Test;

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

    }

}
