'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('user', {
                url: '/users/:id',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'users/userDetails/userDetails.html',
                        controller: 'UserDetailsController'
                    }
                }
            })
            .state('users', {
                url: '/users',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'users/userList/userList.html',
                        controller: 'UserListController'
                    }
                }
            });
    });
