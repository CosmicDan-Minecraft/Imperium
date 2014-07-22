package com.cosmicdan.minecraftempires.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

/*
 * EntityAIFlockMentality has these characteristics, in descending priority:
 *  1) TODO: Flee from player entities (integrate the EntityAIAvoidEntity with this)
 *  2) ? Flock mechanics are TODO!
 *  3) Wander around
 * 
 */
public class EntityAIFlockMentality extends EntityAIBase {
    private EntityCreature entity;
    private double xPos;
    private double yPos;
    private double zPos;
    private double speedMultiplier;
    //private static final String __OBFID = "CL_00001608";

    public EntityAIFlockMentality(EntityCreature entity, double speedMultiplier) {
        this.entity = entity;
        this.speedMultiplier = speedMultiplier;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.entity.getAge() >= 100)
            return false;
        else if (this.entity.getRNG().nextInt(120) != 0)
            return false;
        else {
            Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
            if (vec3 == null)
                return false;
            else {
                this.xPos = vec3.xCoord;
                this.yPos = vec3.yCoord;
                this.zPos = vec3.zCoord;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.xPos, this.yPos, this.zPos, this.speedMultiplier);
    }
}
