import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import static org.junit.Assert.*;

public class AVLTreeTest {
    @Test
    public void delete(){
        AVLTree<Integer, Integer> st = new AVLTree<Integer, Integer>();
        st.put(15, 500);
        st.put(10, 100);
        st.put(11, 200);
        st.put(8, 300);
        st.put(16, 600);
        st.put(20, 700);
        assertEquals(st.contains(15), TRUE);
        assertEquals(st.contains(10), TRUE);
        assertEquals(st.contains(11), TRUE);
        assertEquals(st.contains(8), TRUE);
        assertEquals(st.contains(16), TRUE);
        assertEquals(st.contains(20), TRUE);
        st.delete(10);
        assertEquals(st.contains(15), TRUE);
        assertEquals(st.contains(10), FALSE);
        assertEquals(st.contains(11), TRUE);
        assertEquals(st.contains(8), TRUE);
        assertEquals(st.contains(16), TRUE);
        assertEquals(st.contains(20), TRUE);
    }

}
