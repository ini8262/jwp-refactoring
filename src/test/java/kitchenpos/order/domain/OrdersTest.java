package kitchenpos.order.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import kitchenpos.ordertable.domain.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class OrdersTest {

	@DisplayName("결제완료된 주문은 상태를 변경할 수 없음")
	@Test
	void changeOrderStatus() {
		Orders order = new Orders();
		ReflectionTestUtils.setField(order, "orderStatus", OrderStatus.COMPLETION.name());

		//when, then
		assertThatIllegalArgumentException()
			  .isThrownBy(() -> order.changeStatus(OrderStatus.MEAL.name()))
			  .withMessage("결제 완료된 주문은 상태를 변경할 수 없습니다.");
	}

	@DisplayName("비어있는 테이블은 주문을 등록할 수 없음")
	@Test
	void createWithEmptyTable() {
		//when, then
		assertThatIllegalArgumentException()
			  .isThrownBy(() -> new Orders(new OrderTable(true), OrderStatus.COOKING.name()))
			  .withMessage("테이블이 비어있습니다.");
	}
}
