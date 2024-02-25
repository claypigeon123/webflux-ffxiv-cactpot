import { Button, Group, Stack, Text } from "@mantine/core";
import { ContextModalProps } from "@mantine/modals";
import { useMemo } from "react";
import { FaRotate } from "react-icons/fa6";
import { appApi } from "../../redux/api/AppApi";
import { LabelledLoadingSpinner } from "../feedback/LabelledLoadingSpinner";


export const TicketChooserModal = ({ context: ctx, id, innerProps: { } }: ContextModalProps<{}>) => {

    const { data: ticketsResponse, isFetching: isFetchingTickets, refetch: refetchTickets } = appApi.useQueryTicketsQuery({});

    const ticketsDisplay = useMemo(() => {
        if (!ticketsResponse) return;

        return ticketsResponse.documents.map((ticket, index) => (
            <Group key={ticket.id}>
                <Text> {ticket.id} </Text>
            </Group>
        ));
    }, [ticketsResponse]);

    if (isFetchingTickets) return (
        <LabelledLoadingSpinner text='Loading tickets' />
    );

    if (!ticketsResponse || ticketsResponse.documents.length < 1) return (
        <Stack align='center'>
            <LabelledLoadingSpinner error text='No tickets found' />
            <Button variant='light' px='sm' leftSection={<FaRotate size='18' />} onClick={refetchTickets}> Refresh </Button>
        </Stack>
    );

    return (
        <Stack align='center'>
            {ticketsDisplay}
        </Stack>
    );
}