'use strict';

angular.module('app')
    .controller('SnippetDetailsController', function ($rootScope, $scope, $state, $stateParams, snippetService, commentService) {
        if ($stateParams.id) {
            snippetService.findById(
                $stateParams.id,
                function (res) {
                    $scope.snippet = res.data;
                },
                function () {
                    $state.go("snippets");
                });
        }
        else {
            $state.go("snippets");
        }

        $scope.setBanned = function (banned) {
            snippetService.setBanned(
                $scope.snippet.id, banned,
                function () {
                    $scope.snippet.banned = banned;
                },
                function () {
                }
            );
        };

        $scope.comment = {};
        $scope.commentsOrder = "upVotes";

        $scope.addComment = function () {
            snippetService.createComment(
                $scope.snippet.id,
                $scope.comment,
                function (res) {
                    commentService.findById(
                        res.data,
                        function (res) {
                            $scope.snippet.comments.push(res.data);
                        },
                        function () {
                        }
                    );
                    $scope.comment = {};
                },
                function () {
                    $scope.comment = {};
                });
        };

        $scope.rateCommentDisabled = function (comment) {
            if ($rootScope.USER == null || $rootScope.USER.banned) {
                return true;
            }
            for (var i = 0; i < comment.scores.length; i++) {
                if (comment.scores[i].user.id == $rootScope.USER.id) {
                    return true;
                }
            }
            return false;
        };

        $scope.commentRated = function (comment, upVote) {
            if ($rootScope.USER == null) {
                return false;
            }
            for (var i = 0; i < comment.scores.length; i++) {
                if (comment.scores[i].user.id == $rootScope.USER.id &&
                    comment.scores[i].upVote == upVote) {
                    return true;
                }
            }
            return false;
        };

        $scope.rateComment = function (comment, upVote) {
            var score = {
                upVote: upVote,
                user: $rootScope.USER
            };

            commentService.createScore(
                comment.id, score,
                function () {
                    snippetService.findById(
                        $stateParams.id,
                        function (res) {
                            $scope.snippet = res.data;
                        },
                        function () {
                            $state.go("snippets");
                        });
                },
                function () {
                });
        };

        $scope.removeComment = function (snippet, comment) {
            snippetService.removeComment(
                snippet.id, comment.id,
                function () {
                    var i = snippet.comments.indexOf(comment);
                    if (i > -1) {
                        snippet.comments.splice(i, 1);
                    }
                },
                function () {
                }
            );
        };
    });
