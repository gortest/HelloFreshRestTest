package steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import testdata.RequestDataHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class CountriesSteps {

  private final int FOUND_STATUS = 200;
  private final int CREATED_STATUS = 201;
  private final String BASE_URL = "http://services.groupkt.com";
  private final String GET_ALL_PATH = "/country/get/all";
  private final String GET_COUNTRY_PATH = "/country/get/iso2code/";
  private final String ADD_COUNTRY_PATH = "/country/add/";
  private final String NON_EXISTING_COUNTRY = "nonexisting";

  private Response response;

  @Step
  public void search_country_by_code(String code) {
    response = SerenityRest.when().get(BASE_URL + GET_COUNTRY_PATH + code);
  }

  @Step
  public void success_code_has_been_returned() {
    response.then().statusCode(FOUND_STATUS);
  }

  @Step
  public void created_code_has_been_returned() {
    response.then().statusCode(CREATED_STATUS);
  }

  @Step
  public void not_found_message_has_been_returned() {
    response.then().body("RestResponse.messages[0]",
        is("No matching country found for requested code [" + NON_EXISTING_COUNTRY + "]."));
  }

  @Step
  public void correct_country_response_has_been_returned(
      String country, String alpha2Code, String alpha3Code) {
    response.then().body("RestResponse.result.name", is(country));
    response.then().body("RestResponse.result.alpha2_code", is(alpha2Code));
    response.then().body("RestResponse.result.alpha3_code", is(alpha3Code));
  }

  @Step
  public void get_all_countries() {
    response = SerenityRest.when().get(BASE_URL + GET_ALL_PATH);
  }

  @Step
  public void check_if_us_gb_de_have_been_returned(
      String country, String alpha2Code, String alpha3Code) {

    ArrayList<HashMap<String, String>> countries = response.jsonPath().get("RestResponse.result");
    assertThat("All countries should be returned", countries, is(notNullValue()));

    boolean countryFound = false;
    for(HashMap<String, String> countryMap : countries) {
      if(countryMap.get("name").equals(country)) {
        assertThat("Code is wrong", countryMap.get("alpha2_code"), is(alpha2Code));
        assertThat("Code3 is wrong", countryMap.get("alpha3_code"), is(alpha3Code));
        countryFound = true;
        break;
      }
    }

    assertThat("Country" + country + " not found", countryFound, is(true));
  }

  @Step
  public void get_non_existing_country() {
    response = SerenityRest.when().get(BASE_URL + GET_COUNTRY_PATH + NON_EXISTING_COUNTRY);
  }

  @Step
  public void add_new_country (String country, String alpha2Code, String alpha3Code) {
    String requestBody = RequestDataHelper.
        createBodyForNewCountryRequest(country, alpha2Code, alpha3Code);

    response = SerenityRest.given().contentType("application/json")
        .body(requestBody).when().post(BASE_URL + ADD_COUNTRY_PATH);
  }
}
