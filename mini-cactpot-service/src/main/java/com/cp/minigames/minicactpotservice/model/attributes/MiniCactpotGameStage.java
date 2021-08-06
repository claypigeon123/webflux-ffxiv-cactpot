package com.cp.minigames.minicactpotservice.model.attributes;

public enum MiniCactpotGameStage {
    SCRATCHING_FIRST(0),
    SCRATCHING_SECOND(1),
    SCRATCHING_THIRD(2),
    SELECTING(3),
    DONE(4);

    MiniCactpotGameStage(int index) {
        this.index = index;
    }

    private final int index;

    public int getIndex() {
        return index;
    }

    public MiniCactpotGameStage advance() {
        int current = getIndex();
        if (current == DONE.getIndex()) {
            return this;
        }

        return MiniCactpotGameStage.values()[current + 1];
    }
}