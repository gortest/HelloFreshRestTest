package testdata;

import org.json.JSONObject;

public class RequestDataHelper {

  public static String createBodyForNewCountryRequest(String name,
                                                          String code2, String code3) {
    JSONObject requestParams = new JSONObject();
    requestParams.put("name", name);
    requestParams.put("alpha2_code", code2);
    requestParams.put("alpha3_code", code3);

    return requestParams.toString();
  }
}
