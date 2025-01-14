package kitchenpos.application;

import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.orderTable.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public OrderService(
            final MenuRepository menuRepository,
            final OrderRepository orderRepository,
            final OrderTableRepository orderTableRepository
    ) {
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> list() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order create(final Order order) {
        validationByNewOrder(order);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    protected void validationByNewOrder(final Order order) {
        orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(() -> new IllegalArgumentException("테이블이 존재하지 않습니다."));

        final List<Long> menuIds = order.getOrderLineItemIds();

        Long menuCount = menuRepository.countByIdIn(menuIds);
        if (!order.isSameSize(menuCount)) {
            throw new IllegalArgumentException("메뉴가 존재하지 않습니다.");
        }
    }

    public Order changeOrderStatus(final Long orderId, final Order order) {
        final Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문은 존재하지 않습니다."));

        savedOrder.changeOrderStatus(order.getOrderStatus());

        return savedOrder;
    }
}