package pomobox.utils;

public class StringUtil {

    public static StringBuilder getStringUtil(String...strings){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:strings){
            stringBuilder.append(s);
        }
        return stringBuilder;
    }
}
