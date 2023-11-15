
$(document).ready(
	function() {

		/*
		$.ajax({
			url: '/admin/statistical-revenue/yearly-revenue',
			method: 'GET',
			success: function(response) {
				var data = {
					labels: response.labels,
					values: response.values
				}
				var ctx = document.getElementById('revenueYearChart')
					.getContext('2d');
				drawChart(data, ctx); // Vẽ biểu đồ với dữ liệu
			},

			error: function(error) {
				console.log(error);
			}
		});*/

		// Hàm vẽ biểu đồ



		$(function() {
			var dataDay;
			$.ajax({
				url: '/admin/statistical-revenue/daily-revenue',
				method: 'GET',
				success: function(response) {
					dataDay = {
						labels: response.labels,
						values: response.values
					}
					console.log(Math.max(...dataDay.values))
					var chart = {
						series: [
							{ name: "Earnings this month:", data: dataDay.values },/*
					{ name: "Expense this month:", data: [280, 250, 325, 215, 250, 310, 280, 250] },*/
						],

						chart: {
							type: "bar",
							height: 345,
							offsetX: -15,
							toolbar: { show: true },
							foreColor: "#adb0bb",
							fontFamily: 'inherit',
							sparkline: { enabled: false },
						},

						colors: ["#5D87FF", "#49BEFF"],

						plotOptions: {
							bar: {
								horizontal: false,
								columnWidth: "35%",
								borderRadius: [6],
								borderRadiusApplication: 'end',
								borderRadiusWhenStacked: 'all'
							},
						},
						markers: { size: 0 },

						dataLabels: {
							enabled: false,
						},
						legend: {
							show: false,
						},
						grid: {
							borderColor: "rgba(0,0,0,0.1)",
							strokeDashArray: 3,
							xaxis: {
								lines: {
									show: false,
								},
							},
						},
						xaxis: {
							type: "category",
							categories: dataDay.labels,
							labels: {
								style: { cssClass: "grey--text lighten-2--text fill-color" },
							},
						},
						yaxis: {
							show: true,
							min: 0,
							max: Math.max(...dataDay.values) + 50000,
							tickAmount: 8,
							labels: {
								style: {
									cssClass: "grey--text lighten-2--text fill-color",
								},
							},
						},
						stroke: {
							show: true,
							width: 3,
							lineCap: "butt",
							colors: ["transparent"],
						},


						tooltip: { theme: "light" },

						responsive: [
							{
								breakpoint: 600,
								options: {
									plotOptions: {
										bar: {
											borderRadius: 3,
										}
									},
								}
							}
						]
					};
					var chart = new ApexCharts(document.querySelector("#chart"), chart);
					chart.render();
				},

				error: function(error) {
					console.log(error);
				}
			});

			// =====================================
			// Breakup
			// =====================================
			$.ajax({
				url: '/admin/statistical-revenue/yearly-revenue',
				method: 'GET',
				success: function(response) {
					var dataYear = {
						labels: response.labels,
						values: response.values
					}
					var breakup = {
						color: "#adb5bd",
						series: dataYear.values,
						labels: dataYear.labels,
						chart: {
							width: 180,
							type: "donut",
							fontFamily: "Plus Jakarta Sans', sans-serif",
							foreColor: "#adb0bb",
						},
						plotOptions: {
							pie: {
								startAngle: 0,
								endAngle: 360,
								donut: {
									size: '75%',
								},
							},
						},
						stroke: {
							show: false,
						},

						dataLabels: {
							enabled: false,
						},

						legend: {
							show: false,
						},
						colors: ["#5D87FF", "#a4b4df", "#F9F9FD"],

						responsive: [
							{
								breakpoint: 991,
								options: {
									chart: {
										width: 150,
									},
								},
							},
						],
						tooltip: {
							theme: "dark",
							fillSeriesColor: false,
						},
					};

					var chart = new ApexCharts(document.querySelector("#breakup"), breakup);
					chart.render();

				},

				error: function(error) {
					console.log(error);
				}
			});





			// =====================================
			// Earning
			// =====================================
			$.ajax({
				url: '/admin/statistical-revenue/monthly-revenue',
				method: 'GET',
				success: function(response) {
					var dataMonth = {
						labels: response.labels,
						values: response.values
					}
					var earning = {
						chart: {
							id: "sparkline3",
							type: "area",
							height: 60,
							sparkline: {
								enabled: true,
							},
							group: "sparklines",
							fontFamily: "Plus Jakarta Sans', sans-serif",
							foreColor: "#adb0bb",
						},
						series: [
							{
								name: "Earnings",
								color: "#49BEFF",
								data: dataMonth.values,
							},
						],
						stroke: {
							curve: "smooth",
							width: 2,
						},
						fill: {
							colors: ["#f3feff"],
							type: "solid",
							opacity: 0.05,
						},

						markers: {
							size: 0,
						},
						tooltip: {
							theme: "dark",
							fixed: {
								enabled: true,
								position: "right",
							},
							x: {
								show: false,
							},
						},
					};
					new ApexCharts(document.querySelector("#earning"), earning).render();
				},

				error: function(error) {
					console.log(error);
				}
			});


		})
	});