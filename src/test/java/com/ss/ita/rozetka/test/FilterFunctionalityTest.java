package com.ss.ita.rozetka.test;

import com.ss.ita.rozetka.pageobject.elements.filters.FilterSideBar;
import com.ss.ita.rozetka.pageobject.modals.BasketModal;
import com.ss.ita.rozetka.pageobject.pages.HomePage;
import com.ss.ita.rozetka.pageobject.pages.ProductPage;
import com.ss.ita.rozetka.pageobject.pages.ProductTypePage;
import com.ss.ita.rozetka.pageobject.utils.TestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

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

        var company ="Dell";

        productTypePage
                .filterProductsByParameters(company);

        assertThat(productTypePage.getProductTitle(1))
                .as("Title should contains Dell")
                .contains(company);

        FilterSideBar filterSideBar = new FilterSideBar()
                .setMinPrice(15000)
                .setMaxPrice(50000);

        productTypePage.getProductPricesList().stream().filter(s->s>=15000&&s<=50000);
        int productPrisesCount=productTypePage.getProductPricesList().size();
        productTypePage.getProductPricesList().size();

        boolean status = false;
        for (int i = 0; i <= productPrisesCount; i++) {
            if (productTypePage.getProductPricesList().get(i) > 50000 && productTypePage.getProductPricesList().get(i) < 15000) {
                status = true;
            } else status = false;
        }
    }
}
