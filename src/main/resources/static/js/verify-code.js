$(document).ready(function() {
	console.log('this is register js')
	$('#formVerify').submit(function(e) {
		e.preventDefault();
		var form = $('#formVerify')[0];
		var data2 = new FormData(form);
		var dataId = $("input[name='verifyCode']").data("id");
		console.log(dataId)
		console.log(data2)
		$.ajax({
			type: "POST",
			url: '/account/verify-code/' +dataId,
			processData: false,
			contentType: false,
			cache: false,
			data: data2,
			success: function(response) {
				// Xóa thành công
				// Chuyển hướng hoặc làm bất kỳ điều gì bạn muốn
				if (response == 'success') {
					Swal.fire({
						icon: 'success',
						title: 'Xác thực thành công',
						text: "Bạn sẽ được chuyển sang trang đăng nhập!",
						showConfirmButton: false,
						allowOutsideClick: false,
						allowEscapeKey: false,
						timer: 1500,
						timerProgressBar: true,
						didClose: () => {
							window.location.href = "/account/login"; // Tải lại trang	
						}
					});

				} else if (response == 'fail') {
					Swal.fire({
						icon: 'error',
						title: 'Xác thực thất bại',
						text: "Có lỗi xảy ra!",
						showConfirmButton: true,
						timer: 1500
					});
				} else {
					console.log(response)

					$('#verifyError').html('');
					
					$('#verifyError').html(response.verifyCode);
				}
			},
			error: function(xhr, status, error) {
				// Xóa thất bại
				alert("Lỗi: " + error);
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