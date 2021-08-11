package com.westeroscraft.westerosblocks.modelexport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.westeroscraft.westerosblocks.WesterosBlockDef;
import com.westeroscraft.westerosblocks.WesterosBlocks;

import net.minecraft.block.Block;

public class CakeBlockModelExport extends ModelExport {
    // Template objects for Gson export of block state
    public static class StateObject {
        public Map<String, Variant> variants = new HashMap<String, Variant>();
    }
    public static class Variant {
        public String model;
        public Integer x;
        public Integer y;
        public Boolean uvlock;
    }
    
    // Template objects for Gson export of block models
    public static class ModelObjectCake {
        public String parent = "minecraft:block/thin_block";    // Use 'thin_block' model for single texture
        public Texture textures = new Texture();
        public List<Element> elements = new ArrayList<Element>();
    }
    public static class Texture {
        public String particle;
        public String bottom;
        public String top;
        public String side;
        public String inside;
    }
    public static class Element {
        public float[] from = { 1, 0, 1 };
        public float[] to = { 15, 8, 15 };
        public Map<String, Face> faces = new HashMap<String, Face>();
    }
    public static class Face {
        public String texture;
        public String cullface;
    }
    public static class ModelObject {
    	public String parent;
    }
    
    public CakeBlockModelExport(Block blk, WesterosBlockDef def, File dest) {
        super(blk, def, dest);
        addNLSString("block." + WesterosBlocks.MOD_ID + "." + def.blockName, def.label);
    }
    
    @Override
    public void doBlockStateExport() throws IOException {
        StateObject so = new StateObject();
        
        for (int i = 0; i < 7; i++) {
            Variant var = new Variant();
            if (i == 0)
                var.model = WesterosBlocks.MOD_ID + ":block/generated/" + def.blockName + "_uneaten";
            else
                var.model = WesterosBlocks.MOD_ID + ":block/generated/" + def.blockName + "_slice" + i;
            so.variants.put(String.format("bites=%d", i), var);
        }
        this.writeBlockStateFile(def.blockName, so);
    }

    @Override
    public void doModelExports() throws IOException {
        String bottxt = getTextureID(def.getTextureByIndex(0));
        String toptxt = getTextureID(def.getTextureByIndex(1));
        String sidetxt = getTextureID(def.getTextureByIndex(2));
        String insidetxt = getTextureID(def.getTextureByIndex(3));

        for (int i = 0; i < 7; i++) {
            ModelObjectCake mod = new ModelObjectCake();
            mod.textures.particle = sidetxt;
            mod.textures.bottom = bottxt;
            mod.textures.top = toptxt;
            mod.textures.side = sidetxt;
            mod.textures.inside = insidetxt;
            int xmin = 1 + (2*i);
            // Handle add element for cake
            Element elem = new Element();
            elem.from[0] = xmin;     // Set size
            // Add down face
            Face f = new Face();
            f.texture = "#bottom";
            f.cullface = "down";
            elem.faces.put("down", f);
            // Add up face
            f = new Face();
            f.texture = "#top";
            elem.faces.put("up", f);
            // Add north face
            f = new Face();
            f.texture = "#side";
            elem.faces.put("north", f);
            // Add south face
            f = new Face();
            f.texture = "#side";
            elem.faces.put("south", f);
            // Add west face
            f = new Face();
            if (i == 0)
                f.texture = "#side";
            else
                f.texture = "#inside";
            elem.faces.put("west", f);
            // Add eath face
            f = new Face();
            f.texture = "#side";
            elem.faces.put("east", f);
            mod.elements.add(elem);
            
            if (i == 0)
                this.writeBlockModelFile(def.blockName + "_uneaten", mod);
            else
                this.writeBlockModelFile(def.blockName + "_slice" + i, mod);
        }
        // Build simple item model that refers to block model
        ModelObject mo = new ModelObject();
        mo.parent = WesterosBlocks.MOD_ID + ":block/generated/" + def.blockName + "_uneaten";
        this.writeItemModelFile(def.blockName, mo);
    }
    @Override
    public void doWorldConverterMigrate() throws IOException {
    	String oldID = def.getLegacyBlockName();
    	if (oldID == null) return;
    	String oldVariant = def.getLegacyBlockVariant();
    	addWorldConverterComment(def.legacyBlockID + "(" + def.label + ")");
    	// BUild old variant map
    	Map<String, String> oldstate = new HashMap<String, String>();
    	Map<String, String> newstate = new HashMap<String, String>();
    	oldstate.put("bites","$0");
    	newstate.put("bites","$0");
        addWorldConverterRecord(oldID, oldstate, def.getBlockName(), newstate);
    }
}
