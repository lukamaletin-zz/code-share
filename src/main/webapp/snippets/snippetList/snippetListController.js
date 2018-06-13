'use strict';

angular.module('app')
    .controller('SnippetListController', function ($scope, $state, snippetService, languageService) {
        $scope.filter = {};

        languageService.findAll(
            function (res) {
                $scope.languages = res.data;
            },
            function () {
                $scope.languages = [];
            });

        snippetService.findAll(
            function (res) {
                $scope.snippets = res.data;
            },
            function () {
                $scope.snippets = [];
            });

        $scope.searchSnippets = function () {
            snippetService.search(
                $scope.filter.description, $scope.filter.language, $scope.filter.startDate, $scope.filter.endDate,
                function (res) {
                    $scope.snippets = res.data;
                }, function () {
                    $scope.snippets = [];
                }
            );
        };

        $scope.removeSnippet = function (snippet) {
            snippetService.remove(
                snippet.id,
                function () {
                    var i = $scope.snippets.indexOf(snippet);
                    if (i > -1) {
                        $scope.snippets.splice(i, 1);
                    }
                },
                function () {
                }
            );
        };
    });
