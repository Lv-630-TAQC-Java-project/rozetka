package com.ss.ita.rozetka.ui;

import com.ss.ita.rozetka.ui.Modals.SideMenuModal;
import com.ss.ita.rozetka.ui.pages.HomePage;
import com.ss.ita.rozetka.ui.runner.TestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SideMenuModalTest extends TestRunner {

    @Test
    public void verifyOpenHamburgerModal() {
        HomePage homePage = new HomePage().open();
        homePage.getHeader().openSideMenuModal();
        Assert.assertTrue(homePage.getHeader().isVisibleSideMenuModal());
    }

    @Test
    public void verifyCloseHamburgerModal() {
        HomePage homePage = new HomePage().open();
        SideMenuModal menu = homePage.getHeader().openSideMenuModal();
        menu.closeSideMenuModal();
        Assert.assertFalse(homePage.getHeader().isVisibleSideMenuModal());
    }
 }
