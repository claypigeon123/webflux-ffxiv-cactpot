import { Avatar, Loader, Stack, Text, useMantineTheme } from "@mantine/core";
import { FC } from "react";
import { FaCheck, FaExclamation } from "react-icons/fa6";

export interface LabelledLoadingSpinnerProps {
    text?: string
    success?: boolean
    error?: boolean
}

export const LabelledLoadingSpinner: FC<LabelledLoadingSpinnerProps> = ({ text = "Loading", success = false, error = false }) => {

    const { primaryColor, colors } = useMantineTheme();

    if (error) return (
        <Stack align='center' gap={0}>
            <div className="loading-spinner-container">
                <Avatar color='red' size={37} radius={100} > <FaExclamation size="22" /> </Avatar>
            </div>
            <Text fw='450' mt={6} c='red'>
                {text}
            </Text>
        </Stack>
    )

    if (success) return (
        <Stack align='center' gap={0}>
            <div className="loading-spinner-container">
                <Avatar color='green' size={37} radius={100} > <FaCheck size="22" /> </Avatar>
            </div>
            <Text fw='450' mt={6} c='green'>
                {text}
            </Text>
        </Stack>
    );

    return (
        <Stack align='center' gap={0}>
            <div className="loading-spinner-container">
                <Loader color={primaryColor} />
            </div>
            <Text fw='450' c={primaryColor}>
                {text}
            </Text>
        </Stack>
    )
}
