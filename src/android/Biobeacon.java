package com.biotekno.beacon;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.oksijen.lbs.indoorpositioning.proximity.VFProximityBeacon;
import com.oksijen.lbs.indoorpositioning.proximity.VFProximityManager;

public class Biobeacon extends CordovaPlugin{    

    private boolean initialized = false ;
    public static final String tag = "biobeacon";   
    private VFProximityManager vfm;      
    
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView)  {
       
        super.initialize(cordova, webView);           
           
        vfm = VFProximityManager.getInstance();
        
        initialized = true;  
        Log.d(tag,"Biobeacon plugin initialized");
    }    
    
    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {         
        
        if(action.equalsIgnoreCase(PluginMethod.ADD_BEACON)){            
        	if(addBeacon(args)){
        		callbackContext.success();      		
        		return true;        		
        	}
        	return false;      
        }   
        
        if(action.equalsIgnoreCase(PluginMethod.REMOVE_BEACON)){
        	if(removeBeacon(args)){
        		callbackContext.success();      		
        		return true;        		
        	}
        	
        	return false;             	
        }
        
        if(action.equalsIgnoreCase(PluginMethod.START_BEACONS)){
        	if(startBeacon(args,callbackContext))return true; 
        }
        
        if(action.equalsIgnoreCase(PluginMethod.STOP_BEACONS)){
        	if(stopRanging(args, callbackContext)) return true;        	
        }        	
        
        return false;  
    }     
    
    private boolean addBeacon(JSONArray args){
    	
    	String uuid = "";
        int major = 0,minor = 0;
        
        try{
            uuid = args.getString(0);
            major = args.getInt(1);
            minor = args.getInt(2);
        }
        catch(JSONException error){ return false;}
        
        vfm.addBeacon(new VFProximityBeacon(uuid,major,minor));
        Log.d(tag,String.format("Beacon added uuid : %s , major: %d , minor: %d",uuid,major,minor));
        return true;
    }
    
    private boolean removeBeacon(JSONArray args){
    	
    	String uuid = "";
        int major = 0,minor = 0;
        
        try{
            uuid = args.getString(0);
            major = args.getInt(1);
            minor = args.getInt(2);
        }
        catch(JSONException error){ return false;}
        
        vfm.removeBeacon(new VFProximityBeacon(uuid,major,minor));    	
        return true;
    }
    
    private boolean startBeacon(JSONArray args, CallbackContext context){
    	
    	startProximintyListener(context);
         
    	String endpoint = "https://indoor.oksijen.com/indoor-server";
    	String apikey = "6dd16b32df5043b1b81a2322736e567f";
    	Integer id = 68622;
    	Long clientId = (long) id;
     
    	vfm.start(endpoint,apikey,clientId,cordova.getActivity().getApplicationContext());         
    	return true;    	
    }
    
    private boolean stopRanging(JSONArray args, CallbackContext context){    	
    	vfm.stop();    	
    	return true;
    }
    
    private void startProximintyListener(final CallbackContext  context){
    	    	
    	vfm.setProximityListener(new VFProximityManager.VFProximityListener(){             
             
    		   @Override
               public void onStarted() {
                    Log.d(tag,"Beacon Proxminity started");                    
               }

               @Override
               public void onNotStarted(String error) {
                   Log.e(tag,"Beacon Proxminity error " +error);
                   String message ="Beacon ranging couldn't started eroor: " + error;     
                 
                   context.error(message); 
               }              
               
               @Override
               public void onBeaconUpdate(VFProximityBeacon beacon, int proximity) { 	   
            	    
                  Log.d(tag,"In renging "+ beacon.uuid);
            	   
            	  JSONObject params = new JSONObject();
            	 
            	  try{            		  
            		  params.put("uuid", beacon.uuid);            		  
            		  params.put("major", beacon.major);
            		  params.put("minor", beacon.minor);            		
            		  params.put("proximity",proximity);
            	  }
            	  catch(JSONException error){};            	  
            	  
            	  PluginResult result = new PluginResult(PluginResult.Status.OK, params);
            	  
            	  result.setKeepCallback(true);
            	  context.sendPluginResult(result);   
                  
               }
        });     
    }    
}