import { Button } from "react-bootstrap";
import { useState } from "react";
import { FaTicketAlt } from "react-icons/fa";

import { ContentBox } from "./elements/ContentBox";
import axios from "axios";
import { MiniCactpotDisplay } from "./elements/MiniCactpotDisplay";

export const GameComponent = () => {

    const [gameStarted, setGameStarted] = useState(false);

    const [gameId, setGameId] = useState(undefined);
    const [board, setBoard] = useState({});
    const [nScratch, setNScratch] = useState(0);
    const [winnings, setWinnings] = useState(0);

    const startGame = () => {
        axios.post("/api/mini-cactpot/start-new-game").then(res => {
            setGameId(res.data["id"]);
            setBoard(res.data["board"]);
            setGameStarted(true);
        }).catch(err => {
            console.log(err.response?.data);
        });
    }

    const scratch = (index) => {
        const data = {
            id: gameId,
            position: index + 1
        };
        axios.post("/api/mini-cactpot/scratch", data).then(res => {
            setBoard(res.data["board"]);
            setNScratch(nScratch + 1);
        }).catch(err => {
            console.log(err.response?.data);
        });
    }

    const makeSelection = (selection) => {
        const data = {
            id: gameId,
            selection: selection
        };
        axios.post("/api/mini-cactpot/make-selection", data).then(res => {
            setBoard(res.data["board"]);
            setWinnings(res.data.winnings);
        }).catch(err => {
            console.log(err.response?.data);
        });
    }

    const renderGame = () => {
        if (!gameStarted) {
            return (
                <Button onClick={startGame} variant="outline-light"> Start game </Button>
            );
        }

        return <MiniCactpotDisplay board={board} scratch={scratch} canScratch={nScratch < 3} makeSelection={makeSelection} />
    }

    return (
        <ContentBox title="Mini Cactpot Game" icon={<FaTicketAlt />}>
            <div className="text-center">
                { renderGame() }
            </div>
        </ContentBox>
    )
};