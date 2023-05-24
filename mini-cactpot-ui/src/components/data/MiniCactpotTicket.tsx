import { Center, Grid, Overlay, Text } from '@mantine/core'
import { FC, useState } from 'react'
import { MiniCactpotGameStage, MiniCactpotSelection, PageProps } from '../../Interfaces'
import { appApi } from '../../redux/api/AppApi'
import { displayGenericErrorNotification } from '../../util/NotificationUtils'
import { LabelledLoadingSpinner } from '../feedback/LabelledLoadingSpinner'
import { MiniCactpotNodeDisplay } from './MiniCactpotNodeDisplay'
import { SelectionButton } from './SelectionButton'


export interface MiniCactpotTicketProps extends PageProps {
    ticketId?: string
}

const br = {
    xs: 2.01
}

export const MiniCactpotTicket: FC<MiniCactpotTicketProps> = ({ extendedDisplay = true, ticketId }) => {

    const { data: ticket, isLoading: isLoadingTicket } = appApi.useGetTicketQuery({ id: ticketId! }, { skip: !ticketId });
    const [scratch] = appApi.useScratchMutation();
    const [makeSelection] = appApi.useMakeSelectionMutation();

    const [hoveringSelector, setHoveringSelector] = useState<MiniCactpotSelection | undefined>();

    const onValidScratch = async (index: number) => {
        if (!ticket) return;

        try {
            await scratch({ id: ticket.id, request: { position: index + 1 } }).unwrap();
        } catch (err) {
            displayGenericErrorNotification();
        }

    }

    const onMakeSelection = async (selection: MiniCactpotSelection) => {
        if (!ticket) return;

        try {
            await makeSelection({ id: ticket.id, request: { selection } }).unwrap();
        } catch (err) {
            displayGenericErrorNotification();
        }
    }

    if (isLoadingTicket) return <LabelledLoadingSpinner text='Loading ticket' />

    if (!ticketId || !ticket) return <></>;

    return (
        <>
            <Text align='center' size='lg'> Ticket </Text>
            <Center style={{ position: 'relative' }}>
                {ticket.stage === MiniCactpotGameStage.DONE &&
                    <Overlay center opacity={0.4} blur={1} radius='lg'>
                        <Text align='center' color='green' weight={600}> Won {ticket.winnings} MGP </Text>
                    </Overlay>
                }
                <Grid justify='center' mb='xl'>
                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.TOP_LEFT_DIAGONAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.LEFT_VERTICAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.MIDDLE_VERTICAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.RIGHT_VERTICAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.TOP_RIGHT_DIAGONAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>


                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.TOP_HORIZONTAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={0} node={ticket.board[0]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={1} node={ticket.board[1]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={2} node={ticket.board[2]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br} />


                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.MIDDLE_HORIZONTAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={3} node={ticket.board[3]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={4} node={ticket.board[4]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={5} node={ticket.board[5]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br} />


                    <Grid.Col {...br}>
                        <SelectionButton type={MiniCactpotSelection.BOTTOM_HORIZONTAL} stage={ticket.stage} setHoveringSelection={setHoveringSelector} select={onMakeSelection} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={6} node={ticket.board[6]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={7} node={ticket.board[7]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br}>
                        <MiniCactpotNodeDisplay extendedDisplay={extendedDisplay} index={8} node={ticket.board[8]} stage={ticket.stage} hoveringSelector={hoveringSelector} onValidScratch={onValidScratch} />
                    </Grid.Col>
                    <Grid.Col {...br} />
                </Grid>
            </Center>
        </>
    )
}
