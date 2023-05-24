import { Center, Grid, Text } from '@mantine/core'
import { FC } from 'react'
import { PageProps } from '../../Interfaces'
import { appApi } from '../../redux/api/AppApi'
import { LabelledLoadingSpinner } from '../feedback/LabelledLoadingSpinner'
import { MiniCactpotNodeDisplay } from './MiniCactpotNodeDisplay'


export interface MiniCactpotTicketProps extends PageProps {
    ticketId?: string
}

const br = {
    md: 4
}

export const MiniCactpotTicket: FC<MiniCactpotTicketProps> = ({ extendedDisplay = true, ticketId }) => {

    const { data: ticket, isLoading: isLoadingTicket } = appApi.useGetTicketQuery({ id: ticketId! }, { skip: !ticketId });

    if (isLoadingTicket) return <LabelledLoadingSpinner text='Loading ticket' />

    if (!ticketId || !ticket) return <></>;

    return (
        <>
            <Text align='center' size='lg'> Ticket </Text>
            <Center h='100%'>
                <Grid justify='center'>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[0]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[1]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[2]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[3]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[4]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[5]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[6]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[7]} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay node={ticket.board[8]} />
                    </Grid.Col>
                </Grid>
            </Center>
        </>
    )
}
