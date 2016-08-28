/**
 * Created by Dmitrij on 14.08.2016.
 */
var app = angular.module("AdminPage", ['ngResource', 'ngMaterial', 'ngRoute', 'yaMap']);

app.config(function ($httpProvider, $mdThemingProvider, $routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/templates/received_orders.html',
            controller: 'receivedOrders',
            resolve: {
                orders: function (Order) {
                    return Order.get({page: 0, status: 'RECEIVED'}).$promise;
                }
            }
        })
        .when('/login', {
            templateUrl: "/templates/login.html",
            controller: "loginController"
        })
        .when('/orders/accepted', {
            templateUrl: '/templates/accepted_orders.html',
            controller: 'acceptedOrders',
            resolve: {
                orders: function (Order) {
                    return Order.get({page: 0, status: 'ACCEPTED'}).$promise;
                }
            }
        })
        .when('/orders/budget', {
            templateUrl: "/templates/budget.html",
            controller: 'budget',
            resolve: {
                prices: function (Prices) {
                    return Prices.query().$promise;
                }
            }
        })
        .when("/admin/settings", {
            templateUrl: "/templates/admins_profiles.html",
            controller: "adminsProfiles",
            resolve: {
                admins: function (Admin) {
                    return Admin.get({page: 0}).$promise;
                }
            }
        })
        .otherwise('/');
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $mdThemingProvider.theme('default')
        .primaryPalette('light-blue');

    $mdThemingProvider.theme('docs-dark')
        .dark();

    $mdThemingProvider.setDefaultTheme('default')
});

app.directive('orderAddress', function () {
    return {
        restrict: 'E',
        templateUrl: 'templates/order_address.html',
        scope: {order: '='},
        controller: ['$scope', 'faClasses', function ($scope, faClasses) {
            $scope.getClass = faClasses.getClass;
            $scope.changeOrderStatus = function (order) {
                order.status = order.status == 'DELIVERED' ? 'ACCEPTED' : 'DELIVERED';
                $scope.order = order;
            }

        }]
    }
});
app.directive('order', function () {
    return {
        restrict: "E",
        templateUrl: '/templates/order.html',
        scope: {
            order: '=',
            active: '='
        },
        controller: ['$scope', 'mdClasses', function ($scope, mdClasses) {
            $scope.getClass = mdClasses.getClass;
        }]
    }
});
app.directive("ordersList", function () {
    return {
        restrict: 'E',
        templateUrl: 'templates/orders_list.html',
        scope: {orders: '=', currentOrder: '='},
        controller: ['$scope', function ($scope) {
            $scope.selectOrder = function (order) {
                $scope.currentOrder = order;
            };
            $scope.isActive = function (order) {
                return $scope.currentOrder && $scope.currentOrder.id == order.id
            };
            $scope.sort = {
                name: {
                    direction: 'down',
                    name: 'client.name',
                    directionSign: '-'
                },
                date: {
                    direction: 'down',
                    name: 'orderMade',
                    directionSign: '-'
                }
            };
            $scope.activeSorting = $scope.sort.date;
            $scope.changeSorting = function (sort) {
                sort.direction = sort.direction == 'down' ? 'up' : 'down';
                sort.directionSign = sort.directionSign == '-' ? '+' : '-';
                $scope.activeSorting = sort;
            };
        }]
    }
});
app.directive('ordersAddressesList', function () {
    return {
        templateUrl: 'templates/orders_addresses_list.html',
        restrict: 'E',
        scope: {orders: '=', currentOrder: '='},
        controller: ['$scope', function ($scope) {
            $scope.selectOrder = function (order) {
                $scope.currentOrder = order;
            };
            $scope.sort = {
                city: {
                    direction: 'down',
                    name: 'orderAddress.city',
                    directionSign: '-'
                },
                street: {
                    direction: 'down',
                    name: 'orderAddress.street',
                    directionSign: '-'
                },
                date: {
                    direction: 'down',
                    name: 'orderMade',
                    directionSign: '-'
                }
            };
            $scope.activeSorting = $scope.sort.date;
            $scope.changeSorting = function (sort) {
                sort.direction = sort.direction == 'down' ? 'up' : 'down';
                sort.directionSign = sort.directionSign == '-' ? '+' : '-';
                $scope.activeSorting = sort;
            };
        }]
    }
});
app.directive('orderForm', function () {
    return {
        restrict: 'E',
        templateUrl: 'templates/order_form.html',
        scope: {currentOrder: '='},
        controller: ['$scope', 'Order', function ($scope, Order) {
            $scope.updateOrder = function (order) {
                order.status = "ACCEPTED";
                Order.update({id: order.id}, order);
            };
        }]
    }
});

app.controller('appController', ['$scope', '$mdSidenav', 'sidenavTitle','$timeout', function ($scope, $mdSidenav, sidenavTitle,$timeout) {
    $scope.getTitle = function () {
        return sidenavTitle.title;
    };
    
    $scope.toggleLeft = buildDelayedToggler('left');
    function debounce(func, wait, context) {
        var timer;
        return function debounced() {
            var context = $scope,
                args = Array.prototype.slice.call(arguments);
            $timeout.cancel(timer);
            timer = $timeout(function () {
                timer = undefined;
                func.apply(context, args);
            }, wait || 10);
        };
    }

    function buildDelayedToggler(navID) {
        return debounce(function () {
            $mdSidenav(navID).toggle()
        }, 200);
    }
}]);
app.controller('navigator', ['$scope', 'Credentials', '$location', 'navigation', 'sidenavTitle',
    function ($scope, Credentials, $location, navigation, sidenavTitle) {

        $scope.navigations = navigation;
        $scope.changeLocation = function (navigation) {
            sidenavTitle.title = navigation.header;
            $location.path(navigation.path);
        };

        var callback = function () {
            if (Credentials.authenticated) {
                $location.path("/");
                $scope.error = false;
            } else {
                $location.path("/login");
                $scope.error = true;
            }
        };

        Credentials.authenticate(null, null, callback);

        $scope.authenticated = function () {
            return Credentials.authenticated;
        };

        $scope.admin = {userName: null, password: null};

        $scope.login = function (admin) {
            Credentials.authenticate(admin.userName, admin.password, callback);
        };
        $scope.close = function () {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav('left').close();
        };
    }]);
app.controller('loginController', function () {
});
app.controller("receivedOrders", ['$scope', 'orders', function ($scope, orders) {
    $scope.orders = orders['content'];
    $scope.currentOrder = null;
}]);
app.controller("acceptedOrders", ['$scope', 'orders', 'Order', function ($scope, orders, Order) {
    $scope.orders = orders['content'];
    $scope.currentOrder = null;
    $scope.submitOrders = function (orders) {
        var deliveredOrders = filterOrders(orders, 'DELIVERED');
        for (var i = 0; i < deliveredOrders.length; i++)
            Order.update({id: deliveredOrders[i].id}, deliveredOrders[i]);
        $scope.orders = filterOrders(orders, "ACCEPTED");
    };
    var deliveryHome = {
        point: "Украина, Волынская область, Иваничевский район, село Низкиничи, Октябрьская улица, 18",
        type: "wayPoint"
    };

    var filterOrders = function (orders, status) {
        return orders.filter(function (order) {
            return order.status == status;
        });
    };

    var serializePoints = function (orders) {
        return orders.map(function (order) {
            return {
                point: "Украина, " + order.orderAddress.city + ", " + order.orderAddress.street + ", " + order.orderAddress.building,
                type: 'wayPoint'
            }
        })
    };
    var routePoints = serializePoints($scope.orders);

    $scope.$watch("orders", function (newOrders, oldOrders) {
        var acceptedOrders = filterOrders(newOrders, 'DELIVERED');
        $scope.map.geoObjects.removeAll();
        if (acceptedOrders.length != 0) {
            routePoints = serializePoints(acceptedOrders);
            routePoints.unshift("Украина, Волынская область, Иваничевский район, село Низкиничи, Октябрьская улица, 18");
            routePoints.push("Украина, Волынская область, Иваничевский район, село Низкиничи, Октябрьская улица, 18");

            ymaps.route(routePoints, {mapStateAutoApply: true}).then(function (route) {
                $scope.map.geoObjects.add(route);
            }, function (error) {
                alert('Возникла ошибка: ' + error.message);
            });
        }
    }, true);

    $scope.map = null;
    $scope.route = function (map) {
        $scope.map = map;
    };
    /*
     $scope.buildRoute = function (orders) {
     var acceptedOrders = filterOrders(orders, 'DELIVERED');
     $scope.map.geoObjects.removeAll();
     if (acceptedOrders.length > 0) {
     routePoints = serializePoints(acceptedOrders);
     routePoints.unshift(deliveryHome);
     routePoints.push(deliveryHome);

     ymaps.route(routePoints, {mapStateAutoApply: true}).then(function (route) {
     $scope.map.geoObjects.add(route);
     }, function (error) {
     alert('Возникла ошибка: ' + error.message);
     });
     }
     };*/

}]);
app.controller("adminsProfiles", ["$scope", "Admin", "admins", function ($scope, Admin, admins) {
    $scope.admins = admins['content'];
    $scope.activeAdmin = undefined;
    $scope.getClass = function(admin){
        switch (admin.authorities){
            case "SUPER_ADMIN":
                return "person";
            case "ADMIN":
                return "person_outline";
        }
    };
    $scope.newAdmin = {};
    
    $scope.makeActive = function (admin) {
        $scope.activeAdmin = admin;
    };
    $scope.updateAdmin = function (admin) {
        Admin.update({id: admin.id}, admin);
    };
}]);
app.controller("budget", ['$scope', 'prices', 'Prices', "Order", function ($scope, prices, Prices, Order) {
    $scope.prices = prices;
    $scope.orders = [];

    var statuses = ["ACCEPTED", "DELIVERED"];
    for (var i = 0; i < statuses.length; i++)
        Order.get({status: statuses[i], page: 0}).$promise.then(function (orders) {
            $scope.orders = $scope.orders.concat(orders['content']);
        });


    $scope.getPriceLabel = function (price) {
        switch (price.priceName) {
            case "natural_gas":
                return "Ціна за м<sup>3</sup> газу";
            case "briquettes":
                return "Ціна за кг брикету";
            case "wood":
                return "Ціна за кг дров";
        }
    };
    $scope.updatePrices = function () {
        for (var i = 0; i < prices.length; i++) {
            Prices.update({id: prices[i].id}, prices[i]);
        }
    };

    $scope.currentOrder = null;

    $scope.updateOrder = function (order, status) {
        if (order && status) {
            order.status = status;
            Order.update({id: order.id}, order);
        }
    };
    $scope.orderActions = [{
        status: "COMPLETED",
        message: "Помітити як виконане",
        icon: "done"
    }, {
        status: "RECEIVED",
        message: "Змінити деталі",
        icon: "settings_phone"
    }, {
        status: "ACCEPTED",
        message: "Повторна доставка",
        icon: "local_shipping"
    }, {
        status: "CANCELLED",
        message: "Помітити як відмінене",
        icon: "delete"
    }]

}]);

app.service('faClasses', function () {
    this.getClass = function (className) {
        switch (className) {
            case "RECEIVED":
                return 'fa-phone';
            case "ACCEPTED":
                return 'fa-truck';
            case "DELIVERED":
                return 'fa-money';
            case "CLOSED":
                return 'fa-thumbs-o-up';
        }
    }
});
app.service('mdClasses', function () {
    this.getClass = function (className) {
        switch (className) {
            case "RECEIVED":
                return 'phone';
            case "ACCEPTED":
                return 'local_shipping';
            case "DELIVERED":
                return 'monetization_on';
            case "COMPLETED":
                return 'done';
            case "CANCELLED":
                return 'delete';
        }
    }
});
app.service('navigation', function () {
    return [
        {
            mdClass: 'local_shipping',
            header: 'Доставка',
            subheader: 'Замовлення що очікують доставки',
            path: "/orders/accepted"
        },
        {
            mdClass: 'phone',
            header: 'Зворотні звінки',
            subheader: 'Замовлення що очікують дзвінка',
            path: "/"
        },
        {
            mdClass: 'monetization_on',
            header: 'Оплата',
            subheader: 'Замовлення що очікують оплати',
            path: "/orders/budget"
        },
        {
            mdClass:'supervisor_account',
            header:'Налаштування',
            subheader:"Змінити ",
            path:"/admin/settings"
        }];
});
app.service('Credentials', function ($http) {
    var self = this;
    self.authenticated = false;
    self.userName = null;
    self.password = null;
    self.authenticate = function (userName, password, callback) {
        var headers = userName ? {authorization: "Basic " + btoa(userName + ":" + password)} : {};
        $http.get("/admin", {headers: headers}).then(function (responce) {
            self.authenticated = true;
            self.userName = responce.userName;
            callback && callback();
        }, function () {
            self.authenticated = false;
            callback && callback();
        });
    }
});
app.service('sidenavTitle', ['navigation', function (navigation) {
    this.title = navigation[1].header;
}]);

app.factory('Admin', function ($resource) {
    return $resource('/admin/:id', {id: '@id'})
});
app.factory('Order', ['$resource', function ($resource) {
    return $resource("/order/:id", {id: '@id'},
        //actions of Order
        {
            update: {method: "PUT"},
            //returns all orders that need a callback
            getReceived: {
                method: "GET",
                params: {status: "RECEIVED"},
                cache: false
            }
        });
}]);
app.factory('Client', ['$resource', function ($resource) {
    return $resource('/client/:id', {id: '@id'}, {
        getWithOrders: {
            method: 'GET',
            params: {clientOrders: true}
        }
    })
}]);
app.factory('Prices', ['$resource', function ($resource) {
    return $resource("/price/:id", {id: "@id"}, {
        update: {
            method: "PUT"
        }
    });
}]);