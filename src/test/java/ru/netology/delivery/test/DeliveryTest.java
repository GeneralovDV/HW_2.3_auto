package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder=\"Город\"]").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[name=\"name\"]").setValue(validUser.getName());
        $("[name=\"phone\"]").setValue(validUser.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Запланировать\"]").click();
        $x("//div[@class=\"notification__title\"]").shouldBe(Condition.visible, Duration.ofSeconds(15));//задержка
        $("[class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $x("//span[text()=\"Запланировать\"]").click();
//        $("[data-test-id=\"replan-notification\"]").shouldBe(Condition.visible, Duration.ofSeconds(15));//задержка
//        $("[data-test-id=\"replan-notification\"]").shouldHave(Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать? "));
        $x("//span[text()=\"Перепланировать\"]").click();
        $("[data-test-id=\"success-notification\"]").shouldBe(Condition.visible, Duration.ofSeconds(15));//задержка
        $("[class=\"notification__content\"]").shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.о
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
    }
}