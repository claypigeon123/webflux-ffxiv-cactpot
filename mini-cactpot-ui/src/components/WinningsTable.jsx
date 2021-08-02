import { useState, useEffect } from "react";
import { FaCoins } from "react-icons/fa";
import { Table, Row, Col } from "react-bootstrap";
import axios from "axios";

import { ContentBox } from "./elements/ContentBox";

export const WinningsTable = () => {

    const [winningsMap, setWinningsMap] = useState({});

    useEffect(() => {
        axios.get("/api/mini-cactpot/winnings").then(res => {
            setWinningsMap(res.data);
        });
    }, []);

    const renderTableRows = () => {
        return Object.keys(winningsMap).map((key, index) => (
            <tr key={index}>
                <td> {key} </td>
                <td> {winningsMap[key]} </td>
            </tr>
        ));
    }

    return (
        <ContentBox title="Winnings Table" icon={<FaCoins />}>
            <Row className="justify-content-center">
                <Col lg="8">
                    <Table size="sm" responsive striped hover variant="dark">
                        <thead>
                            <tr>
                                <th> Selection Sum </th>
                                <th> Payout </th>
                            </tr>
                        </thead>
                        <tbody>
                            { renderTableRows() }
                        </tbody>
                    </Table>
                </Col>
            </Row>
        </ContentBox>
    )
};