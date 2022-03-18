import java.util.Map;
import java.util.TreeMap;

class MyTreeMap {
    public static void main(String[] args) {
        Map<Key, String> treemap = new TreeMap<>();
        treemap.put(new Key("Key4"), "Zeya");
        treemap.put(new Key("Key3"), "Geek");
        treemap.put(new Key("Key2"), "Bob");
        treemap.put(new Key("Key1"), "Alice");

        for (Map.Entry<Key, String> entry :
                treemap.entrySet())

            System.out.println(
                    "[" + entry.getKey() + " = "
                            + entry.getValue() + "]");
    }
}