package com.ss.ita.rozetka.test;

import com.codeborne.selenide.CollectionCondition;
import com.ss.ita.rozetka.pageobject.elements.Header;
import com.ss.ita.rozetka.pageobject.elements.Product;
import com.ss.ita.rozetka.pageobject.pages.HomePage;
import com.ss.ita.rozetka.pageobject.pages.ProductTypePage;
import com.ss.ita.rozetka.pageobject.utils.TestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchFunctionalityTest extends TestRunner {

    @Test
    @Description("Verify that user can do search with Xiaomi products")
    @TmsLink(value = "LVTAQC630-10")
    public void verifyXiaomiWillBeInSearchResult() {
        String searchProduct = "Xiaomi";

        ProductTypePage productTypePage = new HomePage()
                .open()
                .getHeader()
                .doSearch(searchProduct);

        assertThat(productTypePage.getProductTitle(1))
                .as("Product should have Xiaomi in title")
                .contains(searchProduct);
    }

    @Test
    @TmsLink(value = "LVTAQC630-4")
    @Description(value = "Verify that searched product was added to search history")
    public void verifySearchHistoryText() {
        var header = new HomePage()
                .open()
                .getHeader();
        var searchTerm = "DELL";
        var searchResultPage = header.doSearch(searchTerm);
        assertThat(searchResultPage.isSelectSortingTypeDisplayed())
                .as("Select sorting type should be displayed")
                .isTrue();
        var homePage = header.openHomePage();
        assertThat(homePage.isMainMenuCategoriesDisplayed())
                .as("Main Menu Categories should be displayed")
                .isTrue();
        int numberSearchTerm = 1;
        var actualSearchTerm = header
                .setSearchInputInFocus()
                .getTextFromSearchHistory(numberSearchTerm);
        assertThat(actualSearchTerm)
                .as("Last search term and first text in search history should be equals")
                .isEqualTo(searchTerm);
    }

    @Test
    @Description(value  = "Verify that search from search history works correct and items in the search history in right order")
    public void verifySearchFromSearchHistory() {
        Header header = new HomePage()
                .open()
                .getHeader();
        List<String> searchTerms = new ArrayList<>(Arrays.asList("Dell", "НР", "Bosch", "IPhone", "Stihl"));
        ProductTypePage searchResultPage;
        int productCount;
        SoftAssertions softAssert = new SoftAssertions();
        for (String searchTerm : searchTerms) {
            searchResultPage = header.doSearch(searchTerm);
            productCount = searchResultPage.getProductsCount();
            for (int i = 1; i <= productCount; i++) {
                String productTitle = searchResultPage
                        .getProduct(i)
                        .getTitle()
                        .toUpperCase();
                softAssert.assertThat(productTitle)
                        .as("Product title should contains search term")
                        .contains(searchTerm.toUpperCase());
            }
            header.openHomePage();
        }
        header.setSearchInputInFocus();
        softAssert.assertAll();
    }
}
