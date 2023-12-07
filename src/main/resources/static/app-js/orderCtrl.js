app.controller(
  "orderCtrl",
  function ($scope, $rootScope, ApiService, $location, $timeout) {
    console.log("orderCtrl");
    $scope.currentPage = 1;
    $scope.pageSize = 4;
    var typeOrder = $location.path();
    console.log($rootScope.userLogin);
    $scope.type = typeOrder.substr(typeOrder.lastIndexOf("/") + 1);

    $timeout(function () {
      var accountId = $rootScope.userLogin.accountId;
      console.log(accountId);
      console.log(typeOrder);
      ApiService.callApi("GET", "/rest" + typeOrder + "/" + accountId)
        .then(function (response) {
          $scope.listOrder = response;
          console.log($scope.listOrder);
          const filteredOrders = $scope.filterOrdersByDeliveryStatusId(
            $scope.listOrder,
            $scope.type
          );
          $scope.listOrderByType = filteredOrders;
          console.log(filteredOrders);
        })
        .catch(function (err) {
          console.log(err);
        });
    }, 1000);

    $scope.getLatestOrder = function (orderStatus) {
      return orderStatus.filter(
        (order) =>
          order.date ===
          orderStatus.reduce((prev, curr) =>
            curr.date > prev.date ? curr : prev
          ).date
      )[0];
    };

    $scope.filterByDeliveryStatusId = function (list, id) {
      return list.orderStatus.filter(function (order) {
        return order.deliveryStatus.id === id;
      });
    };

    $scope.filterOrdersByDeliveryStatusId = function (
      orders,
      deliveryStatusId
    ) {
      return orders.filter((order) => {
        return order.orderStatus.some(
          (status) =>
            status.deliveryStatus.id == deliveryStatusId &&
            status.status == true
        );
      });
    };

    $scope.showComment = function (productId, orderDetailId) {
      console.log(productId + " - " + orderDetailId);
      $scope.rating = {
        star: 5,
        content: "",
        date: null,
        userComment: $rootScope.userLogin.accountId,
        orderDetailId: "",
        productId: "",
        commentImages: [],
      };
      ApiService.callApi(
        "GET",
        "/rest/comment/" + productId + "/" + orderDetailId
      )
        .then(function (response) {
          console.log(response);
          if (response == "") {
            $scope.rating.star = 5;
            $("#star5").trigger({ type: "click" });
            console.log("click");
          } else {
            $scope.rating = response;
            console.log($scope.rating);
            var star = response.star;

            switch (star) {
              case 1:
                $("#star1").trigger({ type: "click" });
                break;
              case 2:
                $("#star2").trigger({ type: "click" });
                break;
              case 3:
                $("#star3").trigger({ type: "click" });
                break;
              case 4:
                $("#star4").trigger({ type: "click" });
                break;
              case 5:
                $("#star5").trigger({ type: "click" });
                break;
              default:
                break;
            }
          }
        })
        .catch(function (err) {
          console.log(err);
        });
    };

    $scope.cancelOrder = function (orderID, index) {
      console.log(orderID);
      console.log(index);

      Swal.fire({
        title: "Hủy hóa đơn",
        text: "Bạn chắc chắn muốn hủy hóa đơn này không?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Hủy hóa đơn!",
        cancelButtonText: "Trở về",
      }).then((result) => {
        if (result.isConfirmed) {
          ApiService.callApi("PUT", "/rest/order/cancel-order/" + orderID)
            .then(function (response) {
              console.log(response);
              if (response == 200) {
                Swal.fire({
                  title: "Hủy hóa đơn thành công!",
                  text: "Hóa đơn bạn chọn đã được hủy.",
                  icon: "success",
                });
                let index2 = $scope.listOrderByType.findIndex(
                  (el) => el.id == orderID
                );
                console.log(index2);
                $scope.listOrderByType.splice(index2, 1);
              }
            })
            .catch(function (err) {
              console.log(err);
            });
        }
      });
    };

    $scope.checkPic = function () {
      console.log($scope.rating);
    };

    $scope.checkFileSize = function (e) {
      var file_list = e.files;
      var totalSize = 0;

      for (var i = 0; i < file_list.length; i++) {
        var file = file_list[i];
        var fileExtension = file.name
          .split(".")
          [file.name.split(".").length - 1].toLowerCase();
        var iConvert;

        if (file.size > 1024 * 1024) {
          iConvert = (file.size / (1024 * 1024)).toFixed(2); // Chuyển đổi thành MB
        } else {
          iConvert = (file.size / 1024).toFixed(2); // Chuyển đổi thành KB
        }

        totalSize += file.size;

        txt = "File type: " + fileExtension + "\n";
        txt +=
          "Size: " +
          iConvert +
          (file.size > 1024 * 1024 ? " MB" : " KB") +
          "\n";
      }

      if (totalSize > 10 * 1024 * 1024) {
        console.log("Vượt quá kích thước");
        Swal.fire({
          icon: "warning",
          title: "Vượt quá kích thước",
          text: "Vui lòng chọn ảnh kích thước lớn nhỏ hơn 10MB !",
          showConfirmButton: true,
        });
        e.value = null;
      }
    };
  }
);
