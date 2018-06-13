'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('newSnippet', {
                url: '/newSnippet',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'snippets/newSnippet/newSnippet.html',
                        controller: 'NewSnippetController'
                    }
                }
            })
            .state('snippet', {
                url: '/snippets/:id',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'snippets/snippetDetails/snippetDetails.html',
                        controller: 'SnippetDetailsController'
                    }
                }
            })
            .state('snippets', {
                url: '/snippets',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'snippets/snippetList/snippetList.html',
                        controller: 'SnippetListController'
                    }
                }
            });
    });
