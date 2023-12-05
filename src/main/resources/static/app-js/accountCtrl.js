app.controller("accountCtrl", function ($scope, $location) {
  var currentURL = $location.path();
  console.log(currentURL);

  if (currentURL.includes("profile")) {
    $scope.views = "profile";
  } else if (currentURL.includes("address")) {
    $scope.views = "address";
  } else if (currentURL.includes("change-password")) {
    $scope.views = "changePass";
  } else if (currentURL.includes("purchase-order")) {
    $scope.views = "order";
  }

  var allCollapses = document.querySelectorAll(".collapse");
  allCollapses.forEach(function (collapse) {
    if (collapse !== collapseParent) {
      collapse.classList.remove("show");
    }
  });
  var links = document.querySelectorAll(".item-custom");
  links.forEach(function (link) {
    if (link.getAttribute("href") === currentURL) {
      link.classList.add("focus-item");
    }
  });
  var focusItem = document.querySelector(".focus-item");

  if (focusItem) {
    var collapseParent = focusItem.closest(".collapse");

    if (collapseParent) {
      collapseParent.classList.add("show");
    }
  }
});
