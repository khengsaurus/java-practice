import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LintCode {
    public static void main(String[] args) {
        List<String> l = new ArrayList<>(Arrays.asList("lint", "code", "abc"));
        String encoded = encode(l);
        l = decode(encoded);
        System.out.println(l);
    }

    public static String encode(List<String> strs) {
        StringBuilder str = new StringBuilder();
        for (String s : strs) str.append(s.length() + "#" + s);
        return str.toString();
    }

    public static List<String> decode(String str) {
        List<String> res = new ArrayList<>();
        int i = 0, strLen = 0;
        boolean inWord = false;
        StringBuilder sb = new StringBuilder();
        while (i < str.length()) {
            if (inWord) {
                sb.append(str.charAt(i));
                if (--strLen == 0) {
                    res.add(sb.toString());
                    sb.setLength(0);
                    inWord = false;
                }
            } else {
                if (str.charAt(i) == '#') {
                    strLen = Integer.parseInt(sb.toString());
                    inWord = true;
                    sb.setLength(0);
                } else {
                    sb.append(str.charAt(i));
                }
            }
            i++;
        }
        return res;
    }
}
