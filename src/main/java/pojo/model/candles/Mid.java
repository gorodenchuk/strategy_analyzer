
package pojo.model.candles;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class Mid {

    @JsonProperty("o")
    private String o;

    @JsonProperty("h")
    private String h;

    @JsonProperty("l")
    private String l;

    @JsonProperty("c")
    private String c;

    @Override
    public String toString() {
        return "Mid{" +
                "o='" + o + '\'' +
                ", h='" + h + '\'' +
                ", l='" + l + '\'' +
                ", c='" + c + '\'' +
                '}';
    }
}
