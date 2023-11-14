$(document).ready(function() {
	console.log('this is checkout js')
	$('#postCheckout').click(function(e) {
		e.preventDefault()
		var totalPriceSpan = $("#totalPriceAmount");
		var totalPriceText = totalPriceSpan.text();
		var totalPriceWithoutSymbols = totalPriceText.replace(/[^0-9]/g, '');
		var totalPriceAsInt = parseInt(totalPriceWithoutSymbols, 10);
		console.log(totalPriceAsInt);

		var niceSelectDiv = $(".nice-select.custom-select");
		var selectedValue = niceSelectDiv.find(".current").text();
		console.log(selectedValue.trim());
		let randomUri = generateRandomString();
		$.ajax({
			type: 'POST',
			url: '/checkout',
			data: {
				totalAmount: totalPriceAsInt,
				note: selectedValue.trim()
			},
			success: function(response) {
				console.log(response)
				if (response == 'success') {
					Swal.fire({
						icon: 'success',
						title: 'Đặt hàng thành công !',
						text: 'Ấn OK để tiếp tục !',
						confirmButtonText: 'OK',
						backdrop: false
					}).then((result) => {
						if (result.isConfirmed) {
							window.location.href = '/account/purchased-order/1';
						}
					});
				}else{
					$('#addressError').html(response.address)
				}
			},
			error: function(xhr, status, error) {
				console.log(xhr)
				Swal.fire({
					icon: 'error',
					title: 'Opps... !',
					text: "Có lỗi xảy ra, vui lòng thử lại !",
					showConfirmButton: true,
					timer: 1500
				});
			}
		});
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

var randomString = generateRandomString();