$(document).ready(function() {
	console.log('this is change pass11')
	var mainVeriCode;
	var mainEmail;
	$('#button-sendcode').click(function() {
		let email = $('#email').val();
		if (email == '') {
			$('#emailError').removeClass("d-none");
			$('#emailError').html("Vui lòng nhập email.");
		} else {
			if (!isValidEmail(email)) {
				$('#emailError').removeClass("d-none");
				$('#emailError').html("Email không hợp lệ.");
			} else {
				$("#button-sendcode").prop("disabled", true);
				$("#loading").removeClass("d-none");
				$('#emailError').addClass("d-none")
				$.ajax({
					type: 'POST',
					url: '/account/send-code',
					data: {
						email: email
					},
					success: function(response) {
						$("#loading").addClass("d-none");
						mainVeriCode = response;
						console.log(response)
						if (response != "404") {
							mainEmail = email;
							Swal.fire({
								icon: 'success',
								title: 'Mã xác nhận đã được gửi !',
								text: "Hãy kiểm tra Email để nhập mã xác nhận !",
								showConfirmButton: true,
								timer: 1500
							});
							$('#site-email').remove();
							$('.titlePass').html("Thay đổi mật khẩu");
							$("#site-change").removeClass("d-none");
						} else {
							$("#loading").addClass("d-none");
							Swal.fire({
								icon: 'error',
								title: 'Email không tồn tại !',
								text: "Email bạn vừa nhập chưa được đăng ký !",
								showConfirmButton: true,
								timer: 1500
							});
							$("#button-sendcode").prop("disabled", false);

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
						$("#loading").addClass("d-none");
						$("#button-sendcode").prop("disabled", false);
					}
				});
			}
		}
	});
	function isValidEmail(email) {
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailPattern.test(email);
	}
	function isValidVerificationCode(code) {
		return /^[0-9]{6}$/.test(code);
	}
	$('#forgotPasswordForm').submit(function(e) {
		e.preventDefault();
		let veriCode = $('#veriCode').val();
		let newPasswordInput = $('#newPassword').val();
		let confirmPasswordInput = $('#confirmPassword').val();
		let isvalid = false;
		if (!isValidVerificationCode(veriCode)) {
			$('#veriCodeError').removeClass("d-none");
			$('#veriCodeError').html("Mã xác nhận không hợp lệ.");
			isvalid = true;
		} else if (veriCode != mainVeriCode) {
			$('#veriCodeError').removeClass("d-none");
			$('#veriCodeError').html("Mã xác nhận không chính xác.");
			isvalid = true;
		} else {
			$('#veriCodeError').addClass("d-none");
		}
		const pattern = /^(?=.*[a-zA-Z]).{6,}$/;
		if (newPasswordInput == '') {
			$('#passError').removeClass("d-none");
			$('#passError').html("Vui lòng nhập mật khẩu mới.");
			isvalid = true;
		} else if (!pattern.test(newPasswordInput)) {
			$('#passError').removeClass("d-none");
			$('#passError').html("Mật khẩu phải chứa ít nhất một chữ cái");
			isvalid = true;
		} else {
			$('#passError').addClass("d-none");
		}

		if (confirmPasswordInput != newPasswordInput) {
			$('#confirmPassErorr').removeClass("d-none");
			$('#confirmPassErorr').html("Xác nhận mật khẩu không chính xác.");
			isvalid = true;
		} else {
			$('#confirmPassErorr').addClass("d-none");
		}

		if (isvalid) {
			console.log(false)
		} else {
			$('#veriCodeError').addClass("d-none");
			$('#passError').addClass("d-none");
			$('#confirmPassErorr').addClass("d-none");
			let email = $('#email').val();
			let newPass = $('#newPassword').val();
			console.log('email: ' + email);
			console.log('new pas: ' + newPass)
			$.ajax({
				type: 'POST',
				url: '/account/forgot-password',
				data: {
					email: mainEmail,
					newPass: newPass
				}, success: function(response) {
					console.log(response)
					if (response == 'success') {

						let timerInterval
						Swal.fire({
							icon: 'success',
							title: 'Thay đổi mật khẩu thành công !',
							html: 'Bạn sẽ đi đến trang đăng nhập trong <b></b> mili giây.',
							timer: 2000,
							timerProgressBar: true,
							backdrop: false,
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
								window.location.href = "/account/sign-in";
							}
						})

					} else {
						Swal.fire({
							icon: 'error',
							title: 'Opps... !',
							text: "Có lỗi xảy ra, vui lòng thử lại !",
							showConfirmButton: true,
							timer: 1500
						});
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
		};
	});
});
