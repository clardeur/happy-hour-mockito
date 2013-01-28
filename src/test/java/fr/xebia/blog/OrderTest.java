package fr.xebia.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Locale;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:clardeur@xebia.fr">Clement Lardeur</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    @Mock Product product1;
    @Mock Product product2;

    @Test
    public void should_have_a_total_price_equal_to_8_99() throws Exception {
        when(product1.getPrice()).thenReturn(new BigDecimal("3.99"));
        when(product2.getPrice()).thenReturn(new BigDecimal("5.00"));
        Order order = new Order(newArrayList(product1, product2));
        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal("8.99"));
    }

    @Test
    public void should_have_a_total_price_equals_to_20() throws Exception {
        Order order = new Order(newArrayList(product1, product2));
        given(product1.getPrice()).willReturn(BigDecimal.TEN); // GIVEN
        given(product2.getPrice()).willReturn(BigDecimal.TEN);
        BigDecimal totalPrice = order.getTotalPrice();         // WHEN
        assertThat(totalPrice).isEqualTo(new BigDecimal(20));  // THEN
    }

    @Test
    public void should_format_total_price_to_10_00_€_string() throws Exception {
        Order order = spy(new Order(null));
        doReturn(BigDecimal.TEN).when(order).getTotalPrice();
        assertThat(order.formatTotalPrice(Locale.FRANCE)).isEqualTo("10,00 €");
        verify(order, times(1)).getTotalPrice();
    }

    @Test
    public void should_have_an_empty_string_if_any_exception_occurs() throws Exception {
        Order order = spy(new Order(null));
        doThrow(Exception.class).when(order).getTotalPrice();
        assertThat(order.formatTotalPrice(Locale.US)).isEmpty();
    }
}
