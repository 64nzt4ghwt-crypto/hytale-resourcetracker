package com.howlstudio.resourcetracker;
import com.hypixel.hytale.component.Ref; import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.nio.file.*; import java.util.*;
import java.util.stream.Collectors;
public class ResourceManager {
    private final Path dataDir;
    private final Map<UUID,ResourceStats> stats=new LinkedHashMap<>();
    public ResourceManager(Path d){this.dataDir=d;try{Files.createDirectories(d);}catch(Exception e){}load();}
    public int getPlayerCount(){return stats.size();}
    public ResourceStats getOrCreate(UUID uid,String name){ResourceStats s=stats.computeIfAbsent(uid,k->new ResourceStats(k,name));s.setName(name);return s;}
    public void save(){try{StringBuilder sb=new StringBuilder();for(ResourceStats s:stats.values())sb.append(s.toConfig()).append("\n");Files.writeString(dataDir.resolve("stats.txt"),sb.toString());}catch(Exception e){}}
    private void load(){try{Path f=dataDir.resolve("stats.txt");if(!Files.exists(f))return;for(String l:Files.readAllLines(f)){ResourceStats s=ResourceStats.fromConfig(l);if(s!=null)stats.put(s.getUuid(),s);}}catch(Exception e){}}
    public List<ResourceStats> getTopMiners(int n){return stats.values().stream().sorted(Comparator.comparingLong(ResourceStats::getBlocksMined).reversed()).limit(n).collect(Collectors.toList());}
    public AbstractPlayerCommand getStatsCommand(){
        return new AbstractPlayerCommand("rstats","Resource stats. /rstats [player|top]"){
            @Override protected void execute(CommandContext ctx,Store<EntityStore> store,Ref<EntityStore> ref,PlayerRef playerRef,World world){
                String arg=ctx.getInputString().trim().toLowerCase();
                if(arg.equals("top")){var top=getTopMiners(10);playerRef.sendMessage(Message.raw("=== Top Miners ==="));int i=1;for(ResourceStats s:top)playerRef.sendMessage(Message.raw("  "+i+++". §6"+s.getName()+"§r — "+s.getBlocksMined()+" blocks"));return;}
                ResourceStats s=stats.getOrDefault(playerRef.getUuid(),null);
                if(!arg.isEmpty()){for(ResourceStats rs:stats.values())if(rs.getName().equalsIgnoreCase(arg)){s=rs;break;}}
                if(s==null){playerRef.sendMessage(Message.raw("[RStats] No data yet."));return;}
                playerRef.sendMessage(Message.raw("=== Stats: "+s.getName()+" ==="));
                playerRef.sendMessage(Message.raw("  Blocks mined: §6"+s.getBlocksMined()));
                playerRef.sendMessage(Message.raw("  Items crafted: §6"+s.getItemsCrafted()));
                playerRef.sendMessage(Message.raw("  Items used: §6"+s.getItemsUsed()));
                playerRef.sendMessage(Message.raw("  Chat messages: §6"+s.getChatMessages()));
            }
        };
    }
}
