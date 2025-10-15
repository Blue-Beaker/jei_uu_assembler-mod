package io.bluebeaker.jei_uu_assembler.jei.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class DummyCropTile implements ICropTile {
    public final CropCard card;
    public int stage;
    public DummyCropTile(CropCard card){
        this.card=card;
    }
    @Override
    public CropCard getCrop() {
        return this.card;
    }

    @Override
    public void setCrop(CropCard paramCropCard) {

    }

    @Override
    public int getCurrentSize() {
        return stage;
    }

    @Override
    public void setCurrentSize(int newSize) {
        this.stage=newSize;
    }

    @Override
    public int getStatGrowth() {
        return 1;
    }

    @Override
    public void setStatGrowth(int paramInt) {

    }

    @Override
    public int getStatGain() {
        return 1;
    }

    @Override
    public void setStatGain(int paramInt) {

    }

    @Override
    public int getStatResistance() {
        return 1;
    }

    @Override
    public void setStatResistance(int paramInt) {

    }

    @Override
    public int getStorageNutrients() {
        return 0;
    }

    @Override
    public void setStorageNutrients(int paramInt) {

    }

    @Override
    public int getStorageWater() {
        return 0;
    }

    @Override
    public void setStorageWater(int paramInt) {

    }

    @Override
    public int getStorageWeedEX() {
        return 0;
    }

    @Override
    public void setStorageWeedEX(int paramInt) {

    }

    @Override
    public int getScanLevel() {
        return 4;
    }

    @Override
    public void setScanLevel(int paramInt) {

    }

    @Override
    public int getGrowthPoints() {
        return 0;
    }

    @Override
    public void setGrowthPoints(int paramInt) {

    }

    @Override
    public boolean isCrossingBase() {
        return false;
    }

    @Override
    public void setCrossingBase(boolean paramBoolean) {

    }

    @Override
    public NBTTagCompound getCustomData() {
        return null;
    }

    @Override
    public int getTerrainHumidity() {
        return 0;
    }

    @Override
    public int getTerrainNutrients() {
        return 0;
    }

    @Override
    public int getTerrainAirQuality() {
        return 0;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public BlockPos getLocation() {
        return null;
    }

    @Override
    public int getLightLevel() {
        return 0;
    }

    @Override
    public boolean pick() {
        return false;
    }

    @Override
    public boolean performManualHarvest() {
        return false;
    }

    @Override
    public List<ItemStack> performHarvest() {
        return Arrays.asList(this.getCrop().getGains(this));
    }

    @Override
    public void reset() {

    }

    @Override
    public void updateState() {

    }

    @Override
    public boolean isBlockBelow(Block paramBlock) {
        return false;
    }

    @Override
    public boolean isBlockBelow(String paramString) {
        return false;
    }

    @Override
    public ItemStack generateSeeds(CropCard paramCropCard, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        return null;
    }

    @Override
    public BlockPos getPosition() {
        return null;
    }

    @Override
    public World getWorldObj() {
        return null;
    }
}
