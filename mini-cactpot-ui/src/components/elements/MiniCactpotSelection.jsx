

export const MiniCactpotSelection = ({ selection, makeSelection, hidden = false }) => {

    const displayArrow = () => {
        switch (selection) {
            case "BOTTOM_HORIZONTAL":
            case "MIDDLE_HORIZONTAL":
            case "TOP_HORIZONTAL":
                return <div> {">"} </div>
            case "TOP_LEFT_DIAGONAL":
                return <div style={{transform: 'rotate(45deg)'}}> {">"} </div>
            case "LEFT_VERTICAL":
            case "MIDDLE_VERTICAL":
            case "RIGHT_VERTICAL":
                return <div style={{transform: 'rotate(90deg)'}}> {">"} </div>
            case "TOP_RIGHT_DIAGONAL":
                return <div style={{transform: 'rotate(-45deg)'}}> {"<"} </div>
            default:
                return <div> {"x"} </div>
        }
    }

    const select = () => {
        makeSelection(selection);
    }

    return (
        <div onClick={select} className={`node m-2 px-3 py-2 rounded-circle selection ${hidden && "selection-hidden"}`}>
            {displayArrow()}
        </div>
    )
};