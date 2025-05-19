package pojo.model.candles;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.annotation.processing.Generated;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candle)) return false;

        Candle candle = (Candle) o;

        if (!complete.equals(candle.complete)) return false;
        if (!volume.equals(candle.volume)) return false;
        if (!time.equals(candle.time)) return false;
        return mid.equals(candle.mid);
    }


    @Override
    public int hashCode() {
        return Objects.hash(getComplete(), getVolume(), getTime(), getMid());
    }
}
