package com.ss.ita.google.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ss.ita.google.ui.pages.runnerAndProperties.TestRunner.*;

public class SearchResultPage {

    public SearchResultPage doSearch(String searchText) {
        setSearchTerms(searchText).search();
        return this;
    }

    public SearchResultPage search() {
        driver.findElement(By.xpath("//button[@class = 'Tg7LZd']")).click();
        return this;
    }

    public SearchResultPage setSearchTerms(String searchText) {
        WebElement searchField = driver.findElement(By.xpath("//input[@class='gLFyf gsfi']"));
        searchField.clear();
        searchField.sendKeys(searchText);
        return this;
    }

    public String getLinkText(int numberOfLink) {
        return getSearchedLink(numberOfLink).getText();
    }
    public SearchResultPage changeFirstLinkColor(String color){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('.g>div>div>div>a>h3').style.color =" + color +";");
        return this;
    }

    public List<String> getResultLinksTexts() {
        return driver.findElements(By.xpath("//h3[contains(@class,'LC20lb')]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getLinkColor(int numberOfLink) {
        return getSearchedLink(numberOfLink).getCssValue("color");
    }

    public WebElement getSearchedLink(int numberOfLink) {
        return driver.findElements(By.xpath("//div[@id='rso']//h3[contains(@class,'LC20lb')]")).get(numberOfLink);
    }

    public SearchResultPage returnHomePage() {
        driver.findElement(By.xpath("//a/img")).click();
        return this;
    }
}
