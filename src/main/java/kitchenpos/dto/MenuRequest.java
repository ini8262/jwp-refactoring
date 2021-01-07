package kitchenpos.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

public class MenuRequest {
	private String name;
	private BigDecimal price;

	@NotNull(message = "메뉴 그룹 정보가 없습니다.")
	private Long menuGroupId;
	private List<MenuProductItem> menuProducts;

	private MenuRequest() {
	}

	private MenuRequest(String name, BigDecimal price, Long menuGroupId, List<MenuProductItem> menuProducts) {
		this.name = name;
		this.price = price;
		this.menuGroupId = menuGroupId;
		this.menuProducts = menuProducts;
	}

	public static MenuRequest of(String name, BigDecimal price, Long menuGroupId, List<MenuProductItem> menuProducts) {
		return new MenuRequest(name, price, menuGroupId, menuProducts);
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getMenuGroupId() {
		return menuGroupId;
	}

	public List<MenuProductItem> getMenuProducts() {
		return menuProducts;
	}

	public List<Long> getProductIds() {
		return menuProducts.stream()
			.map(MenuProductItem::getProductId)
			.collect(Collectors.toList());
	}

	public List<Long> getQuantities() {
		return menuProducts.stream()
			.map(MenuProductItem::getQuantity)
			.collect(Collectors.toList());
	}
}