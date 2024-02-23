import { AppShell, Box, Button, Center, Container, Group, Stack, Text } from '@mantine/core';
import { FC, ReactElement } from 'react';
import { FaBoxOpen, FaTicket } from 'react-icons/fa6';
import { PageProps } from '../../Interfaces';
import { appApi } from '../../redux/api/AppApi';
import { appSlice } from '../../redux/slice/AppSlice';
import { useAppDispatch } from '../../redux/util/Hooks';
import { displayGenericErrorNotification, displaySuccessNotification } from '../../util/NotificationUtils';
import { openTicketChooserModal } from '../modals/TicketChooserModal';
import classes from './Layout.module.css';


export interface LayoutProps extends PageProps {
    children?: ReactElement
}

export const Layout: FC<LayoutProps> = ({ extendedDisplay = true, children }) => {

    const dispatch = useAppDispatch();

    const [startNewGame, { isLoading: isStartingNewGame }] = appApi.useStartNewGameMutation();

    const onStartNewGame = async () => {
        try {
            const res = await startNewGame({}).unwrap();
            dispatch(appSlice.actions.changeActiveTicket(res.id));
            displaySuccessNotification('Game Started', 'A new mini cactpot game has been started');
        } catch (err) {
            displayGenericErrorNotification(err);
        }
    }

    return (
        <AppShell classNames={{ main: classes.main }}>
            <AppShell.Main>
                <Center h='100vh'>
                    <Container size='sm' w='100%' px={0}>
                        <Stack align='stretch' gap={0}>
                            <Group py='sm' px='lg' className={classes.header}>
                                <Text size='xl'> Mini Cactpot Game </Text>
                                <Button
                                    ml={extendedDisplay ? 'auto' : 0}
                                    size='xs'
                                    px='sm'
                                    variant='light'
                                    leftSection={<FaBoxOpen size='16' />}
                                    onClick={() => openTicketChooserModal()}
                                >
                                    My Tickets
                                </Button>
                                <Button
                                    size='xs'
                                    px='sm'
                                    variant='light'
                                    loading={isStartingNewGame}
                                    leftSection={<FaTicket size='16' />}
                                    onClick={() => onStartNewGame()}
                                >
                                    New Ticket
                                </Button>
                            </Group>
                            <Box py='sm' px='lg' className={classes.content}>
                                {children}
                            </Box>
                        </Stack>
                    </Container>
                </Center>
            </AppShell.Main>
        </AppShell>
    )
}
