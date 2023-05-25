import { Center, Grid, Text } from '@mantine/core';
import { FC, useMemo } from 'react';
import { MiniCactpotSelection, PageProps } from '../../Interfaces';
import { appApi } from '../../redux/api/AppApi';
import { useAppSelector } from '../../redux/util/Hooks';
import { getPositionsForSelector } from '../../util/DomainUtils';
import { MiniCactpotTicket } from '../data/MiniCactpotTicket';
import { MiniCactpotWinningsMapDisplay } from '../data/MiniCactpotWinningsMapDisplay';

export const MiniCactpotGamePage: FC<PageProps> = ({ extendedDisplay = true }) => {

    const activeTicketId = useAppSelector(state => state.app.activeTicketId);

    const { data: activeTicket, isLoading: isLoadingTicket } = appApi.useGetTicketQuery({ id: activeTicketId! }, { skip: !activeTicketId });

    const ticketDisplay = useMemo(() => {
        if (!activeTicketId || !activeTicket) return (
            <Center h='100%'>
                <Text align='center' color='dimmed'> No active ticket yet </Text>
            </Center>
        );

        return <MiniCactpotTicket extendedDisplay={extendedDisplay} ticket={activeTicket} isLoadingTicket={isLoadingTicket} />;
    }, [extendedDisplay, activeTicketId, activeTicket, isLoadingTicket]);

    const highlightKey = useMemo<string | undefined>(() => {
        if (!activeTicket || !activeTicket.selection || activeTicket.selection === MiniCactpotSelection.NONE) return;

        const positions = getPositionsForSelector(activeTicket.selection);
        const key = positions.reduce((left, right) => left + activeTicket.board[right].number, 0);

        return `${key}`;
    }, [activeTicket, activeTicket?.selection]);

    return (
        <Grid>
            <Grid.Col xs={6}>
                {ticketDisplay}
            </Grid.Col>
            <Grid.Col xs={6}>
                <MiniCactpotWinningsMapDisplay highlightKey={highlightKey} />
            </Grid.Col>
        </Grid>
    );
}
