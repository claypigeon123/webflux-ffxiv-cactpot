import { Avatar, Center, MantineTheme, useMantineTheme } from '@mantine/core'
import { FC, useMemo } from 'react'
import { FaArrowDown, FaArrowRight } from 'react-icons/fa'
import { MiniCactpotGameStage, MiniCactpotSelection } from '../../Interfaces'
import classes from './SelectionButton.module.css';


export interface SelectionButtonProps {
    type: MiniCactpotSelection
    stage: MiniCactpotGameStage
    setHoveringSelection: (selection?: MiniCactpotSelection) => void
    select: (selection: MiniCactpotSelection) => void
}

const useStyles = ({ colors, primaryColor }: MantineTheme, { canSelect, stage }: { canSelect: boolean, stage: MiniCactpotGameStage }) => {

    let containerCircle: any = {
        cursor: canSelect ? 'pointer' : 'not-allowed',
        transition: 'all 200ms ease'
    }

    if (stage !== MiniCactpotGameStage.SELECTING) {
        delete containerCircle['cursor'];
    }

    return {
        styles: {
            container: {
                transition: 'all 200ms ease',
                opacity: canSelect ? '100%' : '0'
            },
            containerCircle,
            icon: {
                verticalAlign: 'middle'
            }
        }
    }
};

export const SelectionButton: FC<SelectionButtonProps> = ({ type, stage, setHoveringSelection, select }) => {

    const theme = useMantineTheme();

    const canSelect = useMemo(() => {
        return stage === MiniCactpotGameStage.SELECTING
    }, [stage]);

    const { styles } = useStyles(theme, { canSelect, stage });

    const icon = useMemo(() => {
        switch (type) {
            case MiniCactpotSelection.BOTTOM_HORIZONTAL:
            case MiniCactpotSelection.MIDDLE_HORIZONTAL:
            case MiniCactpotSelection.TOP_HORIZONTAL:
                return <FaArrowRight className={styles.icon} />
            case MiniCactpotSelection.TOP_LEFT_DIAGONAL:
                return <FaArrowRight className={styles.icon} style={{ transform: 'rotate(45deg)' }} />
            case MiniCactpotSelection.LEFT_VERTICAL:
            case MiniCactpotSelection.MIDDLE_VERTICAL:
            case MiniCactpotSelection.RIGHT_VERTICAL:
                return <FaArrowDown className={styles.icon} />
            case MiniCactpotSelection.TOP_RIGHT_DIAGONAL:
                return <FaArrowDown className={styles.icon} style={{ transform: 'rotate(45deg)' }} />
        }
    }, [type])

    return (
        <Center style={styles.container} h='100%'>
            <Avatar
                style={styles.containerCircle}
                className={canSelect ? classes.containerCircle : ''}
                variant='outline'
                radius='xl'
                onClick={() => select(type)}
                onMouseEnter={() => setHoveringSelection(type)}
                onMouseLeave={() => setHoveringSelection(undefined)}
            >
                {icon}
            </Avatar>
        </Center>
    )
}
