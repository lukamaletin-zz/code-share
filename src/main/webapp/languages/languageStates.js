'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('languages', {
                url: '/languages',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'languages/languageList.html',
                        controller: 'LanguageListController'
                    }
                }
            });
    });
