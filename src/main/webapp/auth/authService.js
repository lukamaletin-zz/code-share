angular.module('app')
    .service('authService', function ($http) {
        return {
            signup: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/auth/signup',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            signin: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/auth/signin',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            signout: function (onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/auth/signout'
                };
                $http(req).then(onSuccess, onError);
            },
            authenticate: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/auth/authenticate'
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });
