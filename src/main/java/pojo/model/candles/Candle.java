package pojo.model.candles;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.annotation.processing.Generated;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class Candle {

    @JsonProperty("complete")
    private Boolean complete;

    @JsonProperty("volume")
    private Integer volume;

    @JsonProperty("time")
    private String time;

    @JsonProperty("mid")
    private Mid mid;

    @Override
    public String toString() {
        return "Candle{" + "complete=" + complete + ", volume=" + volume + ", time='" + time + '\'' + ", mid=" + mid + '}';
    }
}
