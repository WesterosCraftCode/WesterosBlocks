package com.westeroscraft.westerosblocks.modelexport;

import java.io.File;
import java.io.IOException;

import com.westeroscraft.westerosblocks.WesterosBlockDef;
import com.westeroscraft.westerosblocks.WesterosBlocks;

import net.minecraft.block.Block;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CuboidNSEWUDBlockModelExport extends CuboidBlockModelExport {
    
    public CuboidNSEWUDBlockModelExport(Block blk, WesterosBlockDef def, File dest) {
        super(blk, def, dest);
    }
    
    @Override
    public void doBlockStateExport() throws IOException {
        StateObject so = new StateObject();

        List<Variant> vars = new ArrayList<Variant>();
        for (int setidx = 0; setidx < def.getRandomTextureSetCount(); setidx++) {
        	WesterosBlockDef.RandomTextureSet set = def.getRandomTextureSet(setidx);
        	Variant var = new Variant();
        	var.model = modelFileName("", setidx);
        	vars.add(var);
        }
        so.variants.put("facing=north", vars);
        
        vars = new ArrayList<Variant>();
        for (int setidx = 0; setidx < def.getRandomTextureSetCount(); setidx++) {
        	WesterosBlockDef.RandomTextureSet set = def.getRandomTextureSet(setidx);
        	Variant var = new Variant();
        	var.model = modelFileName("", setidx);
            var.y = 90;
        	vars.add(var);
        }
        so.variants.put("facing=east", vars);
        
        vars = new ArrayList<Variant>();
        for (int setidx = 0; setidx < def.getRandomTextureSetCount(); setidx++) {
        	WesterosBlockDef.RandomTextureSet set = def.getRandomTextureSet(setidx);
        	Variant var = new Variant();
        	var.model = modelFileName("", setidx);
        	var.y = 180;
        	vars.add(var);
        }
        so.variants.put("facing=south", vars);
        
        vars = new ArrayList<Variant>();
        for (int setidx = 0; setidx < def.getRandomTextureSetCount(); setidx++) {
        	WesterosBlockDef.RandomTextureSet set = def.getRandomTextureSet(setidx);
        	Variant var = new Variant();
        	var.model = modelFileName("", setidx);
        	var.y = 270;
        	vars.add(var);
        }
        so.variants.put("facing=west", vars);
        
        vars = new ArrayList<Variant>();
        for (int setidx = 0; setidx < def.getRandomTextureSetCount(); setidx++) {
        	WesterosBlockDef.RandomTextureSet set = def.getRandomTextureSet(setidx);
        	Variant var = new Variant();
        	var.model = modelFileName("", setidx);
        	var.x = 270;
        	vars.add(var);
        }
        so.variants.put("facing=up", vars);
        
        vars = new ArrayList<Variant>();
        for (int setidx = 0; setidx < def.getRandomTextureSetCount(); setidx++) {
        	WesterosBlockDef.RandomTextureSet set = def.getRandomTextureSet(setidx);
        	Variant var = new Variant();
        	var.model = modelFileName("", setidx);
        	var.x = 90;
        	vars.add(var);
        }
        so.variants.put("facing=down", vars);
        
        this.writeBlockStateFile(def.blockName, so);
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
    	oldstate.put("variant", oldVariant);
    	oldstate.put("facing", "$0");
    	newstate.put("facing", "$0");
        addWorldConverterRecord(oldID, oldstate, def.getBlockName(), newstate);
    }
}
