angular.module('app')
    .service('commentService', function ($http) {
        return {
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/comments/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            createScore: function (commentId, score, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/comments/' + commentId + '/scores',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: score
                };
                $http(req).then(onSuccess, onError);
            },
            removeScore: function (commentId, scoreId, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/comments/' + commentId + '/scores/' + scoreId
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });
