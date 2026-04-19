package com.konkentrate.neotools.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents stat bonuses for tool upgrades.
 *
 * To add a new bonus field:
 *   1. Add a @Nullable field + fluent setter to Builder
 *   2. Add a getter (with default) to UpgradeBonus
 *   3. Add one line to CODEC group + one arg in the apply() lambda
 *   4. Add one line to combine()
 *   — No other files need to change.
 */
public final class UpgradeBonus {

    // ── Fields (stored as Optional for codec compat) ──────────────────
    private final Optional<ResourceLocation> item;
    private final Optional<ResourceLocation> tag;
    private final Optional<Float>   miningSpeedBonus;
    private final Optional<Float>   miningSpeedMultiplier;
    private final Optional<Float>   attackDamageBonus;
    private final Optional<Float>   attackDamageMultiplier;
    private final Optional<Float>   attackSpeedBonus;
    private final Optional<Integer> durabilityBonus;
    private final Optional<Float>   durabilityMultiplier;
    private final Optional<Integer> fortuneBonus;
    private final Optional<Float>   experienceMultiplier;
    private final Optional<Integer> enchantabilityBonus;
    private final Optional<Boolean> autoSmelt;

    private UpgradeBonus(Builder b) {
        this.item                 = Optional.ofNullable(b.item);
        this.tag                  = Optional.ofNullable(b.tag);
        this.miningSpeedBonus     = Optional.ofNullable(b.miningSpeedBonus);
        this.miningSpeedMultiplier= Optional.ofNullable(b.miningSpeedMultiplier);
        this.attackDamageBonus    = Optional.ofNullable(b.attackDamageBonus);
        this.attackDamageMultiplier = Optional.ofNullable(b.attackDamageMultiplier);
        this.attackSpeedBonus     = Optional.ofNullable(b.attackSpeedBonus);
        this.durabilityBonus      = Optional.ofNullable(b.durabilityBonus);
        this.durabilityMultiplier = Optional.ofNullable(b.durabilityMultiplier);
        this.fortuneBonus         = Optional.ofNullable(b.fortuneBonus);
        this.experienceMultiplier = Optional.ofNullable(b.experienceMultiplier);
        this.enchantabilityBonus  = Optional.ofNullable(b.enchantabilityBonus);
        this.autoSmelt            = Optional.ofNullable(b.autoSmelt);
    }

    public static Builder builder() { return new Builder(); }

    // ── Convenience getters with sensible defaults ─────────────────────
    public Optional<ResourceLocation> item()               { return item; }
    public Optional<ResourceLocation> tag()                { return tag; }
    public float   getMiningSpeedBonus()      { return miningSpeedBonus.orElse(0f); }
    public float   getMiningSpeedMultiplier() { return miningSpeedMultiplier.orElse(1f); }
    public float   getAttackDamageBonus()     { return attackDamageBonus.orElse(0f); }
    public float   getAttackDamageMultiplier(){ return attackDamageMultiplier.orElse(1f); }
    public float   getAttackSpeedBonus()      { return attackSpeedBonus.orElse(0f); }
    public int     getDurabilityBonus()       { return durabilityBonus.orElse(0); }
    public float   getDurabilityMultiplier()  { return durabilityMultiplier.orElse(1f); }
    public int     getFortuneBonus()          { return fortuneBonus.orElse(0); }
    public float   getExperienceMultiplier()  { return experienceMultiplier.orElse(1f); }
    public int     getEnchantabilityBonus()   { return enchantabilityBonus.orElse(0); }
    public boolean hasAutoSmelt()             { return autoSmelt.orElse(false); }

    public boolean isEmpty() {
        return getMiningSpeedBonus() == 0f && getMiningSpeedMultiplier() == 1f
            && getAttackDamageBonus() == 0f && getAttackDamageMultiplier() == 1f
            && getAttackSpeedBonus() == 0f
            && getDurabilityBonus() == 0 && getDurabilityMultiplier() == 1f
            && getFortuneBonus() == 0 && getExperienceMultiplier() == 1f
            && getEnchantabilityBonus() == 0 && !hasAutoSmelt();
    }

    // ── Singleton empty instance ───────────────────────────────────────
    public static final UpgradeBonus EMPTY = builder().build();

    // ── Codec ─────────────────────────────────────────────────────────
    public static final Codec<UpgradeBonus> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ResourceLocation.CODEC.optionalFieldOf("item").forGetter(b -> b.item),
            ResourceLocation.CODEC.optionalFieldOf("tag").forGetter(b -> b.tag),
            Codec.FLOAT.optionalFieldOf("mining_speed_bonus").forGetter(b -> b.miningSpeedBonus),
            Codec.FLOAT.optionalFieldOf("mining_speed_multiplier").forGetter(b -> b.miningSpeedMultiplier),
            Codec.FLOAT.optionalFieldOf("attack_damage_bonus").forGetter(b -> b.attackDamageBonus),
            Codec.FLOAT.optionalFieldOf("attack_damage_multiplier").forGetter(b -> b.attackDamageMultiplier),
            Codec.FLOAT.optionalFieldOf("attack_speed_bonus").forGetter(b -> b.attackSpeedBonus),
            Codec.INT.optionalFieldOf("durability_bonus").forGetter(b -> b.durabilityBonus),
            Codec.FLOAT.optionalFieldOf("durability_multiplier").forGetter(b -> b.durabilityMultiplier),
            Codec.INT.optionalFieldOf("fortune_bonus").forGetter(b -> b.fortuneBonus),
            Codec.FLOAT.optionalFieldOf("experience_multiplier").forGetter(b -> b.experienceMultiplier),
            Codec.INT.optionalFieldOf("enchantability_bonus").forGetter(b -> b.enchantabilityBonus),
            Codec.BOOL.optionalFieldOf("auto_smelt").forGetter(b -> b.autoSmelt)
        ).apply(instance, (item, tag, msb, msm, adb, adm, asb, db, dm, fb, em, eb, as_) ->
            builder()
                .itemOpt(item).tagOpt(tag)
                .miningSpeedOpt(msb).miningSpeedMultiplierOpt(msm)
                .attackDamageOpt(adb).attackDamageMultiplierOpt(adm)
                .attackSpeedOpt(asb)
                .durabilityOpt(db).durabilityMultiplierOpt(dm)
                .fortuneOpt(fb).experienceMultiplierOpt(em)
                .enchantabilityOpt(eb).autoSmeltOpt(as_)
                .build()
        )
    );

    // ── Combine two bonuses (gemstone + coating) ───────────────────────
    public UpgradeBonus combine(UpgradeBonus other) {
        return builder()
            .miningSpeedOpt(        addFloat(miningSpeedBonus,      other.miningSpeedBonus))
            .miningSpeedMultiplierOpt(mulFloat(miningSpeedMultiplier, other.miningSpeedMultiplier))
            .attackDamageOpt(       addFloat(attackDamageBonus,     other.attackDamageBonus))
            .attackDamageMultiplierOpt(mulFloat(attackDamageMultiplier, other.attackDamageMultiplier))
            .attackSpeedOpt(        addFloat(attackSpeedBonus,      other.attackSpeedBonus))
            .durabilityOpt(         addInt(durabilityBonus,         other.durabilityBonus))
            .durabilityMultiplierOpt(mulFloat(durabilityMultiplier,  other.durabilityMultiplier))
            .fortuneOpt(            addInt(fortuneBonus,            other.fortuneBonus))
            .experienceMultiplierOpt(mulFloat(experienceMultiplier,  other.experienceMultiplier))
            .enchantabilityOpt(     addInt(enchantabilityBonus,     other.enchantabilityBonus))
            .autoSmeltOpt(          orBool(autoSmelt,               other.autoSmelt))
            .build();
    }

    // ── Combine helpers ────────────────────────────────────────────────
    private static Optional<Float>   addFloat(Optional<Float> a, Optional<Float> b)   { if (a.isEmpty()) return b; if (b.isEmpty()) return a; return Optional.of(a.get() + b.get()); }
    private static Optional<Float>   mulFloat(Optional<Float> a, Optional<Float> b)   { if (a.isEmpty()) return b; if (b.isEmpty()) return a; return Optional.of(a.get() * b.get()); }
    private static Optional<Integer> addInt(Optional<Integer> a, Optional<Integer> b) { if (a.isEmpty()) return b; if (b.isEmpty()) return a; return Optional.of(a.get() + b.get()); }
    private static Optional<Boolean> orBool(Optional<Boolean> a, Optional<Boolean> b) { if (a.isEmpty()) return b; if (b.isEmpty()) return a; return Optional.of(a.get() || b.get()); }

    // ── Builder ────────────────────────────────────────────────────────
    public static final class Builder {
        private @Nullable ResourceLocation item, tag;
        private @Nullable Float   miningSpeedBonus, miningSpeedMultiplier;
        private @Nullable Float   attackDamageBonus, attackDamageMultiplier, attackSpeedBonus;
        private @Nullable Integer durabilityBonus;
        private @Nullable Float   durabilityMultiplier;
        private @Nullable Integer fortuneBonus, enchantabilityBonus;
        private @Nullable Float   experienceMultiplier;
        private @Nullable Boolean autoSmelt;

        // ── Public fluent setters ──────────────────────────────────────
        public Builder item(ResourceLocation v)           { this.item = v; return this; }
        public Builder tag(ResourceLocation v)            { this.tag = v; return this; }
        public Builder miningSpeed(float v)               { this.miningSpeedBonus = v; return this; }
        public Builder miningSpeedMultiplier(float v)     { this.miningSpeedMultiplier = v; return this; }
        public Builder attackDamage(float v)              { this.attackDamageBonus = v; return this; }
        public Builder attackDamageMultiplier(float v)    { this.attackDamageMultiplier = v; return this; }
        public Builder attackSpeed(float v)               { this.attackSpeedBonus = v; return this; }
        public Builder durability(int v)                  { this.durabilityBonus = v; return this; }
        public Builder durabilityMultiplier(float v)      { this.durabilityMultiplier = v; return this; }
        public Builder fortune(int v)                     { this.fortuneBonus = v; return this; }
        public Builder experienceMultiplier(float v)      { this.experienceMultiplier = v; return this; }
        public Builder enchantability(int v)              { this.enchantabilityBonus = v; return this; }
        public Builder autoSmelt(boolean v)               { this.autoSmelt = v; return this; }

        // ── Optional setters (used by codec + combine) ─────────────────
        Builder itemOpt(Optional<ResourceLocation> v)     { v.ifPresent(this::item); return this; }
        Builder tagOpt(Optional<ResourceLocation> v)      { v.ifPresent(this::tag); return this; }
        Builder miningSpeedOpt(Optional<Float> v)         { v.ifPresent(this::miningSpeed); return this; }
        Builder miningSpeedMultiplierOpt(Optional<Float> v){ v.ifPresent(this::miningSpeedMultiplier); return this; }
        Builder attackDamageOpt(Optional<Float> v)        { v.ifPresent(this::attackDamage); return this; }
        Builder attackDamageMultiplierOpt(Optional<Float> v){ v.ifPresent(this::attackDamageMultiplier); return this; }
        Builder attackSpeedOpt(Optional<Float> v)         { v.ifPresent(this::attackSpeed); return this; }
        Builder durabilityOpt(Optional<Integer> v)        { v.ifPresent(this::durability); return this; }
        Builder durabilityMultiplierOpt(Optional<Float> v){ v.ifPresent(this::durabilityMultiplier); return this; }
        Builder fortuneOpt(Optional<Integer> v)           { v.ifPresent(this::fortune); return this; }
        Builder experienceMultiplierOpt(Optional<Float> v){ v.ifPresent(this::experienceMultiplier); return this; }
        Builder enchantabilityOpt(Optional<Integer> v)    { v.ifPresent(this::enchantability); return this; }
        Builder autoSmeltOpt(Optional<Boolean> v)         { v.ifPresent(this::autoSmelt); return this; }

        public UpgradeBonus build() { return new UpgradeBonus(this); }
    }
}