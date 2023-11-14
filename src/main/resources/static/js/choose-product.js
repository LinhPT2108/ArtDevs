$(document).ready(function() {
	console.log('this is choose product1');
	// Sử dụng jQuery để tìm thẻ <a> với href="#1" và thêm lớp "active"
	$('a[href="#1"]').addClass('active');

	// Sử dụng jQuery để tìm tất cả các tab và thêm sự kiện click
	$('.nav-link.link-product-highlight').on('click', function() {
		// Lấy id của tab-pane tương ứng
		var tabId = $(this).attr('href').substring(1);
		// Xóa lớp "showw" và "active" khỏi tất cả các tab-pane
		$('.tab-pane').removeClass('show active');

		// Thêm lớp "showw" và "active" vào tab-pane tương ứng
		$('#' + tabId).addClass('show active');
	});

	$('.quickViews').click(function() {
		let productId = $(this).data('product-id');
		$('.addToCart').data('product-id', productId);
		console.log(productId)
		let sliderDiv = $(".quickview-slider-active");  // Chọn phần tử <div>
		// Gọi cuộc gọi Ajax để lấy dữ liệu sản phẩm
		$.ajax({
			type: 'get',
			url: '/quick-views/' + productId,
			success: function(data) {
				if (data == 'fail') {
					console.log(data);
				} else {
					$('#quick-title-product').html(data.productName);
					if (data.quantityInStock == 0) {
						$('#qtyStock').html('<i class="fa fa-times-circle text-danger"></i> Hết hàng');
					} else {
						$('#qtyStock').html('<i class="fa fa-check-circle-o"></i> Còn hàng');
					}
					let title = data.productDetailDescription[0].tile;
					let description = data.productDetailDescription[0].description;

					$('#quickview-description').html('<h6>' + title + '</h6>' + '<p>' + description + '</p>');

					// Lấy danh sách hình ảnh từ dữ liệu sản phẩm
					var images = data.productImage;

					// Chờ đợi tất cả hình ảnh được nạp xong
					appendImages(images);
					console.log(data)
					let originalPrice = formatCurrency(data.price);
					let priceDiscounted = data.productPromotionalDetails[0] != null ? formatCurrency(data.productPromotionalDetails[0].discountedPrice) : '';
					console.log(priceDiscounted)

					if (priceDiscounted == '') {
						$('#review-product-price').html('<h3 id="originalPrice" class="ml-3 text-danger" >'
							+ originalPrice + ' đ</h3 > ')
					} else {
						$('#review-product-price').html('<h3 id="originalPrice" class=""><del>' + originalPrice + ' đ</del></h3>' +
							'<h3 class="ml-3 text-danger" id="discountedPrice">' + priceDiscounted + 'đ</h3>')
					}
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
				console.log(error);
			}
		});

		function appendImages(images) {
			var sliderDiv = $(".carousel-inner");
			sliderDiv.empty();

			var activeImg = true;

			$.each(images, function(index, image) {
				var img = new Image();
				var itemClass = activeImg ? 'active' : '';
				sliderDiv.append(
					'<div class="carousel-item ' + itemClass + '">' +
					'<img class="d-block w-100" src="../images/products/' + image.image + '" alt="First slide">' +
					'</div>'
				);
				activeImg = false;
				img.src = '../images/products/' + image.image;
			});

		}
	});
	function formatCurrency(number) {
		return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
	}
});