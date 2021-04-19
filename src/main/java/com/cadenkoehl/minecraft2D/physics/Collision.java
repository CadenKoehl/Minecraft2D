package com.cadenkoehl.minecraft2D.physics;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.entities.Tile;

public class Collision {

    private final Tile tile;
    private final Block block;
    private final Direction direction;

    public Collision(Tile tile, Block block) {
        this.tile = tile;
        this.block = block;
        this.direction = calculateDirection();
    }

    private Direction calculateDirection() {
        if (tile.pos.x > block.pos.x) return Direction.RIGHT;
        if (tile.pos.x < block.pos.x) return Direction.LEFT;
        if (tile.pos.y > block.pos.y) return Direction.UP;
        if (tile.pos.y < block.pos.y) return Direction.DOWN;
        return null;
    }

    public Tile getTile() {
        return tile;
    }

    public Block getBlock() {
        return block;
    }

    public Direction getDirection() {
        return direction;
    }
}
