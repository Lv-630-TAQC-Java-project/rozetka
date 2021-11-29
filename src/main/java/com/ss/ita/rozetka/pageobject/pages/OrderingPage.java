package com.ss.ita.rozetka.pageobject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Or;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.Integer.parseInt;

public class OrderingPage {

    @Step("OrderingPage: get total products price")
    public int getTotalProductsPrice() {
        return parseInt($x("//dd[@class = 'checkout-total__value checkout-total__value_size_large']")
                .text()
                .replaceAll("[^0-9]", StringUtils.EMPTY));
    }

    @Step("OrderingPage: get ordering page status")
    public boolean isOrderingPageOpened() {
        return $x("//div[@class = 'checkout-sidebar ng-star-inserted']")
                .shouldBe(Condition.visible)
                .isDisplayed();
    }

    @Step("OrderingPage: get product title")
    public String getProductTitle() {
        return $x("//div[@class = 'checkout-product__title-product']").text();
    }

    @Step("OrderingPage: set Surname")
    public OrderingPage setSurname(String surname) {
        $("div[class='form__row js-surname'] input[type='text']").sendKeys(surname);
        return this;
    }

    @Step("OrderingPage: set Name")
    public OrderingPage setName(String name) {
        $("div[class='form__row js-name'] input[type='text']").sendKeys(name);
        return this;
    }

    @Step("OrderingPage: set phoneNumber")
    public OrderingPage setPhoneNumber(String number) {
        $("input[class='ng-invalid ng-dirty ng-touched']").sendKeys(number);
        return this;
    }

    @Step("OrderingPage: set required field")
    public OrderingPage setRequiredFields(String surname, String name, String number) {
        setSurname(surname);
        setName(name);
        setPhoneNumber(number);
        return this;
    }

    @Step("OrderingPage: get name error message")
    public boolean getNameErrorMessage() {
        return $x("//p[contains(text(),\"Введіть своє ім'я кирилицею\")]").isDisplayed();
    }

    @Step("OrderingPage: get surname error message")
    public boolean getSurnameErrorMessage() {
        return $x("//p[contains(text(),'Введіть своє прізвище кирилицею')]").isDisplayed();
    }

    @Step("OrderingPage: get phone number  error message")
    public boolean getPhoneNumberErrorMessage() {
        return $x("//p[contains(text(),'Введіть номер мобільного телефону')]").isDisplayed();
    }

    @Step("OrderingPage: confirm the order")
    public void confirmTheOrder() {
        $("input.button--green").click();
    }

}
