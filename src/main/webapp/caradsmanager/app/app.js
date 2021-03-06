'use strict';

var app = angular.module('client', ['ngRoute', 'ngCookies', 'ngTable', 'pascalprecht.translate', 'ui.bootstrap', 'angular.filter']);

app.config(function ($routeProvider) {

    $routeProvider
            .when('/driver/:driverId/cars/', {
                controller: 'cars',
                templateUrl: 'views/cars.html'
            })
            .when('/driver/:driverId/car/:carId', {
                controller: 'car',
                templateUrl: 'views/car.html'
            })
            .when('/driver/:driverId/carEdit/', {
                controller: 'carEdit',
                templateUrl: 'views/carEdit.html'
            })
            .when('/driver/:driverId/carEdit/:carId', {
                controller: 'carEdit',
                templateUrl: 'views/carEdit.html'
            })
            .when('/advertiser/:advertiserId/campaigns/', {
                controller: 'campaigns',
                templateUrl: 'views/campaigns.html'
            })
            .when('/advertiser/:advertiserId/campaignEdit/', {
                controller: 'campaignEdit',
                templateUrl: 'views/campaignEdit.html'
            })
            .when('/advertiser/:advertiserId/campaignEdit/:campaignId', {
                controller: 'campaignEdit',
                templateUrl: 'views/campaignEdit.html'
            })
            .when('/advertiser/:advertiserId/campaign/:campaignId', {
                controller: 'campaign',
                templateUrl: 'views/campaign.html'
            })
            .when('/advertiser/:advertiserId/campaign/:campaignId/carRequest/:carId', {
                controller: 'campaign',
                templateUrl: 'views/campaign.html'
            })
            .when('/advertiser/:advertiserId/campaign/:campaignId/availableCars/', {
                controller: 'carSearch',
                templateUrl: 'views/carSearch.html'
            })
            .when('/advertiser/:id', {
                controller: 'advertiser',
                templateUrl: 'views/advertiser.html'
            })
            .when('/advertiser/:id/edit', {
                controller: 'advertiserEdit',
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
            .when('/carRequests/', {
                controller: 'carRequests',
                templateUrl: 'views/carRequests.html'
            })
            .when('/faq/', {
                controller: 'faq',
                templateUrl: 'views/faq.html'
            })
            .when('/accountSettings/', {
                controller: 'accountSettings',
                templateUrl: 'views/accountSettings.html'
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

// datepickerPopup fix
app.directive('datepickerPopup', function () {
    return {
        restrict: 'EAC',
        require: 'ngModel',
        link: function (scope, element, attr, controller) {
            //remove the default formatter from the input directive to prevent conflict
            controller.$formatters.shift();
        }
    };
});

app.run(function (datepickerConfig, datepickerPopupConfig, timepickerConfig) {
    datepickerConfig.startingDay = 1;
    datepickerConfig.showWeeks = false;

    datepickerPopupConfig.currentText = 'Heute';
    datepickerPopupConfig.clearText = 'Löschen';
    datepickerPopupConfig.closeText = 'Schließen';

    timepickerConfig.hourStep = 1;
    timepickerConfig.minuteStep = 15;

    timepickerConfig.showMeridian = true;

});