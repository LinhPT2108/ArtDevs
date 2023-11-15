app.controller("accountCtrl", function ($scope, $location) {
  var currentURL = $location.path();
  console.log(currentURL);

  if (currentURL.includes("profile")) {
    $scope.views = "profile";
    console.log(true);
  } else if (currentURL.includes("address")) {
    $scope.views = "address";
    console.log(true);
  } else if (currentURL.includes("change-password")) {
    $scope.views = "changePass";
    console.log(true);
  }

  var allCollapses = document.querySelectorAll(".collapse");
  allCollapses.forEach(function (collapse) {
    if (collapse !== collapseParent) {
      collapse.classList.remove("show");
    }
  });
  var links = document.querySelectorAll(".item-custom");
  console.log(links);
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
