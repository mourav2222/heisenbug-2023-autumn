package guru.qa.rococo;

import com.codeborne.selenide.logevents.SelenideLogger;
import guru.qa.rococo.jupiter.first.ApiLogin;
import guru.qa.rococo.jupiter.second.AllureParamId;
import guru.qa.rococo.jupiter.second.WebTest;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@WebTest
class DemoWebTest {

  @BeforeEach
  void addAllureSelenide() {
    SelenideLogger.addListener(
        "AllureSelenide",
        new AllureSelenide()
            .screenshots(true)
            .savePageSource(true)
    );
  }

  @ApiLogin(username = "dima", password = "12345")
  @Test
  void usernameShouldBeVisibleInProfile() {
    $("[data-testid='avatar']").click();
    $("h4.text-center").should(text("dima"));
  }

  @CsvSource({
      "123, Morning in a pine forest",
      "124, Above eternal peace"
  })
  @ApiLogin(username = "ivan", password = "12345")
  @ParameterizedTest
  void paintingNameShouldEditedByAuthorizedUser(@AllureParamId String allureId, String painting) {
    $$("nav.list-nav a").find(text("Картины")).click();
    $$("div.text-center").find(text(painting)).parent().click();
    $("[data-testid='edit-painting']").click();
    $("input[name='title']").setValue("Edited");
    $("button[type='submit']").click();

    $("header.card-header").should(text("Edited"));
  }

  @ApiLogin(username = "petr", password = "12345")
  @Test
  void museumNameShouldEditedByAuthorizedUser() {
    $$("nav.list-nav a").find(text("Музеи")).click();
    $$("div.text-center").find(text("Hermitage")).parent().click();
    $("[data-testid='edit-museum']").click();
    $("input[name='title']").setValue("Edited");
    $("button[type='submit']").click();

    $("header.card-header").should(text("Edited"));
  }

  @ApiLogin(username = "valentin", password = "12345")
  @Test
  void artistNameShouldEditedByAuthorizedUser() {
    $$("nav.list-nav a").find(text("Художники")).click();
    $$("span.flex-auto").find(text("Shishkin")).parent().click();
    $("[data-testid='edit-artist']").click();
    $("input[name='name']").setValue("Edited");
    $("button[type='submit']").click();

    $("header.card-header").should(text("Edited"));
  }
}
