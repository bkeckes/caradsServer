'use strict';

var app = angular.module('client', ['ngRoute', 'ngTable', 'pascalprecht.translate', 'ui.bootstrap']);

app.config(function ($routeProvider) {

    $routeProvider
            .when('/driver/:driverId/cars/', {
                controller: 'cars',
                templateUrl: 'views/cars.html'
            })
            .when('/driver/:driverId/car/', {
                controller: 'car',
                templateUrl: 'views/car.html'
            })
            .when('/driver/:driverId/car/:carId', {
                controller: 'car',
                templateUrl: 'views/car.html'
            })
            .when('/advertiser/:advertiserId/campaigns/', {
                controller: 'campaigns',
                templateUrl: 'views/campaigns.html'
            })
            .when('/advertiser/:advertiserId/campaign/', {
                controller: 'campaign',
                templateUrl: 'views/campaign.html'
            })
            .when('/advertiser/:advertiserId/campaign/:campaignId', {
                controller: 'campaign',
                templateUrl: 'views/campaign.html'
            })
            .when('/advertiser/:id', {
                controller: 'advertiser',
                templateUrl: 'views/advertiser.html'
            })
            .when('/advertiser/:id/edit', {
                controller: 'advertiser',
                templateUrl: 'views/advertiserEdit.html'
            })
            .when('/driver/:id', {
                controller: 'driver',
                templateUrl: 'views/driver.html'
            })
            .when('/driver/:id/edit', {
                controller: 'driver',
                templateUrl: 'views/driverEdit.html'
            })
            .when('/drivers/', {
                controller: 'drivers',
                templateUrl: 'views/drivers.html'
            })
            .when('/advertisers/', {
                controller: 'advertisers',
                templateUrl: 'views/advertisers.html'
            })
            .when('/home', {
                controller: 'home',
                templateUrl: 'views/home.html'
            })
            .when('/', {
                redirectTo: '/home'
            })
            .otherwise({redirectTo: '/'});

});

app.config(function ($translateProvider) {

    var de = (function () {
        var json = null;
        $.ajax({
            'async': false,
            'global': false,
            'url': "lang/de.json",
            'dataType': "json",
            'success': function (data) {
                json = data;
            }
        });
        return json;
    })();

    var en = (function () {
        var json = null;
        $.ajax({
            'async': false,
            'global': false,
            'url': "lang/en.json",
            'dataType': "json",
            'success': function (data) {
                json = data;
            }
        });
        return json;
    })();

    $translateProvider
            .translations('de', de)
            .translations('en', en)
            .registerAvailableLanguageKeys(['en', 'de'], {
                'en_US': 'en',
                'en_UK': 'en',
                'de_DE': 'de',
                'de_CH': 'de',
                'de_AT': 'de'
            })
            .determinePreferredLanguage()
            .fallbackLanguage('en');

});