'use strict';

angular.module('app')
    .controller('UserDetailsController', function ($rootScope, $scope, $state, $stateParams, userService) {
        if ($stateParams.id) {
            userService.findById(
                $stateParams.id,
                function (res) {
                    $scope.user = res.data;
                    $scope.initMap();
                },
                function () {
                    $state.go("users");
                });

            userService.findSnippets(
                $stateParams.id,
                function (res) {
                    $scope.snippets = res.data;
                },
                function () {
                    $scope.snippets = [];
                });
        }
        else {
            $state.go("users");
        }

        $scope.setBanned = function (banned) {
            userService.setBanned(
                $scope.user.id, banned,
                function () {
                    $scope.user.banned = banned;
                },
                function () {
                }
            );
        };

        $scope.uploadPhoto = function (files) {
            if (files && files[0].size > (128 * 1024)) {
                toastr.error('Max photo size is 128KB!');
                return;
            }
            userService.uploadPhoto(
                files[0],
                function (res) {
                    $scope.user.photoUrl = res.data.photoUrl;
                },
                function () {
                }
            );
        };

        $scope.initMap = function () {
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 15,
                center: {lat: 0, lng: 0}
            });
            var geocoder = new google.maps.Geocoder();
            $scope.geocodeAddress(geocoder, map);
        };

        $scope.geocodeAddress = function (geocoder, resultsMap) {
            var address = $scope.user.address;
            geocoder.geocode({'address': address}, function (results, status) {
                if (status === 'OK') {
                    resultsMap.setCenter(results[0].geometry.location);
                    var marker = new google.maps.Marker({
                        map: resultsMap,
                        position: results[0].geometry.location
                    });
                }
            });
        };
    });
