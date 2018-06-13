angular.module('app')
    .service('languageService', function ($http) {
        return {
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/languages/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/languages'
                };
                $http(req).then(onSuccess, onError);
            },
            create: function (language, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/languages',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: language
                };
                $http(req).then(onSuccess, onError);
            },
            remove: function (id, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/languages/' + id
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });
