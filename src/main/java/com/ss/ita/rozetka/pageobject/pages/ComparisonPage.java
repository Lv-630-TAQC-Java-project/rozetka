package com.ss.ita.rozetka.pageobject.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;

public class ComparisonPage {
    @Step("ComparisonPage: get product list")
    private List<SelenideElement> getProductList() {
        return $$x("//a[@class='product__heading']")
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
    }

    @Step("ComparisonPage: get product comparison list size")
    public int sizeOfProductComparisonList() {
        return getProductList().size();
    }

}
