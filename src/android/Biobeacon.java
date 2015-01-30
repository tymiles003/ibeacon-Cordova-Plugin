package com.biotekno.beacon;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

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
        
        if(action.equalsIgnoreCase("add_beacon")){
            
            String uuid = "";
            int major = 0;
            int minor = 0;
            
            try{
                uuid = args.getString(0);
                major = args.getInt(1);
                minor = args.getInt(2);
            }
            catch(JSONException error){
                callbackContext.error("Parameters error");
                return false;
            }
            
            vfm.addBeacon(new VFProximityBeacon(uuid,major,minor));
            callbackContext.success();
        
            return true;
        }       
        if(action.equals("start_proximinity")){
            
            startProximintyListener();
            
            return true;
        }
                        
        return false;  
    } 
    
    private void startProximintyListener(){
        vfm.setProximityListener(new VFProximityManager.VFProximityListener() {
               
            @Override
               public void onStarted() {
                    
               }

               @Override
               public void onNotStarted(String error) {
                   // Could not start tracking.
               }

               @Override
               public void onBeaconUpdate(VFProximityBeacon beacon, int proximity) {
                   // Proximity event
                   switch(proximity){
                        case VFProximityManager.PROXIMITY_IMMEDIATE:
                        
                        break;
                        case VFProximityManager.PROXIMITY_NEAR:
                        
                        break;
                        case VFProximityManager.PROXIMITY_FAR:
                        
                        break;
                        case VFProximityManager.PROXIMITY_UNKNOWN:
                        break;
                   }
               }
        });     
    }
    
}