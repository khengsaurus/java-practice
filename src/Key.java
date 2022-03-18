public class Key implements Comparable<Key> {
    int data;
    private String key;

    public Key(String key) {
        super();
        this.key = key;
//        this.data = data;
    }

    @Override
    public String toString() {
        return this.key;
    }

    @Override
    public int compareTo(Key obj) {
        return key.compareTo(obj.key);
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}