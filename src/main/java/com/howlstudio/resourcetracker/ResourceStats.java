package com.howlstudio.resourcetracker;
import java.util.UUID;
public class ResourceStats {
    private final UUID uuid; private String name;
    private long blocksMined, itemsCrafted, itemsUsed, chatMessages;
    public ResourceStats(UUID uid,String name){this.uuid=uid;this.name=name;}
    public UUID getUuid(){return uuid;} public String getName(){return name;} public void setName(String n){name=n;}
    public long getBlocksMined(){return blocksMined;} public void addMined(long n){blocksMined+=n;}
    public long getItemsCrafted(){return itemsCrafted;} public void addCrafted(long n){itemsCrafted+=n;}
    public long getItemsUsed(){return itemsUsed;} public void addUsed(long n){itemsUsed+=n;}
    public long getChatMessages(){return chatMessages;} public void addChat(){chatMessages++;}
    public String toConfig(){return uuid+"|"+name+"|"+blocksMined+"|"+itemsCrafted+"|"+itemsUsed+"|"+chatMessages;}
    public static ResourceStats fromConfig(String s){String[]p=s.split("\\|",6);if(p.length<6)return null;ResourceStats r=new ResourceStats(UUID.fromString(p[0]),p[1]);r.blocksMined=Long.parseLong(p[2]);r.itemsCrafted=Long.parseLong(p[3]);r.itemsUsed=Long.parseLong(p[4]);r.chatMessages=Long.parseLong(p[5]);return r;}
}
