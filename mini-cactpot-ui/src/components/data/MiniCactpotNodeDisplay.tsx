import { Avatar, Center, MantineStyleProp, MantineTheme, Text, useMantineTheme } from '@mantine/core'
import { FC, useMemo } from 'react'
import { FaQuestion } from 'react-icons/fa6'
import { MiniCactpotGameStage, MiniCactpotSelection, MiniCactpotTicketDto, PageProps } from '../../Interfaces'
import { getPositionsForSelector } from '../../util/DomainUtils'
import classes from './MiniCactpotNodeDisplay.module.css'


export interface MiniCactpotNodeDisplayProps extends PageProps {
    ticket: MiniCactpotTicketDto
    index: number
    hoveringSelector?: MiniCactpotSelection
    onValidScratch: (index: number) => void
}

const useStyles = ({ colors, primaryColor }: MantineTheme, { canScratch, index, hoveringSelector, stage, selection }: { canScratch: boolean, index: number, hoveringSelector?: MiniCactpotSelection, stage: MiniCactpotGameStage, selection: MiniCactpotSelection }) => {
    const boxShadowValue = `inset 0px 0px 0px 1.5px ${colors[primaryColor][8]}`;

    let containerCircle: MantineStyleProp = {
        cursor: canScratch ? 'pointer' : 'not-allowed',
        transition: 'all 200ms ease'
    }

    const isHoveringAffectedSelector = stage === MiniCactpotGameStage.SELECTING && getPositionsForSelector(hoveringSelector).includes(index);
    const hasBeenSelected = stage === MiniCactpotGameStage.DONE && getPositionsForSelector(selection).includes(index);
    if (isHoveringAffectedSelector || hasBeenSelected) {
        containerCircle = {
            ...containerCircle,
            boxShadow: boxShadowValue
        }
    }

    return {
        styles: {
            containerCircle,
            icon: {
                verticalAlign: 'middle'
            }
        }
    }
};

export const MiniCactpotNodeDisplay: FC<MiniCactpotNodeDisplayProps> = ({ extendedDisplay = true, ticket, index, hoveringSelector, onValidScratch }) => {

    const { colors, primaryColor, ...others } = useMantineTheme();

    const node = useMemo(() => {
        return ticket.board[index];
    }, [ticket, index]);

    const canScratch = useMemo(() => {
        return node.number === -1 && (ticket.stage === MiniCactpotGameStage.SCRATCHING_FIRST || ticket.stage === MiniCactpotGameStage.SCRATCHING_SECOND || ticket.stage === MiniCactpotGameStage.SCRATCHING_THIRD)
    }, [node.number, ticket.stage]);

    const { styles } = useStyles({ colors, primaryColor, ...others }, { canScratch, index, hoveringSelector, stage: ticket.stage, selection: ticket.selection });

    const label = useMemo(() => (
        <Text c={node.number === -1 ? 'dimmed' : primaryColor}>
            {node.number === -1 ? <FaQuestion size='18' style={styles.icon} /> : node.number}
        </Text>
    ), [node]);

    const onScratch = () => {
        if (!canScratch) return;
        onValidScratch(index);
    }

    return (
        <Center>
            <Avatar style={styles.containerCircle} className={canScratch ? classes.containerCircle : ''} onClick={() => onScratch()} radius='xl' size={extendedDisplay ? 'lg' : 'md'}>
                {label}
            </Avatar>
        </Center>
    )
}