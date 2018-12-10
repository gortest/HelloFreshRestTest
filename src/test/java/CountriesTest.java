import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;

import steps.CountriesSteps;

@RunWith(SerenityRunner.class)
public class CountriesTest {

  @Steps
  private CountriesSteps countriesSteps;

  @Test
  public void getNonExistingCountry() {
    countriesSteps.get_non_existing_country();
    countriesSteps.not_found_message_has_been_returned();
  }

  @Test
  public void addNewCountry() {
    countriesSteps.add_new_country(
        "test", "test", "test");
    countriesSteps.created_code_has_been_returned();
  }
}
