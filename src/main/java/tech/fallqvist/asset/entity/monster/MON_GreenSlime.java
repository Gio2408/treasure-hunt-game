package tech.fallqvist.asset.entity.monster;

import tech.fallqvist.GamePanel;
import tech.fallqvist.asset.entity.Entity;
import tech.fallqvist.asset.object.ability.OBJ_Rock;

import java.awt.*;
import java.util.Random;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gamePanel) {
        super(gamePanel);

        setName("Green Slime");
        setDirection("down");
        setSpeed(1);
        setMaxLife(4);
        setCurrentLife(getMaxLife());
        setAttackPower(5);
        setDefensePower(0);
        setExp(2);

        setProjectile(new OBJ_Rock(gamePanel));
        setMaxAmmo(5);
        setCurrentAmmo(getMaxAmmo());

        setCollisionArea(new Rectangle(3, 18, 42, 30));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);

        getAnimationImages();
    }

    public void getAnimationImages() {
        setUp1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setUp2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setDown2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setLeft2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight1(setup("/images/monster/greenslime_down_1", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
        setRight2(setup("/images/monster/greenslime_down_2", getGamePanel().getTileSize(), getGamePanel().getTileSize()));
    }

    @Override
    public void damageReaction() {
        setActionLockCounter(0);
        setDirection(getGamePanel().getPlayer().getDirection());
    }

    @Override
    public void setupAI() {
        super.setupAI();

        int i = new Random().nextInt(100) + 1;

        if (i > 99
                && !getProjectile().isAlive()
                && getProjectileAvailableCounter() == 30
                && getProjectile().haveEnoughResource(this)) {

            getProjectile().set(getWorldX(), getWorldY(), getDirection(), true, this);
            getProjectile().subtractResource(this);
            getGamePanel().getProjectiles().add(getProjectile());
            setProjectileAvailableCounter(0);
        }
    }


}
