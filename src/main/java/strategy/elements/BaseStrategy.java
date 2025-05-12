package strategy.elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarantlr4.v4.misc.OrderedHashMap;
import helper.CandleHelper;
import lombok.SneakyThrows;
import pojo.model.candles.Candle;
import utils.FileRoutine;

import java.util.List;
import java.util.Map;

public class BaseStrategy {

    private final ObjectMapper mapper = new ObjectMapper();
    protected FileRoutine fileRoutine = new FileRoutine();
    protected CandleHelper candleHelper = new CandleHelper();

    @SneakyThrows
    public <T> T getMappedObject(String json, Class<T> valueType) {
        return mapper.readValue(json, valueType);
    }
}
