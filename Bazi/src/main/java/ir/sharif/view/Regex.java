package ir.sharif.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regex {
    USERNAME("^[a-zA-Z0-9-]+$"),
    PASSWORD("^[A-Za-z\\d@$!%*?&]+$"),
    STRONG_PASSWORD("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"),
    EMAIL("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
    PLACE_CARD("^place card -index (?<index>\\d+) -pos (?<pos>\\d+)$"),
    VETO_CARD("^veto card -number (?<number>\\d+)$"),
    EXECUTE_LEADER("^execute leader$"),
    PASS_TURN("^pass turn$");



    private final Pattern pattern;

    Regex(String input){
        pattern = Pattern.compile(input);
    }

    public Matcher getMatcher(String input){
        return pattern.matcher(input);
    }

    public boolean matches(String input){
        return getMatcher(input).matches();
    }
}
