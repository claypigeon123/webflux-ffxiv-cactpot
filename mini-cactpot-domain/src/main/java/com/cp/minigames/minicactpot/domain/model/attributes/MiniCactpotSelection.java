package com.cp.minigames.minicactpot.domain.model.attributes;

public enum MiniCactpotSelection {
    NONE(null),
    BOTTOM_HORIZONTAL(new int[]{6, 7, 8}),
    MIDDLE_HORIZONTAL(new int[]{3, 4, 5}),
    TOP_HORIZONTAL(new int[]{0, 1, 2}),
    TOP_LEFT_DIAGONAL(new int[]{0, 4, 8}),

    LEFT_VERTICAL(new int[]{0, 3, 6}),
    MIDDLE_VERTICAL(new int[]{1, 4, 7}),
    RIGHT_VERTICAL(new int[]{2, 5, 8}),
    TOP_RIGHT_DIAGONAL(new int[]{2, 4, 6});

    private final int[] positions;

    MiniCactpotSelection(int[] positions) {
        this.positions = positions;
    }

    public int[] getPositions() {
        return positions;
    }
}
