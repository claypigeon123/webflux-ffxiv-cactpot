import { Avatar, Center, createStyles } from '@mantine/core'
import { FC, useMemo } from 'react'
import { FaArrowDown, FaArrowRight } from 'react-icons/fa'
import { MiniCactpotGameStage, MiniCactpotSelection } from '../../Interfaces'


export interface SelectionButtonProps {
    type: MiniCactpotSelection
    stage: MiniCactpotGameStage
    setHoveringSelection: (selection?: MiniCactpotSelection) => void
    select: (selection: MiniCactpotSelection) => void
}

const useStyles = createStyles(({ colors, primaryColor }, { canSelect, stage }: { canSelect: boolean, stage: MiniCactpotGameStage }) => {

    let containerCircle: any = {
        cursor: canSelect ? 'pointer' : 'not-allowed',
        transition: 'all 200ms ease',
        '&:hover': {
            backgroundColor: canSelect ? `${colors[primaryColor][9]}` : 'none'
        }
    }

    if (stage !== MiniCactpotGameStage.SELECTING) {
        delete containerCircle['cursor'];
    }

    return {
        container: {
            transition: 'all 200ms ease',
            opacity: canSelect ? '100%' : '0'
        },
        containerCircle,
        icon: {
            verticalAlign: 'middle'
        }
    }
});

export const SelectionButton: FC<SelectionButtonProps> = ({ type, stage, setHoveringSelection, select }) => {

    const canSelect = useMemo(() => {
        return stage === MiniCactpotGameStage.SELECTING
    }, [stage]);

    const { classes } = useStyles({ canSelect, stage });

    const icon = useMemo(() => {
        switch (type) {
            case MiniCactpotSelection.BOTTOM_HORIZONTAL:
            case MiniCactpotSelection.MIDDLE_HORIZONTAL:
            case MiniCactpotSelection.TOP_HORIZONTAL:
                return <FaArrowRight className={classes.icon} />
            case MiniCactpotSelection.TOP_LEFT_DIAGONAL:
                return <FaArrowRight className={classes.icon} style={{ transform: 'rotate(45deg)' }} />
            case MiniCactpotSelection.LEFT_VERTICAL:
            case MiniCactpotSelection.MIDDLE_VERTICAL:
            case MiniCactpotSelection.RIGHT_VERTICAL:
                return <FaArrowDown className={classes.icon} />
            case MiniCactpotSelection.TOP_RIGHT_DIAGONAL:
                return <FaArrowDown className={classes.icon} style={{ transform: 'rotate(45deg)' }} />
        }
    }, [type])

    return (
        <Center className={classes.container} h='100%'>
            <Avatar
                className={classes.containerCircle}
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
