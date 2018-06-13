angular.module('app')
    .service('userService', function ($http) {
        return {
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users'
                };
                $http(req).then(onSuccess, onError);
            },
            search: function (username, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users/search',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    params: {
                        username: username
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            findSnippets: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users/' + id + '/snippets'
                };
                $http(req).then(onSuccess, onError);
            },
            setBanned: function (id, banned, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/users/' + id + '/ban',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: banned
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (id, user, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/users/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            remove: function (id, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/users/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            uploadPhoto: function (file, onSuccess, onError) {
                var formData = new FormData();
                formData.append('file', file);
                var req = {
                    method: 'POST',
                    url: '/api/users/uploadPhoto',
                    headers: {
                        'Content-Type': undefined
                    },
                    data: formData,
                    transformRequest: angular.identity
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });
