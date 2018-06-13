'use strict';

angular.module('app')
    .controller('NewSnippetController', function ($rootScope, $scope, $state, snippetService, languageService) {
        if ($rootScope.USER != null && $rootScope.USER.banned) {
            $state.go("snippets");
        }

        $scope.snippet = {};

        $scope.getLanguages = function () {
            languageService.findAll(
                function (res) {
                    $scope.languages = res.data;
                    $scope.snippet.language = $scope.languages[0];
                },
                function () {
                    $scope.languages = [];
                });
        };

        $scope.createSnippet = function () {
            $scope.snippet.duration = $scope.durationHours * 60 * 60;

            snippetService.create(
                $scope.snippet,
                function (res) {
                    $state.go("snippet", {id: res.data});
                    $scope.snippet = {};
                },
                function () {
                });
        };
    });
