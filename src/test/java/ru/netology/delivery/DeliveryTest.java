package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void DeliveryFormTest() {
        String meetingDate = generateDate(7);

        $("[data-test-id = 'city'] input").setValue("Санкт-Петербург");
        $("[data-test-id = 'date'] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(meetingDate);
        $x("//input[@name='name']").setValue("Иванов Василий");
        $x("//input[@name='phone']").setValue("+79995556677");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}