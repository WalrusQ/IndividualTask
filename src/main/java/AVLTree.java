import java.util.*;

public class AVLTree<Key extends Comparable<Key>, Value> implements SortedMap<Key, Value> {


    @Override
    public Comparator<? super Key> comparator() {
        return null;
    }


    @Override
    public SortedMap<Key, Value> subMap(Key fromKey, Key toKey) {
        SortedMap<Key, Value> sMap = new TreeMap<Key, Value>();
        subMap(root, fromKey, toKey, sMap);
        return sMap;
    }

    private void subMap(Node x, Key fromKey, Key toKey, SortedMap<Key, Value> sMap){

        if(x == null) return;
        int cmpFrom, cmpTo;
        subMap(x.left, fromKey, toKey, sMap);
        cmpFrom = fromKey.compareTo(x.key);
        cmpTo = toKey.compareTo(x.key);
        if( cmpFrom <= 0 && cmpTo > 0) sMap.put(x.key, x.val);
        subMap(x.right, fromKey, toKey, sMap);
    }

    @Override
    public SortedMap<Key, Value> headMap(Key toKey) {

        SortedMap<Key, Value> hMap = new TreeMap<Key, Value>();
        headMap(root, toKey, hMap);
        return hMap;
    }
    private void headMap(Node x, Key toKey, SortedMap<Key, Value> hMap){

        if(x == null) return;
        int cmpTo = toKey.compareTo(x.key);;
        headMap(x.left, toKey, hMap);

        if( cmpTo > 0) hMap.put(x.key, x.val);
        headMap(x.right, toKey, hMap);

    }

    @Override
    public SortedMap<Key, Value> tailMap(Key fromKey) {
        SortedMap<Key, Value> tMap = new TreeMap<Key, Value>();
        tailMap(root, fromKey, tMap);
        return tMap;
    }

    private void tailMap(Node x, Key fromKey, SortedMap<Key, Value> tMap){

        if(x == null) return;
        int cmpFrom = fromKey.compareTo(x.key);;
        tailMap(x.left, fromKey, tMap);

        if( cmpFrom <= 0) tMap.put(x.key, x.val);
        tailMap(x.right, fromKey, tMap);

    }

    @Override
    public Key firstKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return min(root).key;
    }

    @Override
    public Key lastKey() {
        if (isEmpty()) throw new NoSuchElementException();
        return max(root).key;
    }

    @Override
    public Set<Key> keySet() {
        Set<Key> keys = new HashSet<>();
        keySet(root, keys);
        return keys;
    }

    private void keySet(Node x, Set<Key> keys) {
        if (x == null) return;
        keySet(x.left, keys);
        keys.add(x.key);
        keySet(x.right, keys);
    }

    @Override
    public Collection<Value> values() {
        Collection<Value> values = new HashSet<>();
        valueSet(root, values);
        return values;
    }

    private void valueSet(Node x, Collection<Value> values) {
        if (x == null) return;
        valueSet(x.left, values);
        values.add(x.val);
        valueSet(x.right, values);
    }

    @Override
    public Set<Entry<Key, Value>> entrySet() {
        Set<Entry<Key, Value>> eSet = new HashSet<>();
        entrySet(root, eSet);
        return eSet;

    }
    private void entrySet(Node x, Set<Entry<Key, Value>> eSet) {
        if (x == null) return;
        entrySet(x.left, eSet);
        //Не работал с интерфейсом Map.Entry, не совсем понимаю как искользовать его в данном случае
  //      eSet.add(x.key, x.val);
        entrySet(x.right, eSet);
    }

    private class Node {
        private final Key key;
        private Value val;
        private int height;
        private int size;
        private Node left;
        private Node right;

        public Node(Key key, Value val, int height, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.height = height;
        }
    }

    private Node root;


    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Collection<Value> values = values();
        return values.contains(value);
    }

    @Override
    public Value get(Object key) {
        if (key == null) throw new IllegalArgumentException();
        Node x = get(root, (Key) key);
        if (x == null) return null;
        return x.val;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }


    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return x.height;
    }

    private Node get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x;
    }


    public Value put(Key key, Value val) {
        if (val == null || key == null) throw new NullPointerException();
        root = put(root, key, val);
        return val;

    }

    @Override
    public Value remove(Object key) {

        if (key == null) throw new IllegalArgumentException();
        if (!containsKey(key)) throw new NullPointerException();
        Value val = get(key);
        root = delete(root, (Key) key);
        return val;
    }

    @Override
    public void putAll(Map<? extends Key, ? extends Value> m) {
        for(Map.Entry<? extends Key, ? extends Value> entry : m.entrySet())
            put(entry.getKey(), entry.getValue());

    }

    @Override
    public void clear() {
// no thanks
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 0, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
            return x;
        }
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }


    private Node balance(Node x) {
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }


    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    private Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }


    private Node delete(Node x, Key key) {
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.left == null) {
                return x.right;
            } else if (x.right == null) {
                return x.left;
            } else {
                Node y = x;
                x = min(y.right);
                x.right = deleteMin(y.right);
                x.left = y.left;
            }
        }
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }


    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }


    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException();
        root = deleteMax(root);
    }


    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }


    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

}