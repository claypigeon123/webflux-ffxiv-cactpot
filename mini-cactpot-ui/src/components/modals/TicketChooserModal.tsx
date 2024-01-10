import { Button, Group, Loader, Stack, Text, Title, rem, useMantineTheme } from "@mantine/core";
import { ContextModalProps, openContextModal } from "@mantine/modals";
import { useMemo } from "react";
import { FaBoxOpen, FaSync } from "react-icons/fa";
import { appApi } from "../../redux/api/AppApi";


export const TICKET_CHOOSER_MODAL_NAME = 'ticketChooserModal';

export const openTicketChooserModal = () => {
    openContextModal({
        modal: TICKET_CHOOSER_MODAL_NAME,
        title: (
            <Group gap={rem(10)}>
                <FaBoxOpen size='24' style={{ verticalAlign: 'middle' }} />
                <Title order={4}> My Tickets </Title>
            </Group>
        ),
        innerProps: {}
    });
}

export const TicketChooserModal = ({ context: ctx, id, innerProps: { } }: ContextModalProps<{}>) => {

    const { primaryColor } = useMantineTheme();
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
        <Stack align='center' gap={rem(5)}>
            <Loader />
            <Text c={primaryColor} fw={500}> Loading tickets </Text>
        </Stack>
    );

    if (!ticketsResponse || ticketsResponse.documents.length < 1) return (
        <Stack align='center'>
            <Text c='dimmed'> No tickets found. </Text>
            <Button variant='light' px='sm' leftSection={<FaSync size='18' />} onClick={refetchTickets}> Refresh </Button>
        </Stack>
    );

    return (
        <Stack align='center'>
            {ticketsDisplay}
        </Stack>
    );
}