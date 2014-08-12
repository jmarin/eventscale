//define(['angular', 'home', 'dashboard', 'services'], function(angular) {
  define(['angular'], function(angular) {
  'use strict';

  // We must already declare most dependencies here (except for common), or the submodules' routes
  // will not be resolved
	return angular.module('app', []);
  //return angular.module('app', ['yourprefix.home','yourprefix.dashboard', 'yourprefix.services']);
});
