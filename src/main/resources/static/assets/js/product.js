
let counter = $('.description-container').length;
console.log(counter)
function themMoTa() {
	counter++;

	const newRow = document.createElement("div");
	newRow.className = "col-xs-12 col-md-6 mb-3 description-container";
	newRow.innerHTML = `
        <div class=" border border-1 p-3 position-relative description-product">
            <span class="position-absolute z-3 bg-white motaClass" style="top: -13px" data-counter-number="${counter}">Mô tả ${counter}:</span>
            <div class="mb-3">
                <div class="form-floating">
                    <input type="text" class="form-control" id="tieude${counter}" placeholder="">
                    <label for="tieude${counter}">Tiêu đề:</label>
                </div>
            </div>
            <div class="form-floating">
                <textarea class="form-control" placeholder="Leave a comment here" id="noidung${counter}" style="height: 100px;"></textarea>
                <label for="noidung${counter}">Nội dung:</label>
            </div>
            <button class="btn btn-danger" onclick="xoaMoTa(this)" style="
				    position: absolute;
				    top: -13px;
				    right: -12px;
				    padding: 2px 6px;
				    border-radius: 50%;
				">X</button>
				        </div>`;

	const addButton = document.querySelector(".button");
	addButton.parentElement.insertBefore(newRow, addButton);
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


function layDuLieuMoTa() {
	// Sử dụng jQuery để tìm thẻ có class "motaClass" cuối cùng và lấy giá trị "data-counter-number"
	var lastMotaClassElement = $('.motaClass').last();
	var counterNumber = lastMotaClassElement.data('counter-number');

	const moTaObjects = [];

	for (let index = 0; index <= counterNumber; index++) {
		const tieuDe = $(`#tieude${index}`).val() || '';
		const noiDung = $(`#noidung${index}`).val() || '';

		// Kiểm tra nếu cả hai ô đều trống thì không thêm vào moTaObjects
		if (tieuDe.trim() !== "" || noiDung.trim() !== "") {
			const moTa = {
				tieuDe: tieuDe,
				description: noiDung
			};

			moTaObjects.push(moTa);
		}
	}

	return moTaObjects;
}




function xoaMoTa(button) {
	button.parentElement.parentElement.remove();
	// Sử dụng jQuery để tìm thẻ có class "motaClass" cuối cùng
	var lastMotaClassElement = $('.motaClass').last();

	// Lấy giá trị từ thuộc tính "data-counter-number"
	var counterNumber = lastMotaClassElement.data('counter-number');
	counter = counterNumber;
	console.log(counter)

}


$(document).ready(function() {
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
				maxWidth: "100px",
				maxHeight: "100px"
			}).attr("src", URL.createObjectURL(file));

			imageContainer.append(image);
			previewImages.append(imageContainer);
		}
		console.log(fileInput[0].files)
	});
});


$(document).ready(function() {
	$('#formProduct').submit(function(event) {
		event.preventDefault();
		// Lấy dữ liệu từ form
		var formData = new FormData(this);

		var descriptions = layDuLieuMoTa();
		formData.append("descriptions", JSON.stringify(descriptions));

		console.log(formData);
		for (var pair of formData.entries()) {
			console.log(pair[0] + ': ' + pair[1]);
		}
		$.ajax({
			type: 'POST',
			enctype: 'multipart/form-data',
			upload: true,
			url: '/admin/product',
			processData: false,
			contentType: false,
			cache: false,
			data: formData,
			success: function(data) {
				if (data == 'success') {
					/*Swal
						.fire({
							icon: 'success',
							title: 'Thêm thành công',
							text: "Sản phẩm đã được thêm vào hệ thống !",
							showConfirmButton: true,
							timer: 1500
						});*/

					console.log(formData);
					let timerInterval
					Swal.fire({
						icon:'success',
						title: 'Dữ liệu đang được thêm vào hệ thông!',
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
						/* Read more about handling dismissals below */
						if (result.dismiss === Swal.DismissReason.timer) {
							console.log('I was closed by the timer')
							location.reload();
						}
					})
				} else {

					$("#productNameError").html('');
					$("#categoryProductError").html('');
					$("#manufacturerProductError").html('');
					$("#quantityInStockError").html('');
					$("#priceError").html('');
					$('#imageError').html('');
					$('#detailDecriptionError').html('');
					$("#productNameError").html(data.productName);
					$("#categoryProductError").html(data.categoryProduct);
					$("#manufacturerProductError").html(data.manufacturerProduct);
					$("#quantityInStockError").html(data.quantityInStock);
					$("#priceError").html(data.price);
					$('#imageError').html(data.image);
					$('#detailDecriptionError').html(data.detailDecription);
					Swal.fire({
						icon: 'error',
						title: 'Thêm sản phẩm thất bại',
						text: "Vui lòng kiểm tra lại thông tin !",
						showConfirmButton: true,
						timer: 1500
					});
					console.log(data)
				}
			},
			error: function(xhr, status, error) {
				console
					.log('Ajax errors');
			}
		});
	});

	$('#formProductEdit').submit(function(event) {
		event.preventDefault();
		// Lấy dữ liệu từ form
		var formData = new FormData(this);

		var descriptions = layDuLieuMoTa();
		formData.append("descriptions", JSON.stringify(descriptions));
		console.log(formData);
		for (var pair of formData.entries()) {
			console.log(pair[0] + ': ' + pair[1]);
		}
		$.ajax({
			type: 'POST',
			enctype: 'multipart/form-data',
			upload: true,
			url: '/admin/product/update-product',
			processData: false,
			contentType: false,
			cache: false,
			data: formData,
			success: function(data) {
				if (data == 'success') {
					
					let timerInterval
					Swal.fire({
						icon: 'success',
						title: 'Dữ liệu đang được cập nhật vào hệ thông!',
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
						/* Read more about handling dismissals below */
						if (result.dismiss === Swal.DismissReason.timer) {
							console.log('I was closed by the timer')
							location.reload();
						}
					})
				} else {

					$("#productNameError").html('');
					$("#categoryProductError").html('');
					$("#manufacturerProductError").html('');
					$("#quantityInStockError").html('');
					$("#priceError").html('');
					$('#imageError').html('');
					$('#detailDecriptionError').html('');
					$("#productNameError").html(data.productName);
					$("#categoryProductError").html(data.categoryProduct);
					$("#manufacturerProductError").html(data.manufacturerProduct);
					$("#quantityInStockError").html(data.quantityInStock);
					$("#priceError").html(data.price);
					$('#imageError').html(data.image);
					$('#detailDecriptionError').html(data.detailDecription);
					Swal.fire({
						icon: 'warning',
						title: 'Cập nhật sản phẩm thất bại',
						text: "Vui lòng kiểm tra lại thông tin !",
						showConfirmButton: true,
						timer: 1500
					});
					console.log(data)
				}
			},
			error: function(xhr, status, error) {
				console
					.log('Ajax errors');
			}
		});
	});


	$('.btnDelProduct').click(function(e) {
		e.preventDefault();
		console.log('del')
		Swal.fire({
			icon: 'info',
			title: 'Bạn muốn xóa sản phẩm đã chọn ?',
			text: 'Ấn OK để xóa !',
			confirmButtonText: 'OK',
			showCancelButton: true,
		}).then((result) => {
			if (result.isConfirmed) {
				var hrefValue = $(this).attr("href");
				$.ajax({
					type: 'POST',
					url: hrefValue,
					success: function(resp) {
						console.log(resp)
						if (resp == 'success') {
							Swal.fire({
								icon: 'success',
								title: 'Xóa thành công',
								text: "Sản phẩm đã được xóa !",
								showConfirmButton: true,
								timer: 1500
							});
							location.reload();
						} else if (resp == 'fail') {
							Swal
								.fire({
									icon: 'error',
									title: 'Xóa thất bại',
									text: "Có lỗi xảy ra, vui lòng thử lại !",
									showConfirmButton: false,
									timer: 1500
								});
						}
					},
					error: function(xhr,
						status, error) {
						Swal
							.fire({
								icon: 'error',
								title: 'Xoa thất bại',
								text: "Có lỗi xảy ra, vui lòng thử lại !",
								showConfirmButton: false,
								timer: 1500
							});
					}
				});
			}
		})
	})
});
