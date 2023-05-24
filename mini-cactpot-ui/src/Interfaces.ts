
// Generic
export interface PageProps {
    extendedDisplay?: boolean
}

export interface Pagination {
    page: number
    limit: number
    total: number
}

// Domain
export interface WinningsMap {
    6: number
    7: number
    8: number
    9: number
    10: number
    11: number
    12: number
    13: number
    14: number
    15: number
    16: number
    17: number
    18: number
    19: number
    20: number
    21: number
    22: number
    23: number
    24: number
}

export enum MiniCactpotGameStage {
    SCRATCHING_FIRST = 'SCRATCHING_FIRST',
    SCRATCHING_SECOND = 'SCRATCHING_SECOND',
    SCRATCHING_THIRD = 'SCRATCHING_THIRD',
    SELECTING = 'SELECTING',
    DONE = 'DONE'
}

export enum MiniCactpotSelection {
    NONE = 'NONE',
    BOTTOM_HORIZONTAL = 'BOTTOM_HORIZONTAL',
    MIDDLE_HORIZONTAL = 'MIDDLE_HORIZONTAL',
    TOP_HORIZONTAL = 'TOP_HORIZONTAL',
    TOP_LEFT_DIAGONAL = 'TOP_LEFT_DIAGONAL',
    LEFT_VERTICAL = 'LEFT_VERTICAL',
    MIDDLE_VERTICAL = 'MIDDLE_VERTICAL',
    RIGHT_VERTICAL = 'RIGHT_VERTICAL',
    TOP_RIGHT_DIAGONAL = 'TOP_RIGHT_DIAGONAL'
}

export interface MiniCactpotPublicNode {
    number: number
}

export interface MiniCactpotTicketDto {
    id: string
    createdDate: string
    updatedDate: string
    stage: MiniCactpotGameStage
    winnings?: number
    board: MiniCactpotPublicNode[]
}

// Request
export interface ScratchMiniCactpotNodeRequest {
    position: number
}

export interface MakeMiniCactpotSelectionRequest {
    selection: MiniCactpotSelection
}

// Response
export interface PaginatedResponse<T> {
    documents: T[]
    pagination: Pagination
}

export interface StartMiniCactpotGameResponse {
    id: string
    board: MiniCactpotPublicNode[]
    stage: MiniCactpotGameStage
}

export interface ScratchMiniCactpotNodeResponse {
    id: string
    board: MiniCactpotPublicNode[]
    stage: MiniCactpotGameStage
}

export interface MakeMiniCactpotSelectionResponse {
    id: string
    board: MiniCactpotPublicNode[]
    stage: MiniCactpotGameStage
    winnings: number
}