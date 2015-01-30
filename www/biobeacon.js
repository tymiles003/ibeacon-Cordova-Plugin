var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

const PluginMethod ={ 
	ADD_BEACON :"add_beacon"
}

var Biobeacon = function(){}
Biobeacon.prototype.addBeacon = function(beacon,onsucceed,onfail){	
	cordova.exec(function(winParam) {
		if(onsucceed) onsucceed(winParam);
	},function(error) {
		if(onfail) onfail(error);
	},"Beacon",PluginMethod.ADD_BEACON,[beacon.uuid,beacon.major,beacon.minor]);
};

console.log("Beacon plugin added to module");

module.exports = Biobeacon;


/**
* Biotekno Ibeacon Oksijen Cordova Plugin v1.0 2014
*
* @arthur Ozgur Cimen
**/
