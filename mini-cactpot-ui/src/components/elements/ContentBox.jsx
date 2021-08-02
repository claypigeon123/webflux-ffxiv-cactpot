import { Container } from 'react-bootstrap';

import { Title } from "./Title";

export const ContentBox = ({ title, icon, children }) => {
    return (
        <Container fluid className="p-3 rounded bg-dark shadow my-4">
            <Title title={title} icon={icon} />
            { children }
        </Container>
    )
};