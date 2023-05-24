import { Grid, Stack, Table, Text } from '@mantine/core';
import { FC, JSX, useMemo } from 'react';
import { PageProps } from '../../Interfaces';
import { appApi } from '../../redux/api/AppApi';
import { LabelledLoadingSpinner } from '../feedback/LabelledLoadingSpinner';

export const MiniCactpotWinningsMapDisplay: FC<PageProps> = ({ extendedDisplay }) => {

    const { data: winningsMap, isFetching: isFetchingWinningsMap } = appApi.useGetWinningsMapQuery({});

    const winningsDisplay = useMemo<JSX.Element[]>(() => {
        if (!winningsMap) return [];

        return Object.entries(winningsMap).map(([key, value]) => (
            <tr key={key}>
                <td>{key}</td>
                <td> {value} </td>
            </tr>
        ));
    }, [winningsMap]);

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
