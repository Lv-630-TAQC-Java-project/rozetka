package com.ss.ita.rozetka.ui.Modals;

import com.ss.ita.rozetka.ui.pages.*;

import static com.codeborne.selenide.Selenide.*;

public class HamburgerModal {

    public Header closeHamburgerModal() {
        $x("//button[@class='side-menu__close']").click();
        return new Header();
    }
}
