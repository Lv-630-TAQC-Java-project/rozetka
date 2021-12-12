package com.ss.ita.rozetka.test;

import com.ss.ita.rozetka.pageobject.elements.Product;
import com.ss.ita.rozetka.pageobject.elements.filters.FilterName;
import com.ss.ita.rozetka.pageobject.pages.HomePage;
import com.ss.ita.rozetka.pageobject.utils.TestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.ss.ita.rozetka.pageobject.elements.filters.FilterName.PRODUCER;
import static com.ss.ita.rozetka.pageobject.product.GeneralProductCategory.NOTEBOOKS_AND_COMPUTERS;
import static com.ss.ita.rozetka.pageobject.product.ProductCategoryAndSubCategory.NOTEBOOKS_CATEGORY;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest extends TestRunner {
    @Test
    @Description("Verify that after selecting one filter option the amount of products that corresponds to filter is equal to quantity in filter")
    @TmsLink("LVTAQC630-50")
    public void verifyFilteredProductsAmountIsCorrect() {
        var productTypePage = new HomePage()
                .open()
                .openProductCategoryPage(NOTEBOOKS_AND_COMPUTERS)
                .openProductTypePage(NOTEBOOKS_CATEGORY);
        var filter = productTypePage
                .getFilterSideBar()
                .getFilter(FilterName.SELLER);

        var optionName = "Rozetka";
        int expectedProductsAmount = filter.getOptionProductsQuantity(optionName);

        filter.selectOption(optionName);
        int actualProductsAmount = productTypePage.getSelectedProductsAmount();

        assertThat(actualProductsAmount)
                .as("The amount of products that corresponds to option should be equal to selected products on page")
                .isEqualTo(expectedProductsAmount);
    }

    @Test
    @Description("Verify that after selecting one filter option in other filters amount of options will decrease")
    @TmsLink(value = "LVTAQC630-54")
    public void verifyOptionsAmountDecreasingAfterFilterSelecting() {
        var productTypePage = new HomePage()
                .open()
                .openProductCategoryPage(NOTEBOOKS_AND_COMPUTERS)
                .openProductTypePage(NOTEBOOKS_CATEGORY);

        var producerFilter = productTypePage
                .getFilterSideBar()
                .getFilter(PRODUCER);

        int countBeforeSelecting = producerFilter.getOptionsQuantityInFilter();

        var sellerFilter = productTypePage
                .getFilterSideBar()
                .getFilter(FilterName.SELLER);

        sellerFilter.selectOption("Rozetka");

        assertThat(countBeforeSelecting)
                .as("countBeforeSelecting should be greater than count after filtration")
                .isGreaterThan(producerFilter.getOptionsQuantityInFilter());
    }

    @Test
    @Description("Verify that after selecting filter options and submitting some price bound products correspond to filters")
    @TmsLink("LVTAQC630-62")
    public void verifyPriceFilterAndRegularFilterWorksCorrectlyTogether() {
        var productTypePage = new HomePage()
                .open()
                .openProductCategoryPage(NOTEBOOKS_AND_COMPUTERS)
                .openProductTypePage(NOTEBOOKS_CATEGORY);
        var filterSidebar = productTypePage.getFilterSideBar();

        var producerFilterOptionName = "ASUS";
        filterSidebar.getFilter(PRODUCER).selectOption(producerFilterOptionName);

        int lowerPriceBound = 5000;
        int upperPriceBound = 20000;
        filterSidebar
                .setMinPrice(lowerPriceBound)
                .setMaxPrice(upperPriceBound)
                .filterByPrice();

        int productsAmountOnPage = productTypePage.getProductsCount();
        List<Product> productsList = IntStream
                .rangeClosed(1, productsAmountOnPage)
                .mapToObj(productTypePage::getProduct)
                .collect(toList());

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(productsList)
                .as("Selected products should have names corresponding to filter")
                .map(Product::getTitle)
                .filteredOn(title -> containsIgnoreCase(title, producerFilterOptionName))
                .hasSameSizeAs(productsList);

        softly.assertThat(productsList)
                .as("Selected product prices should be in selected price bounds")
                .map(Product::getPrice)
                .filteredOn(price -> price >= lowerPriceBound && price <= upperPriceBound)
                .hasSameSizeAs(productsList);

        softly.assertAll();
    }
}
