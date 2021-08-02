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

    const startGame = () => {
        axios.post("/api/mini-cactpot/start-new-game").then(res => {
            setGameId(res.data["id"]);
            setBoard(res.data["board"]["board"]);
            setGameStarted(true);
        });
    }

    const scratch = (index) => {
        const data = {
            id: gameId,
            position: index + 1
        };
        axios.post("/api/mini-cactpot/scratch", data, {headers: { "Content-Type": "application/json" }}).then(res => {
            setBoard(res.data["board"]["board"]);
            setNScratch(nScratch + 1);
        }).catch(err => {
            console.log(err.response?.data);
        })
    }

    const renderGame = () => {
        if (!gameStarted) {
            return (
                <Button onClick={startGame} variant="outline-light"> Start game </Button>
            );
        }

        return <MiniCactpotDisplay board={board} scratch={scratch} canScratch={nScratch < 3} />
    }

    return (
        <ContentBox title="Mini Cactpot Game" icon={<FaTicketAlt />}>
            <div className="text-center">
                { renderGame() }
            </div>
        </ContentBox>
    )
};