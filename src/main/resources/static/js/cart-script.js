$(document).ready(function() {
	console.log('this is cart js');

	$('.addToCart').click(function(e) {
		e.preventDefault();
		let productId = $(this).data('product-id');
		let isLoggedIn = $(this).data('user-id') !== '';
		let quantityInCart = $(".qty").val() == 'undefined' ? 1 : $(".qty").val();
		if (!isLoggedIn) {
			Swal.fire({
				icon: 'error',
				title: 'Oops...',
				text: 'Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng!',
				confirmButtonColor: '#dc3545',
				confirmButtonText: 'Đăng nhập'
			}).then((result) => {
				if (result.isConfirmed) {
					window.location.href = '/account/sign-in';
				}
			});
		} else {
			$.ajax({
				type: 'POST',
				url: '/cart/add-to-cart',
				data: {
					productId: productId,
					quantityInCart: quantityInCart
				},
				success: function(response) {
					if (response == 'fail') {
						Swal.fire({
							icon: 'warning',
							title: 'Something wrong !',
							showConfirmButton: true
						});
					} else if (response == 'success') {
						Swal.fire({
							icon: 'success',
							title: 'Thêm vào giỏ hàng thành công !',
							showConfirmButton: false,
							timer: 1500
						});
						$('.nothing').remove();
						if ($('.bottomCart').find('.total').length > 0) {
							// Có ít nhất một phần tử con bên trong .bottomCart
						} else {
							// Không có phần tử con bên trong .bottomCart
							$('.cartBottom').append('<div class="bottomCart"><div class="total">' +
								'<span> Tổng tiền</span> <span class="total-amount"> <fmt: formatNumber' +
								'type="number" pattern="###,###,###"' +
								'value="${totalPriceInCart}" /> ₫' +
								'</span></div>' +
								'<a href="/checkout" class="btn animate">Thanh toán</a></div>')
						}


						updateCartInfo(productId, quantityInCart, response, false);
					} else {
						Swal.fire({
							icon: 'success',
							title: 'Thêm vào giỏ hàng thành công !',
							showConfirmButton: false,
							timer: 1500
						});
						updateCartInfo(productId, quantityInCart, response, true);
					}
				},
				error: function(xhr, status, error) {
					Swal.fire({
						icon: 'error',
						title: 'Thêm vào giỏ hàng thất bại',
						text: "Có lỗi xảy ra, vui lòng thử lại !",
						showConfirmButton: false,
						timer: 1500
					});
				}
			});

		}
	});




	function updateCartInfo(productId, quantityInCart, cartID, check) {
		console.log(productId + " - " + quantityInCart);
		$.ajax({
			type: "POST",
			url: "/cart/get-cart-info", // Đặt đường dẫn tới servlet để lấy thông tin giỏ hàng ở đây
			data: {
				product_id: productId,
			},
			success: function(response) {
				var json = JSON.parse(response);
				console.log(cartID)
				console.log($('#' + cartID + '').find('.qtyInStock'));


				if (check) {
					let qty = $('.qty').val();
					console.log(qty);
					var qtyInStockElement = $('#' + cartID).find('.qtyInStock');
					var currentQty = parseInt(qtyInStockElement.text());
					var newQty = currentQty + parseInt(qty);
					qtyInStockElement.text(newQty);
				} else {
					var qtyInStockElement = $('#' + cartID).find('.qtyInStock');
					var currentQty = parseInt(qtyInStockElement.text());
					var newQty = currentQty + 1;
					qtyInStockElement.text(newQty);
				}

				let cartId = json.cartId;
				let cartCount = json.cartCount;
				let productName = json.productName;
				let originalPrice = json.originalPrice;
				let discountedPrice = json.discountedPrice;
				let productId = json.productId;

				let image = json.image;
				let totalCart = json.totalPrice;
				let quantityInStock = json.quantityInStock;
				$('.total-count').html(cartCount);
				$('.total-amount').html(formatCurrency(totalCart));
				let quantity = 0;
				if (typeof quantityInCart === 'undefined') {
					quantity = 1;
				} else {
					quantity = quantityInCart;
				}

				if (!check) {
					// Tạo chuỗi HTML đại diện cho khối sản phẩm
					var productHtml = '<li><div class="mb-2 productInCart" id="' + cartId + '">';
					productHtml += '<a class="cart-img" href="/products/' + productId + '"><img src="../../images/products/' + image + '" alt="' + image + '"></a>';
					productHtml += '<h4><a href="#">' + productName + '</a></h4>';
					productHtml += '<p class="quantity">Số lượng: <span class="qtyInStock">' + quantity + '</span>';
					productHtml += '<span class="amount d-flex flex-column">';

					// Định dạng giá tiền
					if (originalPrice > discountedPrice) {
						productHtml += '<del><span class="text-muted text-decoration-line-through me-2">' + formatCurrency(originalPrice) + ' ₫</span></del>';
						productHtml += '<span class="fw-bold float-end text-danger">' + formatCurrency(discountedPrice) + ' ₫</span>';
					} else {
						productHtml += '<span class="fw-bold float-end text-danger">' + formatCurrency(originalPrice) + ' ₫</span>';
					}

					productHtml += '</span>';
					productHtml += '<button onclick="removeCartItem(' + cartId + ', this)" class="remove remove-product-cart" title="Xóa khỏi giỏ hàng"><i class="fa fa-remove"></i></button>';
					productHtml += '</p>';
					productHtml += '</div></li>';

					// Thêm chuỗi HTML vào danh sách sản phẩm trong giỏ hàng
					$('#listProductCart').append(productHtml);
					console.log(originalPrice + ' - ' + discountedPrice)
				}


			},
			error: function(error) {
				console.log("Đã xảy ra lỗi khi thêm vào giỏ hàng: " + error);
			}
		});
	}



});
function formatCurrency(number) {
	return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


function changeQuantityProduct(cartId, productId, quantity, quantityInStock, price) {
	console.log(cartId + " - " + productId + " - " + quantity + " - " + quantityInStock + " - " + price)

	if (parseInt(quantity) <= 0) {
		Swal.fire({
			icon: 'error',
			title: 'Opps....',
			text: "Vui lòng nhập số lượng lớn hơn 0 !",
			showConfirmButton: true,
		});
		$(".input-quantity").val(1);
		return;
	} else if (parseInt(quantity) > parseInt(quantityInStock)) {
		Swal.fire({
			icon: 'error',
			title: 'Opps....',
			text: "Số lượng vượt quá số lượng tồn kho !",
			showConfirmButton: true,
		});
		$(".input-quantity").val(1);
		return;
	} else {
		console.log(1)
		$.ajax({
			url: "/cart/update-cart",
			type: "POST",
			data: {
				cartId: cartId,
				productId: productId,
				quantity: quantity
			},
			success: function(resp) {
				if (resp == 'fail') {
					Swal.fire({
						icon: 'error',
						title: 'Opps....',
						text: "Có lỗi xảy ra khi thay đổi số lượng !",
						showConfirmButton: true,
					});
				} else {
					var json = JSON.parse(resp);
					console.log(json.cartCount);
					console.log(json.totalPrice);

					let cartCount = json.cartCount;
					let totalPrice = json.totalPrice;
					let totalAmount = parseFloat(quantity) * parseFloat(price);
					console.log(ship)
					console.log(totalAmount)
					$('.total-amount' + cartId).html(formatCurrency(totalAmount) + " ₫")
					$('#totalCart').html(formatCurrency(totalPrice) + " ₫");
					if (totalPrice > 3000000) {
						$('#totalPriceAmount').html(formatCurrency(totalPrice) + " ₫")
					} else {
						$('#totalPriceAmount').html(formatCurrency(totalPrice + 50000) + " ₫")
					}
				}
			},
			error: function(error) {
				alert("Something error ! " + error);
			}
		})
	}
}


function removeCartItem(cartId, buttonElement) {
	console.log(cartId)
	Swal.fire({
		text: "Bạn có muốn xóa sản phẩm đã chọn khỏi giỏ hàng ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Trở lại',
		confirmButtonText: 'Có'
	}).then((result) => {
		if (result.isConfirmed) {
			let method = 'POST';

			$.ajax({
				url: '/cart/remove/' + cartId,
				type: method,
				success: function(response) {
					console.log(method)
					var jsonRemove = JSON.parse(response);
					console.log(jsonRemove)
					if (response == 'fail') {
						Swal.fire({
							icon: 'warning',
							title: 'Có lỗi xảy ra, vui lòng thử lại !',
							showConfirmButton: true
						});
					} else {
						let cartCount = jsonRemove.cartCount;
						let totalPrice = jsonRemove.totalPrice;
						console.log(totalPrice)
						$('.total-count').html(cartCount);
						$('.total-amount').html(formatCurrency(totalPrice));
						let cartItem = document.getElementById(cartId).closest('li');
						cartItem.remove();
						if (cartCount == 0) {
							$('.bottomCart').remove();
							$('#listProductCart').append('<div' +
								' class="nothing d-flex flex-column align-items-center justify-content-center">' +
								'<i class="bi bi-cart-x display-1"></i> <span>Chưa' +
								'có sản phẩm</span>' +
								'</div>');
						}
						console.log('del ok')
						Swal.fire(
							'Deleted!',
							'Xóa thành công !',
							'success'
						)
					}
				},
				error: function(xhr, status, error) {
					Swal.fire({
						icon: 'error',
						title: 'Thêm vào giỏ hàng thất bại',
						text: "Có lỗi xảy ra, vui lòng thử lại !",
						showConfirmButton: false,
						timer: 1500
					});
				}
			});

		}
	})

}


function removeCartItem2(cartId, buttonElement) {
	console.log(cartId)
	Swal.fire({
		text: "Bạn có muốn xóa sản phẩm đã chọn khỏi giỏ hàng ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Trở lại',
		confirmButtonText: 'Có'
	}).then((result) => {
		if (result.isConfirmed) {
			let method = 'POST';

			$.ajax({
				url: 'cart/remove/' + cartId,
				type: method,
				success: function(response) {
					console.log(method)
					var jsonRemove = JSON.parse(response);
					console.log(jsonRemove)
					if (response == 'fail') {
						Swal.fire({
							icon: 'warning',
							title: 'Có lỗi xảy ra, vui lòng thử lại !',
							showConfirmButton: true
						});
					} else {
						let cartCount = jsonRemove.cartCount;
						let totalPrice = jsonRemove.totalPrice;
						console.log(totalPrice)
						$('.total-count').html(cartCount);
						$('#totalCart').html(formatCurrency(totalPrice) + " ₫");
						let cartItem = document.getElementById(cartId)
						console.log(cartItem)
						cartItem.remove();
						if (cartCount == 0) {
							$('.right').remove();
						}
						console.log('del ok')
						Swal.fire(
							'Deleted!',
							'Xóa thành công !',
							'success'
						)
					}
				},
				error: function(xhr, status, error) {
					Swal.fire({
						icon: 'error',
						title: 'Thêm vào giỏ hàng thất bại',
						text: "Có lỗi xảy ra, vui lòng thử lại !",
						showConfirmButton: false,
						timer: 1500
					});
				}
			});

		}
	})

}