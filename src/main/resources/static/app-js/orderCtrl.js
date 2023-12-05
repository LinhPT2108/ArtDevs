app.controller("orderCtrl", function ($scope, $rootScope, ApiService) {
  console.log("orderCtrl");
  var typeOrder = $location.search().type;
  console.log(typeOrder);
});
