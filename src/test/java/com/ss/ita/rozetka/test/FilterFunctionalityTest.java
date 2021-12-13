package com.ss.ita.rozetka.test;

import com.ss.ita.rozetka.pageobject.modals.BasketModal;
import com.ss.ita.rozetka.pageobject.pages.HomePage;
import com.ss.ita.rozetka.pageobject.pages.ProductPage;
import com.ss.ita.rozetka.pageobject.pages.ProductTypePage;
import com.ss.ita.rozetka.pageobject.utils.TestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static com.ss.ita.rozetka.pageobject.elements.filters.FilterName.PRODUCER;
import static com.ss.ita.rozetka.pageobject.elements.filters.FilterName.SELLER;
import static com.ss.ita.rozetka.pageobject.product.GeneralProductCategory.NOTEBOOKS_AND_COMPUTERS;
import static com.ss.ita.rozetka.pageobject.product.ProductCategoryAndSubCategory.MONITORS_CATEGORY;
import static com.ss.ita.rozetka.pageobject.product.ProductCategoryAndSubCategory.NOTEBOOKS_CATEGORY;
import static com.ss.ita.rozetka.pageobject.utils.PageUtil.getCurrentUrl;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterFunctionalityTest extends TestRunner {

    @Test
    @Description("Verify that user can filter products with parameters")
    @TmsLink(value = "LVTAQC630-22")
    public void verifyUserCanFilterProducts() {
        ProductTypePage productTypePage = new HomePage()
                .open()
                .openProductCategoryPage(NOTEBOOKS_AND_COMPUTERS)
                .openProductTypePage(MONITORS_CATEGORY);

        assertThat(getCurrentUrl())
                .as("Url should contain 'Monitors'")
                .contains("monitors");

        String company = "Samsung";
        String resolution = "1920x1080";

        productTypePage
                .filterProductsByParameters(company)
                .filterProductsByParameters(resolution);

        assertThat(productTypePage.getProductTitle(1))
                .as("Title should be Samsung")
                .contains(company);

        ProductPage productPage = productTypePage.openProductPage(1);

        assertThat(productPage
                .getProductCharacteristics())
                .as("In characteristics should be 1920 x 1080 resolution")
                .contains("1920 x 1080");

        BasketModal<ProductPage> basket = productPage.addProductToBasket();

        int totalPriceWithoutOptions = basket.getProductsTotalPrice();

        basket.addAdditionalServices();

        int totalPriceWithOptions = basket.getProductsTotalPrice();

        assertThat(totalPriceWithoutOptions)
                .as("Total price should be changed")
                .isLessThan(totalPriceWithOptions);
    }

    @Test
    @Description("Verify that after changing and submitting price bounds displayed products with correct price")
    @TmsLink(value = "LVTAQC630-55")
    public void verifyThatPriceFilterIsCorrect() {
        var productTypePage = new HomePage()
                .open()
                .openProductCategoryPage(NOTEBOOKS_AND_COMPUTERS)
                .openProductTypePage(NOTEBOOKS_CATEGORY);

        var company = "Dell";
        var filterSideBar = productTypePage.getFilterSideBar();

        filterSideBar
                .getFilter(PRODUCER)
                .selectOption(company);

        assertThat(productTypePage.getProductTitle(1))
                .as("Title should contains Dell")
                .contains(company);

        int minPrice = 15000;
        int maxPrice = 50000;

        filterSideBar
                .setMinPrice(minPrice)
                .setMaxPrice(maxPrice)
                .filterByPrice();

        int productPrisesCount = productTypePage.getProductsCount();
        var productPricesList = productTypePage.getProductPricesList();
        SoftAssertions softAssertion = new SoftAssertions();

        for (int i = 0; i < productPrisesCount; i++) {
            softAssertion
                    .assertThat(productPricesList.get(i))
                    .as("Price should be in selected bounds")
                    .isGreaterThan(minPrice)
                    .isLessThan(maxPrice);
        }
        softAssertion.assertAll();
    }

    @Test
    @TmsLink(value = "LVTAQC630-53")
    @Description(value = "Verify that after multiply filtering all products corresponds to selected filter options")
    public void verifyProductsCorrespondsToSelectedFilterOptions(){
        var productCategoryPage = new HomePage()
                .open()
                .openProductCategoryPage(NOTEBOOKS_AND_COMPUTERS);
        assertThat(productCategoryPage.isOpened())
                .as("Product category page should be opened")
                .isTrue();
        var  productTypePage = productCategoryPage.openProductTypePage(NOTEBOOKS_CATEGORY);
        assertThat(productTypePage.isOpened())
                .as("Product type page should be opened")
                .isTrue();
        var filterSideBar = productTypePage.getFilterSideBar();
        filterSideBar
                .getFilter(SELLER)
                .selectOption("Rozetka");
        filterSideBar
                .getFilter(PRODUCER)
                .selectOption("Dell");

        sleep(10000);

    }
}
