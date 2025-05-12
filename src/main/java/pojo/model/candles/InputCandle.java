
package pojo.model.candles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class InputCandle {

    @JsonProperty("instrument")
    private String instrument;

    @JsonProperty("granularity")
    private String granularity;

    @JsonProperty("candles")
    private List<Candle> candles = new ArrayList<Candle>();

    public List<Candle> getCandles() {
        return candles.stream().filter(candle -> candle.getComplete().equals(true)).collect(Collectors.toList());
    }
}
