import { Avatar, Center, Text, createStyles, useMantineTheme } from '@mantine/core'
import { FC, useMemo } from 'react'
import { FaQuestion } from 'react-icons/fa'
import { MiniCactpotGameStage, MiniCactpotPublicNode, MiniCactpotSelection, PageProps } from '../../Interfaces'
import { getPositionsForSelector } from '../../util/DomainUtils'


export interface MiniCactpotNodeDisplayProps extends PageProps {
    index: number,
    node: MiniCactpotPublicNode
    stage: MiniCactpotGameStage
    hoveringSelector?: MiniCactpotSelection
    onValidScratch: (index: number) => void
}

const useStyles = createStyles(({ colors, primaryColor }, { canScratch, index, hoveringSelector, stage }: { canScratch: boolean, index: number, hoveringSelector?: MiniCactpotSelection, stage: MiniCactpotGameStage }) => {
    const boxShadowValue = `inset 0px 0px 0px 1.5px ${colors[primaryColor][8]}`;

    let containerCircle: any = {
        cursor: canScratch ? 'pointer' : 'not-allowed',
        transition: 'all 200ms ease',
        '&:hover': {
            transform: canScratch ? 'scale(1.02)' : 'none',
            boxShadow: canScratch ? boxShadowValue : 'none'
        }
    }

    const isHoveringAffectedSelector = stage === MiniCactpotGameStage.SELECTING && getPositionsForSelector(hoveringSelector).includes(index);

    if (isHoveringAffectedSelector) {
        containerCircle = {
            ...containerCircle,
            boxShadow: boxShadowValue
        }
    }

    return {
        containerCircle,
        icon: {
            verticalAlign: 'middle'
        }
    }
});

export const MiniCactpotNodeDisplay: FC<MiniCactpotNodeDisplayProps> = ({ extendedDisplay = true, index, node, stage, hoveringSelector, onValidScratch }) => {

    const { primaryColor } = useMantineTheme();
    const canScratch = useMemo(() => {
        return node.number === -1 && (stage === MiniCactpotGameStage.SCRATCHING_FIRST || stage === MiniCactpotGameStage.SCRATCHING_SECOND || stage === MiniCactpotGameStage.SCRATCHING_THIRD)
    }, [stage]);
    const { classes } = useStyles({ canScratch, index, hoveringSelector, stage });

    const label = useMemo(() => (
        <Text color={node.number === -1 ? 'dimmed' : primaryColor}>
            {node.number === -1 ? <FaQuestion size='18' className={classes.icon} /> : node.number}
        </Text>
    ), [node]);

    const onScratch = () => {
        if (!canScratch) return;
        onValidScratch(index);
    }

    return (
        <Center>
            <Avatar className={classes.containerCircle} onClick={() => onScratch()} radius='xl' size={extendedDisplay ? 'lg' : 'md'}>
                {label}
            </Avatar>
        </Center>
    )
}

/*
<Paper className={classes.node} px='md' py='xs' radius='xl'>
                {label}
            </Paper>
 */