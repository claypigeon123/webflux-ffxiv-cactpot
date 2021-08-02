import { Container } from "react-bootstrap";
import { GameComponent } from "./components/GameComponent";
import { WinningsTable } from "./components/WinningsTable";


export const App = () => {
    return (
        <Container>
            <GameComponent />
            <WinningsTable />
        </Container>
    )
};