$(document).ready(function() {
	console.log(4555)
	var $grid = $('.content-listProduct .row').isotope({
		itemSelector: '.product-item',
		layoutMode: 'fitRows',
		getSortData: {
			price: function(itemElem) {
				var priceText = $(itemElem).find('.product-price').text();
				var priceValue = parseFloat(priceText.replace("đ", "").replace(/\,/g, ""));
				return priceValue;
			},
			name: '.product_name'
		}
	});

	$(".filter-checkbox, #sort-select").change(function() {
		var brandFilters = [];
		var categoryFilters = [];
		var priceFilters = [];

		$(".brand-checkbox:checked").each(function() {
			brandFilters.push("." + $(this).val()); // Thêm dấu chấm trước tên thương hiệu
		});

		$(".category-checkbox:checked").each(function() {
			categoryFilters.push("." + $(this).val()); // Thêm dấu chấm trước tên phân loại
		});

		$(".price-checkbox:checked").each(function() {
			var filterValue = parseInt($(this).val());
			priceFilters.push(function() {
				var productPrice = parseFloat($(this).find(".product-price").text().replace("đ", "").replace(/\,/g, ""));
				console.log(productPrice)
				if (productPrice <= filterValue) {
					return productPrice <= filterValue;
				}

			});
		});

		var totalFilter = function() {
			var $this = $(this);
			var passBrand = brandFilters.length === 0 || brandFilters.indexOf("." + $this.data("brand")) !== -1;
			var passCategory = categoryFilters.length === 0 || categoryFilters.indexOf("." + $this.data("category")) !== -1;
			var passPrice = priceFilters.length === 0 || priceFilters.some(function(filter) {
				return filter.call($this);
			});
			return passBrand && passCategory && passPrice;
		};

		$grid.isotope({ filter: totalFilter });
	});

	// Xử lý sự kiện khi sắp xếp thay đổi
	$("#sort-select").change(function() {
		var sortBy = $(this).val();
		console.log(sortBy)
		var sortAscending = true;
		var byWhat = '';
		if (sortBy === "0" || sortBy === "1") {
			console.log(1)
			byWhat = 'price'
		} else {
			byWhat = 'name'
		}
		if (sortBy === "1" || sortBy === "3") {
			sortAscending = false;
		}

		$grid.isotope({ sortBy: byWhat, sortAscending: sortAscending });
	});


});
