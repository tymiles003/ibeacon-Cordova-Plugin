var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

var PluginMethod = {
	ADD_BEACON	  : "add_beacon",
	REMOVE_BEACON : "remove_beacon",
	START_RANGING : "start_beacons",
	STOP_RANGING  : "stop_beacons"
}

 var VFProximity = {
 	PROXIMITY_IMMEDIATE	:1,
    PROXIMITY_NEAR		:2,
    PROXIMITY_FAR		:3,
    PROXIMITY_UNKNOWN	:0
}

const PLUGIN_CLASS  = "Biobeacon";

var Biobeacon = function(){
	console.log("Constructer beacon")
}

Biobeacon.addBeacon = function(beacon,onsucceed,onfail){		
	cordova.exec(function(winParam) {
		if(onsucceed) onsucceed(winParam);
	},function(error) {
		if(onfail) onfail(error);
	},PLUGIN_CLASS,PluginMethod.ADD_BEACON,[beacon.uuid,beacon.major,beacon.minor]);
};

Biobeacon.removeBeacon = function(beacon,onsucceed,onfail){		
	cordova.exec(function(winParam) {
		if(onsucceed) onsucceed(winParam);
	},function(error) {
		if(onfail) onfail(error);
	},PLUGIN_CLASS,PluginMethod.REMOVE_BEACON,[beacon.uuid,beacon.major,beacon.minor]);
};

Biobeacon.startRanging = function(onBeaconUpdate,onfail){
	cordova.exec(onBeaconUpdate,function(error) {
		if(onfail) onFail(onfail);
	},PLUGIN_CLASS,PluginMethod.START_RANGING,[]);	
}

module.exports = Biobeacon;