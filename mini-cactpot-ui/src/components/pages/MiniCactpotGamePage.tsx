import { FC, useMemo } from 'react';
import { PageProps } from '../../Interfaces';
import { Box, Grid, SimpleGrid, Text } from '@mantine/core';
import { useAppSelector } from '../../redux/util/Hooks';
import { MiniCactpotTicket } from '../data/MiniCactpotTicket';
import { MiniCactpotWinningsMapDisplay } from '../data/MiniCactpotWinningsMapDisplay';

export const MiniCactpotGamePage: FC<PageProps> = ({ extendedDisplay = true }) => {

    const activeTicketId = useAppSelector(state => state.app.activeTicketId);

    const ticketDisplay = useMemo(() => {
        if (!activeTicketId) return <Text align='center' color='dimmed'> No active ticket yet </Text>;

        return <MiniCactpotTicket extendedDisplay={extendedDisplay} ticketId={activeTicketId} />;
    }, [extendedDisplay, activeTicketId]);

    return (
        <Grid>
            <Grid.Col span={6}>
                {ticketDisplay}
            </Grid.Col>
            <Grid.Col span={6}>
                <MiniCactpotWinningsMapDisplay />
            </Grid.Col>
        </Grid>
    );
}
