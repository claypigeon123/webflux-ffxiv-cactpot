import { Grid, Stack, Table, Text, createStyles } from '@mantine/core';
import { FC, JSX, useMemo } from 'react';
import { appApi } from '../../redux/api/AppApi';
import { mgpFormat } from '../../util/DomainUtils';
import { LabelledLoadingSpinner } from '../feedback/LabelledLoadingSpinner';


export interface MiniCactpotWinningsMapDisplayProps {
    highlightKey?: string
}

const useStyles = createStyles(({ primaryColor, colors, fn: { rgba } }) => ({
    highlightedRow: {
        background: `${rgba(colors[primaryColor][9], 0.4)}`
    }
}));

export const MiniCactpotWinningsMapDisplay: FC<MiniCactpotWinningsMapDisplayProps> = ({ highlightKey }) => {

    const { classes } = useStyles();
    const { data: winningsMap, isFetching: isFetchingWinningsMap } = appApi.useGetWinningsMapQuery({});

    const winningsDisplay = useMemo<JSX.Element[]>(() => {
        if (!winningsMap) return [];

        return Object.entries(winningsMap).map(([key, value]) => (
            <tr key={key} className={highlightKey === key ? classes.highlightedRow : ''}>
                <td>{key}</td>
                <td> {mgpFormat.format(value)} </td>
            </tr>
        ));
    }, [winningsMap, highlightKey]);

    if (isFetchingWinningsMap) return <LabelledLoadingSpinner text='Getting Winnings Table' />

    return (
        <Stack>
            <Text align='center' size='lg'> Payout </Text>
            <Grid gutter='xs'>
                <Grid.Col span={6}>
                    <Table fontSize='xs' verticalSpacing={0}>
                        <thead>
                            <tr>
                                <th> Sum </th>
                                <th> MGP </th>
                            </tr>
                        </thead>
                        <tbody>
                            {winningsDisplay.slice(0, 10)}
                        </tbody>
                    </Table>
                </Grid.Col>
                <Grid.Col span={6}>
                    <Table fontSize='xs' verticalSpacing={0}>
                        <thead>
                            <tr>
                                <th> Sum </th>
                                <th> MGP </th>
                            </tr>
                        </thead>
                        <tbody>
                            {winningsDisplay.slice(10)}
                        </tbody>
                    </Table>
                </Grid.Col>
            </Grid>
        </Stack>
    )
}
