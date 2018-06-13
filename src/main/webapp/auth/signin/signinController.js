'use strict';

angular.module('app')
    .controller('SigninController', function ($rootScope, $scope, $state, authService) {
        $scope.signin = function () {
            authService.signin(
                $scope.user,
                function (res) {
                    $rootScope.USER = {
                        id: res.data.id,
                        role: res.data.role,
                        username: res.data.username,
                        firstName: res.data.firstName,
                        lastName: res.data.lastName,
                        phoneNumber: res.data.phoneNumber,
                        email: res.data.email,
                        address: res.data.address,
                        photoUrl: res.data.photoUrl,
                        banned: res.data.banned
                    };
                    $scope.user = {};
                    $state.go("snippets");
                }, function () {
                    $rootScope.USER = null;
                    toastr.error('Wrong username/password!');
                });
        };
    });
