package com.ss.ita.google.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public SearchResultPage doSearch(String searchText) {
        return setSearchTerms(searchText).search();
    }

    public HomePage setSearchTerms(String terms) {
        WebElement input = driver.findElement(By.xpath("//input[@class='gLFyf gsfi']"));
        input.clear();
        input.sendKeys(terms);
        return this;
    }

    public SearchResultPage search() {
        driver.findElement(By.xpath("//input[@class='gNO89b'][1]")).click();
        return new SearchResultPage(driver);
    }

    public HomePage changeLanguage(String language) {
        driver.findElement((By.xpath("//a[contains(text(),'" + language + "')]"))).click();
        return new HomePage(driver);
    }

    public String getSearchButtonText() {
        return driver.findElement(By
                .xpath("//div[@class='FPdoLc lJ9FBc']//input[@name='btnK']")).getAttribute("value");
    }
}
