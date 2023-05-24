import { Center, Paper, Text, useMantineTheme } from '@mantine/core'
import { FC, useMemo } from 'react'
import { MiniCactpotPublicNode } from '../../Interfaces'


export interface MiniCactpotNodeDisplayProps {
    node: MiniCactpotPublicNode
}

export const MiniCactpotNodeDisplay: FC<MiniCactpotNodeDisplayProps> = ({ node }) => {

    const { primaryColor } = useMantineTheme();

    const label = useMemo(() => (
        <Text color={node.number === -1 ? 'dimmed' : primaryColor}>
            {node.number === -1 ? '?' : node.number}
        </Text>
    ), [node]);

    return (
        <Center>
            <Paper px='md' py='xs'>
                {label}
            </Paper>
        </Center>
    )
}
