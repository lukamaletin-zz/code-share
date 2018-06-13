'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('signin', {
                url: '/signin',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'auth/signin/signin.html',
                        controller: 'SigninController'
                    }
                }
            })
            .state('signup', {
                url: '/signup',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'auth/signup/signup.html',
                        controller: 'SignupController'
                    }
                }
            });
    });
