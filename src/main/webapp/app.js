'use strict';

angular.module('app', ['ui.router'])
    .config(function ($urlRouterProvider) {
        $urlRouterProvider.otherwise('/snippets');
    })
    .run(function ($rootScope, authService) {
        authService.authenticate(
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
                }
            }, function () {
                $rootScope.USER = null;
            });
    });
