package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import static io.restassured.RestAssured.given;


public class HttpClient {

    private final static String TOKEN_TYPE = "Bearer";
    private final static String ACCESS_TOKEN = "10cd2bd217709a1a38d2bfff68cac557-5e04059acdbeaace0b9c3bf390aec7a2";
    private final static String ACCOUNT_ID = "101-004-31607564-001";


    protected static RequestSpecification getSpecification() {
        RequestSpecification request = given().relaxedHTTPSValidation();
        request.contentType(ContentType.JSON).header("Authorization", String.format("%1$s %2$s", TOKEN_TYPE, ACCESS_TOKEN));
        return request;
    }

    public static Response getPrice() {
        String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts/{accountID}/pricing";

        return getSpecification().header("Accept-Datetime-Format", "RFC3339").pathParam("accountID", ACCOUNT_ID).queryParam("instruments", "EUR_USD").queryParam("candleSpecifications", "EUR_USD:S10:BM").get(BASE_URL).then().log().all().extract().response();
    }

    public static Response getCandles(String instrument, String granularity, int count) {
        String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts/{accountID}/instruments/{instrument}/candles";

        return getSpecification().header("Accept-Datetime-Format", "RFC3339").pathParam("accountID", ACCOUNT_ID).pathParam("instrument", instrument)
//                .queryParam("instruments", "EUR_USD")
//                .queryParam("price", "BMA")
//                .queryParam("candleSpecifications", "EUR_USD:S10:BM")
                .queryParam("granularity", granularity).queryParam("count", count)
//                .queryParam("from", "2025-04-29T08:00:00.000000000Z")
//                .queryParam("to", "2025-04-29T18:00:00.000000000Z")
                .get(BASE_URL).then().log().all().extract().response();
    }

    public static Response getCandles(String instrument, String granularity, String from, String to) {
        String BASE_URL = "https://api-fxpractice.oanda.com/v3/accounts/{accountID}/instruments/{instrument}/candles";

        return getSpecification().header("Accept-Datetime-Format", "RFC3339").pathParam("accountID", ACCOUNT_ID).pathParam("instrument", instrument).queryParam("granularity", granularity).queryParam("from", from).queryParam("to", to).get(BASE_URL).then().log().all().extract().response();
    }

    @SneakyThrows
    public static void main(String[] args) {
        FileRoutine fileRoutine = new FileRoutine();
//        getCandles("EUR_USD", "M15", "2025-04-30T08:00:00.000000000Z", "2025-04-30T17:00:00.000000000Z");

        final String INSTRUMENT = "GBP_USD";
        final String GRANULARITY = "M15";
        final int COUNT = 5000;
        final String PATH = "src/main/resources/response/gbp-usd/4h-1h-15m/";
        final String FILE_NAME = INSTRUMENT + "_" + GRANULARITY + "_" + COUNT + "_candles.json";

        Response response = getCandles(INSTRUMENT, GRANULARITY, COUNT);
        fileRoutine.writeResourceAsString(PATH, FILE_NAME, response.prettyPrint());
    }
}
