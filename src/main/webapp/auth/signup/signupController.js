'use strict';

angular.module('app')
    .controller('SignupController', function ($rootScope, $scope, $state, authService) {
        $scope.signup = function () {
            authService.signup(
                $scope.user,
                function () {
                    $scope.user = {};
                    $state.go("signin");
                }, function () {
                    toastr.error('Username taken!');
                });
        };
    });
