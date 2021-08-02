import { MiniCactpotNode } from './MiniCactpotNode';

export const MiniCactpotDisplay = ({ board, scratch, canScratch }) => {

    if (board === null || board === undefined) {
        return <div> Loading... </div>
    }

    return (
        <div>
            <div className="d-flex justify-content-center">
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={0} number={board[0].number} />
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={1} number={board[1].number} />
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={2} number={board[2].number} />
            </div>
            <div className="d-flex justify-content-center">
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={3} number={board[3].number} />
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={4} number={board[4].number} />
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={5} number={board[5].number} />
            </div>
            <div className="d-flex justify-content-center">
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={6} number={board[6].number} />
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={7} number={board[7].number} />
                <MiniCactpotNode canScratch={canScratch} scratch={scratch} index={8} number={board[8].number} />
            </div>
        </div>
    )
};