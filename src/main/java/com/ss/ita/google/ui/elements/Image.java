package com.ss.ita.google.ui.elements;

import com.ss.ita.google.ui.locators.BaseLocator;
import org.openqa.selenium.WebDriver;

public class Image extends BaseWebElement{

    public Image(WebDriver driver, BaseLocator locator) {
        super(driver, locator);
    }

    public void clickImage() {
        this.webElement.click();
    }
}
