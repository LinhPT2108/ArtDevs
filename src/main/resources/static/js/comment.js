
$(document).ready(function() {
	console.log('this is comment js');

	$('#btnCheck').click(function() {
		var form2 = $('.form-reviews')[0];
		var data2 = new FormData(form2);
		console.log(data2)
		let productId = $(this).data('product-id');
		let invoiceDetailId = $(this).data('invoice-detail-id');
		let commentId = $(this).data('comment-id');
		data2.append("productId", JSON.stringify(productId));
		data2.append("invoiceDetailId", JSON.stringify(invoiceDetailId));
		if (commentId === undefined) {
			commentId = -1;
		}
		console.log(commentId)
		data2.append("commentId", JSON.stringify(commentId));
		for (var pair of data2.entries()) {
			console.log(pair[0] + ': ' + pair[1]);
		}

		$.ajax({
			type: 'POST',
			enctype: 'multipart/form-data',
			upload: true,
			url: '/comment/add-comment',
			processData: false,
			contentType: false,
			cache: false,
			data: data2,
			success: function(data) {
				if (data == 'success') {
					console.log(data2);
					let timerInterval
					Swal.fire({
						title: 'Đánh giá thành công!',
						html: 'sẽ làm mới trang trong <b></b> mili giây.',
						timer: 1500,
						allowOutsideClick: false,
						timerProgressBar: true,
						didOpen: () => {
							Swal.showLoading()
							const b = Swal.getHtmlContainer().querySelector('b')
							timerInterval = setInterval(() => {
								b.textContent = Swal.getTimerLeft()
							}, 100)
						},
						willClose: () => {
							clearInterval(timerInterval)
						}
					}).then((result) => {
						if (result.dismiss === Swal.DismissReason.timer) {
							console.log('I was closed by the timer')
							location.reload();
						}
					})
				} else {
					Swal.fire({
						icon: 'error',
						title: 'Thêm đánh giá thất bại',
						text: "Vui lòng kiểm tra lại thông tin !",
						showConfirmButton: true,
						timer: 1500
					});
					console.log(data)
				}
			},
			error: function(xhr, status, error) {
				console.log('Ajax errors');
			}
		});

	})

	$('.showComment').click(function() {
		let productId = $(this).data('product-id');
		let commentId = $(this).data('comment-id');
		console.log(commentId)

		let invoiceDetailId = $(this).data('invoice-detail-id');
		// Chọn thẻ có ID "btnCheck" và đặt giá trị thuộc tính "data-product-id"
		$("#btnCheck").attr("data-product-id", productId);
		$("#btnCheck").attr("data-invoice-detail-id", invoiceDetailId);
		if (commentId !== undefined) {
			console.log(123123)
			$("#btnCheck").attr("data-comment-id", commentId);
		} else {
			$("#btnCheck").attr("data-comment-id", null);
		}

		$.ajax({
			type: 'post',
			url: '/comment/get-comment',
			data: {
				productId: productId,
				invoiceDetailId: invoiceDetailId,
				commentId: commentId
			},
			success: function(data) {
				console.log(data);
				if (data != '404') {
					$('#content').text(data.content);
					$('.dateComment').text(formatDateToDDMMYYYY(data.date));
					$('#date').val(data.date);
					// Cập nhật đánh giá sao
					if (data.star) {
						// Chọn input đánh giá sao dựa trên giá trị star
						$('input[name="rate"]').prop('checked', false);
						var statusTexts = ["Tệ", "Không hài lòng", "Bình thường", "Hài lòng", "Tuyệt vời"];
						$('input[name="rate"][value="' + data.star + '"]').prop('checked', true);
						console.log(statusTexts[data.star - 1])
						var statusText = statusTexts[data.star - 1];
						$('.status-ratting').text(statusText);
						$('#star').val(data.star);
					} else {
						// Nếu không có giá trị star, thì bỏ chọn tất cả các input
						$('input[name="rate"]').prop('checked', false);
						$('.status-ratting').text("");
					}
					let listImages = [];
					data.commentImages.forEach(function(item, index) {
						listImages.push(item);
					});

					var imageList = $("#listImg"); // Chọn phần tử danh sách hình ảnh
					imageList.html('');
					let imageArray = data.commentImages;
					if (imageArray.length > 0) {
						imageList.html('');

						// Sử dụng một biến để duyệt qua các phần tử mảng
						for (var i = 0; i < imageArray.length; i++) {
							(function(index) {
								// Tạo phần tử li chứa hình ảnh
								var listItem = $("<li>").css({ maxWidth: "100px", display: "inline-block", margin: "5px" });

								// Tạo phần tử hình ảnh và thêm vào phần tử li
								var image = $("<img>").attr("src", "/images/comments/" + imageArray[index].image);
								listItem.append(image)
								// Thêm phần tử li vào danh sách
								imageList.append(listItem);

							})(i);

						}
					}
				} else {
					$('.date-comment').html('');
					$('#listImg').html('');
					$('#star4').attr("checked", true);
					$('#content').val('');
					console.log('new')
				}
			},
			error: function(xhr, status, error) {
				Swal.fire({
					icon: 'error',
					title: 'thất bại',
					text: "Có lỗi xảy ra, vui lòng thử lại !",
					showConfirmButton: false,
					timer: 1500
				});
				console.log(error);
			}
		});
	});

	// Sự kiện khi một sao được chọn
	$('.rating input').change(function() {
		var selectedValue = $(this).val();
		var statusTexts = ["Tệ", "Không hài lòng", "Bình thường", "Hài lòng", "Tuyệt vời"];
		$('.status-ratting').text(statusTexts[selectedValue - 1]);
		$('#star').val(selectedValue);
	});

	const fileInput = $("#listImage");
	const previewImages = $("#listImg");

	fileInput.on("change", function() {
		// Xóa tất cả các thẻ con
		previewImages.empty();

		for (let i = 0; i < fileInput[0].files.length; i++) {
			const file = fileInput[0].files[i];
			const imageContainer = $("<div>").addClass("image-container").css({
				display: "inline-block",
				margin: "5px"
			});
			const image = $("<img>").css({
				maxWidth: "127px",
				maxHeight: "100px"
			}).attr("src", URL.createObjectURL(file));

			imageContainer.append(image);
			previewImages.append(imageContainer);
		}
		console.log(fileInput[0].files)
	});


});
function generateRandomString() {
	var randomString = '';
	var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	var charactersLength = characters.length;
	for (var i = 0; i < 8; i++) {
		randomString += characters.charAt(Math.floor(Math.random() * charactersLength));
	}

	var currentTimeMillis = new Date().getTime();

	var finalString = randomString + currentTimeMillis.toString();

	return finalString;
}

function checkFileSize(e) {
	var file_list = e.files;
	var totalSize = 0; // Tổng kích thước của tất cả các tệp

	for (var i = 0; i < file_list.length; i++) {
		var file = file_list[i];
		var fileExtension = file.name.split('.')[file.name.split('.').length - 1].toLowerCase();
		var iConvert;

		if (file.size > (1024 * 1024)) {
			iConvert = (file.size / (1024 * 1024)).toFixed(2); // Chuyển đổi thành MB
		} else {
			iConvert = (file.size / 1024).toFixed(2); // Chuyển đổi thành KB
		}

		totalSize += file.size;

		txt = "File type: " + fileExtension + "\n";
		txt += "Size: " + iConvert + (file.size > (1024 * 1024) ? " MB" : " KB") + "\n";
	}

	if (totalSize > 10 * 1024 * 1024) {
		console.log('Vượt quá kích thước');
		Swal.fire({
			icon: 'warning',
			title: 'Vượt quá kích thước',
			text: "Vui lòng chọn ảnh kích thước lớn nhỏ hơn 10MB !",
			showConfirmButton: true
		});
		e.value = null;
	}
}


// Hàm định dạng ngày
function formatDateToDDMMYYYY(inputDate) {
	if (!(inputDate instanceof Date)) {
		inputDate = new Date(inputDate);
	}
	var day = inputDate.getDate();
	var month = inputDate.getMonth() + 1;
	var year = inputDate.getFullYear();
	day = day < 10 ? '0' + day : day;
	month = month < 10 ? '0' + month : month;
	var formattedDate = day + '-' + month + '-' + year;
	return formattedDate;
}

