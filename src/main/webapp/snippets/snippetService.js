angular.module('app')
    .service('snippetService', function ($http) {
        return {
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/snippets/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/snippets'
                };
                $http(req).then(onSuccess, onError);
            },
            search: function (description, language, startDate, endDate, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/snippets/search',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    params: {
                        description: description,
                        language: language,
                        startDate: startDate,
                        endDate: endDate
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            create: function (snippet, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/snippets',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: snippet
                };
                $http(req).then(onSuccess, onError);
            },
            setBanned: function (id, banned, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/snippets/' + id + '/ban',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: banned
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (id, snippet, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/snippets/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: snippet
                };
                $http(req).then(onSuccess, onError);
            },
            remove: function (id, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/snippets/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            createComment: function (snippetId, comment, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/snippets/' + snippetId + '/comments',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: comment
                };
                $http(req).then(onSuccess, onError);
            },
            removeComment: function (snippetId, commentId, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/snippets/' + snippetId + '/comments/' + commentId
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });
