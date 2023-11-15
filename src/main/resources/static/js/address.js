$(document).ready(function() {

	var cities = $("#city").selectize({
		valueField: "Id",
		labelField: "Name",
		searchField: "Name",
		options: [],
		placeholder: "Chọn tỉnh thành",
		maxItems: 1,
		onChange: function(value) {
			if (value) {
				var selectedCity = this.options[value];
				var districtSelectize = districts[0].selectize;
				districtSelectize.clearOptions();
				districtSelectize.addOption(selectedCity.Districts);
				districtSelectize.clear();
				districtSelectize.refreshItems();
				districtSelectize.open();
			} else {
				districts[0].selectize.clearOptions();
				districts[0].selectize.clear();
				wards[0].selectize.clearOptions();
				wards[0].selectize.clear();
			}
		},
	});

	var districts = $("#district").selectize({
		valueField: "Id",
		labelField: "Name",
		searchField: "Name",
		options: [],
		placeholder: "Chọn quận huyện",
		maxItems: 1,
		onChange: function(value) {
			if (value) {
				var selectedCity = cities[0].selectize.getValue();
				var selectedDistrict = this.options[value];
				var wardSelectize = wards[0].selectize;
				wardSelectize.clearOptions();
				wardSelectize.addOption(selectedDistrict.Wards);
				wardSelectize.clear();
				wardSelectize.refreshItems();
				wardSelectize.open();
			} else {
				wards[0].selectize.clearOptions();
				wards[0].selectize.clear();
			}
		},
	});

	var wards = $("#ward").selectize({
		valueField: "Id",
		labelField: "Name",
		searchField: "Name",
		options: [],
		placeholder: "Chọn phường xã",
		maxItems: 1,
	});

	axios
		.get("../../data/data.json")
		.then(function(response) {
			var data = response.data;
			cities[0].selectize.addOption(data);
		})
		.catch(function(error) {
			console.error("Error fetching data:", error);
		});

	$('.btnCreateAddress').click(function() {
		$('#btnCheck').attr('href', '/account/address/add');
		console.log($('#btnCheck').attr('href'));
		$('#phoneNumber').val('');
		$('#phoneNumber').removeAttr('readonly');
		$('#address').val('');
	});

	$('.btnUpdateAddress').click(function() {
		$('#btnCheck').attr('href', '/account/address/update');
	});
	$("#btnCheck").click(function(event) {
		event.preventDefault();
		var detail_address = $('textarea#detail_address').val();

		var modifiedAddress = detail_address.replace(/,/g, " -");
		var selectedCityValue = cities[0].selectize.getValue();
		var selectedDistrictValue = districts[0].selectize.getValue();
		var selectedWardValue = wards[0].selectize.getValue();
		console.log(selectedCityValue)
		if (selectedCityValue == '' || selectedDistrictValue == '' || selectedWardValue == '') {
			let hrefString = $('#btnCheck').attr('href');
			if (hrefString != '/account/address/add') {
				let addressParts2 = $('#address').val().split(', ');
				if (addressParts2.length >= 4) {
					var city2 = addressParts2[3].trim();
					var district2 = addressParts2[2].trim();
					var ward2 = addressParts2[1].trim();
					var detailAddress2 = modifiedAddress;
				} else if (addressParts2.length === 3) {
					var city2 = addressParts2[2].trim();
					var district2 = addressParts2[1].trim();
					var ward2 = addressParts2[0].trim();
					var detailAddress2 = "";
				}
				var selectedAddress = `${detailAddress2 == "" ? "" : detailAddress2.replace(/,/g, " -") + ","} ${ward2}, ${district2}, ${city2}`;
				console.log(selectedAddress);
				$('#address').val(selectedAddress);

			}
		} else {
			var selectedCityName = cities[0].selectize.options[selectedCityValue].Name;
			var selectedDistrictName =
				districts[0].selectize.options[selectedDistrictValue].Name;
			var selectedWardName = wards[0].selectize.options[selectedWardValue].Name;

			var selectedAddress = `${modifiedAddress == "" ? "" : modifiedAddress + ","} ${selectedWardName}, ${selectedDistrictName}, ${selectedCityName}`;
			console.log(selectedAddress);
			$('#address').val(selectedAddress);
			/*$('#modalAddress').modal('hide');*/
		}
		// Lấy dữ liệu từ form
		var form2 = $('#formAddress')[0];
		var data2 = new FormData(form2);
		var Url = $(this).attr("href");
		console.log(Url)
		$.ajax({
			type: 'POST',
			url: Url,
			data: data2,
			processData: false,
			contentType: false,
			cache: false,
			success: function(data) {
				if (data == 'success') {
					Swal.fire({
						icon: 'success',
						title: 'Thao tác thành công',
						text: "Thông tin đang cập nhật vào hệ thống!",
						showConfirmButton: false,
						allowOutsideClick: false,
						allowEscapeKey: false,
						timer: 1500,
						timerProgressBar: true,
						didClose: () => {
							location.reload();
						}
					});
					$("#phoneNumberError").html("");
					$("#addressError").html("");
					console.log(data);
				} else {
					$("#phoneNumberError").html(data.phoneNumber);
					$("#addressError").html(data.address);

					console.log(data)
				}
			},
			error: function(xhr, status, error) {
				console.log('Ajax errors');
			}
		});
	});
	$(".btnDeleteAddress").on("click", function(e) {
		e.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

		var deleteUrl = $(this).attr("href");

		// Gửi yêu cầu Xóa bằng Ajax
		$.ajax({
			url: deleteUrl,
			type: "GET",
			success: function(response) {
				// Xóa thành công
				// Chuyển hướng hoặc làm bất kỳ điều gì bạn muốn
				if (response == 'success') {
					window.location.href = "http://localhost:8080/account/address"; // Tải lại trang					
				} else {
					Swal.fire({
						icon: 'error',
						title: 'Xóa thất bại',
						text: "Có lỗi xảy ra!",
						showConfirmButton: true,
						timer: 1500
					});
				}
			},
			error: function(xhr, status, error) {
				// Xóa thất bại
				alert("Xóa không thành công: " + error);
			}
		});


	});
	$(".btnUpdateAddress").on("click", function(e) {
		e.preventDefault(); // Ngăn chặn hành động mặc định của liên kết
		console.log(1)
		var editUrl = $(this).attr("href");

		// Gửi yêu cầu Xóa bằng Ajax
		$.ajax({
			url: editUrl,
			type: "GET",
			success: function(response) {
				console.log(response)
				let fullAddress = response.address;
				let addressParts = fullAddress.split(', ');
				$('#phoneNumber').val(response.phoneNumber);
				$('#phoneNumber').attr('readonly', 'true');
				$('#address').val(response.address);
				console.log(addressParts);

				if (addressParts.length >= 4) {
					var city = addressParts[3].trim();
					var district = addressParts[2].trim();
					var ward = addressParts[1].trim();
					var detailAddress = addressParts[0].trim();
				} else if (addressParts.length === 3) {
					var city = addressParts[2].trim();
					var district = addressParts[1].trim();
					var ward = addressParts[0].trim();
					var detailAddress = "";
				}
				$('#city-selectized').val(city);
				$('#district-selectized').val(district);
				$('#ward-selectized').val(ward);
				// Đặt giá trị cho detail_address
				$('#detail_address').val(detailAddress);
			},
			error: function(xhr, status, error) {
				// Xóa thất bại
				alert("Lỗi: " + error);
			}
		});
	});
});
