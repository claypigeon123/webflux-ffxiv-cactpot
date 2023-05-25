import { AppShell, Box, Button, Center, Container, Group, Stack, Text, createStyles } from '@mantine/core';
import { FC, ReactElement } from 'react';
import { FaTicketAlt } from 'react-icons/fa';
import { PageProps } from '../../Interfaces';
import { appApi } from '../../redux/api/AppApi';
import { appSlice } from '../../redux/slice/AppSlice';
import { useAppDispatch } from '../../redux/util/Hooks';
import { displayGenericErrorNotification, displaySuccessNotification } from '../../util/NotificationUtils';


export interface LayoutProps extends PageProps {
    children?: ReactElement
}

const useStyles = createStyles(({ colors }, { extendedDisplay = true }: { extendedDisplay: boolean }) => ({
    main: {
        background: 'linear-gradient(to right top, #1c1f59, #162052, #12204b, #102043, #111f3b, #171c34, #1a192d, #1b1626, #1d121e, #1b0d16, #18090e, #120505)',
        paddingLeft: !extendedDisplay ? '0 !important' : undefined,
        paddingRight: !extendedDisplay ? '0 !important' : undefined,
        paddingBottom: 0,
        paddingTop: 0
    },
    header: {
        background: colors['dark'][8],
        border: `1px solid ${colors['dark'][6]}`,
        borderRadius: extendedDisplay ? '1rem 1rem 0 0' : 'none',
    },
    content: {
        background: colors['dark'][9],
        borderLeft: `1px solid ${colors['dark'][6]}`,
        borderBottom: `1px solid ${colors['dark'][6]}`,
        borderRight: `1px solid ${colors['dark'][6]}`,
    }
}))

export const Layout: FC<LayoutProps> = ({ extendedDisplay = true, children }) => {

    const { classes } = useStyles({ extendedDisplay });
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
            <Center h='100vh'>
                <Container size='sm' w='100%' px={0}>
                    <Stack align='stretch' spacing={0}>
                        <Group py='sm' px='lg' className={classes.header}>
                            <Text size='xl'> Mini Cactpot Game </Text>
                            <Button
                                ml={extendedDisplay ? 'auto' : 0}
                                size='xs'
                                px='sm'
                                variant='light'
                                loading={isStartingNewGame}
                                leftIcon={<FaTicketAlt size='16' />}
                                onClick={() => onStartNewGame()}
                            >
                                <Text> Request New Ticket </Text>
                            </Button>
                        </Group>
                        <Box py='sm' px='lg' className={classes.content}>
                            {children}
                        </Box>
                    </Stack>
                </Container>
            </Center>
        </AppShell>
    )
}
