import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;

import steps.CountriesSteps;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "./src/main/resources/testdata.csv")
public class CountriesParameterizedTest {

  private String countryCode;
  private String countryName;
  private String code3;

  @Steps
  private CountriesSteps countriesSteps;

  @Test
  public void findUnitedStatesGermanyGreatBritainUsingCodes() {
    countriesSteps.search_country_by_code(countryCode);
    countriesSteps.success_code_has_been_returned();
    countriesSteps.correct_country_response_has_been_returned(
        countryName, countryCode, code3);

  }

  @Test
  public void getAllAndCheckUnitedStatesGermanyGreatBritainAreReturned() {
    countriesSteps.get_all_countries();
    countriesSteps.success_code_has_been_returned();
    countriesSteps.check_if_us_gb_de_have_been_returned(countryName, countryCode, code3);

  }
}
