package com.westeroscraft.westerosblocks.modelexport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.westeroscraft.westerosblocks.WesterosBlockDef;
import com.westeroscraft.westerosblocks.WesterosBlocks;

import net.minecraft.block.Block;

public class FireBlockModelExport extends ModelExport {
    // Template objects for Gson export of block state
    public static class StateObject {
    	public List<States> multipart = new ArrayList<States>();
    }
    public static class States {
    	public WhenRec when = new WhenRec();
    	public List<Apply> apply = new ArrayList<Apply>();
    }
    public static class SideStates extends States {
    	SideStates() {
    		when.OR = new ArrayList<WhenRec>();
    		WhenRec wr = new WhenRec();
    		wr.north = wr.south = wr.east = wr.west = wr.up = Boolean.FALSE;
    		when.OR.add(wr);
    	}
    }
    public static class WhenRec {
    	Boolean north, south, west, east, up;
    	public List<WhenRec> OR;
    }
    public static class Apply {
    	String model;
    	Integer y;
        public Apply(String bn, String ext) {
            this(bn, ext, 0);
        }
    	public Apply(String bn, String ext, int y) {
    	    model = bn;
    	    if (ext != null)
    	        model = model + "_" + ext;
    	    if (y != 0)
    	        this.y = y; 
    	}
    }
    
    // Template objects for Gson export of block models
    public static class ModelObjectFloor {
        public String parent = "minecraft:block/template_fire_floor";    // Use 'fire_floor' model for single texture
        public Texture textures = new Texture();
    }
    public static class ModelObjectSide {
        public String parent = "minecraft:block/template_fire_side";    // Use 'fire_side' model for single texture
        public Texture textures = new Texture();
    }
    public static class ModelObjectSideAlt {
        public String parent = "minecraft:block/template_fire_side_alt";    // Use 'fire_sida_alt' model for single texture
        public Texture textures = new Texture();
    }
    public static class ModelObjectUp {
        public String parent = "minecraft:block/template_fire_up";    // Use 'fire_up' model for single texture
        public Texture textures = new Texture();
    }
    public static class ModelObjectUpAlt {
        public String parent = "minecraft:block/template_fire_up_alt";    // Use 'fire_up_alt' model for single texture
        public Texture textures = new Texture();
    }
    public static class Texture {
        public String fire;
    }
    public static class TextureLayer0 {
        public String layer0;
    }
    public static class ModelObject {
    	public String parent = "item/generated";
        public TextureLayer0 textures = new TextureLayer0();
    }

    public FireBlockModelExport(Block blk, WesterosBlockDef def, File dest) {
        super(blk, def, dest);
        addNLSString("block." + WesterosBlocks.MOD_ID + "." + def.blockName, def.label);
    }
    
    @Override
    public void doBlockStateExport() throws IOException {
        StateObject so = new StateObject();
        String bn = WesterosBlocks.MOD_ID + ":block/generated/" + def.blockName;
        // Make when (all false)
        WhenRec whennone = new WhenRec();
        whennone.north = false;
        whennone.south = false;
        whennone.east = false;
        whennone.west = false;
        whennone.up = false;
        // Add firefloor
        States ps = new States();
        ps.when = whennone;
        ps.apply.add(new Apply(bn, "floor0"));
        ps.apply.add(new Apply(bn, "floor1"));
        so.multipart.add(ps);
        // Add north:true
        ps = new SideStates();
        WhenRec wrec = new WhenRec();
        wrec.north = true;
        ps.when.OR.add(wrec);
        ps.apply.add(new Apply(bn, "side0"));
        ps.apply.add(new Apply(bn, "side1"));
        ps.apply.add(new Apply(bn, "side_alt0"));
        ps.apply.add(new Apply(bn, "side_alt1"));
        so.multipart.add(ps);
        // Add east:true
        ps = new SideStates();
        wrec = new WhenRec();
        wrec.east = true;
        ps.when.OR.add(wrec);
        ps.apply.add(new Apply(bn, "side0", 90));
        ps.apply.add(new Apply(bn, "side1", 90));
        ps.apply.add(new Apply(bn, "side_alt0", 90));
        ps.apply.add(new Apply(bn, "side_alt1", 90));
        so.multipart.add(ps);
        // Add south:true
        ps = new SideStates();
        wrec = new WhenRec();
        wrec.south = true;
        ps.when.OR.add(wrec);
        ps.apply.add(new Apply(bn, "side0", 180));
        ps.apply.add(new Apply(bn, "side1", 180));
        ps.apply.add(new Apply(bn, "side_alt0", 180));
        ps.apply.add(new Apply(bn, "side_alt1", 180));
        so.multipart.add(ps);
        // Add west:true
        ps = new SideStates();
        wrec = new WhenRec();
        wrec.west = true;
        ps.when.OR.add(wrec);
        ps.apply.add(new Apply(bn, "side0", 270));
        ps.apply.add(new Apply(bn, "side1", 270));
        ps.apply.add(new Apply(bn, "side_alt0", 270));
        ps.apply.add(new Apply(bn, "side_alt1", 270));
        so.multipart.add(ps);
        // Add up:true
        ps = new States();
        ps.when.up = true;
        ps.apply.add(new Apply(bn, "up0"));
        ps.apply.add(new Apply(bn, "up1"));
        ps.apply.add(new Apply(bn, "up_alt0"));
        ps.apply.add(new Apply(bn, "up_alt1"));
        so.multipart.add(ps);
        this.writeBlockStateFile(def.blockName, so);
    }

    @Override
    public void doModelExports() throws IOException {
        String txt0 = getTextureID(def.getTextureByIndex(0)); 
        String txt1 = getTextureID(def.getTextureByIndex(1)); 
        // floor0
        ModelObjectFloor modf = new ModelObjectFloor();
        modf.textures.fire = txt0;
        this.writeBlockModelFile(def.blockName + "_floor0", modf);
        // floor1
        modf = new ModelObjectFloor();
        modf.textures.fire = txt1;
        this.writeBlockModelFile(def.blockName + "_floor1", modf);
        // side0
        ModelObjectSide mods = new ModelObjectSide();
        mods.textures.fire = txt0;
        this.writeBlockModelFile(def.blockName + "_side0", mods);
        // side1
        mods = new ModelObjectSide();
        mods.textures.fire = txt1;
        this.writeBlockModelFile(def.blockName + "_side1", mods);
        // side_alt0
        ModelObjectSideAlt modsa = new ModelObjectSideAlt();
        modsa.textures.fire = txt0;
        this.writeBlockModelFile(def.blockName + "_side_alt0", modsa);
        // side_alt1
        modsa = new ModelObjectSideAlt();
        modsa.textures.fire = txt1;
        this.writeBlockModelFile(def.blockName + "_side_alt1", modsa);
        // up0
        ModelObjectUp modu = new ModelObjectUp();
        modu.textures.fire = txt0;
        this.writeBlockModelFile(def.blockName + "_up0", modu);
        // up1
        modu = new ModelObjectUp();
        modu.textures.fire = txt1;
        this.writeBlockModelFile(def.blockName + "_up1", modu);
        // up_alt0
        ModelObjectUpAlt modua = new ModelObjectUpAlt();
        modua.textures.fire = txt0;
        this.writeBlockModelFile(def.blockName + "_up_alt0", modsa);
        // up_alt1
        modua = new ModelObjectUpAlt();
        modua.textures.fire = txt1;
        this.writeBlockModelFile(def.blockName + "_up_alt1", modsa);
        // Build simple item model that refers to block model
        ModelObject mo = new ModelObject();
        mo.textures.layer0 = txt0;
        this.writeItemModelFile(def.blockName, mo);
    }
    @Override
    public void doWorldConverterMigrate() throws IOException {
    	String oldID = def.getLegacyBlockName();
    	if (oldID == null) return;
    	String oldVariant = def.getLegacyBlockVariant();
    	addWorldConverterComment(def.legacyBlockID + "(" + def.label + ")");
    	// BUild old variant map
    	HashMap<String, String> oldstate = new HashMap<String, String>();
    	HashMap<String, String> newstate = new HashMap<String, String>();
    	oldstate.put("north", "$0");
    	oldstate.put("east", "$1");
    	oldstate.put("south", "$2");
    	oldstate.put("west", "$3");
    	oldstate.put("up", "$4");
    	oldstate.put("age", "$5");
    	newstate.put("north", "$0");
    	newstate.put("east", "$1");
    	newstate.put("south", "$2");
    	newstate.put("west", "$3");
    	newstate.put("up", "$4");
    	newstate.put("age", "$5");
        addWorldConverterRecord(oldID, oldstate, def.getBlockName(), newstate);
    }

}
