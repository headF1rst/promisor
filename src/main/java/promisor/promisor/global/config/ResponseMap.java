package promisor.promisor.global.config;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseMap extends ApiResponse {

    private Map responseData = new HashMap();

    public ResponseMap() {
        changeResult(responseData);
    }

    public void changeResponseData(String key, Object value) {
        this.responseData.put(key, value);
    }

}
