app.controller(
  "orderCtrl",
  function ($scope, $rootScope, ApiService, $location, $timeout, $http) {
    console.log("orderCtrl");
    $scope.currentPage = 1;
    $scope.pageSize = 5;
    var typeOrder = $location.path();
    console.log($rootScope.userLogin);
    $scope.type = typeOrder.substr(typeOrder.lastIndexOf("/") + 1);
    $scope.GetListOrder = function () {
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
          if ($scope.type == "all") {
            $scope.listOrderByType = $scope.listOrder;
          } else {
            $scope.listOrderByType = filteredOrders;
          }
          console.log(filteredOrders);
        })
        .catch(function (err) {
          console.log(err);
        });
    };
    $timeout(function () {
      $scope.GetListOrder();
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
    }, 800);
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
      $(".gallery-img").html("");
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
            $scope.rating.productId = productId;
            $scope.rating.orderDetailId = orderDetailId;
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
      var imageInput = document.getElementById("gallery-photo-add");
      console.log(imageInput.files);
      $scope.rating.commentImages = imageInput.files;
      console.log($scope.rating);

      const formData = new FormData();

      formData.append("ad", JSON.stringify($scope.rating));

      const request = $http({
        method: "POST",
        url: "/rest/add-comment",
        headers: {
          transformRequest: angular.identity,
          "Content-Type": "application/json",
        },
        data: JSON.stringify({
          star: $scope.rating.star,
          content: $scope.rating.content,
          date: $scope.rating.date,
          userComment: $rootScope.userLogin.accountId,
          orderDetailId: $scope.rating.orderDetailId,
          productId: $scope.rating.productId,
        }),
      });
      request
        .then((response) => {
          console.log(response.data);
          $scope.rating.date = new Date();
          var imageInput2 = document.getElementById("gallery-photo-add");
          if (imageInput2.files.length > 0) {
            const formData2 = new FormData();
            for (const image of imageInput2.files) {
              formData2.append("imgsComment", image);
            }

            const request = $http({
              method: "POST",
              url:
                "/rest/get-image-comment/" +
                $rootScope.userLogin.accountId +
                "/" +
                $scope.rating.orderDetailId,
              headers: {
                transformRequest: angular.identity,
                "Content-Type": undefined,
              },
              data: formData2,
            });
            request
              .then((response) => {
                console.log(response.data);
                $scope.GetListOrder();
                $(".show-img").html("");
              })
              .catch((error) => {
                console.log(error);
              });
          }

          Swal.fire({
            title: "Đánh giá thành công",
            text: "Cảm ơn bạn đã đánh giá đơn hàng !",
            icon: "success",
          });
        })
        .catch((error) => {
          console.log(error);
        });
    };
  }
);
