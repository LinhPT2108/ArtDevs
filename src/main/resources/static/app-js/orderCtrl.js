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

    $timeout(function () {
      $scope.rating = {
        star: 5,
        content: "",
        date: null,
        userComment: $rootScope.userLogin.accountId,
        orderDetailId: "",
        productId: "",
        commentImages: [],
      };
    }, 500);
    $scope.showComment = function (productId, orderDetailId) {
      console.log(productId + " - " + orderDetailId);
      $scope.rating = {
        star: 5,
        content: "",
        date: "",
        userComment: $rootScope.userLogin.accountId,
        orderDetailId: orderDetailId,
        productId: productId,
        commentImages: [],
      };
      $("#gallery-photo-add").val(null);
      $(".gallery").html("");
      ApiService.callApi(
        "GET",
        "/rest/comment/" + productId + "/" + orderDetailId
      )
        .then(function (response) {
          console.log(response);
          if (response == "") {
            $scope.rating.star = 5;
            $("#star5").trigger({ type: "click" });
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

    $scope.addComment = function () {
      console.log($scope.rating);
      var imageInput = document.getElementById("gallery-photo-add");
      console.log(imageInput.files);

      ApiService.callApi("POST", "/rest/add-comment", {
        comment: $scope.rating,
      })
        .then(function (response) {
          console.log(response);
        })
        .catch(function (err) {
          console.log(err);
        });
    };
  }
);
