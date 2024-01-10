import { Grid, Stack, Table, Text } from '@mantine/core';
import { FC, JSX, useMemo } from 'react';
import { appApi } from '../../redux/api/AppApi';
import { mgpFormat } from '../../util/DomainUtils';
import { LabelledLoadingSpinner } from '../feedback/LabelledLoadingSpinner';
import classes from './MiniCactpotWinningsMapDisplay.module.css';


export interface MiniCactpotWinningsMapDisplayProps {
    highlightKey?: string
}

export const MiniCactpotWinningsMapDisplay: FC<MiniCactpotWinningsMapDisplayProps> = ({ highlightKey }) => {

    const { data: winningsMap, isFetching: isFetchingWinningsMap } = appApi.useGetWinningsMapQuery({});

    const winningsDisplay = useMemo<JSX.Element[]>(() => {
        if (!winningsMap) return [];

        return Object.entries(winningsMap).map(([key, value]) => (
            <Table.Tr key={key} className={highlightKey === key ? classes.highlightedRow : ''}>
                <Table.Td>{key}</Table.Td>
                <Table.Td> {mgpFormat.format(value)} </Table.Td>
            </Table.Tr>
        ));
    }, [winningsMap, highlightKey]);

    if (isFetchingWinningsMap) return <LabelledLoadingSpinner text='Getting Winnings Table' />

    return (
        <Stack>
            <Text ta='center' size='lg'> Payout </Text>
            <Grid gutter='xs'>
                <Grid.Col span={6}>
                    <Table fz='xs' verticalSpacing={0}>
                        <Table.Thead>
                            <Table.Tr>
                                <Table.Th> Sum </Table.Th>
                                <Table.Th> MGP </Table.Th>
                            </Table.Tr>
                        </Table.Thead>
                        <Table.Tbody>
                            {winningsDisplay.slice(0, 10)}
                        </Table.Tbody>
                    </Table>
                </Grid.Col>
                <Grid.Col span={6}>
                    <Table fz='xs' verticalSpacing={0}>
                        <Table.Thead>
                            <Table.Tr>
                                <Table.Th> Sum </Table.Th>
                                <Table.Th> MGP </Table.Th>
                            </Table.Tr>
                        </Table.Thead>
                        <Table.Tbody>
                            {winningsDisplay.slice(10)}
                        </Table.Tbody>
                    </Table>
                </Grid.Col>
            </Grid>
        </Stack>
    )
}
