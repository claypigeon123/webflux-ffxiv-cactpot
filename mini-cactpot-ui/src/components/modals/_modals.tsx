import { Group, Title, rem } from "@mantine/core";
import { openContextModal } from "@mantine/modals";
import { FaBoxOpen } from "react-icons/fa6";

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