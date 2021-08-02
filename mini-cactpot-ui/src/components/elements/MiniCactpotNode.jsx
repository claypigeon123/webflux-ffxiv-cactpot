

export const MiniCactpotNode = ({ index, number, canScratch = false, scratch }) => {

    const scratchNode = () => {
        if (!canScratch) {
            return;
        }

        scratch(index);
    }

    return (
        <div onClick={scratchNode} className={`node m-2 px-3 py-2 rounded-circle ${number === -1 ? "node-hidden" : "node-revealed"}`}>
            {number === -1 ? "x" : number}
        </div>
    )
};