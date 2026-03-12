package com.howlstudio.resourcetracker;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/** ResourceTracker — Track player statistics: blocks mined, items crafted, distance walked, items used. */
public final class ResourceTrackerPlugin extends JavaPlugin {
    private ResourceManager mgr;
    public ResourceTrackerPlugin(JavaPluginInit init){super(init);}
    @Override protected void setup(){
        System.out.println("[ResourceTracker] Loading...");
        mgr=new ResourceManager(getDataDirectory());
        new ResourceListener(mgr).register();
        CommandManager.get().register(mgr.getStatsCommand());
        System.out.println("[ResourceTracker] Ready. "+mgr.getPlayerCount()+" tracked players.");
    }
    @Override protected void shutdown(){if(mgr!=null)mgr.save();System.out.println("[ResourceTracker] Stopped.");}
}
