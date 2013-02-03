package fr.xebia.blog;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:clardeur@xebia.fr">Clement Lardeur</a>
 */
public class OrderTest {

    @Test
    public void should_instantiate_an_order_with_2_products() throws Exception {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        Order order = new Order(newArrayList(product1, product2));
        assertThat(product1).isNotNull();
        assertThat(product2).isNotNull();
        assertThat(order.products).hasSize(2);
    }

    @Test
    public void test_default_answer_mocking() throws Exception {
        Product product1 = mock(Product.class, RETURNS_DEFAULTS);
        assertThat(product1.getPrice()).isNull();
        // product1.getPrice().abs() -> NPE

        Product product2 = mock(Product.class, RETURNS_SMART_NULLS);
        assertThat(product2.getPrice()).isNotNull();
        // product2.getPrice().abs() -> NPE

        Product product3 = mock(Product.class, RETURNS_MOCKS);
        assertThat(product3.getPrice()).isNotNull();
        assertThat(product3.getPrice().abs()).isNotNull();
        assertThat(product3.getPrice().getClass().getName()).contains(BigDecimal.class.getName());
    }

    @Test
    public void should_have_a_total_price_equal_to_8_99() throws Exception {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        when(product1.getPrice()).thenReturn(new BigDecimal("3.99"));
        when(product2.getPrice()).thenReturn(new BigDecimal("5.00"));
        Order order = new Order(newArrayList(product1, product2));
        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal("8.99"));
    }

    @Test(expected = IllegalStateException.class)
    public void should_throws_exception_when_calling_get_price() throws Exception {
        Product product1 = mock(Product.class);
        when(product1.getPrice()).thenThrow(new IllegalStateException());
        product1.getPrice();
    }

    @Test
    public void should_format_total_price_to_10_00_euros_string() throws Exception {
        Order order = spy(new Order(mock(List.class)));
        doReturn(BigDecimal.TEN).when(order).getTotalPrice();
        assertThat(order.formatTotalPrice(Locale.FRANCE)).isEqualTo("10,00 €");
        verify(order, times(1)).getTotalPrice();
    }
}
