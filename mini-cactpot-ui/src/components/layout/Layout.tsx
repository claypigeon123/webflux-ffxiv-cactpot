import { AppShell, Box, Button, Center, Container, Group, Stack, Text, createStyles, useMantineTheme } from '@mantine/core';
import { useMediaQuery } from '@mantine/hooks';
import { FC, ReactElement } from 'react';
import { FaFile, FaTicketAlt } from 'react-icons/fa';


export interface LayoutProps {
    children?: ReactElement
}

const useStyles = createStyles(({ colors }, { matches }: { matches: boolean }) => ({
    main: {
        background: 'linear-gradient(to right top, #1c1f59, #162052, #12204b, #102043, #111f3b, #171c34, #1a192d, #1b1626, #1d121e, #1b0d16, #18090e, #120505)',
        paddingLeft: !matches ? '0 !important' : undefined,
        paddingRight: !matches ? '0 !important' : undefined,
        paddingBottom: 0,
        paddingTop: 0
    },
    header: {
        background: colors['dark'][8],
        border: `1px solid ${colors['dark'][6]}`,
        borderRadius: '1rem 1rem 0 0',
    },
    content: {
        background: colors['dark'][9],
        borderLeft: `1px solid ${colors['dark'][6]}`,
        borderBottom: `1px solid ${colors['dark'][6]}`,
        borderRight: `1px solid ${colors['dark'][6]}`,
    }
}))

export const Layout: FC<LayoutProps> = ({ children }) => {

    const { breakpoints, colors } = useMantineTheme();
    const matches = useMediaQuery(`(min-width: ${breakpoints.md})`);
    const { classes } = useStyles({ matches });

    return (
        <AppShell classNames={{ main: classes.main }}>
            <Center h='100vh'>
                <Container size='md' w='100%'>
                    <Stack align='stretch' spacing={0}>
                        <Group py='sm' px='lg' className={classes.header}>
                            <Text size='xl'> Mini Cactpot Game </Text>
                            <Button ml='auto' size='xs' px='sm' variant='light' leftIcon={<FaTicketAlt size='16' />}> Request New Ticket </Button>
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
