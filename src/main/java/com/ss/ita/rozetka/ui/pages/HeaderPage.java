package com.ss.ita.rozetka.ui.pages;

import com.codeborne.selenide.Selenide;
import com.ss.ita.rozetka.ui.elements.Header;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

import java.util.Base64;

public abstract class HeaderPage {
    public Header header = new Header();

    @Attachment(value = "pageScreenshot", type = "image/png")
    public static byte[] capturePNG() {
        String screenshot = Selenide.screenshot(OutputType.BASE64);
        System.out.println("332423");
        return Base64.getDecoder().decode(screenshot);
    }
}
