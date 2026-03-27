import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Objects;

public class DialingCodes {
    Map<Integer, String> dialingCodes;

    public DialingCodes() {
        this.dialingCodes = new HashMap<>();
    }

    public Map<Integer, String> getCodes() {
        return this.dialingCodes;
    }

    public void setDialingCode(Integer code, String country) {
        this.dialingCodes.put(code, country);
    }

    public String getCountry(Integer code) {
        return this.dialingCodes.get(code);
    }

    public void addNewDialingCode(Integer code, String country) {
        if (this.dialingCodes.containsKey(code) || this.dialingCodes.containsValue(country))
            return;
        this.dialingCodes.put(code, country);
    }

    public Integer findDialingCode(String country) {
        Integer result = null;
        for (Map.Entry<Integer, String> dc : this.dialingCodes.entrySet()) {
            if (Objects.equals(dc.getValue(), country)) {
                result = dc.getKey();
            }
        }

        return result;
    }

    public void updateCountryDialingCode(Integer code, String country) {
        Integer oldCode = findDialingCode(country);

        this.dialingCodes.remove(oldCode);

        setDialingCode(code, country);
    }
}
