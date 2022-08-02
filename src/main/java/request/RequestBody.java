package request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {

    private static final String QUERY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> parameters = new HashMap<>();

    public RequestBody(String parameters) {
        if (parameters == null || parameters.isBlank()) {
            return;
        }

        this.parameters.putAll(parse(parameters));
    }

    private Map<String, String> parse(String parameters) {
        return Arrays.stream(parameters.split(QUERY_DELIMITER))
            .map(keyValue -> keyValue.split(KEY_VALUE_DELIMITER))
            .filter(this::validateKeyValue)
            .collect(Collectors.toMap(key -> key[KEY_INDEX], value -> value[VALUE_INDEX]));
    }

    private Boolean validateKeyValue(String[] keyValue) {
        return keyValue.length > 1;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String get(String key) {
        return parameters.get(key);
    }
}
