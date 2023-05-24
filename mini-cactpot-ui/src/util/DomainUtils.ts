import { MiniCactpotSelection } from "../Interfaces";


export const getPositionsForSelector = (selection?: MiniCactpotSelection): [number, number, number] => {
    switch (selection) {
        case MiniCactpotSelection.BOTTOM_HORIZONTAL: return [6, 7, 8]
        case MiniCactpotSelection.MIDDLE_HORIZONTAL: return [3, 4, 5]
        case MiniCactpotSelection.TOP_HORIZONTAL: return [0, 1, 2]

        case MiniCactpotSelection.TOP_LEFT_DIAGONAL: return [0, 4, 8]

        case MiniCactpotSelection.LEFT_VERTICAL: return [0, 3, 6]
        case MiniCactpotSelection.MIDDLE_VERTICAL: return [1, 4, 7]
        case MiniCactpotSelection.RIGHT_VERTICAL: return [2, 5, 8]

        case MiniCactpotSelection.TOP_RIGHT_DIAGONAL: return [2, 4, 6]

        default: return [-1, -1, -1];
    }
}

export const mgpFormat = Intl.NumberFormat('en-GB', { maximumFractionDigits: 0 });