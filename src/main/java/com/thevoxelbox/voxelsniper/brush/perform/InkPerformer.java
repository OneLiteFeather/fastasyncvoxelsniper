/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.Message;
import org.bukkit.block.Block;

/**
 * @author Voxel
 */
public class InkPerformer extends AbstractPerformer {

	private byte d;

	public InkPerformer() {
		super("Ink");
	}

	@Override
	public void init(com.thevoxelbox.voxelsniper.SnipeData snipeData) {
		this.world = snipeData.getWorld();
		this.d = snipeData.getData();
	}

	@Override
	public void info(Message message) {
		message.performerName(this.getName());
		message.blockData();
	}

	@Override
	public void perform(Block block) {
		this.undo.put(block);
		block.setData(this.d);
	}
}