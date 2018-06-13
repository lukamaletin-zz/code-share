'use strict';

angular.module('app')
    .controller('NavbarController', function ($rootScope, $scope, $state, authService) {
        $scope.signout = function () {
            authService.signout(
                function () {
                    $rootScope.USER = null;
                    $state.go("snippets");
                },
                function () {
                    $rootScope.USER = null;
                    $state.go("snippets");
                });
        };
    });
