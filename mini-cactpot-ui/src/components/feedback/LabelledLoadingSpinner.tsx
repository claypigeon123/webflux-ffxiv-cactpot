import { Avatar, Loader, Stack, Text, useMantineTheme } from "@mantine/core";
import { FC } from "react";
import { FaCheck, FaExclamation } from "react-icons/fa6";

export interface LabelledLoadingSpinnerProps {
    text?: string,
    success?: boolean,
    error?: boolean
}

export const LabelledLoadingSpinner: FC<LabelledLoadingSpinnerProps> = ({ text = "Loading", success = false, error = false }) => {

    const { primaryColor } = useMantineTheme();

    if (error) return (
        <Stack align='center' gap={0}>
            <div className="loading-spinner-container">
                <Avatar color='red' size={58} radius={100} > <FaExclamation size="30" /> </Avatar>
            </div>
            <Text fw='450' size='xl' mt={6} c='red'>
                {text}
            </Text>
        </Stack>
    )

    if (success) return (
        <Stack align='center' gap={0}>
            <div className="loading-spinner-container">
                <Avatar color='green' size={58} radius={100} > <FaCheck size="30" /> </Avatar>
            </div>
            <Text fw='450' size='xl' mt={6} c='green'>
                {text}
            </Text>
        </Stack>
    );

    return (
        <Stack align='center' gap={0}>
            <div className="loading-spinner-container">
                <Loader size='xl' color={primaryColor} />
            </div>
            <Text fw='450' size='xl' c={primaryColor}>
                {text}
            </Text>
        </Stack>
    )
}
