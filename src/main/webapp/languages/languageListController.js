'use strict';

angular.module('app')
    .controller('LanguageListController', function ($rootScope, $scope, $state, languageService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'ADMIN') {
            $state.go("snippets");
        }

        languageService.findAll(
            function (res) {
                $scope.languages = res.data;
            },
            function () {
                $scope.languages = [];
            });

        $scope.createLanguage = function () {
            languageService.create(
                $scope.language,
                function () {
                    $scope.languages.push($scope.language);
                    $scope.language = {};
                },
                function () {
                    $scope.language = {};
                    toastr.error('Language name taken!');
                });
        };
    });
