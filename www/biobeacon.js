/**
* Biotekno Ibeacon Oksijen Cordova Plugin v1.0 2014
*
* @arthur Ozgur Cimen
**/
var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

var Biobeacon = function(){
	console.log("Constructer beacon")
}

Biobeacon.addBeacon = function(beacon,onsucceed,onfail){	

	exec(null, null, "Media", "startPlayingAudio", [this.id, this.src, options]);
	cordova.exec(function(winParam) {
		if(onsucceed) onsucceed(winParam);
	},function(error) {
		if(onfail) onfail(error);
	},"Beacon","add_beacon",[beacon.uuid,beacon.major,beacon.minor]);
};

console.log("Beacon plugin added to module");


module.exports = Biobeacon;

