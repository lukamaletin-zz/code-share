'use strict';

angular.module('app')
    .controller('UserListController', function ($scope, $state, userService) {
        $scope.filter = {};

        userService.findAll(
            function (res) {
                $scope.users = res.data;
            },
            function () {
                $scope.users = [];
            });

        $scope.searchUsers = function () {
            userService.search(
                $scope.filter.username,
                function (res) {
                    $scope.users = res.data;
                }, function () {
                    $scope.users = [];
                }
            );
        };
    });
