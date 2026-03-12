package com.howlstudio.resourcetracker;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
public class ResourceListener {
    private final ResourceManager mgr;
    public ResourceListener(ResourceManager m){this.mgr=m;}
    public void register(){
        HytaleServer.get().getEventBus().registerGlobal(PlayerReadyEvent.class,e->{Player p=e.getPlayer();if(p==null)return;PlayerRef r=p.getPlayerRef();if(r!=null)mgr.getOrCreate(r.getUuid(),r.getUsername());});
        HytaleServer.get().getEventBus().registerGlobal(PlayerDisconnectEvent.class,e->{mgr.save();});
    }
}
